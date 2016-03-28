package model;

import java.util.ArrayList;

public class QueryBuilder
{
	private String measure = " AVG(f.cropvol_per_land) ";
	
	public String buildQuery( ArrayList<String> ruddAttributes, ArrayList<String> sdAttributes, ArrayList<String> tables )
	{
		String queryAttributes = buildSelectGroupBy(ruddAttributes);
		String select = "SELECT ";
		String fromwhere = buildFromWhere(sdAttributes, tables);
		String groupby = "";
		
		// if there are selected attributes, add them to select and groupby
		// add measure to select
		if( ruddAttributes.size() != 0 )
		{
			select += queryAttributes + ", " + measure;
			groupby = "GROUP BY " + queryAttributes;
		}
		else
		{
			select += measure;
		}
		
		return select + fromwhere + groupby + ";";
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
	private String buildFromWhere( ArrayList<String> sdAttributes, ArrayList<String> tables )
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
				where += buildWhereKeyJoin(tables.get(i)) + "AND ";
			}
			else
			{
				from += tables.get(i);
				where += buildWhereKeyJoin(tables.get(i));
			}
		}
		
		for( int i = 0; i < sdAttributes.size(); i++ )
		{
			if( i == 0 && tables.size() != 0 )
			{
				where += "AND " + sdAttributes.get(i) + " ";
			}
			else if( i == 0 && tables.size() == 0 )
			{
				where += sdAttributes.get(i) + " ";
			}
			else
			{
				where += "AND " + sdAttributes.get(i) + " ";
			}
		}
		
		return from + " " + where;
	}
	
	// add primary key joins
	private String buildWhereKeyJoin( String table )
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
