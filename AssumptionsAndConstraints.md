# Assumptions & Constraints

**Project:** ElevatorSystem (Office Building Simulation)

**Prepared By:** Deepak Kumar

**Date:** January 21, 2026

---

## 1. Purpose

This document lists the explicit assumptions and constraints that govern the design and implementation of the ElevatorSystem Java (Maven) project. The goal is to reduce ambiguity, make implicit expectations explicit, and provide a single reference to guide design, testing, and validation.

---

## 2. Scope

The system is a *software-only simulation* of an elevator control system for an office building with the following baseline parameters used for development and testing:

* **Floors:** 12 (numbered 1..12)
* **Elevators:** 8
* **User interaction:** Console input of current floor and direction (UP/DOWN) to simulate a call button
* **Functional controls simulated inside each elevator:** Open Gate, Close Gate, Emergency Alarm

The system will manage allocation, movement simulation, door operations, request queuing, and emergency handling. It will not interface with physical hardware.

---

## 3. Assumptions

### 3.1. Domain / Functional Assumptions

1. **Single building, single shaft system view:** All elevators serve the same set of floors in the same building (no zoning or dedicated express shafts).
2. **Uniform floor spacing and travel time:** Travel time between any two adjacent floors is constant and deterministic (a configurable constant in the simulation).
3. **Capacity ignored:** Elevator load/capacity (weight or person count) is out of scope. All dispatched elevators are assumed capable of carrying waiting passengers.
4. **Binary internal controls only:** Internal elevator functions exposed for the simulation are limited to `OPEN_GATE`, `CLOSE_GATE`, and `EMERGENCY_ALARM`.
5. **User input model:** For simulation, the user explicitly provides the current floor and desired direction (UP/DOWN). In reality, the floor would be detected by hardware; this is simulated via input.
6. **Request semantics:** A single external request represents a call from a floor wanting to go in a specified direction; internal destination selection after boarding is not modeled beyond optional extensions.
7. **Emergency semantics:** When `EMERGENCY_ALARM` is triggered inside an elevator, that elevator transitions to a non-operational safety state and stops responding to new requests until manually reset (simulated via admin command).
8. **Doors and safety:** Doors cannot open while the elevator is in motion. Door open/close events are atomic in simulation (no partial states required).

### 3.2. Scheduling & Performance Assumptions

1. **Deterministic scheduler:** The scheduler is deterministic given the same state and request queue; randomization is not used in allocation decisions.
2. **Single request per user action:** Each simulated button press generates one external request; duplicate presses are treated as identical requests and coalesced if applicable.
3. **Finite queueing:** Requests are queued; the system assumes memory sufficient for queued requests during normal operation.
4. **Negligible processing latency:** Computation time for scheduling decisions is negligibly small relative to simulated travel time and therefore does not affect timing unless explicitly instrumented.

### 3.3. Development & Testing Assumptions

1. **Single-node execution:** The simulation runs as a single Java process (no distributed components).
2. **Threading model:** Concurrency may be used to simulate elevator movement and request handling; thread scheduling differences on different machines may slightly change time-based behavior.
3. **Deterministic tests:** Unit tests will mock or freeze timing to assert scheduler behavior deterministically.
4. **Input validation:** User inputs are assumed to be within valid ranges for typical operation; validation will be present but not exhaustive for malformed interactive input.

### 3.4. Stakeholder / Operational Assumptions

1. **No live integration:** There will be no integration with actual building management or elevator hardware during this project.
2. **Operator interventions:** Any manual or administrative overrides (e.g., resetting an emergency elevator) are simulated via a console/admin API.

---

## 4. Constraints

### 4.1. Technical Constraints

1. **Software-only:** Hardware-level controls, sensors, and actuators are out of scope.
2. **Technology stack:** The project must be implemented in Java and use Maven for build and dependency management.
3. **Runtime environment:** Target runtime is OpenJDK 11+ (or specify later if required). The system should run on a standard development machine (Windows/Linux/macOS).
4. **No external services:** The system should not depend on cloud services, networked databases, or external APIs for core simulation features.
5. **UI constraint:** Primary UI for the project is console-based (text input/output). GUI or web frontend is optional and considered an extension.

### 4.2. Time & Resource Constraints

1. **Project timeline:** Implementation will be scoped to a single learning/project term; certain advanced features (e.g., machine-learning-based scheduler) are out of scope unless time permits.
2. **Compute resources:** The simulation must run comfortably on an ordinary developer laptop (no GPU or heavy compute required).

### 4.3. Safety & Regulatory Constraints (Simulation Context)

1. **Regulatory compliance not required:** Because this is a software simulation with no physical control, compliance with elevator safety codes is not required, though safety-like behaviors will be simulated (e.g., emergency stop).

### 4.4. Design & Extensibility Constraints

1. **Modular design expected:** While constrained to Java and Maven, the design must be modular to allow later addition of features such as load-balancing heuristics, destination control, or GUI integration.
2. **API boundaries:** Public interfaces for the scheduler and elevator controller must be clearly defined to enable unit testing and future integrations.

---

## 5. Risks and Mitigations

1. **Risk:** Scheduler design may not scale conceptually to more elevators/floors.

   * **Mitigation:** Design scheduler with clear separation of algorithm (strategy pattern) and make performance assumptions explicit. Provide simple benchmarking harness.
2. **Risk:** Concurrency bugs (race conditions) during simulated elevator movement and request handling.

   * **Mitigation:** Prefer immutable request objects, use synchronized queues or concurrent collections, and write concurrency-focused unit tests.
3. **Risk:** Deterministic tests fail on different machines due to timing.

   * **Mitigation:** Use dependency injection for timing (clock abstraction) and mock it during tests.
4. **Risk:** User interaction model (manual floor input) introduces unrealistic test cases.

   * **Mitigation:** Create a simulation script harness to reproduce realistic workloads and deterministic scenarios.

---

## 6. Acceptance Criteria (Linked to Assumptions)

* The system must accept floor + direction requests via console, assign an elevator, and simulate movement with door open/close actions.
* Emergency alarm must immediately stop an elevator and mark it unavailable until reset via admin command.
* Scheduler should produce consistent allocations that follow the documented algorithm and should be verifiable in unit tests.
* The system should run under OpenJDK 11+ and build cleanly with Maven.

---

## 7. Change Control & Revision History

* **Version 1.0** — 2026-01-21 — Initial assumptions & constraints document (author: Deepak Kumar).

---

---

