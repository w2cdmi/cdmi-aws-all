package pw.cdmi.msm.geo.repositories.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import pw.cdmi.msm.geo.model.entity.City;
import pw.cdmi.msm.geo.repositories.CustomCityRepository;

public class CustomCityRepositoryImpl implements CustomCityRepository{
    
    @Autowired
    private MongoTemplate operations;


    @Override
    public long countByProvince(Integer provinceId) {
        Query query = new Query();
        query.addCriteria(new Criteria("provinceId").is(provinceId));
        return operations.count(query, City.class);
    }

}
