package triage;

/**
 * Student: Vince Mikel Masong
 * RAM ID: R02102624
 * I certify that this submission is my own individual work, with the assistance of ChatGPT.
 *
 * There is a possibility where another Patient in a more critical condition may arrive at the hospital
 * when the rooms are full. In this file, I made it so that the patient with a more critical condition will
 * override where the least critical patients are, and the least urgent patient would move back to the priority queue
 */
public class RoomManager {
    private final Patient[] rooms;

    public RoomManager(int roomCount) {
        if (roomCount < 1) {
            throw new IllegalArgumentException("There must be at least one room.");
        }
        rooms = new Patient[roomCount];
    }

    /** Adds the arrival to the queue, fills an open room, or promotes a more urgent patient. */
    public void receivePatient(Patient patient, PatientPriorityQueue waitingQueue) {
        waitingQueue.add(patient);
        fillOpenRooms(waitingQueue);

        while (waitingQueue.peek() != null) {
            int leastUrgentRoom = findLeastUrgentRoom();
            Patient waiting = waitingQueue.peek();
            Patient inRoom = rooms[leastUrgentRoom];

            // A lower urgency number is more urgent. Equal urgency does not displace a patient.
            if (waiting.getUrgencyLevel() >= inRoom.getUrgencyLevel()) {
                break;
            }

            Patient displaced = rooms[leastUrgentRoom];
            rooms[leastUrgentRoom] = waitingQueue.removeNext();
            waitingQueue.add(displaced);
            System.out.printf("Room %d: %s is moved back to the waiting queue for a more urgent patient.%n",
                    leastUrgentRoom + 1, displaced.getName());
            System.out.printf("Room %d: %s is now receiving care.%n",
                    leastUrgentRoom + 1, rooms[leastUrgentRoom].getName());
        }
    }

    /** Marks a room occupant's care complete and sends the next waiting patient to that room. */
    public Patient completeCare(int roomNumber, PatientPriorityQueue waitingQueue) {
        if (roomNumber < 1 || roomNumber > rooms.length) {
            throw new IllegalArgumentException("Room number is out of range.");
        }
        int index = roomNumber - 1;
        Patient completed = rooms[index];
        if (completed == null) {
            return null;
        }

        rooms[index] = waitingQueue.removeNext();
        return completed;
    }

    public Patient getPatientInRoom(int roomNumber) {
        if (roomNumber < 1 || roomNumber > rooms.length) {
            throw new IllegalArgumentException("Room number is out of range.");
        }
        return rooms[roomNumber - 1];
    }

    public int getRoomCount() {
        return rooms.length;
    }

    private void fillOpenRooms(PatientPriorityQueue waitingQueue) {
        for (int i = 0; i < rooms.length && !waitingQueue.isEmpty(); i++) {
            if (rooms[i] == null) {
                rooms[i] = waitingQueue.removeNext();
                System.out.printf("Room %d: %s is now receiving care.%n", i + 1, rooms[i].getName());
            }
        }
    }

    private int findLeastUrgentRoom() {
        int leastUrgentIndex = 0;
        for (int i = 1; i < rooms.length; i++) {
            Patient candidate = rooms[i];
            Patient currentLeastUrgent = rooms[leastUrgentIndex];
            if (candidate.getUrgencyLevel() > currentLeastUrgent.getUrgencyLevel()
                    || (candidate.getUrgencyLevel() == currentLeastUrgent.getUrgencyLevel()
                    && candidate.getArrivalOrder() > currentLeastUrgent.getArrivalOrder())) {
                leastUrgentIndex = i;
            }
        }
        return leastUrgentIndex;
    }
}
