package pw.cdmi.msm.geo.repositories.mongo;

import org.springframework.data.mongodb.repository.Query;

import pw.cdmi.msm.geo.model.entity.Country;
import pw.cdmi.msm.geo.repositories.CountryRepository;

public interface MongoCountryRepository extends CountryRepository {

    @Override
    @Query(value = "{'name': ?0} OR {'fullName': ?0}", fields = "{openId:1}")
    public Integer findIdByName(String name);

    @Override
    @Query(value = "{'domain': ?0}", fields = "{openId:1}")
    public Integer findIdByDomain(String domain);

    @Override
    @Query(value = "{}", fields = "{openId : 1, name : 1}")
    public Iterable<Country> findNames();

    @Override
    @Query(value = "{hasSovereignty : true}", fields = "{openId:1, name:1}")
    public Iterable<Country> findNamesBySovereign();

    @Override
    @Query(value = "{hasSovereignty : true}")
    public Iterable<Country> findBySovereign();
}
