package com.mygdx.buhoma;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenuScreen extends BaseScreen {
    Stage stage;
    OrthographicCamera camera;
///главное меню(1-экран)


    public final static float const_button_width = 0.15625f;
    public final static float const_button_height = 0.13889f;

    private MyFontButton skin;

    private TextButton play;
    private TextButton exit;


    public MainMenuScreen( final Buhoma game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage();

        Image fon = new Image(new Texture(Gdx.files.internal("images/MainFon.jpg")));
        fon.setSize(game.width,game.height);
        stage.addActor(fon);

        Gdx.input.setInputProcessor(stage);

        // устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
        camera = new OrthographicCamera(game.width, game.height);

        // этим методом мы центруем камеру на половину высоты и половину ширины
        camera.setToOrtho(false,game.width,game.height);

        // кожа кнопки
        skin = new MyFontButton();

        //создание кнопок
        play = new TextButton("Играть",skin.textButtonStyle_play);
        exit = new TextButton("Выход",skin.textButtonStyle_exit);

        //Setsize button
        play.setSize(game.width*const_button_width,game.height*const_button_height);
        exit.setSize(game.width*const_button_height,game.height*const_button_height);

        //setPosition button
        play.setPosition((game.width/2f - play.getWidth()/2) ,game.height - (game.height/4) - (play.getHeight()/2));
        exit.setPosition((game.width/2f - exit.getWidth()/2), (game.height/4) - (play.getHeight()/2));

        //повесили слушателей
        play.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.dispose();
                game.setScreen(game.levelScreen);
            }
        });
        exit.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        // добавление объектов на сцеену
        stage.addActor(play);
        stage.addActor(exit);
    }

    @Override
    public void render(float delta) {
        //рендер
        camera.update();
        // запуск метода act актеров, которые были добавлены в сцену
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        // прорисовка сцены
        stage.draw();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {//не забывай!!!
        stage.dispose();

    }
}
