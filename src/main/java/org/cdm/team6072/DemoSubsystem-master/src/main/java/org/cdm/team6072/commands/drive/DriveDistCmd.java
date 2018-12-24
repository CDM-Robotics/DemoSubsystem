package org.cdm.team6072.commands.drive;

import edu.wpi.first.wpilibj.command.*;
import org.cdm.team6072.subsystems.DriveSys;

public class DriveDistCmd extends Command {
    private DriveSys mDriveSys;
    private double mDistanceInFeet;
    private double mSpeed;

    public DriveDistCmd(double distanceinFeet) {
        mDriveSys = DriveSys.getInstance();
        mDistanceInFeet = distanceinFeet;
    }

    protected void initialize() {
        println("DriveDistCmd initializing drive command");
        mDriveSys.initDriveDist(mDistanceInFeet);
    }

    protected void execute() {
        mSpeed = mDriveSys.getSpeed();
        println("mSpeed : " + mSpeed);
        mDriveSys.arcade(mSpeed, 0);
    }

    protected boolean isFinished() {
        return (mDriveSys.isFinishedDriving());
    }

    public void println(String s) {
        System.out.println(s);
    }
}