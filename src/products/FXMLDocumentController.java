/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products;

import DataBase.ConnectDB;
import DataBase.Images_converter;
import Model.ModelTable;
import Model.Product;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Mohammad
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField search;

    @FXML
    private ChoiceBox<String> choicebox;

    @FXML
    private TableView<ModelTable> table;

    @FXML
    private ImageView newImageView;

    @FXML
    private TextField newName;

    @FXML
    private TextField newType;

    @FXML
    private Spinner<Double> newPrice;

    @FXML
    private ImageView updateImageView;

    @FXML
    private TextField updateName;

    @FXML
    private TextField updateType;

    @FXML
    private Spinner<Double> updatePrice;
    @FXML
    private Label newMessages;

    @FXML
    private Label updateMessages;
    ConnectDB connectDB;
    static File file;
    int id;

    @FXML
    void deleteAction(ActionEvent event) throws Exception {
        connectDB.delete(search.getText(), choicebox.getValue().toLowerCase());
        displayTable();

    }
    
    @FXML
    void tabAction(MouseEvent event) {
        newMessages.setText("");
        updateMessages.setText("");
    }

    @FXML
    void saveProduct(ActionEvent event) throws Exception {
        if (!newName.getText().isEmpty() && !newType.getText().isEmpty()
                && !newPrice.getValue().equals(0) && newImageView.getImage() != null) {

            byte[] data = Images_converter.convertImageToByteArray(file);
            Product product = new Product(newName.getText(), newType.getText(), data, newPrice.getValue().floatValue());

            newMessages.setText(connectDB.save(product));
            displayTable();
            newName.setText("");
            newImageView.setImage(null);
            newPrice.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0));
            newType.setText("");
        } else {

        }

    }

    @FXML
    void searchAction(ActionEvent event) throws Exception {
        if (!search.getText().isEmpty()) {

            table.setItems(connectDB.select(search.getText(), choicebox.getValue()));

            TableColumn id = new TableColumn("#Id");
            id.setCellValueFactory(new PropertyValueFactory("id"));

            TableColumn name = new TableColumn("Name");
            name.setCellValueFactory(new PropertyValueFactory("name"));

            TableColumn type = new TableColumn("Type");
            type.setCellValueFactory(new PropertyValueFactory("type"));

            TableColumn image = new TableColumn("Image");
            image.setCellValueFactory(new PropertyValueFactory("imageView"));
            image.setPrefWidth(100);

            TableColumn price = new TableColumn("Price");
            price.setCellValueFactory(new PropertyValueFactory("price"));

            table.getColumns().setAll(id, image, name, type, price);

        }

    }

    @FXML
    void selecteNewImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter totalFilter = new FileChooser.ExtensionFilter("Image files ", "*.JPG", "*.PNG", "*.gif", "*.bmp");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        FileChooser.ExtensionFilter extFilterGIF = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.gif");
        FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");

        fileChooser.getExtensionFilters().addAll(totalFilter, extFilterJPG, extFilterPNG, extFilterGIF, extFilterBMP);

        //Show open file dialog
        file = fileChooser.showOpenDialog(null);

        if (file.isFile()) {

            Image image = new Image(new FileInputStream(file));
            newImageView.setImage(image);

        }

    }
    

    @FXML
    void selecteUpdateImage(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter totalFilter = new FileChooser.ExtensionFilter("Image files ", "*.JPG", "*.PNG", "*.gif", "*.bmp");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        FileChooser.ExtensionFilter extFilterGIF = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.gif");
        FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");

        fileChooser.getExtensionFilters().addAll(totalFilter, extFilterJPG, extFilterPNG, extFilterGIF, extFilterBMP);

        //Show open file dialog
        file = fileChooser.showOpenDialog(null);

        if (file.isFile()) {

            Image image = new Image(new FileInputStream(file));
            updateImageView.setImage(image);

        }
    }

    @FXML
    void updateProduct(ActionEvent event) throws Exception {
        if (!updateName.getText().isEmpty() && !updateType.getText().isEmpty()
                && !updatePrice.getValue().equals(0) && updateImageView.getImage() != null) {

            byte[] data = Images_converter.convertImageToByteArray(updateImageView.getImage());
            Product product = new Product(id, updateName.getText(), updateType.getText(), data, updatePrice.getValue().floatValue());

            updateMessages.setText(connectDB.update(product));
            displayTable();
            updateName.setText("");
            updateImageView.setImage(null);
            updatePrice.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0));
            updateType.setText("");
        } else {

        }
    }
    
    @FXML
    void showAll(ActionEvent event) throws Exception {
        displayTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // connect DataBase
            connectDB = new ConnectDB();

            choicebox.getItems().addAll("Id", "Name", "Type", "Price");
            choicebox.setValue("Name");
            // Add a ChangeListener to the ComboBox
            choicebox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                    search.setPromptText(newValue);
                }
            });
            // End ChangeListener to the ComboBox

            // Add a ChangeListener to the Table
            table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ModelTable>() {
                @Override
                public void changed(ObservableValue<? extends ModelTable> observable, ModelTable oldValue, ModelTable newValue) {
                    try {
                        updateImageView.setImage(newValue.getImageView().getImage());
                        updateName.setText(newValue.getName());
                        updateType.setText(newValue.getType());
                        id = newValue.getId();
                        updatePrice.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(newValue.getPrice(), Double.MAX_VALUE, 0));

                        switch (choicebox.getValue()) {
                            case "Id":
                                search.setText(String.valueOf(newValue.getId()));
                                break;
                            case "Name":
                                search.setText(newValue.getName());
                                break;
                            case "Type":
                                search.setText(newValue.getType());
                                break;
                            case "Price":
                                search.setText(String.valueOf(newValue.getPrice()));
                                break;

                        }
                    } catch (Exception e) {
                    }
                }

            });
            newPrice.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0));
            updatePrice.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0));

            // show data table runtime
            displayTable();
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void displayTable() throws Exception {

        table.setItems(connectDB.getAll());

        TableColumn id = new TableColumn("#Id");
        id.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn type = new TableColumn("Type");
        type.setCellValueFactory(new PropertyValueFactory("type"));

        TableColumn image = new TableColumn("Image");
        image.setCellValueFactory(new PropertyValueFactory("imageView"));
        image.setPrefWidth(100);

        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory("price"));

        table.getColumns().setAll(id, image, name, type, price);

    }

}
