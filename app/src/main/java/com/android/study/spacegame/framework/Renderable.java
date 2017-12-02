package com.android.study.spacegame.framework;


import android.graphics.Canvas;

/**
 * То, что мы можем нарисовать
 */
public interface Renderable extends ZOrdered{
    /**
     * Рисует объект на данной канве.
     */
    void render(Canvas canvas);
    
}
