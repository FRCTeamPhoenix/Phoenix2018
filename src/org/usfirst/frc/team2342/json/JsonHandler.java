package org.usfirst.frc.team2342.json;

import java.io.File;

import java.io.IOException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

//*********************************************************************************************
// The JSONHandler class essentially handles the JSON file. It initializes a new file to hold *
// JSON values, and does other basic methods onto the file, such as writing to, or reading    *
// from it.                                                                                   *
// ********************************************************************************************
public class JsonHandler 
{
	private String m_fname;
	private File m_fileHandler;
	private ObjectMapper m_mapper = new ObjectMapper();
	private boolean m_fExists;

	//****************************************************************************************
	// This is the constructor. It initializes the file, makes sure the objects in the       *
	// JSON are OK, and checks if file exists.                                               *
	//****************************************************************************************
	public JsonHandler(String fname) 
	{
		m_mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.m_fname = fname;
		this.m_fileHandler = new File(fname);
		this.m_fExists = m_fileHandler.exists();

		if (m_fExists == true) 
		{
			System.out.println("File exists, we are good to go.");
		}
		else 
		{
			System.out.println("This file does not exists.");
			System.out.println("If you want to next time use the write function to create a json file.");
		}
	}


	//***********************************************************************
	// This is the getFname method. It returns the name of the file.        *
	//***********************************************************************
	public String getFname() 
	{
		return this.m_fname;
	}


	//***********************************************************************
	// This is the setFname method. It returns the name of the file.        *
	//***********************************************************************
	public void setFname(String name) 
	{
		this.m_fname = name;
		this.m_fileHandler = new File(name);
		this.m_fExists = this.m_fileHandler.exists();
	}
	
	public boolean getFileExisting() {
		return this.m_fExists;
	}

	//************************************************************************************
	// This is the write method. It essentially writes values to the file                *
	//************************************************************************************
	public <T> void write(T t) throws JsonGenerationException, JsonMappingException, IOException 
	{
		this.m_fExists = m_fileHandler.exists();
		m_mapper.writeValue(m_fileHandler, t);
	}


	//**************************************************************************************
	// This is the read method. It essentially reads values to the file                    *
	// WARNING: When you try to read values off of the file you must pass in a Class Type  *
	//**************************************************************************************
	public <T> T read(Class<T> pass) throws JsonGenerationException, JsonMappingException, IOException 
	{
		return m_mapper.readValue(m_fileHandler, pass);
	}
	
	// Get the json file and load the contents into the object
	// If Json file does not exists, write the object into a json file.
	public <T> void getJson(String fname, T variable) {
		this.setFname("/home/lvuser/" + fname);
		try {
			if (this.getFileExisting()) {
				variable = this.read((Class<T>) variable);
			}
			else {
				this.write(variable);
			}
		}
		catch (Exception e) {
			//DONOTHING
		}
	}

	// updates the object into the json file
	public <T> void updateJson(String fname, T variable) {
		this.setFname("/home/lvuser/" + fname);
		try {
			this.write(variable);
		}
		catch (Exception e) {
			//DONOTHING
		}
	}
}