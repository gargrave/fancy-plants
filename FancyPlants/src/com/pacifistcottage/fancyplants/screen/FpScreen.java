package com.pacifistcottage.fancyplants.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.pacifistcottage.fancyplants.FancyPlants;
import com.pacifistcottage.fancyplants.Hud;
import com.pacifistcottage.fancyplants.World;
import com.pacifistcottage.fancyplants.entity.Player;
import com.pacifistcottage.fancyplants.entity.Sign;

public abstract class FpScreen implements Screen
{
	protected static final String STR_DESIGN = 
		"Designed and developed by Gabe Hargrave";
	protected static final String STR_COPYRIGHT = 
		"Copyright 2013 Pacifist Cottage Games";
	protected static final String STR_ANYKEY = "Press any key.";
	
	protected static FancyPlants game;
	
	protected static OrthographicCamera cam;
	protected static SpriteBatch sb = new SpriteBatch();
	protected static ShapeRenderer shape = new ShapeRenderer();
	protected static TextBounds tb;
	
	protected static Hud hud;
	protected static World world;
	protected static Player player;
	protected static Sign sign;
	protected static Sign jgravy;
	
	protected float fadeAlpha=1;
	protected float fadeSpeed=2.25f;
	protected boolean fadingIn=false, fadingOut=false;
	
	public FpScreen(FancyPlants _game)
	{
		if (game==null)
			game=_game;
	}
	
	public void beginFadeIn() 
	{
		fadeAlpha=1;
		fadingIn=true;
		fadingOut=false;
	}
	
	public void beginFadeOut() 
	{ 
		fadeAlpha=0;
		fadingIn=false;
		fadingOut=true; 
	}
	
	public void onFadeInFinished(){}
	public void onFadeOutFinished(){}
	
	public void updateFade(float dt)
	{
		if (fadingIn)
		{
			fadeAlpha -= fadeSpeed*dt;
			if (fadeAlpha<=0)
			{
				fadeAlpha=0;
				onFadeInFinished();
			}
		}
		else if (fadingOut)
		{
			fadeAlpha += fadeSpeed*dt;
			if (fadeAlpha>=1)
			{
				fadeAlpha=1;
				onFadeOutFinished();
			}
		}
	}
	
	public void drawFade()
	{
		Gdx.gl.glEnable(GL10.GL_BLEND);
		shape.begin(ShapeType.Filled);
		shape.setColor(0,0,0,fadeAlpha);
		shape.rect(0, 0, 1280, 720);
		shape.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}
	
	public void update(float dt){}
	@Override
	public void render(float delta){}
	@Override
	public void resize(int width, int height){}
	@Override
	public void show(){}
	@Override
	public void hide(){}
	@Override
	public void pause(){}
	@Override
	public void resume(){}
	@Override
	public void dispose(){}
}
