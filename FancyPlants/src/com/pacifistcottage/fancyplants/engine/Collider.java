package com.pacifistcottage.fancyplants.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Collider
{
	public enum CollType { STATIC, DYNX, DYNY }
	
	protected static ShapeRenderer shape;
	
	public final Entity owner;
	public final CollType type;
	public final Rectangle bounds = new Rectangle();
	
	public Collider(Entity owner, CollType type)
	{
		this.owner = owner;
		this.type = type;
		
		if (shape==null)
			shape = new ShapeRenderer();
	}
	
	public void update(float dt)
	{
		if (type==CollType.STATIC)
			bounds.set(owner.pos.x+1, owner.pos.y+1,
				owner.size.x-2, owner.size.y-2);
		
		else if (type==CollType.DYNX)
		{
			if (owner.velAdj.x>=0)
				bounds.set(
					owner.pos.x + owner.size.x,
					owner.pos.y+2,
					owner.velAdj.x,
					owner.size.y-4);
			else if (owner.velAdj.x<0)
				bounds.set(
					owner.pos.x + owner.velAdj.x,
					owner.pos.y+2,
					-owner.velAdj.x,
					owner.size.y-4);
		}
		
		else if (type==CollType.DYNY)
		{
			if (owner.velAdj.y>0)
				bounds.set(
					owner.pos.x+2,
					owner.pos.y+owner.size.y,
					owner.size.x-4,
					owner.velAdj.y);
			else if (owner.velAdj.y<=0)
				bounds.set(
					owner.pos.x+2,
					owner.pos.y + owner.velAdj.y,
					owner.size.x-4,
					-owner.velAdj.y);
		}
	}
	
	public void draw(SpriteBatch sb)
	{
		sb.end();
		shape.begin(ShapeType.Line);
		
		if (type==CollType.STATIC)
			shape.setColor(Color.RED);
		else if (type==CollType.DYNX)
			shape.setColor(Color.MAGENTA);
		else if (type==CollType.DYNY)
			shape.setColor(Color.GREEN);
		
		shape.rect(bounds.x,bounds.y,bounds.width,bounds.height);
		shape.end();
		sb.begin();
	}
}
