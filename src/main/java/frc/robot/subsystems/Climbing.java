package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Utils;
import frc.robot.wrappers.PIDMotorWrapper;

public class Climbing extends SubsystemBase {

    // Define the signleton 
    private static Climbing instance;
    public static Climbing getInstance() {
        if (instance == null) {
            instance = new Climbing();
        }
        return instance;
    }

    private final PIDMotorWrapper stationaryVertical;
    private final PIDMotorWrapper nonStationaryVertical;
    private final PIDMotorWrapper nonStationaryRotate;

    // @TODO: Tune these values to be equvalent to the number of ratations needed on the climbing motors to reach their maximum
    private final double stationaryVerticalMaxRotations = 141.0;
    private final double nonStationaryVerticalMaxRotations = 10.0;
    private final double nonStationaryRotateMaxRotations = 10.0;

    // Target rotational value for the Climbers to move towards
    private double stationaryVerticalTargetPos = 0.0;
    private double nonStationaryRotateTargetPos = 0.0;
    private double nonStationaryVerticalTargetPos = 0.0;

    /**
     * Creates a Climbing
     */
    private Climbing() {
        // Define the motors
        stationaryVertical = new PIDMotorWrapper(7, 8, 5e-5, 1e-6, 0, 0, 0.0002, -1, 1, -2000, 2000, 1500, 0);
        nonStationaryVertical = new PIDMotorWrapper(9, 10, 5e-5, 1e-6, 0, 0, 0.000156, -1, 1, -2000, 2000, 1500, 0);
        nonStationaryRotate = new PIDMotorWrapper(11, 12, 5e-5, 1e-6, 0, 0, 0.000156, -1, 1, -2000, 2000, 1500, 0);
    }

    /**
     * Sets the target position for the "stationary vertical" (The stationary hooks) motors
     * 
     * @param pos (0, 1) 0 for bottom; 1 for max
     */
    public void setStationaryVerticalPos(double pos) {
        stationaryVerticalTargetPos = Utils.clamp(pos, 0, 1);
    }

    /**
     * Sets the target position for the "non-stationary vertical" (The moving hooks) motors
     * 
     * @param pos (0, 1) 0 for bottom; 1 for max
     */
    public void setNonStationaryVerticalPos(double pos) {
        nonStationaryVerticalTargetPos = Utils.clamp(pos, 0, 1);
    }

    /**
     * Sets the target rotate position for the "non-stationary rotate" (Rotating the "non-stationary vertical") motors
     *
     * @param pos (0, 1) 0 for bottom; 1 for max
     */
    public void setNonStationaryRotatePos(double pos) {
        nonStationaryRotateTargetPos = Utils.clamp(pos, 0, 1);
    }

    public double getStationaryVerticalRotations() {
        return stationaryVertical.getEncoder().getPosition() / stationaryVerticalMaxRotations;
    }

    public void setEncoderZero() {
        stationaryVertical.getEncoder().setPosition(0);
    }

    /**
     * Resets this subsystem to default init status
     * @TODO: check if this is needed as all non-final variables are set in periodic()
     */
    public void init() {
        stationaryVerticalTargetPos = 0.0;
        nonStationaryRotateTargetPos = 0.0;
        nonStationaryVerticalTargetPos = 0.0;
    }

    /**
     * Runs periodically
     */
    public void periodic() {
        // Handle moving the motors
        stationaryVertical.set(stationaryVerticalTargetPos * stationaryVerticalMaxRotations);
        nonStationaryVertical.set(nonStationaryVerticalTargetPos * nonStationaryVerticalMaxRotations);
        nonStationaryRotate.set(nonStationaryRotateTargetPos * nonStationaryRotateMaxRotations);
    }

}
