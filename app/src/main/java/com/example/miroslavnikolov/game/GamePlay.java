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
//        this.currentPlayer = new Player();
        this.currentPlayer = firstPlayer;
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

//        for (int i =0; i < 20; i++){
//            IPlayer player = NextPlayer();
//            type = player.GetType();
//
//            System.out.println("here          1");
//            synchronized (this) {
//                try {
//                    System.out.println("here          2");
//                    System.out.println(player.GetType());
//                    this.wait(); // Will block until lock.notify() is called on another thread.
//                    Position position = player.ReturnPosition();
//                    System.out.println("here          2.1");
//
//
//                    System.out.println(position.row);
//                    System.out.println(position.col);
//                    System.out.println(type);
//                    playground.setField(position.row,position.col, type);
//
//                } catch (InterruptedException e) {
//
//                    System.out.println("here          3");
//                    e.printStackTrace();
//                }
//
//                System.out.println("here          4");
//            }
//            System.out.println("here          5");
//
//
//            System.out.println("--------------------");
//
//            System.out.println("here          6");
//
////            if(haveWinner())
////            {
////                System.out.println("===================================");
////                System.out.println("===================================");
////
////            }
//
//        }
        System.out.println("here          7");


        IPlayer player = NextPlayer();
        while(!haveWinner())
        {
            type = player.GetType();

            System.out.println("here          1");
            synchronized (this) {
                try {
                    System.out.println("here          2");
                    System.out.println(player.GetType());
                    this.wait(); // Will block until lock.notify() is called on another thread.
                    Position position = player.ReturnPosition();

//                    if(playground.getField(position.row, position.col) != Playground.Field.Free
//                            || playground.getBigField(position.row/3, position.col/3) != Playground.Field.Free)
//                    {
//                        System.out.println("=================================");
//                        System.out.println("=================================");
//                        continue;
//                    }
                    System.out.println("here          2.1");


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
            player = NextPlayer();
        }
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
        Playground.Field value = Playground.Field.X;
        for (int i = 0; i < 2; i ++)
        {
            for (int r = 0; r < playground.getRows() / 3 ; r++)
            {
                if (playground.getBigField(r, 0) == value &&
                        playground.getBigField(r, 1) == value &&
                        playground.getBigField(r, 2) == value
                        )
                {
                    return true;
                }
            }

            for (int c = 0; c < playground.getCols() / 3 ; c++)
            {
                if(playground.getBigField(0, c) == value &&
                        playground.getBigField(0 + 1, c) == value &&
                        playground.getBigField(0 + 2, c) == value)
                {
                    return true;
                }
            }

            if(playground.getBigField(1, 1) == value)
            {
                if( (playground.getBigField(0,0) == value &&
                        playground.getBigField(2,2) == value) ||

                        playground.getBigField(0,2) == value &&
                                playground.getBigField(2,0) == value)
                {
                    return true;
                }
            }

            value = Playground.Field.Circle;
        }



        return false;
    }
//    private boolean canMove()
//    {
//        return true;
//    }


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
