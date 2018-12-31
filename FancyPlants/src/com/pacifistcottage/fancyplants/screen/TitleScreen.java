package com.pacifistcottage.fancyplants.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.pacifistcottage.fancyplants.Art;
import com.pacifistcottage.fancyplants.FancyPlants;
import com.pacifistcottage.fancyplants.engine.Input;

public class TitleScreen extends FpScreen
{	
	private final String titleStr = "Fancyplants";
	
	private boolean ready=false;
	private float delayDuration = 1.5f;
	private float delayTimer = 0;
	
	public TitleScreen(FancyPlants game)
	{
		super(game);
	}
	
	@Override
	public void show()
	{
		ready=false;
		delayTimer=0;
		fadingOut=false;
		Art.titleFont1.setColor(Color.WHITE);
		Art.titleFont2.setColor(Color.WHITE);
		Art.textFont1.setColor(Color.WHITE);
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
		
		if (!ready)
		{
			delayTimer+=dt;
			if (delayTimer>=delayDuration)
				ready=true;
		}
		
		if (ready && Input.isPressed(Keys.ANY_KEY))
		{
			fadeSpeed=2.0f;
			beginFadeOut();
		}
	}
	
	@Override
	public void render(float dt)
	{	
		update(dt);
		
		Gdx.gl.glClearColor(.09f, .115f, .09f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		cam.update();
		cam.apply(Gdx.gl10);
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		
		// draw left/right "frame vines"
		for (int i=0; i<12; ++i)
		{
			sb.draw(Art.vineV, -20, i*64, 0, 0, 32, 32, 2, 2, 0);
			sb.draw(Art.vineV, game.w-44, i*64, 0, 0, 32, 32, 2, 2, 0);
		}
		// draw top/bottom "frame vines"
		for (int i=0; i<22; ++i)
		{
			sb.draw(Art.vineH, i*64, 64, 0, 0, 32, 32, 2, 2, 180);
			sb.draw(Art.vineH, i*64, game.h-64, 0, 0, 32, 32, 2, 2, 0);
		}
		
		// draw the main title
		tb=Art.titleFont1.getBounds(titleStr);
		Art.titleFont1.draw(sb, titleStr, 
			game.w/2 - tb.width/2, 
			game.h/2 + tb.height/2 + 88);
		
		// draw the sub-title
		if (ready)
		{
			tb=Art.titleFont2.getBounds(STR_ANYKEY);
			Art.titleFont2.draw(sb, STR_ANYKEY, 
				game.w/2 - tb.width/2, 
				game.h/2 - tb.height*3 + 80);
		}
		
		// draw copyright string
		tb=Art.textFont1.getBounds(STR_COPYRIGHT);
		Art.textFont1.draw(sb, STR_COPYRIGHT, 
			game.w/2 - tb.width/2, 80);
		
		sb.end();
		
		if (fadingOut || fadingIn)
			drawFade();
	}
	
	@Override
	public void onFadeOutFinished()
	{
		world.loadLevel(1);
		game.setScreen(game.gameScreen);
	}
}
