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

import org.cdm.team6072.commands.drive.ArcadeDriveCmd;
import org.cdm.team6072.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * 
 * IF PACKAGE NAME CHANGES, MAKE SURE TO UPDATE build.gradle:
 *  def ROBOT_CLASS = "org.cdm.team6072.Robot"
 */
public class Robot extends TimedRobot {


  private DriveSys mDriveSys;

  // ControlBoard holds the operator interface code such as JoyStick
  private ControlBoard mControlBoard  = ControlBoard.getInstance();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    System.out.println("DemoSubsys.ctor  -------------------------------------------");

    m_leftStick = new Joystick(0);

    System.out.println("DemoSubsys.ctor  joystick doen  --------------------------------");

    // must initialize nav system here for the navX-MXP
    NavXSys.getInstance();

    System.out.println("DemoSubsys.ctor  NavX done         ----------------------------");

    mDriveSys = DriveSys.getInstance();

    System.out.println("DemoSubsys.ctor  driveSys done     -------------------------------");
    mDriveSys.setSensorStartPosn();

    System.out.println("DemoSubsys.ctor  ds.setSensor done     ------------------------");
  }


  
  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
    NavXSys.getInstance().zeroYawHeading(); 
    ArcadeDriveCmd  mArcadeDriveCmd = new ArcadeDriveCmd(mControlBoard.mDriveStick);
    Scheduler.getInstance().removeAll();
    Scheduler.getInstance().add(mArcadeDriveCmd);
  }


  /**
   * This function is called periodically during teleoperated mode.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    // double mag, yaw;
    // mag = m_leftStick.getY();                   // how fast
    // yaw = m_leftStick.getX();                   // turn left or right
    // yaw = yaw * 0.8;                            // reduce sensitivity on turn
    // mDriveSys.arcadeDrive(-mag, yaw);
  }


  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    NavXSys.getInstance().zeroYawHeading(); 
    mDriveSys.initDriveDist(4);
  }


  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    mDriveSys.driveDist();
  }



  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }


  
}
