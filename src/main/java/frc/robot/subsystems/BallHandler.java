package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BallHandler extends SubsystemBase {

    // Define the signleton
    private static BallHandler instance;
    public static BallHandler getInstance() {
        if (instance == null) {
            instance = new BallHandler();
        }
        return instance;
    }

    private CANSparkMax intake; // Cont. spinning (Toggle)
    private CANSparkMax belt, beltFollower; // Controlled to store ball (Controlled)
    private CANSparkMax flywheel, flywheelFollower; // Flywheel cont spinning (Toggle)
    private final double targetIntakeSpeed = 0.3; // Speed for the intake. NOTE: Under NO circumstances should this change unless the physical "dancing" of the intake system is fixed
    private final double targetFlywheelSpeed = 1; // Speed for the launch
    private boolean isIntakeEnabled = false, isFlywheelEnabled = false;

    /**
     * Resets this subsystem to default init status
     */
    public void init() {
        SmartDashboard.putBoolean("Intake Enabled", isIntakeEnabled);
        SmartDashboard.putBoolean("Flywheel Enabled", isFlywheelEnabled);
        SmartDashboard.putNumber("Belt Encoder Position", 0);
    }

    /**
     * Creates a BallHandler
     */
    private BallHandler() {
        intake = new CANSparkMax(4, MotorType.kBrushless);
        belt = new CANSparkMax(5, MotorType.kBrushless);
        flywheel = new CANSparkMax(6, MotorType.kBrushless);

        beltFollower = new CANSparkMax(13, MotorType.kBrushless);
        flywheelFollower = new CANSparkMax(14, MotorType.kBrushless);

        beltFollower.follow(belt, true);
        flywheelFollower.follow(flywheel, true);
    }

    /**
     * Toggles the intake
     */
    public void toggleIntake() {
        if (isIntakeEnabled) { // Intake is running
            intake.set(0.0);
        } else { // Intake is not running
            intake.set(targetIntakeSpeed);
        }
        SmartDashboard.putBoolean("Intake Enabled", !isIntakeEnabled);
        isIntakeEnabled = !isIntakeEnabled;
    }

    /**
     * Sets the speed for the middle motor in the ball mechanism
     */
    public void setBeltSpeed(double speed) {
        belt.set(speed);
    }

    /**
     * Toggles the launcher
     */
    public void toggleFlywheel() {
        if (isFlywheelEnabled) { // Launch is running
            flywheel.set(0.0);
            SmartDashboard.putBoolean("Flywheel Enabled", true);
        } else { // Launch is not running
            flywheel.set(targetFlywheelSpeed);
            SmartDashboard.putBoolean("Flywheel Enabled", false);
        }
        isFlywheelEnabled = !isFlywheelEnabled;
    }
}