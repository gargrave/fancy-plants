package com.pacifistcottage.fancyplants.entity;

import com.pacifistcottage.fancyplants.Art;
import com.pacifistcottage.fancyplants.FancyPlants;

public class Jgravy extends Sign
{
	public Jgravy(FancyPlants game)
	{
		super(game);
		tr = Art.jgravy;
		size.x = tr.getRegionWidth();
		size.y = tr.getRegionHeight();
		rx = game.w/2 - rw/2;
		ry = game.h/2 - rh/2;
	}
}
