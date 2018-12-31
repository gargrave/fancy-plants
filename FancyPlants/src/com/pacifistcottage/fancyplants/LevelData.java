package com.pacifistcottage.fancyplants;

import java.util.HashMap;
import java.util.Map;

public class LevelData
{
	public static class Level
	{
		public final String msg;
		public final int[] plants;
		
		public Level(String msg, int[] plants)
		{
			this.msg=msg;
			this.plants=plants;
		}
		
		public static final Level EMPTY = 
			new Level("Unset level",new int[]{ 0,0,0 });
	}
	
	private static final Level[] levels = new Level[]
	{
		new Level(
			"\"A helping hand\"",
			new int[]{ 0,0,0 }),
		new Level(
			"\"OMG, two stars this time!\"",
			new int[]{ 0,0,0 }),
		new Level(
			"\"What kind of name is \"J. Gravy\" anyway?\"",
			new int[]{ 0,0,0 }),
		new Level(
			"\"Meet the climbey vine\"",
			new int[]{ 1,0,0 }),
		new Level(
			"\"Climbing the corporate ladder\"",
			new int[]{ 2,0,0 }),
		new Level(
			"\"Stars, stars, everywhere...\"",
			new int[]{ 3,0,0 }),
		new Level(
			"\"Archipelago\"",
			new int[]{ 2,0,0 }),
		new Level(
			"\"Bridging the gap\"",
			new int[]{ 0,1,0 }),
		new Level(
			"\"A real brain-buster\"",
			new int[]{ 1,1,0 }),
		new Level(
			"\"Marzipan: an almond confection\"",
			new int[]{ 2,3,0 }),
		new Level(
			"\"CHOMP!\"",
			new int[]{ 2,0,1 }),
		new Level(
			"\"Barry be nimble...\"",
			new int[]{ 2,0,1 }),
		new Level(
			"\"F-F-Fancy footwork\"",
			new int[]{ 3,3,1 }),
		new Level(
			"\"Climbing to new possibilities\"",
			new int[]{ 1,0,1 }),
		new Level(
			"\"Timing is everything!\"",
			new int[]{ 2,1,1 }),
		new Level(
			"\"Payment for services rendered\"",
			new int[]{ 0,0,0 })
	};
	public static Level get(int lvl)
	{
		if (lvl<0 || lvl>levels.length-1)
			return Level.EMPTY;
		return levels[lvl];
	}
	
	private static final Map<Integer, String> signs = new HashMap<Integer, String>();
	static
	{
		signs.put(1, 
			"Hello, Barry. Thanks for dropping by!\r\n" +
			"This is the job I was telling you about.\r\n" +
			"I need you to get all these blasted stars\r\n" +
			"out of my arenas.\r\n\r\n" +
			
			"Use the A/D or Left/Right keys to move,\r\n" +
			"and go grab them! Hurry up, now, I'm \r\n" +
			"not paying you to read my signs!" +
			"\r\n\r\n -J. Gravy");
		
		signs.put(2, 
			"Oh, oh, I almost forgot! If you find yourself\r\n" +
			"stuck at any point, just press the 'R' key\r\n" +
			"to reset the arena.\r\n\r\n" +
			
			"Now let's go! No more dilly-dally! I am still\r\n" +
			"not paying you to read my signs!" +
			"\r\n\r\n -J. Gravy");
		
		signs.put(4, 
			"Heeeeeeey buddy, one thing I may have forgotten\r\n" +
			"to mention: things might get a little more\r\n" +
			"complicated than I initially indicated,\r\n" +
			"so I hope you're ready for a little climbing.\r\n\r\n" +
			
			"Use the Q/E keys to select the climbey vine\r\n" +
			"in your stash, and then press spacebar to plant it\r\n" +
			"by that wall. Then you can climb up and grab yonder\r\n" +
			"star, LIKE I AM PAYING YOU TO DO." +
			"\r\n\r\n-J. Gravy");
		
		signs.put(5, 
			"Hello again, Barry. Just a reminder: if you get\r\n" +
			"stuck, just hit the 'R' key, and you can start over\r\n" +
			"as if nothing ever happened." +
			"\r\n\r\n -J. Gravy");
		
//		signs.put(6, 
//			"Hi Barry. I just thought it was time for me to come\r\n" +
//			"clean, so here goes:\r\n\r\n" +
//			".\r\n\r\n" +
//			"Wow, I feel better already! Don't you feel a lot\r\n" +
//			"better now that I got that off my chest? I still believe\r\n" +
//			"we can find a way to work things out. ;)" +
//			"\r\n\r\n -J. Gravy");
		
		signs.put(8, 
			"Making climbey-ropes is not all those vines can\r\n" +
			"do! I put one in your stash that will work as a\r\n" +
			"bridge for that gap.\r\n\r\n" +
			"You know, if you could jump like a normal person,\r\n" +
			"this would all be a lot easier. Just saying..." +
			"\r\n\r\n -J. Gravy");
		
		signs.put(11, 
			"Hey Barry--you see that little Nasty Thing\r\n" +
			"up there? Yeah, it wants to eat you.\r\n\r\n" +
			"I can't say I am incredibly inclined to\r\n" +
			"keep it from doing so, but I thought it might\r\n" +
			"be fun to give you a fighting chance anyway.\r\n\r\n" +
			"I put a CHOMP in your stash. That's all I'm\r\n" +
			"going to say. You're a big boy--figure it out." + 
			"\r\n\r\n-J. Gravy");
		
		signs.put(13, 
			"Hey buddy! Don't lose your footing--those\r\n" +
			"Nasty Things look pretty hungry. ;)" +
			"\r\n\r\n-J. Gravy");
		
		signs.put(14, 
			"Barry, do you remember a couple years back, when\r\n" +
			"I told you about my idea for a series of \"assisted\r\n" +
			"revenge arenas,\" and you told me it was a terrible idea,\r\n" +
			"and that I would never be able to find any\r\n" +
			"\"volunteers\" dumb enough to enter said arenas? \r\n\r\n" +
			"Did I mention that I got that\r\n" +
			"project off the ground the last month? Yeah, I am\r\n" +
			"currently prototyping it with an old friend." +
			"\r\n\r\n-J. Gravy");
		
		signs.put(16, 
			"Hey there, Barry. Good to see you, old friend.\r\n" +
			"I appreciate all you have done for me, and I\r\n" + 
			"suppose it's time for us to settle up.\r\n\r\n" + 
			"Why don't you step into my uh... well...\r\n" + 
			"my office over here, and I will have my uh...\r\n" + 
			"my secretary cut you a check.\r\n\r\n" + 
			"Go ahead, big guy. I'll be right behind you!");
	}
	
	public static String getSign(int lvl)
	{
		if (signs.containsKey(lvl))
			return signs.get(lvl);
		return "";
	}
}
