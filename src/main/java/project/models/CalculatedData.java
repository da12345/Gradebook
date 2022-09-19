package project.models;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalculatedData {
	
	private int mode;
	
	private List<String> coursenames;
	private List<String> letterswithnull;
	private List<String> numberswithnull;
	
	private List<String> allnumbers = new ArrayList<String>();
	private List<String> allletters = new ArrayList<String>();
	
	public CalculatedData(CourseList courselist, int mode) {
		this.mode = mode;
		coursenames = new ArrayList<String>(courselist.getCourseNames());
		letterswithnull = new ArrayList<String>(courselist.getLetterGrades());
		numberswithnull = new ArrayList<String>(courselist.getNumberGrades());
		allToAlpha();
		allToNumeric();
	}
	
	public String getHighestGrade() {
		if (mode == 1) {
			return getHighestLetter().toString();
		}
		else if (mode == 2) {
			return getHighestNumber().toString();
		}
		return null;
	}
	
	public String getLowestGrade() {
		if (mode == 1) {
			return getLowestLetter().toString();
		}
		else if (mode == 2) {
			return getLowestNumber().toString();
		}
		return null;
	}
	
	public String getAverageGrade() {
		if (mode == 1) {
			return getAverageLetter().toString();
		}
		else if (mode == 2) {
			return getAverageNumber().toString();
		}
		return null;
	}
	
	public String getBestCourse() {
		if (mode == 1) {
			int occ = Collections.frequency(allletters, getHighestLetter());
			List<String> matchingCourses = new ArrayList<>();
			if (occ == 1) {
				int index = allletters.indexOf(getHighestLetter());
				return coursenames.get(index);
			}
			else {
				List<Integer> matchingIndices = new ArrayList<>();
				String matchingCoursesString = "";
				for (int i = 0; i < allletters.size(); i++) {
				    String element = allletters.get(i);
				    if (getHighestLetter().equals(element)) {
				        matchingIndices.add(i);
				    }
				}
				for (Integer i : matchingIndices) {
					matchingCourses.add(coursenames.get(i));
				}
				for (int i = 0; i < matchingCourses.size(); i++) {
					if (matchingCoursesString == "") {
						matchingCoursesString = matchingCourses.get(i);
					}
					else {
						matchingCoursesString = matchingCoursesString + " and " + matchingCourses.get(i); 
					}
				}
				return matchingCoursesString;
			}	
		}
		else if (mode == 2) {
			int occ = Collections.frequency(allnumbers, getHighestNumber());
			List<String> matchingCourses = new ArrayList<>();
			if (occ == 1) {
				int index = allnumbers.indexOf(getHighestNumber());
				String string = coursenames.get(index);
				return string;
			}
			else {
				List<Integer> matchingIndices = new ArrayList<>();
				String matchingCoursesString = "";
				for (int i = 0; i < allnumbers.size(); i++) {
				    String element = allnumbers.get(i);
				    if (getHighestNumber().equals(element)) {
				        matchingIndices.add(i);
				    }
				}
				for (Integer i : matchingIndices) {
					matchingCourses.add(coursenames.get(i));
				}
				for (int i = 0; i < matchingCourses.size(); i++) {
					if (matchingCoursesString == "") {
						matchingCoursesString = matchingCourses.get(i);
					}
					else {
						matchingCoursesString = matchingCoursesString + " and " + matchingCourses.get(i); 
					}
				}
				return matchingCoursesString;
			}
		}
		return null;
	}
	
	public ArrayList<String> getCourses() {
		return new ArrayList<String>(coursenames);
	}
	
	public String getWorstCourse() {		
		if (mode == 1) {
			int occ = Collections.frequency(allletters, getLowestLetter());
			List<String> matchingCourses = new ArrayList<>();
			if (occ == 1) {
				int index = allletters.indexOf(getLowestLetter());
				String string = coursenames.get(index);   
				return string;
			}
			else {
				List<Integer> matchingIndices = new ArrayList<>();
				String matchingCoursesString = "";
				
				
				for (int i = 0; i < allletters.size(); i++) {
					if (getLowestLetter().equals(allletters.get(i))) {
				        matchingIndices.add(i);		
					}
				}
				for (Integer i : matchingIndices) {
					matchingCourses.add(coursenames.get(i));
				}
				for (String course : matchingCourses) {
					if (matchingCoursesString == "") {
						matchingCoursesString = course;
					}
					else {
						matchingCoursesString = matchingCoursesString + " and " + course; 
					}
				}
				return matchingCoursesString;
			}
		}
		else if (mode == 2) {
			int occ = Collections.frequency(allnumbers, getLowestNumber());
			List<String> matchingCourses = new ArrayList<>();
			if (occ == 1) {
				int index = allnumbers.indexOf(getLowestNumber());
				String string = coursenames.get(index);
				return string;
			}
			else {
				List<Integer> matchingIndices = new ArrayList<>();
				String matchingCoursesString = "";

				for (int i = 0; i < allnumbers.size(); i++) {
					if (getLowestNumber().equals(allnumbers.get(i))) {
				        matchingIndices.add(i);		
					}
				}
				for (Integer i : matchingIndices) {
					matchingCourses.add(coursenames.get(i));
				}
				for (String course : matchingCourses) {
					if (matchingCoursesString == "") {
						matchingCoursesString = course;
					}
					else {
						matchingCoursesString = matchingCoursesString + " and " + course; 
					}
				}
				return matchingCoursesString;
				}
		}
		return null;	
	}
	
	public int getMode() {
		return mode;
	}
	
	private List<String> allToAlpha() {
		for (int i = 0; i < letterswithnull.size(); i++) {
			if (letterswithnull.get(i) == "-") {
				String ch = numberToLetter(numberswithnull.get(i));
				allletters.add(ch);
			}
			else {
				allletters.add(letterswithnull.get(i));
			}
		}
		return allletters;
	}
	
	private List<String> allToNumeric() {
		for (int i = 0; i < numberswithnull.size(); i++) {
			if (numberswithnull.get(i) == "-") {
				String in = letterToNumber(letterswithnull.get(i));
				allnumbers.add(in);
			}
			else {
				allnumbers.add(numberswithnull.get(i));
			}
		}
		return allnumbers;
	}
	
	private String letterToNumber(String string) {
		String i;
		switch (string) {
			case "A" -> i = "95";
			case "B" -> i = "85";
			case "C" -> i = "75";
			case "D" -> i = "65";
			case "E" -> i = "55";
			case "F" -> i = "45";
			default -> i = null;
		}
		return i;
	}
	
	private String numberToLetter(String string) {
		int i = Integer.parseInt(string);
		if (i >= 90) {
			return "A";
		}
		else if (i >= 80) {
			return "B";
		}
		else if (i >= 70) {
			return "C";
		}
		else if (i >= 60) {
			return "D";
		}
		else if (i >= 50) {
			return "E";
		}
		else {
			return "F";
		}
	}
	
	private String getHighestNumber() {
		if (allnumbers.size() != 0) {
			return Collections.max(allnumbers);
		}
		return null;
	}
	
	private String getLowestNumber() {
		if (allnumbers.size() != 0) {
			return Collections.min(allnumbers);
		}
		return null;
	}
	
	private String getHighestLetter() {
		if (allletters.size() != 0) {
			return Collections.min(allletters);
		}
		return null;
	}
	
	private String getLowestLetter() {
		if (allletters.size() != 0) {
			return Collections.max(allletters);
		}
		return null;
	}
	
	private String getAverageLetter() {
		if (allletters.size() != 0) {
			Integer gp = 0;
			for (String letter : allletters) {
				switch (letter) {
					case "A" -> gp += 5;
					case "B" -> gp += 4;
					case "C" -> gp += 3;
					case "D" -> gp += 2;
					case "E" -> gp += 1;
				}
			}
			float gpa = gp/allletters.size();
			if (gpa > 4.0) {
				return "A";
			}
			else if (gpa >= 3.5) {
				return "B";
			}
			else if (gpa >= 2.5) {
				return "C";
			}
			else if (gpa >= 1.5) {
				return "D";
			}
			else if (gpa >= 0.5) {
				return "E";
			}
			else {
				return "F";
			}
		}
		return null;
	}
	
	private Float getAverageNumber() {
		float total = 0;
		if (allnumbers.size() != 0) {
			for (String i : allnumbers) {
				Integer in = Integer.parseInt(i);
				total += in;
			}
			float fl = total/allnumbers.size();
			DecimalFormat df = new DecimalFormat("##.#");
			df.format(fl);
			return fl;
		}
		return null;
	}
	
	public List<String> getCourseNames() {
		return new ArrayList<String>(coursenames);
	}
	public List<String> getLetterGrades() {
		return new ArrayList<String>(letterswithnull);
	}
	public List<String> getNumberGrades() {
		return new ArrayList<String>(numberswithnull);
	}
	public List<String> getAllLetters() {
		return new ArrayList<String>(allletters);
	}
	public List<String> getAllNumbers() {
		return new ArrayList<String>(allnumbers);
	}
}