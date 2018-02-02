//package org.usfirst.frc.team2342.json;
//
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//import java.util.ArrayList;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class JsonHelper {
//	public static void WriteConfigMethod() throws Exception {
//		ObjectMapper objMapper = new ObjectMapper();
//		Json config = new Json();
//		config.talons = new ArrayList<Talon>();
//		
//		Talon talon1 = new Talon();
//		talon1.inverted = false;
//		talon1.maxForwardSpeed = 1.2d;
//		PIDGains velocityGain = new PIDGains();
//		velocityGain.d = 1.7d;
//		velocityGain.ff = 2.1d;
//		talon1.velocityGains = velocityGain;
//		config.talons.add(talon1);
//		config.actions = new ArrayList<Action>();
//		Action action1 = new Action();
//		action1.Name = "MoveForward";
//		action1.Type = "DriveAction";
//		action1.Parameters = new ArrayList<Parameter>();
//		Parameter parameter1 = new Parameter();
//		parameter1.Name = "distance";
//		parameter1.Value = "0";
//		action1.Parameters.add(parameter1);
//		Parameter parameter2 = new Parameter();
//		parameter2.Name = "angle";
//		parameter2.Value = "0";
//		action1.Parameters.add(parameter2);
//		config.actions.add(action1);
//		String outputJson = objMapper.writeValueAsString(config);
//		System.out.println(outputJson);
//		Files.write(Paths.get("./config.json"), outputJson.getBytes(), StandardOpenOption.CREATE);
//	}
//	
//	public static Json readConfigMethod() throws Exception {
//		byte[] jsonBytes = Files.readAllBytes(Paths.get("./config.json"));
//		String jsonString = new String(jsonBytes, "UTF-8");
//		ObjectMapper objMapper = new ObjectMapper();
//		return objMapper.readValue(jsonString, Json.class);
//	}
//	public static String getParameterValueByName(ArrayList<Parameter> parameters, String name) {
//		for (Parameter parameter : parameters) {
//			if (parameter.Name.equals(name)) {
//				return parameter.Value;
//			}
//		}
//		return null;
//	}
//}
