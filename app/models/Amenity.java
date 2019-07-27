package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Amenity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int amenityId;
    private String amenityName;
    private String amenityCity;
    private String amenityType;
    private String industry;
    private String amenityAddress;
    private int amenityZipCode;
    private double amenityLatitude = 2.6;
    private double amenityLongitude = 2.6;

    public String getIndustry()
    {
        return industry;
    }

    public void setIndustry(String industry)
    {
        this.industry = industry;
    }

    public void setAmenityLatitude(double amenityLatitude)
    {
        this.amenityLatitude = amenityLatitude;
    }

    public void setAmenityLongitude(double amenityLongitude)
    {
        this.amenityLongitude = amenityLongitude;
    }

    public int getAmenityZipCode()
    {
        return amenityZipCode;
    }

    public void setAmenityZipCode(int amenityZipCode)
    {
        this.amenityZipCode = amenityZipCode;
    }

    public String getAmenityAddress()
    {
        return amenityAddress;
    }

    public void setAmenityAddress(String amenityAddress)
    {
        this.amenityAddress = amenityAddress;
    }

    public String getAmenityCity()
    {
        return amenityCity;
    }

    public void setAmenityCity(String amenityCity)
    {
        this.amenityCity = amenityCity;
    }

    public String getAmenityType()
    {
        return amenityType;
    }

    public void setAmenityType(String amenityType)
    {
        this.amenityType = amenityType;
    }

    public void setAmenityId(int amenityId)
    {
        this.amenityId = amenityId;
    }

    public void setAmenityName(String amenityName)
    {
        this.amenityName = amenityName;
    }

    public void setAmenityLongitude(int amenityLongitude)
    {
        this.amenityLongitude = amenityLongitude;
    }

    public void setAmenityLatitude(int amenityLatitude)
    {
        this.amenityLatitude = amenityLatitude;
    }

    public int getAmenityId()
    {
        return amenityId;
    }

    public String getAmenityName()
    {
        return amenityName;
    }

    public double getAmenityLongitude()
    {
        return amenityLongitude;
    }

    public double getAmenityLatitude()
    {
        return amenityLatitude;
    }



}
