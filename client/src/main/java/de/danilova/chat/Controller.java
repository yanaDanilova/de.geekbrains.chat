package de.danilova.chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    TextField msgField;

    @FXML
    TextArea msgArea;

    private Socket socket;
    DataInputStream in;
    DataOutputStream out;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket = new Socket("localHost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to connect to Local Server [8189]");
        }

        Thread thread = new Thread(()->{
            try {

                while (true){
                    String msg = in.readUTF();
                    msgArea.appendText(msg + " ");
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });
        thread.start();

    }

    public void sendMsg() {
        try {
            out.writeUTF(msgField.getText());
            msgField.clear();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Unable to send message", ButtonType.OK);
            alert.showAndWait();
        }
    }
}

