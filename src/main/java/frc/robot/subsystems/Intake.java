// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;

import com.revrobotics.spark.SparkMax;
//import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.EncoderConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;


public class Intake extends SubsystemBase {

  private static final int collectMotorID = IntakeConstants.collectIntakeMotorId;
  private static final int rotateMotorID = IntakeConstants.rotateIntakeMotorId;



  private SparkMax m_collectintakeMotor = new SparkMax(collectMotorID, MotorType.kBrushless);
  private SparkMax m_rotateintakeMotor = new SparkMax(rotateMotorID, MotorType.kBrushless);
  
  private SparkClosedLoopController maxPid = m_rotateintakeMotor.getClosedLoopController();
  private final RelativeEncoder intakeEncoder;
  private SparkMaxConfig config1 = new SparkMaxConfig();
  private double range=0.2;
  //DigitalInput intakelimitSwitch = new DigitalInput(1);




  
 
  public Intake() {

    intakeEncoder = m_rotateintakeMotor.getEncoder();
    
       
    //config for the leadMotor.
    
    

    config1
    .idleMode(IdleMode.kBrake);
    config1.encoder
    .positionConversionFactor(1)
    .velocityConversionFactor(1000);

    config1.closedLoop
    .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
    .p(0.2).i(0).d(0.08).
    outputRange(-0.5, 0.5)
    .feedForward
    .kV(12.0/11000);

    m_rotateintakeMotor.configure(config1, ResetMode.kResetSafeParameters,PersistMode.kNoPersistParameters);
    

  }
/**
 * 
 * @param speed
 */
  public void collect(double speed){

    m_collectintakeMotor.set(speed);

  }


  /**
   * 
   * @param position the postion to of the intake
   * @param speed the speed of the intake
   */
  public void setPosition(double position,double speed) {

   //  config1.closedLoop
   // .pid(Constants.IntakeConstants.intakeKP, Constants.IntakeConstants.intakeKi, Constants.IntakeConstants.intakeKd).outputRange(-speed,speed);
    
    config1.closedLoop.p(0.2).i(0).d(0.08).outputRange(-speed, speed);
      
    maxPid.setSetpoint(position, ControlType.kPosition);
    

  }

  /*public boolean limitSwitch(){
    return intakelimitSwitch.get();
  }*/


  /**
   * 
   * @return returns the encoder postion
   */
  public double getArmEncoderPosition(){

    return intakeEncoder.getPosition();
  }
  public void IntakeManualOut(){

    m_rotateintakeMotor.set(0.3);

  }

  public void IntakeManualIn(){

    m_rotateintakeMotor.set(-0.3);


  }

  public void resetIntakeEncoder() {
    intakeEncoder.setPosition(0);
  }

  public void rotatestop(){

    m_rotateintakeMotor.stopMotor();
  }
  public void collectstop(){

    m_collectintakeMotor.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Intake Encoder", intakeEncoder.getPosition());
    SmartDashboard.putNumber("Motor speed", m_collectintakeMotor.getAppliedOutput());
  }
}
