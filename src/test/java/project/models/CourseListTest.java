package project.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseListTest {
	
	private CourseList courselist;
	private Course course;
	private Course course2;
	private Course course3;
	private Course course4;
	private Course course5;
	private Course course6;
	
	@BeforeEach
	public void setup() {
		courselist = new CourseList();
		course = new Course("Math", "A", "90");
		course2 = new Course("Science", "C", "60");
		course3 = new Course("English", "B", "80");
		course4 = new Course("Math", "A", "90");
		course5 = new Course("Spanish", "-", "90");
		course6 = new Course("History", "A", "-");
	}
	
	@Test
	public void testAddCourse() {
		assertFalse(courselist.getCourses().contains(course));
		assertFalse(courselist.getCourseNames().contains(course.getCourseName()));
		courselist.addCourse(course);
		assertTrue(courselist.getCourses().contains(course));
		assertTrue(courselist.getCourseNames().contains(course.getCourseName()));
		assertTrue(courselist.getLetterGrades().contains(course.getLetterGrade()));
		assertTrue(courselist.getNumberGrades().contains(course.getNumberGrade()));
		// checks if adding a course with the same name as existing one throws an exception
		assertThrows(IllegalStateException.class, () -> {
			courselist.addCourse(course4);
		});
	}
	
	@Test
	public void testRemoveCourse() {
		courselist.addCourse(course);
		assertTrue(courselist.getCourses().contains(course));
		// tests removing a course with out of bounds index
		assertThrows(IndexOutOfBoundsException.class, () -> {
			courselist.removeCourse(-1);
		});
		// tests removing a course with out of bounds index
		assertThrows(IndexOutOfBoundsException.class, () -> {
			courselist.removeCourse(1);
		});
		courselist.removeCourse(0);
		// makes sure that the course was removed from both courses and coursenames
		assertFalse(courselist.getCourses().contains(course));
		assertFalse(courselist.getCourseNames().contains(course.getCourseName()));
	}
	
	@Test
	public void testSetName() {
		courselist.setName("Daniel");
		assertEquals("Daniel", courselist.getName());
	}
	
	@Test
	public void testSetMode() {
		assertEquals(0, courselist.getMode());
		courselist.setMode(1);
		assertEquals(1, courselist.getMode());
	}
	
	@Test
	public void testCreateCalculatedData() {
		CalculatedData cd = courselist.createCalculatedData();
		assertEquals(cd, courselist.getCalculatedData());
	}

	@Test
	public void removeAll() {
		courselist.addCourse(course);
		courselist.addCourse(course3);
		courselist.removeAll();
		assertTrue(courselist.getCourses().isEmpty());
		assertTrue(courselist.getCourseNames().isEmpty());
		assertTrue(courselist.getLetterGrades().isEmpty());
		assertTrue(courselist.getNumberGrades().isEmpty());
	}
	
	@Test
	public void testGetLength() {
		courselist.addCourse(course);
		courselist.addCourse(course3);
		assertEquals(2, courselist.getLength());
	}
	
	@Test
	public void testListIsFull() {
		for (int i = 0; i < 10; i++) {
			assertFalse(courselist.listIsFull());
			courselist.addCourse(new Course("course" + i, "A", "90"));
		}
		assertTrue(courselist.listIsFull());
	}
	
	@Test
	public void testContainsNull_letterGradeMode() {
		courselist.setMode(1);
		courselist.addCourse(new Course("Science", "", "100"));
		assertTrue(courselist.containsNull());
	}
	
	@Test
	public void testContainsNull_numberGradeMode() {
		courselist.setMode(2);
		courselist.addCourse(new Course("Science", "A", ""));
		assertTrue(courselist.containsNull());
	}
	
	@Test
	public void testIterator() {
		Iterator<Course> it = courselist.iterator();
		List<Course> courses = new ArrayList<Course>();
		courselist.setCourses(courses);
		assertFalse(it.hasNext());
		assertThrows(Exception.class, () -> {
			it.next();
		});
		courses.add(course);
		courselist.setCourses(courses);
		assertTrue(it.hasNext());
	}
	
	@Test
	public void testGetSortedByCourseName() {
		courselist.addCourse(course);
		courselist.addCourse(course2);
		courselist.addCourse(course3);
		
		CourseList sortedlist = courselist.getSortedByCourseName();
		assertEquals("English", sortedlist.getCourse(0).getCourseName());
		assertEquals("Math", sortedlist.getCourse(1).getCourseName());
		assertEquals("Science", sortedlist.getCourse(2).getCourseName());
		
		sortedlist.getSortedByCourseName();
		assertEquals("Science", sortedlist.getCourse(2).getCourseName());
		assertEquals("Math", sortedlist.getCourse(1).getCourseName());
		assertEquals("English", sortedlist.getCourse(0).getCourseName());
		
		sortedlist.getSortedByCourseName();
		assertEquals("English", sortedlist.getCourse(0).getCourseName());
		assertEquals("Math", sortedlist.getCourse(1).getCourseName());
		assertEquals("Science", sortedlist.getCourse(2).getCourseName());
	}
	
	@Test
	public void testGetSortedByLetter() {
		courselist.addCourse(course2);
		courselist.addCourse(course3);
		courselist.addCourse(course5);
		courselist.addCourse(course6);
		
		CourseList sortedlist = courselist.getSortedByLetter();
		assertEquals("History", sortedlist.getCourse(0).getCourseName());
		assertEquals("English", sortedlist.getCourse(1).getCourseName());
		assertEquals("Science", sortedlist.getCourse(2).getCourseName());
		assertEquals("Spanish", sortedlist.getCourse(3).getCourseName());
		
		assertEquals("A", sortedlist.getCourse(0).getLetterGrade());
		assertEquals("B", sortedlist.getCourse(1).getLetterGrade());
		assertEquals("C", sortedlist.getCourse(2).getLetterGrade());
		assertEquals("-", sortedlist.getCourse(3).getLetterGrade());
		
		assertEquals("-", sortedlist.getCourse(0).getNumberGrade());
		assertEquals("80", sortedlist.getCourse(1).getNumberGrade());
		assertEquals("60", sortedlist.getCourse(2).getNumberGrade());
		assertEquals("90", sortedlist.getCourse(3).getNumberGrade());
		
		sortedlist = sortedlist.getSortedByLetter();
		assertEquals("Spanish", sortedlist.getCourse(0).getCourseName());
		assertEquals("Science", sortedlist.getCourse(1).getCourseName());
		assertEquals("English", sortedlist.getCourse(2).getCourseName());
		assertEquals("History", sortedlist.getCourse(3).getCourseName());
		
		assertEquals("-", sortedlist.getCourse(0).getLetterGrade());
		assertEquals("C", sortedlist.getCourse(1).getLetterGrade());
		assertEquals("B", sortedlist.getCourse(2).getLetterGrade());
		assertEquals("A", sortedlist.getCourse(3).getLetterGrade());;
		
		assertEquals("90", sortedlist.getCourse(0).getNumberGrade());
		assertEquals("60", sortedlist.getCourse(1).getNumberGrade());
		assertEquals("80", sortedlist.getCourse(2).getNumberGrade());
		assertEquals("-", sortedlist.getCourse(3).getNumberGrade());
		
		sortedlist = sortedlist.getSortedByLetter();
		assertEquals("History", sortedlist.getCourse(0).getCourseName());
		assertEquals("English", sortedlist.getCourse(1).getCourseName());
		assertEquals("Science", sortedlist.getCourse(2).getCourseName());
		assertEquals("Spanish", sortedlist.getCourse(3).getCourseName());
		
		assertEquals("A", sortedlist.getCourse(0).getLetterGrade());
		assertEquals("B", sortedlist.getCourse(1).getLetterGrade());
		assertEquals("C", sortedlist.getCourse(2).getLetterGrade());
		assertEquals("-", sortedlist.getCourse(3).getLetterGrade());
		
		assertEquals("-", sortedlist.getCourse(0).getNumberGrade());
		assertEquals("80", sortedlist.getCourse(1).getNumberGrade());
		assertEquals("60", sortedlist.getCourse(2).getNumberGrade());
		assertEquals("90", sortedlist.getCourse(3).getNumberGrade());
	}
	
	@Test
	public void testGetSortedByNumber() {
		courselist.addCourse(course2);
		courselist.addCourse(course3);
		courselist.addCourse(course5);
		courselist.addCourse(course6);
		
		CourseList sortedlist = courselist.getSortedByNumber();
		assertEquals("Spanish", sortedlist.getCourse(0).getCourseName());
		assertEquals("English", sortedlist.getCourse(1).getCourseName());
		assertEquals("Science", sortedlist.getCourse(2).getCourseName());
		assertEquals("History", sortedlist.getCourse(3).getCourseName());
		
		assertEquals("-", sortedlist.getCourse(0).getLetterGrade());
		assertEquals("B", sortedlist.getCourse(1).getLetterGrade());
		assertEquals("C", sortedlist.getCourse(2).getLetterGrade());
		assertEquals("A", sortedlist.getCourse(3).getLetterGrade());
		
		assertEquals("90", sortedlist.getCourse(0).getNumberGrade());
		assertEquals("80", sortedlist.getCourse(1).getNumberGrade());
		assertEquals("60", sortedlist.getCourse(2).getNumberGrade());
		assertEquals("-", sortedlist.getCourse(3).getNumberGrade());
		
		sortedlist = sortedlist.getSortedByNumber();
		assertEquals("History", sortedlist.getCourse(0).getCourseName());
		assertEquals("Science", sortedlist.getCourse(1).getCourseName());
		assertEquals("English", sortedlist.getCourse(2).getCourseName());
		assertEquals("Spanish", sortedlist.getCourse(3).getCourseName());
		
		assertEquals("A", sortedlist.getCourse(0).getLetterGrade());
		assertEquals("C", sortedlist.getCourse(1).getLetterGrade());
		assertEquals("B", sortedlist.getCourse(2).getLetterGrade());
		assertEquals("-", sortedlist.getCourse(3).getLetterGrade());;
		
		assertEquals("-", sortedlist.getCourse(0).getNumberGrade());
		assertEquals("60", sortedlist.getCourse(1).getNumberGrade());
		assertEquals("80", sortedlist.getCourse(2).getNumberGrade());
		assertEquals("90", sortedlist.getCourse(3).getNumberGrade());
		
		sortedlist = sortedlist.getSortedByNumber();
		assertEquals("Spanish", sortedlist.getCourse(0).getCourseName());
		assertEquals("English", sortedlist.getCourse(1).getCourseName());
		assertEquals("Science", sortedlist.getCourse(2).getCourseName());
		assertEquals("History", sortedlist.getCourse(3).getCourseName());
		
		assertEquals("-", sortedlist.getCourse(0).getLetterGrade());
		assertEquals("B", sortedlist.getCourse(1).getLetterGrade());
		assertEquals("C", sortedlist.getCourse(2).getLetterGrade());
		assertEquals("A", sortedlist.getCourse(3).getLetterGrade());
		
		assertEquals("90", sortedlist.getCourse(0).getNumberGrade());
		assertEquals("80", sortedlist.getCourse(1).getNumberGrade());
		assertEquals("60", sortedlist.getCourse(2).getNumberGrade());
		assertEquals("-", sortedlist.getCourse(3).getNumberGrade());
	}
}
