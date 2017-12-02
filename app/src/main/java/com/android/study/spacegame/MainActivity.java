package com.android.study.spacegame;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.android.study.spacegame.framework.GameView;
import com.android.study.spacegame.framework.Updatable;


public class MainActivity extends Activity  {

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
	
	// прошедшее время с последнего добавления



}
