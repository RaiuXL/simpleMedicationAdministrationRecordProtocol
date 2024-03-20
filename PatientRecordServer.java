import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A server that executes the Simple Medication Administration Record Protocol.
 */
public class PatientRecordServer {
    public static void main(String[] args) throws IOException {
        final int RECORDS_LENGTH = 10; // Assuming a fixed size for simplicity
        PatientRecords patientRecords = new PatientRecords(RECORDS_LENGTH);
        final int SMARP_PORT = 8888; // SMARP Port, can be changed as needed
        ServerSocket server = new ServerSocket(SMARP_PORT);
        System.out.println("Waiting for clients to connect...");

        while (true) {
            Socket s = server.accept();
            System.out.println("Client connected.");
            PatientRecordService service = new PatientRecordService(s, patientRecords);
            Thread t = new Thread(service);
            t.start();
        }
    }
}
