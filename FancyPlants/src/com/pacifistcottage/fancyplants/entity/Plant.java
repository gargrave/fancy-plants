package com.pacifistcottage.fancyplants.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.pacifistcottage.fancyplants.Art;
import com.pacifistcottage.fancyplants.FancyPlants;
import com.pacifistcottage.fancyplants.LevelData;
import com.pacifistcottage.fancyplants.Sounds;
import com.pacifistcottage.fancyplants.engine.Anim;
import com.pacifistcottage.fancyplants.engine.Entity;

public class Plant extends Entity
{	
	public enum PlantType
	{ VINE_V, VINE_V_TOP, VINE_H, CHOMP, BIG_CHOMP }
	
	private PlantType type;
	
	public Plant(FancyPlants game)
	{
		super(game);
	}
	
	public void set(PlantType type, int x, int y)
	{
		this.type=type;
		anim=null;
		if (type==PlantType.VINE_V)
			tr=Art.vineV;
		else if (type==PlantType.VINE_V_TOP)
			tr=Art.vineVTop;
		else if (type==PlantType.VINE_H)
			tr=Art.vineH;
		else if (type==PlantType.CHOMP || type==PlantType.BIG_CHOMP)
		{
			chompTimer=0;
			chompActive=true;
			TextureRegion[] frames = new TextureRegion[]
			{
				Art.chomp[0],
				Art.chomp[1]
			};
			tr = frames[0];
			anim = new Anim(frames, .155f);
		}
		
		size.x = tr.getRegionWidth();
		size.y = tr.getRegionHeight();
		if (type==PlantType.BIG_CHOMP)
		{
			size.x*=6;
			size.y*=3;
		}
		pos.x=x;
		pos.y=y;
		activate();
	}
	
	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
	
	@Override
	public void draw(SpriteBatch sb)
	{		
		if (!active) return;
		
		if (anim!=null)
			tr=anim.frame();
		
		if (type==PlantType.BIG_CHOMP)
			sb.draw(tr, pos.x, pos.y, 0,0,32,32,6,6,0);
		else sb.draw(tr, pos.x, pos.y);
	}
	
	@Override
	public void deactivate()
	{
		pos.x=-100;
		pos.y=-100;
		active=false;
	}
	
	
	/**************************************
	 * static members
	 **************************************/
	
	public static int numVineV = 0;
	public static int numVineH = 0;
	public static int numChomp = 0;
	
	private static boolean chompActive=false;
	private static float chompDuration=.155f*2;
	private static float chompTimer=0;
	
	private static int grid=0;
	private static final Rectangle testRect = new Rectangle();
	
	private static List<int[]> vineTops = new ArrayList<int[]>();
	private static List<Plant> plants = new ArrayList<Plant>();
		
	public static void init(FancyPlants game)
	{
		grid = game.tileSize;
		for (int i=0; i<10; ++i)
			plants.add(new Plant(game));
	}
	
	public static Plant getInactivePlant()
	{
		for (Plant p : plants)
			if (!p.active)
				return p;
		
		// if we are still here, create a new Plant!
		Plant p = new Plant(game);
		plants.add(p);
		return p;
	}
	
	public static void clear()
	{
		chompActive=false;
		vineTops.clear();
		for (Plant p : plants)
			p.deactivate();
	}
	
	public static void stopSounds()
	{
		chompActive=false;
	}
	
	public static void loadLevel(int lvl)
	{
		int[] p = LevelData.get(lvl-1).plants;
		numVineV=p[0];
		numVineH=p[1];
		numChomp=p[2];
	}
	
	public static void updateAll(float dt)
	{
		for (Plant p : plants)
			if (p.active)
				p.update(dt);
		
		if (chompActive)
		{
			chompTimer+=dt;
			if (chompTimer>=chompDuration)
			{
				Sounds.chomp.play(.55f);
				chompTimer=0;
			}
		}
	}
	
	public static void drawAll(SpriteBatch sb)
	{
		for (Plant p : plants)
			if (p.active)
				p.draw(sb);
	}
	
	public static void setPlant(float x, float y, int sel)
	{
		if (sel==0)
			setVineV(x,y);
		else if (sel==1)
			setVineH(x,y);
		else if (sel==2)
			setChomp(x,y);
		else if (sel==3)
			setBigChomp(x,y);
	}
	
	private static void setVineV(float x, float y)
	{
		int px = (int)(x - (x%grid));
		int py = (int)(y - (y%grid));
		
		// early-out if we don't have any of these left
		if (numVineV<=0) return;
		
		// early-out if we are not on solid ground
		int ground=(py-grid)/grid;
		if (!game.world.isSolidAt(px/grid, ground))
			return;
		
		// make sure there is no solid ground for the 4 blocks above
		for (int i=1; i<=4; ++i)
			if (game.world.isSolidAt(px/grid, ground+i))
				return;
		
		// early-out if we already have a plant here
		for (Plant p : plants)
			if (p.pos.x==px && p.pos.y==py)
				return;
		
		// otherwise, set the plant + 3 on top of it
		for (int i=0; i<4; ++i)
		{
			if (i==3)
			{
				getInactivePlant().set(
					PlantType.VINE_V_TOP,
					px, py+i*grid);
				vineTops.add(new int[] {px, py+i*grid});
			}
			else
			{
				getInactivePlant().set(
					PlantType.VINE_V,
					px, py+i*grid);
			}
		}
		--numVineV;
		Sounds.vineV.play(.85f);
	}
	
	private static void setVineH(float x, float y)
	{
		int dx = game.player.facing;
		int px = (int)(x+(grid*dx) - (x%grid));
		int py = (int)y-grid;
		
		// early-out if we don't have any of these left
		if (numVineH<=0) return;
		
		// early-out if we are not on solid ground
		int ground=(px-(grid*dx))/grid;
		if (!game.world.isSolidAt(ground, py/grid) ||
			game.world.isSolidAt(px/grid, py/grid))
			return;
		
		for (int i=1; i<4; ++i)
		{
			if (game.world.isSolidAt((px/grid)+i*dx, py/grid))
				return;
		}
		
		// early-out if we already have a plant here
		for (Plant p : plants)
			if (p.pos.x==px && p.pos.y==y)
				return;
		
		// otherwise, set the plant + 3 in direction player is facing
		for (int i=0; i<4; ++i)
		{
			getInactivePlant().set(
				PlantType.VINE_H,
				px+(i*dx)*grid, py);
			vineTops.add(new int[] {px+(i*dx)*grid, py});
		}
		--numVineH;
		Sounds.vineH.play(.225f);
	}
	
	private static void setChomp(float x, float y)
	{
		if (numChomp<=0) return;
		
		int px = (int)(x - (x%grid));
		
		// early-out if we already have a plant here
		for (Plant p : plants)
			if (p.pos.x==px && p.pos.y==y)
				return;
		
		getInactivePlant().set(
			PlantType.CHOMP,
			px, (int)y);
		--numChomp;
	}
	
	private static void setBigChomp(float x, float y)
	{		
		int px = (int)(x - (x%grid));
		
		getInactivePlant().set(
			PlantType.BIG_CHOMP,
			px, (int)y);
	}
	
	public static Rectangle getColl(Rectangle bounds, PlantType type)
	{
		for (Plant p : plants)
		{
			if (p.active && p.type==type)
			{
				testRect.set(p.pos.x, p.pos.y, p.size.x, p.size.y);
				if (testRect.overlaps(bounds))
					return testRect;
			}
		}
		return null;
	}
	
	public static Rectangle getVineTopColl(Rectangle bounds)
	{
		for (int[] xy : vineTops)
		{
			testRect.set(xy[0], xy[1], grid, grid);
			if (testRect.overlaps(bounds))
				return testRect;
		}
		return null;
	}
}
