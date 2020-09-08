package pw.cdmi.aws.geo.repositories.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.cdmi.aws.geo.model.entities.City;
import pw.cdmi.aws.geo.repositories.CityRepository;


public interface JpaCityRepository extends CityRepository{
    
    @Override
    @Query("SELECT p FROM City p, Country c WHERE p.countryId=c.openId AND (c.name = :countryName OR c.fullName = :countryName)")
    public Iterable<City> findByCountryName(@Param(value="countryName") String countryName);
    
    @Override
    @Query("FROM City c WHERE c.countryId = :countryId")
    public Iterable<City> findByCountryId(@Param(value="countryId") Integer countryId);
    
    @Override
    @Query("FROM City c WHERE c.provinceId = :provinceId")
    public Iterable<City> findByProvinceId(@Param(value="provinceId") Integer provinceId);
    
    @Override
    @Query(value="SELECT new City(c.openId,c.name) FROM City c WHERE c.countryId = :countryId")
    public Iterable<City> findNamesByCountryId(@Param(value="countryId") Integer countryId);
    
    @Override
    @Query(value="SELECT new City(c.openId,c.name) FROM City c WHERE c.provinceId = :provinceId")
    public Iterable<City> findNamesByProvinceId(@Param(value="provinceId") Integer provinceId);
    
    @Override
    @Query(value="SELECT c.openId FROM City c WHERE c.name = :name OR c.fullName = :name")
    public Integer findIdByName(@Param(value="name") String name);
    
    @Override
    @Query(value="SELECT c.openId FROM City c WHERE (c.name = :name OR c.fullName = :name) AND c.provinceId = :provinceId")
    public Integer findIdByNameAndProvinceId(@Param(value="name") String name, @Param(value="provinceId") Integer provinceId);
    
    @Override
    @Query(value="SELECT count(c) FROM City c WHERE c.countryId = :countryId")
    long countByCountryId(@Param(value="countryId") Integer countryId);
    
    @Override
    @Query(value="SELECT count(c) FROM City c WHERE c.provinceId = :provinceId")
    long countByProvinceId(@Param(value="provinceId") Integer  provinceId);
   
}
