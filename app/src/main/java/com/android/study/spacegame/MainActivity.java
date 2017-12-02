package com.android.study.spacegame;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.android.study.spacegame.framework.GameView;
import com.android.study.spacegame.framework.Updatable;


public class MainActivity extends Activity  implements Updatable{

	GameView game;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Cоздаем GameView
		game = new GameView(this);
		// Устанавливаем наш GameView на активность
		setContentView(game);
	}
	
	@Override
	public void onWindowFocusChanged (boolean hasFocus) {
		//добавляем себя в игру
		game.addObject(this);
		game.addObject(new Stars(game));
}

    float lastTime = 0;
	@Override
	public void update(float deltaTime) {
		lastTime += deltaTime;
		if (lastTime > 3) {
			game.addObject(
					new Asteroid(
							BitmapFactory.decodeResource(getResources(),
									R.drawable.asteroid1), game));
			lastTime = 0;
		}
	}

	// прошедшее время с последнего добавления



}
