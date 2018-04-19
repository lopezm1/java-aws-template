package net.app.core.entities.university;

import lombok.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
public class Professor {

    public String firstName;
    public String lastName;
    public List<Course> currentCourses;
    public List<Course> courseHistory;

    public boolean registerForCourses(Course course){

        //Cannot teach a course that was in the past
        if(Calendar.getInstance().getTime().after(course.endDate)) { return false; }

        //Cannot teach more than 5 courses in a semester
        if(currentCourses.size() >= 5) { return false; }

        currentCourses.add(course);

        return true;
    }
}
