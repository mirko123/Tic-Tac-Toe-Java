package com.example.miroslavnikolov.game;

/**
 * Created by miroslavnikolov on 23.05.16.
 */
public class Player implements IPlayer {
    private String name;
    public Playground.Field type;
    public GamePlay.Position lastPosition;
    public Thread thread;

    public Player() {
        this(Playground.Field.Circle);
    }
    public Player(Playground.Field field) {
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
        GamePlay.Position position;// = new GamePlay.Position(2,2);
        position = lastPosition;
//        synchronized (this) {
//            try {
//                System.out.println("here          2");
//                System.out.println(this.GetType());
//                this.wait(); // Will block until lock.notify() is called on another thread.
//            } catch (InterruptedException e) {
//
//                System.out.println("here          3");
//                e.printStackTrace();
//            }
//
//            System.out.println("here          4");
//        }
        return position;
    }
}
