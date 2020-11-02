/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Falcons extends SubsystemBase {
  /**
   * Creates a new Falcons.
   */

  private TalonFX talon10 = new TalonFX(10); 

  final TalonFXInvertType kFxInvertType = TalonFXInvertType.Clockwise;
  final NeutralMode kBrakeDurNeutral = NeutralMode.Brake;

  final int kUnitsPerRevolution = 2048;

  int _loops = 0;


  public Falcons() {

    TalonFXConfiguration configs = new TalonFXConfiguration();

    configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;

    talon10.configAllSettings(configs);
    talon10.setInverted(kFxInvertType);
    talon10.setNeutralMode(kBrakeDurNeutral);



  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    double appliedMotorOutput = talon10.getMotorOutputPercent();
	int selSenPos = talon10.getSelectedSensorPosition(0); /* position units */
	int selSenVel = talon10.getSelectedSensorVelocity(0); /* position units per 100ms */

	/* scaling depending on what user wants */
	double pos_Rotations = (double) selSenPos / kUnitsPerRevolution;
	double vel_RotPerSec = (double) selSenVel / kUnitsPerRevolution * 10; /* scale per100ms to perSecond */
	double vel_RotPerMin = vel_RotPerSec * 60.0;

	/*
	 * Print to console. This is also a good oppurtunity to self-test/plot in Tuner
	 * to see how the values match.
	 * 
	 * Note these prints can cause "Loop time of 0.02s overrun" errors in the console.
	 * This is because prints are slow.
	 */
	if (++_loops >= 10) {
		_loops = 0;
		System.out.printf("Motor-out: %.2f | ", appliedMotorOutput);
		System.out.printf("Pos-units: %d | ", selSenPos);
		System.out.printf("Vel-unitsPer100ms: %d | ", selSenVel);
		System.out.printf("Pos-Rotations:%.3f | ", pos_Rotations);
		System.out.printf("Vel-RPS:%.1f | ", vel_RotPerSec);
		System.out.printf("Vel-RPM:%.1f | ", vel_RotPerMin);
		System.out.println();
	}

  }

  public void driveWithJoystics(Joystick joy) {
    talon10.set(ControlMode.PercentOutput, joy.getY());
  }

  public void driveForward(double speed) {
    talon10.set(ControlMode.PercentOutput, speed);
  }

  public double posRotations() {
    return talon10.getSelectedSensorPosition() / kUnitsPerRevolution;
  }

  public double velRPS() {
    return talon10.getSelectedSensorVelocity() / kUnitsPerRevolution * 10;
  }

  public double velRPM (){
    return velRPS() * 60;
  }

  public void reset() {
    talon10.setSelectedSensorPosition(0);
  }



}
