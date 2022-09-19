package project.models;

public class Course {
	private String coursename;
	private String numbergrade = null;
	private String lettergrade = null;

	public Course(String coursename, String lettergrade, String numbergrade) {
		setCourseName(coursename);
		setLetterGrade(lettergrade);
		setNumberGrade(numbergrade);
		checkIfAtLeastOneGrade();
	}
		
	public String getCourseName() {
		return coursename;
	} 
	
	public String getNumberGrade() {
		return numbergrade;
	}
	
	public String getLetterGrade() {
		return lettergrade;
	}
	
	void setCourseName(String coursename) {
		this.coursename = validateCourseName(coursename);
	}
	
	void setLetterGrade(String lettergrade) {
		this.lettergrade = validateLetterGrade(lettergrade);
		checkIfAtLeastOneGrade();
	}
	
	void setNumberGrade(String numbergrade) {
		this.numbergrade = validateNumberGrade(numbergrade);
		checkIfAtLeastOneGrade();
	}
	
	private String validateCourseName(String coursename) {
		if (coursename.length() > 1 && coursename.matches(".*[a-zA-Z]+.*")) {
			if (coursename.length() > 4) {
				if (!coursename.matches("\\S+")) {
					String [] arr = coursename.split(" ");
					if (arr[1].length() == 1 && arr.length == 2) {
						String mainword = arr[0].substring(0, 1).toUpperCase() + arr[0].substring(1);
						String letter = arr[1].toUpperCase();
						return mainword + " " + letter;
					}
				}
				return coursename.substring(0, 1).toUpperCase() + coursename.substring(1);
			}
			return coursename;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	private String validateNumberGrade(String numbergrade) {
		if (numbergrade == "-") {
			return numbergrade;
		}
		
		if (numbergrade != "") {
			int numbergradeint = Integer.parseInt(numbergrade);
			if (numbergrade.matches("[0-9]+") && numbergradeint <= 100 && numbergradeint >= 0) {
					return numbergrade;
			}
			else {
				throw new NumberFormatException();
			}
		}
		return "-";
	}
	
	private String validateLetterGrade(String lettergrade) {
		if (lettergrade == "" || lettergrade == "-") {
			return "-";
		}
		else if (lettergrade.length() == 1 && lettergrade.matches("[a-fA-F]")) {
			return String.valueOf(Character.toUpperCase(lettergrade.charAt(0)));
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	private void checkIfAtLeastOneGrade() {
		if (numbergrade == "-" && lettergrade == "-") {
			throw new IllegalArgumentException();
		}
	}
}