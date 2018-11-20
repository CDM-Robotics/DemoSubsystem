package org.cdm.team6072.subsystems;

import java.util.ArrayList;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.cdm.team6072.Robot;
import org.cdm.team6072.RobotConfig;


/**
 * Implement a drive subsystem for the 2018 robot
 * Two motors per side, driving a single shaft per side
 * Each motor controlled by a CANTalon on the CAN bus
 *
 * To configure Talon Device Ids, need to use the NI web browser RoboRio config
 * Requires IE and Silverlight
 * Connect to robot wifi, then browse to http://roborio-6072-frc.local
 *
 * As at 2018-02-10, the Talons have been given device IDs that match the PDP port they are connected to
 *      See RobotConfig for details
 */
public class DriveSys extends Subsystem {

    private WPI_TalonSRX mLeft_Master;
    private WPI_TalonSRX mLeft_Slave0;
    private WPI_TalonSRX mRight_Master;
    private WPI_TalonSRX mRight_Slave0;

    ArrayList<TalonSRX> mMasterTalons = new ArrayList<TalonSRX>();

    private DifferentialDrive mRoboDrive;


    private static DriveSys mInstance;
    public static DriveSys getInstance() {
        if (mInstance == null) {
            mInstance = new DriveSys();
        }
        return mInstance;
    }

    /**
     * When configuring talon, the configXXX() methods have a timeout param.
     * Recommended value when config during init is 10mSec, because each call witll wait to ensure value set correctly
     * During run time, want the value ot be 0 to avoid blocking main thread
     */
    private DriveSys() {
        System.out.println("6072: DriveSys constructor");

        try {
            mLeft_Master = new WPI_TalonSRX(RobotConfig.DRIVE_LEFT_MASTER);
            mLeft_Master.setSafetyEnabled(false);
            mLeft_Master.configOpenloopRamp(0.1 , 10);
            mLeft_Master.setNeutralMode(NeutralMode.Brake);
            mLeft_Master.setSensorPhase(true);

            mLeft_Slave0 = new WPI_TalonSRX(RobotConfig.DRIVE_LEFT_SLAVE0);
            mLeft_Slave0.set(ControlMode.Follower, RobotConfig.DRIVE_LEFT_MASTER);
            mLeft_Slave0.setInverted(false);

            mRight_Master = new WPI_TalonSRX(RobotConfig.DRIVE_RIGHT_MASTER);
            mRight_Master.setSafetyEnabled(false);
            mRight_Master.configOpenloopRamp(0.1, 10);
            mRight_Master.setNeutralMode(NeutralMode.Brake);

            mRight_Slave0 = new WPI_TalonSRX(RobotConfig.DRIVE_RIGHT_SLAVE0);
            mRight_Slave0.set(ControlMode.Follower, RobotConfig.DRIVE_RIGHT_MASTER);
            mRight_Slave0.setInverted(false);

            mRoboDrive = new DifferentialDrive(mLeft_Master, mRight_Master);

            mMasterTalons.add(mRight_Master);
            mMasterTalons.add(mLeft_Master);
        }
        catch (Exception ex) {
            System.out.println("Exception in DriveSys ctor: " + ex.getMessage() + "\r\n" + ex.getStackTrace());
        }
    }


    /**
     * Each subsystem may, but is not required to, have a default command
     * which is scheduled whenever the subsystem is idle
     * (the command currently requiring the system completes).
     *  The most common example of a default command is a command for the drivetrain
     *  that implements the normal joystick control. This command may be interrupted
     *  by other commands for specific maneuvers ("precision mode", automatic alignment/targeting, etc.)
     *  but after any command requiring the drivetrain completes the joystick command would be scheduled again.
     */
    public void initDefaultCommand() {
        System.out.println("DriveSys: init default command");
    }


    private void sleep(int milliSecs) {
        try {
            Thread.sleep(milliSecs);
        } catch (Exception ex) {}
    }

        /**
     * Set the quad posn to same as PW posn.
     * Left motor sensor goes -ve when driving forward
     * This should only be called from Robot.Init because of the time delays
     */
    public void setSensorStartPosn() {

        mLeft_Master.getSensorCollection().setPulseWidthPosition(0, 10);
        mRight_Master.getSensorCollection().setPulseWidthPosition(0, 10);
        int leftPosn = mLeft_Master.getSensorCollection().getPulseWidthPosition();
        int rightPosn = mRight_Master.getSensorCollection().getPulseWidthPosition();

        /* mask out overflows, keep bottom 12 bits */
        int leftAbsPosition = leftPosn  & 0xFFF;
        int rightAbsPosn = rightPosn & 0xFFF;

        mLeft_Master.setSelectedSensorPosition(-leftAbsPosition, 0, 10);
        mRight_Master.setSelectedSensorPosition(rightAbsPosn, 0, 10);
        //mTalon.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs);
        // setSelected takes time so wait for it to get accurate print
        sleep(100);
    }

    public int getLeftSensPosn() {
        return mLeft_Master.getSensorCollection().getPulseWidthPosition();
    }


    private int mLoopCnt = 0;
    
    public void arcadeDrive(double mag, double yaw) {
        yaw = yaw * 0.8;        // reduce sensitivity on turn
        mRoboDrive.arcadeDrive(-mag, yaw, true);
    }


}