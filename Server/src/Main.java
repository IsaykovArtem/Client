import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException{
        int serverPort = 8080;
        ServerSocket serverSocket = new ServerSocket(serverPort);

        System.out.println("Сервер запущен на порту " + serverPort);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new ToClient(clientSocket);
        }
    }
}