# Emergency Room Triage

An IntelliJ-friendly Java command-line demonstration of a priority queue. It uses a custom binary min-heap; it does not use `java.util.PriorityQueue`.

## Priority rules

- Urgency 1 is most urgent; urgency 5 is least urgent.
- When urgency levels are equal, the patient who arrived first is handled first.
- The simulation has three treatment rooms and starts with three example patients.
- If all rooms are occupied and a more urgent patient arrives, that patient takes the room of the least urgent occupant. The displaced patient returns to the waiting priority queue with their original arrival order.
- When care is marked complete for a room, the highest-priority waiting patient moves into that room.

## Run in IntelliJ IDEA

1. Open the `EmergencyRoomTriage` folder as a project.
2. Open `src/main/java/triage/Main.java`.
3. Run `Main` and use the numbered menu.

## Menu

1. Add a patient
2. View the next patient without removing them
3. List waiting patients in priority order
4. View the three rooms
5. Mark a room patient's care complete
6. Exit

## Before submitting

Replace `[Add your RAM ID before submitting]` in the Java source comments with your RAM ID. Confirm your instructor's preferred Java language level in IntelliJ.

## Generative AI assistance

AI prompts used: “Help me plan and implement a Java command-line emergency-room triage demonstration using a custom binary-heap priority queue, with urgency levels 1–5 and arrival order as the tie-breaker. Include add, peek, remove-next, and list operations, sample startup data, and comments for the required integrity statement.” “Extend the simulation to use a fixed number of treatment rooms. When all rooms are occupied and a more urgent patient arrives, move the least urgent room occupant back into the priority queue and place the more urgent patient in that room. Add menu options to view rooms and mark care complete.”
