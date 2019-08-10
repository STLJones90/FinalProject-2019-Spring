package models;

import java.math.BigDecimal;

public class Location
{

    private double latitude = 3.6;
    private double longitude = 3.6;
    private String url;


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


    public String getUrl()
    {
        return url;
    }


    public void setUrl(String url)
    {
        this.url = url;
    }

}

