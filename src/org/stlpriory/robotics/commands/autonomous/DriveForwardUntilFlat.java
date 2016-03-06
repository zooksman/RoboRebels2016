package org.stlpriory.robotics.commands.autonomous;

import org.stlpriory.robotics.commands.autonomous.DriveForwardTime;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem.Direction;
import org.stlpriory.robotics.Robot;

public class DriveForwardUntilFlat extends DriveForwardTime {
    private static final int FLAT_TICK_CEILING;

    private boolean hasTilted;
    private int flatTicks;
    public DriveUntilFlat(Direction direction)
    {
        super(0, direction);
    }
    public DriveUntilFlat()
    {
        super(0, Direction.FORWARD);
    }
    protected void initialize()
    {
        super.initialize();
        flatTicks = 0;
        hasTilted = false;
    }
    @Override
    protected boolean isFinished()
    {
        // If we haven't tilted at all yet (ie, we are still on flat ground from the beginning), we will 
        // never return true.
        if(!hasTilted)
        {
            if(Robot.drivetrain.isFlat())
            {
                hasTilted = true;
            }
            return false;
        }
        if(!Robot.drivetrain.isFlat())
        {
            flatTicks = 0;
            return false;
        }
        else
        {
            // If there is a flat spot in the middle of the obstacle, naive code would
            // stop there, and we'd be screwed. I imagine that we couldn't be on a flat spot for
            // more than 5 ticks, so I make sure we've been flat for more time than that before stopping.
            flatTicks++;
            if(flatTicks > FLAT_TICK_CEILING)
            {
                return true;
            }
        }
        return false;
    } 
}
