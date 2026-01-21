# Software Requirements Specification (SRS)

**Project Name:** ElevatorSystem (Office Building Elevator Management System)

**Version:** 1.0

**Prepared by:** Deepak Kumar

**Date:** January 21, 2026

---

## 1. Introduction

### 1.1 Purpose

This Software Requirements Specification (SRS) document formally defines the functional and non-functional requirements of the ElevatorSystem project. The purpose of this document is to provide a clear, complete, and unambiguous description of system behavior to guide design, implementation, testing, and validation.

This document is intended for:

* Developers implementing the system
* Evaluators/reviewers assessing correctness and completeness
* Future maintainers extending the system

---

### 1.2 Scope

The ElevatorSystem is a **software-only simulation** of an elevator control system for an office building. The system manages multiple elevators, processes external floor requests, allocates an optimal elevator to each request, and simulates elevator movement, door operations, and emergency handling.

The system supports:

* 12 floors
* 8 elevators
* External requests via floor + direction (UP/DOWN)
* Internal elevator controls: Open Gate, Close Gate, Emergency Alarm

The primary objective is to minimize passenger waiting time while efficiently utilizing all available elevators.

---

### 1.3 Definitions, Acronyms, and Abbreviations

* **SRS:** Software Requirements Specification
* **FR:** Functional Requirement
* **NFR:** Non-Functional Requirement
* **HLD:** High-Level Design
* **LLD:** Low-Level Design
* **Idle Elevator:** Elevator not currently servicing any request

---

### 1.4 References

* IEEE 830 / IEEE 29148 – Software Requirements Specification standards
* Assumptions & Constraints Document – ElevatorSystem

---

## 2. Overall Description

### 2.1 Product Perspective

The ElevatorSystem is a standalone Java-based simulation application. It follows a centralized control architecture where a central controller receives requests and delegates allocation decisions to a scheduler component.

Each elevator operates as an independent logical unit with clearly defined states and behavior, coordinated by the controller.

---

### 2.2 Product Functions (High-Level)

At a high level, the system shall:

* Accept elevator requests from floors
* Allocate the most suitable elevator for each request
* Simulate elevator movement between floors
* Control elevator door operations
* Handle emergency situations
* Support multiple concurrent requests

---

### 2.3 User Classes and Characteristics

| User Class              | Description                                                               |
| ----------------------- | ------------------------------------------------------------------------- |
| General User            | Simulates a person requesting an elevator by entering floor and direction |
| System Operator (Admin) | Simulates maintenance/emergency reset operations                          |

---

### 2.4 Operating Environment

* Programming Language: Java
* Build Tool: Maven
* Runtime: OpenJDK 11 or higher
* Interface: Console-based input/output
* Platform: Windows / Linux / macOS

---

### 2.5 Design and Implementation Constraints

* Software-only simulation; no hardware integration
* Must use Java and Maven
* Console-based UI
* Deterministic scheduling algorithm

---

### 2.6 Assumptions and Dependencies

* Floor and direction are manually provided as input for simulation
* Travel time between floors is uniform
* Elevator capacity is not modeled
* Emergency resets are performed manually

(Refer to the Assumptions & Constraints document for full details.)

---

## 3. System Features and Functional Requirements

### 3.1 Feature: External Elevator Request Handling

**Description:** Allows a user to request an elevator from a given floor in a specified direction.

**FR-1:** The system shall allow a user to input a floor number between 1 and 12.

**FR-2:** The system shall allow a user to specify a direction (UP or DOWN).

**FR-3:** The system shall validate that the requested direction is valid for the given floor.

---

### 3.2 Feature: Elevator Allocation

**Description:** Determines the optimal elevator to service a request.

**FR-4:** The system shall evaluate all available elevators when allocating a request.

**FR-5:** The system shall prioritize idle elevators closest to the request floor.

**FR-6:** The system shall prefer elevators already moving in the requested direction when possible.

**FR-7:** The system shall enqueue requests when no suitable elevator is immediately available.

---

### 3.3 Feature: Elevator Movement Simulation

**Description:** Simulates elevator travel between floors.

**FR-8:** The system shall move the allocated elevator toward the request floor.

**FR-9:** The system shall update the current floor of the elevator after each movement step.

**FR-10:** The system shall prevent direction changes while servicing a request unless required.

---

### 3.4 Feature: Door Control

**Description:** Manages elevator door operations.

**FR-11:** The system shall open the elevator gate upon arrival at the destination floor.

**FR-12:** The system shall close the elevator gate before initiating movement.

**FR-13:** The system shall prevent door operations while the elevator is in motion.

---

### 3.5 Feature: Emergency Handling

**Description:** Handles emergency situations triggered inside the elevator.

**FR-14:** The system shall provide an emergency alarm control for each elevator.

**FR-15:** Activating the emergency alarm shall immediately halt elevator movement.

**FR-16:** An elevator in emergency state shall not accept new requests.

**FR-17:** The system shall allow an administrator to reset an elevator from emergency state.

---

### 3.6 Feature: System Monitoring

**Description:** Provides visibility into system status.

**FR-18:** The system shall display the current state and floor of each elevator.

**FR-19:** The system shall log request handling and elevator state transitions.

---

## 4. External Interface Requirements

### 4.1 User Interface

* Console-based input for user requests
* Console output for system responses, elevator movements, and logs

---

### 4.2 Software Interfaces

* No external software dependencies beyond Java standard libraries and Maven-managed test libraries

---

### 4.3 Communication Interfaces

* Not applicable (single-process system)

---

## 5. Non-Functional Requirements

### 5.1 Performance Requirements

* The system shall allocate an elevator within a negligible computation time relative to travel simulation time.

### 5.2 Reliability Requirements

* The system shall continue operating correctly under multiple sequential and concurrent requests.

### 5.3 Scalability Requirements

* The design shall allow easy extension to support additional elevators or floors.

### 5.4 Usability Requirements

* The system shall provide clear prompts and readable output messages.

### 5.5 Maintainability Requirements

* The system shall follow modular design and object-oriented principles.

### 5.6 Safety Requirements (Simulation Context)

* Emergency operations shall always override normal scheduling behavior.

---

## 6. System Models

This version of the SRS references the following models to be provided as separate deliverables:

* Use Case Diagram
* Class Diagram
* Sequence Diagram
* State Diagram (Elevator lifecycle)

---

## 7. Validation and Acceptance Criteria

The system shall be considered acceptable when:

* All functional requirements FR-1 to FR-19 are implemented and tested.
* Emergency handling behaves as specified.
* Scheduler behavior matches documented allocation rules.
* The system builds successfully using Maven and runs on OpenJDK 11+.

---

## 8. Future Enhancements (Out of Scope)

* Destination control inside elevator
* Load/capacity-based scheduling
* Graphical or web-based user interface
* Advanced scheduling algorithms (AI/ML-based)

---

## 9. Revision History

| Version | Date       | Description         |
| ------- | ---------- | ------------------- |
| 1.0     | 2026-01-21 | Initial SRS release |

---

*End of Software Requirements Specification*
