package pw.cdmi.aws.geo.repositories.mongo;

import pw.cdmi.aws.geo.repositories.CustomCityRepository;
import pw.cdmi.core.db.MongoRepositoryBean;

@MongoRepositoryBean
public class CustomCityRepositoryImpl implements CustomCityRepository{


    @Override
    public long countByProvince(Integer provinceId) {
        return 0l;
    }

}
