package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaCurrentGame;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TfodCurrentGame;

/**
 * A helper class for using computer vision
 */
public class RingDetector {
  /**
   * Holds the parent op mode
   * Private because the the user should have the opmode themselves
   */
  private static LinearOpMode parentOpMode;

  private static VuforiaCurrentGame vuforia;
  private static TfodCurrentGame tfod;

  static Recognition recognition;
  static List<Recognition> recognitions;
  
  public static int ringCount;
  static double index;
  
  RingDetector(LinearOpMode parentOpMode)
  {
      this.parentOpMode = parentOpMode;
  }
  
  /**
   * 
   * \n NOTE: This must be called before any other functions
   */
  public static void setup()
  {

    vuforia = new VuforiaCurrentGame();
    tfod = new TfodCurrentGame();
    
    vuforia.initialize(
        "", // vuforiaLicenseKey
        parentOpMode.hardwareMap.get(WebcamName.class, "Webcam 1"), // cameraName
        "", // webcamCalibrationFilename
        false, // useExtendedTracking
        false, // enableCameraMonitoring
        VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES, // cameraMonitorFeedback
        0, // dx
        0, // dy
        0, // dz
        90, // xAngle
        0, // yAngle
        90, // zAngle
        true); // useCompetitionFieldTargetLocations
    // Set min confidence threshold to 0.7
    tfod.initialize(vuforia, 0.7F, true, true);
    // Initialize TFOD before waitForStart.
    // Init TFOD here so the object detection labels are visible
    // in the Camera Stream preview window on the Driver Station.
    tfod.activate();
    // Enable following block to zoom in on target.
    tfod.setZoom(2.5, 16 / 9);
    ringCount = 0;
  }
  
  public static void update()
  {
    // Put loop blocks here.
    // Get a list of recognitions from TFOD.
    recognitions = tfod.getRecognitions();
    // If list is empty, inform the user. Otherwise, go
    // through list and display info for each recognition.
    if (recognitions.size() == 0) {
      ringCount=0;
    } else {
      index = 0;
      // Iterate through list and call a function to
      // display info for each recognized object.
      for (Recognition recognition_item : recognitions) {
        recognition = recognition_item;
        if(recognition.getLabel() == "Quad")
        {
            ringCount = 4;
        }
        else
        {
            ringCount = 1;
        }
        // Display info.
        //displayInfo(index);
        // Increment index.
        index = index + 1;
      }
    }
  }
  
  /**
   * Adds ring detector telemetry data
   * \n NOTE: This does not update telemetry
   */
  public static void addTelemetry()
  {
    parentOpMode.telemetry.addData("Ring Count", ringCount);
  }
}
