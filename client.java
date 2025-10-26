/*import java.net.*; */
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Ús: java Client <IP servidor> <port>");
            return;
        }

        InetAddress ipServidor = InetAddress.getByName(args[0]);
        int port = Integer.parseInt(args[1]);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Introdueix un número enter: ");
        int valor = scanner.nextInt();
        scanner.close();

        DatagramSocket socket = new DatagramSocket();
        byte[] enviat = String.valueOf(valor).getBytes();
        DatagramPacket paquetEnviat = new DatagramPacket(enviat, enviat.length, ipServidor, port);
        socket.send(paquetEnviat);

        // Rebre resposta
        byte[] buffer = new byte[1024];
        DatagramPacket paquetResposta = new DatagramPacket(buffer, buffer.length);
        socket.receive(paquetResposta);

        String resposta = new String(paquetResposta.getData(), 0, paquetResposta.getLength());
        System.out.println("Resposta: " + resposta);

        socket.close();
    }
}