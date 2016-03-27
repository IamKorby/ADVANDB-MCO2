package model;

import java.util.HashMap;

// RollUpDrillDownAttribute
public class RUDDAttribute
{
	private HashMap<String, String> attributes;
	
	public RUDDAttribute()
	{
		attributes = new HashMap<>();
	}
	
	public HashMap<String, String> getAttributes()
	{
		return attributes;
	}
}
