package org.cdm.team6072.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.cdm.team6072.RobotConfig;

public class IntakeSys {

    private WPI_TalonSRX mTalonLeft; // Calls TalonX

    private static IntakeSys mInstance; 

    public static IntakeSys getInstance() {
        if (mInstance == null) { // Initiallizes IntakeSys if not initiallized
            mInstance = new IntakeSys();
        }
        return mInstance;
    }

    private IntakeSys() {

        mTalonLeft = new WPI_TalonSRX(RobotConfig.INTAKE_TALON_LEFT); // InitiallizesTalonX
        mTalonLeft.setNeutralMode(NeutralMode.Brake); // Protection from Speedy Vroom Vroom 
        mTalonLeft.setInverted(false); // Sets Direction of TalonX
    }

    public void slowRunWheels(String dir, double speed) {
        if (dir == "Out") { // Selects Direction
            speed *= -1;
        mTalonLeft.set(ControlMode.PercentOutput, speed); // Scales Speed Down/Up

        }
    }
}