// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.*;

import com.revrobotics.spark.SparkMax;
//import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;


public class Shooter extends SubsystemBase {

  private static final int motorShooter1ID = ShooterConstants.motorShooter1ID;

  private final Camera camera = new Camera();


  private SparkMax m_shooterMotor1 = new SparkMax(motorShooter1ID, MotorType.kBrushless);

  private SparkClosedLoopController maxPid = m_shooterMotor1.getClosedLoopController();
  
  private final RelativeEncoder shooterEncoder;
  private SparkMaxConfig config1 = new SparkMaxConfig();
  private double range=0.2;

  private final double d = camera.getDistance();
  //real life error
  private final double error = 1;
 




  
  /** Creates a new Lift.*/
  public Shooter() {

    shooterEncoder = m_shooterMotor1.getEncoder();

  
  

  

    //config for the leadMotor.

    config1
    .idleMode(IdleMode.kBrake);
    config1.encoder
    .positionConversionFactor(1)
    .velocityConversionFactor(1000);
    config1.closedLoop
    .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
    .pid(0.002, 0, 0.08).outputRange(-range,range);





    
    m_shooterMotor1.configure(config1, ResetMode.kResetSafeParameters,PersistMode.kNoPersistParameters);






  }

  public void shoot(double voltage){

    //m_shooterMotor1.set(speed);
    if(d != -1){ 
      voltage = error * getVoltage();
      
    }
    System.out.println(voltage);
    maxPid.setSetpoint(voltage, ControlType.kVoltage,ClosedLoopSlot.kSlot0);
    
    

  }

  public void stop(){

    m_shooterMotor1.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Encoder", shooterEncoder.getPosition());
    SmartDashboard.putNumber("Motor speed", m_shooterMotor1.getAppliedOutput());
  }
  /**
   * 
   * @return Brother read the method (returns voltage)
   */
  public double getVoltage(){
    return (1/(ShooterConstants.radiusOfWheel*594.389))*(Math.sqrt((9.807*Math.pow(d, 2))/((d*Math.sin(ShooterConstants.angleOfShooter))-(2*ShooterConstants.height*Math.pow(Math.cos(ShooterConstants.angleOfShooter), 2)))));
  }
}
