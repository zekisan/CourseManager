package controller;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import model.Course;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This is our MainController class, here we initialize all components of our
 * .fxml file and can set ActionListener for example...
 * 
 * @author ezequiel
 *
 */
public class MainController implements Initializable {

	@FXML
	private Button saveBtn;
	@FXML
	private Button resetBtn;

	// Text fields
	@FXML
	private TextField courseNameTF;

	@FXML
	private TextField maxParticipantsTF;

	@FXML
	private ListView<Course> courseList;

	@FXML
	private TextField priceTF;

	@FXML
	private TextArea descriptionTF;

	@Override
	public void initialize(URL location, ResourceBundle bundle) {
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				courseNameTF.clear();
				maxParticipantsTF.clear();
			}
		});

		courseList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					if(!courseList.getSelectionModel().isEmpty()){
						int selectedIndex = courseList.getSelectionModel()
								.getSelectedIndex();
						Course course = courseList.getItems().get(selectedIndex);
						showPopUp(course, false);
					}
				}
			}

		});
		
		ContextMenu contextMenu = new ContextMenu();
		MenuItem editItem = new MenuItem("Edit");
		editItem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Course selectedCourse = courseList.getSelectionModel().getSelectedItem();
				showPopUp(selectedCourse, true);
			}
		});
		contextMenu.getItems().add(editItem);
		courseList.setContextMenu(contextMenu);
	}
	
	private void showPopUp(final Course course, final boolean editable) {
		Stage popupStage = new Stage();
		popupStage.setTitle("Course: " + course.getName());

		VBox root = new VBox();
		TextField courseName = new TextField(course.getName());
		courseName.setEditable(editable);

		TextArea description = new TextArea(course.getDescription());
		description.setEditable(editable);

		TextField price = new TextField(String.valueOf(course
				.getPrice()));
		price.setEditable(editable);

		root.getChildren().addAll(courseName, description, price);
		Scene scene = new Scene(root, 648, 480);
		popupStage.setScene(scene);
		popupStage.show();
	}

	@FXML
	public void handleSaveButton() {
		String courseName = courseNameTF.getText();
		String priceText = priceTF.getText();
		String description = descriptionTF.getText();
		try {
			double price = Double.parseDouble(priceText);
			int maxParts = Integer.parseInt(maxParticipantsTF.getText());
			Course course = new Course(courseName, description, maxParts, price);
			courseList.getItems().add(course);
		} catch (NumberFormatException e) {
			System.out.println("Erro!");
		}
	}
}
