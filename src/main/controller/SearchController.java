package main.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
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

    private String imagePath = "";
    private ArrayList<String> directories = new ArrayList<>();
    private ArrayList<Color> colors = new ArrayList<>();
    private Date date = new Date(1000000000);
    private int width = -1;
    private int height = -1;

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
    }

    public void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\Twfek Ajeneh\\Desktop\\Collage\\Forth year\\Chapter two\\Practical\\Multimedia\\multimedia-project\\src\\main\\resources\\images\\search"));
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null){
            imagePath = file.getPath();
            Image image = new Image(imagePath);
            imageView.setImage(image);
        } else {
            System.out.println("NO File");
        }
    }

    public void selectDirectory(ActionEvent event){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("C:\\Users\\Twfek Ajeneh\\Desktop\\Collage\\Forth year\\Chapter two\\Practical\\Multimedia\\multimedia-project\\src\\main\\resources\\images\\search"));
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

    public void submit(ActionEvent event) throws IOException {
        if(imageView.getImage() != null  &&  !directories.isEmpty()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/SearchResult.fxml"));
            root = loader.load();
            SearchResultController searchResultController = loader.getController();

            searchResultController.SearchResult(imagePath , directories , colors , date , width , height);

            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("Please Select Image First And Select Directory");
        }
    }
}
