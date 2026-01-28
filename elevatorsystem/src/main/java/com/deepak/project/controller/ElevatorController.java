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
        Elevator e = new Elevator(i);
        elevators.add(e);
        new Thread(e).start(); // Start the elevator worker thread
    }
}

	public List<Elevator> getElevators() {
		return elevators;
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

	/**
     * Scoring Logic:
     * 1. If Elevator is IDLE: Score = Distance.
     * 2. If Elevator is moving TOWARDS the floor: Score = Distance.
     * 3. If Elevator is moving AWAY: Score = (Distance to end of current trip) + (Distance from there to floor).
     */

	private int calculateScore(Elevator e, int targetFloor, Direction targetDir) {
		if (e.isMaintenanceMode() || e.isEmergencyStopActive() || e.isOverloaded()) {
        return Integer.MAX_VALUE; // Maximum penalty makes it unselectable
    }
        int distance = Math.abs(e.getCurrentFloor() - targetFloor);

        // Case 1: Ideal pick-up (Idle or on the way)
        if (e.getCurrentDirection() == Direction.IDLE || e.isMovingTowards(targetFloor, targetDir)) {
            return distance;
        }

        // Case 2: Elevator is busy moving in the opposite direction
        // We penalize this heavily because it has to finish its current queue first
        return distance + 100; // 100 is a "penalty" constant
    }

}
