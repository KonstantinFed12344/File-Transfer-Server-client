/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import javafx.concurrent.Task;

/**
 *
 * @author Konstantin
 */
public class ServerQueue extends Task {

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
        BufferedInputStream br;
        
        for (String files : files) {
            System.out.println(files);
            out.println(files);
            out.flush();
        }
        
        String fileName;
        File file;
        byte[] bytes;
        OutputStream os;
        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
        
        while (true) {
            fileName = clientFile.readLine();
            System.out.println(fileName);
            file = new File("C:/FileBank/" + fileName);
            bytes = new byte[(int) file.length()];
            
            br = new BufferedInputStream(new FileInputStream(file));
            br.read(bytes, 0, bytes.length);
            dos.writeLong(bytes.length);
            
            os = clientSocket.getOutputStream();
            os.write(bytes, 0, bytes.length);
            os.flush();
            br.close();
            System.out.println("Done");

        }

    }

}
