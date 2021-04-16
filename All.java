package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "All (Blocks to Java)")
@Disabled
public class All extends LinearOpMode {

  private DcMotor FL;
  private DcMotor BL;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    double k;

    FL = hardwareMap.get(DcMotor.class, "FL");
    BL = hardwareMap.get(DcMotor.class, "BL");

    // Put initialization blocks here.
    FL.setDirection(DcMotorSimple.Direction.REVERSE);
    k = 0;
    boolean nope = false;
    waitForStart();
    if (opModeIsActive()&&!nope) {
      // Put run blocks here.
      while (opModeIsActive()) {
        if (gamepad1.a) {
          while (k >= 0) {
            k += -0.0005;
            BL.setPower(k);
            telemetry.addData("k", k);
            telemetry.update();
          }
          k = 0;
          nope = true;
          break;
        }
        if (k < 1) {
          k += 0.00005;
        }
        BL.setPower(k);
        // Put loop blocks here.
        telemetry.addData("k", k);
        telemetry.update();
      }
    }
  }
}
