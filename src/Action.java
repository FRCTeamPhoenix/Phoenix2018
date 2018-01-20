import org.usfirst.frc.team2342.util.Constants;

public abstract class Action {
	
	//0 = not yet started
	//1 = in progress
	//2 = completed
	private static int currentState;

	private static String name = "";
	
	public Action(String name)
	{
		this.name = name;
		currentState = 0;
	}

	public void start(){}
	
	public void run(){}
	
	public boolean isCompleted()
	{
		return (currentState == 2);
	}
	
	public void stop(){}
	
	public void reset()
	{
		currentState = 0;
	}
}