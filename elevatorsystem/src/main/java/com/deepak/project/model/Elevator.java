package com.deepak.project.model;

import java.util.TreeSet;

import com.deepak.project.service.LoggingService;

public class Elevator implements Runnable {
	private final int id;
	private int currentFloor;
	private Direction currentDirection;
	private ElevatorStatus status;
private boolean emergencyStop = false;
    private int currentWeight = 0;
    private final int MAX_WEIGHT = 800; // kg
private boolean maintenanceMode = false;

	// Using a TreeSet to keep stops in order
	private final TreeSet<Integer> upRequests;
	private final TreeSet<Integer> downRequests;

	public Elevator(int id) {
		this.id = id;
		this.currentFloor = 0;
		this.currentDirection = Direction.IDLE;
		this.status = ElevatorStatus.STOPPED;
		this.upRequests = new TreeSet<>();
		this.downRequests = new TreeSet<>();
	}

	@Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (!maintenanceMode) {
                processRequests();
            } else {
                // If maintenance is active and queue is empty, we just wait/idle
                if (upRequests.isEmpty() && downRequests.isEmpty()) {
                    try {
                        Thread.sleep(2000); // Low-power polling
                    } catch (InterruptedException e) {
                        break;
                    }
                } else {
                    processRequests(); // Finish current queue first
                }
            }
        }
    }


	private void processRequests() {
        while (!upRequests.isEmpty() || !downRequests.isEmpty()) {
            if (currentDirection == Direction.UP || currentDirection == Direction.IDLE) {
                handleUpMovement();
                if (upRequests.isEmpty() && !downRequests.isEmpty()) currentDirection = Direction.DOWN;
            } else {
                handleDownMovement();
                if (downRequests.isEmpty() && !upRequests.isEmpty()) currentDirection = Direction.UP;
            }
        }
        currentDirection = Direction.IDLE;
    }
	
	public synchronized void setMaintenanceMode(boolean active) {
        this.maintenanceMode = active;
        if (active) {
            System.out.println("Elevator " + id + " entering Maintenance Mode (Finishing current tasks...)");
        } else {
            System.out.println("Elevator " + id + " is back online.");
        }
    }

    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }

private void handleUpMovement() {
        while (!upRequests.isEmpty()) {
            Integer nextStop = upRequests.ceiling(currentFloor);
            if (nextStop == null) break;

            moveToFloor(nextStop);
            upRequests.remove(nextStop);
            openDoors();
        }
    }

	private void handleDownMovement() {
        while (!downRequests.isEmpty()) {
            Integer nextStop = downRequests.floor(currentFloor);
            if (nextStop == null) break;

            moveToFloor(nextStop);
            downRequests.remove(nextStop);
            openDoors();
        }
    }

	private void moveToFloor(int target) {
    while (currentFloor != target) {
        // Safety Check 1: Emergency Stop
        if (emergencyStop) return;

        // Safety Check 2: Overload
        if (isOverloaded()) {
            System.out.println("Elevator " + id + " stuck at floor " + currentFloor + " due to overload.");
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            continue; // Wait until weight is reduced
        }

        try {
            Thread.sleep(500);
            if (target > currentFloor) currentFloor++;
            else currentFloor--;
            
            LoggingService.getInstance().log("Elevator " + id + " moved to Floor " + currentFloor);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

    private void openDoors() {
        LoggingService.getInstance().log("Elevator " + id + " STOPPED at Floor " + currentFloor + " - Doors Opening");
        try { Thread.sleep(1000); } catch (InterruptedException e) {} // Simulate passenger entry/exit
    }



	// Synchronized methods to add requests safely
    public synchronized void addRequest(int floor) {
        if (floor > currentFloor) upRequests.add(floor);
        else if (floor < currentFloor) downRequests.add(floor);
        else openDoors(); // Already there
    }
	
	public boolean isMovingTowards(int floor, Direction reqDir) {
		if (this.currentDirection == Direction.IDLE) {
			return true;
		}
		
		if (this.currentDirection == Direction.UP) {
			return floor >= this.currentFloor && (reqDir == Direction.UP);
		}
		return floor <= this.currentFloor && (reqDir == Direction.DOWN);
	}

	public synchronized void triggerEmergencyStop() {
        this.emergencyStop = true;
        this.upRequests.clear();
        this.downRequests.clear();
        System.err.println("!!! EMERGENCY STOP TRIGGERED ON ELEVATOR " + id + " !!!");
    }

    public synchronized boolean isEmergencyStopActive() {
        return emergencyStop;
    }

    public synchronized void resetEmergency() {
        this.emergencyStop = false;
        System.out.println("Elevator " + id + " safety reset. Resuming operations.");
    }

    public synchronized boolean isOverloaded() {
        return currentWeight > MAX_WEIGHT;
    }

	public synchronized void updateWeight(int weightChange) {
        this.currentWeight += weightChange;
        if (isOverloaded()) {
            System.err.println("Elevator " + id + " OVERLOADED! Current weight: " + currentWeight + "kg");
        }
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
