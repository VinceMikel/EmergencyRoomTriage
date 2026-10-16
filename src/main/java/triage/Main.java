package triage;

import java.util.List;
import java.util.Scanner;

/**
 * Student: Vince Mikel Masong of
 * RAM ID: R02102624
 * I certify that this submission is my own individual work, with the assistance of ChatGPT.
 */
public class Main {
    private static long nextArrivalOrder = 1;

    public static void main(String[] args) {
        PatientPriorityQueue waitingRoom = new PatientPriorityQueue();
        RoomManager roomManager = new RoomManager(3);
        loadSamplePatients(waitingRoom, roomManager);

        try (Scanner input = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu(waitingRoom.size());
                String choice = input.nextLine().trim();
                switch (choice) {
                    case "1" -> addPatient(input, waitingRoom, roomManager);
                    case "2" -> viewNextPatient(waitingRoom);
                    case "3" -> listPatients(waitingRoom);
                    case "4" -> showRooms(roomManager);
                    case "5" -> completeCare(input, roomManager, waitingRoom);
                    case "6" -> {
                        System.out.println("Goodbye.");
                        running = false;
                    }
                    default -> System.out.println("Please choose an option from 1 to 6.");
                }
                System.out.println();
            }
        }
    }

    private static void printMenu(int waitingCount) {
        System.out.println("=== Emergency Room Triage ===");
        System.out.println("Waiting patients: " + waitingCount);
        System.out.println("1. Add a patient");
        System.out.println("2. View the next waiting patient");
        System.out.println("3. List waiting patients");
        System.out.println("4. View treatment rooms");
        System.out.println("5. Mark a room patient's care complete");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addPatient(Scanner input, PatientPriorityQueue queue, RoomManager roomManager) {
        String name = readNonBlank(input, "Patient name: ");
        String condition = readNonBlank(input, "Brief condition: ");
        int urgency = readUrgency(input);
        Patient patient = new Patient(name, condition, urgency, nextArrivalOrder++);
        System.out.println("Patient registered.");
        // RoomManager decides whether the patient waits, takes an open room, or displaces a less urgent patient.
        roomManager.receivePatient(patient, queue);
    }

    private static String readNonBlank(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Please enter a value.");
        }
    }

    private static int readUrgency(Scanner input) {
        while (true) {
            System.out.print("Urgency (1=critical, 2=very urgent, 3=urgent, 4=less urgent, 5=non-urgent): ");
            try {
                int urgency = Integer.parseInt(input.nextLine().trim());
                if (urgency >= 1 && urgency <= 5) {
                    return urgency;
                }
            } catch (NumberFormatException ignored) {
                // Show the same helpful validation message below.
            }
            System.out.println("Enter a whole number from 1 to 5.");
        }
    }

    private static void viewNextPatient(PatientPriorityQueue queue) {
        Patient next = queue.peek();
        if (next == null) {
            System.out.println("There are no patients waiting.");
        } else {
            System.out.println("Next patient: " + next);
        }
    }

    private static void listPatients(PatientPriorityQueue queue) {
        List<Patient> patients = queue.getPatientsInPriorityOrder();
        if (patients.isEmpty()) {
            System.out.println("There are no patients waiting.");
            return;
        }
        System.out.println("Waiting patients (highest priority first):");
        for (int i = 0; i < patients.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, patients.get(i));
        }
    }

    private static void showRooms(RoomManager roomManager) {
        System.out.println("Treatment rooms:");
        for (int room = 1; room <= roomManager.getRoomCount(); room++) {
            Patient patient = roomManager.getPatientInRoom(room);
            System.out.printf("Room %d: %s%n", room, patient == null ? "Available" : patient.toString());
        }
    }

    private static void completeCare(Scanner input, RoomManager roomManager, PatientPriorityQueue waitingQueue) {
        showRooms(roomManager);
        System.out.print("Enter the room number to mark care complete: ");
        try {
            int roomNumber = Integer.parseInt(input.nextLine().trim());
            Patient completed = roomManager.completeCare(roomNumber, waitingQueue);
            if (completed == null) {
                System.out.println("That room is already available.");
            } else {
                System.out.println("Care completed for " + completed.getName() + ".");
                Patient nowInRoom = roomManager.getPatientInRoom(roomNumber);
                if (nowInRoom != null) {
                    System.out.println(nowInRoom.getName() + " moved from the waiting queue into Room " + roomNumber + ".");
                }
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Enter a valid room number from 1 to " + roomManager.getRoomCount() + ".");
        }
    }

    private static void loadSamplePatients(PatientPriorityQueue queue, RoomManager roomManager) {
        roomManager.receivePatient(new Patient("Jordan Lee", "Example case A", 3, nextArrivalOrder++), queue);
        roomManager.receivePatient(new Patient("Morgan Chen", "Example case B", 1, nextArrivalOrder++), queue);
        roomManager.receivePatient(new Patient("Taylor Smith", "Example case C", 4, nextArrivalOrder++), queue);
    }
}
