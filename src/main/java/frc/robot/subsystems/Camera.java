// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class Camera extends SubsystemBase {


  private final PhotonCamera camera = new PhotonCamera("FHD_Webcam");

  public Camera(){
    PortForwarder.add(5800, "photonvision.local", 5800);
    camera.setPipelineIndex(0);
  }

  private PhotonPipelineResult result;

  private PhotonTrackedTarget target;

  private boolean hasTargets;

  HashMap<String, Double> targetInfo = new HashMap<>();

  PhotonTrackedTarget bestTarget;

  @Override
  public void periodic() {
    if (!camera.isConnected()) {
      //System.out.println("PhotonVision not connected yet");
      return;
    }
    if (camera.isConnected()){ 
    result = camera.getLatestResult();
    hasTargets = result.hasTargets();
    //System.out.println("hasTarget=" + hasTargets);
    SmartDashboard.putBoolean("CameraHasTarget:", hasTargets);
    //SmartDashboard.putNumber("YAW: ", targetInfo.get("Yaw"));
    if (!result.hasTargets()) {
        targetInfo.clear();
        return;
    }

    bestTarget = result.getBestTarget();

    if (bestTarget == null) {
        targetInfo.clear();
        return;
    }
    
    targetInfo.put("Yaw", Math.toRadians(bestTarget.getYaw()));
    targetInfo.put("Pitch", bestTarget.getPitch());
  
    }

  }

  public boolean hasTargets(){
    return hasTargets;
  }
  public HashMap<String, Double> targetInfo(){
    return targetInfo;
  }
  /**
   * 
   * @return Distance, if camera does not have targets then it will return -1
   */
  public double getDistance(){
    if((hasTargets)){ 
    return bestTarget.getBestCameraToTarget().getX();
    }
    return -1;

  }
}



