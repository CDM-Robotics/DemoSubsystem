package org.cdm.team6072.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import org.cdm.team6072.subsystems.DriveSys;

public class DriveDistCmd extends Command {

    private DriveSys mDriveSys;

    private double mDistInFeet;

 
    /**
     * Specify the the command requires the DriveSys subsystem
     * @param distInFeeet - distance to travel in feet
     */
    public DriveDistCmd(double distInFeeet) {
        requires(DriveSys.getInstance());
        mDistInFeet = distInFeeet;
    }

    @Override
    protected void initialize() {
        mDriveSys = DriveSys.getInstance();
        mDriveSys.initDriveDist(mDistInFeet);
    }

    /**
     * Execute is called by the scheduler until the command returns finished or the
     * OI stops requesting - for example if the whileHeld() button command is used
     */
    protected void execute() {
        // mDriveSys.moveDistanceExec();
        mDriveSys.driveDist();
    }

    /**
     * @return Return true when command is completed
     */
    @Override
    protected boolean isFinished() {
        return mDriveSys.isDriveDistComplete();
    }

}