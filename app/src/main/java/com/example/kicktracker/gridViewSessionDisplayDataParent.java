package com.example.kicktracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class gridViewSessionDisplayDataParent extends gridViewSessionDisplay {

    ViewSessionDataActivity m_parentSession;

    public gridViewSessionDisplayDataParent(Context context){
        this(context, null);
    }

    public gridViewSessionDisplayDataParent(Context context, AttributeSet attrs){
        super(context, attrs);

    }

    private void handleTouchEvent(float x, float y){
        selectedCol = (int) x/(getWidth()/10);
        selectedRow = (int) y/(getHeight()/7);
        m_parentSession.displayGridSquareStats(selectedRow, selectedCol);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            handleTouchEvent(event.getX(), event.getY());
            return true;
        }
        else {
            return super.onTouchEvent(event);
        }
    }

    protected void setM_parentSession(ViewSessionDataActivity parent){
        m_parentSession = parent;
    }

    public void drawColourMap(Canvas canvas){
        int[][][] session = (m_parentSession).getSessionArray();
        for(int row = 0; row != 7; row++){
            for(int col = 0; col != 10; col++) {
                if(session[row][col][0] != 0){
                    int successPercentage = (int)(((float)session[row][col][1] / (float)session[row][col][0])*100);
                    Paint paint = getCellColour(successPercentage);
                    fillCell(canvas, row, col, paint);
                }
            }
        }
    }

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

        if(m_parentSession != null){
            drawColourMap(canvas);
        }

        fillSelectedCell(canvas, selectedRow,selectedCol, selectPaint);
    }



}
