package com.android.study.spacegame;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.android.study.spacegame.framework.GameView;
import com.android.study.spacegame.framework.Renderable;
import com.android.study.spacegame.framework.Updatable;


/**
 * Это будет наш звездный фон
 */
class Stars implements Renderable, Updatable {

	GameView game;

	// Количество звёзд
	static final int STAR_NUM = 200;
	// Тут у нас будут храниться координаты и "яркость" звёзд
	ArrayList<Vector> coords = new ArrayList<Vector>(STAR_NUM);
	ArrayList<Integer> alpha = new ArrayList<Integer>(STAR_NUM);

	public Stars(GameView game) {
		this.game = game;
		// Сейчас мы сгенерируем звезды
		for (int i = 0; i < STAR_NUM; i++) {
			coords.add(
					new Vector((float) (Math.random() * game.getWidth()), (float) (Math.random() * game.getHeight())));
			alpha.add((int) (Math.random() * 256));
		}
	}

	Paint paint = new Paint();

	@Override
	public void render(Canvas canvas) {
		// Рисуем черное небо
		canvas.drawColor(Color.BLACK);
		// Рисуем звёзды
		paint.setColor(Color.WHITE);
		for (int i = 0; i < coords.size(); i++) {
			final Vector coord = coords.get(i);
			paint.setAlpha(alpha.get(i));
			canvas.drawPoint(coord.x, coord.y, paint);
		}
	}

	@Override
	public void update(float deltaTime) {
		// Применяем изменение alpha на все звёзды
		for (int i = 0; i < coords.size(); i++) {
			// Достаём данные
			int a = alpha.get(i);
			a += (int) (Math.random() * 51 - 25);
			// Если выходим за рамки возможной прозрачности  0..255, 
			// «возвращаем» новое значение в пределы.
			if (a > 255)
				a = 255;
			if (a < 0)
				a = 0;
			// Закладываем новые данные
			alpha.set(i, a);

		}
	}

	// Звезды рисуются подо всем.
	@Override
	public float zOrder() {
		return -1.0f;
	}
}