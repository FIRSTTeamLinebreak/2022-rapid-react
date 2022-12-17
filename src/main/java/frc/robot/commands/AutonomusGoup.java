package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * The Command group where we schedule commands to run during the 
 * autonomous period.
 */
public class AutonomusGoup extends SequentialCommandGroup {

	// Define the signleton 
	private static AutonomusGoup instance;
	public static AutonomusGoup getInstance() {
		if (instance == null) {
			instance = new AutonomusGoup();
		}
		return instance;
	}

	private AutonomusGoup() {
		// may want to figure out some way of drive an amount rather than a time and using a conditional command
		addCommands(
				// List of commands to run during autonomus
				// Ex: `new DriveCommand(0, 1, 0).withTimeout(1),` will drive forward for one second
				// Starting in the tarmac, positioned to our choosing
				new LaunchCommand(true), // Launch
				new BlankCommand().withTimeout(.5), // Wait
				new DriveCommand(0, -.5, 0).withTimeout(2), // Back out
				new LaunchCommand(false), // Stop stuff
				new DriveCommand(0, 0, 0) // REQUIRED: Force the robot to stop when there are no more auto commands to run			
		);
	}
}
