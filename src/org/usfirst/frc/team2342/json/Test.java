package org.usfirst.frc.team2342.json;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//JsonHelper.WriteConfigMethod();
			Json config = JsonHelper.getConfig();
			System.out.println(config.talons.get(0).maxForwardSpeed);
		} catch(Exception e) {e.printStackTrace();}
	}
}
