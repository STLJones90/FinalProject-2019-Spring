package models;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.List;

public class SchoolRepository
{

    private JPAApi jpaApi;

    @Inject
    public SchoolRepository(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    public List<School> getList()
    {
        String sql = "SELECT sc FROM school sc GROUP BY sc.school";
        TypedQuery query = jpaApi.em().createQuery(sql, School.class);
        List<School> schools = query.getResultList();
        return schools;
    }



}
