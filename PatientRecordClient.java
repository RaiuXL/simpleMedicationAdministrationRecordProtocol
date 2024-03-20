import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * This program tests the Simple Medication Administration Record Protocol server.
 */
public class PatientRecordClient
{
    public static void main(String[] args) throws IOException
    {
        final int SMARP_PORT = 8888;
        try (Socket s = new Socket("localhost", SMARP_PORT))
        {
            InputStream instream = s.getInputStream();
            OutputStream outstream = s.getOutputStream();
            Scanner in = new Scanner(instream);
            PrintWriter out = new PrintWriter(outstream);

            // View medication list for patient 1
            String command = "viewList 1";
            System.out.println("Sending: " + command);
            out.print(command + "\n");
            out.flush();
            String response = in.nextLine();
            System.out.println("Receiving: " + response);

            // Add medication for patient 1
            command = "addMed 1 Tylenol";
            System.out.println("Sending: " + command);
            out.print(command + "\n");
            out.flush();
            response = in.nextLine();
            System.out.println("Receiving: " + response);

            // Remove medication for patient 1
            command = "removeMed 1 Tylenol";
            System.out.println("Sending: " + command);
            out.print(command + "\n");
            out.flush();
            response = in.nextLine();
            System.out.println("Receiving: " + response);

            // Quit the connection
            command = "QUIT";
            System.out.println("Sending: " + command);
            out.print(command + "\n");
            out.flush();
        }
    }
}
