# Office Elevator System Simulation

A comprehensive Java-based simulation of an elevator control system for a multi-floor office building. This project demonstrates advanced software engineering principles including concurrency, design patterns, and system simulation.

## 📋 Project Overview

**ElevatorSystem** is a software-only simulation that manages multiple elevators in a building with 12 floors and 8 elevators. The system efficiently allocates requests to elevators, simulates movement, manages door operations, and handles emergency situations.

### Key Features

- **Multi-Floor Support**: 12 floors with optimized request allocation
- **8 Concurrent Elevators**: Independent elevator controllers with thread-based simulation
- **Intelligent Scheduling**: Smart elevator allocation algorithm that considers:
  - Elevator idle status
  - Direction of travel
  - Current position vs. request floor
  - Maintenance and emergency modes
  - Weight capacity constraints
- **Safety Features**:
  - Emergency stop mechanism with evacuation support
  - Overload detection and handling (max 800 kg capacity)
  - Maintenance mode with graceful shutdown
- **Real-Time Monitoring**: Status dashboard with live elevator tracking
- **Logging System**: Comprehensive logging to both console and file (`elevator_system.log`)
- **Concurrent Request Simulation**: Handles multiple simultaneous elevator requests

## 🏗️ Architecture

### Project Structure

```
elevatorsystem/
├── src/
│   ├── main/
│   │   └── java/com/deepak/project/
│   │       ├── Main.java                 # Entry point and simulation orchestration
│   │       ├── controller/
│   │       │   └── ElevatorController.java    # Central elevator dispatcher
│   │       ├── model/
│   │       │   ├── Direction.java             # Enum for movement direction
│   │       │   ├── Elevator.java              # Elevator state and behavior
│   │       │   └── ElevatorStatus.java        # Enum for elevator states
│   │       └── service/
│   │           ├── LoggingService.java        # Centralized logging (singleton)
│   │           └── StatusDashboard.java       # Real-time status display
│   └── test/                            # Test classes directory
├── pom.xml                              # Maven build configuration
├── target/                              # Compiled output
├── SRS.md                               # Software Requirements Specification
├── AssumptionsAndConstraints.md         # Design assumptions and constraints
└── README.md                            # This file
```

### Core Components

#### **1. ElevatorController**
Central dispatcher that:
- Manages all 8 elevators
- Allocates incoming requests to the best available elevator
- Implements scoring algorithm to optimize wait times
- Filters out unavailable elevators (maintenance, emergency, overloaded)

```java
Scoring Logic:
- Idle or moving towards: Score = Distance
- Moving away: Score = Distance + 100 (penalty)
- Max penalties: Maintenance, Emergency, Overload → Integer.MAX_VALUE
```

#### **2. Elevator**
Individual elevator with:
- State management (floor, direction, status)
- Request queue using TreeSet (separate UP and DOWN queues)
- Concurrent processing with thread-safe operations
- Movement simulation with 500ms per floor
- Door management with 1000ms opening

#### **3. LoggingService**
Singleton logger providing:
- Timestamped console output
- File logging to `elevator_system.log`
- Thread-safe operations for concurrent logging

#### **4. StatusDashboard**
Real-time monitor displaying:
- Current floor position for each elevator
- Movement direction status
- Updates every 2 seconds

## 🔧 Technical Details

### Technology Stack

- **Language**: Java 17
- **Build Tool**: Maven 3.x+
- **Runtime**: OpenJDK 11 or higher (tested with Java 17)
- **Concurrency**: Java Thread API with TreeSet and synchronized collections
- **Logging**: File I/O with timestamps

### Design Patterns Used

1. **Singleton Pattern**: LoggingService for centralized logging
2. **Strategy Pattern**: Scoring algorithm for elevator allocation
3. **Thread Pool Pattern**: ExecutorService for concurrent request simulation
4. **Observer Pattern**: StatusDashboard monitoring elevators
5. **Runnable Interface**: Elevator as independent worker threads

### Thread Safety

- Synchronized methods for shared state modifications
- TreeSet ordering for efficient floor request management
- Concurrent request processing with daemon threads
- Atomic door operations preventing race conditions

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Version 17 or higher
- **Apache Maven**: Version 3.6.0 or higher
- **Git** (optional, for cloning the repository)

### Installation

1. **Clone or download the project**:
```bash
cd c:\Users\deepak\Projects\OfficeElevatorSystem
```

2. **Navigate to the project directory**:
```bash
cd elevatorsystem
```

3. **Build the project with Maven**:
```bash
mvn clean compile
```

### Running the Simulation

Execute the main class:
```bash
mvn exec:java -Dexec.mainClass="com.deepak.project.Main"
```

Or compile and run directly:
```bash
mvn clean package
java -cp target/elevatorsystem-1.0-SNAPSHOT.jar com.deepak.project.Main
```

### Example Simulation Output on My Machine: 

The simulation will:
1. Initialize 8 elevators starting at floor 0
2. Start a real-time status dashboard (updating every 2 seconds)
3. Simulate emergency scenarios on Elevator 0
4. Add weight overload scenario on Elevator 1
5. Place Elevators 0 and 1 in maintenance mode
6. Process 15 concurrent elevator requests from random floors
7. Log all events to console and `elevator_system.log` file

Output:
```
Starting Elevator System Simulation ...

--- ELEVATOR SYSTEM SNAPSHOT ---
Elevator [0] | Floor: 0 | Status: IDLE      
Elevator [1] | Floor: 0 | Status: IDLE
Elevator [2] | Floor: 0 | Status: IDLE      
Elevator [3] | Floor: 0 | Status: IDLE
Elevator [4] | Floor: 0 | Status: IDLE
Elevator [5] | Floor: 0 | Status: IDLE
Elevator [6] | Floor: 0 | Status: IDLE      
Elevator [7] | Floor: 0 | Status: IDLE
--------------------------------


--- ELEVATOR SYSTEM SNAPSHOT ---
Elevator [0] | Floor: 0 | Status: IDLE      
Elevator [1] | Floor: 0 | Status: IDLE      
Elevator [2] | Floor: 0 | Status: IDLE      
Elevator [3] | Floor: 0 | Status: IDLE      
Elevator [4] | Floor: 0 | Status: IDLE      
Elevator [5] | Floor: 0 | Status: IDLE      
Elevator [6] | Floor: 0 | Status: IDLE      
Elevator [7] | Floor: 0 | Status: IDLE      
--------------------------------


[SIMULATION] Manually triggering emergency on Elevator 0...
!!! EMERGENCY STOP TRIGGERED ON ELEVATOR 0 !!!

--- ELEVATOR SYSTEM SNAPSHOT ---
Elevator [0] | Floor: 0 | Status: IDLE      
Elevator [1] | Floor: 0 | Status: IDLE      
[SIMULATION] Adding heavy load to Elevator 1...
Elevator 1 OVERLOADED! Current weight: 1000kg
Elevator [2] | Floor: 0 | Status: IDLE      
Elevator [3] | Floor: 0 | Status: IDLE      
Elevator [4] | Floor: 0 | Status: IDLE      
Elevator [5] | Floor: 0 | Status: IDLE      
Elevator [6] | Floor: 0 | Status: IDLE      
Elevator [7] | Floor: 0 | Status: IDLE      
--------------------------------

[SYSTEM] Taking Elevator 0 and 1 offline for maintenance...
Elevator 0 entering Maintenance Mode (Finishing current tasks...)
Elevator 1 entering Maintenance Mode (Finishing current tasks...)
Request received from floor 1 going UP
Request received from floor 2 going UP
Request received from floor 6 going DOWN
Request received from floor 1 going UP
Request received from floor 3 going UP
[2026/01/28 23:33:42] Elevator 2 moved to Floor 1
[2026/01/28 23:33:42] Elevator 2 STOPPED at Floor 1 - Doors Opening
Request received from floor 7 going DOWN
Request received from floor 5 going DOWN
Request received from floor 9 going DOWN
Request received from floor 2 going UP
Request received from floor 1 going UP
[2026/01/28 23:33:42] Elevator 2 STOPPED at Floor 1 - Doors Opening

--- ELEVATOR SYSTEM SNAPSHOT ---
Elevator [0] | Floor: 0 | Status: IDLE
Elevator [1] | Floor: 0 | Status: IDLE
Elevator [2] | Floor: 1 | Status: IDLE
Elevator [3] | Floor: 0 | Status: IDLE
Elevator [4] | Floor: 0 | Status: IDLE
Elevator [5] | Floor: 0 | Status: IDLE
Elevator [6] | Floor: 0 | Status: IDLE
Elevator [7] | Floor: 0 | Status: IDLE
--------------------------------

Request received from floor 6 going DOWN
Request received from floor 6 going DOWN
Request received from floor 9 going DOWN
Request received from floor 0 going UP
[2026/01/28 23:33:44] Elevator 3 STOPPED at Floor 0 - Doors Opening
[2026/01/28 23:33:44] Elevator 2 moved to Floor 2
[2026/01/28 23:33:44] Elevator 2 STOPPED at Floor 2 - Doors Opening
Request received from floor 8 going DOWN

--- ELEVATOR SYSTEM SNAPSHOT ---
Elevator [0] | Floor: 0 | Status: IDLE
Elevator [1] | Floor: 0 | Status: IDLE
Elevator [2] | Floor: 2 | Status: IDLE
Elevator [3] | Floor: 0 | Status: IDLE
Elevator [4] | Floor: 0 | Status: IDLE
Elevator [5] | Floor: 0 | Status: IDLE
Elevator [6] | Floor: 0 | Status: IDLE
Elevator [7] | Floor: 0 | Status: IDLE
--------------------------------

[2026/01/28 23:33:45] Elevator 2 moved to Floor 3
[2026/01/28 23:33:45] Elevator 2 STOPPED at Floor 3 - Doors Opening

--- ELEVATOR SYSTEM SNAPSHOT ---
Elevator [0] | Floor: 0 | Status: IDLE
Elevator [1] | Floor: 0 | Status: IDLE
Elevator [2] | Floor: 3 | Status: IDLE
Elevator [3] | Floor: 0 | Status: IDLE
Elevator [4] | Floor: 0 | Status: IDLE
Elevator [5] | Floor: 0 | Status: IDLE
Elevator [6] | Floor: 0 | Status: IDLE
Elevator [7] | Floor: 0 | Status: IDLE
--------------------------------

[2026/01/28 23:33:47] Elevator 2 moved to Floor 4
[2026/01/28 23:33:48] Elevator 2 moved to Floor 5
[2026/01/28 23:33:48] Elevator 2 STOPPED at Floor 5 - Doors Opening

--- ELEVATOR SYSTEM SNAPSHOT ---
Elevator [0] | Floor: 0 | Status: IDLE
Elevator [1] | Floor: 0 | Status: IDLE
Elevator [2] | Floor: 5 | Status: IDLE
Elevator [3] | Floor: 0 | Status: IDLE
Elevator [4] | Floor: 0 | Status: IDLE
Elevator [5] | Floor: 0 | Status: IDLE
Elevator [6] | Floor: 0 | Status: IDLE
Elevator [7] | Floor: 0 | Status: IDLE
--------------------------------

[2026/01/28 23:33:49] Elevator 2 moved to Floor 6
[2026/01/28 23:33:49] Elevator 2 STOPPED at Floor 6 - Doors Opening
[2026/01/28 23:33:51] Elevator 2 moved to Floor 7
[2026/01/28 23:33:51] Elevator 2 STOPPED at Floor 7 - Doors Opening

--- ELEVATOR SYSTEM SNAPSHOT ---
Elevator [0] | Floor: 0 | Status: IDLE
Elevator [1] | Floor: 0 | Status: IDLE
Elevator [2] | Floor: 7 | Status: IDLE
Elevator [3] | Floor: 0 | Status: IDLE
Elevator [4] | Floor: 0 | Status: IDLE
Elevator [5] | Floor: 0 | Status: IDLE
Elevator [6] | Floor: 0 | Status: IDLE
Elevator [7] | Floor: 0 | Status: IDLE
--------------------------------

[2026/01/28 23:33:52] Elevator 2 moved to Floor 8
[2026/01/28 23:33:52] Elevator 2 STOPPED at Floor 8 - Doors Opening

--- ELEVATOR SYSTEM SNAPSHOT ---
Elevator [0] | Floor: 0 | Status: IDLE
Elevator [1] | Floor: 0 | Status: IDLE
Elevator [2] | Floor: 8 | Status: IDLE
Elevator [3] | Floor: 0 | Status: IDLE
Elevator [4] | Floor: 0 | Status: IDLE
Elevator [5] | Floor: 0 | Status: IDLE
Elevator [6] | Floor: 0 | Status: IDLE
Elevator [7] | Floor: 0 | Status: IDLE
--------------------------------

[2026/01/28 23:33:54] Elevator 2 moved to Floor 9
[2026/01/28 23:33:54] Elevator 2 STOPPED at Floor 9 - Doors Opening

--- ELEVATOR SYSTEM SNAPSHOT ---
Elevator [0] | Floor: 0 | Status: IDLE
Elevator [1] | Floor: 0 | Status: IDLE
Elevator [2] | Floor: 9 | Status: IDLE
Elevator [3] | Floor: 0 | Status: IDLE
Elevator [4] | Floor: 0 | Status: IDLE
Elevator [5] | Floor: 0 | Status: IDLE
Elevator [6] | Floor: 0 | Status: IDLE
Elevator [7] | Floor: 0 | Status: IDLE
--------------------------------


--- ELEVATOR SYSTEM SNAPSHOT ---
Elevator [0] | Floor: 0 | Status: IDLE
Elevator [1] | Floor: 0 | Status: IDLE
Elevator [2] | Floor: 9 | Status: IDLE
Elevator [3] | Floor: 0 | Status: IDLE
Elevator [4] | Floor: 0 | Status: IDLE
Elevator [5] | Floor: 0 | Status: IDLE
Elevator [6] | Floor: 0 | Status: IDLE
Elevator [7] | Floor: 0 | Status: IDLE
--------------------------------
```

## 📊 Simulation Features

### Request Allocation Algorithm

When a request arrives:
1. Calculate score for each available elevator
2. Filter out unavailable elevators (maintenance, emergency, overload)
3. Select elevator with minimum score
4. Add request to selected elevator's queue

### Elevator Movement

- **Speed**: 500ms per floor (configurable)
- **Door Operation**: 1000ms open time
- **Safety Checks**: Emergency stop and overload detection during movement
- **Direction Management**: Switches between UP and DOWN based on request queue

### Safety Features Demonstrated

1. **Emergency Stop**:
   - Clears all pending requests
   - Prevents further movement
   - Requires manual reset
   - Logged with error messages

2. **Overload Protection**:
   - Weight tracking (simulated)
   - Maximum capacity: 800 kg
   - Elevator halts if exceeded
   - Can be reset by removing weight

3. **Maintenance Mode**:
   - Gracefully completes current requests
   - Rejects new requests
   - Low-power polling when idle
   - Can be remotely enabled/disabled

## 📝 Configuration

### Building Blocks Configuration

To modify system parameters, edit values in:

- **Number of Floors**: Currently hardcoded to 12 in logic
- **Number of Elevators**: Parameter in `Main.java` line 14: `new ElevatorController(8)`
- **Floor Travel Time**: `Elevator.java` line ~101: `Thread.sleep(500)`
- **Door Duration**: `Elevator.java` line ~109: `Thread.sleep(1000)`
- **Dashboard Refresh**: `StatusDashboard.java` line ~20: `Thread.sleep(2000)`
- **Max Weight Capacity**: `Elevator.java` line ~15: `private final int MAX_WEIGHT = 800`

### System Properties

Edit `pom.xml` to change compilation target:
```xml
<maven.compiler.source>17</maven.compiler.source>
<maven.compiler.target>17</maven.compiler.target>
```

## 🧪 Testing Scenarios

The Main class includes built-in testing scenarios:

1. **Concurrent Requests**: 15 simultaneous requests from random floors
2. **Emergency Handling**: Demonstrates emergency stop and recovery
3. **Overload Simulation**: Tests weight capacity constraints
4. **Maintenance Mode**: Shows graceful degradation with offline elevators

## 📈 Performance Considerations

- **Scalability**: Algorithm supports 12+ floors and 8+ elevators
- **Response Time**: O(n) where n = number of elevators
- **Throughput**: Handles multiple concurrent requests efficiently
- **Memory**: Minimal overhead with TreeSet-based queuing
- **I/O**: Asynchronous logging prevents blocking main thread

## 🔍 Error Corrections Applied

The following errors were identified and corrected:

1. **Package Declaration Errors** (Direction.java, ElevatorStatus.java):
   - **Issue**: Incorrect package `java.deepak.project.model`
   - **Fix**: Changed to `com.deepak.project.model`

2. **Missing Method** (ElevatorController.java):
   - **Issue**: `getElevators()` method not defined
   - **Fix**: Added public getter method for elevator list

3. **Missing Method** (Elevator.java):
   - **Issue**: `isEmergencyStopActive()` called but not implemented
   - **Fix**: Added synchronized method returning emergency status

4. **Unused Variable** (Main.java):
   - **Issue**: Declared `requestId` variable never used
   - **Fix**: Removed unused variable declaration

## 📚 Documentation

- **SRS.md**: Complete Software Requirements Specification (287 lines)
- **AssumptionsAndConstraints.md**: Design assumptions and system constraints (124 lines)
- **README.md**: This comprehensive guide

## 🤝 Contributing

To extend this project:

1. **Add new scheduling algorithms**: Implement in `ElevatorController.calculateScore()`
2. **Enhance safety features**: Extend `Elevator` class with new safety mechanisms
3. **Implement GUI**: Create JavaFX/Swing dashboard instead of console
4. **Add metrics**: Track average wait time, throughput, etc.
5. **Implement destination control**: Let users select floor after boarding


## 📝 License

This project is created for educational/learning purposes.

## 👨‍💻 Author

**Deepak Kumar**

## 📞 Support

For issues or questions about the system behavior, refer to:
- `SRS.md` for functional requirements
- `AssumptionsAndConstraints.md` for design decisions
- Console output and `elevator_system.log` for runtime behavior

---

**Last Updated**: January 28, 2026
