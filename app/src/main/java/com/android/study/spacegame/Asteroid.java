package com.android.study.spacegame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.android.study.spacegame.framework.GameView;
import com.android.study.spacegame.framework.Renderable;
import com.android.study.spacegame.framework.Touchable;
import com.android.study.spacegame.framework.Updatable;

/**
 * Это наши астероиды.
 */
class Asteroid implements Updatable, Renderable, Touchable {

	@Override
	public float zOrder() {
		// Положение астероида у нас зависит от его размера
		return diameter;
	}

	GameView game;

	// Текущая координата
	Vector coord;
	Vector velocity;

	// Поворот в градусах
	float spin = 0;
	// Скорость поворота
	float spin_speed = (float) (Math.random() * 100 - 50);
	// Размер астероида в пикселях
	float diameter = 1;
	// Нужная картинка с астероидом
	Bitmap image;

	public Asteroid(Bitmap image, GameView game) {

		// super(image, game);
		this.game = game;
		this.image = image;

		Vector center = new Vector((float) game.getWidth() / 2, game.getHeight() / 2);

		// Генерируем случайный вектор начала движения
		coord = new Vector((float) (Math.random()), (float) (Math.random())).scale(game.getWidth(), game.getHeight());

		// Генерируем вектор скорости (разлет от центра экрана)
		velocity = new Vector(coord.x, coord.y).sub(center)
				// Нормализуем его
				.norm();
	}

	// Старайтесь создавать как можно меньше объектов в часто выполняемых
	// операциях, таких как render. Это быстро заполняет память.
	RectF dst = new RectF();

	Paint paint = new Paint();

	@Override
	public void render(Canvas canvas) {
		// Выставляем размер астероида в нужный нам
		dst.set(0, 0, diameter, diameter);
		dst.offset((int) coord.x, (int) coord.y);
		// И ещё немного сдвигаем, чтобы координата астероида
		// указывала на его центр
		dst.offset((int) (-diameter / 2), (int) (-diameter / 2));

		// Обнуляем цвет
		paint.setColor(Color.WHITE);
		// Теперь повернём наш астероид.
		// Для этого надо:
		// Cохранить текущее состояние канвы
		canvas.save();
		// Повернуть канву на нужный нам угол
		canvas.rotate(spin, coord.x, coord.y);
		// Нарисовать астероид
		canvas.drawBitmap(image, null, dst, paint);
		// И вернуть канву в начальное положение.
		canvas.restore();

		// Мы повернули Вселенную вокруг астероида, чтобы повернуть его :)
	}

	@Override
	public boolean onScreenTouch(float touchX, float touchY, boolean justTouched) {
		// просто удаляем его, если по нему нажали только что,
		// Для определения расположения используем dst - квадрат,
		// в который был нарисован астероид в последнем обновлении.
		if (justTouched && dst.contains(touchX, touchY)) {
			game.removeObject(this);
			// и добавляем взрыв
			game.addObject(new Explosion(BitmapFactory.decodeResource(game.getResources(), R.drawable.explosion2), this,
					game));
			return true;
		}
		return false;
	}

	Vector tmpVector = new Vector();

	@Override
	public void update(float deltaTime) {
		// Обновим значение поворота
		spin += deltaTime * spin_speed;
		
		// Изменяем положение астероида
		// чем ближе, тем быстрее
		velocity = velocity.scale((float) (1 + 0.05 * deltaTime));
		// Мы будем увеличивать диаметр астероида, как будто он приближается
	    diameter += (float) (Math.sqrt(velocity.dst2(new Vector(0, 0)))) * deltaTime * 20;
		coord.add(tmpVector.set(velocity).scale((float)(deltaTime * 20)));

		if (diameter > game.getHeight() / 2)
			game.addObject(new Explosion(BitmapFactory.decodeResource(game.getResources(), R.drawable.explosion1), this,
					game));
		else if (!dst.intersect(new RectF(0, 0, game.getWidth(), game.getHeight())))
			game.removeObject(this);
	}

}
