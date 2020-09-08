package pw.cdmi.aws.geo.repositories.mongo;

import org.springframework.data.mongodb.repository.Query;

import pw.cdmi.aws.geo.model.entities.Town;
import pw.cdmi.aws.geo.repositories.TownRepository;
import pw.cdmi.core.db.MongoRepositoryBean;

@MongoRepositoryBean
public interface MongoTownRepository extends TownRepository {

    @Override
    @Query(value="{districtId : ?0}")
    public Iterable<Town> findByDistrictId(Integer districtId);
    
    @Override
    @Query(value="{name : ?0, districtId : ?1}", fields="{openId : 1}")
    public long findIdByNameAndDistrictId(String name, int districtId);

    @Override
    @Query(value="{districtId : ?0}", fields="{openId : 1, name : 1}")
    public Iterable<Town> findNamesByDistrictId(Integer districtId);
    
    @Override
    @Query(count = true, value="{countryId : ?0}")
    public long countByCountryId(Integer countryId);
    
    @Override
    @Query(count = true, value="{provinceId : ?0}")
    public long countByProvinceId(Integer  provinceId);
    
    @Override
    @Query(count = true, value="{cityId : ?0}")
    public long countByCityId(Integer  cityId);
    
    @Override
    @Query(count = true, value="{districtId : ?0}")
    public long countByDistrictId(Integer  districtId);
    
}
