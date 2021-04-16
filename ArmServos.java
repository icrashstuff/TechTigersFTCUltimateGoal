package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * A servo helper class, for the ARM
 * \n NOTE: Generally this class should not need changes, other than the first 5 variables
 */
public class ArmServos {

  public static double high = 0.76;
  public static double mid = 0.3;
  public static double low = 0.114;
  
  // This is for reasons
  public static double servoOffset = 0.120;
  /*
   * The difference between the 2 servos
   * This is subtracted from the right servo
   */
  public static double internalServoOffset = 0.0175;
  
  // Left Servo
  public static Servo sLeftArm;
  // Right Servo labeled R
  public static Servo sRightArm;
  // Mid servo for holding wobble goal
  public static Servo sWobbleHolder;
  
  /**
   * Holds the parent op mode
   * Private because the the user should have the opmode themselves
   */
  private static LinearOpMode parentOpMode;
  
  ArmServos(LinearOpMode parentOpMode)
  {
      this.parentOpMode = parentOpMode;
  }
  
  /**
   * Sets up the servos
   * \n NOTE: This must be called before any other functions
   */
  public static void setup()
  {
    sLeftArm = parentOpMode.hardwareMap.servo.get("sLeftArm");
    sRightArm = parentOpMode.hardwareMap.servo.get("sRightArm");
    sWobbleHolder = parentOpMode.hardwareMap.servo.get("sWobbleHolder");
    sLeftArm.setDirection(Servo.Direction.REVERSE);
    sRightArm.setDirection(Servo.Direction.FORWARD);
    sWobbleHolder.setDirection(Servo.Direction.FORWARD);
    //setArcPosition(0.13);
    sWobbleHolder.setPosition(0.779);
  }
  
  /**
   * Sets the position of the arc servos
   * \n NOTE: The function will not prevent you from nullifing the internalServoOffset at extreme ranges
   * @param position The position to go to
   */
  public static void setArcPosition(double position)
  {
    setArcPosition(position,false);
  }
  
  /**
   * Sets the position of the arc servos
   * \n NOTE: The function will not prevent you from nullifing the internalServoOffset at extreme ranges
   * @param position The position to go to
   * @param disableServoOffset When true servoOffset is not added to the position
   */
  public static void setArcPosition(double position, boolean disableServoOffset)
  {
    sLeftArm.setPosition(position+(disableServoOffset ? 0.0 : servoOffset));
    sRightArm.setPosition(position+(disableServoOffset ? 0.0 : servoOffset)-internalServoOffset);
  }
  
  /**
   * Sets the position of the wobble holder servo
   * @param position The position to go to
   */
  public static void setWobbleHolderPosition(double position)
  {
    sWobbleHolder.setPosition(position);
  }
  
  /**
   * Increments the wobble holder servo
   * @param increment The amount to increment the servo by
   * \n NOTE: For debugging purposes ONLY!
   */
  public static void addToWobbleHolderServos(double increment)
  {
    sWobbleHolder.setPosition(sWobbleHolder.getPosition()+increment);
  }
  
  /**
   * Increments the arc servos
   * @param increment The amount to increment the servos by
   * \n NOTE: For debugging purposes ONLY!
   */
  public static void addToArcServos(double increment)
  {
    setArcPosition(sLeftArm.getPosition()+increment,true);
    //sLeftArm.setPosition(sLeftArm.getPosition()+increment);
    //sRightArm.setPosition(sLeftArm.getPosition()+increment-internalServoOffset);
  }
  
  /**
   * Sets all servo positions to zero
   * WARNING: THE PROGRAM SHOULD STOP IMEDIATELY AFTER CALLING
   */
  public static void setAllZero()
  {
    sLeftArm.setPosition(0.0);
    sRightArm.setPosition(0.0);
    sWobbleHolder.setPosition(0.0);
  }
  
  /**
   * Adds servo telemetry data
   * \n NOTE: This does not update telemetry
   */
  public static void addTelemetry()
  {
    //parentOpMode.telemetry.addData("sWobbleHolder Position", sWobbleHolder.getPosition());
    parentOpMode.telemetry.addData("sLeftArm Position", sLeftArm.getPosition());
    parentOpMode.telemetry.addData("sRightArm Position", sRightArm.getPosition());
    parentOpMode.telemetry.addData("Servo offset", servoOffset);
    parentOpMode.telemetry.addData("internalServoOffset", internalServoOffset);
    parentOpMode.telemetry.addData("Low", low);
  }
}
