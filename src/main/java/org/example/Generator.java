package org.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.security.SecureRandom;
import java.util.Base64;

import javafx.scene.Scene;

public class Generator extends Application {

    private static String genString(int lenght){
        SecureRandom randomString = new SecureRandom();
        byte[] bytes = new byte[lenght];
        randomString.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    @Override
    public void start(Stage stage){
    int stringLenght;

        Label title = new Label("Gerador de Senhas Customizavel");
        Text lenghtDescription = new Text("Quantidade de Caracteres: ");
        TextField lenght = new TextField();
        Button btn = new Button("Gerar Senha!");
        Label result = new Label();

        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(15);
        layout.setVgap(5);

        layout.add(title, 0,0,3,1);
        layout.addRow(1,lenghtDescription);
        layout.add(lenght,1,1,2,1);
        layout.add(btn,1,3);
        layout.add(result, 0,4,3,1);

        btn.setOnAction(actionEvent -> {
            try{
                int getLenght = Integer.parseInt(lenght.getText());
                result.setText(genString(getLenght));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Scene scene = new Scene(layout, 300,240);
        stage.setTitle("Gerador de Senhas");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args){
        launch(args);
    }
}
