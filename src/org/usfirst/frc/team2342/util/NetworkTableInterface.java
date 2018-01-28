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
	public static void setValue(String subtablePath, String varName, boolean value){
		String[] tables = splitString(subtablePath);
		NetworkTable table = NetworkTable.getTable(tables[0]);
		ITable subtable = null;
		if(tables.length > 1)
			subtable = table.getSubTable(tables[1]);
		for(int i = 2; i < tables.length; i++){
			subtable = subtable.getSubTable(tables[i]);
		}
		if(tables.length == 1){
			table.putBoolean(varName, value);
		}else{
			subtable.putBoolean(varName, value);
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
	
	@SuppressWarnings("deprecation")
	public static boolean getBool(String subtablePath, String varName){
		String[] tables = splitString(subtablePath);
		NetworkTable table = NetworkTable.getTable(tables[0]);
		ITable subtable = null;
		if(tables.length > 1)
			subtable = table.getSubTable(tables[1]);
		for(int i = 2; i < tables.length; i++){
			subtable = subtable.getSubTable(tables[i]);
		}
		if(tables.length == 1){
			return table.getBoolean(varName, false);
		}else{
			return subtable.getBoolean(varName, false);
		}
	}
	
	public static void setTalon(String talonTableLocation, int id, double p, double i, double d, double maxSpeed, double ff, double rr, boolean inverted, String desc){
		String talonLocation = talonTableLocation+"/talon["+id+"]";
		NetworkTableInterface.setValue(talonLocation, "p", p);
		NetworkTableInterface.setValue(talonLocation, "i", i);
		NetworkTableInterface.setValue(talonLocation, "d", d);
		NetworkTableInterface.setValue(talonLocation, "maxSpeed", maxSpeed);
		NetworkTableInterface.setValue(talonLocation, "ff", ff);
		NetworkTableInterface.setValue(talonLocation, "rr", rr);
		
		NetworkTableInterface.setValue(talonLocation, "inverted", inverted);
		
		NetworkTableInterface.setValue(talonLocation, "Desc", desc);
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
