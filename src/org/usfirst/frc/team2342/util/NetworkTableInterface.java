package org.usfirst.frc.team2342.util;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;

public class NetworkTableInterface {
	
	//use slashes to seperate subtables sort of like a file system
	
	@SuppressWarnings("deprecation")
	public static void setValue(String subtablePath, String varName, double value){
		String[] tables = splitString(subtablePath);
		NetworkTable table = NetworkTable.getTable(tables[0]);
		ITable subtable = null;
		if(tables.length > 1)
			subtable = table.getSubTable(tables[1]);
		for(int i = 2; i < tables.length; i++){
			subtable = subtable.getSubTable(tables[i]);
		}
		if(tables.length == 1){
			table.putDouble(varName, value);
		}else{
			subtable.putDouble(varName, value);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void setValue(String subtablePath, String varName, String value){
		String[] tables = splitString(subtablePath);
		NetworkTable table = NetworkTable.getTable(tables[0]);
		ITable subtable = null;
		if(tables.length > 1)
			subtable = table.getSubTable(tables[1]);
		for(int i = 2; i < tables.length; i++){
			subtable = subtable.getSubTable(tables[i]);
		}
		if(tables.length == 1){
			table.putString(varName, value);
		}else{
			subtable.putString(varName, value);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static double getDouble(String subtablePath, String varName){
		String[] tables = splitString(subtablePath);
		NetworkTable table = NetworkTable.getTable(tables[0]);
		ITable subtable = null;
		if(tables.length > 1)
			subtable = table.getSubTable(tables[1]);
		for(int i = 2; i < tables.length; i++){
			subtable = subtable.getSubTable(tables[i]);
		}
		if(tables.length == 1){
			return table.getDouble(varName, 0.0);
		}else{
			return subtable.getDouble(varName, 0.0);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static String getString(String subtablePath, String varName){
		String[] tables = splitString(subtablePath);
		NetworkTable table = NetworkTable.getTable(tables[0]);
		ITable subtable = null;
		if(tables.length > 1)
			subtable = table.getSubTable(tables[1]);
		for(int i = 2; i < tables.length; i++){
			subtable = subtable.getSubTable(tables[i]);
		}
		if(tables.length == 1){
			return table.getString(varName, "NULL");
		}else{
			return subtable.getString(varName, "NULL");
		}
	}
	
	private static String[] splitString(String target){
		String[] tables = {"one"};
		if(target.contains("/")){
			tables = target.split("/");
		}else if(target.contains("\\")){
			//have to use four slashes for some reason
			tables = target.split("\\\\");
		}else{
			tables[0] = target;
		}
		return tables;
	}
	
}
