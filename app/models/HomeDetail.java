package models;

        import javax.persistence.Entity;
        import javax.persistence.Id;
        import java.math.BigDecimal;

@Entity
public class HomeDetail
{
    @Id
    private int homeId;
    private String city;
    private String address;
    private String state;
    private String zipCode;
    private double latitude = 3.6;
    private double longitude = 3.6;
    private int houseSqrFeet;
    private int lotSqrFeet;
    private int numBedrooms;
    private int numBathrooms;
    private byte isForeclosure;
    private int yearBuilt;
    private BigDecimal estHomeValue;
    private byte[] homePic;

    public HomeDetail(int homeId, String city, String address, String state, String zipCode, double latitude,
                      double longitude, int houseSqrFeet, int lotSqrFeet, int numBedrooms, int numBathrooms,
                      byte isForeclosure, int yearBuilt, BigDecimal estHomeValue, byte[] homePic)
    {
        this.homeId = homeId;
        this.city = city;
        this.address = address;
        this.state = state;
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.houseSqrFeet = houseSqrFeet;
        this.lotSqrFeet = lotSqrFeet;
        this.numBedrooms = numBedrooms;
        this.numBathrooms = numBathrooms;
        this.isForeclosure = isForeclosure;
        this.yearBuilt = yearBuilt;
        this.estHomeValue = estHomeValue;
        this.homePic = homePic;
    }

    public int getHomeId()
    {
        return homeId;
    }

    public String getCity()
    {
        return city;
    }

    public String getAddress()
    {
        return address;
    }

    public String getState()
    {
        return state;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public int getSqrFeetHouse()
    {
        return houseSqrFeet;
    }

    public int getLotSqrFeet()
    {
        return lotSqrFeet;
    }

    public int getNumBedrooms()
    {
        return numBedrooms;
    }

    public int getNumBathrooms()
    {
        return numBathrooms;
    }

    public byte getIsForeclosure()
    {
        return isForeclosure;
    }

    public int getYearBuilt()
    {
        return yearBuilt;
    }

    public BigDecimal getEstHomeValue()
    {
        return estHomeValue;
    }

    public byte[] getHomePic()
    {
        return homePic;
    }
}
