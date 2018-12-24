package org.cdm.team6072.commands.intake;

import edu.wpi.first.wpilibj.command.Command;
import org.cdm.team6072.subsystems.IntakeSys;

public class IntakeStartCmd extends Command {

    private IntakeSys mIntakeSys;

    private boolean mFinished = false;
    private double mSpeed;

    public IntakeStartCmd(double speed) {
        mIntakeSys = IntakeSys.getInstance();
        mSpeed = speed;
        System.out.println("Intake. Start Ctor \n speed: " + speed + "\n");

    }

    @Override
    protected void initialize() {
        mIntakeSys.setSpeed(mSpeed);
        System.out.println("Starting Intake");
    }

    @Override
    protected void execute() {
        mIntakeSys.runMotor();
        mFinished = true;
    }

    /**
     * Can return true immediately - Motor continues running
     */
    @Override
    protected boolean isFinished() {
        return mFinished;
    }

}
