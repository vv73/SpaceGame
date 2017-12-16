package com.android.study.spacegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.android.study.spacegame.framework.Renderable;
import com.android.study.spacegame.framework.Touchable;
import com.android.study.spacegame.framework.Updatable;
import com.android.study.spacegame.framework.ZOrdered;

/**
 * Created by user on 09.12.2017.
 */

public class Ship implements Renderable, Updatable, Touchable, ZOrdered{
    RectF rect;
    Bitmap image;
    Paint paint;
    @Override
    public void update(float deltaTime) {

    }

    Ship(float x, float y, Bitmap image)
    {
        rect = new RectF(x - 50, y - 100,
                x + 50, y + 100);
        this.image = image;
        paint = new Paint();
    }

    @Override
    public float zOrder() {
        return 1000;
    }

    @Override
    public void render(Canvas canvas) {
         canvas.drawBitmap(image, null, rect, paint);
    }

    boolean move = false;

    @Override
    public boolean onScreenTouch(float touchX, float touchY, boolean justTouched) {
        if (justTouched){
           if (rect.contains(touchX, touchY)) {
               rect.set(touchX - 50, touchY - 100, touchX + 50, touchY + 100);
               move = true;
           }
           else
               move = false;
        }
        else{
            if (move)
                rect.set(touchX - 50, touchY - 100, touchX + 50, touchY + 100);
        }

        return false;
    }
}
