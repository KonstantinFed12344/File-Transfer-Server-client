/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

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
public class ClientController implements Initializable {
    
    @FXML
    private TextField host;
    @FXML
    private TextField port;
    @FXML
    private Label error;
    
    private Socket socket;
    
    public void connect() throws InterruptedException {
        try {
            socket = new Socket(InetAddress.getByName(host.getText()),Integer.parseInt(port.getText()));
        } catch (Exception e) {
            System.out.println("Client Error");            
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        
    }        
}
