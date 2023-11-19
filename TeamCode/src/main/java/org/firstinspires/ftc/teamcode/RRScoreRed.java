
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.path.PathContinuityViolationException;
import com.acmerobotics.roadrunner.profile.MotionProfile;
import com.acmerobotics.roadrunner.profile.MotionProfileGenerator;
import com.acmerobotics.roadrunner.profile.MotionState;
import com.acmerobotics.roadrunner.trajectory.DisplacementMarker;
import com.acmerobotics.roadrunner.trajectory.DisplacementProducer;
import com.acmerobotics.roadrunner.trajectory.MarkerCallback;
import com.acmerobotics.roadrunner.trajectory.SpatialMarker;
import com.acmerobotics.roadrunner.trajectory.TemporalMarker;
import com.acmerobotics.roadrunner.trajectory.TimeProducer;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.trajectory.TrajectoryMarker;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.acmerobotics.roadrunner.util.Angle;

@Autonomous (preselectTeleOp = "CenterStageTele")
public class RRScoreRed extends OpMode{

    DcMotor rf;
    DcMotor lf;
    DcMotor rb;
    DcMotor lb;
    DcMotor ls;
    DcMotor rs;

    Servo intakeRight;
    Servo intakeLeft;
    Servo pRight;
    Servo pLeft;
    Servo armRight;
    Servo armLeft;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init(){
        rf = hardwareMap.get(DcMotor.class, "motorRF");
        lf = hardwareMap.get(DcMotor.class, "motorLF");
        rb = hardwareMap.get(DcMotor.class, "motorRB");
        lb = hardwareMap.get(DcMotor.class, "motorLB");
        ls = hardwareMap.get(DcMotor.class, "motorLS");
        rs = hardwareMap.get(DcMotor.class, "motorRS");

        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeRight = hardwareMap.get(Servo.class, "intakeRight");
        intakeLeft = hardwareMap.get(Servo.class, "intakeLeft");
        pRight = hardwareMap.get(Servo.class, "plungerRight");
        pLeft = hardwareMap.get(Servo.class, "plungerLeft");
        armRight = hardwareMap.get(Servo.class, "armRight");
        armLeft = hardwareMap.get(Servo.class, "armLeft");

        ls.setDirection(DcMotorSimple.Direction.FORWARD);
        rs.setDirection(DcMotorSimple.Direction.REVERSE);

        rf.setPower(0.0);
        lf.setPower(0.0);
        rb.setPower(0.0);
        lb.setPower(0.0);

        intakeRight.setDirection(Servo.Direction.REVERSE);

        intakeLeft.scaleRange(0.0, 0.55);
        intakeRight.scaleRange(0.22, 1);

        armLeft.scaleRange(0.0, 0.245);
        armRight.scaleRange(0.0, 0.245);

        pRight.scaleRange(0.72, 0.77);
        pLeft.scaleRange(0.60,0.67);

        intakeLeft.setPosition(1.0);
        intakeRight.setPosition(1.0);

        runtime.reset();
    }

    @Override
    public void start(){
        runtime.reset();
    }
    @Override
    public void loop(){

    }
    public void stop(){
        super.stop();
        rf.setPower(0);
        lf.setPower(0);
        rb.setPower(0);
        lb.setPower(0);
        ls.setPower(0);
        rs.setPower(0);
    }
}