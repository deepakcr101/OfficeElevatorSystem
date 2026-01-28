package com.deepak.project.model;

import java.util.TreeSet;

public class Elevator implements Runnable {
	private final int id;
	private int currentFloor;
	private Direction currentDirection;
	private ElevatorStatus status;

	private final TreeSet<Integer> upRequests;
	private final TreeSet<Integer> downRequest;

	public Elevator(int id) {
		this.id = id;
		this.currentFloor = 0;
		this.currentDirection = Direction.IDLE;
		this.status = ElevatorStatus.STOPPED;
		this.upRequests = new TreeSet<>();
		this.downRequest = new TreeSet<>();
	}

	@Override
	public void run() {
		while (true) {
			processRequests();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
				break;
			}
		}
	}

	private void processRequests() {
	while(!upRequests.isEmpty()) {
		if(currentDirection == Direction.UP || currentDirection== Direction.IDLE) {
			handleUpMovement();
			if(upRequests.isEmpty() && !downRequest.isEmpty()) {
				currentDirection=Direction.DOWN;
			}
			else {
				handleDownMovement();
				if(downRequest.isEmpty() && !upRequests.isEmpty()) {
					currentDirection=Direction.UP;
				}
			}
			currentDirection=
		}
	}
	
}

	public synchronized void addRequest(int floor) {
		if (floor > this.currentFloor) {
			upRequests.add(floor);
		} else if (floor < this.currentFloor) {
			downRequest.add(floor);
		}
	}

	public boolean isMovingTowards(int floor, Direction reqDir) {
		if (this.currentDirection == Direction.IDLE) {
			return true;
		} else if (this.currentDirection == Direction.UP) {
			return floor >= this.currentFloor && (reqDir == Direction.UP);
		}
		return floor <= this.currentFloor && (reqDir == Direction.DOWN);
	}

	public int getId() {
		return id;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public Direction getCurrentDirection() {
		return currentDirection;
	}

	public ElevatorStatus getStatus() {
		return status;
	}
}
