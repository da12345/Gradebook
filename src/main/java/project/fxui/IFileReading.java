package project.fxui;

import java.io.InputStream;
import java.io.OutputStream;

import project.models.CalculatedData;
import project.models.CourseList;

public interface IFileReading {
	CourseList readCourseList(InputStream is) throws Exception;
	CourseList readCourseList1(String name) throws Exception;
	void writeCourseList(CourseList courselist, CalculatedData cd, OutputStream os) throws Exception;
	void writeCourseList1(CalculatedData cd, CourseList courselist, String string) throws Exception;
}
