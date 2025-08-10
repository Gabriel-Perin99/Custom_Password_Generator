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

    private static String genString(int length){
        SecureRandom randomString = new SecureRandom();
        byte[] bytes = new byte[length];
        randomString.nextBytes(bytes);
        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        if (encoded.length() > length) return encoded.substring(0, length);
        return encoded;
    }

    @Override
    public void start(Stage stage){

        Label title = new Label("Gerador de Senhas Customizavel");
        Text lengthDescription = new Text("Quantidade de Caracteres: ");
        TextField length = new TextField();
        Button btn = new Button("Gerar Senha!");
        Label result = new Label();

        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(15);
        layout.setVgap(5);

        layout.add(title, 0,0,3,1);
        layout.addRow(1,lengthDescription);
        layout.add(length,1,1,2,1);
        layout.add(btn,1,3);
        layout.add(result, 0,4,3,1);

        btn.setOnAction(_ -> {
            try{
                int getLenght = Integer.parseInt(length.getText());
                if (getLenght <= 0){
                    result.setText("Digite um número maior que zero!");
                    return;
                }
                result.setText(genString(getLenght));
            } catch (NumberFormatException e) {
                result.setText("Digite um número válido!");
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
