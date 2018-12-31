package com.pacifistcottage.fancyplants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds
{
	public static Sound chomp;
	public static Sound enemyDie;
	public static Sound playerClimb1;
	public static Sound playerClimb2;
	public static Sound playerDie;
	public static Sound playerDieFinal;
	public static Sound playerLand;
	public static Sound playerStep1;
	public static Sound playerStep2;
	public static Sound signShow;
	public static Sound signHide;
	public static Sound starPickup;
	public static Sound worldReset;
	public static Sound vineH;
	public static Sound vineV;
	
	public static void load()
	{
		chomp = Gdx.audio.newSound(Gdx.files.internal(
			"sounds/chomp01.wav"));
		
		enemyDie = Gdx.audio.newSound(Gdx.files.internal(
			"sounds/enemyDie.wav"));
		
		playerClimb1 = Gdx.audio.newSound(Gdx.files.internal(
			"sounds/playerClimb1.wav"));
		playerClimb2 = Gdx.audio.newSound(Gdx.files.internal(
			"sounds/playerClimb2.wav"));
		playerDie = Gdx.audio.newSound(Gdx.files.internal(
			"sounds/playerDie.wav"));
		playerDieFinal = Gdx.audio.newSound(Gdx.files.internal(
			"sounds/playerDieFinal.wav"));
		playerLand = Gdx.audio.newSound(Gdx.files.internal(
			"sounds/playerLand.wav"));
		playerStep1 = Gdx.audio.newSound(Gdx.files.internal(
			"sounds/playerStep1.wav"));
		playerStep2 = Gdx.audio.newSound(Gdx.files.internal(
			"sounds/playerStep2.wav"));
		
		signShow = Gdx.audio.newSound(Gdx.files.internal(
			"sounds/signShow.wav"));
		signHide = Gdx.audio.newSound(Gdx.files.internal(
			"sounds/signHide.wav"));
		
		starPickup = Gdx.audio.newSound(Gdx.files.internal(
			"sounds/starPickup.wav"));
		
		worldReset = Gdx.audio.newSound(Gdx.files.internal(
			"sounds/worldReset.wav"));
		
		vineH = Gdx.audio.newSound(Gdx.files.internal(
			"sounds/vineH.wav"));
		vineV = Gdx.audio.newSound(Gdx.files.internal(
			"sounds/vineV.wav"));
	}
	
	public static void dispose()
	{
		chomp.dispose();
		enemyDie.dispose();
		playerClimb1.dispose();
		playerClimb2.dispose();
		playerDie.dispose();
		playerDieFinal.dispose();
		playerLand.dispose();
		playerStep1.dispose();
		playerStep2.dispose();
		signShow.dispose();
		signHide.dispose();
		starPickup.dispose();
		worldReset.dispose();
		vineH.dispose();
		vineV.dispose();
	}
}
