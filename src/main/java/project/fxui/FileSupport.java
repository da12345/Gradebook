package project.fxui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javafx.stage.Modality;
import project.models.CalculatedData;
import project.models.Course;
import project.models.CourseList;


public class FileSupport implements IFileReading {
	
    private static final String COURSELIST_EXTENSION = "courselist";
    
	@Override
    public void writeCourseList1(CalculatedData cd, CourseList courselist, String name) throws Exception {
        var courseListPath = getCourseListPath(name);
        findFolderPath();
        
        File file = courseListPath.toFile();
        
        if (file.exists()) {
        		String simplename = file.getName().split("\\.", 2)[0];
				AlertWindowController a = new AlertWindowController("yesno", (simplename + " already exists! Do you want to overwrite the file?"));
				a.window.initModality(Modality.APPLICATION_MODAL);
				a.setMessageProperties();
				a.layout.getChildren().addAll(a.label,a.yes,a.no);
				a.no.setOnAction(e -> a.window.close());
				a.yes.setOnAction(e -> {
					a.window.close();
			        FileOutputStream output;
					try {
						output = new FileOutputStream(file);
				        writeCourseList(courselist, cd, output);
					} 
					catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} 
					catch (Exception e1) {
						e1.printStackTrace();
					}
				});
				a.window.showAndWait();
        }
        else {
            try (var output = new FileOutputStream(file)) {
                writeCourseList(courselist, cd, output);
            }
            catch (IOException e) {
            	e.printStackTrace();
            }
            catch (Exception e) {
            	e.printStackTrace();
            }
        }
    }

	@Override
	public void writeCourseList(CourseList courselist, CalculatedData cd, OutputStream output) throws Exception {
			try (var writer = new PrintWriter(output)) {
	        	writer.println(courselist.getName());
	        	
	        	for (String s : courselist.getCourseNames()) {
	        		writer.print(s + ";");
	        	}
	        	
	        	writer.println();
	        	
	        	for (String s : courselist.getLetterGrades())
	        		writer.print(s + ";");
	        	
	        	writer.println();
	        	
	        	for (String s : courselist.getNumberGrades()) {
	        		writer.print(s + ";");
	        	}
	        	
	        	writer.println();
	        	writer.print(courselist.getMode());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	@Override
	public CourseList readCourseList(InputStream is) throws IOException, Exception {
        CourseList courselist = null;
        List<String> courses = null;
        List<String> lettergrades = null;
        List<String> numbergrades = null;
        @SuppressWarnings("resource")
		var scanner = new Scanner(is);
        courselist = new CourseList();
        while (scanner.hasNextLine()) {
        	var line = scanner.nextLine().stripTrailing();
        	if (line.isEmpty()) {
                continue;
            }
            if (courselist.getName() == null) {
                courselist.setName(line);
            }
            else if (courses == null) {
            	courses = Arrays.asList(line.split(";"));
            }
            else if (lettergrades == null) {
            	lettergrades = Arrays.asList(line.split(";"));
            }
            else if (numbergrades == null) {
            	numbergrades = Arrays.asList(line.split(";"));
            }
            else if (courselist.getMode() == 0) {
            	courselist.setMode(Integer.parseInt(line));
            }
        }
        for (int i = 0; i < courses.size(); i++) {
        	if (!lettergrades.get(i).matches("[a-fA-F]")) {
            	courselist.addCourse(new Course(courses.get(i), "", numbergrades.get(i)));
           	}
           	else if (!numbergrades.get(i).matches("[a-fA-F]")) {
               	courselist.addCourse(new Course(courses.get(i), lettergrades.get(i), ""));           		
           	}
           	else {
               	courselist.addCourse(new Course(courses.get(i), lettergrades.get(i), numbergrades.get(i)));
           	}
        }
        return courselist;
	}

	public CourseList readCourseList1(String name) throws IOException, Exception {
        var path = getCourseListPath(name);
        var input = new FileInputStream(path.toFile());
        return readCourseList(input);
    }
    
    private Path getCourseListPath(String name) {
        return getCourseListUserFolderPath().resolve(name + "." + COURSELIST_EXTENSION);
    }

    private boolean findFolderPath() {
        try {
            Files.createDirectories(getCourseListUserFolderPath());
            return true;
        } 
        catch (IOException ioe) {
            return false;
        }
    }
    
    private Path getCourseListUserFolderPath() {
        return Path.of(System.getProperty("user.home"));
    }
}
