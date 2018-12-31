package com.pacifistcottage.fancyplants.engine;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Anim
{
	private TextureRegion[] frames;
	
	private float duration = .5f;
	private float timer = 0;
	private int currentFrame = 0;
	
	public Anim(TextureRegion[] frames, float duration)
	{
		this.frames = frames;
		this.duration = duration;
	}
	
	public void update(float dt)
	{
		timer += dt;
		if (timer>=duration)
		{
			timer=0;
			if (++currentFrame>frames.length-1)
				currentFrame=0;
		}
	}
	
	public TextureRegion frame() { return frames[currentFrame]; }
}
