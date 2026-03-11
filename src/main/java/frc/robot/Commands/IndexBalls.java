// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class IndexBalls extends Command {
  private final Indexer m_indexer;

  private final double speed;
  private boolean isBackingUp = false;
  private final Timer stallTimer = new Timer();
  
  
  /** Creates a new IndexerBalls. */
  public IndexBalls(Indexer m_indexer, double speed) {
   
    
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_indexer = m_indexer;
    this.speed = speed;
    addRequirements(this.m_indexer);    

    }
   
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    m_indexer.index(speed);


  }
 
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    {
    // 1. Check for stall: High current (>30A) and Low velocity (<10 RPM)
    if (m_indexer.stalled()) {
      if (!isBackingUp) {
        stallTimer.restart();
        isBackingUp = true;
      }
    }

    // 2. Logic Switch
    if (isBackingUp) {
      m_indexer.index(-speed); // Reverse at 30% power
      
      if (stallTimer.hasElapsed(0.2)) { // After 1 second
        isBackingUp = false;
        stallTimer.stop();
      }
    } else {
      m_indexer.index(speed); // Normal forward operation
    }
  }
}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if(interrupted){
      m_indexer.stop();
      
  }


  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
