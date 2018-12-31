package com.pacifistcottage.fancyplants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.pacifistcottage.fancyplants.engine.Input;
import com.pacifistcottage.fancyplants.entity.Plant;

public class Hud
{
	// the starting x-pos for drawing items
	private final int startX = 590;
	// x/y offset for the display of item amounts
	private final int countX=23, countY=17;
	
	// the currently-selected item
	private int selected=0;
	// the list of coordinates for drawing items
	private final int[][] itemPos;
	
	
	/***********************************
	 * initialization
	 ***********************************/
	
	public Hud()
	{
		itemPos = new int[][]
		{
			new int[] {startX-16, 4},
			new int[] {startX+34, 4},
			new int[] {startX+84, 4},
		};
	}
	
	public void reset()
	{
		selected=0;
	}
	
	
	/***********************************
	 * update methods
	 ***********************************/
	
	public void update(float dt)
	{
		if (Input.isPressed(Keys.Q))
		{
			if (--selected<0)
				selected=itemPos.length-1;
		}
		else if (Input.isPressed(Keys.E))
		{
			if (++selected>itemPos.length-1)
				selected=0;
		}
	}
	
	public void draw(SpriteBatch sb, ShapeRenderer shape)
	{
		//draw HUD background
		Gdx.gl.glEnable(GL10.GL_BLEND);
		shape.setColor(Color.WHITE);
		shape.begin(ShapeType.Filled);
		shape.setColor(0,0,0,.65f);
		int rw=164, rh = 46;
		shape.rect(640-rw/2,0,rw,rh);
		
		// draw item backgrounds
		int r=34;
		shape.rect(itemPos[0][0], itemPos[0][1], r,r);
		shape.rect(itemPos[1][0], itemPos[1][1], r,r);
		shape.rect(itemPos[2][0], itemPos[2][1], r,r);
		shape.end();
		
		// draw white frames around the backgrounds
		shape.begin(ShapeType.Line);
		shape.setColor(.8f,.8f,.8f,.85f);
		shape.rect(itemPos[0][0], itemPos[0][1], r,r);
		shape.rect(itemPos[1][0], itemPos[1][1], r,r);
		shape.rect(itemPos[2][0], itemPos[2][1], r,r);
		shape.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
		
		// draw item sprites and counts
		Art.textFont2.setColor(Color.WHITE);
		sb.begin();
		if (Plant.numVineV>0)
		{
			sb.draw(Art.vineV, itemPos[0][0]+1, itemPos[1][1]+1);
			Art.textFont2.draw(sb, ""+Plant.numVineV, itemPos[0][0]+countX, 
				itemPos[1][1]+countY);
		}
			
		if (Plant.numVineH>0)
		{
			sb.draw(Art.vineH, itemPos[1][0]+1, itemPos[1][1]+1);
			Art.textFont2.draw(sb, ""+Plant.numVineH, itemPos[1][0]+countX, 
				itemPos[1][1]+countY);
		}
		
		if (Plant.numChomp>0)
		{
			sb.draw(Art.chomp[0], itemPos[2][0]+1, itemPos[2][1]+1);
			Art.textFont2.draw(sb, ""+Plant.numChomp, itemPos[2][0]+countX, 
				itemPos[1][1]+countY);
		}
		sb.end();
		
		// draw overlay on selected item
		Gdx.gl.glEnable(GL10.GL_BLEND);
		shape.begin(ShapeType.Filled);
		shape.setColor(1,1,1,.25f);
		shape.rect(itemPos[selected][0], itemPos[1][1], r,r);
		shape.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}
	
	public int getSelected() { return selected; }
}
