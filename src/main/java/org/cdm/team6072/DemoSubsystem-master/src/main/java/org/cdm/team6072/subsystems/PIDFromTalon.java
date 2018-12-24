package org.cdm.team6072.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.PIDSourceType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class PIDFromTalon implements PIDSource {

    private WPI_TalonSRX mLeftMaster;

    public PIDFromTalon(WPI_TalonSRX leftMaster) {
        mLeftMaster = leftMaster;
    }

    public double pidGet() {
        return mLeftMaster.getSensorCollection().getQuadraturePosition();
    }

    public void setPIDSourceType(PIDSourceType s) {
    }

    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }
}