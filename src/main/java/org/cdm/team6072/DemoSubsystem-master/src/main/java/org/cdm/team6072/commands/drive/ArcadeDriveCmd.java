package org.cdm.team6072.commands.drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.cdm.team6072.subsystems.DriveSys;

public class ArcadeDriveCmd extends Command {

    private Joystick mStick;
    private DriveSys mDriveSys;

    public ArcadeDriveCmd(Joystick s) {
        mStick = s;
        mDriveSys = DriveSys.getInstance();
    }

    protected void initialize() {
        System.out.println("Initializing Arcade Drive Command");
    }

    protected void execute() {
        double mag = mStick.getY();
        double yaw = mStick.getX();
        mDriveSys.arcade(mag, yaw);
    }

    protected boolean isFinished() {
        return false;
    }
}