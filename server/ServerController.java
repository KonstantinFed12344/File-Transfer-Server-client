/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.*;
import java.net.URL;
import java.util.ResourceBundle;
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
    private Button connectButton;
    @FXML
    private Label error;

    private ServerSocket server;
    private Socket clientSocket;

    @FXML
    public void connect() throws InterruptedException {
        try {
            clientSocket = server.accept();
            client.setText(clientSocket.getInetAddress().getHostAddress());
        } catch (Exception e) {
            System.out.println("Server Error");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            server = new ServerSocket(0, 1, InetAddress.getLocalHost());
            host.setText(server.getInetAddress().getHostAddress());
            port.setText(Integer.toString(server.getLocalPort()));

        } catch (Exception e) {
            System.out.println("Server Error");
        }
    }
}
