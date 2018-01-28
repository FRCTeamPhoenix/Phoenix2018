package org.usfirst.frc.team2342.json;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {
	public static void WriteConfigMethod() throws Exception {
		ObjectMapper objMapper = new ObjectMapper();
		Json config = new Json();
		config.talons = new ArrayList<Talon>();
		
		Talon talon1 = new Talon();
		talon1.inverted = false;
		talon1.maxForwardSpeed = 1.2d;
		PIDGains velocityGain = new PIDGains();
		velocityGain.d = 1.7d;
		velocityGain.ff = 2.1d;
		talon1.velocityGains = velocityGain;
		config.talons.add(talon1);
		String outputJson = objMapper.writeValueAsString(config);
		System.out.println(outputJson);
		Files.write(Paths.get("./config.json"), outputJson.getBytes(), StandardOpenOption.CREATE);
	}
	
	public static Json readConfigMethod() throws Exception {
		byte[] jsonBytes = Files.readAllBytes(Paths.get("./config.json"));
		String jsonString = new String(jsonBytes, "UTF-8");
		ObjectMapper objMapper = new ObjectMapper();
		return objMapper.readValue(jsonString, Json.class);
	}
}
