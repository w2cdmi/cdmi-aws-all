package pw.cdmi.aws.geo.repositories.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.cdmi.aws.geo.model.entities.Country;
import pw.cdmi.aws.geo.repositories.CountryRepository;

public interface JpaCountryRepository extends CountryRepository {
    
    @Override
    @Query("SELECT openId FROM Country WHERE name = :name or fullName = :name")
    public Integer findIdByName(@Param(value="name") String name);
    
    @Override
    @Query("SELECT openId FROM Country WHERE domain = :domain")
    public Integer findIdByDomain(@Param(value="domain") String domain);
    
    @Override
    @Query("SELECT new Country(c.openId,c.name) FROM Country c")
    public Iterable<Country> findNames();
    
    @Override
    @Query("SELECT new Country(c.openId,c.name) FROM Country c WHERE hasSovereignty =true")
    public Iterable<Country> findNamesBySovereign();
    
    @Override
    @Query("FROM Country WHERE hasSovereignty =true")
    public Iterable<Country> findBySovereign();
}
