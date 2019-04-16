package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket connection;

    public static final int PORT = 19000;

    public static void main(String[] args) {

        //File folder = new File("/home/andrey/Documents/test/transfered/");
        File folder = new File(args[0]);

        try {
            connection = new ServerSocket(PORT);

            System.out.println("Server started. Waiting for connections...");

            Socket socket = connection.accept();

            System.out.println("Client connected.");

            try (InputStream in = socket.getInputStream()) {

                byte[] buf = new byte[1024];
                FileOutputStream fos = null;

                // while all files not received
                while (true) {
                    int readBytes = in.read(buf);

                    if (readBytes == 0 || readBytes == -1)
                        break;

                    String message = new String(buf, 0, readBytes);
                    String filename;
                    if (message.contains("New file: ")) {
                        filename = message.substring(10, message.indexOf("#"));
                        File file = new File(folder + "/", filename);
                        file.createNewFile();
                        fos = new FileOutputStream(file);
                    } else
                        fos.write(message.getBytes());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
