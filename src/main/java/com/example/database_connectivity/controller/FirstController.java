package com.example.database_connectivity.controller;

import com.example.database_connectivity.DatabaseConnectivity;
import com.example.database_connectivity.dao.ItemsDao;
import com.example.database_connectivity.model.Category;
import com.example.database_connectivity.model.Items;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstController {
    public TextField txtID;
    public TextField txtName;
    public TextField txtPrice;
    public TextArea txtDesc;
    public ComboBox cboxCategory;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView tbView;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colPrice;
    public TableColumn colCategory;
    private Stage stage;

    private SecondController sc;
    private ObservableList<Items>itemsOList;

    public void initialize() throws IOException {
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader(DatabaseConnectivity.class.getResource("second_page.fxml"));
        Scene scene = new Scene(loader.load());

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("CategoryManagement");
        stage.setScene(scene);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        sc = loader.getController();

        cboxCategory.setItems(sc.olistCategory);
        cboxCategory.getSelectionModel();

        ItemsDao Idao = new ItemsDao();
        itemsOList = Idao.getData();
        tbView.setItems(itemsOList);
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

    }
    public void onSave(ActionEvent event) {
        ItemsDao Idao = new ItemsDao();
        if (txtID.getText().isEmpty() || txtName.getText().isEmpty() || txtDesc.getText().isEmpty() || txtPrice.getText().isEmpty() || cboxCategory.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR,"please fill in all the field", ButtonType.OK);
            alert.showAndWait();
        }else {
            Idao.addData(new Items(Integer.valueOf(txtID.getText()),txtName.getText(),Double.valueOf(txtPrice.getText()),txtDesc.getText(), (Category) this.cboxCategory.getValue()));
            itemsOList = Idao.getData();
            tbView.setItems(itemsOList);
        }

    }

    public void onReset(ActionEvent event) {
        txtID.clear();
        txtName.clear();
        txtPrice.clear();
        txtDesc.clear();
        cboxCategory.setItems(null);
    }

    public void onShowCategoryManagement(ActionEvent event) {
        stage.showAndWait();
    }

    public void onClose(ActionEvent event) {
        cboxCategory.getScene().getWindow().hide();
        System.out.println("Close");
    }
}