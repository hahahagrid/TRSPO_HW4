import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        // Серверний код
        Thread serverThread = new Thread(() -> {
            try {
                // Створення серверного сокету, який слухатиме на порту 12345
                ServerSocket serverSocket = new ServerSocket(12345);

                // Очікування підключення клієнта
                Socket clientSocket = serverSocket.accept();

                // Введення потоків для читання з та запису до клієнта
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Обмін повідомленнями з клієнтом
                out.println("Привіт, це сервер!");
                String message = in.readLine();
                System.out.println("Клієнт говорить: " + message);

                // Закриття з'єднання
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Клієнтський код
        Thread clientThread = new Thread(() -> {
            try {
                // Підключення до сервера за його IP-адресою та портом
                Socket socket = new Socket("127.0.0.1", 12345);

                // Введення потоків для читання з та запису до сервера
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Обмін повідомленнями з сервером
                String message = in.readLine();
                System.out.println("Сервер говорить: " + message);
                out.println("Привіт, це клієнт!");

                // Закриття з'єднання
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Запуск сервера та клієнта
        serverThread.start();
        clientThread.start();
    }
}
