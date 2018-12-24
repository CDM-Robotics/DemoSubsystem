package org.cdm.team6072.commands.intake;

import edu.wpi.first.wpilibj.command.Command;
import org.cdm.team6072.subsystems.IntakeSys;

public class IntakeStopCmd extends Command {

    private IntakeSys mIntakeSys;
    private boolean mFinished = false;

    public IntakeStopCmd() {
        mIntakeSys = IntakeSys.getInstance();
        System.out.println("Intake. Stop Ctor ");
    }

    @Override
    protected void initialize() {
        mIntakeSys.setSpeed(0.0);
        System.out.println("Stopping Intake");
    }

    @Override
    protected void execute() {
        mIntakeSys.runMotor();
        mFinished = true;
    }

    @Override
    protected boolean isFinished() {

        return mFinished;
    }

}
