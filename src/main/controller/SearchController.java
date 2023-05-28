package main.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class SearchController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView imageView;

    @FXML
    private ListView directoryList;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField widthField;

    @FXML
    private TextField heightField;

    @FXML
    private CheckBox cut;

    @FXML
    private Label imageWidth;

    @FXML
    private Label imageHeight;

    @FXML
    private TextField x1Field;

    @FXML
    private TextField y1Field;

    @FXML
    private TextField x2Field;

    @FXML
    private TextField y2Field;

    private String imagePath = "";
    private ArrayList<String> directories = new ArrayList<>();
    private ArrayList<Color> colors = new ArrayList<>();
    private Date date = new Date(1000000000);
    private int width = -1;
    private int height = -1;
    private int x1 = -1 , y1 = -1 , x2 = -1 , y2 = -1;

    public void initialize() {
        widthField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                String value = newValue.replaceAll("[^\\d]", "");
                widthField.setText(value);
                if(value.compareTo("")!=0)
                    width = Integer.parseInt(value);
            }
        });

        heightField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                String value = newValue.replaceAll("[^\\d]", "");
                heightField.setText(value);
                if(value.compareTo("")!=0)
                    height = Integer.parseInt(value);
            }
        });

        x1Field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                String value = newValue.replaceAll("[^\\d]", "");
                x1Field.setText(value);
                if(value.compareTo("")!=0)
                    x1 = Integer.parseInt(value);
            }
        });

        y1Field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                String value = newValue.replaceAll("[^\\d]", "");
                y1Field.setText(value);
                if(value.compareTo("")!=0)
                    y1 = Integer.parseInt(value);
            }
        });

        x2Field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                String value = newValue.replaceAll("[^\\d]", "");
                x2Field.setText(value);
                if(value.compareTo("")!=0)
                    x2 = Integer.parseInt(value);
            }
        });

        y2Field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                String value = newValue.replaceAll("[^\\d]", "");
                y2Field.setText(value);
                if(value.compareTo("")!=0)
                    y2 = Integer.parseInt(value);
            }
        });
    }

    public void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\ASUS\\Desktop\\University\\4-Th Year\\Chapter 2\\Multimedia\\multimedia-project\\src\\main\\resources\\images"));
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null){
            imagePath = file.getPath();
            Image image = new Image(imagePath);
            imageView.setImage(image);
            imageWidth.setText(imageWidth.getText() + " " + image.getWidth());
            imageHeight.setText(imageHeight.getText() + " " + image.getHeight());
        } else {
            System.out.println("NO File");
        }
    }

    public void selectDirectory(ActionEvent event){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("C:\\Users\\ASUS\\Desktop\\University\\4-Th Year\\Chapter 2\\Multimedia\\multimedia-project\\src\\main\\resources\\images"));
        File directory = directoryChooser.showDialog(null);
        if(directory != null){
            directoryList.getItems().add(directory.getAbsolutePath());
            directories.add(directory.getAbsolutePath());
        } else {
            System.out.println("NO Directory");
        }
    }

    public void selectColors(ActionEvent event){
        if(colorPicker.getValue() != null){
            colors.add(
                    new Color(
                            (float)colorPicker.getValue().getRed(),
                            (float)colorPicker.getValue().getGreen(),
                            (float)colorPicker.getValue().getBlue()
                    )
            );
        } else {
            System.out.println("Select Color");
        }
    }

    public void selectDate(ActionEvent event){
        if(datePicker.getValue() != null){
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate localDate = datePicker.getValue();
            date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        } else {
            System.out.println("Select Date");
        }
    }

    public void isCut(ActionEvent event){
        if(cut.isSelected()){
            x1Field.setDisable(false);
            y1Field.setDisable(false);
            x2Field.setDisable(false);
            y2Field.setDisable(false);
        } else {
            x1Field.setText("");
            y1Field.setText("");
            x2Field.setText("");
            y2Field.setText("");
            x1 = y1 = x2 = y2 = -1;
            x1Field.setDisable(true);
            y1Field.setDisable(true);
            x2Field.setDisable(true);
            y2Field.setDisable(true);
        }
    }

    public void submit(ActionEvent event) throws IOException {
        if(imageView.getImage() != null  &&  !directories.isEmpty()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/SearchResult.fxml"));
            root = loader.load();
            SearchResultController searchResultController = loader.getController();

            searchResultController.SearchResult(imagePath , directories , colors , date , width , height , x1 , y1 , x2 , y2);

            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("Please Select Image First And Select Directory");
        }
    }
}
