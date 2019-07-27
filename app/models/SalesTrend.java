package models;

import java.math.BigDecimal;

public class SalesTrend
{
    private String startDateRange;
    private String endDateRange;
    private int homeCount;
    private int averageSalePrice;
    private String month;
    private String quarter;

    public String getMonth()
    {
        return month;
    }

    public void setMonth(String month)
    {
        this.month = month;
    }

    public String getQuarter()
    {
        return quarter;
    }

    public void setQuarter(String quarter)
    {
        this.quarter = quarter;
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
