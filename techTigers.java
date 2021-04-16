package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.Rotation;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.ArmServos;
import org.firstinspires.ftc.teamcode.SuperSecretTelemetry;


@TeleOp(name = "techTigers", group = "")
public class techTigers extends LinearOpMode
{
  private double pi = 3.141592653589793;
  
  private DcMotor FL;
  private DcMotor BL;
  private DcMotor FR;
  private DcMotor BR;
  private DcMotor Nothing;

  boolean g1_a;
  boolean g1_b;
  boolean g1_x;
  boolean g1_y;

  double g1_lStick_x;
  double g1_lStick_y;
  double g1_lStick_x_abs;
  double g1_lStick_y_abs;

  double g1_rStick_x;
  double g1_rStick_y;
  double g1_rStick_x_abs;
  double g1_rStick_y_abs;

  int q;
  
  // This contains all the stuff for the arm servos
  private ArmServos armServos;
  
  private SuperSecretTelemetry sst;
  
  /**
   * Entrance point of the program
   */
  @Override
  public void runOpMode() {
    armServos = new ArmServos(this);
    ArmServos.setup();
    sst = new SuperSecretTelemetry(this);
    armServos.setArcPosition(armServos.low+0.03);
    FL = hardwareMap.dcMotor.get("FL");
    BL = hardwareMap.dcMotor.get("BL");
    FR = hardwareMap.dcMotor.get("FR");
    BR = hardwareMap.dcMotor.get("BR");
    //Nothing = hardwareMap.dcMotor.get("Nothing");
    FR.setDirection(DcMotorSimple.Direction.REVERSE);
    BR.setDirection(DcMotorSimple.Direction.REVERSE);
    waitForStart();
    if (opModeIsActive())
    {
      while (opModeIsActive())
      {
         //Nothing.setPower(1.0);
        if(gamepad1.right_bumper)
        {
          armServos.low+=0.000025;
          armServos.setArcPosition(armServos.low);
          if(armServos.low > 0.3)
          {
            armServos.low = 0.3;
          }
        }
        else if(gamepad1.left_bumper)
        {
          armServos.low-=0.000025;
          armServos.setArcPosition(armServos.low);
          if(armServos.low < 0)
          {
            armServos.low = 0;
          }
        }
        
        if(gamepad1.y)
        {
          armServos.setArcPosition(0.78);
        }
        else if(gamepad1.b)
        {
          armServos.setArcPosition(0.3);
        }
        else if(gamepad1.a)
        {
          armServos.setArcPosition(armServos.low);
        }
        else if(gamepad1.back)
        {
          if(gamepad1.left_trigger > 0.5)
          {
            armServos.addToArcServos(-0.00025);
          }
          else if(gamepad1.right_trigger > 0.5)
          {
            armServos.addToArcServos(+0.00025);
          }
          else if(gamepad1.dpad_right)
          {
            armServos.addToWobbleHolderServos(0.00025);
          }
          else if(gamepad1.dpad_left)
          {
            armServos.addToWobbleHolderServos(-0.00025);
          }
          else if(gamepad1.dpad_up)
          {
            armServos.internalServoOffset+=0.00025;
            armServos.setArcPosition(armServos.mid);
          }
          else if(gamepad1.dpad_down)
          {
            armServos.internalServoOffset-=0.00025;
            armServos.setArcPosition(armServos.mid);
          }
        }
        else if(gamepad1.left_trigger > 0.5)
        {
          armServos.setWobbleHolderPosition(0.779);
        }
        else if(gamepad1.right_trigger > 0.5)
        {
          armServos.setWobbleHolderPosition(0.09);
        }
        
        //g1_x = gamepad1.x;
        
        g1_lStick_x = gamepad1.left_stick_x;
        g1_lStick_y = gamepad1.left_stick_y;
  
        g1_rStick_x = gamepad1.right_stick_x;
        g1_rStick_y = gamepad1.right_stick_y;
        
        g1_lStick_x_abs = Math.abs(g1_lStick_x);
        g1_lStick_y_abs = Math.abs(g1_lStick_y);
        
        g1_rStick_x_abs = Math.abs(g1_rStick_x);
        g1_rStick_y_abs = Math.abs(g1_rStick_y);
        translateAndRotate();
        
        sst.addTelemetry();
        telemetry.addData("q", q);
        telemetry.addData("FL Pow", FL.getPower());
        telemetry.addData("FR Pow", FR.getPower());
        telemetry.addData("BL Pow", BL.getPower());
        telemetry.addData("BR Pow", BR.getPower());
        armServos.addTelemetry();
        //sensors.update();
        //sensors.addTelemetry();
        telemetry.update();
      }
    }
  }
  
  
  /**
   * Translates and rotates the robot
   */
  private void translateAndRotate()
  {
    double FRD = 0.0;
    double FLD = 0.0;
    double BRD = 0.0;
    double BLD = 0.0;
    /*
    
    // Begin Rotate
    if(g1_rStick_x<-0.01)
    {
      FLD-=g1_rStick_x_abs;FRD+=g1_rStick_x_abs;
      BLD-=g1_rStick_x_abs;BRD+=g1_rStick_x_abs;
    }
    else if(g1_rStick_x>0.01)
    {
      FLD+=g1_rStick_x_abs;FRD-=g1_rStick_x_abs;
      BLD+=g1_rStick_x_abs;BRD-=g1_rStick_x_abs;
    }
    
    // X and Y are swapped because it works
    double theta = Math.atan2(-g1_lStick_y,g1_lStick_x);
    double magnitude = Math.sqrt( (g1_lStick_x*g1_lStick_x) + (g1_lStick_y*g1_lStick_y*1.2) );
    double sin1 = Math.sin(theta+45)*magnitude;
    double sin2 = Math.sin(theta-45)*magnitude;
    
    telemetry.addData("theta", theta);
    telemetry.addData("magnitude", magnitude);
    telemetry.addData("sin1", sin1);
    telemetry.addData("sin2", sin2);
    
    FLD+=sin1;FRD+=sin2;
    BLD+=sin2;BRD+=sin1;
    
    FL.setPower(FLD*0.77);FR.setPower(FRD*0.85);
    BL.setPower(BLD*0.77);BR.setPower(BRD*1.00);
    */
    double theta = Math.atan2(-gamepad1.left_stick_y,gamepad1.left_stick_x);
    double magnitude = Math.sqrt(gamepad1.left_stick_x*gamepad1.left_stick_x + gamepad1.left_stick_y*gamepad1.left_stick_y);
    
    if(g1_lStick_x_abs < 0.5)
    {
      magnitude*=1.2;
    }
    
    double sin1 = Math.sin(theta+45)*magnitude*1.5;
    double sin2 = Math.sin(theta-45)*magnitude*1.5;
    double rotation = gamepad1.right_stick_x;
    
    
    telemetry.addData("theta", theta);
    telemetry.addData("magnitude", magnitude);
    telemetry.addData("sin1", sin1);
    telemetry.addData("sin2", sin2);
    
    FLD = (sin1+rotation);FRD=(sin2-rotation);
    BLD = (sin2+rotation);BRD=(sin1-rotation);
    
    FLD = FLD > 1.0 ? 1.0 : FLD;FRD = FRD > 1.0 ? 1.0 : FRD;
    BLD = BLD > 1.0 ? 1.0 : BLD;BRD = BRD > 1.0 ? 1.0 : BRD;
    
    //FL.setPower(sin1+Rotation);
    //FR.setPower(sin2-Rotation);
    //BL.setPower(sin2+Rotation);
    //BR.setPower(sin1-Rotation);
    FL.setPower(FLD*0.77);FR.setPower(FRD*0.85);
    BL.setPower(BLD*0.77);BR.setPower(BRD*1.00);
  }
}
