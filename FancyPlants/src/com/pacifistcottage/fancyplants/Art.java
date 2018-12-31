package com.pacifistcottage.fancyplants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Art
{
	public static TextureRegion[][] player2;
	public static TextureRegion star;
	public static TextureRegion sign;
	public static TextureRegion jgravy;
	public static TextureRegion grave;
	public static TextureRegion[][] enemy;
	public static TextureRegion[][] tiles;
	
	public static TextureRegion[][] plants;
	public static TextureRegion vineV;
	public static TextureRegion vineVTop;
	public static TextureRegion vineH;
	public static TextureRegion[] chomp;
	
	public static Pixmap level;
	
	public static BitmapFont titleFont1;
	public static BitmapFont titleFont2;
	public static BitmapFont titleFont3;
	public static BitmapFont textFont1;
	public static BitmapFont textFont2;
	
	public static void load()
	{
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(
			"img/atlas.atlas"));
		
		player2 = atlas.findRegion("player-OL").split(34, 66);
		
		star = atlas.findRegion("star");
		sign = atlas.findRegion("sign");
		jgravy = atlas.findRegion("jgravy");
		tiles = atlas.findRegion("tiles-OL").split(32, 32);
//		plants = atlas.findRegion("plants").split(32, 32);
		plants = new TextureRegion(new Texture(Gdx.files.internal(
			"img/plants.png"))).split(32, 32);
		
		enemy = atlas.findRegion("enemy-OL").split(34, 34);
		grave = atlas.findRegion("grave");
		
		vineV = plants[0][0];
		vineVTop = plants[0][4];
		vineH = plants[0][1];
		chomp = new TextureRegion[] {plants[0][2], plants[0][3]};
		
		level = new Pixmap(Gdx.files.internal("img/level.png"));
		
		titleFont1 = new BitmapFont(
			Gdx.files.internal("fonts/titleFont.fnt"), 
			Gdx.files.internal("fonts/titleFont.png"), false, true);
		titleFont2 = new BitmapFont(
			Gdx.files.internal("fonts/titleFont2.fnt"), 
			Gdx.files.internal("fonts/titleFont2.png"), false, true);
		titleFont3 = new BitmapFont(
			Gdx.files.internal("fonts/titleFont3.fnt"), 
			Gdx.files.internal("fonts/titleFont3.png"), false, true);
		textFont1 = new BitmapFont(
			Gdx.files.internal("fonts/textFont1.fnt"), 
			Gdx.files.internal("fonts/textFont1.png"), false, true);
		textFont2 = new BitmapFont(
			Gdx.files.internal("fonts/textFont2.fnt"), 
			Gdx.files.internal("fonts/textFont2.png"), false, true);
	}
	
	public static void dispose()
	{
		level.dispose();
		titleFont1.dispose();
		titleFont2.dispose();
		titleFont3.dispose();
		textFont1.dispose();
		textFont2.dispose();
	}
}
