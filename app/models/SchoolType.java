package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SchoolType
{
    @Id
    @GeneratedValue
    private int schoolTypeId;
    private String schoolType;

    public SchoolType(int schoolTypeId, String schoolType)
    {
        this.schoolTypeId = schoolTypeId;
        this.schoolType = schoolType;
    }

    public void setSchoolTypeId(int schoolTypeId)
    {
        this.schoolTypeId = schoolTypeId;
    }

    public void setSchoolType(String schoolType)
    {
        this.schoolType = schoolType;
    }

}
