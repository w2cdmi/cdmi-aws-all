package pw.cdmi.msm.geo.repositories.mongo;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.cdmi.msm.geo.model.entity.District;
import pw.cdmi.msm.geo.repositories.DistrictRepository;

public interface MongoDistrictRepositories extends DistrictRepository{

    @Override
    @Query(value="{cityId : ?0}")
    public Iterable<District> findByCityId(Integer cityId);
    
    @Override
    @Query(value="{name : ?0, cityId : ?1}", fields="{openId : 1}")
    public Integer findIdByNameAndCityId(String name, Integer cityId);
    
    @Override
    @Query(value="{name : ?0}", fields="{openId : 1}")
    public Integer findIdByName(@Param(value = "name") String name);

    @Override
    @Query(value="{cityId : ?0}", fields="{openId : 1, name : 1}")
    public Iterable<District> findNamesByCityId(Integer cityId);
    
    @Override
    @Query(count = true, value="{countryId : ?0}")
    public long countByCountryId(Integer countryId);
    
    @Override
    @Query(count = true, value="{provinceId : ?0}")
    public long countByProvinceId(Integer  provinceId);
    
    @Override
    @Query(count = true, value="{cityId : ?0}")
    public long countByCityId(Integer  cityId);
}
