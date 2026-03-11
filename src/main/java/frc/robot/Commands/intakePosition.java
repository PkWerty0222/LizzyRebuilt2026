// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.Intake;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class intakePosition extends Command {
  private final Intake m_intake;

  //false = intake is in ------ true = intake is out
  private boolean location = false;
  private double goToPos = 0;

  private final double speed;
  
  
  /** Creates a new IntakeBalls. */
  public intakePosition(Intake m_intake, double speed) {
   
    
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_intake = m_intake;
    this.speed = speed;
    
    
   addRequirements(this.m_intake);    

    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    if (!location){ 
      goToPos = IntakeConstants.intakePositionOut;
    }
    else{
      goToPos = IntakeConstants.intakePositionIn;
    }
  
    location = !location;
    m_intake.setPosition(goToPos, speed);
    

  }
 
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    

    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  if(interrupted){
    m_intake.rotatestop();
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
 
    if(Math.abs(m_intake.getArmEncoderPosition()-goToPos)<.5){

      return true;
    }

    return false;
  }
}
