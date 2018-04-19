package net.app.core.entities.university;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Course {

    public String code;
    public String name;
    public String description;
    public Date startDate;
    public Date endDate;

}
