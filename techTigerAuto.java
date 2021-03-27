package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.ArmServos;

@Autonomous(name = "techTigerAuto ", group = "")
public class techTigerAuto extends LinearOpMode {
  
  private DcMotor FR;
  private DcMotor BR;
  private DcMotor FL;
  private DcMotor BL;
  
  
  // This contains all the stuff for the arm servos
  private ArmServos armServos;
  
  /**
   * Entrance point of the program
   */
  @Override
  public void runOpMode() {
    telemetry.addLine("Init");
    telemetry.update();
    armServos = new ArmServos(this);
    ArmServos.setup();
    
    FR = hardwareMap.dcMotor.get("FR");
    BR = hardwareMap.dcMotor.get("BR");
    FL = hardwareMap.dcMotor.get("FL");
    BL = hardwareMap.dcMotor.get("BL");
    

    // Put initialization blocks here.
    FR.setDirection(DcMotorSimple.Direction.REVERSE);
    BR.setDirection(DcMotorSimple.Direction.REVERSE);
    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      armServos.setWobbleHolderPosition(0.779);
      armServos.setArcPosition(0.49,true);
      sleep(100);
      // Move to the side for wobble goal
      setAndSleepMotorRows(0.4,-0.4,350);
      brakeMotors();
      
      //Move forward to capture the wobble goal
      setAndSleepMotors(0.5,0.5,0.5,0.5,450);
      brakeMotors();
      
      
      // Move over more to the side for the ring
      setAndSleepMotorRows(0.43,-0.43,550);
      brakeMotors();
      
      //Get to the line
      setAndSleepMotors(0.5,0.5,0.5,0.5,2350);
      brakeMotors();
      
      // Move over more to get the wobble goal inplace
      setAndSleepMotorRows(0.4,-0.4,75);
      brakeMotors();
      
      //Rotate Robot 180 degress
      setAndSleepMotorSides(0.4,-0.4,(long)(13.0*180));
      brakeMotors();
      
      //Release wobble goal
      armServos.setArcPosition(0.65,true);
      sleep(300);
      
      // Move back from wobble goal
      setAndSleepMotors(-0.5,-0.5,-0.5,-0.5,450);
      brakeMotors();
      
      // Move over more to the side for the ring goal
      setAndSleepMotorRows(0.4,-0.4,900);
      brakeMotors();
      
      // Move back to the ring goal
      setAndSleepMotors(-0.5,-0.5,-0.5,-0.5,725);
      brakeMotors();
      
      // Move back to the ring goal slow
      setAndSleepMotors(-0.25,-0.25,-0.25,-0.25,500);
      brakeMotors();
      
      // Deliver rings
      armServos.setArcPosition(0.76+0.12,true);
      sleep(1000);
      
      armServos.setArcPosition(0.63,true);
      sleep(100);
      
      // Undo Move back to the ring goal slow
      setAndSleepMotors(0.15,0.15,0.15,0.15,100);
      brakeMotors();
      
      // Undo Move back to the ring goal
      setAndSleepMotors(0.5,0.5,0.5,0.5,725);
      brakeMotors();
      
      // Move over more to the side to miss the wobble goal
      setAndSleepMotorRows(0.4,-0.4,350);
      brakeMotors();
      
      // Undo Move back from wobble goal
      setAndSleepMotors(0.5,0.5,0.5,0.5,650);
      brakeMotors();
      
      sleep(750);

      // HACK: Attempt to fix weird Servo Bug
      armServos.setArcPosition(0.0);
      sleep(100);
    }
  }
  void setAndSleepMotorRows(double front, double back, long sleepDuration)
  {
    setAndSleepMotors(front,-front,back,-back,sleepDuration);
  }
  void setAndSleepMotorSides(double right, double left, long sleepDuration)
  {
    setAndSleepMotors(right,left,right,left,sleepDuration);
  }
  /**
   * Sets motor power, updates telemetry, and sleeps
   * @param frontRight Motor Power for front right motor
   * @param frontLeft Motor Power for front left motor
   * @param backRight Motor Power for back right motor
   * @param backLeft Motor Power for back left motor
   */
  void setAndSleepMotors(double frontRight, double frontLeft, double backRight, double backLeft, long sleepDuration)
  {
    FR.setPower(frontRight);
    FL.setPower(frontLeft);
    // Workaround for the fact that one of our motors is 223 while the rest are above 300
    BR.setPower(backRight/0.85); 
    BL.setPower(backLeft);
    tele();
    sleep(sleepDuration);
  }
  
  /**
   * Sets motor power to zero and sleeps for 300ms
   */
  void brakeMotors()
  {
    setAndSleepMotorRows(0,0,300);
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
