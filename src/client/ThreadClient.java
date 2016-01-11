/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import static client.Client.ipDestino;
import static client.Client.portDestino;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author mauriverti
 */
public class ThreadClient extends Thread {

    private final String hostname;

    public ThreadClient(String hostname) {
        this.hostname = hostname;

    }

    @Override
    public void run() {
        try {
            Socket client = new Socket(ipDestino, portDestino);
            System.out.println("Cliente " + hostname + " se conectou com sucesso!");

            Scanner scanner = new Scanner(client.getInputStream());
            if (scanner.hasNextLine()) {
                Integer port = Integer.parseInt(scanner.nextLine());
                System.out.println("Iniciando comunicacao na Porta: " + port);
                client = new Socket(ipDestino, port);
            }

            PrintStream saida = new PrintStream(client.getOutputStream());

            String getHostnameCommand = "hostname";
            Process getHostNameProcess = Runtime.getRuntime().exec(getHostnameCommand);

//            BufferedReader bufferHostname = new BufferedReader(new InputStreamReader(getHostNameProcess.getInputStream()));
//            String hostname = bufferHostname.readLine();
            String getStatusCommand = "sudo dstat -c -m -t 1";

            Process getStatusProcess = Runtime.getRuntime().exec(getStatusCommand);
            BufferedReader reader = new BufferedReader(new InputStreamReader(getStatusProcess.getInputStream()));
            String line;
            reader.readLine(); // cabecalho
            reader.readLine(); // titulos
            
            while ((line = reader.readLine()) != null) {
                saida.println(hostname + "->" + line);
            }

            getStatusProcess.waitFor();

            saida.close();
            client.close();
        } catch (ConnectException ce) {
            System.out.println("Falha ao se comunicar com o servidor em " + ipDestino + ":" + portDestino);
            ce.printStackTrace();
        } catch (Exception e) {
            System.out.println("Falha inesperada no Cliente");
            e.printStackTrace();
        }
    }

}
