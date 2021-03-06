/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Falcons;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class EncoderTalon extends PIDCommand {
  /**
   * Creates a new EncoderTalon.
   */
   
  public EncoderTalon(double targetRotations, Falcons falcon) {
    
    super(
        // The controller that the command will use
        new PIDController(0.1, 0.02, 0.0),
        // This should return the measurement
        falcon::posRotations,
        // This should return the setpoint (can also be a constant)
        targetRotations,
        // This uses the output
        output -> {
          // Use the output here
          falcon.driveForward(output * 0.1);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(falcon);
    falcon.reset();
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(0.01);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
