package client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
    private static Socket connection;

    public static final int PORT = 19000;
    public static final String HOST = "localhost";

    public static void main(String[] args) {
        try {
            connection = new Socket(HOST, PORT);

            //final File folder = new File("/home/andrey/Documents/test");
            final File folder = new File(args[0]);
            List<File> files = getFilenamesFromFolder(folder);
            FileInputStream fis = null;
            int readed;

            try (OutputStream out = connection.getOutputStream();
                 BufferedOutputStream bout = new BufferedOutputStream(out, 1024)) {

                for (final File file : files) {
                    if (file.isFile()) {
                        fis = new FileInputStream(file);

                        // send package with file name
                        bout.write(("New file: " + file.getName() + "#").getBytes());
                        System.out.println("File " + file.getName());
                        Thread.currentThread().sleep(20);
                        bout.flush();

                        // while file not sent
                        while (true) {
                            byte[] data = new byte[1024];
                            readed = fis.read(data, 0, 1024);
                            //System.out.println(readed);
                            if (readed == -1)
                                break;

                            else {
                                for (int i = 0; i < readed; i++)
                                    bout.write(data[i]);
                                Thread.currentThread().sleep(20);
                                bout.flush();
                            }
                        }
                    }
                }

                fis.close();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<File> getFilenamesFromFolder(final File folder) {
        List<File> filenamaes = new ArrayList<>();
        filenamaes.addAll(Arrays.asList(folder.listFiles()));

        return filenamaes;
    }

}
