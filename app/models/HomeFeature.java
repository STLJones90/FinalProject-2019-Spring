package models;

import java.math.BigDecimal;

public class HomeFeature
{
    private int houseSqrFeet;
    private int lotSqrFeet;
    private int numBedrooms;
    private int numBathrooms;
    private int numFloors;
    private byte isForeclosure;
    private int yearBuilt;
    private BigDecimal estHomeValue;

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
}
