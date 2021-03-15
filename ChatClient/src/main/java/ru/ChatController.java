package ru;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.messages.MessageDTO;
import ru.messages.MessageType;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable, MessageProcessor{
    private final String ALL = "SEND TO ALL";
    @FXML
    public TextArea chatArea;
    @FXML
    public ListView onlineUsers;
    @FXML
    public Button btnSendMessage;
    @FXML
    public TextField input;
    @FXML
    public HBox chatBox;
    @FXML
    public HBox inputBox;
    @FXML
    public MenuBar menuBar;
    @FXML
    public HBox authPanel;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passField;

    private MessageService messageService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageService = new ChatMessageService("localhost", 65500, this);
    }
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void showHelp(ActionEvent actionEvent) throws URISyntaxException, IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(new URI("https://docs.google.com/document/d/1wr0YEtIc5yZtKFu-KITqYnBtp8KC28v2FEYUANL0YAM/edit#"));
    }




    public void mockAction(ActionEvent actionEvent) {
        try {
            throw new RuntimeException("AAAAAAAAAAAAAAAAAAAA!!!!!!!");
        } catch (RuntimeException e) {
            showError(e);
        }

    }
    private void refreshUserList(MessageDTO dto) {
        dto.getUsersOnline().add(0, ALL);
        onlineUsers.setItems(FXCollections.observableArrayList(dto.getUsersOnline()));
        onlineUsers.getSelectionModel().selectFirst();
    }


    public void pressEnter(ActionEvent actionEvent) {
        sendMessage();
    }

    public void btnSend(ActionEvent actionEvent) {
        sendMessage();
    }

    private void sendMessage() {
        String msg = input.getText();
        if (msg.length() == 0) return;

        MessageDTO dto = new MessageDTO();
        if (msg.startsWith("/wuser3")){
            dto.setMessageType(MessageType.PRIVATE_MESSAGE);
            dto.setTo("user3");
        }else {
            String selected = (String) onlineUsers.getSelectionModel().getSelectedItem();

            if (selected.equals(ALL)) dto.setMessageType(MessageType.PUBLIC_MESSAGE);

            else {
                dto.setMessageType(MessageType.PRIVATE_MESSAGE);
                dto.setTo(selected);
            }
        }
        dto.setBody(msg);
        messageService.sendMessage(dto.convertToJson());
        input.clear();

    }

    private void showMessage(MessageDTO message) {
        String msg = String.format("[%s] [%s] -> %s\n", message.getMessageType(), message.getFrom(), message.getBody());
        chatArea.appendText(msg);
    }

    @Override
    public void processMessage(String msg) {
        Platform.runLater(() -> {
            MessageDTO dto = MessageDTO.convertFromJson(msg);
            System.out.println("Received message");
            switch (dto.getMessageType()) {
                case PUBLIC_MESSAGE, PRIVATE_MESSAGE -> showMessage(dto);
                case CLIENTS_LIST_MESSAGE -> refreshUserList(dto);
                case AUTH_CONFIRM -> {
                    ChatApp.getStage().setTitle(dto.getBody());
                    authPanel.setVisible(false);
                    chatBox.setVisible(true);
                    inputBox.setVisible(true);
                    menuBar.setVisible(true);
                }
                case ERROR_MESSAGE ->
                    showError(dto);

            }
        });
    }




    public void aboutTheProgram(ActionEvent actionEvent) {

    }

    public void textSize(ActionEvent actionEvent) {

    }

    public void minimizeToTrade(ActionEvent actionEvent) {

    }

    public void fullScreen(ActionEvent actionEvent) {

    }

    public void topWindows(ActionEvent actionEvent) {

    }

    public void connection(ActionEvent actionEvent) {

    }

    public void onlineStatus(ActionEvent actionEvent) {

    }

    public void profile(ActionEvent actionEvent) {

    }

    public void sendAuth(ActionEvent actionEvent) {
        String log = loginField.getText();
        String pass = passField.getText();
        if(log.equals(" ") || pass.equals(" ")) return;
        MessageDTO dto = new MessageDTO();
        dto.setLogin(log);
        dto.setPassword(pass);
        dto.setMessageType(MessageType.SEND_AUTH_MESSAGE);
        messageService.sendMessage(dto.convertToJson());
    }

    private void showError(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong!");
        alert.setHeaderText(e.getMessage());
        VBox dialog = new VBox();
        Label label = new Label("Trace:");
        TextArea textArea = new TextArea();
        //TODO
        StringBuilder builder = new StringBuilder();
        for (StackTraceElement el : e.getStackTrace()) {
            builder.append(el).append(System.lineSeparator());
        }
        textArea.setText(builder.toString());
        dialog.getChildren().addAll(label, textArea);
        alert.getDialogPane().setContent(dialog);
        alert.showAndWait();
    }
    private void showError(MessageDTO dto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong!");
        alert.setHeaderText(dto.getMessageType().toString());
        VBox dialog = new VBox();
        Label label = new Label("Trace:");
        TextArea textArea = new TextArea();
        textArea.setText(dto.getBody());
        dialog.getChildren().addAll(label, textArea);
        alert.getDialogPane().setContent(dialog);
        alert.showAndWait();
    }
}
