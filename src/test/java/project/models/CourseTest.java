package project.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseTest {
	
	private Course course;

	@BeforeEach
	public void setup() {
		course = new Course("english","a","100");
	}
	
	@Test
	public void testConstructor() {
		assertEquals("English", course.getCourseName());
		assertEquals("A", course.getLetterGrade());
		assertEquals("100", course.getNumberGrade());
		// tests the private method checkIfAtLeastOneGrade
		course = new Course("English", "a", "-");
		assertThrows(IllegalArgumentException.class, () -> {
			course.setLetterGrade("-");
		});
	}
	
	@Test
	public void testSetCourseName() {
		course.setCourseName("Math");
		assertEquals("Math", course.getCourseName());
	}
	
	@Test
	public void testSetLetterGrade() {
		course.setLetterGrade("A");
		assertEquals("A", course.getLetterGrade());
		course.setNumberGrade("-");
		// checks if setting letter grade so both are empty throws an exception
		assertThrows(IllegalArgumentException.class, () -> {
			course.setLetterGrade("-");
		});		
	}
	
	@Test
	public void testSetNumberGrade() {
		course.setNumberGrade("100");
		assertEquals("100", course.getNumberGrade());
		course.setLetterGrade("-");
		// checks if setting number grade so both are empty throws an exception
		assertThrows(IllegalArgumentException.class, () -> {
			course.setNumberGrade("-");
		});
	}
	
	@Test
	public void testValidateCourseName_number() {
		assertThrows(IllegalArgumentException.class, () -> {
			course.setCourseName("123");
		});
	}
	
	@Test
	public void testValidateCourseName_oneChar() {
		assertThrows(IllegalArgumentException.class, () -> {
			course.setCourseName("a");
		});
	}
	
	@Test
	public void testValidateCourseName_emptyString() {
		assertThrows(IllegalArgumentException.class, () -> {
			course.setCourseName("");
		});
	}
	
	@Test
	public void testValidateCourseName_doesCapitalize() {
		course.setCourseName("science");
		assertEquals("Science", course.getCourseName());
	}
	
	@Test
	public void testValidateCourseName_doesNotCapitalize() {
		course.setCourseName("math");
		assertEquals("math", course.getCourseName());
	}

	
	
	@Test
	public void testValidateLetterGrade_emptyString() {
		course.setLetterGrade("");
		assertEquals("-", course.getLetterGrade());
	}
	
	@Test
	public void testValidateLetterGrade_number() {
		assertThrows(IllegalArgumentException.class, () -> {
			course.setLetterGrade("1");
		});
	}
	
	@Test
	public void testValidateLetterGrade_moreThanOneLetter() {
		assertThrows(IllegalArgumentException.class, () -> {
			course.setLetterGrade("AB");
		});
	}
	
	@Test
	public void testValidateLetterGrade_doesCapitalize() {
		course.setLetterGrade("a");
		assertEquals("A", course.getLetterGrade());
	}
	
	@Test
	public void testValidateNumberGrade_negativeNumber() {
		assertThrows(NumberFormatException.class, () -> {
			course.setNumberGrade("-1");
		});
	}
	
	@Test
	public void testValidateNumberGrade_overOneHundred() {
		assertThrows(NumberFormatException.class, () -> {
			course.setNumberGrade("101");
		});
	}
	
	@Test
	public void testValidateNumberGrade_letter() {
		assertThrows(NumberFormatException.class, () -> {
			course.setNumberGrade("A");
		});
	}
}
