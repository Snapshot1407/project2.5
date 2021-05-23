package com.mygdx.buhoma;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Letter extends Actor {
    public int type;
    public float x,y;
    public float height,width;
    final float const_width = 0.0390625f;
    final float const_height = 0.0694444f;
    boolean increase;
    boolean snd;
    boolean touchabl;
    private float height_SRC,width_SRC;
    final String quest = "Quest", game = "Game";
    boolean marker;
    boolean stop;
    boolean First=false;
    String screen;
    Texture imgABC[] = new Texture[33];
    Sound[] sndABC = new Sound[33];


    public float dx, dy, mx, my;

    public boolean alive = false;
    //перестроить летер  на циркл

    public Letter(){
        increase = true;
        touchabl = true;
        marker = false;
        stop = false;
        snd = true;
        dx = MathUtils.random(1,2);
        dy = MathUtils.random(1,2);
        for (int i =0;i<imgABC.length;i++)imgABC[i] = new Texture(Gdx.files.internal("letters/images/"+i+".png"));
        for (int i = 0; i < sndABC.length; i++) sndABC[i] = Gdx.audio.newSound(Gdx.files.internal("letters/audios/" + i+".mp3"));
    }
    public boolean isAlive(){
        return alive;
    }
    boolean isIncrease() {return increase;}
    public boolean isTouchabl() { return touchabl;}
    public boolean isSnd(){ return snd; }
    public boolean isMarker(){return marker;}
    public boolean isStop(){return  stop;}

    //////////////////

    public void setMarker(boolean marker){this.marker = marker;}
    public void setStringScreen(String s){screen = s;}
    public void setTouchabl(boolean touche){this.touchabl = touche;}
    public void setIncrease(boolean increase){this.increase = increase;}
    void setType(int n){
        type = n;
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    public void setStop(boolean stop){this.stop = stop; }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }
    void setSnd(boolean snd){
        this.snd = snd;
    }
    public void setSize(float width, float height){
        width_SRC = width;
        height_SRC = height;
        this.width = width * const_width;
        this.height = height * const_height;
    }
    public void setActor(float width, float height){
        this.width = width;
        this.height = height;
        playSnd();
        stop();
    }
    public void act(float delta){
        super.act(delta);
        x += dx;
        y += dy;
        if (screen == game){
            if (x> width_SRC-width|| x<0)dx=-dx;
            if (y>height_SRC-height ||y<100)dy=-dy;}
        if (screen == quest){
            if (x> width_SRC * QuestScreen.const_Width_P + (width_SRC * QuestScreen.const_Width_S) - width   || x < width_SRC * QuestScreen.const_Width_S )dx=-dx;
            if (y>height_SRC * QuestScreen.const_Height_P + (height_SRC * QuestScreen.const_Height_S) - height || y < height_SRC * QuestScreen.const_Height_S)dy=-dy;
        }
    }
    public Actor hit(float x, float y, boolean touchable){
        super.hit(x,y,this.touchabl);
        if (screen==game && x > this.x && x < this.x + width && y > this.y && y < this.y + height && isTouchabl() && First){
            setAlive(false);
            playSnd();
        }
        if (screen==quest && x > this.x && x < this.x + width && y > this.y && y < this.y + height && isTouchabl() && First){
            setAlive(false);


        }

        return null;
    }
    void stop(){
        mx = dx;
        my = dy;
        dx = dy = 0;
        setAlive(false);
        setIncrease(false);
        setTouchabl(false);
        setSnd(false);
        setStop(true);


    }
    void resume(){
        dx = mx;
        dy = my;
        setAlive(true);
        setIncrease(true);
        setTouchabl(true);
        setSnd(true);
        setStop(false);

    }
    public void draw (Batch batch, float parentAlpha){

        super.draw(batch, parentAlpha);
        if ((isAlive() && isIncrease() && isTouchable()) || isMarker()) batch.draw(imgABC[type], x, y, width, height);

    }
    void playSnd(){
        if (isSnd()){
            sndABC[type].play();
            setSnd(false);}

    }
}
