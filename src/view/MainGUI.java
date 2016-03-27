package view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.Controller;
import javax.swing.JTable;
import javax.swing.LookAndFeel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;

public class MainGUI extends JFrame implements ActionListener, ChangeListener, ItemListener
{
	private Controller controller;
	
	private DefaultTableModel defaultTableModel;
	
	private JButton buttonExecute;
	private JCheckBox ckboxHouseholdId, ckboxCropId, ckboxLandParcelId, ckboxCropLine, ckboxCropType, ckboxCropTypeOthers, ckboxCropVolume,  
	                  ckboxLineNumber, ckboxALTenure, ckboxOtherALTenure, chckbxFarmArea, ckboxLandOwned, ckboxCropIndustry, ckboxCropInCash,
	                  ckboxCropInKind, ckboxYearsInCI, ckboxHarvestAmount, ckboxHDReason, ckboxOtherHDReason, ckboxCropChange, ckboxCCReason, 
	                  ckboxOtherCCReason;
	private JLabel labelRD, labelColumnWidth, labelFactTable, labelCrop, labelLandParcel, lblHousehold, labelSD, labelRowsReturned;
	private JPanel jpanel;
	private JScrollPane tableScrollPane;
	private JSpinner spinnerColumnWidth;
	private JTable table;
	
	private ArrayList<String> ruddAttributes;
	private ArrayList<String> tables;
	
	private int crop = 0, household = 0, landparcel = 0;
	
	public MainGUI( Controller controller )
	{
		try
		{
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
					UIDefaults defaults = lookAndFeel.getDefaults();
					defaults.put("ScrollBar.minimumThumbSize", new Dimension(30, 30));
					break;
				}
			}
		}
		catch (Exception e)
		{
			// If Nimbus is not available, you can set the GUI to another
			// look and feel.
		}
		
		this.controller = controller;
		
		ruddAttributes = new ArrayList<String>(0);
		tables = new ArrayList<String>(0);
		
		ruddAttributes.add("f.pkID_hh");
		ruddAttributes.add("f.pkID_crop");
		ruddAttributes.add("f.pkID_landparcel");
		
		jpanel = new JPanel();
		jpanel.setSize(1300, 700);
		jpanel.setLayout(null);
		
		labelRD = new JLabel("Roll-Up / Drill-Down");
		labelRD.setFont(new Font("Segoe UI", Font.BOLD, 15));
		labelRD.setBounds(10, 12, 170, 23);
		jpanel.add(labelRD);
		
		labelColumnWidth = new JLabel("Set Column Width");
		labelColumnWidth.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		labelColumnWidth.setBounds(220, 16, 109, 14);
		jpanel.add(labelColumnWidth);
		
		spinnerColumnWidth = new JSpinner();
		spinnerColumnWidth.setBounds(330, 12, 60, 25);
		spinnerColumnWidth.setValue(172);
		spinnerColumnWidth.addChangeListener(this);
		jpanel.add(spinnerColumnWidth);
		
		labelFactTable = new JLabel("Fact Table");
		labelFactTable.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		labelFactTable.setBounds(10, 46, 65, 14);
		jpanel.add(labelFactTable);
		
		ckboxHouseholdId = new JCheckBox("Household ID");
		ckboxHouseholdId.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxHouseholdId.setBounds(10, 67, 97, 23);
		ckboxHouseholdId.setSelected(true);
		ckboxHouseholdId.setActionCommand("f.pkID_hh");
		ckboxHouseholdId.addItemListener(this);
		jpanel.add(ckboxHouseholdId);
		
		ckboxCropId = new JCheckBox("Crop ID");
		ckboxCropId.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxCropId.setBounds(10, 92, 97, 23);
		ckboxCropId.setSelected(true);
		ckboxCropId.setActionCommand("f.pkID_crop");
		ckboxCropId.addItemListener(this);
		jpanel.add(ckboxCropId);
		
		ckboxLandParcelId = new JCheckBox("Land Parcel ID");
		ckboxLandParcelId.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxLandParcelId.setBounds(10, 118, 97, 23);
		ckboxLandParcelId.setSelected(true);
		ckboxLandParcelId.setActionCommand("f.pkID_landparcel");
		ckboxLandParcelId.addItemListener(this);
		jpanel.add(ckboxLandParcelId);
		
		labelCrop = new JLabel("Crop");
		labelCrop.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		labelCrop.setBounds(10, 148, 89, 14);
		jpanel.add(labelCrop);
		
		ckboxCropLine = new JCheckBox("Crop Line");
		ckboxCropLine.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxCropLine.setBounds(10, 169, 97, 23);
		ckboxCropLine.setActionCommand("c.crop_line");
		ckboxCropLine.addItemListener(this);
		jpanel.add(ckboxCropLine);
		
		ckboxCropType = new JCheckBox("Crop Type");
		ckboxCropType.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxCropType.setBounds(10, 195, 97, 23);
		ckboxCropType.setActionCommand("c.croptype");
		ckboxCropType.addItemListener(this);
		jpanel.add(ckboxCropType);
		
		ckboxCropTypeOthers = new JCheckBox("Other Crop Types");
		ckboxCropTypeOthers.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxCropTypeOthers.setBounds(10, 221, 120, 23);
		ckboxCropTypeOthers.setActionCommand("c.croptype_o");
		ckboxCropTypeOthers.addItemListener(this);
		jpanel.add(ckboxCropTypeOthers);
		
		ckboxCropVolume = new JCheckBox("Crop Volume");
		ckboxCropVolume.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxCropVolume.setBounds(10, 247, 97, 23);
		ckboxCropVolume.setActionCommand("c.crop_vol");
		ckboxCropVolume.addItemListener(this);
		jpanel.add(ckboxCropVolume);
		
		labelLandParcel = new JLabel("Land Parcel");
		labelLandParcel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		labelLandParcel.setBounds(10, 277, 89, 14);
		jpanel.add(labelLandParcel);
		
		ckboxLineNumber = new JCheckBox("Line Number");
		ckboxLineNumber.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxLineNumber.setBounds(10, 298, 97, 23);
		ckboxLineNumber.setActionCommand("l.alp_line");
		ckboxLineNumber.addItemListener(this);
		jpanel.add(ckboxLineNumber);
		
		ckboxALTenure = new JCheckBox("Agricultural Land Tenure");
		ckboxALTenure.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxALTenure.setBounds(10, 324, 170, 23);
		ckboxALTenure.setActionCommand("l.alp_tenur");
		ckboxALTenure.addItemListener(this);
		jpanel.add(ckboxALTenure);
		
		ckboxOtherALTenure = new JCheckBox("Other Agricultural Land Tenure");
		ckboxOtherALTenure.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		ckboxOtherALTenure.setBounds(10, 350, 170, 23);
		ckboxOtherALTenure.setActionCommand("l.alp_tenur_o");
		ckboxOtherALTenure.addItemListener(this);
		jpanel.add(ckboxOtherALTenure);
		
		chckbxFarmArea = new JCheckBox("Farm Area");
		chckbxFarmArea.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		chckbxFarmArea.setBounds(10, 376, 97, 23);
		chckbxFarmArea.setActionCommand("l.alp_area");
		chckbxFarmArea.addItemListener(this);
		jpanel.add(chckbxFarmArea);
		
		lblHousehold = new JLabel("Household");
		lblHousehold.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblHousehold.setBounds(220, 45, 65, 14);
		jpanel.add(lblHousehold);
		
		ckboxLandOwned = new JCheckBox("Land Owned");
		ckboxLandOwned.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxLandOwned.setBounds(220, 66, 97, 23);
		ckboxLandOwned.setActionCommand("h.landagri");
		ckboxLandOwned.addItemListener(this);
		jpanel.add(ckboxLandOwned);
		
		ckboxCropIndustry = new JCheckBox("Crop Industry");
		ckboxCropIndustry.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxCropIndustry.setBounds(220, 91, 97, 23);
		ckboxCropIndustry.setActionCommand("h.cropind");
		ckboxCropIndustry.addItemListener(this);
		jpanel.add(ckboxCropIndustry);
		
		ckboxCropInCash = new JCheckBox("Crop in Cash");
		ckboxCropInCash.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxCropInCash.setBounds(220, 117, 97, 23);
		ckboxCropInCash.setActionCommand("h.cropincsh");
		ckboxCropInCash.addItemListener(this);
		jpanel.add(ckboxCropInCash);
		
		ckboxCropInKind = new JCheckBox("Crop in Kind");
		ckboxCropInKind.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxCropInKind.setBounds(220, 142, 97, 23);
		ckboxCropInKind.setActionCommand("h.cropinknd");
		ckboxCropInKind.addItemListener(this);
		jpanel.add(ckboxCropInKind);
		
		ckboxYearsInCI = new JCheckBox("Years in Crop Industry");
		ckboxYearsInCI.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxYearsInCI.setBounds(220, 169, 150, 23);
		ckboxYearsInCI.setActionCommand("h.yrs_in_cropind");
		ckboxYearsInCI.addItemListener(this);
		jpanel.add(ckboxYearsInCI);
		
		ckboxHarvestAmount = new JCheckBox("Harvest Amount");
		ckboxHarvestAmount.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxHarvestAmount.setBounds(220, 195, 116, 23);
		ckboxHarvestAmount.setActionCommand("h.u_amt_harv");
		ckboxHarvestAmount.addItemListener(this);
		jpanel.add(ckboxHarvestAmount);
		
		ckboxHDReason = new JCheckBox("Harvest Decrease Reason");
		ckboxHDReason.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxHDReason.setBounds(220, 220, 160, 23);
		ckboxHDReason.setActionCommand("h.u_low_harv");
		ckboxHDReason.addItemListener(this);
		jpanel.add(ckboxHDReason);
		
		ckboxOtherHDReason = new JCheckBox("Other Harvest Decrease Reason");
		ckboxOtherHDReason.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		ckboxOtherHDReason.setBounds(220, 246, 170, 23);
		ckboxOtherHDReason.setActionCommand("h.u_low_harv_o_lb");
		ckboxOtherHDReason.addItemListener(this);
		jpanel.add(ckboxOtherHDReason);
		
		ckboxCropChange = new JCheckBox("Crop Change");
		ckboxCropChange.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxCropChange.setBounds(220, 271, 97, 23);
		ckboxCropChange.setActionCommand("h.u_chng_pcrop");
		ckboxCropChange.addItemListener(this);
		jpanel.add(ckboxCropChange);
		
		ckboxCCReason = new JCheckBox("Crop Change Reason");
		ckboxCCReason.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		ckboxCCReason.setBounds(220, 298, 150, 23);
		ckboxCCReason.setActionCommand("h.u_chng_pcrop_y");
		ckboxCCReason.addItemListener(this);
		jpanel.add(ckboxCCReason);
		
		ckboxOtherCCReason = new JCheckBox("Other Crop Change Reason");
		ckboxOtherCCReason.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		ckboxOtherCCReason.setBounds(220, 324, 160, 23);
		ckboxOtherCCReason.setActionCommand("h.u_chng_pcrop_y_o_lb");
		ckboxOtherCCReason.addItemListener(this);
		jpanel.add(ckboxOtherCCReason);
		
		labelSD = new JLabel("Slice / Dice");
		labelSD.setFont(new Font("Segoe UI", Font.BOLD, 15));
		labelSD.setBounds(10, 407, 150, 23);
		jpanel.add(labelSD);
		
		buttonExecute = new JButton("Execute");
		buttonExecute.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		buttonExecute.setBounds(10, 635, 89, 25);
		buttonExecute.addActionListener(this);
		jpanel.add(buttonExecute);
		
		labelRowsReturned = new JLabel("Rows returned: ");
		labelRowsReturned.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		labelRowsReturned.setBounds(109, 637, 231, 18);
		jpanel.add(labelRowsReturned);
		
		defaultTableModel = new DefaultTableModel();
		table = new JTable(defaultTableModel);
		tableScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tableScrollPane.setBounds(400, 11, 884, 649);
		jpanel.add(tableScrollPane);
		
		this.setTitle("ADVANDB MCO2");
		this.setSize(1300, 700);
		this.setContentPane(jpanel);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	private void populateTable(ResultSet rs)
	{
		ResultSetMetaData rsdm = null;
		int colCount = 0;
		
		// get ResultMetaData to get column header
		// get column count
		try
		{
			rsdm = rs.getMetaData();
			colCount = rsdm.getColumnCount();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// get column headers
		String[] columns = new String[colCount];
		for( int i = 0; i < colCount; i++ )
		{
			try
			{
				columns[i] = rsdm.getColumnLabel(i+1);
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// get table data
		ArrayList<Object[]> rows = new ArrayList<Object[]>(0);
		
		try
		{
			while(rs.next())
			{
				Object[] rowData = new Object[colCount];
				for( int i = 0; i < colCount; i++ )
				{
					rowData[i] = rs.getObject(i+1);
				}
				rows.add(rowData);
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		defaultTableModel = new DefaultTableModel(arraylistToObjectArray(rows), columns);
		table.setModel(defaultTableModel);
		
		if( colCount <= 5 )
		{
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
		else
		{
			updateTableColumnWidth();
		}
		
		labelRowsReturned.setText("Rows returned: " + rows.size());
	}

	// convert ArrayList<Object[]> to Object[][]
	private Object[][] arraylistToObjectArray( ArrayList<Object[]> list )
	{
		Object[][] newList = new Object[list.size()][];
		for( int i = 0; i < list.size(); i++ )
		{
			newList[i] = list.get(i);
		}
		
		return newList;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		if( e.getSource() == buttonExecute )
		{
			//populateTable(controller.getDimensionCrop());
			//populateTable(controller.getDimensionLandParcel());
			//populateTable(controller.getDimensionHousehold());
			//populateTable(controller.getFactTable());
			populateTable(controller.getData(ruddAttributes, tables));
		}
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		// TODO Auto-generated method stub
		updateTableColumnWidth();
	}
	
	private void updateTableColumnWidth()
	{
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		int width = (int) spinnerColumnWidth.getValue();
		
		for( int i = 0; i < table.getColumnModel().getColumnCount(); i++ )
		{	
			table.getColumnModel().getColumn(i).setMinWidth(width);
			table.getColumnModel().getColumn(i).setMaxWidth(width);
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		// TODO Auto-generated method stub
		if( e.getStateChange() == ItemEvent.SELECTED )
		{
			ruddAttributes.add(((AbstractButton) e.getItem()).getActionCommand());
			checkboxChecker(e, true);
			//System.out.println("Added - " + ((AbstractButton) e.getItem()).getActionCommand());
		}
		else if( e.getStateChange() == ItemEvent.DESELECTED )
		{
			ruddAttributes.remove(((AbstractButton) e.getItem()).getActionCommand());
			checkboxChecker(e, false);
			//System.out.println("Removed - " + ((AbstractButton) e.getItem()).getActionCommand());
		}
	}
	
	public void checkboxChecker( ItemEvent e, boolean add )
	{
		if( e.getSource() == ckboxCropLine || e.getSource() == ckboxCropType || e.getSource() == ckboxCropTypeOthers || e.getSource() == ckboxCropVolume )
		{
			if( add )
			{
				if( !tables.contains("dim_crop c") )
				{
					tables.add("dim_crop c");
				}
				crop++;
			}
			else
			{
				crop--;
			}
		}
		else if( e.getSource() == ckboxLineNumber || e.getSource() == ckboxALTenure || e.getSource() == ckboxOtherALTenure || e.getSource() == chckbxFarmArea )
		{
			if( add )
			{
				if( !tables.contains("dimlandparcel l") )
				{
					tables.add("dimlandparcel l");
				}
				landparcel++;
			}
			else
			{
				landparcel--;
			}
		}
		else if( e.getSource() == ckboxCropId || e.getSource() == ckboxHouseholdId || e.getSource() == ckboxLandParcelId )
		{
			
		}
		else
		{
			if( add )
			{
				if( !tables.contains("dim_household h") )
				{
					tables.add("dim_household h");
				}
				household++;
			}
			else
			{
				household--;
			}
		}
		
		if( crop == 0 )
		{
			tables.remove("dim_crop c");
		}
		
		if( landparcel == 0 )
		{
			tables.remove("dimlandparcel l");
		}
		
		if( household == 0 )
		{
			tables.remove("dim_household h");
		}
		
		/*for(int i = 0; i < tables.size(); i++ )
		{
			System.out.println(tables.get(i));
		}
		System.out.println();*/
	}
}
