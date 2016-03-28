package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.fabric.xmlrpc.base.Array;

import java.sql.PreparedStatement;

public class DatabaseManager
{
	private static DatabaseManager databaseManager = new DatabaseManager();
	private DatabaseConnector connection;
	private QueryBuilder queryBuilder;
	private double queryTime;
	
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
			
			double start = System.currentTimeMillis();
			ResultSet rs = ps.executeQuery();
			double end = System.currentTimeMillis();
			
			queryTime = end - start;
			
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

			double start = System.currentTimeMillis();
			ResultSet rs = ps.executeQuery();
			double end = System.currentTimeMillis();
			
			queryTime = end - start;
			
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

			double start = System.currentTimeMillis();
			ResultSet rs = ps.executeQuery();
			double end = System.currentTimeMillis();
			
			queryTime = end - start;
			
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

			double start = System.currentTimeMillis();
			ResultSet rs = ps.executeQuery();
			double end = System.currentTimeMillis();
			
			queryTime = end - start;
			
			return rs;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ResultSet getData( ArrayList<String> ruddAttributes, ArrayList<String> sdAttributes, ArrayList<String> tables )
	{
		PreparedStatement ps;
		String sql = queryBuilder.buildQuery(ruddAttributes, sdAttributes, tables);
		
		System.out.println(sql);
		
		try
		{
			ps = connection.getConnection().prepareStatement(sql);

			double start = System.currentTimeMillis();
			ResultSet rs = ps.executeQuery();
			double end = System.currentTimeMillis();
			
			queryTime = end - start;
			
			return rs;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block3w
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String[] getCropTypes()
	{
		ArrayList<String> cropTypeList = new ArrayList<String>(0);
		PreparedStatement ps;
		String sql = "SELECT DISTINCT croptype FROM dim_crop ORDER BY croptype;";
		
		cropTypeList.add("");
		
		try
		{
			ps = connection.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				cropTypeList.add(rs.getString(1));
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cropTypeList.toArray(new String[0]);
	}
	
	public String[] getHarvestDecreaseReason()
	{
		ArrayList<String> harvestDecreaseReason = new ArrayList<String>(0);
		PreparedStatement ps;
		String sql = "SELECT DISTINCT u_low_harv FROM dim_household ORDER BY u_low_harv;";
		
		try
		{
			ps = connection.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				harvestDecreaseReason.add(rs.getString(1));
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return harvestDecreaseReason.toArray(new String[0]);
	}
	
	public String[] getCropChangeReason()
	{
		ArrayList<String> cropChangeReason = new ArrayList<String>(0);
		PreparedStatement ps;
		String sql = "SELECT DISTINCT u_chng_pcrop_y FROM dim_household ORDER BY u_chng_pcrop_y;";
		
		try
		{
			ps = connection.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				cropChangeReason.add(rs.getString(1));
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cropChangeReason.toArray(new String[0]);
	}
	
	public double getQueryTime()
	{
		return queryTime;
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
