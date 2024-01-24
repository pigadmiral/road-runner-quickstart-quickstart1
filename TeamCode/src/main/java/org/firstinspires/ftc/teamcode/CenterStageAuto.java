package org.firstinspires.ftc.teamcode;

import static android.provider.SyncStateContract.Helpers.update;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.VisionPipeline;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@Disabled
public abstract class CenterStageAuto extends CenterStageOpMode implements AutoInterface {
    int elementPosition;
    double delay = 0.0;

    enum State {
        PURPLE,
        YELLOW,
        CYCLE,
        PARK,
        IDLE
    }

    State currentState;
    VisionPipeline pipeline;

    @Override
    public void init() {
        super.init();
        currentState = State.PURPLE;
        pipeline = new VisionPipeline(blue, true);
        webcam.setPipeline(pipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                //error?
            }
        });
    }

    @Override
    final public void init_loop() {
        telemetry.addData("pipeline", pipeline.getPipelineTelemetry() + "     " + pipeline.getOutput());
        telemetry.addData("delay time", delay);

        telemetry.update();

        if(gamepad1.dpad_up && !gamepad1prev.dpad_up){
            delay += 500.0;
        }else if(gamepad1.dpad_down && !gamepad1prev.dpad_down){
            if(delay <= 500.0){
                delay = 0.0;
            }
            else{
                delay -= 500.0;
            }
        }else{
            delay = delay;
        }

        telemetry.addData("delay time", delay);
        gamepad1prev.copy(gamepad1);
        gamepad2prev.copy(gamepad2);
    }

    @Override
    public void start() {
        ElapsedTime wait = new ElapsedTime();
        while(wait.milliseconds() < delay){
            telemetry.addData("delay time", wait.milliseconds());
            telemetry.update();
        }
        super.start();
        elementPosition = pipeline.getOutput();

       /* switch (currentState)
            case State.PURPLE:
                switch(elementPosition)
                    case 0:
                        nvjonvnoivnoi
                        break;
                    case 1:
                        nfuriuberiubviueriu
                        break;
                    case 2:
                        ngiogiorngoierng
                        break;
            case State.YELLOW:
                switch(elementPosition)
                    case 0:
                        nvjonvnoivnoi
                        break;
                    case 1:
                        nfuriuberiubviueriu
                        break;
                    case 2:
                        ngiogiorngoierng
                        break;
            case State.CYCLE:
            do more and more stuff
                break;
        case State.PARK:
            do more and more and more stuff
                break;
        default:
            currentState = State.IDLE; */
    }


    @Override
    public void childLoop() {
        super.childLoop();
        drive.update();
        //danielNotShowUp = 3.0;
    }
}
