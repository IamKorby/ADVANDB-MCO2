package model;

import java.util.ArrayList;

public class QueryBuilder
{
	private String measure = " AVG(f.cropvol_per_land) ";
	
	public String buildQuery( ArrayList<String> attributes, ArrayList<String> tables )
	{
		String queryAttributes = buildSelectGroupBy(attributes);
		String select = "SELECT ";
		String fromwhere = buildFromWhere(tables);
		String groupby = "";
		
		// if there are selected attributes, add them to select and groupby
		// add measure to select
		if( attributes.size() != 0 )
		{
			select += queryAttributes + ", " + measure;
			groupby = "GROUP BY " + queryAttributes;
		}
		else
		{
			select += measure;
		}
		
		return select + fromwhere + groupby;
	}
	
	// appends all attributes separated by a comma
	private String buildSelectGroupBy( ArrayList<String> attributes )
	{
		String queryAttributes = "";
		
		for( int i = 0; i < attributes.size(); i++ )
		{
			if( i + 1 != attributes.size() )
			{
				queryAttributes += attributes.get(i) + ", ";
			}
			else
			{
				queryAttributes += attributes.get(i);
			}
		}
		
		return queryAttributes;
	}
	
	// builds from and where clause depending on the tables needed (based on the user's column selection)
	private String buildFromWhere( ArrayList<String> tables )
	{
		String from = "FROM facttable f ";
		String where = "";
		
		if( tables.size() != 0 )
		{
			from += ", ";
			where = "WHERE ";
		}
		
		for( int i = 0; i < tables.size(); i++ )
		{
			if( i + 1 != tables.size() )
			{
				from += tables.get(i) + ", ";
				where += buildWhere(tables.get(i)) + "AND ";
			}
			else
			{
				from += tables.get(i);
				where += buildWhere(tables.get(i));
			}
		}
		
		return from + " " + where;
	}
	
	// add primary key joins
	private String buildWhere( String table )
	{
		if( table.charAt(table.length()-1) == 'c' )
		{
			return "f.pkID_crop = c.pkID ";
		}
		else if( table.charAt(table.length()-1) == 'h' )
		{
			return "f.pkID_hh = h.pkID ";
		}
		else if( table.charAt(table.length()-1) == 'l' )
		{
			return "f.pkID_landparcel = l.pkID ";
		}
		else
		{
			return "";
		}
	}
}
