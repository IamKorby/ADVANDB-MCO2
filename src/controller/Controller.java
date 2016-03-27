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
	
	public ResultSet getData(ArrayList<String> attributes, ArrayList<String> tables)
	{
		return databaseManager.getData(attributes, tables);
	}
}
