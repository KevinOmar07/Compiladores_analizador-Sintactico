package com.example.analizador_sintactico;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    @FXML
    private ImageView idImagenView;
    @FXML
    private Label labelStatus;
    @FXML
    private Label labelStatus2;
    @FXML
    private TextField idEntrada;
    @FXML
    private Pane paneLista;
    @FXML
    private Button btnLimpiar;
    @FXML
    private ListView listView;

    AnalizadorLexico analizador;

    @FXML
    public void initialize() {
        System.out.println("second");
        analizador = new AnalizadorLexico();

    }

    @FXML
    private void validarEntrada(){
        //String entrada = idEntrada.getText().replace(" ",""); // Se eliminan los espacios de la entrada
        String entrada =idEntrada.getText();
        if (entrada.length() > 0){
            labelStatus.setText("");
            paneLista.setVisible(true);
            btnLimpiar.setVisible(true);
            ArrayList<ArrayList<String>> listaValidados = analizador.analizarEntrada(entrada);

            System.out.println("Tamaño: " +  listaValidados.size());

            for (ArrayList item : listaValidados){
                //System.out.println("Token: " + item.get(2) + " | Dato: " + item.get(0) + " - Correcto");
                String estado;
                if (item.get(1).equals("1")){
                    estado = "Correcto";
                } else {
                    estado = "Incorrecto";
                }
                listView.getItems().add("Token: " + item.get(2) + " | Dato: " + item.get(0) + " - " + estado);

            }

            if (analizador.getStatus() && listaValidados.size() > 0){
                AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(listaValidados);
                analizadorSintactico.analizarEntrada();
                if (analizadorSintactico.getStatus()){
                    labelStatus.setText("Entrada correcta");
                    labelStatus.setStyle("-fx-text-fill: GREEN");
                } else {
                    String mjs = analizadorSintactico.getMensaje();
                    String mjs2 = analizadorSintactico.getMensaje2();
                    labelStatus.setText(mjs);
                    labelStatus.setStyle("-fx-text-fill: RED");
                    labelStatus2.setText(mjs2);
                    labelStatus2.setStyle("-fx-text-fill: ORANGE");
                }

            } else {
                labelStatus.setText("Entrada incorrecta: Error de lexico");
                labelStatus.setStyle("-fx-text-fill: RED");
                if (listaValidados.isEmpty()){
                    System.out.println("No hay");
                    listView.getItems().add("Entrada incorrecta : " + entrada);
                }
            }
        } else {
            System.out.println("Entrada vacía");
            labelStatus.setText("Entrada Vacía");
            labelStatus.setStyle("-fx-text-fill: RED");
        }
    }

    @FXML
    private void limpiar(){
        idEntrada.clear();
        paneLista.setVisible(false);
        btnLimpiar.setVisible(false);
        labelStatus.setText("");
        labelStatus2.setText("");
        listView.getItems().clear();
    }
}