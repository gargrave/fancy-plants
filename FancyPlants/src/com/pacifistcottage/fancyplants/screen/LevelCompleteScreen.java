package com.pacifistcottage.fancyplants.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.pacifistcottage.fancyplants.Art;
import com.pacifistcottage.fancyplants.FancyPlants;
import com.pacifistcottage.fancyplants.engine.Input;
import com.pacifistcottage.fancyplants.entity.Enemy;
import com.pacifistcottage.fancyplants.entity.Plant;
import com.pacifistcottage.fancyplants.entity.Star;

public class LevelCompleteScreen extends FpScreen
{	
	private final String str1 = "Level complete!";
	private final String str2 = "Press any key to continue";
	
	public LevelCompleteScreen(FancyPlants game)
	{
		super(game);
	}
	
	@Override
	public void show()
	{
		Plant.stopSounds();
		fadingOut=false;
		Input.lockAll();
	}
	
	public void update(float dt)
	{	
		Input.update();
		if (fadingIn || fadingOut)
		{
			updateFade(dt);
			return;
		}
		
		if (Input.isPressed(Keys.ANY_KEY))
		{
			fadeSpeed=3.85f;
			beginFadeOut();
		}
	}
	
	@Override
	public void render(float dt)
	{	
		update(dt);
		
		// draw the world
		Gdx.gl.glClearColor(game.SKY.r, game.SKY.g, game.SKY.b, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		cam.update();
		cam.apply(Gdx.gl10);
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.setColor(Color.WHITE);
		world.draw(sb);
		Plant.drawAll(sb);
		Star.drawAll(sb);
		sign.draw(sb);
		Enemy.drawAll(sb);
		player.draw(sb);
		sb.end();
		hud.draw(sb, shape);
		
		// draw the overlay
		Gdx.gl.glEnable(GL10.GL_BLEND);
		shape.begin(ShapeType.Filled);
		shape.setColor(.05f, .05f, .05f, .75f);
		shape.rect(0, 0, 1280, 720);
		shape.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
		
		// draw the text
		sb.begin();
		tb = Art.titleFont1.getBounds(str1);
		float tx = game.w/2 - tb.width/2;
		float ty = game.h/2 + tb.height/2 + 130;
		Art.titleFont1.draw(sb, str1, (int)tx, (int)ty);
		
		tb = Art.titleFont2.getBounds(str2);
		tx = game.w/2 - tb.width/2;
		ty = game.h/2 + tb.height/2 + 40;
		Art.titleFont2.draw(sb, str2, (int)tx, (int)ty);
		sb.end();
		
		if (fadingOut || fadingIn)
			drawFade();
	}
	
	@Override
	public void onFadeOutFinished()
	{
		if (world.loadNextLevel())
			game.setScreen(game.gameScreen);
	}
}
