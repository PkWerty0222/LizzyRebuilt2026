// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Feeder;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class FeedBalls extends Command {
  private final Feeder m_feeder;

  private final double speed;
  
  
  /** Creates a new IntakeBalls. */
  public FeedBalls(Feeder m_feeder, double speed) {
   
    
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_feeder = m_feeder;
    this.speed = speed;
    addRequirements(this.m_feeder);    

    }
    
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    m_feeder.feed(speed);


  }
 
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if(interrupted){
      m_feeder.stop();
      
  }


  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
