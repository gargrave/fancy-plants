package com.wediditgames.fancyplants;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pacifistcottage.fancyplants.FancyPlants;

public class Main
{
	public static void main(String[] args)
	{
		LwjglApplicationConfiguration cfg = 
			new LwjglApplicationConfiguration();
		
		cfg.title = "Fancyplants";
		cfg.useGL20 = false;
		cfg.width = 1280;
		cfg.height = 720;
		cfg.vSyncEnabled = true;
		
		cfg.addIcon("img/Fp-Icon-128.png", FileType.Internal);
		cfg.addIcon("img/Fp-Icon-32.png", FileType.Internal);
		cfg.addIcon("img/Fp-Icon-16.png", FileType.Internal);

		new LwjglApplication(new FancyPlants(), cfg);
	}
}
