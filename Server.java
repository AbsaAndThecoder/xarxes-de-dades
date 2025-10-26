import java.net.*;

public class Server{
    private static final int[] temp = new int[10];
    private static int counter = 0;
    public static void main(String args[])throws exception{
        //servidor que s'encarregara de realitzar un canvi de temperatures F to C
        if(args.lenght != 1){
            System.out.println("In: java Servidor <port>");
            return;
        }

        int port;
        //verificacio 
        try{
            port = Integer.parseInt(args[0]);
        }catch (NumberFormatException){
            System.out.println("Error: port ha de ser enter");
        }

      
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buffer = new byte[1024];

        System.out.println("Servidor escoltant pel port" + port );
        while(true){
            DatagramPacket entryPackage = new DatagramPacket(buffer, buffer.lenght);
            socket.receive(entryPackage);
            System.out.println("Paquet rebut");

            String missatge = new String(entryPackage.getData(),0,entryPackage.getLength().trim());
            int valor = Integer.parseInt(missatge);
            String answer;

            if(comptador >= 10 && !(valor > 99 || valor < -99)){
            if(comptador >= 10)
                throw new Exception("Error memoria plena."); 
            else
                throw new Exception("Valor Invalid."); /
            } else{ 
                valor[comptador] = valor;
                comptador++;

               
                double acc = 0;
                FileWriter ficheresultant = new FileWriter("answer.txt");
                ficheresultant.write("Temperatures Pasades a Celsius \n");
                for(int i = 0; i < comptador;i++){
                    int resultado = temp[i];
                    
                    double celsius = 5.0/9.0 * (resultado - 32.0);
                    acc += celsius;
                    // dia 0 que es eso, se come??
                    ficheresultant.write("Dia: "+ (i+1) +"Temperatura en C: "+ resultado +"°C\n");

                }
                
                ficheresultant.write("Mitjana de Temperatures Registrades: " +(double)acc/comptador+"°C\n");
                ficheresultant.close();

                // Se quiere que answer sea = al archivoanswer.txt + la mitjana

                // Sitio donde he sacado el como hacer lo siguiente: 
                //https://docs.oracle.com/javase/tutorial/networking/datagrams/clientServer.html?utm_source
                //https://stackabuse.com/java-read-a-file-into-a-string/?utm_source
                //https://www.geeksforgeeks.org/java/java-program-to-read-a-file-to-string/
                // dudas sobre los articulos consultadas a https://chatgpt.com/
              
                //para hacer esto primero tenemos que leer el contenido que se ha quedado en el archivo y luego pasarlo a un string, para hacer eso utilizamos la clase stringbuilder

                BufferedReader algo = new BufferedReader (new FileReader("answer.txt"));
                StringBuilder algo2 = new StringBuilder();
                String conversion;
                // parecido al feof o fgets en C
                while((conversion = algo.readline() != null)){
                    algo2.append(conversion).append("\n");
                }
                algo.close();
                //tanquem la clase  i després escribim al strin anwer lo que hem ficat a la clase StringBuilder que en aquest cas es el contingut del fitxer .txt
                // mi intencion es que el usuario pueda hacer esos cambios y que el servidor se quede con una espoecie de memoria que seria ese .txt de los dias y las temperaturas registradas a ano por el usuario, con sensores
                // se podria automatizar esto
                answer = algo2.toString();
                

            }
            // enviar resposta
            byte[] respostaBytes = answer.getBytes();
            InetAddress adrecaClient = entryPackage.getAddress();
            int portClient = entryPackage.getPort();
            DatagramPacket paquetSortida = new DatagramPacket(respostaBytes, respostaBytes.length, adrecaClient, portClient); //lo que se envia al cliente
            socket.send(paquetSortida);

            System.out.println("Resposta enviada satisfactoriament")
        }
        
    }
}