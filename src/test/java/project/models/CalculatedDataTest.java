package project.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculatedDataTest {
	
	CalculatedData cd;
	CourseList courselist;
	
	@BeforeEach
	public void setup() {
		courselist = new CourseList();
		courselist.addCourse(new Course("Course1", "A", ""));
		courselist.addCourse(new Course("Course2", "C", ""));
		courselist.addCourse(new Course("Course4", "", "80"));
		courselist.addCourse(new Course("Course5", "", "60"));
		courselist.addCourse(new Course("Course6", "B", "96"));
		courselist.addCourse(new Course("Course7", "", "66"));
	}
	
	@Test
	public void testConstructor() {
		courselist.setMode(1);
		cd = new CalculatedData(courselist, courselist.getMode());
		assertEquals(1, cd.getMode());
		assertEquals(courselist.getCourseNames(), cd.getCourseNames());
		assertEquals(courselist.getLetterGrades(), cd.getLetterGrades());
		assertEquals(courselist.getNumberGrades(), cd.getNumberGrades());
		
		List<String> expectedletters = new ArrayList<String>(Arrays.asList("A","C","B","D","B","D"));
		assertEquals(expectedletters, cd.getAllLetters());
		
		List<String> expectednumbers = new ArrayList<String>(Arrays.asList("95","75","80","60","96", "66"));
		assertEquals(expectednumbers, cd.getAllNumbers());
	}
	
	@Test
	public void getHighestGrade_letter() {
		courselist.setMode(1);
		cd = new CalculatedData(courselist, courselist.getMode());
		assertEquals("A", cd.getHighestGrade());
	}
	
	@Test
	public void getHighestGrade_number() {
		courselist.setMode(2);
		cd = new CalculatedData(courselist, courselist.getMode());
		assertEquals("96", cd.getHighestGrade());
	}
	
	@Test
	public void getLowestGrade_letter() {
		courselist.setMode(1);
		cd = new CalculatedData(courselist, courselist.getMode());
		assertEquals("D", cd.getLowestGrade());
	}
	
	@Test
	public void getLowestGrade_number() {
		courselist.setMode(2);
		cd = new CalculatedData(courselist, courselist.getMode());
		assertEquals("60", cd.getLowestGrade());
	}
	
	@Test
	public void getAverageGrade_letter() {
		courselist.setMode(1);
		cd = new CalculatedData(courselist, courselist.getMode());
		assertEquals("C", cd.getAverageGrade());
	}
	
	@Test
	public void getAverageGrade_number() {
		courselist.setMode(2);
		cd = new CalculatedData(courselist, courselist.getMode());
		assertEquals("78.666664", cd.getAverageGrade());
	}
	
	@Test
	public void getBestCourse_letter() {
		courselist.setMode(1);
		cd = new CalculatedData(courselist, courselist.getMode());
		assertEquals("Course1", cd.getBestCourse());
	}
	
	@Test
	public void getBestCourse_number() {
		courselist.setMode(2);
		cd = new CalculatedData(courselist, courselist.getMode());
		assertEquals("Course6", cd.getBestCourse());
	}
	
	@Test
	public void getWorstCourse_letter() {
		courselist.setMode(1);
		cd = new CalculatedData(courselist, courselist.getMode());
		assertEquals("Course5 and Course7", cd.getWorstCourse());
	}
	
	@Test
	public void getWorstCourse_number() {
		courselist.setMode(2);
		cd = new CalculatedData(courselist, courselist.getMode());
		assertEquals("Course5", cd.getWorstCourse());
	}
}
