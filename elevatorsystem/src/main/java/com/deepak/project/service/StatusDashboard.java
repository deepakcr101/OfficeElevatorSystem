package com.deepak.project.service;

import com.deepak.project.model.Elevator;
import java.util.List;

public class StatusDashboard implements Runnable {
    private final List<Elevator> elevators;

    public StatusDashboard(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    @Override
    public void run() {
        while (true) {
            printStatus();
            try {
                Thread.sleep(2000); // Update every 2 seconds
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private void printStatus() {
        System.out.println("\n--- ELEVATOR SYSTEM SNAPSHOT ---");
        for (Elevator e : elevators) {
            String dir = e.getCurrentDirection().toString();
            System.out.printf("Elevator [%d] | Floor: %d | Status: %-10s\n", 
                              e.getId(), e.getCurrentFloor(), dir);
        }
        System.out.println("--------------------------------\n");
    }
}