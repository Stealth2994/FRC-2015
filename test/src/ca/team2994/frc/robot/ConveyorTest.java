package ca.team2994.frc.robot;

import static org.junit.Assert.*;
import mockit.Delegate;
import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.junit.Test;

import ca.team2994.frc.mechanism.Conveyor;

public class ConveyorTest {
	
	@Mocked
	private Motor motor;

	double motorValue;
	double conveyorValue;
	int numb = 2;
	
	
	@Test
	public void conveyorMockTest() {
		new NonStrictExpectations() {{
			motor.set(anyDouble);
			result = new Delegate<Double>() {
				@SuppressWarnings("unused")
				void set(double speed) {
					System.out.println("Speed is " + speed);
					motorValue = speed;
				}
				
			};
			motor.get();
			result = new Delegate<Double>() {
				@SuppressWarnings("unused")
				double get() {
					if(numb != 0) {
						numb--;
					}
					else {
						System.out.println("Speed is " + motorValue);
					}
					return motorValue;
				}
				
			};	
			motor.disable();
			result = new Delegate<Double>() {
				@SuppressWarnings("unused")
				void disable() {
					System.out.println("Disabled");
				}
				
			};				
		}};
		
		Constants.readConstantPropertiesFromFile();

		Conveyor conveyor = new Conveyor(motor);
		
		conveyor.load();
		
		assertTrue(motor.get() == -Constants.getConstantAsDouble(Constants.CONVEYOR_SPEED));
		
		conveyor.unload();
		
		assertTrue(motor.get() == Constants.getConstantAsDouble(Constants.CONVEYOR_SPEED));
		
		conveyor.stop();
	}
}