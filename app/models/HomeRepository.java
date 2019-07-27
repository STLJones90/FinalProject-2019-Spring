package models;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.List;

public class HomeRepository
{
    private JPAApi jpaApi;

    @Inject
    public HomeRepository(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }



    public List<Home> getList()
    {
        String sql = "SELECT h FROM Home h";
        TypedQuery query = jpaApi.em().createQuery(sql, Home.class);
        List<Home> homes = query.getResultList();
        return homes;
    }


    public void add(Home home)
    {
        jpaApi.em().persist(home);
    }



/*

    public Home get(int homeId)
    {
        String sql = "SELECT h FROM Home h WHERE homeId = :homeId";
        TypedQuery<Home> query = jpaApi.em().createQuery(sql, Home.class);
        query.setParameter("homeId", homeId);
        Home home = query.getSingleResult();
        return home;
    }


    /*

    //allows the user to search houses by zip code
    public List<Home> getList(String searchZipCode)
    {
        String searchValue = searchZipCode;

        if (searchZipCode == null)
        {
            searchZipCode = "";
        }

        if (searchZipCode.length() <= 1)
        {
            searchZipCode = searchZipCode + "%";
        }

        else
        {
            searchZipCode = "%" + searchZipCode + "%";
        }

        String sql = "SELECT h FROM Home h WHERE h.zipcode LIKE :searchZipCode ORDER BY e.zipcode";
        TypedQuery query = jpaApi.em().createQuery(sql, Home.class);
        query.setParameter("searchZipCode", "%" + searchZipCode + "%");
        List<Home> homes = query.getResultList();
        return homes;
    }

    public List<HomeDetail> getDetailList(String searchAddress)
    {
        String searchValue = searchAddress;

        if (searchAddress == null)
        {
            searchAddress = "";
        }

        if (searchAddress.length() <= 1)
        {
            searchAddress = searchAddress + "%";
        }

        else
        {
            searchAddress = "%" + searchAddress + "%";
        }



        String sql =
                "SELECT NEW HomeDetail(h.homeId, h.city, h.address, h.state, h.zipCode ," +
                        "h.latitude, h.longitude, h.sqrFeetHouse, h.lotSqrFeet, h.numBedrooms, h.numBathrooms " +
                        "h.isForeclosure, h.yearBuilt, h.estHomeValue, h.picture " +
                        "FROM Home h " +
                        "JOIN salehistory s " +
                        "ON s.homeid = h.homeid " +
                        "WHERE address LIKE :searchAddress " +
                        "ORDER BY h.zipcode";
        TypedQuery<HomeDetail> query = jpaApi.em().createQuery(sql, HomeDetail.class);
        query.setParameter("searchAddress", "%" + searchAddress + "%");
        List<HomeDetail> homes = query.getResultList();
        return homes;
    }


*/

}
