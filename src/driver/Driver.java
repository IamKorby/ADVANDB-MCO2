package driver;

import controller.Controller;
import view.MainGUI;

public class Driver
{
	public static void main(String[] args)
	{
		Controller controller = new Controller();
		MainGUI mainGUI = new MainGUI(controller);
	}
}
