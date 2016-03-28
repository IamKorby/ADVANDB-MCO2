package controller;

import java.sql.ResultSet;
import java.util.ArrayList;

import model.DatabaseManager;

public class Controller
{
	private DatabaseManager databaseManager;
	
	public Controller()
	{
		databaseManager = DatabaseManager.getInstance();
	}
	
	public ResultSet getDimensionCrop()
	{
		return databaseManager.getDimensionCrop();
	}
	
	public ResultSet getDimensionHousehold()
	{
		return databaseManager.getDimensionHousehold();
	}
	
	public ResultSet getDimensionLandParcel()
	{
		return databaseManager.getDimensionLandParcel();
	}
	
	public ResultSet getFactTable()
	{
		return databaseManager.getFactTable();
	}
	
	public ResultSet getData(ArrayList<String> ruddAttributes, ArrayList<String> sdAttributes, ArrayList<String> tables)
	{
		return databaseManager.getData(ruddAttributes, sdAttributes, tables);
	}
	
	public String[] getCropTypes()
	{
		return databaseManager.getCropTypes();
	}
	
	public String[] getHarvestDecreaseReason()
	{
		return databaseManager.getHarvestDecreaseReason();
	}
	
	public String[] getCropChangeReason()
	{
		return databaseManager.getCropChangeReason();
	}
	
	public double getQueryTime()
	{
		return databaseManager.getQueryTime();
	}
}
