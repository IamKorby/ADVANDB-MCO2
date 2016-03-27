package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;

public class DatabaseManager
{
	private static DatabaseManager databaseManager = new DatabaseManager();
	private DatabaseConnector connection;
	private QueryBuilder queryBuilder;
	
	private DatabaseManager()
	{
		connection = DatabaseConnector.getInstance();
		queryBuilder = new QueryBuilder();
	}
	
	public static DatabaseManager getInstance()
	{
		return databaseManager;
	}
	
	// QUERY
	public ResultSet getDimensionCrop()
	{
		PreparedStatement ps;
		String sql = "SELECT * FROM dimcrop";
		
		try
		{
			ps = connection.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			return rs;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ResultSet getDimensionHousehold()
	{
		PreparedStatement ps;
		String sql = "SELECT * FROM dimhousehold LIMIT 1000";
		
		try
		{
			ps = connection.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			return rs;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ResultSet getDimensionLandParcel()
	{
		PreparedStatement ps;
		String sql = "SELECT * FROM dimlandparcel";
		
		try
		{
			ps = connection.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			return rs;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ResultSet getFactTable()
	{
		PreparedStatement ps;
		String sql = "SELECT * FROM fact_table";
		
		try
		{
			ps = connection.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			return rs;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ResultSet getData( ArrayList<String> attributes, ArrayList<String> tables )
	{
		PreparedStatement ps;
		String sql = queryBuilder.buildQuery(attributes, tables);
		
		System.out.println(sql);
		
		try
		{
			ps = connection.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			return rs;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
//	public ArrayList<Preference> getAllPreferences()
//	{
//		ArrayList<Preference> preferenceList = new ArrayList<>(0);
//		PreparedStatement ps;
//		String sql = "SELECT * FROM preference";
//		
//		try
//		{
//			ps = connection.getConnection().prepareStatement(sql);
//			ResultSet rs = ps.executeQuery();
//			
//			while(rs.next())
//			{
//				Preference p = new Preference(rs.getInt(1),
//						rs.getString(2));
//				
//				preferenceList.add(p);
//			}
//		}
//		catch (SQLException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return (ArrayList<Preference>) preferenceList.clone();
//	}
}
