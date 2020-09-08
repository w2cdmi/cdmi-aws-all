package pw.cdmi.aws.geo.repositories;

public interface CustomCityRepository {

    long countByProvince(Integer provinceId);
}
