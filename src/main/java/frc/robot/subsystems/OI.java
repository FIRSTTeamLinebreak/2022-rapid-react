package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Robot;
import frc.robot.commands.NonStationaryVerticalMove;
import frc.robot.commands.PrepForSwitchToClimb;
import frc.robot.commands.StationaryVerticalMove;

/**
 * The class responsible for handle user I/O
 */
public class OI extends SubsystemBase {

	// Define the signleton 
	private static OI instance;
	public static OI getInstance() {
		if (instance == null) {
			instance = new OI();
		}
		return instance;
	}

	private BallHandler ballHandlerSub;

	private Joystick driveJoystick;
	private XboxController climbingController;

	/**
	 * define the joystick and bind all of the buttons
	 */
	public OI() {
		ballHandlerSub = BallHandler.getInstance();

		driveJoystick = new Joystick(0);
		climbingController = new XboxController(1);

		// driveJoystick button maps
		new JoystickButton(driveJoystick, 11).whenPressed(ballHandlerSub::toggleIntake, ballHandlerSub); // Toggles the launcher
		new JoystickButton(driveJoystick, 12).whenPressed(ballHandlerSub::toggleFlywheel, ballHandlerSub); // Toggles the flywheels for throwing

		// climbingController button maps
		new JoystickButton(climbingController, 3).whenHeld(new StationaryVerticalMove(true)); // When held move the stationary vertical up
		new JoystickButton(climbingController, 1).whenHeld(new StationaryVerticalMove(false)); // When held move the stationary vertical down
		new JoystickButton(climbingController, 4).whenHeld(new NonStationaryVerticalMove(true)); // When held move the non stationary vertical up
		new JoystickButton(climbingController, 2).whenHeld(new NonStationaryVerticalMove(false)); // When held move the non stationary vertical down
		new JoystickButton(climbingController, 8).whenHeld(new PrepForSwitchToClimb()); // Results in the bot being barely off the ground
	}

	/**
	 * get one of the joystick axis
	 * 
	 * @param axis the id of the target axis
	 * @return the current value of the axis (-1,1)
	 */
	public double getAxis(int axis) {
		return driveJoystick.getRawAxis(axis);
	}

  /**
   * Resets this subsystem to default init status
   */
  public void init() {
      SmartDashboard.putNumber("Speed Multiplier", 0);
  }

	@Override
	public void periodic() {
		if (Robot.getInstance().isTeleopEnabled()) {
			double mult = Math.abs((driveJoystick.getRawAxis(3) - 1) / 2); // Get the speed multiplier

			// put the speed multiplier out to the suffleboard
			SmartDashboard.putNumber("Speed Multiplier", mult);
		}
	}
}
