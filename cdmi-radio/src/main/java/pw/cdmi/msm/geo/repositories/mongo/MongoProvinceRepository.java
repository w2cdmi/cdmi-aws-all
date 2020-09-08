package pw.cdmi.msm.geo.repositories.mongo;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.cdmi.msm.geo.model.entity.Province;
import pw.cdmi.msm.geo.repositories.ProvinceRepository;

public interface MongoProvinceRepository extends ProvinceRepository{

    @Override
    @Query(value="{'name': ?0} OR {'fullName': ?0}", fields="{openId:1}")
    public Integer findIdByName(@Param(value = "name") String name);

    @Override
    @Query(value="{countryId.name: ?0}}")
    public Iterable<Province> findByCountryName(String countryName);
    
//    @Override
////    @Query("SELECT p from Province p, Country c WHERE p.countryId=c.openId AND c.domainName=:countryDomain")
//    @Query(value="{'countryId' : 'Country.openId', 'Country.domainName': ?0}")
//    public Iterable<Province> findByCountryDomain(String countryDomain);
    
    @Override
    @Query(value="{countryId : ?0}")
    public Iterable<Province> findByCountryId(Integer countryId);
    
    @Override
    @Query(value="{countryId : ?0}", fields = "{openId : 1, name :1}")
    public Iterable<Province> findNamesByCountryId(Integer countryId);
    
    @Query(count= true, value="{countryId : ?0 }")
    public long countByCountryId(Integer countryId);
}
