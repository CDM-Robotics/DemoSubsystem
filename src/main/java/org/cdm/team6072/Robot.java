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
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {


  private DriveSys mDriveSys;
  private Joystick m_leftStick;

  // ControlBoard holds the operator interface code such as JoyStick
  private ControlBoard mControlBoard  = ControlBoard.getInstance();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    m_leftStick = new Joystick(0);

    // must initialize nav system here for the navX-MXP
    NavXSys.getInstance();

    mDriveSys = DriveSys.getInstance();
    mDriveSys.setSensorStartPosn();
  }


  
  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
    NavXSys.getInstance().zeroYawHeading(); 
    ArcadeDriveCmd  mArcadeDriveCmd = new ArcadeDriveCmd(mControlBoard.drive_stick);
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
