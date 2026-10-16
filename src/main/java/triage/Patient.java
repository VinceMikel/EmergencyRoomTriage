package triage;

/**
 * Student: Vince Mikel Masong
 * RAM ID: R02102624
 * I certify that this submission is my own individual work, with the assistance of ChatGPT.
 */
public class Patient {
    private final String name;
    private final String condition;
    private final int urgencyLevel;
    private final long arrivalOrder;

    public Patient(String name, String condition, int urgencyLevel, long arrivalOrder) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Patient name cannot be blank.");
        }
        if (condition == null || condition.isBlank()) {
            throw new IllegalArgumentException("Condition cannot be blank.");
        }
        if (urgencyLevel < 1 || urgencyLevel > 5) {
            throw new IllegalArgumentException("Urgency level must be from 1 to 5.");
        }
        this.name = name;
        this.condition = condition;
        this.urgencyLevel = urgencyLevel;
        this.arrivalOrder = arrivalOrder;
    }

    public String getName() {
        return name;
    }

    public String getCondition() {
        return condition;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    public long getArrivalOrder() {
        return arrivalOrder;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | urgency %d", name, condition, urgencyLevel);
    }
}
