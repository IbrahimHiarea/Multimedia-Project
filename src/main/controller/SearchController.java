package main.controller;

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
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

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
    private LocalDate date;
    private int width;
    private int height;

    public void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\\\Users\\\\ASUS\\\\Desktop\\\\University\\\\4-Th Year\\\\Chapter 2\\\\Multimedia\\\\multimedia-project\\\\src\\\\main\\\\resources\\\\images"));
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
        directoryChooser.setInitialDirectory(new File("C:\\\\Users\\\\ASUS\\\\Desktop\\\\University\\\\4-Th Year\\\\Chapter 2\\\\Multimedia\\\\multimedia-project\\\\src\\\\main\\\\resources\\\\images"));
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
            colors.add(colorPicker.getValue());
        } else {
            System.out.println("Select Color");
        }
    }

    public void selectDate(ActionEvent event){
        if(datePicker.getValue() != null){
            date = datePicker.getValue();
        } else {
            System.out.println("Select Date");
        }
    }

    public void selectWidth(ActionEvent event){
        if(widthField.getText() != null){
            width = Integer.parseInt(widthField.getText());
        } else {
            System.out.println("Select Width");
        }
    }

    public void selectHeight(ActionEvent event){
        if(heightField.getText() != null){
            height = Integer.parseInt(heightField.getText());
        } else {
            System.out.println("Select Height");
        }
    }

    public void submit(ActionEvent event) throws IOException {
        if(imageView.getImage() != null  &&  !directories.isEmpty()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/SearchResult.fxml"));
            root = loader.load();
            SearchResultController searchResultController = loader.getController();

            searchResultController.SearchResult(imagePath , directories , colors , date , (widthField.getText() == null ? 0 : width) , (heightField.getText() == null ? 0 : height));

            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("Please Select Image First And Select Directory");
        }
    }
}
