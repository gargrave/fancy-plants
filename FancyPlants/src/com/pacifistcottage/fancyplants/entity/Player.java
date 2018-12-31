package com.pacifistcottage.fancyplants.entity;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.pacifistcottage.fancyplants.Art;
import com.pacifistcottage.fancyplants.FancyPlants;
import com.pacifistcottage.fancyplants.Sounds;
import com.pacifistcottage.fancyplants.engine.Anim;
import com.pacifistcottage.fancyplants.engine.Entity;
import com.pacifistcottage.fancyplants.engine.Input;
import com.pacifistcottage.fancyplants.engine.Screenflash;
import com.pacifistcottage.fancyplants.entity.Plant.PlantType;

public class Player extends Entity
{
	private final int climbSpeed=110;
	
	private Anim animIdle;
	private Anim animRun;
	private Anim animFall;
	private Anim animClimb;
	
	private boolean climbing=false;
	private boolean inAir=false;
	
	// sound-tracking members
	private boolean stepSoundA=true;
	private float stepDuration=.125f;
	private float stepTimer=0;
	
	private boolean climbSoundA=true;
	private float climbDuration=.18f;
	private float climbTimer=0;
	
	public Player(FancyPlants game)
	{
		super(game);
		animIdle = new Anim(new TextureRegion[] {Art.player2[0][0]}, 0);
		animFall = new Anim(new TextureRegion[] {Art.player2[0][3]}, 0);
		
		TextureRegion[] frames = new TextureRegion[]
		{
			Art.player2[0][1],
			Art.player2[0][2]
		};
		tr = frames[0];
		animRun = new Anim(frames, .125f);
		
		frames = new TextureRegion[]
		{
			Art.player2[0][4],
			Art.player2[0][5]
		};
		tr = frames[0];
		animClimb = new Anim(frames, .18f);
			
		anim=animIdle;
		
		size.x = tr.getRegionWidth();
		size.y = tr.getRegionHeight();
		speed = 235;
		facing=RIGHT;
	}
	
	@Override
	public void reset()
	{
		flipped=false;
		facing=RIGHT;
		anim=animIdle;
		vel.x=0;
		vel.y=0;
		velAdj.x=0;
		velAdj.y=0;
		coll.update(0);
		collX.update(0);
		collY.update(0);
		climbing=false;
		inAir=false;
	}
	
	@Override
	public void update(float dt)
	{
		if (Input.isPressed(Keys.R))
		{
			game.world.reset();
			Screenflash.begin();
			Sounds.worldReset.play();
			return;
		}
		
		if (anim!=null)
		{
			anim.update(dt);
			tr = anim.frame();
		}
		
		checkMovementCtl();
		checkPlantCtl();
		
		velAdj.x = vel.x * speed * dt;
		
		if (!climbing)
			vel.y -= GRAVITY*dt;
		else vel.y = climbSpeed;
		if (vel.y<=TERMINAL)
			vel.y=TERMINAL;
		velAdj.y = vel.y*dt;
		
		coll.update(dt);
		collX.update(dt);
		collY.update(dt);
		
		checkCollEnemy();
		checkCollX();
		checkCollY();
		
		updateSounds(dt);
		updateAnimations();
		
		pos.x += velAdj.x;
		pos.y += velAdj.y;
		
		if (pos.y+size.y<-1)
			game.world.loadNextLevel();
	}
	
	private void updateSounds(float dt)
	{
		// player climbing sounds
		if (climbing)
		{
			climbTimer+=dt;
			{
				if (climbTimer>=climbDuration)
				{
					climbTimer=0;
					climbSoundA=!climbSoundA;
					if (climbSoundA)
						Sounds.playerClimb1.play(.225f);
					else 
						Sounds.playerClimb2.play(.225f);
				}
			}
		}
		else
		{
			climbSoundA=true;
			climbTimer=stepDuration;
		}
		
		// play running sounds
		if (vel.x!=0 && vel.y==0)
		{
			stepTimer+=dt;
			{
				if (stepTimer>=stepDuration)
				{
					stepTimer=0;
					stepSoundA=!stepSoundA;
					if (stepSoundA)
						Sounds.playerStep1.play();
					else 
						Sounds.playerStep2.play();
				}
			}
		}
		else if (vel.x==0)
		{
			stepSoundA=true;
			stepTimer=stepDuration;
		}
	}
	
	private void checkCollEnemy()
	{
		if (Enemy.collides(coll.bounds))
		{
			game.world.reset();
			Screenflash.begin();
			Sounds.playerDie.play();
			return;
		}
		
		if (Plant.getColl(coll.bounds, PlantType.BIG_CHOMP)!=null)
			game.finalLevelScreen.beginFadeOut();
	}
	
	private void checkCollX()
	{
		// check horizontal world collisions
		Rectangle cx = game.world.getColl(collX.bounds);
		if (cx!=null)
		{
			if (vel.x>0)
				pos.x = cx.x - size.x;
			else if (vel.x<0)
				pos.x = cx.x + cx.width;
			
			vel.x = 0;
			velAdj.x = 0;
		}
	}
	
	private void checkCollY()
	{
		Rectangle cy = game.world.getColl(collY.bounds);
		if (cy==null && velAdj.y<0)
			cy = Plant.getVineTopColl(collY.bounds);
		if (cy!=null)
		{
			if (vel.y>0)
				pos.y = cy.y - size.y;
			else if (vel.y<0)
				pos.y = cy.y + cy.height;
			
			vel.y = 0;
			velAdj.y = 0;
			if (inAir)
				Sounds.playerLand.play();
			inAir=false;
		}
		else inAir=true;
	}
	
	private void updateAnimations()
	{
		if (climbing)
			anim=animClimb;
		else if (vel.y<0)
			anim=animFall;
		else if (vel.x!=0)
			anim=animRun;
		else anim=animIdle;
	}
	
	private void checkMovementCtl()
	{
		vel.x = 0;
		if (Input.isHeld(Keys.A) ||
			Input.isHeld(Keys.LEFT))
		{
			vel.x = -1;
			facing = LEFT;
			if (!flipped)
				flipped=true;
		}
		else if (Input.isHeld(Keys.D) ||
		Input.isHeld(Keys.RIGHT))
		{
			vel.x = 1;
			facing = RIGHT;
			if (flipped)
				flipped=false;
		}
		
		if (Input.isHeld(Keys.W) ||
			Input.isHeld(Keys.UP))
		{
			Rectangle cp = Plant.getColl(
				coll.bounds, PlantType.VINE_V);
			if (cp!=null)
			{
				climbing=true;
				pos.x = cp.x;
				vel.y=0;
				velAdj.y=0;
			}
			else climbing=false;
		}
		else climbing=false;
	}
	
	private void checkPlantCtl()
	{
		if (Input.isPressed(Keys.SPACE))
			Plant.setPlant(pos.x + size.x/2, pos.y,
				game.hud.getSelected());
	}
}
