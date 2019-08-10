package models;

import java.math.BigDecimal;

public class SalesTrend
{
    private String startDateRange;
    private String endDateRange;
    private int homeCount;
    private int averageSalePrice;
    private int medSalePrice;
    private String interval;

    public int getMedSalePrice()
    {
        return medSalePrice;
    }

    public void setMedSalePrice(int medSalePrice)
    {
        this.medSalePrice = medSalePrice;
    }

    public String getInterval()
    {
        return interval;
    }

    public void setInterval(String interval)
    {
        this.interval = interval;
    }

    public String getStartDateRange()
    {
        return startDateRange;
    }

    public void setStartDateRange(String startDateRange)
    {
        this.startDateRange = startDateRange;
    }

    public String getEndDateRange()
    {
        return endDateRange;
    }

    public void setEndDateRange(String endDateRange)
    {
        this.endDateRange = endDateRange;
    }

    public int getHomeCount()
    {
        return homeCount;
    }

    public void setHomeCount(int homeCount)
    {
        this.homeCount = homeCount;
    }

    public int getAverageSalePrice()
    {
        return averageSalePrice;
    }

    public void setAverageSalePrice(int averageSalePrice)
    {
        this.averageSalePrice = averageSalePrice;
    }
}
