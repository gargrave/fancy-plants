package com.pacifistcottage.fancyplants.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.pacifistcottage.fancyplants.Art;
import com.pacifistcottage.fancyplants.FancyPlants;
import com.pacifistcottage.fancyplants.Sounds;
import com.pacifistcottage.fancyplants.engine.Entity;

public class Sign extends Entity
{	
	private boolean wasShowing=false;
	public boolean showing=false;
	
	protected final int rw = 540, rh = 350;
	protected int rx;
	protected int ry;
	
	protected TextBounds tb;
	public String msg = "";
	
	
	/***********************************
	 * ctors
	 ***********************************/
	
	public Sign(FancyPlants game)
	{
		super(game);
		tr = Art.sign;
		size.x = tr.getRegionWidth();
		size.y = tr.getRegionHeight();
		rx = game.w/2 - rw/2;
		ry = game.h/2 - rh/2;
	}
	
	@Override
	public void update(float dt)
	{	
		if (!active) return;
		
		wasShowing=showing;
		showing = game.player.coll.bounds.overlaps(coll.bounds);
		
		if (wasShowing && !showing)
			Sounds.signHide.play(.85f);
		else if (!wasShowing && showing)
			Sounds.signShow.play(.85f);
	}
	
	public void drawMessage(SpriteBatch sb, ShapeRenderer shape)
	{
		// draw the text box's bg-rect
		Gdx.gl.glEnable(GL10.GL_BLEND);
		shape.begin(ShapeType.Filled);
		shape.setColor(.1f, .1f, .1f, .95f);
		shape.rect(rx-20,ry,rw+40,rh);
		shape.end();
		shape.begin(ShapeType.Line);
		shape.setColor(Color.WHITE);
		shape.rect(rx-20,ry,rw+40,rh);
		shape.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
		
		// draw the text
		sb.begin();
		tb = Art.textFont1.getMultiLineBounds(msg);
		Art.textFont1.setColor(Color.WHITE);
		Art.textFont1.drawWrapped(sb, msg, rx, 
			ry + rh/2 + tb.height/2, 
			rw, HAlignment.CENTER);
		sb.end();
	}
}
