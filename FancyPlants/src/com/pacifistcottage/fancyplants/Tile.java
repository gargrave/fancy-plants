package com.pacifistcottage.fancyplants;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile
{
	private static FancyPlants game;
	
	// tile TextureRegions
	private static TextureRegion drawTile;
	private static TextureRegion dirtUL;
	private static TextureRegion dirtUM;
	private static TextureRegion dirtUR;
	private static TextureRegion dirtML;
	private static TextureRegion dirtMM;
	private static TextureRegion dirtMR;
	private static TextureRegion dirtBL;
	private static TextureRegion dirtBM;
	private static TextureRegion dirtBR;
	private static TextureRegion dirtSHL;
	private static TextureRegion dirtSHM;
	private static TextureRegion dirtSHR;
	private static TextureRegion dirtSVU;
	private static TextureRegion dirtSVM;
	private static TextureRegion dirtSVB;
	private static TextureRegion dirtSingle;
	
	public static void init(FancyPlants _game)
	{
		game = _game;
		dirtUL = Art.tiles[0][0];
		dirtUM = Art.tiles[0][1];
		dirtUR = Art.tiles[0][2];
		dirtML = Art.tiles[1][0];
		dirtMM = Art.tiles[1][1];
		dirtMR = Art.tiles[1][2];
		dirtBL = Art.tiles[2][0];
		dirtBM = Art.tiles[2][1];
		dirtBR = Art.tiles[2][2];
		dirtSHL = Art.tiles[3][0];
		dirtSHM = Art.tiles[3][1];
		dirtSHR = Art.tiles[3][2];
		dirtSVU = Art.tiles[0][3];
		dirtSVM = Art.tiles[1][3];
		dirtSVB = Art.tiles[2][3];
		dirtSingle = Art.tiles[3][3];
		drawTile=dirtUM;
	}
	
	public static void draw(SpriteBatch sb, int x, int y, int tile)
	{
		switch(tile)
		{
		// top-row tiles
		case 6:		drawTile=dirtUL; break;
		case 14:	drawTile=dirtUM; break;
		case 12:	drawTile=dirtUR; break;
		// mid-row tiles
		case 7:		drawTile=dirtML; break;
		case 15:	drawTile=dirtMM; break;
		case 13:	drawTile=dirtMR; break;
		// bottom-row tiles
		case 3:		drawTile=dirtBL; break;
		case 11:	drawTile=dirtBM; break;
		case 9:		drawTile=dirtBR; break;
		// single-v-depth tiles
		case 2:		drawTile=dirtSHL; break;
		case 10: 	drawTile=dirtSHM; break;
		case 8: 	drawTile=dirtSHR; break;
		// single-h-depth tiles
		case 4:		drawTile=dirtSVU; break;
		case 5: 	drawTile=dirtSVM; break;
		case 1: 	drawTile=dirtSVB; break;
		case 0:		drawTile=dirtSingle; break;
		default:	drawTile=dirtUM; break;
		}	
		sb.draw(drawTile, x*game.tileSize, y*game.tileSize);
	}
}
