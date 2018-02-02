//package org.usfirst.frc.team2342.json;
//
//import org.usfirst.frc.team2342.robot.actions.DriveAction;
//
//public class ActionFactory {
//	public static org.usfirst.frc.team2342.robot.actions.Action createAction(Action action) {
//		switch (action.Type)
//		{
//		case "DriveAction":
//			String jDistance = JsonHelper.getParameterValueByName(action.Parameters, "distance");
//			double distance = Double.parseDouble(jDistance);
//			
//			String jAngle = JsonHelper.getParameterValueByName(action.Parameters, "angle");
//			double angle = Double.parseDouble(jAngle);
//			
//			DriveAction driveAction = new DriveAction(distance, angle, action.Name, action.dependencies);
//			return driveAction;
//			default:
//				return null;
//		}
//	}
//}
