package com.example.miroslavnikolov.game;

/**
 * Created by miroslavnikolov on 22.05.16.
 */

public class Playground {
    public void setPrinter(PlaygroundPrinter printer) {
        this.printer = printer;
    }

    PlaygroundPrinter printer;
//    Player firstPlayer;
//    Player secondPlayer;


    private int rows;
    private int cols;


    private Field[][] matrix;
    private Field smallMatrix[][];


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
        smallMatrix = new Field[rows/3][cols/3];

        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                setBigField(row,col,Field.Free);
                setField(row,col,Field.Free);
            }
        }

//        for (int row = 0; row < rows/3; row++)
//        {
//            for (int col = 0; col < cols/3; col++)
//            {
//
//            }
//        }
    }

    public Field getField(int row, int col) { return  this.matrix[row][col]; }
    public Field getBigField(int row, int col) { return  this.smallMatrix[row][col]; }

    public boolean setField(int row, int col, Field value)
    {
//        if(matrix[row][col] != null && matrix[row][col] != Field.Free) {
//
////            throw new IllegalArgumentException("Want to set second time field");
//            return false;
//        }

        if(smallMatrix[row/3][col/3] == Field.X || smallMatrix[row/3][col/3] == Field.Circle)
        {
            return false;
        }

        if(matrix[row/3][col/3] == Field.X || matrix[row/3][col/3] == Field.Circle)
        {
            return false;
        }

        matrix[row][col] = value;
        if (value == Field.X) {
//            firstPlayer.AddField(row, col);
            printer.PrintField(row,col);
        }
        else if(value == Field.Circle) {
//            secondPlayer.AddField(row, col);
            printer.PrintField(row,col);
        }

        setBigField(row,col,value);
        return true;
    }
    public void setBigField(int row, int col, Field value)
    {
        if(value == Field.Free)
        {
            smallMatrix[row/3][col/3] = value;
        }
        else if(smallMatrix[row/3][col/3] == Field.Free)
        {
//            if(row/3 == 0 && col/3 == 0)
//            {
//                if(getField(row + 1,col) == value && getField(row + 2,col) == value ||
//                        getField(row,col + 1) == value && getField(row,col + 2) == value ||
//                        getField(row + 1, col + 1) == value && getField(row + 2,col + 2) == value)
//                {
//                    smallMatrix[row/3][col/3] = value;
//                }
//            }
//            else if(row/3 == 0 && col/3 == 1)
//            {
//
//            }

//            for (int r = 0; r < rows; r+=3)
//            {
//                for (int c = 0; c < cols; c++)
//                {
//                    if(getField(row,col) == value && getField(row + 1, col) == value && getField(row + 2, col) == value)
//                    {
//
//                    }
//                }
//            }

            boolean check = false;
            int nRow = row - row%3;
            int nCol = col - col%3;
            for (int r = nRow; r < nRow + 3 ; r++)
            {
                if (getField(r, nCol) == value &&
                        getField(r, nCol + 1) == value &&
                        getField(r, nCol + 2) == value
                        )
                {
                    smallMatrix[row/3][col/3] = value;
                    printer.PrintBigField(row, col);
                    return;
                }
            }

            for (int c = nCol; c < nCol + 3 ; c++)
            {
                if(getField(nRow, c) == value &&
                        getField(nRow + 1, c) == value &&
                        getField(nRow + 2, c) == value)
                {
                    smallMatrix[row/3][col/3] = value;
                    printer.PrintBigField(row, col);
                    return;
                }
            }

            if(getField(nRow + 1, nCol + 1) == value)
            {
                if( (getField(nRow,nCol) == value &&
                        getField(nRow + 2, nCol + 2) == value) ||

                        getField(nRow, nCol + 2) == value &&
                        getField(nRow + 2, nCol) == value)
                {
                    smallMatrix[row/3][col/3] = value;
                    printer.PrintBigField(row, col);
                }
            }
        }

//        smallMatrix[row][col] = value;
    }

    public boolean canMove(int row, int col)
    {
        if(matrix[row][col] == Field.Free)
            return true;
        else return false;
    }

    //    private boolean CheckField(int row, int col,Field matrix[][])
//    {
//        Field value = Field.Circle;
//        for (int i = 0; i < 2; i++)
//        {
//
//            for (int r = row; r < row + 3 ; r++)
//            {
//                if (getField(r, col) == value &&
//                        getField(r, col + 1) == value &&
//                        getField(r, col + 2) == value
//                        )
//                {
//                    smallMatrix[row/3][col/3] = value;
//                    printer.PrintBigField(row, col);
//                    return true;
//                }
//            }
//
//            for (int c = col; c < col + 3 ; c++)
//            {
//                if(getField(row, c) == value &&
//                        getField(row + 1, c) == value &&
//                        getField(row + 2, c) == value)
//                {
//                    smallMatrix[row/3][col/3] = value;
//                    printer.PrintBigField(row, col);
//                    return true;
//                }
//            }
//
//            if(getField(row + 1, col + 1) == value)
//            {
//                if( (getField(row,col) == value &&
//                        getField(row + 2, col + 2) == value) ||
//
//                        getField(row, col + 2) == value &&
//                                getField(row + 2, col) == value)
//                {
//                    smallMatrix[row/3][col/3] = value;
//                    printer.PrintBigField(row, col);
//                    return true;
//                }
//            }
//            value = Field.X;
//
//        }
//        return false;
//    }

    public Playground(int rows, int cols) {
        setRows(rows);
        setCols(cols);

        setMatrix(rows, cols);

//        firstPlayer = new Player("First player", Field.X);
//        secondPlayer = new Player("Second player", Field.Circle);
    }

    public Playground(int rows, int cols, PlaygroundPrinter printer) {
        this(rows, cols);

        printer.setPlayground(this);
    }

//    private class Player
//    {
//        private final Field type;
//        List<Pair<Integer, Integer>> fields;
//
//        public void AddField(int row, int col)
//        {
//            Pair<Integer, Integer> location = new Pair<>(row, col);
//            fields.add(location);
//        }
//
//        Player(String name, Field type)
//        {
//            this.type = type;
//            fields = new ArrayList<Pair<Integer, Integer>>();
//        }
//
//    }

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