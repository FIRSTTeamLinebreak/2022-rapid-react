package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climbing;
import frc.robot.subsystems.OI;

public class NonStationaryVerticalMove extends CommandBase {

    private boolean moveUp;
    private Climbing climbingSub;

    /**
     * Moves the non stationary vertical arms either up or down
     * 
     * @param moveUp Weather we should move up
     */
    public NonStationaryVerticalMove(boolean moveUp) {
        this.moveUp = moveUp;
        this.climbingSub = Climbing.getInstance();
    }

    /**
     * Called every time this command is run
     */
    public void execute() {
        // climbingSub.setNonStationaryVerticalPos(climbingSub.getNonStationaryVerticalPos() + (moveUp ? .5 : -.5));
    }

    /**
     * Returns false because the execute method needs to be run while the button is held down
     */
    public boolean isFinished() {
        return false;
    }

    /**
     * Called when the button for moving the non stationary vertical arms is released
     */
    @Override
    public void end(boolean interrupted) {
        // climbingSub.setNonStationaryVerticalPos(climbingSub.getNonStationaryVerticalPos());
    }
}