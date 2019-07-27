package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class SaleHistory
{
    @Id
    @GeneratedValue
    private int saleHistoryId;
    private BigDecimal pastSellPrice;
    private String dateOfSale;
    private BigDecimal pricePerSqrFoot;

    public BigDecimal getPricePerSqrFoot()
    {
        return pricePerSqrFoot;
    }

    public void setPricePerSqrFoot(BigDecimal pricePerSqrFoot)
    {
        this.pricePerSqrFoot = pricePerSqrFoot;
    }

    public int getSaleHistoryId()
    {
        return saleHistoryId;
    }

    public void setSaleHistoryId(int saleHistoryId)
    {
        this.saleHistoryId = saleHistoryId;
    }

    public BigDecimal getPastSellPrice()
    {
        return pastSellPrice;
    }

    public void setPastSellPrice(BigDecimal pastSellPrice)
    {
        this.pastSellPrice = pastSellPrice;
    }

    public String getDateOfSale()
    {
        return dateOfSale;
    }

    public void setDateOfSale(String dateOfSale)
    {
        this.dateOfSale = dateOfSale;
    }
}
