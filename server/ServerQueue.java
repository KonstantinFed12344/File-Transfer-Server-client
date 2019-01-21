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
        DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
        DataInputStream fileIn = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        BufferedInputStream br;
        for (String files : files) {
            System.out.println(files);
            out.println(files);
            out.flush();
        }

        String fileName;
        File file;
        byte[] bytes;
        long length;
        while (true) {
            fileName = clientFile.readLine();
            System.out.println(fileName);
            file = new File("C:/FileBank/" + fileName);
//            
//            bytes = new byte[(int)file.length()];
//            out.println(bytes.length);
//            br = new BufferedInputStream(new FileInputStream(file));
//            int b;
//            int currentBytes = 0;
//            
//            while ((b = br.read()) != -1 ){
//               bytes[currentBytes++] = (byte)b;                
//            }
//            
//            System.out.println(bytes.length);
//            
//            for(b=0;b<bytes.length;b++){
//                clientSocket.getOutputStream().write(bytes[b]);
//            }

//            length = file.length();
//            if (length <= Long.MAX_VALUE) 
//                
//                bytes = new byte[(int) length];
//                fileOut.write(bytes, 0, (int)length);
//            }

        }

    }

}
