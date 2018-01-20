import java.util.*;

public class ActionList{
	
	private ArrayList<Action> actions;
	
	private boolean emergancyStop = false;
	
	public ActionList(){
		
	}
	
	//Execute all actions who's conditions have been satisfied
	public void execute(){
		
		for(Action action: actions){
			
			if(!emergancyStop)
				action.run();
			
			else
				action.stop();
		}
	}
	
	public void stop(){
		
		emergancyStop = true;
	}
	
	public void start(){
		
		emergancyStop = false;
	}
	
	
}