package pw.cdmi.aws.geo.repositories.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.cdmi.aws.geo.model.entities.District;
import pw.cdmi.aws.geo.repositories.DistrictRepository;

public interface JpaDistrictRepository extends DistrictRepository {
    
    @Override
    @Query("FROM District WHERE cityId = :cityId")
    public Iterable<District> findByCityId(Integer cityId);
    
    @Override
    @Query("SELECT openId FROM District WHERE name = :name AND cityId = :cityId")
    public Integer findIdByNameAndCityId(String name, Integer cityId);
    
    @Override
    @Query("SELECT openId FROM District WHERE name = :name")
    public Integer findIdByName(@Param(value = "name") String name);

    @Override
    @Query("SELECT new District(d.openId,d.name) FROM District d WHERE d.cityId = :cityId")
    public Iterable<District> findNamesByCityId(@Param(value = "cityId") Integer cityId);
    
    @Override
    @Query("SELECT count(d) FROM District d WHERE d.countryId = :countryId")
    public long countByCountryId(@Param(value = "countryId") Integer countryId);
    
    @Override
    @Query(value="SELECT count(d) FROM District d WHERE d.provinceId = :provinceId")
    public long countByProvinceId(@Param(value="provinceId") Integer  provinceId);
    
    @Override
    @Query(value="SELECT count(d) FROM District d WHERE d.cityId = :cityId")
    public long countByCityId(@Param(value="cityId") Integer  cityId);
}
