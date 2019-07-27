package models;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.List;

public class AmenityRepository
{
    private JPAApi jpaApi;

    @Inject
    public AmenityRepository(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    public List<Amenity> getList()
    {
        String sql = "SELECT a FROM amenity a ORDER BY a.amenityId";
        TypedQuery query = jpaApi.em().createQuery(sql, Amenity.class);
        List<Amenity> amenities = query.getResultList();
        return amenities;
    }






}
