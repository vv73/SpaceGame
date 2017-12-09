package com.android.study.spacegame;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

import com.android.study.spacegame.framework.GameView;
import com.android.study.spacegame.framework.Updatable;


public class MainActivity extends Activity  implements Updatable{

	GameView game;
	Bitmap asteroidBmp, shipBmp;
	static TextView scoreTV;
	static int score = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		asteroidBmp = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1);
		shipBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ship);
		// Cоздаем GameView
		//game = new GameView(this);
		// Устанавливаем наш GameView на активность
		setContentView(R.layout.activity_main);
		game = (GameView)findViewById(R.id.game);
		scoreTV = (TextView) findViewById(R.id.score);
	}

	static void changeScore(int s){
		score += s;
		scoreTV.setText(score + "");
	}
	
	@Override
	public void onWindowFocusChanged (boolean hasFocus) {
		//добавляем себя в игру
		game.addObject(this);
		game.addObject(new Stars(game));
		game.addObject(new Ship(game.getWidth()/2, game.getHeight()/2, shipBmp));
}

    float lastTime = 0;
	@Override
	public void update(float deltaTime) {
		lastTime += deltaTime;
		if (lastTime > 3) {
			game.addObject(
					new Asteroid(asteroidBmp, game));
			lastTime = 0;
		}
	}
}
