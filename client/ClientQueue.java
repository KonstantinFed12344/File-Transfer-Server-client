/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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
    private BufferedReader input;

    public ClientQueue(Socket socket, ArrayList<String> files, ListView<String> fileList) {
        this.socket = socket;
        this.files = files;
        this.fileList = fileList;
    }

    public void serverDownload() throws IOException {
        String fileName = fileList.getSelectionModel().getSelectedItems().get(0);
        File downloaded = new File("C:/FileBankReceived/" + fileName);
        System.out.println(fileName);
        out.println(fileName);
        out.flush();
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        int b;
        byte[] buffer = new byte[1024];
        OutputStream bos = new FileOutputStream(downloaded);
        while ((b = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, b);
            bos.flush();
        }
        bis.close();
        bos.close();

    }

    @Override
    protected String call() throws Exception {
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        files = new ArrayList<>();
        String fileNames = null;

        while ((fileNames = input.readLine()) != null) {//Adds file names to file scroller
            files.add(fileNames);
            System.out.println(files.get(files.size() - 1));
            fileList.getItems().add(fileNames);
        }

        return null;
    }
}
