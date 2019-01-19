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
import static java.lang.System.out;
import java.net.Socket;
import java.util.ArrayList;
import javafx.concurrent.Task;
import javafx.scene.control.ListView;

/**
 *
 * @author Konstantin
 */
public class ClientQueue extends Task<String> {

    private Socket socket;
    private ArrayList<String> files;
    private ListView<String> fileList;
    private PrintWriter out;

    public ClientQueue(Socket socket, ArrayList<String> files, ListView<String> fileList) {
        this.socket = socket;
        this.files = files;
        this.fileList = fileList;
    }

    public void serverDownload() throws IOException {
        File downloaded = new File(fileList.getSelectionModel().getSelectedItems().get(0));
        System.out.println(fileList.getSelectionModel().getSelectedItems().get(0));
        out.println(fileList.getSelectionModel().getSelectedItems().get(0));
        out.flush();
    }

    @Override
    protected String call() throws Exception {
        BufferedReader serverFiles = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        files = new ArrayList<>();
        String fileNames = null;

        while ((fileNames = serverFiles.readLine()) != null) {//Adds file names to file scroller
            files.add(fileNames);
            System.out.println(files.get(files.size() - 1));
            fileList.getItems().add(fileNames);
        }

        return null;
    }
}
