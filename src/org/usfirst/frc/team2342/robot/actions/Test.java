import java.util.ArrayList;

public class Test{
	
	public void main(String[] args){
		
		ArrayList<Action> actions = new ArrayList<Action>();
		
		ArrayList<String> dependence = new ArrayList<String>();
		
		actions.add(new DriveAction(10, 9, "tagYourItBud", dependence));
		
		ActionList actionsList = new ActionList(actions);
		
		actionsList.execute();
		
		
	}
	
}