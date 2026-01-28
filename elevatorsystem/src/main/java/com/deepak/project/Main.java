package com.deepak.project;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.deepak.project.controller.ElevatorController;
import com.deepak.project.model.Direction;

public class Main {
	public static void main(String[] args) {

		ElevatorController controller = new ElevatorController(8);
		ExecutorService simulationExecutor = Executors.newFixedThreadPool(15);
		Random random = new Random();
		System.out.println("Starting Elevator System Simulation ...");

		for (int i = 0; i < 15; i++) {
			final int requestId = i;
			simulationExecutor.execute(() -> {
				int currentFloor = random.nextInt(12);
				int targetDir = random.nextInt(2);
				Direction dir = (targetDir == 2) ? Direction.UP : Direction.DOWN;
				controller.requestElevator(currentFloor, dir);
			});
		}

		simulationExecutor.shutdown();
	}
}