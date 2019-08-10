package models;

import java.math.BigDecimal;

public class HomeFeature
{
    private String fullAddress;
    private int houseSqrFeet;
    private int lotSqrFeet;
    private String numBedrooms;
    private int numBathrooms;
    private int numFloors;
    private byte isForeclosure;
    private int yearBuilt;
    private BigDecimal estHomeValue;
    private double latitude = 3.6;
    private double longitude = 3.6;

    public String getFullAddress()
    {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress)
    {
        this.fullAddress = fullAddress;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public int getNumFloors()
    {
        return numFloors;
    }

    public void setNumFloors(int numFloors)
    {
        this.numFloors = numFloors;
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

    public String getNumBedrooms()
    {
        return numBedrooms;
    }

    public void setNumBedrooms(String numBedrooms)
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
}
