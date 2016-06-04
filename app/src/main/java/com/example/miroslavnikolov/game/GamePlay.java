package com.example.miroslavnikolov.game;


/**
 * Created by miroslavnikolov on 23.05.16.
 */
public class GamePlay implements Runnable {


    private final Playground playground;
    public IPlayer firstPlayer;
    public IPlayer secondPlayer;
    private IPlayer currentPlayer;

    private static volatile GamePlay instance;

    public static void Init(Playground playground, IPlayer firstPlayer)
    {
        Init(playground,firstPlayer, new Player(Playground.Field.X) );
    }
    public static void Init(Playground playground, IPlayer firstPlayer, IPlayer secondPlayer)
    {
        if(instance == null)
        {
            instance = new GamePlay(playground, firstPlayer, secondPlayer);
        }
    }

    public static GamePlay getInstance()
    {
        return instance;
    }

    private GamePlay(Playground playground, IPlayer firstPlayer, IPlayer secondPlayer)
    {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = new Player(Playground.Field.X);
        this.currentPlayer = new Player();
        this.playground = playground;
    }

    public void setFirstPlayer()
    {

    }

    public void PushField()
    {

    }

    public void CheckWin()
    {

    }

    public void SetField()
    {
        Playground.Field type;

//        while(haveWinner() == false)
//        {

        System.out.println("here          0");

        for (int i =0; i < 20; i++){
            IPlayer player = NextPlayer();
            type = player.GetType();

            System.out.println("here          1");
            synchronized (this) {
                try {
                    System.out.println("here          2");
                    System.out.println(player.GetType());
                    this.wait(); // Will block until lock.notify() is called on another thread.
                    Position position = player.ReturnPosition();


                    System.out.println(position.row);
                    System.out.println(position.col);
                    System.out.println(type);
                    playground.setField(position.row,position.col, type);

                } catch (InterruptedException e) {

                    System.out.println("here          3");
                    e.printStackTrace();
                }

                System.out.println("here          4");
            }
            System.out.println("here          5");




            System.out.println("here          6");

        }
        System.out.println("here          7");

//        while(!haveWinner())
//        {
//            IPlayer player = NextPlayer();
//        }
    }
    private IPlayer NextPlayer()
    {
        if(currentPlayer == firstPlayer)
        {
            currentPlayer = secondPlayer;
           // currentPlayer = firstPlayer;
        }
        else
        {
            currentPlayer = firstPlayer;
        }
        return currentPlayer;
    }

    private boolean haveWinner() {
        return false;
    }

    @Override
    public void run() {
        SetField();
    }

    public static class Position
    {
        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
        }

        public int row;
        public int col;

        public Position(int row,int col)
        {
            this.row = row;
            this.col = col;
        }
    }

    public static void HavePosition()
    {

    }

//    public
}
