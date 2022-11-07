package frc.robot;

import java.util.List;
import java.awt.Polygon;

import org.opencv.core.Point;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionProcessing extends SubsystemBase {
  private final double CAMERA_VIEW_ANGLE = 50.7 / 2; // The view angle of a LifeCam 3000
  private final double CENTER_COEFF = 1 / Math.atan(CAMERA_VIEW_ANGLE); // Target Angle = atan(x, CENTER_COEFF)

  private PhotonCamera _camera;

  public VisionProcessing(PhotonCamera camera) {
    _camera = camera;
  }

  @Override
  public void periodic() {
  }

  private Target getTarget(PhotonTrackedTarget target) {
    List<TargetCorner> corners = target.getCorners();
    int[] xCoords = new int[4];
    int[] yCoords = new int[4];
    
    for (int i = 0; i < 4; i++) {
      xCoords[i] = (int)corners.get(i).x;
      yCoords[i] = (int)corners.get(i).y;
    }
    
    Polygon boundingBox = new Polygon(xCoords, yCoords, 4);

    
  }

  class Target {
    double distance;
    double angle;

    public Target(double distance, double angle) {
      this.distance = distance;
      this.angle = angle;
    }
  }
}
