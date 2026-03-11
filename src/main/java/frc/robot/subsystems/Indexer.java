// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IndexerConstants;

import com.revrobotics.spark.SparkMax;
//import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;


public class Indexer extends SubsystemBase {

  private static final int motorIndexer1ID = IndexerConstants.motorIndexer1ID;


  private SparkMax m_indexerMotor = new SparkMax(motorIndexer1ID, MotorType.kBrushless);
  private final RelativeEncoder indexerEncoder;
  
 




  
  /** Creates a new Indexer.*/
  public Indexer() {

    indexerEncoder = m_indexerMotor.getEncoder();
    
  }
  /**
   * 
   * @param speed Speed of indexing
   */

  public void index(double speed){

    m_indexerMotor.set(-speed);

  }

  public void stop(){

    m_indexerMotor.stopMotor();
  }

  public boolean stalled(){

    if(maxCurrentReached() && this.getSpeed()<10){

      return true;
    }
    else
    return false;
  
  }



  public double getSpeed(){


    return indexerEncoder.getVelocity();
  }
  public boolean maxCurrentReached(){

    if(m_indexerMotor.getOutputCurrent()>30){

      return true;

    }
    else
    return false;


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Encoder", indexerEncoder.getPosition());
    SmartDashboard.putNumber("Motor speed", m_indexerMotor.getAppliedOutput());
    SmartDashboard.putNumber("RPM OF INDEXER: ", this.getSpeed());
    SmartDashboard.putNumber("CURRENT OF INDEXER: ", m_indexerMotor.getOutputCurrent());


  }
}
