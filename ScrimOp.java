package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

//Declare TeleOp
@TeleOp
public class ScrimOp extends LinearOpMode {
    // Declaring Motors
    public DcMotor Lift       = null;
    public DcMotor frontLeft  = null;
    public DcMotor backLeft   = null;
    public DcMotor frontRight = null;
    public DcMotor backRight  = null;
    //public Servo   Claw       = null;


    double clawOffset = 0;
    double liftOffset = 0;

    public static final double MID_LIFT        =0.02;
    public static final double MID_SERVO       =  1 ;
    public static final double CLAW_SPEED      = 0.5 ;       // sets rate to move servo


    @Override
    public void runOpMode(){
        //set variables up
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        Lift  = hardwareMap.dcMotor.get("Lift");
        //Claw = hardwareMap.servo.get("Claw");

        //Most mechnum wheels will need to have wheels reversed
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);




        //setting up break behavior
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Code will for start to be pressed
        waitForStart();
        resetRuntime();

        //Loop for TeleOp
        while (opModeIsActive()) {

            //Code for* lift right bumper extends claw up and sets power to 0 if not being pressed
            if (gamepad1.right_bumper)
                Lift.setPower(1);
            else
                Lift.setPower(0.10);

            if (gamepad1.left_bumper)
                Lift.setPower(-1);
            else
                Lift.setPower(0.10);
/*
            if (gamepad2.a)
                clawOffset+=CLAW_SPEED;
            else if (gamepad1.b)
                clawOffset-=CLAW_SPEED;

            clawOffset = Range.clip(clawOffset, -0.5, 0.5);
            Claw.setPosition(MID_SERVO + clawOffset);
*/

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeft.setPower(frontLeftPower / 1.5);
            backLeft.setPower(-backLeftPower / 1.5);
            frontRight.setPower(frontRightPower / 1.5);
            backRight.setPower(-backRightPower / 1.5);

            telemetry.addData("Status", "Run Time: ");
            telemetry.addData("Motors", "left (%.2f), right (%.2f)");
            telemetry.update();
        }


    }

}
