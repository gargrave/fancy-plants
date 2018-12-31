package com.pacifistcottage.fancyplants.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.pacifistcottage.fancyplants.Art;
import com.pacifistcottage.fancyplants.FancyPlants;
import com.pacifistcottage.fancyplants.Hud;
import com.pacifistcottage.fancyplants.Sounds;
import com.pacifistcottage.fancyplants.Tile;
import com.pacifistcottage.fancyplants.World;
import com.pacifistcottage.fancyplants.entity.Enemy;
import com.pacifistcottage.fancyplants.entity.Jgravy;
import com.pacifistcottage.fancyplants.entity.Plant;
import com.pacifistcottage.fancyplants.entity.Player;
import com.pacifistcottage.fancyplants.entity.Sign;
import com.pacifistcottage.fancyplants.entity.Star;

public class InitScreen extends FpScreen
{
	public InitScreen(FancyPlants game)
	{
		super(game);
	}
	
	public void update(float dt)
	{
		if (cam==null)
		{
			cam = new OrthographicCamera();
			cam.setToOrtho(false, 1280, 720);
			hud = new Hud();
			
			Art.load();
			Sounds.load();
			
			Tile.init(game);
			Enemy.init(game);
			Plant.init(game);
			Star.init(game);
			player = new Player(game);	
			player.activate();
			sign = new Sign(game);
			sign.deactivate();
			jgravy = new Jgravy(game);
			jgravy.deactivate();
			
			game.hud=hud;
			game.player=player;
			game.sign=sign;
			game.jgravy=jgravy;
			
			world = new World(game);
			game.world=world;
//			game.setScreen(game.titleScreen);
			world.loadLevel(World.level);
			game.setScreen(game.gameScreen);
		}
	}
	
	public void render(float dt)
	{
		update(dt);
		
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void dispose()
	{
		sb.dispose();
		shape.dispose();
	}
}
