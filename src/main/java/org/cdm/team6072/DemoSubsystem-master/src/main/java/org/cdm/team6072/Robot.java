/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.cdm.team6072;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.Joystick;

import org.cdm.team6072.commands.intake.IntakeStopCmd;
import org.cdm.team6072.commands.drive.ArcadeDriveCmd;
import org.cdm.team6072.commands.drive.DriveDistCmd;
import org.cdm.team6072.commands.intake.IntakeStartCmd;
import org.cdm.team6072.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

  private IntakeSys mIntakeSys;
  private Joystick m_leftStick;

  // ControlBoard holds the operator interface code such as JoyStick
  private ControlBoard mControlBoard = ControlBoard.getInstance();

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    System.out.println("Robot Joystick");
    m_leftStick = new Joystick(0);

    System.out.println("Robot NavX System starting");
    // must initialize nav system here for the navX-MXP
    NavXSys.getInstance();

    System.out.println("Robot Drive System starting");
    DriveSys.getInstance();
    System.out.println("Robot Drive System Initialized");

    System.out.println("Robot INtake System starting");
    mIntakeSys = IntakeSys.getInstance();
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
    NavXSys.getInstance().zeroYawHeading();

    System.out.println("arcadeDriveCmd starting");
    ArcadeDriveCmd arcadeDriveCmd = new ArcadeDriveCmd(m_leftStick);

    System.out.println("Scheduler wiping");
    Scheduler.getInstance().removeAll();
    System.out.println("Scheduler adding arcadeDriveCmd");
    Scheduler.getInstance().add(arcadeDriveCmd);
  }

  /**
   * This function is called periodically during teleoperated mode.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  public void println(String s) {
    System.out.println(s);
  }

  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    NavXSys.getInstance().zeroYawHeading();
    println("Autonomous Initializing Drive Distance Cmd");
    DriveDistCmd driveDistCmd = new DriveDistCmd(5.0);
    println("Initialized Drive Distance Cmd");
    Scheduler.getInstance().removeAll();
    Scheduler.getInstance().add(driveDistCmd);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

}
