package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.Rotation;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.ArmServos;
import org.firstinspires.ftc.teamcode.RingDetector;

@Autonomous(name = "techTigerAuto ", group = "")
public class techTigerAuto extends LinearOpMode {
  
  private DcMotor FR;
  private DcMotor BR;
  private DcMotor FL;
  private DcMotor BL;
  
  
  // This contains all the stuff for the arm servos
  private ArmServos armServos;
  
  private RingDetector ringDetector;
  /**
   * Entrance point of the program
   */
  @Override
  public void runOpMode() {
    telemetry.addLine("Initializing");
    telemetry.update();
    armServos = new ArmServos(this);
    armServos.setup();
    ringDetector = new RingDetector(this);
    ringDetector.setup();
    
    FR = hardwareMap.dcMotor.get("FR");
    BR = hardwareMap.dcMotor.get("BR");
    FL = hardwareMap.dcMotor.get("FL");
    BL = hardwareMap.dcMotor.get("BL");
    
    // Put initialization blocks here.
    FR.setDirection(DcMotorSimple.Direction.REVERSE);
    BR.setDirection(DcMotorSimple.Direction.REVERSE);
    while(!opModeIsActive() && !isStopRequested())
    {
      ringDetector.update();
      telemetry.addLine("Initialized");
      telemetry.addData("Ring Count",ringDetector.ringCount);
      telemetry.update();
    }
    if (opModeIsActive() && !isStopRequested()) {
      // Put run blocks here.
      //armServos.setWobbleHolderPosition(0.779);
      armServos.setArcPosition(armServos.low+0.05);
      sleep(100);
      
      //Move forward so that rings don't drag on wall
      moveForwardAndSleep(0.5,100);
      brakeMotors();
      
      armServos.setArcPosition(armServos.low+0.1);
      sleep(100);
      
      //Move over to line up with A
      moveLeftAndSleep(0.5,700);
      brakeMotors();
      
      //Get to A
      moveForwardAndSleep(0.5,2625);
      brakeMotors();
      
      //Get Square
      switch(ringDetector.ringCount)
      {
        case 0:
          moveLeftAndSleep(0.5,150);
          brakeMotors();
          break;
        case 1:
          moveRightAndSleep(0.5,1000);
          brakeMotors();
          moveForwardAndSleep(0.5,1000);
          brakeMotors();
          break;
        case 4:
          moveRightAndSleep(0.5,100);
          brakeMotors();
          moveForwardAndSleep(0.5,2500);
          brakeMotors();
          moveLeftAndSleep(0.5,750);
          brakeMotors();
          break;
      }
      
      //Release wobble goal
      armServos.setArcPosition(0.65,true);
      sleep(1300);
      
      //Move away from wobble goal
      moveForwardAndSleep(-0.5,100);
      brakeMotors();
      
      // Make it so that you can go forward
      moveRightAndSleep(0.5,1000);
      brakeMotors();
      
      //Get to ring goal
      switch(ringDetector.ringCount)
      {
        case 0:
          moveRightAndSleep(0.5,100);
          brakeMotors();
          moveForwardAndSleep(0.5,2750);
          brakeMotors();
          break;
        case 1:
          moveForwardAndSleep(0.5,2000);
          brakeMotors();
          moveLeftAndSleep(0.5,1000);
          brakeMotors();
          break;
        case 4:
          moveRightAndSleep(0.5,650);
          brakeMotors();
          break;
      } 

          moveLeftAndSleep(0.5,250);
          brakeMotors();
      // Move back to hit wall for ring goal
      moveForwardAndSleep(0.5,750);
      brakeMotors();
      
      armServos.setArcPosition(armServos.mid);
      sleep(1000);
      
      // Deliver rings
      armServos.setArcPosition(0.76+0.12,true);
      sleep(1000);
      
      armServos.setArcPosition(armServos.mid);
      sleep(1000);
      
      // Deliver rings
      armServos.setArcPosition(0.76+0.12,true);
      sleep(1000);
      
      armServos.setArcPosition(0.63,true);
      sleep(100);
      
      // Move back from the ring goal
      moveForwardAndSleep(-0.5,350);
      brakeMotors();
      
      // Move over more to the side to miss the wobble goal
      moveRightAndSleep(0.4,ringDetector.ringCount==1?1750:350);
      brakeMotors();
      
      // Move to the line
      moveForwardAndSleep(-0.5,1750);
      brakeMotors();
      
      sleep(750);

    }
    // HACK: Attempt to fix weird Servo Bug
    armServos.setAllZero();
  }
  
  /**
   * Moves robot forward or backward if you pass a negative power
   * @param power Motor power
   * @param sleepDuration Time for robot to sleep
   */
  void moveForwardAndSleep(double power, long sleepDuration)
  {
    setAndSleepMotors(power, power, power, power, sleepDuration);
  }
  
  /**
   * Moves robot forward or backward if you pass a negative power
   * @param power Motor power
   * @param sleepDuration Time for robot to sleep
   */
  void moveRightAndSleep(double power, long sleepDuration)
  {
    setAndSleepMotors(power, -power, -power, power, sleepDuration);
  }
  
  /**
   * Moves robot forward or backward if you pass a negative power
   * @param power Motor power
   * @param sleepDuration Time for robot to sleep
   */
  void moveLeftAndSleep(double power, long sleepDuration)
  {
    setAndSleepMotors(-power, power, power, -power, sleepDuration);
  }
  
  /**
   * Rotates robot "clockwise"
   * @param power Motor power
   * @param sleepDuration Time for robot to sleep
   */
  void rotateAndSleep(double power, long sleepDuration)
  {
    setAndSleepMotors(power, -power, power, -power, sleepDuration);
  }
  
  /**
   * Sets motor power, updates telemetry, and sleeps
   * @param frontLeft Motor Power for front left motor
   * @param frontRight Motor Power for front right motor
   * @param backLeft Motor Power for back left motor
   * @param backRight Motor Power for back right motor
   * @param sleepDuration Time for robot to sleep
   */
  void setAndSleepMotors(double frontLeft, double frontRight, double backLeft, double backRight, long sleepDuration)
  {
   
    FL.setPower(-frontLeft*0.77);FR.setPower(-frontRight*0.85);
    BL.setPower(-backLeft*0.77); BR.setPower(-backRight*1.00); 
    /*FR.setPower(-frontRight);
    FL.setPower(-frontLeft);
    // Workaround for the fact that one of our motors is 223 while the rest are above 300
    BR.setPower(-backRight/0.86); 
    BL.setPower(-backLeft);*/
    tele();
    sleep(sleepDuration);
  }
  
  /**
   * Sets motor power to zero and sleeps for 300ms
   */
  void brakeMotors()
  {
    setAndSleepMotors(0,0,0,0,300);
  }
  
  /**
   * Updates telemetry
   */
  private void tele()
  {
    //sensors.addTelemetry();
    telemetry.addData("FL Pow", FL.getPower());
    telemetry.addData("FR Pow", FR.getPower());
    telemetry.addData("BL Pow", BL.getPower());
    telemetry.addData("BR Pow", BR.getPower());
    armServos.addTelemetry();
    telemetry.update();
  }
}
