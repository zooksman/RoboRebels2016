package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.RobotMap;
import org.stlpriory.robotics.commands.drivetrain.DriveWithGamepad;
import org.stlpriory.robotics.utils.Constants;
import org.stlpriory.robotics.utils.Debug;
import org.stlpriory.robotics.utils.Ramper;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TestTankDrivetrain extends Subsystem {
    CANTalon rightFront;
    Talon rightRear;
    Talon leftFront;
    Talon leftRear;
    Ramper leftRamper;
    Ramper rightRamper;
    RobotDrive drive;

    public TestTankDrivetrain() {
        Debug.println("[test drivetrain Subsystem] Instantiating...");
        //next two lines for testing encoder/talon srx
        rightFront = new CANTalon(RobotMap.RIGHT_FRONT_CAN_TALON_CHANNEL);
        initTalon(this.rightFront);
        rightRear = new Talon(RobotMap.RIGHT_REAR_TALON_CHANNEL);
        leftFront = new Talon(RobotMap.LEFT_FRONT_TALON_CHANNEL);
        leftRear = new Talon(RobotMap.LEFT_REAR_TALON_CHANNEL);
        leftRamper = new Ramper();
        rightRamper = new Ramper();
        drive = new RobotDrive(leftFront, leftRear, rightFront, rightRear);
        drive.setInvertedMotor(MotorType.kFrontLeft, true);
        drive.setInvertedMotor(MotorType.kFrontRight, true);
        drive.setInvertedMotor(MotorType.kRearLeft, true);
        drive.setInvertedMotor(MotorType.kRearRight, true);
        Debug.println("[DriveTrain Subsystem] Instantiation complete.");
    }

    public void stop() {
        drive.tankDrive(0, 0);
    }

    public void tankDrive(double leftValue, double rightValue)
    {
        drive.tankDrive(leftRamper.scale(leftValue), rightRamper.scale(rightValue));
        System.out.println(rightFront.getEncVelocity());
    }
    public void tankDrive(Joystick joystick)
    {
        // This should be the left stick y axis and the right stick y axis. 
        tankDrive(joystick.getRawAxis(1), joystick.getRawAxis(5));
    }

    public void initDefaultCommand() {
        Debug.println("[DriveTrain.initDefaultCommand()] Setting default command to " 
                + DriveWithGamepad.class.getName());
        setDefaultCommand(new DriveWithGamepad());
    }
    private void initTalon(final CANTalon talon) {
        talon.setPID(Constants.TALON_PROPORTION, Constants.TALON_INTEGRATION, Constants.TALON_DIFFERENTIAL, Constants.TALON_FEEDFORWARD, 0,
                     0, 0);
        talon.reverseSensor(true);
    }

}
