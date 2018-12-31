package com.pacifistcottage.fancyplants.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.pacifistcottage.fancyplants.Art;
import com.pacifistcottage.fancyplants.FancyPlants;
import com.pacifistcottage.fancyplants.Sounds;
import com.pacifistcottage.fancyplants.engine.Anim;
import com.pacifistcottage.fancyplants.engine.Entity;
import com.pacifistcottage.fancyplants.entity.Plant.PlantType;

public class Enemy extends Entity
{	
	public Enemy(FancyPlants _game)
	{
		super(_game);
		TextureRegion[] frames = new TextureRegion[]
			{
				Art.enemy[0][0],
				Art.enemy[0][1]
			};
		tr = frames[0];
		size.x = tr.getRegionWidth();
		size.y = tr.getRegionHeight();
		anim = new Anim(frames, .18f);
		speed = 62;
	}
	
	@Override
	public void update(float dt)
	{
		if (anim!=null)
			anim.update(dt);
		
		velAdj.x = vel.x * speed * dt;
		coll.update(dt);
		collX.update(dt);
		
		// check for collisions with chomp-plants
		if (Plant.getColl(coll.bounds, PlantType.CHOMP)!=null)
		{
			Sounds.enemyDie.play();
			deactivate();
			return;
		}
		
		// check for collisions with stars
		Rectangle cx = Star.getColl(collX.bounds);
		// check for collisions with walls
		if (cx==null)
			cx = game.world.getColl(collX.bounds);
		if (cx!=null)
		{
			if (vel.x>0)
				pos.x = cx.x - size.x;
			else if (vel.x<0)
				pos.x = cx.x + cx.width;
			turnAround();
		}
		// check if the enemy is about to walk off a cliff
		else
		{
			int tx=(int)((pos.x - pos.x%32)/32);
			if (vel.x==1)
				tx+=1;
			int ty=(int)((pos.y - pos.y%32)/32)-1;
			if (!game.world.isSolidAt(tx, ty))
				turnAround();
		}
		pos.x += velAdj.x;
	}
	
	private void turnAround()
	{
		if (vel.x==1)
		{
			vel.x = -1;
			if (!flipped)
				flipped=true;
		}
		else if (vel.x==-1)
		{
			vel.x = 1;
			if (flipped)
				flipped=false;
		}
	}
	
	
	/***********************************
	 * static members
	 ***********************************/
	
	private static List<Enemy> enemies = new ArrayList<Enemy>();
	
	public static void init(FancyPlants game)
	{
		for (int i=0; i<10; ++i)
			enemies.add(new Enemy(game));
	}
	
	public static Enemy getInactive()
	{
		for (Enemy e : enemies)
			if (!e.active)
				return e;
		
		// if we are still here, create a new Enemy!
		Enemy e = new Enemy(game);
		enemies.add(e);
		return e;
	}
	
	public static void setEnemy(int x, int y)
	{
		Enemy e = getInactive();
		e.setPosByGrid(x, y);
		e.vel.x=1;
		e.activate();
	}
	
	public static void clear()
	{
		for (Enemy e : enemies)
			e.deactivate();
	}
	
	public static void updateAll(float dt)
	{
		for (Enemy e : enemies)
			if (e.active)
				e.update(dt);
	}
	
	public static void drawAll(SpriteBatch sb)
	{
		for (Enemy e : enemies)
			if (e.active)
				e.draw(sb);
	}
	
	public static boolean collides(Rectangle bounds)
	{
		for (Enemy e : enemies)
			if (e.active && e.coll.bounds.overlaps(bounds))
				return true;
		return false;
	}
}
