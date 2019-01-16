/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
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
public class ClientController implements Initializable {

    @FXML
    private TextField host;
    @FXML
    private TextField port;
    @FXML
    private Label error;
    @FXML
    private ListView<String> fileList;
    @FXML
    private Button folder;

    private Socket socket;
    private ArrayList<String> files;

    @FXML
    private void openFolder() throws IOException {
        Runtime.getRuntime().exec("explorer.exe /open, C:\\FileBankReceived");
    }

    public void connect() throws InterruptedException {
        try {

            socket = new Socket(InetAddress.getByName(host.getText()), Integer.parseInt(port.getText()));
            String fileNames = null;
            BufferedReader serverFiles = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            files = new ArrayList<>();

            while ((fileNames = serverFiles.readLine()) != null) {
                files.add(fileNames);
                //System.out.println(files.get(files.size()-1));
                fileList.getItems().add(fileNames);
            }
        } catch (Exception e) {
            System.out.println("Client Error");
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
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new File("C:/FileBankReceived").mkdir();
    }

}
