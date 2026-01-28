package com.deepak.project.controller;

import java.util.ArrayList;
import java.util.List;

import com.deepak.project.model.Direction;
import com.deepak.project.model.Elevator;

public class ElevatorController {
	private final List<Elevator> elevators;

	public ElevatorController(int numElevators) {
		elevators = new ArrayList<>();
		for (int i = 0; i < numElevators; i++) {
			elevators.add(new Elevator(i));
		}
	}

	public synchronized void requestElevator(int floor, Direction targetDirection) {
		System.out.println("Request received from floor " + floor + " going " + targetDirection);
		Elevator bestElevator = findBestElevator(floor, targetDirection);
		bestElevator.addRequest(floor);
	}

	private Elevator findBestElevator(int floor, Direction targetDirection) {
		Elevator bestElevator = null;
		int minScore = Integer.MAX_VALUE;

		for (Elevator e : elevators) {
			int score = calculateScore(e, floor, targetDirection);
			if (score < minScore) {
				minScore = score;
				bestElevator = e;
			}
		}
		return bestElevator;
	}

	private int calculateScore(Elevator e, int floor, Direction targetDirection) {
		int distance = Math.abs(floor - e.getCurrentFloor());
		if (e.getCurrentDirection() == Direction.IDLE || e.isMovingTowards(floor, targetDirection)) {
			return distance;
		}
		// penalising
		return distance + 100;
	}

}
