package com.pacifistcottage.fancyplants;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pacifistcottage.fancyplants.entity.Enemy;
import com.pacifistcottage.fancyplants.entity.Plant;
import com.pacifistcottage.fancyplants.entity.Star;

public class World
{	
	// the number of the final level
	private static final int LAST_LEVEL=17;
	// the current level
	public static int level=10;
		
	// level-image color values
	private static final int TILE=0xffffff;		// white = solid tile
	private static final int SIGN=0x0000ff;		// blue = sign
	private static final int STAR=0xffff00;		// yellow = star
	private static final int PLAYER=0xff0000; 	// red = player start
	private static final int GRAVY=0x00ff00; 	// green = j. gravy
	private static final int ENEMY=0xff00ff; 	// magenta = enemy
	private static final int BIG_CHOMP=0xff7f00;// orange = giant chomp
	
	// tile-type int values
	private static final int EMPTY=-1;
	private static final int SOLID=1;
	
	// map size, in tiles
	private static final int w=40, h=23;
	// the number of levels defined in each row of 'level.png'
	private static final int IMG_WIDTH=6;
	// a re-useable Rectangle for collision testing
	private static final Rectangle testRect = new Rectangle();
	// the size of tiles in the world
	private static int tileSize = 32;
	
	// a reference to the main game class
	private FancyPlants game;
	// the map grid for the current level
	private int[] map = new int[w*h];
	// the map's bitmask values for the current values
	private int[] mapBM = new int[w*h];
	
	public World(FancyPlants game)
	{
		this.game = game;
		tileSize=game.tileSize;
	}
	
	public void clearMap()
	{
		for (int y=0; y<h; ++y)
			for (int x=0; x<w; ++x)
				map[x+y*w] = EMPTY;
		Enemy.clear();
		Plant.clear();
		Star.clear();
		game.sign.deactivate();
		game.jgravy.deactivate();
		game.hud.reset();
		game.player.reset();
	}
	
	public void loadLevel(int lvl)
	{
		clearMap();
		level=lvl;
		
		int lx = ((level-1)%IMG_WIDTH)*w;
		int ly = ((level-1)/IMG_WIDTH)*h;
		
		for (int y=0; y<h; ++y)
			for (int x=0; x<w; ++x)
			{
				int px = Art.level.getPixel(
					lx+x, ly+h-y-1) >>> 8;
				
				if (px==TILE)
					map[x+y*w]=SOLID;
				else if (px==STAR)
					Star.set(x*tileSize, y*tileSize);
				else if (px==ENEMY)
					Enemy.setEnemy(x,y);
				else if (px==BIG_CHOMP)
					Plant.setPlant(x*tileSize, y*tileSize, 3);
				else if (px==PLAYER)
				{
					int py = level>1 ? y : y+2;
					game.player.setPosByGrid(x,py);
				}
				else if (px==GRAVY)
				{
					game.jgravy.setPosByGrid(x, y);
					game.jgravy.msg=LevelData.getSign(level);
					game.jgravy.activate();
				}
				else if (px==SIGN)
				{
					game.sign.setPosByGrid(x, y);
					game.sign.msg=LevelData.getSign(level);
					game.sign.activate();
				}
			}
		
		// build the bit-masking list for the new map
		for (int y=0; y<h; ++y)
			for (int x=0; x<w; ++x)
			{
				if (map[x+y*w]==EMPTY)
				{
					mapBM[x+y*w] = EMPTY;
					continue;
				}
				
				int val=0;
				if (tileAbove(x, y)!=EMPTY) 	val += 1;
				if (tileToRight(x, y)!=EMPTY) 	val += 2;
				if (tileBelow(x, y)!=EMPTY) 	val += 4;
				if (tileToLeft(x, y)!=EMPTY) 	val += 8;
				mapBM[x+y*w] = val;
			}
		
		Plant.loadLevel(level);
		if (level==LAST_LEVEL)
			game.setScreen(game.finalLevelScreen);
	}
	
	public boolean loadNextLevel()
	{
		if (level==LAST_LEVEL)
		{
			level=1;
			game.setScreen(game.titleScreen);
			return false;
		}
		else 
		{
			loadLevel(++level);
			return true;
		}
	}
	
	public void reset()
	{
		loadLevel(level);
	}
	
	public void draw(SpriteBatch sb)
	{		
		for (int y=0; y<h; ++y)
			for (int x=0; x<w; ++x)
			{
				int tile = mapBM[x+y*w];
				if (tile==EMPTY)
					continue;
				
				Tile.draw(sb, x, y, tile);
			}
	}
	
	public Rectangle getColl(Rectangle bounds)
	{
		for (int y=0; y<h; ++y)
			for (int x=0; x<w; ++x)
			{	
				int tile = mapBM[x+y*w];
				if (tile==-1 || tile==15)
					continue;
				
				testRect.set(x*tileSize, y*tileSize, tileSize, tileSize);
				if (testRect.overlaps(bounds))
					return testRect;
			}
		return null;
	}
	
	public boolean isSolidAt(int x, int y)
	{
		return map[x+y*w] != EMPTY;
	}
	
	private int tileAbove(int x, int y)
	{ 
		if (y==h-1) return SOLID;
		return map[x+(y+1)*w]; 
	}
	
	private int tileToRight(int x, int y)
	{ 
		if (x==w-1) return SOLID;
		return map[(x+1)+y*w]; 
	}
	
	private int tileBelow(int x, int y)
	{ 
		if (y==0) return SOLID;
		return map[x+(y-1)*w]; 
	}
	
	private int tileToLeft(int x, int y)
	{ 
		if (x==0) return SOLID;
		return map[(x-1)+y*w]; 
	}
}
