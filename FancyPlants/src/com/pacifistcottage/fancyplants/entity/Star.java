package com.pacifistcottage.fancyplants.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pacifistcottage.fancyplants.Art;
import com.pacifistcottage.fancyplants.FancyPlants;
import com.pacifistcottage.fancyplants.Sounds;
import com.pacifistcottage.fancyplants.engine.Entity;

public class Star extends Entity
{	
	public Star(FancyPlants game)
	{
		super(game);
		size.x = Art.star.getRegionWidth();
		size.y = Art.star.getRegionHeight();
	}
	
	public void update(float dt)
	{
		coll.update(dt);
		
		if (coll.bounds.overlaps(game.player.coll.bounds))
			collect();		
	}
	
	public void collect()
	{
		active=false;
		if (++starsCollected==starsInLevel)
			game.setScreen(game.levelCompleteScreen);
		Sounds.starPickup.play();
	}
	
	
	/***********************************
	 * static members
	 ***********************************/
		
	public static List<Star> stars = new ArrayList<Star>();
	public static int starsCollected=0;
	public static int starsInLevel=0;
	
	public static void init(FancyPlants game)
	{
		for (int i=0; i<10; ++i)
			stars.add(new Star(game));
	}
	
	public static Star getInactiveStar()
	{
		for (Star p : stars)
			if (!p.active)
				return p;
		
		// if we are still here, create a new Plant!
		Star p = new Star(game);
		stars.add(p);
		return p;
	}
	
	public static void set(int _x, int _y)
	{
		Star s = getInactiveStar();
		s.pos.x=_x;
		s.pos.y=_y;
		s.active=true;
		++starsInLevel;
	}
	
	public static void clear()
	{
		starsCollected=0;
		starsInLevel=0;
		for (Star s : stars)
			s.active=false;
	}
	
	public static void updateAll(float dt)
	{
		for (Star s : stars)
			if (s.active)
				s.update(dt);
	}
	
	public static void drawAll(SpriteBatch sb)
	{
		for (Star s : stars)
			if (s.active)
				sb.draw(Art.star, 
					s.pos.x, s.pos.y);
	}
	
	public static Rectangle getColl(Rectangle bounds)
	{
		for (Star s : stars)
			if (s.coll.bounds.overlaps(bounds))
				return s.coll.bounds;
		return null;
	}
}
