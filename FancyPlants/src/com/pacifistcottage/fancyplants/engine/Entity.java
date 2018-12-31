package com.pacifistcottage.fancyplants.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.pacifistcottage.fancyplants.FancyPlants;
import com.pacifistcottage.fancyplants.engine.Collider.CollType;

public abstract class Entity
{
	/***********************************
	 * static members
	 ***********************************/
	
	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	public static final int GRAVITY = 1700;
	public static final int TERMINAL = -1350;
	protected static FancyPlants game;
	
	
	/***********************************
	 * display fields
	 ***********************************/
	
	protected Anim anim;
	protected TextureRegion tr;
	protected boolean flipped=false;
	public boolean active=false;
	
	
	/***********************************
	 * position/movement fields
	 ***********************************/
	
	public int facing = RIGHT;
	public float speed = 100;
	public final Vector2 size = new Vector2();
	public final Vector2 pos = new Vector2();
	public final Vector2 vel = new Vector2();
	public final Vector2 velAdj = new Vector2();
	
	
	/***********************************
	 * collider instances
	 ***********************************/
	
	public final Collider coll = new Collider(this, CollType.STATIC);
	public final Collider collX = new Collider(this, CollType.DYNX);
	public final Collider collY = new Collider(this, CollType.DYNY);
	
	
	/***********************************
	 * ctors
	 ***********************************/
	
	public Entity(FancyPlants _game)
	{
		if (game==null)
			game=_game;
	}
	
	
	/***********************************
	 * update methods
	 ***********************************/
	
	public void update(float dt)
	{
		if (!active) return;
		
		if (anim!=null)
			anim.update(dt);
		
		velAdj.x = vel.x * speed * dt;
		velAdj.y = vel.y * dt;
		
		coll.update(dt);
		collX.update(dt);
		collY.update(dt);
		
		pos.x += velAdj.x;
		pos.y += velAdj.y;		
	}
	
	public void draw(SpriteBatch sb)
	{		
		if (!active) return;
		
		if (anim!=null)
			tr=anim.frame();
		
		if (flipped && !tr.isFlipX())
			tr.flip(true, false);
		else if (!flipped && tr.isFlipX())
			tr.flip(true, false);
		sb.draw(tr, pos.x, pos.y);
//		coll.draw(sb);
//		collX.draw(sb);
//		collY.draw(sb);
	}
	
	
	/***********************************
	 * setters
	 ***********************************/
	
	public void setPosByGrid(int x, int y)
	{
		pos.x = x*game.tileSize;
		pos.y = y*game.tileSize;
		flipped=false;
		facing=RIGHT;
		coll.update(0);
	}
	
	public void activate()
	{
		active=true;
	}
	
	public void deactivate()
	{
		active=false;
		flipped=false;
	}
	
	public void reset(){}
}
