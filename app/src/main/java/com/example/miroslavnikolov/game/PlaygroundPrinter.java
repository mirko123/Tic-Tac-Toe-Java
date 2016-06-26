package com.example.miroslavnikolov.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by miroslavnikolov on 22.05.16.
 */
public class PlaygroundPrinter {
    public void setPlayground(Playground playground) {
        this.playground = playground;
    }

    Playground playground;

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    Canvas canvas;

    Paint rPaint;
    Paint gPaint;
    Paint grayPaint;

    int rows, cols;
    int width;
    int height;

    public float getFieldWidth() {
        return fieldWidth;
    }

    public float getFieldHeight() {
        return fieldHeight;
    }

    float fieldWidth;
    float fieldHeight;

    public PlaygroundPrinter(Canvas canvas) {
        this.canvas = canvas;


        rPaint = new Paint();
//        rPaint.setAntiAlias(true);
//        rPaint.setDither(true);
//        rPaint.setColor(0xFFFF0000);
//        rPaint.setStyle(Paint.Style.STROKE);
//        rPaint.setStrokeJoin(Paint.Join.ROUND);
//        // rPaint.setStrokeCap(Paint.Cap.ROUND);
//        rPaint.setStrokeWidth(10);
//        rPaint.setStrokeWidth(10);

        rPaint.setColor(0xFFFF0000);

        grayPaint = new Paint();
        grayPaint.setColor(0x7F7F7F7F);

        gPaint = new Paint();
        gPaint.setAntiAlias(true);
        gPaint.setDither(true);
        gPaint.setColor(Color.GREEN);
        gPaint.setStyle(Paint.Style.STROKE);
        gPaint.setStrokeJoin(Paint.Join.ROUND);
        // gPaint.setStrokeCap(Paint.Cap.ROUND);
        gPaint.setStrokeWidth(10);
        gPaint.setStrokeWidth(10);
    }

    public PlaygroundPrinter(Canvas canvas, Playground playground)
    {
        this(canvas);
        this.playground = playground;

        rows = playground.getRows();
        cols = playground.getCols();

        width = canvas.getWidth();
        height  = canvas.getHeight();
        if(width > height)
            width = height;
        else height = width;

        fieldWidth = width/cols;
        fieldHeight = height/rows;
    }


    public final void PrintPlayground()
    {
        for (int row = 0; row < rows; row++)
        {
            canvas.drawLine(0, fieldHeight*row, width, fieldHeight*row, rPaint);
        }

        for (int col = 0; col < cols; col++)
        {
            canvas.drawLine(fieldWidth*col, 0, fieldWidth*col, height, rPaint);
        }

        for (int row = 0; row <= rows; row+=3)
        {
            canvas.drawLine(0, fieldHeight*row, width, fieldHeight*row, gPaint);
        }

        for (int col = 0; col <= cols; col+=3)
        {
            canvas.drawLine(fieldWidth*col, 0, fieldWidth*col, height, gPaint);
        }
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                PrintField(row,col);
            }
        }
    }

    public void PrintField(int row, int col)
    {
        System.out.println("hereeeeeeeeeeeeeeeeeeeeeeeeee");
        System.out.println(playground.getField(row,col));
        if (playground.getField(row,col) == Playground.Field.Circle)
        {
            float rX = col*fieldWidth + fieldWidth/2;
            float rY =  row*fieldHeight + fieldHeight/2;
            float radius = (float) (((fieldHeight>fieldWidth?fieldWidth:fieldHeight)*0.4));

            canvas.drawCircle(rX,rY,radius ,gPaint);
        }
        else if (playground.getField(row,col) == Playground.Field.X)
        {
            float otstoqnie = 10;
            float startX = col*fieldWidth + otstoqnie;
            float startY = row*fieldHeight + otstoqnie;
            float endX = col*fieldWidth + fieldWidth - otstoqnie;
            float endY = row*fieldHeight + fieldWidth - otstoqnie;

            canvas.drawLine(startX, startY, endX, endY, gPaint);

            startY = row*fieldHeight + fieldHeight - otstoqnie;
            endY = row*fieldHeight + otstoqnie;

            canvas.drawLine(startX, startY, endX, endY, gPaint);
        }
    }
    public void PrintBigField(int row, int col)
    {
//        if(playground.getBigField(row,col) == Playground.Field.Circle)
//        {
//
//        }
//        else if(playground.getBigField(row,col) == Playground.Field.X)
//        {
//
//        }
        int r = row - row%3;
        int c = col - col%3;

        float startX = c*fieldWidth;
        float startY = r*fieldHeight;
        float endX = (c + 3)*fieldWidth;
        float endY = (r + 3)*fieldHeight;

        canvas.drawRect(startX, startY, endX, endY, grayPaint);
    }

    public int PositionInCol(float x)
    {
        int col = (int)(x/fieldWidth);
        return col;
    }
    public int PositionInRow(float y)
    {
        int row = (int)(y/fieldHeight);
        return row;
    }

}
