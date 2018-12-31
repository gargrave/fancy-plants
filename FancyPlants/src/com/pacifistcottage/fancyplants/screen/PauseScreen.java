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

public class PauseScreen extends FpScreen
{
	private final String strCtlPause = "pause/unpause  :  escape";
	private final String strCtlReset = "reset level  :  r";
	private final String strCtlMove = "move left/right  :  a/d  or  left/right";
	private final String strCtlClimb = "climb  :  w  or  up";
	private final String strCtlHud = "select plant  :  q/e";
	private final String strCtlPlant = "use plant  :  spacebar";
	
	public PauseScreen(FancyPlants _game)
	{
		super(_game);
	}
	
	public void update(float dt)
	{	
		Input.update();
		if (Input.isPressed(Keys.ESCAPE))
			game.setScreen(game.gameScreen);
	}
	
	@Override
	public void show()
	{
		Art.titleFont1.setColor(Color.WHITE);
		Art.titleFont2.setColor(Color.WHITE);
		Art.textFont1.setColor(Color.WHITE);
	}
	
	@Override
	public void render(float dt)
	{	
		update(dt);
		
		Gdx.gl.glClearColor(game.SKY.r, game.SKY.g, game.SKY.b, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		cam.apply(Gdx.gl10);

		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		world.draw(sb);
		Plant.drawAll(sb);
		Star.drawAll(sb);
		Enemy.drawAll(sb);
		sign.draw(sb);
		player.draw(sb);
		sb.end();
		
		if (sign.showing)
			sign.drawMessage(sb, shape);
		
		hud.draw(sb, shape);
		
		Gdx.gl.glEnable(GL10.GL_BLEND);
		shape.begin(ShapeType.Filled);
		shape.setColor(0,0,0,.8f);
		shape.rect(0, 0, game.w, game.h);
		shape.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
		
		sb.begin();
		float tx = game.w/2;
		float ty = game.h/2;
		
		tb = Art.titleFont1.getBounds("Paused");
		tx -= tb.width/2;
		ty += tb.height/2 + 220;
		Art.titleFont1.draw(sb, "Paused", (int)tx, (int)ty);
		
		tb=Art.textFont1.getBounds(STR_DESIGN);
		Art.textFont1.draw(sb, STR_DESIGN, 
			game.w/2 - tb.width/2, 110);
		
		// draw copyright string
		tb=Art.textFont1.getBounds(STR_COPYRIGHT);
		Art.textFont1.draw(sb, STR_COPYRIGHT, 
			game.w/2 - tb.width/2, 80);
		
		int yb = 30;
		ty = game.h/2 + tb.height/2 + 75;
		tb = Art.textFont1.getBounds(strCtlPause);
		tx = game.w/2 - tb.width/2;
		Art.textFont1.draw(sb, strCtlPause, (int)tx, (int)ty);
		
		ty-=yb;
		tb = Art.textFont1.getBounds(strCtlReset);
		tx = game.w/2 - tb.width/2;
		Art.textFont1.draw(sb, strCtlReset, (int)tx, (int)ty);
		
		ty-=yb*2;
		tb = Art.textFont1.getBounds(strCtlMove);
		tx = game.w/2 - tb.width/2;
		Art.textFont1.draw(sb, strCtlMove, (int)tx, (int)ty);
		
		ty-=yb;
		tb = Art.textFont1.getBounds(strCtlClimb);
		tx = game.w/2 - tb.width/2;
		Art.textFont1.draw(sb, strCtlClimb, (int)tx, (int)ty);
		
		ty-=yb;
		tb = Art.textFont1.getBounds(strCtlHud);
		tx = game.w/2 - tb.width/2;
		Art.textFont1.draw(sb, strCtlHud, (int)tx, (int)ty);
		
		ty-=yb;
		tb = Art.textFont1.getBounds(strCtlPlant);
		tx = game.w/2 - tb.width/2;
		Art.textFont1.draw(sb, strCtlPlant, (int)tx, (int)ty);
		
		sb.end();
	}
}
