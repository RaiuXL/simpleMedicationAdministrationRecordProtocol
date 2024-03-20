import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 * Executes Simple Medication Administration Record Protocol commands
 * from a socket.
 */
public class PatientRecordService implements Runnable {
    private Socket s;
    private Scanner in;
    private PrintWriter out;
    private PatientRecords patientRecords;

    /**
     * Constructs a service object that processes commands
     * from a socket for patient records.
     * @param aSocket the socket
     * @param aPatientRecords the patient records
     */
    public PatientRecordService(Socket aSocket, PatientRecords aPatientRecords) {
        s = aSocket;
        patientRecords = aPatientRecords;
    }

    public void run() {
        try {
            try {
                in = new Scanner(s.getInputStream());
                out = new PrintWriter(s.getOutputStream());
                doService();
            } finally {
                s.close();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Executes all commands until the QUIT command or the
     * end of input.
     */
    public void doService() throws IOException {
        while (true) {
            if (!in.hasNextLine()) return;
            String inputLine = in.nextLine();
            if (inputLine.equalsIgnoreCase("QUIT")) return;
            executeCommand(inputLine);
        }
    }

    // Assuming this method directly follows the doService() method that reads the full line:
    public void executeCommand(String inputLine) {
        Scanner lineScanner = new Scanner(inputLine);
        if (!lineScanner.hasNext()) {
            out.println("No command received.");
            out.flush();
            return;
        }

        String command = lineScanner.next().toLowerCase(); // Convert command to lowercase.

        // QUIT command can be processed immediately since it doesn't require additional parameters.
        if ("quit".equals(command)) {
            out.println("Quitting...");
            out.flush();
            return;
        }

        // Ensure there is a patient ID following the command.
        if (!lineScanner.hasNextInt()) {
            out.println("Invalid command format: Expected patient ID.");
            out.flush();
            return;
        }
        int patientID = lineScanner.nextInt();

        // For commands that expect a medication name, read the next token if available.
        String medication = "";
        if (lineScanner.hasNext()) {
            medication = lineScanner.next(); // Assumes medication names do not contain spaces.
        }

        switch (command) {
            case "viewlist":
                List<String> medications = patientRecords.getMedications(patientID);
                String medsList = medications.isEmpty() ? "No medications found" : String.join(", ", medications);
                out.println("Medications for patient " + patientID + ": " + medsList);
                break;
            case "addmed":
                if (medication.isEmpty()) {
                    out.println("Invalid command format: Expected medication name.");
                    out.flush();
                    return;
                }
                patientRecords.addMedication(patientID, medication);
                out.println("Medication " + medication + " added for patient " + patientID);
                break;
            case "removemed":
                if (medication.isEmpty()) {
                    out.println("Invalid command format: Expected medication name.");
                    out.flush();
                    return;
                }
                patientRecords.removeMedication(patientID, medication);
                out.println("Medication " + medication + " removed for patient " + patientID);
                break;
            default:
                out.println("Invalid command received.");
                out.flush();
        }
    }








}
