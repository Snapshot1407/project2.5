package com.mygdx.buhoma;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Array;


public class GameScreen extends BaseScreen {
    // игровое меню(2-экран)
    Stage stage;
    final Buhoma game;
    OrthographicCamera camera;
    int []k;
    int Switch_i;
    //переменные для таймера спавна
    long timeGame= TimeUtils.millis(),timeSpawn=3000,timeLastSpawn=TimeUtils.millis()-4000;
    //переменная для количества букв в слове
    long timeMenu;
    byte n_menu = 0;
    boolean menu =false;
    public int count_m;// переменная для выбора случайного слова
    //массив буквы рандомной
    Array<Integer> arrayLetter = new Array<>();
    //рабочии объекты
    Array <Letter> letter = new Array<>();//создает массив нужного размера для слова(которое рандомом выбрали)
    Image terem, fon,sun;
    private MyFontButton skin;
    private TextButton exit_to_menu;
    final float const_sun = 0.29166666666666666666666666666667f;
    short num = 0;
    public void setSwitch_i(int Switch_i){
        this.Switch_i = Switch_i;
    }
    public GameScreen(final Buhoma game){
        super(game);
        this.game = game;

    }

    public void setArrayLetter(int [] k) {
        this.k = k;
        for (int j = 0;j < k.length; j++) arrayLetter.add(k[j]);
    }

    @Override
    public void show() {
        if (game.menuPauseScreen.exit) {
            switch (Switch_i){

                case 0:
                    count_m =  (int) MathUtils.random(0,5);

                    break;
                case 1:
                    count_m =  (int) MathUtils.random(6,11);

                    break;
                case 2:
                    count_m = (int) MathUtils.random(12,16);
                    break;
                case 3:
                    count_m =  (int) MathUtils.random(17,21);
                    break;
                case 4:
                    count_m =  (int) MathUtils.random(22,26);
                    break;
                case 5:
                    count_m =  (int) MathUtils.random(27,31);
                    break;
                case 6:
                    count_m =  (int) MathUtils.random(32,36);
                    break;
                case 7:
                    count_m =  (int) MathUtils.random(37,42);
                    break;
                case 8:
                    count_m =  (int) MathUtils.random(43,48);
                    break;
                case 9:
                    count_m = (int) MathUtils.random(49,58);
                    break;

            }
            setArrayLetter(game.levelScreen.k[count_m]);

            //count_m = MathUtils.random(k.length-1);
            letter = new Array<>();
            stage = new Stage();
            // устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
            camera = new OrthographicCamera(game.width, game.height);
            // этим методом мы центруем камеру на половину высоты и половину ширины
            camera.setToOrtho(false,game.width,game.height);
            //создаем рабочии инструменты
            terem = new Image(new Texture(Gdx.files.internal("images/teremok.png")));
            fon = new Image(new Texture(Gdx.files.internal("images/fon1.png")));
            sun = new Image(new Texture(Gdx.files.internal("images/sun.png")));

            fon.setSize(game.width,game.height);
            terem.setPosition(-game.width/15 ,(game.height/33*11));
            terem.setSize( game.width/2, game.height/2);

            float sun_size = ((game.width<game.height)?game.width:game.height)*const_sun;
            sun.setSize(sun_size,sun_size);
            sun.setPosition(game.width-sun_size, game.height-sun_size);

            stage.addActor(fon);
            stage.addActor(terem);
            stage.addActor(sun);

            skin = new MyFontButton();
            //создание кнопок

            exit_to_menu = new TextButton("Меню",skin.textButtonStyle_exit);
            //Setsize button
            exit_to_menu.setSize(game.width*MainMenuScreen.const_button_width,game.height*MainMenuScreen.const_button_height);

            //setPosition button
            exit_to_menu.setPosition(game.width - exit_to_menu.getWidth(),0);
            exit_to_menu.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    pause();

                    menu=true;
                    game.menuPauseScreen.setScreen("Game");
                    game.setScreen(game.menuPauseScreen);
                }
            });

            // добавление объектов на сцеену

            stage.addActor(exit_to_menu);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);

        timeGame = TimeUtils.millis();
        //создание буквы по таймеру игровому (реализация через список слова ,вылет букв слова в рандомном порядке)
        if(!letter.isEmpty()){
            letter.get(0).First=true;
            if (!letter.get(0).isAlive())letter.removeIndex(0);
        }
        if (!menu && arrayLetter.notEmpty() && (timeGame - timeLastSpawn > timeSpawn+(timeMenu*n_menu))){

            Letter letter1 = new Letter();
            n_menu=0;

            letter1.setPosition(400,400);
            letter1.setType(arrayLetter.get(num));
            letter1.setStringScreen("Game");
            letter1.setSize(game.width,game.height);
            letter1.setAlive(true);

            letter.add(letter1);
            stage.addActor(letter1);
            arrayLetter.removeIndex(num);
            timeLastSpawn=TimeUtils.millis();


        }

        if (arrayLetter.isEmpty() && letter.isEmpty()){
           /* int n = 0;
            for (int i = 0; i < letter.size; i++) {
                if (!letter.get(i).isAlive()) n++;
                if ( n == letter.size ) {*/

                    game.questScreen.massive(k);
                    game.questScreen.cntWord(count_m);
                    game.menuPauseScreen.exit = true;
                    game.setScreen(game.questScreen);
                    dispose();

              /*  }
            }*/
        }

        camera.update();

        //отрисовка изображений
        stage.act(delta);
        stage.draw();


    }

    @Override
    public void pause() {
        for (Letter actor:letter
        ) {
            actor.stop();
        }
        timeGame = 0;

    }

    @Override
    public void resume() {
        for (Letter actor:letter
        ) {
            actor.resume();
        }
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {// не забывай!!!
        stage.dispose();


    }
}
