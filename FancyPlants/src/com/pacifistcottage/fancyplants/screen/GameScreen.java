package com.pacifistcottage.fancyplants.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.pacifistcottage.fancyplants.Art;
import com.pacifistcottage.fancyplants.FancyPlants;
import com.pacifistcottage.fancyplants.LevelData;
import com.pacifistcottage.fancyplants.World;
import com.pacifistcottage.fancyplants.engine.Input;
import com.pacifistcottage.fancyplants.engine.Screenflash;
import com.pacifistcottage.fancyplants.entity.Enemy;
import com.pacifistcottage.fancyplants.entity.Plant;
import com.pacifistcottage.fancyplants.entity.Star;

public class GameScreen extends FpScreen
{
	private static final float TITLE_DUR = 1.6f;
	private static final float BLINK_DUR = 0.55f;
		
	private boolean paused=false;
	private boolean showingTitle=false;
	private boolean titleFinished=false;
	private String titleStr1 = "";
	private String titleStr2 = "";
	private boolean blink=false;
	private float titleTimer=0;
	
	public GameScreen(FancyPlants game)
	{
		super(game);
	}
	
	@Override
	public void show()
	{
		if (!paused)
		{
			titleFinished=false;
			titleStr1 = "Level "+World.level;
			titleStr2 = LevelData.get(World.level-1).msg;
			titleTimer=0;
			fadeSpeed=4.15f;
			beginFadeIn();
		}
		else paused=false;
	}
	
	@Override
	public void beginFadeOut() 
	{ 
		fadeAlpha=0;
		fadeSpeed=0.35f;
		fadingIn=false;
		fadingOut=true; 
	}
	
	@Override
	public void onFadeInFinished()
	{
		fadingIn=false;
		showingTitle=true;
	}
	
	@Override
	public void onFadeOutFinished()
	{
		game.setScreen(game.endScreen);
	}
	
	public void update(float dt)
	{	
		Input.update();
		if (fadingIn || fadingOut)
		{
			updateFade(dt);
			return;
		}
		if (showingTitle)
		{
			titleTimer+=dt;
			if (titleFinished)
			{
				if (titleTimer>=BLINK_DUR)
				{
					titleTimer=0;
					blink=!blink;
				}
				if (Input.isPressed(Keys.ANY_KEY))
				{
					showingTitle=false;
					Input.lock(Keys.SPACE);
				}
			}
			else
			{
				if (titleTimer>=TITLE_DUR)
				{
					titleFinished=true;
					titleTimer=0;
					blink=true;
					Input.lockAll();
				}
			}
			return;
		}		
		player.update(dt);
		Plant.updateAll(dt);
		sign.update(dt);
		jgravy.update(dt);
		Enemy.updateAll(dt);
		Star.updateAll(dt);
		hud.update(dt);
		
		if (Screenflash.active)
			Screenflash.update(dt);
		
		if (Input.isPressed(Keys.ESCAPE))
		{
			game.setScreen(game.pauseScreen);
			paused=true;
		}
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
		jgravy.draw(sb);
		player.draw(sb);
		sb.end();
		
		if (sign.showing)
			sign.drawMessage(sb, shape);
		if (jgravy.showing)
			jgravy.drawMessage(sb, shape);
		
		hud.draw(sb, shape);
		
		if (Screenflash.active)
			Screenflash.draw(shape);
		
		if (showingTitle)
			showTitle();
		
		if (fadingOut || fadingIn)
			drawFade();
	}
	
	private void showTitle()
	{
		int rh = 180;
		Gdx.gl.glEnable(GL10.GL_BLEND);
		shape.begin(ShapeType.Filled);
		shape.setColor(0,0,0,.825f);
		shape.rect(0, game.h/2-(rh/2), 1280, rh);
		shape.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
		
		sb.begin();
		float tx = game.w/2;
		float ty = game.h/2;
		
		tb = Art.titleFont3.getBounds(titleStr1);
		tx -= tb.width/2;
		ty += tb.height/2 + 48;
		Art.titleFont3.draw(sb, titleStr1, (int)tx, (int)ty);
		
		Art.textFont1.setColor(Color.WHITE);
		tb = Art.textFont1.getBounds(titleStr2);
		tx = game.w/2 - tb.width/2;
		ty = game.h/2 + 10;
		Art.textFont1.draw(sb, titleStr2, (int)tx, (int)ty);
		
		if (titleFinished && blink)
		{
			Art.textFont1.setColor(Color.YELLOW);
			tb = Art.textFont1.getBounds(STR_ANYKEY);
			tx = game.w/2 - tb.width/2;
			ty = game.h/2 - 45;
			Art.textFont1.draw(sb, STR_ANYKEY, (int)tx, (int)ty);
		}
		sb.end();
	}
}
