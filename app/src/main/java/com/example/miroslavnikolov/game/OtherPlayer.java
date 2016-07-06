package com.example.miroslavnikolov.game;

/**
 * Created by miroslavnikolov on 1.06.16.
 */
public class OtherPlayer implements IPlayer {
    private String name;
    public Playground.Field type;
    public GamePlay.Position lastPosition;
    private NetworkManager2 networkManager2;


    public OtherPlayer(String name) {
        this(Playground.Field.Circle, name);
    }
    public OtherPlayer(Playground.Field field, String name) {
        this.type = field;
        this.name = name;
        lastPosition = new GamePlay.Position(0,0);
        networkManager2 = NetworkManager2.getInstance();
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

        GamePlay.Position newPosition = networkManager2.askForPosition();
        System.out.println("row: " + newPosition.getRow() + "  col: " + newPosition.getCol());

//        return lastPosition;
        return newPosition;
    }
}
