package pw.cdmi.aws.geo.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.cdmi.aws.geo.model.entities.City;
import pw.cdmi.aws.geo.model.entities.Country;
import pw.cdmi.aws.geo.model.entities.District;
import pw.cdmi.aws.geo.model.entities.Province;
import pw.cdmi.aws.geo.model.entities.Town;
import pw.cdmi.aws.geo.repositories.CityRepository;
import pw.cdmi.aws.geo.repositories.CountryRepository;
import pw.cdmi.aws.geo.repositories.DistrictRepository;
import pw.cdmi.aws.geo.repositories.ProvinceRepository;
import pw.cdmi.aws.geo.repositories.TownRepository;
import pw.cdmi.aws.geo.service.GeoService;
import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.ClientReason;
import pw.cdmi.core.http.exception.GlobalHttpClientError;
import pw.cdmi.core.http.exception.SystemReason;

/****************************************************
 * 实现类，提供行政区域信息的具体处理。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 29, 2014
 ***************************************************/
@Service
public class GeoServiceImpl implements GeoService {
    private static final Logger logger = LoggerFactory.getLogger(GeoServiceImpl.class);

    @Autowired
    private CityRepository cityDaoImpl;

    @Autowired
    private ProvinceRepository provinceDaoImpl;

    @Autowired
    private CountryRepository countryDaoImpl;

    @Autowired
    private DistrictRepository districtDaoImpl;

    @Autowired
    private TownRepository townDaoImpl;

    @Override
    public Country addCountry(Country country) {
        try {
            countryDaoImpl.save(country);
            return country;
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public void deleteCountry(int countryId) {
        try {
            countryDaoImpl.delete(countryId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Country modifyCountry(Country country) {
        try {
            countryDaoImpl.save(country);
            return country;
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Country getCountry(int openId) {
        try {
            return countryDaoImpl.findOne(openId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Integer getCountryId(String cname) {
        try {
            Integer openId = countryDaoImpl.findIdByName(cname);
            return Integer.valueOf(openId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Iterable<Country> listCountryCodes(boolean onlySovereignty) {
        if (onlySovereignty) {
            return countryDaoImpl.findNamesBySovereign();
        } else {
            return countryDaoImpl.findNames();
        }
    }

    @Override
    public Iterable<Country> listCountries(boolean onlySovereignty) {
        if (onlySovereignty) {
            return countryDaoImpl.findBySovereign();
        } else {
            return countryDaoImpl.findAll();
        }
    }

    @Override
    public long getCountryCount() {
        return countryDaoImpl.count();
    }

    @Override
    public Province addProvince(int countryId, Province province) {
        try {
            province.setCountryId(countryId);
            provinceDaoImpl.save(province);
            return province;
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public void deleteProvince(int provinceId) {
        try {
            provinceDaoImpl.delete(provinceId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Province modifyProvince(Province province) {
        try {
            provinceDaoImpl.save(province);
            return province;
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Province getProvince(int openId) {
        try {
            return provinceDaoImpl.findOne(openId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Integer getProvinceId(String cname) {
        try {
            // String hql = "select openId from Province where name='" + cname + "' or fullName='" + cname + "'";
            Object obj = provinceDaoImpl.findIdByName(cname);
            if (obj != null) {
                Integer openId = (Integer) obj;
                return openId;
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Iterable<Province> listProvinces(int countryId) {
        try {
            // String jpql = "SELECT t FROM Province t WHERE t.countryId = :countryId";
            // JPQuery query = JPQuery.createQuery(jpql);
            // query.setParamater("countryId", countryId);
            return provinceDaoImpl.findByCountryId(countryId);

        } catch (Exception e) {
            logger.error("执行listProvinces方法出现错误：" + e);
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Iterable<Province> listProvinces(String domain) {
        // String jpql = "select a.* from Province a, Country b where b.openId=a.countryId and b.domainName=:domain";
        // JPQuery query = JPQuery.createQuery(jpql);
        // query.setParamater("domain", domain);
        Integer country_id = countryDaoImpl.findIdByDomain(domain);
        if (country_id == null) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.NotFoundCountryByDomain);
        }
        return provinceDaoImpl.findByCountryId(country_id);
    }

    @Override
    public Iterable<Province> listProvinceCodes(int countryId) {
        // String jpql = "SELECT new Province(t.openId,t.name) FROM Province t WHERE t.countryId = :countryId";
        // JPQuery query = JPQuery.createQuery(jpql);
        // query.setParamater("countryId", countryId);
        // return daoImpl.find(query);
        return provinceDaoImpl.findNamesByCountryId(countryId);
    }

    @Override
    public long getProvinceCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long getProvinceCountByCountry(Integer countryId) {
        // String jpql = null;
        // JPQuery query = null;
        try {
            if (countryId != null) {
                // jpql = "SELECT count(t) FROM Province t WHERE t.countryId = :countryId";
                // query = JPQuery.createQuery(jpql);
                // query.setParamater("countryId", countryId);
                return provinceDaoImpl.countByCountryId(countryId);
            } else {
                // jpql = "SELECT count(t) FROM Province t";
                // query = JPQuery.createQuery(jpql);
                // return daoImpl.getCount(query);
                // return daoImpl.getCount(Province.class);
                return provinceDaoImpl.count();
            }

        } catch (Exception e) {
            logger.error("执行listProvinces方法出现错误：" + e);
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public City addCity(int countryId, int provinceId, City city) {
        try {
            city.setCountryId(countryId);
            city.setProvinceId(provinceId);
            // entityManager.persist(city);
            cityDaoImpl.save(city);
            return city;
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public void deleteCity(int cityId) {
        // String hql = "delete City where openId ='" + cityId + "'";
        try {
            // entityManager.createQuery(hql).executeUpdate();
            cityDaoImpl.delete(cityId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public City modifyCity(City city) {
        try {
            // entityManager.persist(city);
            cityDaoImpl.save(city);
            return city;
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public City getCity(int cityId) {
        try {
            return cityDaoImpl.findOne(cityId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Integer getCityId(Integer provinceId, String cname) {
//        StringBuffer hql = new StringBuffer();
        // hql.append("select openId from City where (name='" + cname + "' or fullName='" + cname + "')");
        if (provinceId != null && provinceId != 0) {
            // hql.append(" and provinceId = " + provinceId);
            return cityDaoImpl.findIdByNameAndProvinceId(cname, provinceId);
        } else {
            return cityDaoImpl.findIdByName(cname);
        }
        // try {
        // Integer openId = cityDaoImpl.find(hql.toString());
        // return openId;
        // } catch (Exception e) {
        // throw new AWSServiceException(SystemError.SQLError, e);
        // }
    }

    @Override
    public Iterable<City> listCities(int provinceId) {
        return cityDaoImpl.findByProvinceId(provinceId);
        // String jpql = "from City where provinceId=:provinceId";
        // try {
        // JPQuery query = JPQuery.createQuery(jpql);
        // query.setParamater("provinceId", provinceId);
        // return daoImpl.find(query);
        // } catch (Exception e) {
        // e.printStackTrace();
        // throw new AWSServiceException(SystemError.SQLError, e);
        // }
    }

    @Override
    public Iterable<City> listCityCodes(int provinceId) {
        return cityDaoImpl.findNamesByProvinceId(provinceId);
        // try {
        // String jpql = "SELECT new City(t.openId,t.name) FROM City t WHERE t.provinceId = :provinceId";
        // JPQuery query = JPQuery.createQuery(jpql);
        // query.setParamater("provinceId", provinceId);
        // return daoImpl.find(query);
        //
        // } catch (Exception e) {
        // logger.error("执行listCityCodes方法出现错误：" + e);
        // throw new AWSServiceException(SystemError.SQLError, e);
        // }
    }

    @Override
    public long getCityCount() {
        return cityDaoImpl.count();
    }

    @Override
    public long getCityCountByCountry(Integer countryId) {
        return cityDaoImpl.countByCountryId(countryId);
    }

    @Override
    public long getCityCountByProvince(Integer provinceId) {
        return cityDaoImpl.countByProvinceId(provinceId);
    }

    @Override
    public District addDistrict(int countryId, int provinceId, int cityId, District district) {
        try {
            district.setCountryId(countryId);
            district.setProvinceId(provinceId);
            district.setCityId(cityId);
            districtDaoImpl.save(district);
            return district;
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public void deleteDistrict(int districtId) {
        // String hql = "delete District where openId ='" + districtId + "'";
        try {
            // entityManager.createQuery(hql).executeUpdate();
            districtDaoImpl.delete(districtId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public District modifyDistrict(District district) {
        try {
            // entityManager.persist(district);
            districtDaoImpl.save(district);
            return district;
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public District getDistrict(int districtId) {
        try {
            return districtDaoImpl.findOne(districtId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Integer getDistrictId(Integer provinceId, Integer cityId, String cname) {
        // StringBuffer hql = new StringBuffer();
        // hql.append("select openId from District where name='" + cname + "'");
        // if(provinceId != null && provinceId != 0) {
        // hql.append(" and provinceId = " + provinceId);
        // return districtDaoImpl.findIdByNameAndCityId(cname, cityId);
        // }
        if (cityId != null && cityId != 0) {
            // hql.append(" and cityId=" + cityId);
            return districtDaoImpl.findIdByNameAndCityId(cname, cityId);
        } else {
            return districtDaoImpl.findIdByName(cname);
        }
        // try {
        // Integer openId = daoImpl.findOne(hql.toString());
        // return openId;
        // } catch (Exception e) {
        // throw new AWSServiceException(SystemError.SQLError, e);
        // }
    }

    @Override
    public Iterable<District> listDistricts(int cityId) {
        return districtDaoImpl.findByCityId(cityId);
        // try {
        // JPQuery query = JPQuery.createQuery("District.findDistrictsByCityId");
        // query.setParamater("cityId", cityId);
        // return daoImpl.findByNamedQuery(query);
        // } catch (Exception e) {
        // throw new AWSServiceException(SystemError.SQLError, e);
        // }
    }

    @Override
    public Iterable<District> listDistrictCodes(int cityId) {
        return districtDaoImpl.findNamesByCityId(cityId);
        // try {
        // String jpql = "SELECT new District(t.openId,t.name) FROM District t WHERE t.cityId = :cityId";
        // JPQuery query = JPQuery.createQuery(jpql);
        // query.setParamater("cityId", cityId);
        // return daoImpl.find(query);
        //
        // } catch (Exception e) {
        // logger.error("执行listDistrictCodes方法出现错误：" + e);
        // throw new AWSServiceException(SystemError.SQLError, e);
        // }
    }

    @Override
    public long getDistrictCount() {
        return districtDaoImpl.count();
    }

    @Override
    public long getDistrictCountByCountry(Integer countryId) {
        return districtDaoImpl.countByCountryId(countryId);
    }

    @Override
    public long getDistrictCountByProvince(Integer provinceId) {
        return districtDaoImpl.countByProvinceId(provinceId);
    }

    @Override
    public long getDistrictCountByCity(Integer cityId) {
        return districtDaoImpl.countByCityId(cityId);
    }

    @Override
    public Town addTown(int countryId, int provinceId, int cityId, int districtId, Town town) {
        try {
            town.setCountryId(countryId);
            town.setProvinceId(provinceId);
            town.setCityId(cityId);
            town.setDistrictId(districtId);
            townDaoImpl.save(town);
            return town;
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public void deleteTown(long townId) {
        try {
            townDaoImpl.delete(townId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public void clearTown() {
        try {
            townDaoImpl.deleteAll();
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Town modifyTown(Town town) {
        try {
            townDaoImpl.save(town);
            return town;
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Town getTown(long townId) {
        try {
            return townDaoImpl.findOne(townId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Long getTownId(int districtId, String cname) {
        // try {
        // String hql = "select openId from Town where name='" + cname + "'";
        //
        // Long openId = daoImpl.findOne(hql);
        // return openId;
        // } catch (Exception e) {
        // throw new AWSServiceException(SystemError.SQLError, e);
        // }
        return townDaoImpl.findIdByNameAndDistrictId(cname, districtId);
    }

    @Override
    public Iterable<Town> listTowns(int districtId) {
        // try {
        // String jpql = "SELECT t FROM Town t WHERE t.districtId = :districtId";
        // JPQuery query = JPQuery.createQuery(jpql);
        // query.setParamater("districtId", districtId);
        // return daoImpl.find(query);
        //
        // } catch (Exception e) {
        // logger.error("执行listDistrictCodes方法出现错误：" + e);
        // throw new AWSServiceException(SystemError.SQLError, e);
        // }
        return townDaoImpl.findByDistrictId(districtId);
    }

    @Override
    public Iterable<Town> listTownCodes(int districtId) {
        // try {
        // String jpql = "SELECT new Town(t.openId,t.name) FROM Town t WHERE t.districtId = :districtId";
        // JPQuery query = JPQuery.createQuery(jpql);
        // query.setParamater("districtId", districtId);
        // return daoImpl.find(query);
        //
        // } catch (Exception e) {
        // logger.error("执行listDistrictCodes方法出现错误：" + e);
        // throw new AWSServiceException(SystemError.SQLError, e);
        // }
        return townDaoImpl.findNamesByDistrictId(districtId);
    }

    @Override
    public long getTownCount() {
        return townDaoImpl.count();
    }

    @Override
    public long getTownCountByCountry(Integer countryId) {
        return townDaoImpl.countByCountryId(countryId);
    }

    @Override
    public long getTownCountByProvince(Integer provinceId) {
        return townDaoImpl.countByProvinceId(provinceId);
    }

    @Override
    public long getTownCountByCity(Integer cityId) {
        return townDaoImpl.countByCityId(cityId);
    }

    @Override
    public long getTownCountByDistrict(Integer districtId) {
        return townDaoImpl.countByDistrictId(districtId);
    }

    @Override
    public Iterable<Province> listProvinceByCountryName(String countryName) {
        Integer country_id = countryDaoImpl.findIdByName(countryName);
        if(country_id == null){
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter,SystemReason.InvalidRequest);
        }
        return provinceDaoImpl.findByCountryId(country_id);
    }
}
