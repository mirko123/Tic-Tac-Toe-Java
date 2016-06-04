package com.example.miroslavnikolov.game;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miroslavnikolov on 22.05.16.
 */

public class Playground {
    public void setPrinter(PlaygroundPrinter printer) {
        this.printer = printer;
    }

    PlaygroundPrinter printer;
    Player firstPlayer;
    Player secondPlayer;


    private int rows;
    private int cols;


    private Field[][] matrix;


    public static enum Field
    {
        Free,
        Circle,
        X
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public Field[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int rows, int cols) {
        matrix = new Field[rows][cols];
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                setField(row,col,Field.Free);
            }
        }
    }

    public Field getField(int row, int col) { return  this.matrix[row][col]; }

    public void setField(int row, int col, Field value)
    {
//        if(matrix[row][col] != Field.Free)
//            throw new IllegalArgumentException("Want to set second time field");

        matrix[row][col] = value;
        if (value == Field.X) {
            firstPlayer.AddField(row, col);
            printer.PrintField(row,col);
        }
        else if(value == Field.Circle) {
            secondPlayer.AddField(row, col);
            printer.PrintField(row,col);
        }


    }

    public Playground(int rows, int cols) {
        setRows(rows);
        setCols(cols);

        setMatrix(rows, cols);

        firstPlayer = new Player("First player", Field.X);
        secondPlayer = new Player("Second player", Field.Circle);
    }

    public Playground(int rows, int cols, PlaygroundPrinter printer) {
        this(rows, cols);

        printer.setPlayground(this);
    }

    private class Player
    {
        private final Field type;
        List<Pair<Integer, Integer>> fields;

        public void AddField(int row, int col)
        {
            Pair<Integer, Integer> location = new Pair<>(row, col);
            fields.add(location);
        }

        Player(String name, Field type)
        {
            this.type = type;
            fields = new ArrayList<Pair<Integer, Integer>>();
        }

    }

//    public void PrintMatrix(Canvas canvas)
//    {
//        int width = canvas.getWidth();
//        int height  = canvas.getHeight();
//
//        float fieldWidth = width/cols;
//        float fieldHeight = height/rows;
//
//        for (int row = 0; row < rows; row++)
//        {
//            canvas.drawLine(0, fieldHeight*row, width, fieldHeight*row, rPaint);
//        }
//
//        for (int col = 0; col < cols; col++)
//        {
//            canvas.drawLine(fieldWidth*col, 0, fieldWidth*col, height, rPaint);
//        }
//
//        for (int row = 0; row <= rows; row+=3)
//        {
//            canvas.drawLine(0, fieldHeight*row, width, fieldHeight*row, gPaint);
//        }
//
//        for (int col = 0; col <= cols; col+=3)
//        {
//            canvas.drawLine(fieldWidth*col, 0, fieldWidth*col, height, gPaint);
//        }
//    }
}