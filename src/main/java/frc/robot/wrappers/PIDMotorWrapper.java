package frc.robot.wrappers;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * I am not willing to document this until the comps are over, dont talk about it
 */
public class PIDMotorWrapper {
    private CANSparkMax m_motorLeader, m_motorFollower;
    private SparkMaxPIDController m_pidController;

    private double kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput, minVelocity, maxVelocity, maxAccel, allowedErr;

    private double setPoint;

    private final int smartMotionSlot = 0;

    /**
     * creates a pair of brushless motors that use PID control
     * 
     * @param CANid the CAN id of the leader motor controller
     * @param followerCANid the CAN id of the follower motor controller
     * @param kP the Proportional scaling factor of PID
     * @param kI the Integral scaling factor of PID
     * @param kD the Derivative scaling factor of PID
     * @param kIz the Range that the PID error must be in for the Integral to begin to take effect
     * @param kFF the Feed Foward gain value
     * @param kMinOutput the Max power to be used when the motor is reversing
     * @param kMaxOutput the Max power to be used when the motor is going foward
     * @param minVelocity the Max velocity to be used when the motor is reversing
     * @param maxVelocity the Max velocity to be used when the motor is going foward
     * @param maxAccel the Max acceration of the motor
     * @param allowedErr the maximum error from the setpoint
     */
    public PIDMotorWrapper(int CANid, int followerCANid, double kP, double kI, double kD, double kIz, double kFF, double kMinOutput,
            double kMaxOutput, double minVelocity, double maxVelocity, double maxAccel,
            double allowedErr) {
        // set all relavent PID values
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kIz = kIz;
        this.kFF = kFF;
        this.kMinOutput = kMinOutput;
        this.kMaxOutput = kMaxOutput;
        this.minVelocity = minVelocity;
        this.maxVelocity = maxVelocity;
        this.maxAccel = maxAccel;
        this.allowedErr = allowedErr;

        // init the motors
        m_motorLeader = new CANSparkMax(CANid, MotorType.kBrushless);
        m_motorFollower = new CANSparkMax(followerCANid, MotorType.kBrushless);
        m_pidController = m_motorLeader.getPIDController();

        // set the follower to followe the leader
        m_motorFollower.follow(m_motorLeader, true);

        // pass the PID values to the controller
        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);

        // pass the motion values to the controller
        m_pidController.setSmartMotionMaxVelocity(maxVelocity, smartMotionSlot);
        m_pidController.setSmartMotionMinOutputVelocity(minVelocity, smartMotionSlot);
        m_pidController.setSmartMotionMaxAccel(maxAccel, smartMotionSlot);
        m_pidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
    }

    /**
     * this method put all PID values to shuffleboard so we can tune the PID values
     */
    public void startShuffleBoard() {
        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);

        // display Smart Motion coefficients
        SmartDashboard.putNumber("Max Velocity", maxVelocity);
        SmartDashboard.putNumber("Min Velocity", minVelocity);
        SmartDashboard.putNumber("Max Acceleration", maxAccel);
        SmartDashboard.putNumber("Allowed Closed Loop Error", allowedErr);
    }

    /**
     * grab new values from shuffle board to help with tuning PID values easier NOTE: might be bugged
     */
    public void updateShuffleBoard() {
        // read PID coefficients from SmartDashboard
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);
        double maxV = SmartDashboard.getNumber("Max Velocity", 0);
        double minV = SmartDashboard.getNumber("Min Velocity", 0);
        double maxA = SmartDashboard.getNumber("Max Acceleration", 0);
        double allE = SmartDashboard.getNumber("Allowed Closed Loop Error", 0);

        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if((p != kP)) { m_pidController.setP(p); kP = p; }
        if((i != kI)) { m_pidController.setI(i); kI = i; }
        if((d != kD)) { m_pidController.setD(d); kD = d; }
        if((iz != kIz)) { m_pidController.setIZone(iz); kIz = iz; }
        if((ff != kFF)) { m_pidController.setFF(ff); kFF = ff; }
        if((max != kMaxOutput) || (min != kMinOutput)) { 
            m_pidController.setOutputRange(min, max); 
            kMinOutput = min; kMaxOutput = max; 
        }
        if((maxV != maxVelocity)) { m_pidController.setSmartMotionMaxVelocity(maxV,0); maxVelocity = maxV; }
        if((minV != minVelocity)) { m_pidController.setSmartMotionMinOutputVelocity(minV,0); minVelocity = minV; }
        if((maxA != maxAccel)) { m_pidController.setSmartMotionMaxAccel(maxA,0); maxAccel = maxA; }
        if((allE != allowedErr)) { m_pidController.setSmartMotionAllowedClosedLoopError(allE,0); allowedErr = allE; }

        SmartDashboard.putNumber("Set Point", setPoint);
        SmartDashboard.putNumber("Motor Position", m_motorLeader.getEncoder().getPosition());
    }

    /**
     * sets a smartMotion setpoint for the motors,
     * 
     * @param setPoint
     */
    public void set(double setPoint) {
        m_pidController.setReference(setPoint, ControlType.kSmartMotion);
        
        // the setpoint is stored for tuning the PID values, and is never used in normal operation
        this.setPoint = setPoint;
    }

    public void setVelocity(double velocity) {
        m_pidController.setReference(velocity, ControlType.kSmartVelocity);
    }

    public void setInverted(boolean isInverted) {
        m_motorLeader.setInverted(isInverted);
    }

    //start boilerplate getters and setters
    
    public CANSparkMax getMotor() {
        return this.m_motorLeader;
    }

    public SparkMaxPIDController getPidController() {
        return this.m_pidController;
    }

    public RelativeEncoder getEncoder() {
        return m_motorLeader.getEncoder();
    }

    public double getKP() {
        return this.kP;
    }

    public void setKP(double kP) {
        this.kP = kP;
    }

    public double getKD() {
        return this.kD;
    }

    public void setKI(double kI) {
        this.kI = kI;
    }

    public double getKI() {
        return this.kI;
    }

    public void setKD(double kD) {
        this.kD = kD;
    }

    public double getKIz() {
        return this.kIz;
    }

    public void setKIz(double kIz) {
        this.kIz = kIz;
    }

    public double getKFF() {
        return this.kFF;
    }

    public void setKFF(double kFF) {
        this.kFF = kFF;
    }

    public double getKMinOutput() {
        return this.kMinOutput;
    }

    public void setKMinOutput(double kMinOutput) {
        this.kMinOutput = kMinOutput;
    }

    public double getKMaxOutput() {
        return this.kMaxOutput;
    }

    public void setKMaxOutput(double kMaxOutput) {
        this.kMaxOutput = kMaxOutput;
    }

    public double getMinVelocity() {
        return this.minVelocity;
    }

    public void setMinVelocity(double minVelocity) {
        this.minVelocity = minVelocity;
    }

    public double getMaxVelocity() {
        return this.maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public double getMaxAccel() {
        return this.maxAccel;
    }

    public void setMaxAccel(double maxAccel) {
        this.maxAccel = maxAccel;
    }

    public double getAllowedErr() {
        return this.allowedErr;
    }

    public void setAllowedErr(double allowedErr) {
        this.allowedErr = allowedErr;
    }
}
