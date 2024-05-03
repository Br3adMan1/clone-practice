package test;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {
    private int port;
    private ClientHandler ch;
    private ServerSocket serverSocket;
    private volatile boolean stop;

    public MyServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
        this.stop = false;
    }


    public void start() {
        Thread serverThread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                serverSocket.setSoTimeout(1000);
                while (!stop) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        try {
                            InputStream inputStream = clientSocket.getInputStream();
                            OutputStream outputStream = clientSocket.getOutputStream();
                            ch.handleClient(inputStream, outputStream);
                            inputStream.close();
                            outputStream.close();
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        });
        serverThread.start();
    }

    public void close() {
        stop = true;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
