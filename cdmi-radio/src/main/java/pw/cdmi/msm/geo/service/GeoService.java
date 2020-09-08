package pw.cdmi.msm.geo.service;

import pw.cdmi.msm.geo.model.entity.City;
import pw.cdmi.msm.geo.model.entity.Country;
import pw.cdmi.msm.geo.model.entity.District;
import pw.cdmi.msm.geo.model.entity.Province;
import pw.cdmi.msm.geo.model.entity.Town;

/****************************************************
 * 接口类，提供行政区域信息的处理方法。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 29, 2014
 ***************************************************/
public interface GeoService {

    /**
     * 手动添加一个国家信息
     * @param country 待添加的国家信息
     * @return 新添加的国家信息
     */
    public Country addCountry(Country country);

    /**
     * 手动删除一个国家信息，该操作需谨慎操作
     * @param countryId 待删除的国家信息ID
     */
    public void deleteCountry(int countryId);

    /**
     * 修改国家信息
     * @param country 待修改的国家信息
     * @return 修改后的国家信息
     */
    public Country modifyCountry(Country country);

    /**
     * 根据国家编码获得国家信息
     * @param openId 国家编码
     * @return 修改后的国家信息
     */
    public Country getCountry(int openId);

    /**
     * 通过国家名称获得国家的ID
     * @param cname 国家名称
     * @return 国家的ID
     */
    public Integer getCountryId(String cname);
    
    /**
     * 获得所有的国家信息
     * @param onlySovereignty 是否只导出主权国家
     * @return 国家信息列表
     */
    public Iterable<Country> listCountries(boolean onlySovereignty);

    /**
     * 获得所有的国家编码
     * @param onlySovereignty 是否只导出主权国家
     * @return 国家编码列表
     */
    public Iterable<Country> listCountryCodes(boolean onlySovereignty);

    /**
     * 获得所有国家代码数量
     * @return
     */
    public long getCountryCount();
    
    /**
     * 手动添加一个省份信息
     * @param countryId 待添加的省份信息对应的国家信息ID
     * @param province 待添加的省份信息
     * @return 新添加的省份信息
     */
    public Province addProvince(int countryId, Province province);

    /**
     * 手动删除一个省份信息，该操作需谨慎操作
     * @param provinceId 待删除的省份信息ID
     */
    public void deleteProvince(int provinceId);

    /**
     * 修改省份信息
     * @param province 待修改的省份信息
     * @return 修改后的省份信息
     */
    public Province modifyProvince(Province province);

    /**
     * 根据省份编码获得省份信息
     * @param openId 省份编码
     * @return 修改后的省份信息
     */
    public Province getProvince(int openId);

    /**
     * 通过省份名称获得省份地址的ID
     * @param cname 省份名称
     * @return 省份的ID
     */
    public Integer getProvinceId(String cname);
    
    /**
     * 获得指定国家下的所有省份列表
     * @param countryId 特定国家的信息ID
     * @return
     */
    public Iterable<Province> listProvinces(int countryId);

    /**
     * 获得指定国家下的所有省份信息
     * @param domain 特定国家的域名信息
     * @return
     */
    public Iterable<Province> listProvinces(String domain);

    /**
     * 获得指定国家下的所有省份编码列表
     * @param countryId 特定国家的信息ID
     * @return
     */
    public Iterable<Province> listProvinceCodes(int countryId);
    
    /**
     * 获得所有的省份数量
     * @return
     */
    public long getProvinceCount();
    
    /**
     * 获得指定国家下的省份数量，如果参数为空，则获取所有省份记录数量
     * @param countryId 特定国家编码
     * @return
     */
    public long getProvinceCountByCountry(Integer countryId);

    /**
     * 手动添加一个城市信息
     * @param countryId 待添加的省份信息对应的国家信息ID
     * @param provinceId 待添加的城市信息对应的省份信息ID
     * @param city 待添加的城市信息
     * @return 新添加的城市信息
     */
    public City addCity(int countryId, int provinceId, City city);

    /**
     * 手动删除一个城市信息，该操作需谨慎操作
     * @param cityId 待删除的城市信息ID
     */
    public void deleteCity(int cityId);

    /**
     * 修改城市信息
     * @param city 待修改的城市信息
     * @return 修改后的城市信息
     */
    public City modifyCity(City city);

    /**
     * 根据城市编码获取城市信息
     * @param cityId 城市编码
     */
    public City getCity(int cityId);

    /**
     * 通过城市名称获得城市地址的ID
     * @param cname 城市名称
     * @return 城市的ID
     */
    public Integer getCityId(Integer provinceId, String cname);
    
    /**
     * 获得指定省份下的城市信息列表
     * @param provinceId 省份信息ID
     * @return 城市信息列表
     */
    public Iterable<City> listCities(int provinceId);

    /**
     * 获得指定省份下的城市代码列表
     * @param provinceId 省份信息ID
     * @return 城市信息列表
     */
    public Iterable<City> listCityCodes(int provinceId);

    /**
     * 获得所有的城市数量
     * @return
     */
    public long getCityCount();
    
    /**
     * 获得指定国家下的城市数量，如果参数为空，则获取所有城市记录数量
     * @param countryId 特定国家编码
     * @return
     */
    public long getCityCountByCountry(Integer countryId);
    
    /**
     * 获得指定省份下的城市数量，如果参数为空，则获取所有城市记录数量
     * @param provinceId 特定省份编码
     * @return
     */
    public long getCityCountByProvince(Integer provinceId);
    
    /**
     * 手动添加一个城市区县信息
     * @param countryId 待添加的省份信息对应的国家信息ID
     * @param provinceId 待添加的城市区县信息对应的省份信息ID
     * @param cityId 待添加的城市区县信息对应的城市信息ID
     * @param district 待添加的城市区县信息
     * @return 新添加的城市区县信息
     */
    public District addDistrict(int countryId, int provinceId, int cityId, District district);

    /**
     * 手动删除一个城市区县信息，该操作需谨慎操作
     * @param districtId 待删除的城市区县信息ID
     */
    public void deleteDistrict(int districtId);

    /**
     * 修改城市区县信息
     * @param district 待修改的城市区县信息
     * @return 修改后的城市区县信息
     */
    public District modifyDistrict(District district);

    /**
     * 根据区县编码获取区县信息
     * @param cityId 区县编码
     */
    public District getDistrict(int districtId);

    /**
     * 通过区县名称获得县区地址的ID
     * @param cname 区县名称
     * @return 区县的ID
     */
    public Integer getDistrictId(Integer provinceId, Integer cityId,String cname);
    
    /**
     * 获得指定城市下的区县信息列表
     * @param cityId 城市信息ID
     * @return 城市区县信息列表
     */
    public Iterable<District> listDistricts(int cityId);

    /**
     * 获得指定城市下的区县信息列表
     * @param cityId 城市信息ID
     * @return 城市区县信息列表
     */
    public Iterable<District> listDistrictCodes(int cityId);

    /**
     * 获得所有的区县三级行政单位的数量
     * @return
     */
    public long getDistrictCount();
    
    /**
     * 获得指定国家下的所有的区县四级行政单位的数量
     * @param countryId 国家编码
     * @return
     */
    public long getDistrictCountByCountry(Integer countryId);
    
    /**
     * 获得指定国家下的所有的区县四级行政单位的数量
     * @param provinceId 二级行政单位省份编码
     * @return
     */
    public long getDistrictCountByProvince(Integer provinceId);
    
    /**
     * 获得指定国家下的所有的区县四级行政单位的数量
     * @param cityId 三级行政单位城市编码
     * @return
     */
    public long getDistrictCountByCity(Integer cityId);
    
    /**
     * 手动添加一个乡镇、街道办信息
     * @param countryId 待添加的乡镇、街道办对应的国家信息ID
     * @param provinceId 待添加的乡镇、街道办信息对应的省份信息ID
     * @param cityId 待添加的乡镇、街道办信息对应的城市信息ID
     * @param districtId 待乡镇、街道办信息对应的区县信息ID
     * @param town 待添加的乡镇、街道办信息
     * @return 新添加的乡镇、街道办信息
     */
    public Town addTown(int countryId, int provinceId, int cityId, int districtId, Town town);

    /**
     * 手动删除一个城市区县信息，该操作需谨慎操作
     * @param districtId 待删除的城市区县信息ID
     */
    public void deleteTown(long townId);

    /**
     * 清除所有的乡镇、街道办信息，该操作需谨慎操作
     */
    public void clearTown();
    
    /**
     * 修改城市区县信息
     * @param district 待修改的城市区县信息
     * @return 修改后的城市区县信息
     */
    public Town modifyTown(Town town);
    
    /**
     * 根据乡镇、街道办编码获取区县信息
     * @param cityId 乡镇、街道办编码
     */
    public Town getTown(long townId);

    /**
     * 通过乡镇、街道办名称获得乡镇、街道办的ID
     * @param cname 乡镇、街道办名称
     * @return 乡镇、街道办的ID
     */
    public Long getTownId(int districtId, String cname);
    
    /**
     * 获得指定城市下的区县信息列表
     * @param districtId 区县信息ID
     * @return 乡镇、街道办信息列表
     */
    public Iterable<Town> listTowns(int districtId);

    /**
     * 获得指定区县下的乡镇、街道办信息列表
     * @param districtId 区县信息ID
     * @return 乡镇、街道办信息列表
     */
    public Iterable<Town> listTownCodes(int districtId);

    /**
     * 获得所有的区县三级行政单位的数量
     * @return
     */
    public long getTownCount();
    
    /**
     * 获得指定国家下的所有的乡镇、街道办五级行政单位的数量
     * @param countryId 国家编码
     * @return
     */
    public long getTownCountByCountry(Integer countryId);
    
    /**
     * 获得指定省份下的所有的乡镇、街道办五级行政单位的数量
     * @param provinceId 二级行政单位省份编码
     * @return
     */
    public long getTownCountByProvince(Integer provinceId);
    
    /**
     * 获得指定城市下的所有的乡镇、街道办五级行政单位的数量
     * @param cityId 三级行政单位城市编码
     * @return
     */
    public long getTownCountByCity(Integer cityId);
    
    /**
     * 获得指定区县下的所有的乡镇、街道办五级行政单位的数量
     * @param districtId 四级行政单位区县编码
     * @return
     */
    public long getTownCountByDistrict(Integer districtId);
    
    /**
     * 通过国家名称获得省份列表
     * @param countryName 国家名称
     * @return
     */
    public Iterable<Province> listProvinceByCountryName(String countryName);
}
