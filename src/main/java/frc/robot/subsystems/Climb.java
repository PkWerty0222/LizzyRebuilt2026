package frc.robot.subsystems;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
//import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimbConstants;

public class Climb extends SubsystemBase {
    private static final int motorClimb1ID = ClimbConstants.motorClimb1ID;


    private SparkMax m_ClimbMotor = new SparkMax(motorClimb1ID, MotorType.kBrushless);

    private SparkClosedLoopController maxPid = m_ClimbMotor.getClosedLoopController();
    private final RelativeEncoder climbEncoder;
    private SparkMaxConfig config1 = new SparkMaxConfig();
    private double range=0.2;
    

    /** Creates a new Climb.*/
    public Climb() {

        climbEncoder = m_ClimbMotor.getEncoder();
    

    

        //config for the leadMotor.

    config1
        .idleMode(IdleMode.kBrake);
        config1.encoder
        .positionConversionFactor(1)
        .velocityConversionFactor(1000);
    config1.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .pid(0.2, 0, 0.08).
        outputRange(-range,range);
    

        



        
    m_ClimbMotor.configure(config1, ResetMode.kResetSafeParameters,PersistMode.kNoPersistParameters);






    }

    public void collect(double speed){

        m_ClimbMotor.set(speed);
       

    }
    /**
     * 
     * @param position Position(pid based) that the climb goes to 
     * @param speed Speed of the motion untill position(range)
     */

    public void setPosition(double position,double speed) {
        config1.closedLoop
        .pid(0.2, 0, 0.08).outputRange(-speed,speed);
        maxPid.setSetpoint(position, ControlType.kPosition);
    }



    public double getArmEncoderPosition(){

        return climbEncoder.getPosition();
    }

    public void resetindexerEncoder() {
        climbEncoder.setPosition(0);
    }

    public void stop(){

        m_ClimbMotor.stopMotor();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        SmartDashboard.putNumber("Encoder", climbEncoder.getPosition());
        SmartDashboard.putNumber("Motor speed", m_ClimbMotor.getAppliedOutput());
    }
}

