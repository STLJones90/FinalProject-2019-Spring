package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class School
{
    @Id
    @GeneratedValue
    private int schoolId;
    private String schoolName;
    private int schoolLongitude;
    private int schoolLatitude;

    public School(int schoolId, String schoolName, int schoolLongitude, int schoolLatitude)
    {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.schoolLongitude = schoolLongitude;
        this.schoolLatitude = schoolLatitude;
    }

    public void setSchoolId(int schoolId)
    {
        this.schoolId = schoolId;
    }

    public void setSchoolName(String schoolName)
    {
        this.schoolName = schoolName;
    }

    public void setSchoolLongitude(int schoolLongitude)
    {
        this.schoolLongitude = schoolLongitude;
    }

    public void setSchoolLatitude(int schoolLatitude)
    {
        this.schoolLatitude = schoolLatitude;
    }
}
