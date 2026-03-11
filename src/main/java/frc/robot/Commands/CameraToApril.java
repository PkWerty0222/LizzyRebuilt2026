
package frc.robot.Commands;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class CameraToApril extends Command{   
private final CommandSwerveDrivetrain drivetrain;
    private final Camera camera;

    private final SwerveRequest.RobotCentric rotateRequest =
        new SwerveRequest.RobotCentric();

    private static final double kP = 2; // start small, vision is noisy
    private static final double kMaxRot = 2.0; // rad/s
    private static final double kToleranceRad = Math.toRadians(1.5);

    public CameraToApril(CommandSwerveDrivetrain drivetrain, Camera camera) {
        this.drivetrain = drivetrain;
        this.camera = camera;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        
            
        if (camera.hasTargets()) { 
        double yawRad = camera.targetInfo().get("Yaw"); // +right, -left
        double pitchDeg = camera.targetInfo().get("Pitch");
        //System.out.println(pitchDeg);
        double rotationCmd = -kP * yawRad;

        rotationCmd = Math.max(
            -kMaxRot,
            Math.min(rotationCmd, kMaxRot)
        );

        drivetrain.setControl(
            rotateRequest
                .withVelocityX(0)
                .withVelocityY(0)
                .withRotationalRate(rotationCmd)
        );
        }
    }

    @Override
    public boolean isFinished() {
    if (!camera.hasTargets()) {
        return false;
    }
    //return Math.abs(camera.targetInfo().get("Yaw")) < kToleranceRad;
    return false;
}

    @Override
    public void end(boolean interrupted) {
        drivetrain.setControl(
            rotateRequest.withRotationalRate(0)
        );
    }
}
