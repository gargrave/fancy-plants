package com.pacifistcottage.fancyplants.engine;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Input
{
	private static final Map<Integer, Boolean> locked = 
		new HashMap<Integer, Boolean>();
	
	private static boolean anyKeyLock=false;
	
	public static boolean isHeld(int key)
	{
		return Gdx.input.isKeyPressed(key);
	}
	
	public static boolean isPressed(int key)
	{
		if (!Gdx.input.isKeyPressed(key))
			return false;
		
		if (key==Keys.ANY_KEY && anyKeyLock)
			return false;
		
		if (locked.containsKey(key))
		{
			if (locked.get(key)==true)
				return false;
			
			else
			{
				locked.put(key, true);
				return true;
			}
		}
		else
		{
			locked.put(key, true);
			return true;
		}
	}
	
	public static void update()
	{
		if (anyKeyLock==true)
		{
			if (!Gdx.input.isKeyPressed(Keys.ANY_KEY))
				anyKeyLock=false;
		}
		
		for (Map.Entry<Integer, Boolean> kv : locked.entrySet())
		{
			if (kv.getValue()==true && !Gdx.input.isKeyPressed(kv.getKey()))
				kv.setValue(false);
		}
	}
	
	public static void lock(int key)
	{
		locked.put(key, true);
	}
	
	public static void lockAll()
	{
		anyKeyLock=true;
	}
}
