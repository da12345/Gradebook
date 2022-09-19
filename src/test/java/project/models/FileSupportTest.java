package project.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.fxui.FileSupport;

public class FileSupportTest {
	
	private FileSupport fs;
	private CourseList courselist;
	private CalculatedData cd;
	
	@BeforeEach
	public void setup() {
		courselist = new CourseList();
		courselist.setName("MyGrades");
		courselist.addCourse(new Course("Math", "A", ""));
		courselist.addCourse(new Course("Science", "B", ""));
		courselist.addCourse(new Course("English", "", "75"));
		cd = new CalculatedData(courselist, 1);
	}
	
	@Test
	public void testWriteCourseList1() {
		fs = new FileSupport();
		File file;
		
		// makes sure the file "MyGrades" does not exist
		file = Path.of(System.getProperty("user.home"),"MyGrades.courselist").toFile();
		if (file.exists()) {
			file.delete();
		}
		
		// writes CourseList "MyGrades" to file
		try {
			fs.writeCourseList1(cd, courselist, courselist.getName());
		}
		catch (Exception e) {
			fail("Could not write courselist to file");
		}
		
		try {
			// reads "MyGrades" and creates a new CourseList based on the file
			CourseList loadedcourselist = fs.readCourseList1("MyGrades");
			
			// checks that the content of the old courselist and the loaded courselist are the same
			assertEquals(courselist.getName(),loadedcourselist.getName());
			for (Course c : loadedcourselist.getCourses()) {
				assertEquals(c.getCourseName(),	loadedcourselist.getCourses().get(loadedcourselist.getCourses().indexOf(c)).getCourseName());
				assertEquals(c.getLetterGrade(),	loadedcourselist.getCourses().get(loadedcourselist.getCourses().indexOf(c)).getLetterGrade());
				assertEquals(c.getNumberGrade(),	loadedcourselist.getCourses().get(loadedcourselist.getCourses().indexOf(c)).getNumberGrade());
			}
		} catch (Exception e) {
			fail("Could not retreive saved data");
		} finally {
			// deletes file created for test purposes from the user's folder
			file = Path.of(System.getProperty("user.home"), "MyGrades.courselist").toFile();
			file.delete();
		}
	}
	
	@Test
	public void testWriteCourseList() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		fs = new FileSupport();
		try {
			fs.writeCourseList(courselist, cd, os);
		}
		catch (Exception e) {
			fail("Could not write courselist to file");
		}
		// checks if the retreived byte array is the expected value
		assertEquals("MyGrades\nMath;Science;English;\nA;B;-;\n-;-;75;\n0", new String(os.toByteArray()));
	}
	
	@Test
	public void testReadCourseList1() {
		fs = new FileSupport();
		File file;
		
		// makes sure the file "nonexistingfile" does not exist
		file = Path.of(System.getProperty("user.home"), "nonexistingfile.courselist").toFile();
		if (file.exists()) {
			file.delete();
		}
		
		// checks if exception is thrown when "nonexistingfile" is loaded
		assertThrows(FileNotFoundException.class, () -> {
			fs.readCourseList1("nonexistingfile");
		});
		
		// makes sure the file "MyGrades" does not exist
		file = Path.of(System.getProperty("user.home"), "MyGrades.courselist").toFile();
		if (file.exists()) {
			file.delete();
		}
		
		// writes CourseList "MyGrades" to file
		try {
			fs.writeCourseList1(cd, courselist, courselist.getName());
		}
		catch (Exception e) {
			fail("Could not write courselist");
		}
		try {
			// reads "MyGrades" and creates a new CourseList based on the file
			CourseList loadedcourselist = fs.readCourseList1("MyGrades");
			
			// checks that the content of the old courselist and the loaded courselist are the same
			assertEquals(courselist.getName(),loadedcourselist.getName());
			for (Course c : loadedcourselist.getCourses()) {
				assertEquals(c.getCourseName(),	loadedcourselist.getCourses().get(loadedcourselist.getCourses().indexOf(c)).getCourseName());
				assertEquals(c.getLetterGrade(),	loadedcourselist.getCourses().get(loadedcourselist.getCourses().indexOf(c)).getLetterGrade());
				assertEquals(c.getNumberGrade(),	loadedcourselist.getCourses().get(loadedcourselist.getCourses().indexOf(c)).getNumberGrade());
			}
		} catch (Exception e) {
			fail("Could not retreive saved data");
		} finally {
			// deletes file created for test purposes from the user's folder
			file = Path.of(System.getProperty("user.home"), "MyGrades.courselist").toFile();
			file.delete();
		}
	}

	@Test
	public void testReadCourseList() throws UnsupportedEncodingException {
		InputStream is = new ByteArrayInputStream("MyGrades\nMath;Science;English;\nA;B;-;\n-;-;75;\n0".getBytes("UTF-8"));
		fs = new FileSupport();
		try {
			// creates a courselist based on data from is, and the original courselist
			CourseList actual = fs.readCourseList(is);
			CourseList expected = this.courselist;
			
			// checks that the names are the same
			assertEquals(expected.getName(), actual.getName());
			
			var acIterator = actual.iterator();
			var exIterator = expected.iterator();
			
			while (exIterator.hasNext()) {
				try {
					// checks that the contents of the original and the new courselist are the same
					assertEquals(exIterator.next().getCourseName(), acIterator.next().getCourseName());
					assertEquals(exIterator.next().getLetterGrade(), acIterator.next().getLetterGrade());
					assertEquals(exIterator.next().getNumberGrade(), acIterator.next().getNumberGrade());
				}
				catch (IndexOutOfBoundsException e) {
					// will fail if one of the lists are longer than the other
					fail("Wrong number of elements!");
				}
			}
		}
		catch (Exception e) {
			fail("Could not read from file");
		}
	}
}
