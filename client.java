import java.net.*;
import java.util.Scanner;

// El servidor s'encarregara d'executar un canvi de temperatura de Fahrenheit a Celsius i mostrara la mitjana de temperatura en Celsius
public class Client {
    // Rebem primer la IP del servidor i després el port.
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Ús: java client <IP servidor> <port>");
            return;
        }

        // Definim adreces ip del servidor al client
        InetAddress ipServidor = InetAddress.getByName(args[0]);
        int port = Integer.parseInt(args[1]);

        // Definim el Socket
    DatagramSocket socket = new DatagramSocket();
    //petit temporitzador per a veure si el servidor escolta(aixo es mes per a la segona part del ddos)
         socket.setSoTimeout(5000); 

        // Llegim un valor per enviar (demo)
        /*
             Scanner scanner = new Scanner(System.in);
            System.out.print("Envia un valor (ex: temperatura en Fahrenheit): ");
             String valor = scanner.nextLine();
          while(true){
            byte[] enviat = valor.getBytes();
            DatagramPacket sentPackage = new DatagramPacket(enviat, enviat.length, ipServidor, port);
            socket.send(sentPackage);
            enviat += int*enviat
         */
        Scanner scanner = new Scanner(System.in);
        System.out.print("Envia un valor (ex: temperatura en Fahrenheit): ");
        String valor = scanner.nextLine();

        byte[] enviat = valor.getBytes();
        DatagramPacket sentPackage = new DatagramPacket(enviat, enviat.length, ipServidor, port);
        socket.send(sentPackage);

        // Comprovacio/espera de respota del servidor al client
        byte[] buffer = new byte[1024];
        DatagramPacket answerPackage = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(answerPackage);
            String resposta = new String(answerPackage.getData(), 0, answerPackage.getLength());
            System.out.println("Resposta del servidor: " + resposta);
        } catch (java.net.SocketTimeoutException e) {
            throw new Exception("No s'ha rebut resposta del servidor."); // error al no connectar el sevidor
        }

        socket.close();
        scanner.close();
    }
}
