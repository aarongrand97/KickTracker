package com.example.kicktracker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class gridViewKickInput extends gridView {
    NewKickActivity m_parentSession;

    public gridViewKickInput(Context context){
        this(context, null);
    }
    public gridViewKickInput(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void setM_parentSession(NewKickActivity parent){
        m_parentSession = parent;
    }

    private void handleTouchEvent(float x, float y){
        selectedCol = (int) x/(getWidth()/10);
        selectedRow = (int) y/(getHeight()/7);
        m_parentSession.enableStore();
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
}
