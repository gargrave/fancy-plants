package com.pacifistcottage.fancyplants.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.pacifistcottage.fancyplants.FancyPlants;
import com.pacifistcottage.fancyplants.Sounds;
import com.pacifistcottage.fancyplants.engine.Input;
import com.pacifistcottage.fancyplants.entity.Plant;

public class FinalLevelScreen extends FpScreen
{
	
	public FinalLevelScreen(FancyPlants game)
	{
		super(game);
	}
	
	@Override
	public void show()
	{
		fadingOut=false;
	}
	
	@Override
	public void beginFadeOut() 
	{ 
		Sounds.playerDieFinal.play(.5f);
		fadeAlpha=0;
		fadeSpeed=0.215f;
		fadingIn=false;
		fadingOut=true; 
	}
	
	@Override
	public void onFadeOutFinished()
	{
		game.world.clearMap();
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
		
		player.update(dt);
		Plant.updateAll(dt);
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
		player.draw(sb);
		Plant.drawAll(sb);
		sb.end();
				
		if (fadingOut || fadingIn)
			drawFade();
	}
	
	@Override
	public void drawFade()
	{
		Gdx.gl.glEnable(GL10.GL_BLEND);
		shape.begin(ShapeType.Filled);
		shape.setColor(.8f, .05f, .05f, fadeAlpha);
		shape.rect(0, 0, 1280, 720);
		shape.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}
}
