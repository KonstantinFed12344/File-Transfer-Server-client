/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import javafx.concurrent.Task;

/**
 *
 * @author Konstantin
 */
public class ServerQueue extends Task<String> {

    private Socket clientSocket;
    private ArrayList<String> files;

    public ServerQueue(Socket clientSocket, ArrayList<String> files) {
        this.clientSocket = clientSocket;
        this.files = files;
    }

    @Override
    protected String call() throws Exception {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader clientFile = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        for (String files : files) {
            System.out.println(files);
            out.println(files);
            out.flush();
        }

        String fileName;
        fileName = clientFile.readLine();
        System.out.println(fileName);

        out.close();
        clientFile.close();

        return null;
    }

}
