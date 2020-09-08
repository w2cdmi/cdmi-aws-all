package pw.cdmi.msm.geo.repositories.jpa;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.cdmi.msm.geo.model.entity.District;
import pw.cdmi.msm.geo.repositories.DistrictRepository;

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
    @Query("SELECT new District(p.openId,p.name) FROM District WHERE cityId = :cityId")
    public Iterable<District> findNamesByCityId(@Param(value = "cityId") Integer cityId);
    
    @Override
    @Query("SELECT count(d) FROM District d WHERE p.countryId = :countryId")
    public long countByCountryId(@Param(value = "countryId") Integer countryId);
    
    @Override
    @Query(value="SELECT count(d) FROM District d WHERE d.provinceId = :provinceId")
    public long countByProvinceId(@Param(value="provinceId") Integer  provinceId);
    
    @Override
    @Query(value="SELECT count(d) FROM District d WHERE d.cityId = :cityId")
    public long countByCityId(@Param(value="cityId") Integer  cityId);
}
