package com.example.miroslavnikolov.game;

/**
 * Created by miroslavnikolov on 1.06.16.
 */
public class OtherPlayer implements IPlayer {
    private String name;
    public Playground.Field type;
    public GamePlay.Position lastPosition;


    public OtherPlayer() {
        this(Playground.Field.Circle);
    }
    public OtherPlayer(Playground.Field field) {
        this.type = field;
        lastPosition = new GamePlay.Position(0,0);
    }


    @Override
    public String GetName() {
        return name;
    }

    @Override
    public void SetName(String name) {
        this.name = name;
    }

    @Override
    public void setPosition(GamePlay.Position position) {
        this.lastPosition = position;
    }

    @Override
    public Playground.Field GetType() {
        return type;
    }

    @Override
    public GamePlay.Position ReturnPosition() {


        return lastPosition;
    }
}
