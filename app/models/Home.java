package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
public class Home
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int homeId;
    private String city;
    private String address;
    private String state;
    private int zipCode;
    private double latitude = 2.6;
    private double longitude = 2.6;
    private int houseSqrFeet;
    private int lotSqrFeet;
    private int numBedrooms;
    private int numBathrooms;
    private byte isForeclosure;
    private int yearBuilt;
    private BigDecimal estHomeValue;
    private byte[] homePic;

    public int getHomeId()
    {
        return homeId;
    }

    public void setHomeId(int homeId)
    {
        this.homeId = homeId;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public int getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(int zipCode)
    {
        this.zipCode = zipCode;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public int getHouseSqrFeet()
    {
        return houseSqrFeet;
    }

    public void setHouseSqrFeet(int houseSqrFeet)
    {
        this.houseSqrFeet = houseSqrFeet;
    }

    public int getLotSqrFeet()
    {
        return lotSqrFeet;
    }

    public void setLotSqrFeet(int lotSqrFeet)
    {
        this.lotSqrFeet = lotSqrFeet;
    }

    public int getNumBedrooms()
    {
        return numBedrooms;
    }

    public void setNumBedrooms(int numBedrooms)
    {
        this.numBedrooms = numBedrooms;
    }

    public int getNumBathrooms()
    {
        return numBathrooms;
    }

    public void setNumBathrooms(int numBathrooms)
    {
        this.numBathrooms = numBathrooms;
    }

    public byte getIsForeclosure()
    {
        return isForeclosure;
    }

    public void setIsForeclosure(byte isForeclosure)
    {
        this.isForeclosure = isForeclosure;
    }

    public int getYearBuilt()
    {
        return yearBuilt;
    }

    public void setYearBuilt(int yearBuilt)
    {
        this.yearBuilt = yearBuilt;
    }

    public BigDecimal getEstHomeValue()
    {
        return estHomeValue;
    }

    public void setEstHomeValue(BigDecimal estHomeValue)
    {
        this.estHomeValue = estHomeValue;
    }

    public byte[] getHomePic()
    {
        return homePic;
    }

    public void setHomePic(byte[] homePic)
    {
        this.homePic = homePic;
    }

}
