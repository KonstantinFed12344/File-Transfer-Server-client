/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 *
 * @author Konstantin
 */
public class ServerController implements Initializable {

    @FXML
    private TextField host;
    @FXML
    private TextField port;
    @FXML
    private TextField client;
    @FXML
    private Button openServerButton;
    @FXML
    private Label error;
    @FXML
    private ListView<String> fileList;
    @FXML
    private Button folder;

    private ServerSocket server;
    private Socket clientSocket;
    private ArrayList<String> files;

    @FXML
    public void openServer() throws InterruptedException {
        try {
            clientSocket = server.accept();
            client.setText(clientSocket.getInetAddress().getHostAddress());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            
            for(String files : files){
                System.out.println(files);
                out.println(files);
                out.flush();
            }
            out.close();
        } catch (Exception e) {
            System.out.println("Server Error");
            this.errorMessage();
        }
    }

    private void errorMessage() {
        error.setOpacity(1);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                error.setOpacity(0);
            }
        }, 1, TimeUnit.SECONDS);
        scheduler.shutdown();
    }

    private void updateFileBank() {
        File folder = new File("C:/FileBank");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (!files.contains(listOfFiles[i].getName())) {
                files.add(listOfFiles[i].getName());
                fileList.getItems().add(listOfFiles[i].getName());                
            }
        }
    }
    
    @FXML
    private void openFolder() throws IOException{
        this.updateFileBank();
        Runtime.getRuntime().exec("explorer.exe /open, C:\\FileBank");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new File("C:/FileBank").mkdir();
        files = new ArrayList<>();
        this.updateFileBank();

        try {
            server = new ServerSocket(0, 1, InetAddress.getLocalHost());
            host.setText(server.getInetAddress().getHostAddress());
            port.setText(Integer.toString(server.getLocalPort()));

        } catch (Exception e) {
            System.out.println("Server Error");
            this.errorMessage();
        }
    }
}
