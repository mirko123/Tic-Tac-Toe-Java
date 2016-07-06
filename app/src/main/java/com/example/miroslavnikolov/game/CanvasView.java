package com.example.miroslavnikolov.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

//import java.util.Set;


/**
 * Created by miroslavnikolov on 22.05.16.
 */

public class CanvasView extends View {

    /** Called when the activity is first created. */
    PlaygroundPrinter printer;
    private static Playground playground;
    private static IPlayer player1;
    private static IPlayer player2;
    private static NetworkManager2 networkManager2;
    float Mx1,My1;
    float x,y;

    private static final float MINP = 0.25f;
    private static final float MAXP = 0.75f;

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mBitmapPaint;
    GamePlay gamePlay;
    Thread thread;

    public CanvasView(Context context, IPlayer firstPlayer, IPlayer secondPlayer) {
        super(context);

        if(networkManager2 == null)
        {
//            NetworkManager2.Init("Miro","88.87.1.226");
//            NetworkManager2.Init("Miro");
            networkManager2 = NetworkManager2.getInstance();
            networkManager2.setContext(getContext());
        }

        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        if(playground == null) this.playground = new Playground(9, 9);
//        if(player1 == null) this.player1 = new Player(networkManager2.getName());
//        if(player2 == null) this.player2 = new OtherPlayer();
        if(player1 == null) this.player1 = firstPlayer;
        if(player2 == null) this.player2 = secondPlayer;

        GamePlay.Init(playground, player1, player2, this);

        if(gamePlay == null) gamePlay = GamePlay.getInstance();
        if(thread == null)
        {
            thread = new Thread(gamePlay);
//            gamePlay.run();
            thread.start();
        }

        System.out.println("here 0");

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        mBitmap = Bitmap.createBitmap(200, 300, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);


        this.printer = new PlaygroundPrinter(mCanvas, playground);
        playground.setPrinter(printer);
        printer.PrintPlayground();
//        gamePlay.SetField();

        //gamePlay.run();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawColor(0xFFAAAAAA);

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
//        canvas.drawPath(mPath, mPaint);

//        playground.setField(7, 7, Playground.Field.Circle);

//        synchronized ()

    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        float xPos = 0;
        float yPos = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                xPos = event.getX();
                yPos = event.getY();
                invalidate(); // add it here
                break;
            case MotionEvent.ACTION_UP :
                xPos = event.getX();
                yPos = event.getY();
                invalidate(); // add it here

                int row = printer.PositionInRow(yPos);
                int col = printer.PositionInCol(xPos);

                if(row < playground.getRows() && col < playground.getCols() && gamePlay.currentPlayer == gamePlay.firstPlayer)
                {
                   // playground.setField(row,col, Playground.Field.X);

                    GamePlay.Position pos = new GamePlay.Position(row, col);

                    player1.setPosition(pos);
                    networkManager2.sendClickPosition(pos.getRow(), pos.getCol());
                }
               // gamePlay.run();
//                gamePlay.notify();



//                try {
//                    sender();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


//                HashMap<String, String> map = new HashMap<>();
//                map.put("name","miro");
//                String response = networkManager.performPostCall("http://88.87.1.226:1337/");
//                String response = networkManager.performPostCall("1337", map);
//                networkManager.askForPlayers();

//                String response = NetworkManager.performPostCall("http://192.168.0.100:1337/");
//                System.out.println(response);


                synchronized (gamePlay)
                {
                    gamePlay.notify();
                }
                break;
        }

        return true;

    }

    public void winnerEvent(String text)
    {
        Looper.prepare();
//        MessageQueue queue = Looper.myQueue();
//        queue.addIdleHandler(new MessageQueue.IdleHandler() {
//            int mReqCount = 0;
//
//            @Override
//            public boolean queueIdle() {
//                if (++mReqCount == 2) {
//                    Looper.myLooper().quit();
//                    return false;
//                } else
//                    return true;
//            }
//        });


        networkManager2.out.println("have Winner");

        Toast.makeText(this.getContext(), text,Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this.getContext(), ListActivity.class);
        this.getContext().startActivity(intent);
//        finish();

        Looper.loop();
    }

    public void alertForBadClick()
    {
        Looper.prepare();

        String text = "bad click";
        Toast.makeText(this.getContext(), text,Toast.LENGTH_LONG).show();
        Looper.loop();
    }


}
