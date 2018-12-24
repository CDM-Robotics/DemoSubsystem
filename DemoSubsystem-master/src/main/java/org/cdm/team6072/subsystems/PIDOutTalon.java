package org.cdm.team6072.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.PIDSourceType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class PIDOutTalon implements PIDOutput {

    private double mPIDOutput;

    public PIDOutTalon() {
        mPIDOutput = 0.0;
    }

    @Override
    public void pidWrite(double output) {
        mPIDOutput = output;
    }

    public double getOutput() {
        return mPIDOutput;
    }

    public void zeroOutput(){
        mPIDOutput = 0.0;
    }
}