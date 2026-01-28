package com.deepak.project.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggingService {
    private static LoggingService instance;
    private static final String LOG_FILE = "elevator_system.log";
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private LoggingService() {
        // Initialize file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, false))) {
            writer.write("--- Elevator System Log Started ---\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized LoggingService getInstance() {
        if (instance == null) instance = new LoggingService();
        return instance;
    }

    public synchronized void log(String message) {
        String timestamp = dtf.format(LocalDateTime.now());
        String entry = String.format("[%s] %s", timestamp, message);
        
        // Print to console and file
        System.out.println(entry);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(entry + "\n");
        } catch (IOException e) {
            System.err.println("Logging failed: " + e.getMessage());
        }
    }
}