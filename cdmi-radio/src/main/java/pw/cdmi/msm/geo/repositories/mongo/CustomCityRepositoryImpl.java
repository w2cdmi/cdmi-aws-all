package pw.cdmi.msm.geo.repositories.mongo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pw.cdmi.msm.geo.repositories.CustomCityRepository;

public class CustomCityRepositoryImpl implements CustomCityRepository{
    
    @PersistenceContext
    private EntityManager em;


    @Override
    public long countByProvince(Integer provinceId) {
        return 0l;
    }

}
