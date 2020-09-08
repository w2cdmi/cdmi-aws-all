package pw.cdmi.aws.geo.repositories.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.cdmi.aws.geo.model.entities.Town;
import pw.cdmi.aws.geo.repositories.TownRepository;

public interface JpaTownRepository extends TownRepository {    
    
    @Override
    @Query("FROM Town WHERE districtId = :districtId")
    public Iterable<Town> findByDistrictId(Integer districtId);
    
    @Override
    @Query("SELECT openId FROM Town WHERE name = :name AND districtId = :districtId")
    public long findIdByNameAndDistrictId(String name, int districtId);

    @Override
    @Query("SELECT new Town(t.openId,t.name) FROM Town t WHERE t.districtId = :districtId")
    public Iterable<Town> findNamesByDistrictId(@Param(value = "districtId") Integer districtId);
    
    @Override
    @Query("SELECT count(t) FROM Town t WHERE t.countryId = :countryId")
    public long countByCountryId(@Param(value = "countryId") Integer countryId);
    
    @Override
    @Query(value="SELECT count(t) FROM Town t WHERE t.provinceId = :provinceId")
    public long countByProvinceId(@Param(value="provinceId") Integer  provinceId);
    
    @Override
    @Query(value="SELECT count(t) FROM Town t WHERE t.cityId = :cityId")
    public long countByCityId(@Param(value="cityId") Integer  cityId);
    
    @Override
    @Query(value="SELECT count(t) FROM Town t WHERE t.districtId = :districtId")
    public long countByDistrictId(Integer districtId);
}
