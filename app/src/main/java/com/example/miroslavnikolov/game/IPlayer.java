package com.example.miroslavnikolov.game;

/**
 * Created by miroslavnikolov on 23.05.16.
 */
public interface IPlayer {
    public String GetName();
    public void SetName(String name);
    public void setPosition(GamePlay.Position position);
//    public Playground.Field type = Playground.Field.X;
    public Playground.Field GetType();
    public GamePlay.Position ReturnPosition();
//    public void Update();
}
