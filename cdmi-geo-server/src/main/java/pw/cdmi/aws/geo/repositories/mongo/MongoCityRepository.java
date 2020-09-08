package pw.cdmi.aws.geo.repositories.mongo;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.cdmi.aws.geo.model.entities.City;
import pw.cdmi.aws.geo.repositories.CityRepository;
import pw.cdmi.core.db.MongoRepositoryBean;

@MongoRepositoryBean
public interface MongoCityRepository extends CityRepository{
    
    //FIXME
    @Override
//    @Query("SELECT p FROM City p, Country c WHERE p.countryId=c.openId AND (c.name = :countryName OR c.fullName = :countryName)")
    @Query(value="{'countryId':'Country.openId', 'Country.name': ?0}")
    public Iterable<City> findByCountryName(String countryName);
    
    @Override
    @Query(value="{'countryId' : ?0}")
    public Iterable<City> findByCountryId(Integer countryId);
    
    @Override
    @Query(value="{'provinceId' : ?0}")
    public Iterable<City> findByProvinceId(Integer provinceId);
    
    @Override
    @Query(value="{'countryId' : ?0}", fields="{openId:1, name:1}")
    public Iterable<City> findNamesByCountryId(Integer countryId);
    
    @Override
    @Query(value="{'provinceId' : ?0}", fields="{openId:1, name:1}")
    public Iterable<City> findNamesByProvinceId(Integer provinceId);
    
    @Override
    @Query(value="{'name': ?0} OR {'fullName': ?0}", fields="{openId:1}")
    public Integer findIdByName(@Param(value="name") String name);
    
    @Query(count= true, value="{countryId : ?0 }")
    long countByCountryId(Integer countryId);
    
    @Query(count=true, value="{'provinceId': ?0}")
    long countByProvinceId(Integer  provinceId);
    
}
