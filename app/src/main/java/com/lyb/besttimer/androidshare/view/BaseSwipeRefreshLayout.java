package com.lyb.besttimer.androidshare.view;

import android.content.Context;
import android.support.v4.widget.XSwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lyb.besttimer.androidshare.view.util.OnReadySimpleListener;
import com.lyb.besttimer.androidshare.view.util.TouchCombineController;


/**
 * Created by Administrator on 2016/7/14.
 */
public class BaseSwipeRefreshLayout extends XSwipeRefreshLayout {

    public BaseSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public BaseSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        touchCombineController = new TouchCombineController(this, new OnReadySimpleListener() {
            @Override
            public boolean onReadyUp(MotionEvent event) {
                return !onSuperTouchEvent(MotionEvent.obtainNoHistory(event));
            }

            @Override
            public boolean onReadyDown(MotionEvent event) {
                return !onSuperTouchEvent(MotionEvent.obtainNoHistory(event));
            }
        }, new OnReadySimpleListener() {
            @Override
            public boolean onReadyUp(MotionEvent event) {
                return !onSuperTouchEvent(MotionEvent.obtainNoHistory(event));
            }

            @Override
            public boolean onReadyDown(MotionEvent event) {
                return !onSuperTouchEvent(MotionEvent.obtainNoHistory(event));
            }
        });
    }


    private TouchCombineController touchCombineController;

    private boolean onSuperTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {


        if (touchCombineController.onTouchEvent(event)) {
            return false;
        }

        return super.onTouchEvent(event);
    }

}
