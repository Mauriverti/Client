package client;

/**
 * @author mauriverti
 */
public class Client {

    public static Integer portDestino = 8910;
//    public static Integer portDestino = 12000;
//    public static final String ipDestino = "localhost";
    public static final String ipDestino = "localhost";        // unioeste
//    public static final String ipDestino = "192.168.25.200";     // casa
//    public static final String ipDestino = "10.19.10.129";          // MV

    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {

        
        
            new ThreadClient("Domain-" + 0).start();
//            portDestino++;

//        }

    }
}
