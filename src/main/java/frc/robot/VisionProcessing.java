package frc.robot;

import java.util.Arrays;
import java.util.List;
import java.awt.Polygon;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionProcessing extends SubsystemBase {
  private final double CAMERA_VIEW_ANGLE = 50.7 / 2; // The view angle of a LifeCam 3000
  private final double CENTER_COEFF = 1 / Math.tan(CAMERA_VIEW_ANGLE); // Target Angle = atan2(x, CENTER_COEFF)
  private final int IMAGE_WIDTH_PIXELS = 320; // Width in pixels of the camera's image; get this from resolution
  private final double TARGET_WIDTH_INCHES = 16 / 2.54;
  private final int DISTANCE_COEFF = (int) (((IMAGE_WIDTH_PIXELS * TARGET_WIDTH_INCHES) / 2) * CENTER_COEFF);


  private PhotonCamera _camera;

  public VisionProcessing(PhotonCamera camera) {
    _camera = camera;
  }

  @Override
  public void periodic() {
  }

  public Target getTarget(PhotonTrackedTarget target) {
    // Get the corners of the bounding box
    List<TargetCorner> corners = target.getCorners();
    System.out.println("Count: " + corners.size());
    int[] xCoords = new int[4];
    int[] yCoords = new int[4];
    
    // Sort x and y coords for making bounding box
    for (int i = 0; i < 4; i++) {
      xCoords[i] = (int)corners.get(i).x;
      yCoords[i] = (int)corners.get(i).y;
    }
    
    System.out.println("X1: " + corners.get(0).x + " Y1: " + corners.get(0).y);
    System.out.println("X2: " + corners.get(1).x + " Y2: " + corners.get(1).y);
    System.out.println("X3: " + corners.get(2).x + " Y3: " + corners.get(2).y);
    System.out.println("X4: " + corners.get(3).x + " Y4: " + corners.get(3).y);

    // Make bounding box for target
    Polygon boundingBox = new Polygon(xCoords, yCoords, 4);
    double targetCenter = boundingBox.getBounds().getCenterX();
    int targetWidth = boundingBox.getBounds().width;

    // Calculate distance and angle for target
    double angle = Math.atan2(targetCenter, CENTER_COEFF) * 180 / 3.14159;
    double distance = DISTANCE_COEFF / (double) targetWidth;

    return new Target(distance, angle);
  }

  class Target {
    double distance;
    double angle;

    public Target(double distance, double angle) {
      this.distance = distance;
      this.angle = angle;
    }

    public double getDistance() {
      return this.distance;
    }

    public double getAngle() {
      return this.angle;
    }
  }
}
