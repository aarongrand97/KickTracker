package com.example.kicktracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class gridView extends View {

    protected Paint blackPaint = new Paint();
    protected Paint selectPaint = new Paint();
    protected int selectedRow, selectedCol;

    public gridView(Context context){
            this(context, null);
        }
        public gridView(Context context, AttributeSet attrs){
            super(context, attrs);
            blackPaint.setStyle(Paint.Style.STROKE);
            blackPaint.setColor(Color.BLACK);
            blackPaint.setStrokeWidth(3);

            selectPaint.setColor(Color.BLUE);


            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metrics);

            selectedRow = -1;
            selectedCol = -1;
        }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height =(getHeight());
        canvas.drawRect(0,0, width, height, blackPaint);

        for (int i = 1; i!= 10; i++){
            int xPos = i*width/10;
            canvas.drawLine(xPos, 0, xPos, height, blackPaint);
        }
        for (int i = 1; i!= 7; i++){
            int yPos = i*height/7;
            canvas.drawLine(0, yPos, width, yPos, blackPaint);
        }



        fillSelectedCell(canvas, selectedRow,selectedCol, selectPaint);
    }

    protected void fillSelectedCell(Canvas canvas, int r, int c, Paint paint){

        if(selectedCol == -1 || selectedRow == -1)
            return;
        else {
            fillCell(canvas, r, c, paint);
        }
    }

    protected void fillCell(Canvas canvas, int r, int c, Paint paint){
        int margin = 4;
        canvas.drawRect((c*getWidth()/10)+margin, (r*getHeight()/7)+margin, ((c+1)*getWidth()/10)-margin, ((r+1)*getHeight()/7)-margin, paint);
    }

    public Paint getCellColour(int successPercentage){
        Paint paint = new Paint();

        int red,green,blue;
        blue = 0;

        if(successPercentage<=50){
            // RGB 255,0,0 - 255,255,0
            red = 255;
            green = 255/50 * successPercentage;
        }
        else{
            green = 255;
            red = 255 - ((255/50) * successPercentage);
        }
        paint.setColor(Color.rgb(red,green,blue));
        return paint;
    }

    public void setSelectedCell(int row, int col){
        selectedRow = row;
        selectedCol = col;
    }

    public int getSelectedRow(){
        return selectedRow;
    }
    public int getSelectedCol(){
        return selectedCol;
    }

    protected int[][][] convertSessionStringToArray(String sessionString){
        int[][][] returnArray = new int[7][10][2];

        for(int i = 0; i != sessionString.length(); i++){
            int row = (int)Math.floor(i/20);
            int col = (int)Math.floor((i%20)/2);
            if(i%2 == 0){
                returnArray[row][col][0] = Character.getNumericValue(sessionString.charAt(i));
            }
            else{
                returnArray[row][col][1] = Character.getNumericValue(sessionString.charAt(i));
            }

        }

        return returnArray;

    }

}
