package com.example.analizador_sintactico;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Controller {
    @FXML
    private ImageView idImagenView;
    @FXML
    private Label labelStatus;
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
                System.out.println("Token: " + item.get(2) + " | Dato: " + item.get(0) + " - Correcto");
                String estado;
                if (item.get(1).equals("1")){
                    estado = "Correcto";
                } else {
                    estado = "Incorrecto";
                }
                listView.getItems().add("Token: " + item.get(2) + " | Dato: " + item.get(0) + " - " + estado);

            }

            //listView.getItems().add("Pedro");
            if (analizador.getStatus() && listaValidados.size() > 0){
                labelStatus.setText("Entrada correcta");
                labelStatus.setStyle("-fx-text-fill: GREEN");
            } else {
                labelStatus.setText("Entrada incorrecta");
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
        listView.getItems().clear();
    }
}