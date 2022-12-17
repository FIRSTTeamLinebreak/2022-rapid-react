package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * a null command that does nothing
 */
public class BlankCommand extends CommandBase {
  
  /** 
   * Creates a new blankCommand. 
  */
  public BlankCommand() {}

  /**
   * Returns true when the command should end.
   */
  @Override
  public boolean isFinished() {
    return false;
  }
}
