package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallHandler;

public class LaunchCommand extends CommandBase {
  
  private BallHandler ballHandlerSub;

  /**
   * Creates a command that launches a loaded cargo during autonomous
   */
  public LaunchCommand(boolean start) {
    ballHandlerSub = BallHandler.getInstance();

    // ballHandlerSub.setBeltSpeed(start ? 1.0 : 0); // Turns on the belt
    // ballHandlerSub.toggleFlywheel(); // Turns the launcher on, launching the cargo
  }

  /**
   * Returns weather this command is done
   */
  @Override
  public boolean isFinished() {
    return true;
  }

}
