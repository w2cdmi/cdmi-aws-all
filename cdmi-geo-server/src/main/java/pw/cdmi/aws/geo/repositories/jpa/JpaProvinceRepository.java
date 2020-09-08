package pw.cdmi.aws.geo.repositories.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pw.cdmi.aws.geo.model.entities.Province;
import pw.cdmi.aws.geo.repositories.ProvinceRepository;

@Repository
public interface JpaProvinceRepository extends ProvinceRepository {

    @Override
    @Query("SELECT openId FROM Province WHERE name = :name OR fullName = :name")
    public Integer findIdByName(@Param(value = "name") String name);

    @Override
    @Query("SELECT p FROM Province p, Country c WHERE p.countryId=c.openId AND (c.name = :countryName OR c.fullName = :countryName)")
    public Iterable<Province> findByCountryName(@Param(value = "countryName") String countryName);

//    @Override
//    @Query("SELECT p from Province p, Country c WHERE p.countryId=c.openId AND c.domainName=:countryDomain")
//    public Iterable<Province> findByCountryDomain(@Param(value = "countryDomain") String countryDomain);

    @Override
    @Query("FROM Province WHERE countryId = :countryId")
    public Iterable<Province> findByCountryId(@Param(value = "countryId") Integer countryId);
    
    @Override
    @Query("SELECT new Province(p.openId,p.name) FROM Province p WHERE countryId = :countryId")
    public Iterable<Province> findNamesByCountryId(@Param(value = "countryId") Integer countryId);
    @Override
    
    @Query("SELECT count(p) FROM Province p WHERE p.countryId = :countryId")
    public long countByCountryId(@Param(value = "countryId") Integer countryId);
}
