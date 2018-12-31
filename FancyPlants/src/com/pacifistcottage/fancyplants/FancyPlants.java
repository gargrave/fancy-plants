package com.pacifistcottage.fancyplants;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.pacifistcottage.fancyplants.entity.Player;
import com.pacifistcottage.fancyplants.entity.Sign;
import com.pacifistcottage.fancyplants.screen.EndScreen;
import com.pacifistcottage.fancyplants.screen.FinalLevelScreen;
import com.pacifistcottage.fancyplants.screen.FpScreen;
import com.pacifistcottage.fancyplants.screen.GameScreen;
import com.pacifistcottage.fancyplants.screen.InitScreen;
import com.pacifistcottage.fancyplants.screen.LevelCompleteScreen;
import com.pacifistcottage.fancyplants.screen.PauseScreen;
import com.pacifistcottage.fancyplants.screen.TitleScreen;

public class FancyPlants extends Game
{
	// game settings
	public final Color SKY = new Color(
		48.0f/255, 52.0f/255, 109.0f/255, 1);
	public int w=1280, h=720;
	public int tileSize=32;
	
	// game entities
	public Hud hud;
	public World world;
	public Player player;
	public Sign sign;
	public Sign jgravy;
	
	// game screens
	public FpScreen initScreen;
	public FpScreen pauseScreen;
	public FpScreen titleScreen;
	public FpScreen gameScreen;
	public FpScreen levelCompleteScreen;
	public FpScreen finalLevelScreen;
	public FpScreen endScreen;
	
	@Override
	public void create()
	{
		initScreen = new InitScreen(this);
		pauseScreen = new PauseScreen(this);
		gameScreen = new GameScreen(this);
		titleScreen = new TitleScreen(this);
		levelCompleteScreen = new LevelCompleteScreen(this);
		finalLevelScreen = new FinalLevelScreen(this);
		endScreen = new EndScreen(this);
		
		setScreen(initScreen);
	}

	@Override
	public void dispose()
	{
		initScreen.dispose();
		pauseScreen.dispose();
		titleScreen.dispose();
		gameScreen.dispose();
		levelCompleteScreen.dispose();
		finalLevelScreen.dispose();
		endScreen.dispose();
		
		Art.dispose();
		Sounds.dispose();
	}
}
