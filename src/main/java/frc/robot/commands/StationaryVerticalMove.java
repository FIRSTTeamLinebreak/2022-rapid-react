package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Utils;
import frc.robot.subsystems.Climbing;

public class StationaryVerticalMove extends CommandBase {

    private boolean moveUp;
    private Climbing climbingSub;

    /**
     * Moves the stationary vertical arms either up or down
     * 
     * @param moveUp Weather we should move up
     */
    public StationaryVerticalMove(boolean moveUp) {
        this.moveUp = moveUp;
        this.climbingSub = Climbing.getInstance();
    }

    /**
     * Called every time this command is run
     */
    public void execute() {
        climbingSub.setStationaryVerticalPos(moveUp ? 1 : 0.1);
    }

    /**
     * Returns false because the execute method needs to be run while the button is
     * held down
     */
    public boolean isFinished() {
        return false;
    }

    /**
     * Called when the button for moving the stationary vertical arms is released
     */
    @Override
    public void end(boolean interrupted) {
        climbingSub.setStationaryVerticalPos(Utils.clamp(climbingSub.getStationaryVerticalRotations() + (moveUp ? 1 : -1) * 0.15, 0.1, 1));
    }

}