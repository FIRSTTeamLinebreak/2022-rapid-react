package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.AutonomusGoup;
import frc.robot.subsystems.BallHandler;
import frc.robot.subsystems.Climbing;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.OI;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

	// Define the signleton 
	private static Robot instance;
	public static Robot getInstance() {
		if (instance == null) {
			instance = new Robot();
		}
		return instance;
	}

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		//make sure the Drive subsytem is initted
		Drive.getInstance().setAcceration(0.5);

		BallHandler.getInstance().init();
		Climbing.getInstance().init();
		Drive.getInstance().init();
		OI.getInstance().init();
	}

	/**
	 * This function is called every robot packet, no matter the mode. Use this for
	 * items like diagnostics that you want ran during disabled, autonomous,
	 * teleoperated and test.
	 *
	 * <p>
	 * This runs after the mode specific periodic functions, but before LiveWindow
	 * and SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic() {
		// handles the scheduled commands
		CommandScheduler.getInstance().run();
	}

	/** This function is called once each time the robot enters Disabled mode. */
	@Override
	public void disabledInit() {}

	@Override
	public void disabledPeriodic() {}

	private Command m_autonomousCommand; // the auto command group

	/**
	 * Schedule the autonomus commands
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = AutonomusGoup.getInstance();
		m_autonomousCommand.schedule();
	}

	/** This function is called periodically during autonomous. */
	@Override
	public void autonomousPeriodic() {}

	@Override
	public void teleopInit() {
		// Cancel all autonomus commands when tellop starts
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}

		// Make sure the OI is initted
		OI.getInstance();
	}

	/** This function is called periodically during operator control. */
	@Override
	public void teleopPeriodic() {}

	@Override
	public void testInit() {
		// Cancels all running commands at the start of test mode.
		CommandScheduler.getInstance().cancelAll();
		Climbing.getInstance().setEncoderZero();
	}

	/** This function is called periodically during test mode. */
	@Override
	public void testPeriodic() {}
}
