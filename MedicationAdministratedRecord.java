import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A record for managing a patient's medication list with thread safety.
 */
public class MedicationAdministratedRecord {
    private int personalID;
    private List<String> medications;
    private Lock medicationChangeLock;

    /**
     * Constructs a medication administrated record with an associated personal ID.
     * @param personalID the personal ID of the patient
     */
    public MedicationAdministratedRecord(int personalID) {
        this.personalID = personalID;
        this.medications = new ArrayList<>();
        this.medicationChangeLock = new ReentrantLock();
    }

    /**
     * Adds a medication to the patient's list.
     * Ensures thread safety by locking during the operation.
     * @param medication the medication to add
     */
    public void addMed(String medication) {
        medicationChangeLock.lock();
        try {
            medications.add(medication);
        } finally {
            medicationChangeLock.unlock();
        }
    }

    /**
     * Removes a medication from the patient's list.
     * Ensures thread safety by locking during the operation.
     * @param medication the medication to remove
     */
    public void removeMed(String medication) {
        medicationChangeLock.lock();
        try {
            medications.remove(medication);
        } finally {
            medicationChangeLock.unlock();
        }
    }

    /**
     * Gets the list of medications for the patient.
     * @return the list of medications
     */
    public List<String> getMedications() {
        medicationChangeLock.lock();
        try {
            return new ArrayList<>(medications); // Return a copy to avoid concurrent modification
        } finally {
            medicationChangeLock.unlock();
        }
    }

    /**
     * Gets the personal ID of the patient.
     * @return the personal ID
     */
    public int getPersonalID() {
        return personalID;
    }
}
