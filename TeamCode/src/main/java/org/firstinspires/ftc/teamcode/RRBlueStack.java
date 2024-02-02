
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.opencv.core.Mat;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@Autonomous (preselectTeleOp = "CenterStageTele")
public class RRBlueStack extends CenterStageAuto{

    FtcDashboard dashboard;
    TelemetryPacket packet;
    //public static MultipleTelemetry dashTelemetry = new MultipleTelemetry();

    boolean slidesPressed;
    public static double intakeUpPos = 0.64;
    public static double intakeDownPos = 0.07;
    public static double armOutPos = 0.1;
    public static double armInPos = 1.0;
    public static double plungerGrabPos = 0.0;
    public static double plungerReleasePos = 1.0;
    double slideDelay = 0.5;

    Trajectory purplePixel1;
    Trajectory purplePixel2;
    Trajectory purplePixel3;
    TrajectorySequence moveUp1;
    TrajectorySequence moveUp2;
    TrajectorySequence moveUp3;
    Trajectory yellowPixel1;
    Trajectory yellowPixel2;
    Trajectory yellowPixel3;
    Trajectory reset1;
    Trajectory reset2;
    Trajectory reset3;
    Trajectory park1;
    Trajectory park2;
    Trajectory park3;

    @Override
    public void init(){

        blue = true;
        super.init();
        drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = new Pose2d(-34.63, 63.54,Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        purplePixel1 = drive.trajectoryBuilder(startPose)
                .lineToLinearHeading(new Pose2d(-24.7, 31.0, Math.toRadians(150)),
                        SampleMecanumDrive.getVelocityConstraint(40, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
                .addDisplacementMarker(()->{
                    armLeft.setPosition(armInPos);
                    pLeft.setPosition(plungerGrabPos);
                    pRight.setPosition(plungerGrabPos);
                })
                .addDisplacementMarker(()->{
                    drive.followTrajectorySequence(moveUp1);
                })
                .build();

        purplePixel2 = drive.trajectoryBuilder(startPose, true)
                .lineTo(new Vector2d(-34.3,32),
                        SampleMecanumDrive.getVelocityConstraint(40, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
                .addDisplacementMarker(()->{
                    armLeft.setPosition(armInPos);
                    pLeft.setPosition(plungerGrabPos);
                    pRight.setPosition(plungerGrabPos);
                })
                .addDisplacementMarker(()->{
                    drive.followTrajectorySequence(moveUp2);
                })
                .build();

        purplePixel3 = drive.trajectoryBuilder(startPose, true)
                .lineTo(new Vector2d(-42.9,34.2),
                        SampleMecanumDrive.getVelocityConstraint(36, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
                .addDisplacementMarker(()->{
                    armLeft.setPosition(armInPos);
                    pLeft.setPosition(plungerGrabPos);
                    pRight.setPosition(plungerGrabPos);
                })
                .addDisplacementMarker(()->{
                    drive.followTrajectorySequence(moveUp3);
                })
                .build();

        //move up
        moveUp1 = drive.trajectorySequenceBuilder(purplePixel1.end())
                .forward(15,
                        SampleMecanumDrive.getVelocityConstraint(15, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
                .splineToLinearHeading(new Pose2d(-27.2, 11.8, Math.toRadians(181)), 0,
                        SampleMecanumDrive.getVelocityConstraint(20, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .back(80.0)
                .build();

        moveUp2 = drive.trajectorySequenceBuilder(purplePixel2.end())
                .forward(8, SampleMecanumDrive.getVelocityConstraint(15, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
                .splineToLinearHeading(new Pose2d(-27.2, 11.8, Math.toRadians(181)), 0,
                        SampleMecanumDrive.getVelocityConstraint(20, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .back(80.0)
                .build();
        moveUp3 = drive.trajectorySequenceBuilder(purplePixel3.end())
                .forward(8, SampleMecanumDrive.getVelocityConstraint(15, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
                .splineToLinearHeading(new Pose2d(-27.2, 11.8, Math.toRadians(181)), 0,
                        SampleMecanumDrive.getVelocityConstraint(20, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .back(80.0)
                .build();

        //parks
       park1 = drive.trajectoryBuilder(reset1.end())
                .splineToLinearHeading(new Pose2d(58.7,11, Math.toRadians(182)),Math.toRadians(0),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
               .addDisplacementMarker(()->{
                   intakeLeft.setPosition(intakeDownPos);
                   intakeRight.setPosition(intakeDownPos);
                   liftTimer.reset();
               })
                .build();

        park2 = drive.trajectoryBuilder(reset2.end())
                .splineToLinearHeading(new Pose2d(58.7,11, Math.toRadians(182)),Math.toRadians(0),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
                .addDisplacementMarker(()->{
                    intakeLeft.setPosition(intakeDownPos);
                    intakeRight.setPosition(intakeDownPos);
                    liftTimer.reset();
                })
                .build();

        park3 = drive.trajectoryBuilder(reset3.end())
                .lineToLinearHeading(new Pose2d(58.7,11, Math.toRadians(182)),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
                .addDisplacementMarker(()->{
                    intakeLeft.setPosition(intakeDownPos);
                    intakeRight.setPosition(intakeDownPos);
                    liftTimer.reset();
                })
                .build();
    }
    @Override
    public void start(){
        super.start();
    }

    @Override
    public void childLoop() {
        super.childLoop();
        telemetry.addData("target", slidePositionTarget);
        telemetry.addData("loopvision", pipeline.getOutput());
        switch (currentState) {
            case PURPLE:
                followPurple();
                if (!drive.isBusy()) {
                    currentState = AutoState.MOVEUP;
                }
            case MOVEUP:
                followMOVEUP();
                if (!drive.isBusy()) {
                    currentState = AutoState.YELLOW;
                }
            case YELLOW:
                followYellow();
                if (!drive.isBusy()) {
                    currentState = AutoState.RESET;
                }
            case RESET:
                followReset();
                if (!drive.isBusy()) {
                    currentState = AutoState.PARK;
                }
            case CYCLE:
                followCycle();
                break;
            case PARK:
                followPark();
                if (!drive.isBusy()) {
                    currentState = AutoState.IDLE;
                }
                break;
            case IDLE:
                super.stop();
        }
    }

    public void followPurple(){
        if(elementPosition == 0){
            drive.followTrajectoryAsync(purplePixel1);
        }
        else if(elementPosition == 1){
            drive.followTrajectoryAsync(purplePixel2);
        }
        else{
            drive.followTrajectoryAsync(purplePixel3);
        }
        liftTimer.reset();
    }

    @Override
    public void followMOVEUP() {
        if(elementPosition == 0){
            drive.followTrajectorySequenceAsync(moveUp1);
        }
        else if(elementPosition == 1){
            drive.followTrajectorySequenceAsync(moveUp2);
        }
        else{
            drive.followTrajectorySequenceAsync(moveUp3);
        }
        liftTimer.reset();
    }

    @Override
    public void followYellow(){
        if(liftTimer.seconds() > slideDelay){
            slidePositionTarget = 500.0;
        }

        if(elementPosition == 0){
            drive.followTrajectoryAsync(yellowPixel1);
        }
        else if(elementPosition == 1){
            drive.followTrajectoryAsync(yellowPixel2);
        }
        else{
            drive.followTrajectoryAsync(yellowPixel3);
        }
        liftTimer.reset();
    }
    @Override
    public void followReset(){
        if(elementPosition == 0){
            drive.followTrajectoryAsync(reset1);
        }
        else if(elementPosition == 1){
            drive.followTrajectoryAsync(reset2);
        }
        else{
            drive.followTrajectoryAsync(reset3);
        }
        liftTimer.reset();
    }

    @Override
    public void followPark(){
        slidePositionTarget = 0.0;
        if(elementPosition == 0){
            drive.followTrajectoryAsync(park1);
        }
        else if(elementPosition == 1){
            drive.followTrajectoryAsync(park2);
        }
        else{
            drive.followTrajectoryAsync(park3);
        }

        intakeLeft.setPosition(intakeDownPos);
        intakeRight.setPosition(intakeDownPos);
        currentState = AutoState.IDLE;
        liftTimer.reset();
    }
    public void stop(){
        super.stop();
        rf.setPower(0);
        lf.setPower(0);
        rb.setPower(0);
        lb.setPower(0);
        ls.setPower(0);
        rs.setPower(0);
        intake.setPower(0);
    }
}