package project.fxui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.models.Course;
import project.models.CourseList;

public class Controller {
	
	@FXML private Label title;
	
	@FXML private Pane inputfields;
	@FXML private TextField coursefield;
	@FXML private TextField lettergradefield;
	@FXML private TextField numbergradefield;
	@FXML private Button add;
	
	@FXML private RadioButton letterradio;
	@FXML private RadioButton numberradio;
	
	@FXML private Text errormessage;
	@FXML private Text errormessage2;
	@FXML private Text success;
	
	@FXML private ListView<String> courselistview;
	@FXML private ListView<String> numberlistview;
	@FXML private ListView<String> letterlistview;
	
	@FXML private Button coursesort;
	@FXML private Button lettersort;
	@FXML private Button numbersort;
	
	@FXML private Pane buttoncontainer;
	@FXML private Button clear1;
	@FXML private Button clear2;
	@FXML private Button clear3;
	@FXML private Button clear4;
	@FXML private Button clear5;
	@FXML private Button clear6;
	@FXML private Button clear7;
	@FXML private Button clear8;
	@FXML private Button clear9;
	@FXML private Button clear10;
	
	@FXML private Button clearall;
	@FXML private Button calculate;
	
	@FXML private TextField filelocationfield;
	
	@FXML private Pane results;
	@FXML private Pane resultslabels;
	
	@FXML private Label best;
	@FXML private Label worst;
	@FXML private Label average;
	@FXML private Label statslabel;
	
	@FXML private Label bestlabel;
	@FXML private Label worstlabel;
	@FXML private Label averagelabel;
	
	CourseList courselist;
	IFileReading fileSupport;
	
	@FXML
	void initialize() {
		courselist = new CourseList();
		fileSupport = new FileSupport();
		setCourseList(new CourseList());
	}
	
	private void setCourseList(final CourseList courselist) {
		this.courselist = courselist;
		updateLists();
	}
	
	@FXML
	private void handleAddButtonAction() {
		if (!courselist.listIsFull()) {
			if (coursefield.getText() == "" || coursefield.getText() == null) {
				errormessage.setText("Please enter course name and grade!");
			}
			else if (lettergradefield.getText() == "" && numbergradefield.getText() == "") {
				errormessage.setText("Please enter a letter and/or number grade!");
			}
			else {
				try {
					courselist.addCourse(new Course(coursefield.getText().trim(), lettergradefield.getText().trim(), numbergradefield.getText().trim()));
					if (courselist.getCalculatedData() == null) {
						((Button) buttoncontainer.getChildren().get(courselist.getCourses().size() - 1)).setVisible(true);
					}
					resetText();
					updateLists();
				}
				catch (NumberFormatException e) {
					errormessage.setText("Number grade must be a number between 0 and 100!");
					errormessage2.setText("");
				}
				catch (IllegalArgumentException e) {
					errormessage.setText("Invalid format!");
					errormessage2.setText("");
				}
				catch (IllegalStateException e) {
					errormessage.setText("Course: " + coursefield.getText() + " already added!");
					errormessage2.setText("");
				}
				catch (Exception e) {
					errormessage.setText("Error!");
					errormessage2.setText("");
				}
			}
		}
		else {
			errormessage.setText("Course list is full!");
		}
		// updates the statistics section when a course is added and there is already an active calculated data set
		if (courselist.getCalculatedData() != null) {
			courselist.createCalculatedData();
			bestlabel.setText(courselist.getCalculatedData().getHighestGrade() + " (" + courselist.getCalculatedData().getBestCourse() + ")");
			worstlabel.setText(courselist.getCalculatedData().getLowestGrade() + " (" + courselist.getCalculatedData().getWorstCourse() + ")");
			averagelabel.setText(courselist.getCalculatedData().getAverageGrade());
		}
	}
	
	@FXML
	private void handleClearOne(ActionEvent event) {
		Button sourcebutton = (Button) event.getSource();
		// identifies which button is clicked
		int whichclearbuttonclicked = Integer.parseInt(String.valueOf(sourcebutton.getId().charAt(5)));
		AlertWindowController a = new AlertWindowController("yesno","Are you sure you want to delete this course?");
		a.window.initModality(Modality.APPLICATION_MODAL);
		a.layout.getChildren().addAll(a.label,a.yes,a.no);
		a.yes.setOnAction(e -> {
			a.window.close();
			// clears the course with the same index as the button clicked
			clearOne(whichclearbuttonclicked);
		});
		a.window.showAndWait();
	}
	
	private void clearOne(int i) {
		courselist.removeCourse(i-1);
		updateLists();
		buttoncontainer.getChildren().get(courselist.getLength()).setVisible(false);
	}
	
	@FXML
	private void handleClearAll() {
		if (courselist.getCourses().size() > 0) {			
			AlertWindowController a = new AlertWindowController("yesno", "Are you sure you want to clear your courses?");
			a.window.initModality(Modality.APPLICATION_MODAL);
			a.layout.getChildren().addAll(a.label,a.yes,a.no);
			a.yes.setOnAction(e -> {
				a.window.close();
				setCourseList(new CourseList());
				resetGUI();
			});
			a.window.showAndWait();
		}
		else {
			setCourseList(new CourseList());
			resetGUI();
		}
	}	
	
	private void resetGUI() {
		for (int i = 0; i < 3; i++) {
			((TextField) inputfields.getChildren().get(i)).setText("");	
		}
		title.setText(null);
		courselist.removeAll();
		for (int i = 0; i < 10; i++) {
			((Button) buttoncontainer.getChildren().get(i)).setVisible(false);	
		}
		resultslabels.setVisible(false);
		letterradio.setSelected(false);
		numberradio.setSelected(false);
		calculate.setVisible(true);
		coursesort.setText("↓");
		lettersort.setText("↓");
		numbersort.setText("↓");
		updateLists();
		resetText();
	}
	
	@FXML
	private void handleSetName() {
		if (filelocationfield.getText() != "") {
			title.setText(filelocationfield.getText() + "'s Gradebook");
			courselist.setName(filelocationfield.getText());
		}
		else {
			title.setText("");
		}
	}
	
	@FXML
	private void handleCalculate(ActionEvent event) {		
		if (courselist.getLength() < 1) {
			AlertWindowController a = new AlertWindowController("ok", "You need to add one or more courses before calculating!");
			a.window.initModality(Modality.APPLICATION_MODAL);
			a.setMessageProperties();
			a.layout.getChildren().addAll(a.label,a.ok);
			a.ok.setOnAction(e -> a.window.close());
			a.window.showAndWait();
		}
		else {
			showResults();
		}
	}
	
	private void showResults() {
		AlertWindowController a;
		if (courselist.getMode() == 0) {
			a = new AlertWindowController("ok", "Please choose whether to sort by number grade or letter grade.");
			a.window.initModality(Modality.APPLICATION_MODAL);
			a.setMessageProperties();
			a.layout.getChildren().addAll(a.label,a.ok);
			a.ok.setOnAction(e -> a.window.close());
			a.window.showAndWait();
		}
		else {
			if (courselist.containsNull() && courselist.getCalculatedData() == null) {
				a = new AlertWindowController("ok", "Values not entered will be approximated by a common equivalent scale.");
				a.window.initModality(Modality.APPLICATION_MODAL);
				a.setMessageProperties();
				a.layout.getChildren().addAll(a.label,a.ok);
				a.ok.setOnAction(e -> a.window.close());
				a.window.showAndWait();
			}
			
			courselist.createCalculatedData();
			showResultsGUIUpdate();
			
			bestlabel.setText(courselist.getCalculatedData().getHighestGrade() + " (" + courselist.getCalculatedData().getBestCourse() + ")");
			worstlabel.setText(courselist.getCalculatedData().getLowestGrade() + " (" + courselist.getCalculatedData().getWorstCourse() + ")");
			averagelabel.setText(courselist.getCalculatedData().getAverageGrade());
		}
	}
	
	private void showResultsGUIUpdate() {
		calculate.setVisible(false);
		resultslabels.setVisible(true);
		results.setVisible(true);
		errormessage.setText("");
		errormessage2.setText("");
		for (int i = 0; i < 10; i++) {
			((Button) buttoncontainer.getChildren().get(i)).setVisible(false);	
		}
		resetText();
	}
	
	@FXML
	private void handleCourseSort() {
		this.setCourseList(		courselist.getSortedByCourseName());
		this.updateLists();
		if (coursesort.getText() == "↑") {
			coursesort.setText("↓");
		}
		else {
			coursesort.setText("↑");
		}
	}
	
	@FXML
	private void handleLetterSort() {
		for (int i = 0; i < courselist.getLetterGrades().size()-1; i++) {
			if (!courselist.getLetterGrades().get(i).equals(courselist.getLetterGrades().get(i + 1))) {
				this.setCourseList(		courselist.getSortedByLetter());
				this.updateLists();
				break;
			}
		}
		if (lettersort.getText() == "↑") {
			lettersort.setText("↓");
		}
		else {
			lettersort.setText("↑");
		}
	}
	
	@FXML
	private void handleNumberSort() {
		for (int i = 0; i < courselist.getNumberGrades().size()-1; i++) {
			if (!courselist.getNumberGrades().get(i).equals(courselist.getNumberGrades().get(i + 1))) {
				this.setCourseList(		courselist.getSortedByNumber());
				this.updateLists();
				break;
			}
		}
		if (numbersort.getText() == "↑") {
			numbersort.setText("↓");
		}
		else {
			numbersort.setText("↑");
		}
	}
	
	@FXML 
	private void handleMode(ActionEvent event) {		
		courselist.setMode((RadioButton)event.getSource());
		// instantly shows updated results if a calculated data set already exists
		if (courselist.getCalculatedData() != null) {
			showResults();
		}
	}
	
	@FXML
	private void handleSaveAction() {
		if (courselist.getCourses().size() > 0) {
			final String name = getFileLocation();
			if (name != null && !name.isBlank()) {
				try {
					fileSupport.writeCourseList1(courselist.getCalculatedData(), courselist, name);
					errormessage2.setText("");
					success.setText("Saved!");
				}
				catch (Exception e) {
					errormessage2.setText("Could not save!");
				}
			}
		}
		else {
			errormessage2.setText("No data to save!");
		}
	}
	
	@FXML
	private void handleLoadAction() {
		final String name = getFileLocation();
		if (name != null && !name.isBlank()) {
			try {
				setCourseList(fileSupport.readCourseList1(name));
				for (int i = 0; i < courselist.getLength(); i++) {
					((Button) buttoncontainer.getChildren().get(i)).setVisible(true);	
				}
				filelocationfield.setText("");
				resetText();
			}
			catch (IOException e) {
				loadfailedGUIUpdate();
			}
			catch (Exception e) {
				loadfailedGUIUpdate();
			}
		}
	}
	
	private void loadfailedGUIUpdate() {
		errormessage.setText("");
		errormessage2.setText("No such file!");
		letterradio.setSelected(false);
		numberradio.setSelected(false);
		resultslabels.setVisible(false);
		results.setVisible(false);
		calculate.setVisible(true);
	}
	
	@FXML
	private void handleRestart() {
		AlertWindowController a = new AlertWindowController("yesno", "Are you sure you want to delete all data and restart?");
		a.window.initModality(Modality.APPLICATION_MODAL);
		a.setMessageProperties();
		a.layout.getChildren().addAll(a.label,a.yes,a.no);
		a.no.setOnAction(e -> a.window.close());
		a.yes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				a.window.close();
				try {
					Parent root1 = FXMLLoader.load(getClass().getResource("Gradebook.fxml"));
					Stage stage = (Stage) clearall.getScene().getWindow();
					stage.setTitle("Personal Grade Statistics");
					stage.setScene(new Scene(root1));
					stage.show();
				}
				catch (Exception e) {
					errormessage.setText("Error! Cannot restart!");
				}
			}
		});
		a.window.showAndWait();
	}
	
	private String getFileLocation() {
		String name = filelocationfield.getText();
		if (name != null && !name.isBlank()) {
			return name;
		}
		else {
			AlertWindowController a;
			a = new AlertWindowController("ok", "Please enter a name!");
			a.window.initModality(Modality.APPLICATION_MODAL);
			a.setMessageProperties();
			a.layout.getChildren().addAll(a.label,a.ok);
			a.ok.setOnAction(e -> a.window.close());
			a.window.showAndWait();
			return "";
		}
	}
	
	private void updateLists() {
		courselistview.getItems().setAll(courselist.getCourseNames());
		letterlistview.getItems().setAll(courselist.getLetterGrades());
		numberlistview.getItems().setAll(courselist.getNumberGrades());
	}
	
	private void resetText() {
		filelocationfield.setText("");
		errormessage.setText(null);
		errormessage2.setText(null);
		success.setText(null);
		coursefield.clear();
		lettergradefield.clear();
		numbergradefield.clear();
		bestlabel.setText("");
		worstlabel.setText("");
		averagelabel.setText("");
	}
}