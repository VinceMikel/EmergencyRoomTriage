package triage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Student: Vince Mikel Masong
 * RAM ID: R02102624
 * I certify that this submission is my own individual work, with the assistance of ChatGPT.
 *
 * A min-heap ordered by urgency (1 is highest priority), then arrival order.
 */
public class PatientPriorityQueue {
    private static final Comparator<Patient> ORDER = Comparator
            .comparingInt(Patient::getUrgencyLevel)
            .thenComparingLong(Patient::getArrivalOrder);

    private final ArrayList<Patient> heap = new ArrayList<>();

    public void add(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null.");
        }
        heap.add(patient);
        siftUp(heap.size() - 1);
    }

    public Patient peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    public Patient removeNext() {
        if (heap.isEmpty()) {
            return null;
        }
        Patient next = heap.get(0);
        Patient last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return next;
    }

    /** Removes a waiting patient. */
    public Patient removeByArrivalOrder(long arrivalOrder) {
        int index = -1;
        for (int i = 0; i < heap.size(); i++) {
            if (heap.get(i).getArrivalOrder() == arrivalOrder) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return null;
        }

        Patient removed = heap.get(index);
        Patient last = heap.remove(heap.size() - 1);
        if (index < heap.size()) {
            heap.set(index, last);
            if (index > 0 && ORDER.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
                siftUp(index);
            } else {
                siftDown(index);
            }
        }
        return removed;
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /** Returns a sorted snapshot without changing the queue. */
    public List<Patient> getPatientsInPriorityOrder() {
        ArrayList<Patient> patients = new ArrayList<>(heap);
        patients.sort(ORDER);
        return patients;
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (ORDER.compare(heap.get(index), heap.get(parent)) >= 0) {
                break;
            }
            swap(index, parent);
            index = parent;
        }
    }

    private void siftDown(int index) {
        int size = heap.size();
        while (true) {
            int left = 2 * index + 1;
            int right = left + 1;
            int smallest = index;

            if (left < size && ORDER.compare(heap.get(left), heap.get(smallest)) < 0) {
                smallest = left;
            }
            if (right < size && ORDER.compare(heap.get(right), heap.get(smallest)) < 0) {
                smallest = right;
            }
            if (smallest == index) {
                return;
            }
            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int first, int second) {
        Patient temporary = heap.get(first);
        heap.set(first, heap.get(second));
        heap.set(second, temporary);
    }
}
