package org.cdm.team6072.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.cdm.team6072.RobotConfig;

public class IntakeSys extends Subsystem {

    private WPI_TalonSRX mIntake;

    private static IntakeSys mInstance;
    private double mSpeed;

    public static IntakeSys getInstance() {
        if (mInstance == null) {
            mInstance = new IntakeSys();
        }
        return mInstance;
    }

    private IntakeSys() {
        try {
            mIntake = new WPI_TalonSRX(RobotConfig.INTAKE_TALON);
            mIntake.setSafetyEnabled(false);
            mIntake.configOpenloopRamp(0.1, 10);
            mIntake.setNeutralMode(NeutralMode.Brake);
            mIntake.setSensorPhase(true);
        } catch (Exception ex) {
            System.out.print("\n Intake Talon Exception : " + ex + " \n");
        }
    }

    public void setSpeed(double s) {
        mSpeed = s;
    }

    public void runMotor() {
        mIntake.set(ControlMode.PercentOutput, mSpeed);
        System.out.println("IntakeSys.runMotor");
    }

    public void stopMotor() {
        mIntake.stopMotor();
    }

    public void initDefaultCommand() {
        System.out.println("Intake Sys: init default command");
    }
}