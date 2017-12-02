package com.android.study.spacegame;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.android.study.spacegame.framework.GameView;
import com.android.study.spacegame.framework.Renderable;
import com.android.study.spacegame.framework.Updatable;


/**
 * Всякие взрывы!
 */
public class Explosion implements Updatable, Renderable {

	GameView game;

	// Сколько у нас частиц во взрыве
	static final int PARTICLE_NUM = 10;
	// Картинка частицы
	private final Bitmap particle;
	// Текущий диаметр частицы
	private float diameter;
	// Начальный диаметр частицы
	private float start_diameter;
	// Скорости отдельных частиц.
	private ArrayList<Vector> speeds = new ArrayList<Vector>(PARTICLE_NUM);
	// Центр взрыва
	private Vector center;
	// zOrder взрыва
	private final float z;

	public Explosion(Bitmap particle, Asteroid from, GameView game) {

		this.game = game;

		this.particle = particle;

		// Копируем данные о положении взрыва из астероида
		center = new Vector().set(from.coord);
		start_diameter = diameter = from.diameter;
		z = from.zOrder();

		// Генерируем взрыв
		for (int i = 0; i < PARTICLE_NUM; i++) {
			final Vector vector = new Vector((float) Math.random(), (float) Math.random()).sub(0.5f, 0.5f).norm()
					.scale((float) Math.random());
			speeds.add(vector);
		}
	}

	Vector tmp = new Vector();
	private Rect dst = new Rect();
	Paint paint = new Paint();

	@Override
	public void render(Canvas canvas) {
		// Расстояние от эпицентра взрыва. Да, мы не используеми отдельных
		// переменных
		// для интерполяции, просто считаем по разнице начального диаметра и
		// текущего.
		// Так не приходится сохранять положение каждой частицы, да
		// контролировать размер
		// взрыва легче.
		float distance = (start_diameter - diameter) * 1.5f;
		// Тут всё довольно просто, и рисуется всё так же, как и в астероиде.
		
		for (int i = 0; i < PARTICLE_NUM; i++) {
			Vector speed = speeds.get(i);
			// Единственное отличие - поворот вычисляется на основе вектора, 
			// это простейшая тригонометрия.
			float rotation = (float) Math.toDegrees(Math.atan2(speed.y, speed.x));

			tmp.set(speed).scale(distance).add(center);
			dst.set(0, 0, (int) diameter, (int) diameter);
			dst.offset((int) tmp.x, (int) tmp.y);
			dst.offset((int) -diameter / 2, (int) -diameter / 2);

			paint.setColor(Color.WHITE);
			paint.setAlpha((int) (255 * (diameter / start_diameter)));
			canvas.save();
			canvas.rotate(rotation, tmp.x, tmp.y);
			canvas.drawBitmap(particle, null, dst, paint);
			canvas.restore();
		}
	}

	@Override
	public void update(float deltaTime) {
		// Просто уменьшаем диаметр, а после того,
		// как он становится меньше 1 - совершаем суицид.
		diameter *= 1 - deltaTime;
		if (diameter <= 1)
			game.removeObject(this);
	}

	// удаленность взрыва совпадает с удаленностью уничтоженного астероида
	@Override
	public float zOrder() {
		return z;
	}
}