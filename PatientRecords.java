import java.util.ArrayList;
import java.util.List;

/**
 * A collection of medication administration records for multiple patients.
 */
public class PatientRecords {
    private MedicationAdministratedRecord[] records;

    /**
     * Constructs a patient records database with a given number of records.
     * @param size the number of records
     */
    public PatientRecords(int size) {
        records = new MedicationAdministratedRecord[size];
        for (int i = 0; i < records.length; i++) {
            records[i] = new MedicationAdministratedRecord(i); // Assuming constructor takes patientID
        }
    }

    /**
     * Adds a medication to a patient's record.
     * @param patientID the patient's ID
     * @param medication the medication to add
     */
    public void addMedication(int patientID, String medication) {
        MedicationAdministratedRecord record = records[patientID];
        record.addMed(medication);
    }

    /**
     * Removes a medication from a patient's record.
     * @param patientID the patient's ID
     * @param medication the medication to remove
     */
    public void removeMedication(int patientID, String medication) {
        MedicationAdministratedRecord record = records[patientID];
        record.removeMed(medication);
    }

    /**
     * Gets the list of medications for a patient.
     * @param patientID the patient's ID
     * @return the list of medications
     */
    public List<String> getMedications(int patientID) {
        MedicationAdministratedRecord record = records[patientID];
        return record.getMedications(); // This is perfectly fine as it already returns a List
    }


}
