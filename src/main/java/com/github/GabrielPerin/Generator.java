package com.github.GabrielPerin;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import javafx.scene.Scene;

public class Generator extends Application {
    //Method to generate passwords with a word to the user's choice
    private static String partialPasswordGenerator(String word) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[3];
        random.nextBytes(bytes);

        byte[] bytes1 = new byte[3];
        random.nextBytes(bytes1);

        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        String encoded2 = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes1);

        return encoded+word+encoded2;
    }
    //method that generates a password based on the amount of desired characters
    private static String totalPasswordGenerator(int length){
        SecureRandom randomString = new SecureRandom();
        byte[] bytes = new byte[length];

        randomString.nextBytes(bytes);

        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
       //If the string is greater than the desired size, it is cut.
        if (encoded.length() > length) return encoded.substring(0, length);
        return encoded;
    }

    @Override
    public void start(Stage stage){

        Label title = new Label("Gerador de Senhas Customizavel");
        title.getStyleClass().add("title");
        Label info = new Label("Selecione uma Opção:");
        info.getStyleClass().add("info");

        //objects belonging to the total password generation method:
        CheckBox totalPassword = new CheckBox("Gerador Total");
        Text totalDescription = new Text("Quant. de Caracteres: ");
        TextField totalLengthValue = new TextField();
        totalLengthValue.setPromptText("Ex: 12");
        Button btnTotal = new Button("Gerar Senha!");

        //Objects belonging to the partial password generation method:
        CheckBox partialPassword = new CheckBox("Adicione uma Palavra");
        Text wordInfo = new Text("Digite sua Palavra:");
        TextField word = new TextField();
        word.setPromptText("EX: Cachorro12");
        Button btnPartial = new Button("Gerar Senha!");

        //Here is the text field where the password will appear
        TextField result = new TextField();
        result.setPromptText("Resultado será Impresso aqui!");
        result.setEditable(false);

        //Set a Copy Button
        Button copyBtn = new Button("Copiar");

        //Base Layout
        HBox checkBox = new HBox(totalPassword, partialPassword);
        checkBox.setSpacing(15);
        checkBox.setAlignment(Pos.CENTER);
        checkBox.getStyleClass().add("checkBox");

        HBox totalPassInfo = new HBox(totalDescription,totalLengthValue);
        totalPassInfo.setSpacing(10);
        totalPassInfo.setAlignment(Pos.CENTER);
        GridPane.setHalignment(totalPassInfo,HPos.CENTER);

        HBox partialPassInfo = new HBox(wordInfo,word);
        partialPassInfo.setSpacing(10);
        partialPassInfo.setAlignment(Pos.CENTER);
        GridPane.setHalignment(totalPassInfo,HPos.CENTER);

        GridPane layout = new GridPane();
        layout.getStyleClass().add("bkg");
        layout.setAlignment(Pos.CENTER);
        GridPane.setHalignment(layout,HPos.CENTER);
        layout.setVgap(15);

        layout.add(title, 0,0,3,1);
        GridPane.setHalignment(title,HPos.CENTER);

        layout.add(info,1,1,3,1);
        GridPane.setHalignment(info, HPos.CENTER);

        layout.add(checkBox,0,2,3,1);

        //in this section is where the logic is for the specific elements of each method to be implemented the interface

        totalPassword.setOnAction(_ ->{
            layout.getChildren().removeAll(totalPassInfo, btnTotal,partialPassInfo, btnPartial);

            if (totalPassword.isSelected()) {
                result.setText("");
                partialPassword.setSelected(false);
                layout.add(totalPassInfo, 1, 3, 3, 1);
                layout.add(btnTotal, 0, 4,3,1);}
                GridPane.setHalignment(btnTotal,HPos.CENTER);
            });

        partialPassword.setOnAction(_ -> {

            layout.getChildren().removeAll(totalPassInfo, btnTotal,partialPassInfo, btnPartial);

            if (partialPassword.isSelected()) {
                result.setText("");
                totalPassword.setSelected(false);
                layout.add(partialPassInfo, 1, 3, 3, 1);
                layout.add(btnPartial, 0, 4,3,1);
                GridPane.setHalignment(btnPartial,HPos.CENTER);
            }
        });

        layout.add(result, 0,5,3,1);
        //Add the copy button the password when the result is not empty
        result.textProperty().addListener((observable,oldValue,newValue )->{
            if (newValue.isEmpty()) {
                layout.getChildren().removeAll(copyBtn);
            }else if (!layout.getChildren().contains(copyBtn)){
                layout.add(copyBtn,1,5,3,1);
                GridPane.setHalignment(copyBtn,HPos.RIGHT);
            }
        });
        //This section is where the action of the buttons is configured to generate the results based on the user filling

        btnTotal.setOnAction(_ -> {
            try{
                int getLenght = Integer.parseInt(totalLengthValue.getText());
                if (getLenght <= 0){
                    result.setText("");
                    result.setPromptText("Digite um número maior que zero!");
                    return;
                } else if (getLenght > 20) {
                    result.setText("");
                    result.setPromptText("Máximo de 20 Caracteres!");
                    return;
                }
                result.setText(totalPasswordGenerator(getLenght));
            } catch (NumberFormatException e) {
                result.setPromptText("Digite um número válido!");
            }
        });

        btnPartial.setOnAction(_->{
            try{
                String getWord = word.getText();
                if(Objects.equals(getWord, "")){
                    result.setText("");
                    result.setPromptText("É necessário preencher o campo!");
                    return;
                }
                result.setText(partialPasswordGenerator(getWord));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        //Copy button logic
        copyBtn.setOnAction(_->{
            String copyText = result.getText();
            //Object that copies the text established in variable copytext
            ClipboardContent content = new ClipboardContent();
            content.putString(copyText);
            //object that transfers the copied text to the system
            Clipboard clipboard = Clipboard.getSystemClipboard();
            clipboard.setContent(content);
            //Object that generates a warning when the password is copied
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Senha Copiada para área de transferência");
            alert.show();

            result.setText("");
            totalPassword.setSelected(false);
            partialPassword.setSelected(false);
            word.setText("");
            totalLengthValue.setText("");
            layout.getChildren().removeAll(totalPassInfo, btnTotal,partialPassInfo, btnPartial);

        });

        Scene scene = new Scene(layout, 500,400);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/styles.css")).toExternalForm());
        stage.setTitle("Gerador de Senhas");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args){
        launch(args);
    }
}
