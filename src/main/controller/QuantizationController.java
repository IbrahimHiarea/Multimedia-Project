package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuantizationController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView imageView;

    @FXML
    private ChoiceBox<String> algorithm;

    private String[] algorithms = {"Octree" , "Floyd Steinberg" , "Simple Algorithm"};

    private String imagePath = "";

    public void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File ("C:\\Users\\Twfek Ajeneh\\Pictures\\Background"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        algorithm.setValue("Octree");
        algorithm.getItems().addAll(algorithms);
    }

    public void submit(ActionEvent event) throws IOException {
        if(imageView.getImage() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/QuantizationResult.fxml"));
            root = loader.load();
            QuantizationResultController quantizationResultController = loader.getController();

            if(algorithm.getValue() == "Octree"){
                quantizationResultController.octree(imagePath);
            } else if(algorithm.getValue() == "Floyd Steinberg"){
                quantizationResultController.floydSteinberg(imagePath);
            } else {
                quantizationResultController.simple(imagePath);
            }

            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("Please Select Image First");
        }
    }

}
