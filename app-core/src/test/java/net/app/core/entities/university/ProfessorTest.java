package net.app.core.entities.university;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProfessorTest {

    private Date currentDate = new Date();
    private Course course;

    @Before
    public void Create_Course(){
        course = new Course("ASU101", "Intro to ASU", "Intro Course",
                modDays(currentDate, 1),
                modDays(currentDate, 90));
    }


    @Test
    public void Should_FailToRegister_When_ClassStartsInThePast() {
        // Arrange
        Professor professor = new Professor();
        Course pastCourse = new Course("ASU101",
                "Intro to ASU",
                "Intro Course",
                modDays(currentDate, -90),
                modDays(currentDate, -1));

        // Act
        boolean result = professor.registerForCourses(pastCourse);

        // Assert
        assertFalse(result);
    }

    @Test
    public void Should_FailToRegister_When_ProfessorHasMoreThan5Classes() {
        // Arrange
        Professor professor = new Professor();
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        courses.add(course);
        courses.add(course);
        courses.add(course);
        courses.add(course);
        professor.setCurrentCourses(courses);

        // Act
        boolean result = professor.registerForCourses(course);

        // Assert
        assertFalse(result);
    }

    @Test
    public void Should_Register_When_ClassIsInFutureAndLessThan5() {
        // Arrange
        Professor professor = new Professor();
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        courses.add(course);
        professor.setCurrentCourses(courses);

        // Act
        boolean result = professor.registerForCourses(course);

        // Assert
        assertTrue(result);
    }


    /**
     * Utility to help test dates
     * @param date
     * @param days
     * @return Modified time based on date
     */
    public static Date modDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
