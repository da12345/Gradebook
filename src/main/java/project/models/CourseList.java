package project.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.scene.control.RadioButton;

public class CourseList implements Iterable<Course> {
	
	private String name;
	private CalculatedData cd;
	
	private List<Course> courses = new ArrayList<Course>();
	
	private List<String> coursenames = new ArrayList<String>();
	private List<String> courselettergrades = new ArrayList<String>();
	private List<String> coursenumbergrades = new ArrayList<String>();
	
	private int mode;
	
	public void addCourse(Course course) {	
		if (!coursenames.contains(course.getCourseName())) {
			courses.add(course);
			coursenames.add(course.getCourseName());
			courselettergrades.add(course.getLetterGrade());
			coursenumbergrades.add(course.getNumberGrade());
		}
		else {
			throw new IllegalStateException();
		}
	}
	
	public void removeCourse(int i) {
		courses.remove(i);
		coursenames.remove(i);
		courselettergrades.remove(i);
		coursenumbergrades.remove(i);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setMode(RadioButton r) {
		if (r.getText().equals("Letter Grade")) {
			mode = 1;
		}
		else if (r.getText().equals("Number Grade")) {
			mode = 2;
		}
	}
	
	public void setMode(Integer i) {
		mode = i;
	}
	
	public int getMode() {
		return mode;
	}
	
	public Course getCourse(int i) {
		return courses.get(i);
	}
	
	public List<Course> getCourses() {
		return new ArrayList<Course>(courses);
	}
	
	public List<String> getCourseNames() {
		return new ArrayList<String>(coursenames);
	}
	
	public List<String> getLetterGrades() {
		return new ArrayList<String>(courselettergrades);
	}
	
	public List<String> getNumberGrades() {
		return new ArrayList<String>(coursenumbergrades);
	}
	
	public CalculatedData createCalculatedData() {
		cd = new CalculatedData(this, this.getMode());
		setCalculatedData(cd);
		return cd;
	}
	
	public void setCalculatedData(CalculatedData cd) {
		this.cd = cd;
	}
	
	public CalculatedData getCalculatedData() {
		return cd;
	}
	
	public void removeAll() {
		courses.clear();
		coursenames.clear();
		courselettergrades.clear();
		coursenumbergrades.clear();
	}
	
	public int getLength() {
		return courses.size();
	}
	
	public boolean listIsFull() {
		return getLength() >= 10;
	}
	
	public boolean containsNull() {
		return (getMode() == 1 && this.courselettergrades.contains("-") || (getMode() == 2 && this.coursenumbergrades.contains("-")));
	}
	
	//set to package to help test method testIterator();
	void setCourses(List<Course> courses) {
		this.removeAll();
		for (Course c : courses) {
			this.addCourse(c);
		}
	}

	private void setIsSorted(char c) {
		this.isSorted = c;
	}
	
	@Override
	public Iterator<Course> iterator() {
		return courses.iterator();
	}
	
	List<Course> sorteddata;
	private char isSorted;
	
	public CourseList getSortedByCourseName() {
		
		CourseList courselist;
		List<Course> coursessort;
		
		if (isSorted != 'c') {
			sorteddata = new ArrayList<Course>();
			coursessort = new ArrayList<Course>(this.getCourses());
			Collections.sort(coursessort, new SortCourseNames());
			for (Course c : coursessort) {
				sorteddata.add(c);
			}
			courselist = new CourseList();
			courselist.setCourses(coursessort);
			courselist.setIsSorted('c');
			return courselist;
		}
		else if (isSorted == 'c') {
			sorteddata = new ArrayList<Course>();
			coursessort = new ArrayList<Course>(this.getCourses());

			Collections.reverse(coursessort);
			for (Course c : coursessort) {
				sorteddata.add(c);
			}

			courselist = new CourseList();
			courselist.setCourses(sorteddata);
			return courselist;
		}
		return null;
	}
	
	private class SortCourseNames implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) {
			Course c1 = (Course) o1;
			Course c2 = (Course) o2;
			String s1 = c1.getCourseName();
			String s2 = c2.getCourseName();
            return s1.toLowerCase().compareTo(s2.toLowerCase());
		}
    }
	
	public CourseList getSortedByLetter() {
		
		CourseList courselist;
		List<Course> coursessort;
		
		if (isSorted != 'l') {
			sorteddata = new ArrayList<Course>();
			coursessort = new ArrayList<Course>(this.getCourses());
			Collections.sort(coursessort, new SortLetters());
			for (Course c : coursessort) {
				sorteddata.add(c);
			}
			
			courselist = new CourseList();
			courselist.setCourses(coursessort);
			courselist.setIsSorted('l');
			return courselist;
		}
		else if (isSorted == 'l') {
			sorteddata = new ArrayList<Course>();
			coursessort = new ArrayList<Course>(this.getCourses());
			Collections.reverse(coursessort);
			for (Course c : coursessort) {
				sorteddata.add(c);
			}
			
			courselist = new CourseList();
			courselist.setCourses(sorteddata);
			return courselist;
		}
		return null;
	}
	
	public class SortLetters implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) {
			Course c1 = (Course) o1;
			Course c2 = (Course) o2;
			String s1 = c1.getLetterGrade();
			String s2 = c2.getLetterGrade();
			if (s1.equals("-")) {
				return 1;
			}
			if (s2.equals("-")) {
				return -1;
			}
            return s1.compareTo(s2);
		}
    }
	
	public CourseList getSortedByNumber() {
		
		CourseList courselist;
		List<Course> coursessort;
		
		if (isSorted != 'n') {
			sorteddata = new ArrayList<Course>();
			coursessort = new ArrayList<Course>(this.getCourses());
			Collections.sort(coursessort, new SortNumbers());
			for (Course c : coursessort) {
				sorteddata.add(c);
			}
			
			courselist = new CourseList();
			courselist.setCourses(coursessort);
			courselist.setIsSorted('n');
			return courselist;
		}
		else if (isSorted == 'n') {
			sorteddata = new ArrayList<Course>();
			coursessort = new ArrayList<Course>(this.getCourses());
			Collections.reverse(coursessort);
			for (Course c : coursessort) {
				sorteddata.add(c);
			}
			
			courselist = new CourseList();
			courselist.setCourses(sorteddata);
			return courselist;
		}
		return null;
	}
	
	public class SortNumbers implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) {
			Course c1 = (Course) o1;
			Course c2 = (Course) o2;
			String s1 = c1.getNumberGrade();
			String s2 = c2.getNumberGrade();
			if (s1.equals("-")) {
				return 1;
			}
			if (s2.equals("-")) {
				return -1;
			}
            return s2.compareTo(s1);
		}
    }
}