package test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class BookScrabbleHandler implements ClientHandler {
    private DictionaryManager dm;

    public BookScrabbleHandler() {
        this.dm = new DictionaryManager();
    }

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(inFromclient));
                PrintWriter writer = new PrintWriter(outToClient, true);
        ) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    String command = parts[0];
                    String[] books = new String[parts.length - 1];
                    System.arraycopy(parts, 1, books, 0, parts.length - 1);

                    boolean result;
                    if (command.equals("Q")) {
                        result = dm.query(books);
                    } else if (command.equals("C")) {
                        result = dm.challenge(books);
                    } else {
                        result = false;
                    }
                    writer.println(result);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        // No resources to close
    }
}
