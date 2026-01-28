package com.deepak.project.model;

import java.util.TreeSet;

public class Elevator {
  private final int id;
  private int currentFloor;
  private Direction currentDirection;
  private ElevatorStatus status;
  
  
  private final TreeSet<Integer> upRequests;
  private final TreeSet<Integer> downRequest;
  
  public Elevator(int id) {
	  this.id=id;
	  this.currentFloor=0;
	  this.currentDirection=Direction.IDLE;
	  this.status=ElevatorStatus.STOPPED;
	  this.upRequests=new TreeSet<>();
	  this.downRequest=new TreeSet<>();
  }
  
  public synchronized void addRequest(int floor) {
	  if(floor>this.currentFloor) {
		  upRequests.add(floor);
	  }
	  else if(floor < this.currentFloor) {
		  downRequest.add(floor);
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
