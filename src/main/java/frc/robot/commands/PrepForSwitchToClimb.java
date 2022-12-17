package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climbing;

/**
 * Creates a new PrepForSwitchToClimb.
 */
public class PrepForSwitchToClimb extends CommandBase {

    private Climbing climbingSub;
    private boolean hasToppedOut = false; // Weather the stationaryVertical hooks are fully extended
    private double targetFinalPos = 1.0; // @TODO: tune this value

    public PrepForSwitchToClimb() {
        climbingSub = Climbing.getInstance();
        addRequirements(climbingSub);
    }

    /**
     * Runs by periodic
     */
    public void execute() {
        climbingSub.setStationaryVerticalPos(targetFinalPos);

    }

    public void end(boolean interrupted) {
        climbingSub.setStationaryVerticalPos(climbingSub.getStationaryVerticalRotations()-0.1);
    }

    /**
     * Returns weather the bot has reached out, grabbed onto the rungs and pulled it's self just barely off the ground
     */
    @Override
    public boolean isFinished() {
        return false;
    }
}
