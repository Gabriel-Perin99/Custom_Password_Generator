package org.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.security.SecureRandom;
import java.util.Base64;

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

        //objects belonging to the total password generation method:
        CheckBox totalPass = new CheckBox("Gerador Total");
        Text totalDescription = new Text("Quantidade de Caracteres: ");
        TextField totalLengthValue = new TextField();
        totalLengthValue.setPromptText("Ex: 12");
        Button btnTotal = new Button("Gerar Senha!");

        //Objects belonging to the partial password generation method:
        CheckBox partialPass = new CheckBox("Adicione uma Palavra");
        Text wordInfo = new Text("Digite sua Palavra:");
        TextField word = new TextField();
        word.setPromptText("EX: Cachorro12");
        Button btnPartial = new Button("Gerar Senha!");

        //Here is the text field where the password will appear
        TextField result = new TextField();
        result.setPromptText("Resultado será Impresso aqui!");
        result.setEditable(false);

        HBox hBox = new HBox(totalPass,partialPass);
        hBox.setSpacing(15);
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(15);
        layout.setVgap(5);
        layout.add(title, 0,0,3,1);
        layout.add(hBox,1,1);

        //in this section is where the logic is for the specific elements of each method to be implemented the interface

        totalPass.setOnAction(_ ->{
            layout.getChildren().removeAll(totalLengthValue,totalDescription, btnTotal,word,wordInfo, btnPartial);

            if (totalPass.isSelected()) {
                partialPass.setSelected(false);
                layout.addRow(2, totalDescription);
                layout.add(totalLengthValue, 1, 2, 2, 1);
                layout.add(btnTotal, 1, 3);}
            });

        partialPass.setOnAction(_ -> {

            layout.getChildren().removeAll(totalLengthValue,totalDescription, btnTotal, word, wordInfo, btnPartial);

            if (partialPass.isSelected()) {
                totalPass.setSelected(false);
                layout.addRow(2, wordInfo);
                layout.add(word, 1, 2, 2, 1);
                layout.add(btnPartial, 1, 3);
            }
        });

        layout.add(result, 0,4,5,1);

        //This section is where the action of the buttons is configured to generate the results based on the user filling
        btnTotal.setOnAction(_ -> {
            try{
                int getLenght = Integer.parseInt(totalLengthValue.getText());
                if (getLenght <= 0){
                    result.setText("Digite um número maior que zero!");
                    return;
                }
                result.setText(totalPasswordGenerator(getLenght));
            } catch (NumberFormatException e) {
                result.setText("Digite um número válido!");
            }
        });

        btnPartial.setOnAction(_->{
            try{
                String getWord = word.getText();
                if(getWord == ""){
                    result.setText("É necessário preencher o campo!");
                    return;
                }
                result.setText(partialPasswordGenerator(getWord));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Scene scene = new Scene(layout, 500,400);
        stage.setTitle("Gerador de Senhas");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args){
        launch(args);
    }
}
