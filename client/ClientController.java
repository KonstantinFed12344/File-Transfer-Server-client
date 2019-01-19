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
import java.io.PrintWriter;
import java.net.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
    private boolean connected;
    private PrintWriter out;
    private ClientQueue task;

    @FXML
    private void openFolder() throws IOException {
        Runtime.getRuntime().exec("explorer.exe /open, C:\\FileBankReceived");
    }

    @FXML
    private void download() throws IOException {
        if (connected) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        task.serverDownload();
                    } catch (IOException ex) {
                        Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }

    public void connect() throws InterruptedException {
        try {

            socket = new Socket(InetAddress.getByName(host.getText()), Integer.parseInt(port.getText()));

            task = new ClientQueue(socket, files, fileList);
            ExecutorService executorService
                    = Executors.newFixedThreadPool(1);
            executorService.execute(task);

            executorService.shutdown();

            connected = true;
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
        connected = false;
    }

}
