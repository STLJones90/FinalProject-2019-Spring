package models;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.List;

public class SaleHistoryRepository
{

    private JPAApi jpaApi;

    @Inject
    public SaleHistoryRepository(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    public List addSaleHistory(SaleHistory saleHistory)
    {
        return addSaleHistory(saleHistory);
    }

    public List<SaleHistory> getList(int homeId)
    {
        String sql = "SELECT s FROM saleHistory s GROUP BY :homeId";
        TypedQuery query = jpaApi.em().createQuery(sql, SaleHistory.class);
        List<SaleHistory> saleHistories = query.getResultList();
        return saleHistories;
    }


}
