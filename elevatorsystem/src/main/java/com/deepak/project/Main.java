package com.deepak.project;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.deepak.project.controller.ElevatorController;
import com.deepak.project.model.Direction;
import com.deepak.project.service.StatusDashboard;

public class Main {
	public static void main(String[] args) {

		ElevatorController controller = new ElevatorController(8);
		// Start Dashboard
        Thread dashboardThread = new Thread(new StatusDashboard(controller.getElevators()));
        dashboardThread.setDaemon(true); // Stop when main ends
        dashboardThread.start();
		// Simulate 15 Concurrent Requests
        ExecutorService passengerPool = Executors.newFixedThreadPool(15);
        Random random = new Random();

		System.out.println("Starting Elevator System Simulation ...");
try {
    Thread.sleep(5000); 
    System.out.println("\n[SIMULATION] Manually triggering emergency on Elevator 0...");
    controller.getElevators().get(0).triggerEmergencyStop();
    
    Thread.sleep(2000);
    System.out.println("[SIMULATION] Adding heavy load to Elevator 1...");
    controller.getElevators().get(1).updateWeight(1000); // Exceeds 800kg
} catch (InterruptedException e) {
    e.printStackTrace();
}
try {
        Thread.sleep(2000);
        System.out.println("[SYSTEM] Taking Elevator 0 and 1 offline for maintenance...");
        controller.getElevators().get(0).setMaintenanceMode(true);
        controller.getElevators().get(1).setMaintenanceMode(true);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
	
		for (int i = 0; i < 15; i++) {
            passengerPool.execute(() -> {
                try {
                    // Random delay to simulate real-world arrival
                    Thread.sleep(random.nextInt(3000)); 
                    
                    int floor = random.nextInt(10); // 0 to 9
                    Direction dir = (floor < 5) ? Direction.UP : Direction.DOWN;
                    
                    controller.requestElevator(floor, dir);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

		passengerPool.shutdown();
	}
}