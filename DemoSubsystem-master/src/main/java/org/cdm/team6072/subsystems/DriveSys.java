package org.cdm.team6072.subsystems;

import java.util.concurrent.TimeUnit;

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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.cdm.team6072.RobotConfig;

public class DriveSys {

    private static DriveSys mInstance;

    private WPI_TalonSRX mLeftMaster;
    private WPI_TalonSRX mRightMaster;
    private WPI_TalonSRX mLeftSlave0;
    private WPI_TalonSRX mRightSlave0;

    private PIDFromTalon mPIDSourceTalon;
    private PIDOutTalon mPIDOutputTalon;
    private PIDController mPIDController;

    private double mP = 2.0;
    private double mI = 1.0;
    private double mD = 0.5;
    private double mF = 1.0;
    private double TOLERANCE = 100;

    private DifferentialDrive mRobotDrive;

    public static DriveSys getInstance() {
        if (mInstance == null) {
            mInstance = new DriveSys();
        }
        return mInstance;
    }

    private DriveSys() {
        mLeftMaster = new WPI_TalonSRX(RobotConfig.DRIVE_LEFT_MASTER);
        mLeftSlave0 = new WPI_TalonSRX(RobotConfig.DRIVE_LEFT_SLAVE0);
        mLeftMaster.configClosedloopRamp(2, 10);
        mLeftMaster.setSafetyEnabled(false);
        mLeftMaster.setNeutralMode(NeutralMode.Coast);
        mLeftSlave0.follow(mLeftMaster);
        mLeftSlave0.setInverted(false);

        mRightMaster = new WPI_TalonSRX(RobotConfig.DRIVE_RIGHT_MASTER);
        mRightSlave0 = new WPI_TalonSRX(RobotConfig.DRIVE_RIGHT_SLAVE0);
        mRightMaster.configClosedloopRamp(2, 10);
        mRightMaster.setSafetyEnabled(false);
        mRightMaster.setNeutralMode(NeutralMode.Coast);
        mRightSlave0.follow(mRightMaster);
        mRightSlave0.setInverted(false);

        mRobotDrive = new DifferentialDrive(mLeftMaster, mRightMaster);

        initDrivePID();
    }

    private void initDrivePID() {
        mPIDSourceTalon = new PIDFromTalon(mLeftMaster);
        mPIDOutputTalon = new PIDOutTalon();
        mPIDController = new PIDController(mP, mI, mD, mF, mPIDSourceTalon, mPIDOutputTalon);
        mPIDController.setOutputRange(-.8, .8);
        mPIDController.setAbsoluteTolerance(TOLERANCE);
        mPIDController.setContinuous(false);
    }

    public void arcade(double mag, double yaw) {
        currentPosition = getCurrentPosition();
        println("current position : " + currentPosition + "\nfinalPosition : " + finalPosition);
        mRobotDrive.arcadeDrive(mag, yaw, true);
    }

    private int currentPosition;
    private int finalPosition;

    public void initDriveDist(double distanceInFeet) {
        int distance = calcDist(distanceInFeet);
        setPositionZero();
        currentPosition = getCurrentPosition();
        finalPosition = currentPosition + distance;
        println("current position : " + currentPosition + "\nfinalPosition : " + finalPosition);
        mPIDController.setSetpoint(finalPosition);
        mPIDController.enable();
    }

    public int getCurrentPosition() {
        return mLeftMaster.getSensorCollection().getQuadraturePosition();
    }

    public double getSpeed() {
        double speed = mPIDOutputTalon.getOutput();
        return speed;
    }

    private int calcDist(double distanceInFeet) {
        final int quadPerFoot = (5216 / 2);
        distanceInFeet = distanceInFeet * quadPerFoot;
        return (int) (distanceInFeet);
    }

    private void setPositionZero() {
        mLeftMaster.getSensorCollection().setQuadraturePosition(0, 10);
        sleep(1000);
    }

    private void sleep(double amt) {
        long a = System.currentTimeMillis();
        long b = System.currentTimeMillis();
        while ((b - a) <= amt) {
            b = System.currentTimeMillis();
        }
    }

    public boolean isFinishedDriving() {
        boolean finished = mPIDController.onTarget();
        if (finished == true) {
            mPIDOutputTalon.zeroOutput();
            setPositionZero();
            mPIDController.disable();
            println("current position : " + currentPosition + "\nfinalPosition : " + finalPosition);
        }
        return finished;
    }

    public void println(String s) {
        System.out.println(s);
    }
}