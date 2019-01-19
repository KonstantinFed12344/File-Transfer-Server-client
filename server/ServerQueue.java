/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import javafx.concurrent.Task;

/**
 *
 * @author Konstantin
 */
public class ServerQueue extends Task<String> {

    private BufferedReader clientFile;

    public ServerQueue(BufferedReader clientFile) {
        this.clientFile = clientFile;
    }

    @Override
    protected String call() throws Exception {
        return clientFile.readLine();
    }

}
