package com.pacifistcottage.fancyplants.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Screenflash
{	
	private static float initAlpha = .535f;
	private static float speed = 2.25f;
	private static Color color = new Color(.9f, .9f, .9f, initAlpha);
	
	public static boolean active=false;
	
	public static void begin()
	{
		color.a=initAlpha;
		active=true;
	}
	
	public static void update(float dt)
	{
		color.a -= speed*dt;
		if (color.a<=speed*dt)
			active=false;
	}
	
	public static void draw(ShapeRenderer shape)
	{
		Gdx.gl.glEnable(GL10.GL_BLEND);
		shape.setColor(color);
		shape.begin(ShapeType.Filled);
		shape.rect(0, 0, 1280, 720);
		shape.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}
}
