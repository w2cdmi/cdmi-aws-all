package pw.cdmi.msm.geo.rs.v1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.cdmi.core.http.Headers;
import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.core.http.rs.AbstractResource;
import pw.cdmi.msm.geo.ClientError;
import pw.cdmi.msm.geo.ClientReason;
import pw.cdmi.msm.geo.model.entity.City;
import pw.cdmi.msm.geo.model.entity.Country;
import pw.cdmi.msm.geo.model.entity.District;
import pw.cdmi.msm.geo.model.entity.Province;
import pw.cdmi.msm.geo.model.entity.Town;
import pw.cdmi.msm.geo.service.GeoService;

@Path("/v1/xzqh")
@Encoded
@Service("geoResource")
public class GeoResource extends AbstractResource {
    private static final String VERSION = "1.0";

    @Autowired
    private GeoService geoService;

    private static Connection conn = null;

    private static PreparedStatement pstmt = null;

    private static ResultSet getResultFormMysql(String sql) {
        String url = "jdbc:mysql://localhost:3306/db_cdmi";
        String username = "cdmi";
        String password = "123456";
        try {
            // 加载MySql的驱动类
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到驱动程序类 ，加载驱动失败！");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException se) {
            System.out.println("数据库连接失败！");
            se.printStackTrace();
        }

        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            return rs;
            // int col = rs.getMetaData().getColumnCount();
            // System.out.println("============================");
            // while (rs.next()) {
            // for (int i = 1; i <= col; i++) {
            // System.out.print(rs.getString(i) + "\t");
            // if ((i == 2) && (rs.getString(i).length() < 8)) {
            // System.out.print("\t");
            // }
            // }
            // System.out.println("");
            // }
            // System.out.println("============================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // @After
    private static void closeJdbc(ResultSet result) {
        try {
            if (result != null) {
                result.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获得指定国家信息
     * 
     * @return
     */
    @GET
    @Path("/country/mysql2mongo")
    public Response mysql2mongo1() throws Exception {
        String sql = "select * from c_country";
        ResultSet result = getResultFormMysql(sql);
        Country country = null;
        while (result.next()) {
            country = new Country();
            country.setOpenId(result.getInt("open_id"));
            country.setDomainName(result.getString("domain_name"));
            country.setEnglishName(result.getString("english_name"));
            country.setHasSovereignty(result.getInt("has_sovereignty") == 0 ? true : false);
            country.setName(result.getString("name"));
            country.setPhoneCode(result.getInt("phone_code"));
            country.setTimeZone(result.getInt("time_zone"));
            geoService.addCountry(country);
        }
        // 打印保存到mongodb的记录数量
        // tm.commit();
        closeJdbc(result);
        return Response.ok().header(Headers.CDMI_VERSION, VERSION).build();
    }

    @GET
    @Path("/province/mysql2mongo")
    public Response mysql2mongo2() throws Exception {
        String sql = "select * from c_province";
        ResultSet result = getResultFormMysql(sql);
        Province province = null;
        while (result.next()) {
            province = new Province();
            province.setOpenId(result.getInt("open_id"));
            province.setAbbreviation(result.getString("abbreviation"));
            province.setFullName(result.getString("full_name"));
            province.setName(result.getString("name"));
            province.setPinyin(result.getString("pinyin"));
            geoService.addProvince(result.getInt("country"), province);
        }
        // 打印保存到mongodb的记录数量
        // tm.commit();
        closeJdbc(result);
        return Response.ok().header(Headers.CDMI_VERSION, VERSION).build();
    }

    @GET
    @Path("/city/mysql2mongo")
    public Response mysql2mongo3() throws Exception {
        String sql = "select * from c_city";
        ResultSet result = getResultFormMysql(sql);
        City city = null;
        while (result.next()) {
            city = new City();
            city.setOpenId(result.getInt("open_id"));
            city.setFullName(result.getString("full_name"));
            city.setName(result.getString("name"));
            city.setTrunkCode(result.getInt("trunk_code"));
            geoService.addCity(result.getInt("country"), result.getInt("province"), city);
        }
        // 打印保存到mongodb的记录数量
        // tm.commit();
        closeJdbc(result);
        return Response.ok().header(Headers.CDMI_VERSION, VERSION).build();
    }

    @GET
    @Path("/district/mysql2mongo")
    public Response mysql2mongo4() throws Exception {
        String sql = "select * from c_district";
        ResultSet result = getResultFormMysql(sql);
        District district = null;
        while (result.next()) {
            district = new District();
            district.setOpenId(result.getInt("open_id"));
            district.setName(result.getString("name"));
            district.setPostcode(result.getInt("postcode"));
            geoService.addDistrict(result.getInt("country"), result.getInt("province"), result.getInt("city"),
                district);
        }
        // 打印保存到mongodb的记录数量
        // tm.commit();
        closeJdbc(result);
        return Response.ok().header(Headers.CDMI_VERSION, VERSION).build();
    }

    @GET
    @Path("/town/mysql2mongo")
    public Response mysql2mongo5() throws Exception {
        String sql = "select * from c_town";
        ResultSet result = getResultFormMysql(sql);
        Town town = null;
        while (result.next()) {
            town = new Town();
            town.setOpenId(result.getLong("openId"));
            town.setName(result.getString("name"));
            geoService.addTown(result.getInt("countryId"), result.getInt("provinceId"), result.getInt("cityId"),
                result.getInt("districtId"), town);
        }
        // 打印保存到mongodb的记录数量
        // tm.commit();
        closeJdbc(result);
        return Response.ok().header(Headers.CDMI_VERSION, VERSION).build();
    }

    /**
     * 获得指定国家信息
     * 
     * @return
     */
    @GET
    @Path("/country/{openId}")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Country getCountry(@PathParam("openId") int openId) {
        if (openId < 100 || openId > 900) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range);
        }
        Country country = geoService.getCountry(openId);
        return country;
    }

    /**
     * 获得国家列表,默认只限制主权国家
     * 
     * @return 国家信息列表
     */
    @GET
    @Path("/counties")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Iterable<Country> listCountries() {
        MultivaluedMap<String, String> param = ui.getQueryParameters();
        String sovereignty = param.getFirst("s");// 参数s表示是否显示主权国家，s=0，1。
        int v = 0;
        if (StringUtils.isNotBlank(sovereignty)) {
            try {
                v = Integer.valueOf(sovereignty);
            } catch (Exception ex) {
            }
        }
        boolean show = (v == 0) ? true : false; // 0是只显示主权国家，其他数字表示显示所有
        Iterable<Country> countries = geoService.listCountries(show); // 只显示主权国家
        // List<Country> ls_countries = new ArrayList<Country>();
        //
        // while(countries.iterator().hasNext()) {
        // ls_countries.add(countries.iterator().next());
        // }
        // GenericEntity<List<Country>> entities = new
        // GenericEntity<List<Country>>(ls_countries) {};
        return countries;
    }

    /**
     * 获得国家编码列表,默认只限制主权国家的国家编码
     * 
     * @return 国家编码列表，仅包含{openId,name}
     */
    @GET
    @Path("/counties/name")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public List<Country> listCountryNames() {
        MultivaluedMap<String, String> param = ui.getQueryParameters();
        String sovereignty = param.getFirst("s");// 参数s表示是否显示主权国家，s=0，1。
        int v = 0;
        if (StringUtils.isNotBlank(sovereignty)) {
            try {
                v = Integer.valueOf(sovereignty);
            } catch (Exception ex) {
            }
        }
        boolean show = (v == 0) ? true : false; // 0是只显示主权国家，其他数字表示显示所有
        Iterable<Country> countries = geoService.listCountryCodes(show); // 只显示主权国家
        List<Country> list = new ArrayList<Country>();

        Iterator<Country> iterList = countries.iterator();
        while (iterList.hasNext()) {
            list.add(iterList.next());
        }
        return list;
        // JSONArray entities = JSONArray.fromObject(countries);
        // return Response.ok(entities.toString()).header(Headers.CDMI_VERSION, VERSION).build();
    }

    /**
    * 获得指定国家的省份信息列表
    * 
    * @return
    */
    @GET
    @Path("/counties/count")
    public long getCountryCount() {
        long count = geoService.getCountryCount();
        return count;
    }

    /**
     * 获得指定的省份信息
     * 
     * @return
     */
    @GET
    @Path("/province/{openId}")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Province getProvince(@PathParam("openId") int openId) {
        if (openId < 10000 || openId > 90000) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "openId");
        }
        Province province = geoService.getProvince(openId);
        return province;
    }

    /**
     * 获得指定国家的省份信息列表
     * 
     * @return
     */
    @GET
    @Path("/provinces")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Iterable<Province> listProvinces() {
        MultivaluedMap<String, String> queryParam = ui.getQueryParameters();
        String str_countryId = queryParam.getFirst("country_id");
        String countryName = queryParam.getFirst("country_name");
        Iterable<Province> provinces = null;
        if (str_countryId != null) {
            try {
                int countryId = Integer.parseInt(str_countryId);
                if (countryId < 100 || countryId > 900) {// 参数的取值范围与服务接口规范不符
                    throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "country_id");
                } else {
                    provinces = geoService.listProvinces(countryId);
                }
            } catch (NumberFormatException ex) {// 参数的数据类型格式与服务接口规范不符
                throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Type_Not_Match, "country_id");
            }
        } else {
            if (countryName != null) {
                provinces = geoService.listProvinceByCountryName(countryName);
            } else {// 参数的取值访问与服务接口规范不符
                throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.MissingMandatoryParameter);
            }
        }

        return provinces;
    }

    /**
     * 获得指定国家的省份信息列表
     * 
     * @return
     */
    @GET
    @Path("/provinces/name")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Iterable<Province> listProvinceNames(@QueryParam("country_id") int countryId) {
        if (countryId < 100 || countryId > 900) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "country_id");
        }

        Iterable<Province> provinces = geoService.listProvinceCodes(countryId);
        return provinces;
    }

    /**
    * 获得指定国家的省份信息列表
    * 
    * @return
    */
    @GET
    @Path("/{countryId}/provinces/count")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public long getProvinceCount() {
        MultivaluedMap<String, String> param = ui.getQueryParameters();
        String countryId = param.getFirst("country_id");
        Integer openId = null;
        if (StringUtils.isNotBlank(countryId)) {
            try {
                openId = Integer.valueOf(countryId);
            } catch (Exception ex) {
            }
        }
        if ((openId != null) && (openId < 100 || openId > 900)) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "country_id");
        }

        long count = geoService.getProvinceCountByCountry(openId);
        return count;
    }

    /**
     * 获得指定的城市信息
     * 
     * @return
     */
    @GET
    @Path("/city/{openId}")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public City getCity(@PathParam("openId") int openId) {
        if (openId < 1000000 || openId > 9000000) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "openId");
        }
        City city = geoService.getCity(openId);
        return city;
    }

    /**
     * 获得指定省份的城市信息列表
     * 
     * @return
     */
    @GET
    @Path("/cities")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Iterable<City> listCities(@QueryParam("province_id") int provinceId) {
        if (provinceId < 10000 || provinceId > 90000) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "province_id");
        }

        Iterable<City> cities = geoService.listCities(provinceId);
        return cities;
    }

    /**
     * 获得指定省份的城市信息列表
     * 
     * @return
     */
    @GET
    @Path("/cities/count")
    public long getCitiesCount(@QueryParam("province_id") int provinceId) {
        if (provinceId < 10000 || provinceId > 90000) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "province_id");
        }

        long count = geoService.getCityCountByProvince(provinceId);
        return count;
    }

    /**
     * 获得指定省份下的城市编码列表
     * 
     * @return
     */
    @GET
    @Path("/cities/name")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Iterable<City> listCityNames(@QueryParam("province_id") int provinceId) {
        if (provinceId < 10000 || provinceId > 90000) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "province_id");
        }

        Iterable<City> cities = geoService.listCityCodes(provinceId);
        return cities;
    }

    /**
     * 获得指定的城市信息
     * 
     * @return
     */
    @GET
    @Path("/district/{openId}")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public District getDistrict(@PathParam("openId") int openId) {
        if (openId < 100000000 || openId > 900000000) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "openId");
        }
        District district = geoService.getDistrict(openId);
        return district;
    }

    /**
     * 获得指定省份的城市信息列表
     * 
     * @return
     */
    @GET
    @Path("/districts")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Iterable<District> listDistricts(@QueryParam("city_id") int cityId) {
        if (cityId < 1000000 || cityId > 9000000) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "city_id");
        }

        Iterable<District> districts = geoService.listDistricts(cityId);
        return districts;
    }

    /**
     * 获得指定省份下的城市编码列表
     * 
     * @return
     */
    @GET
    @Path("/districts/name")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Iterable<District> listDistrictNames(@QueryParam("city_id") int cityId) {
        if (cityId < 1000000 || cityId > 9000000) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "city_id");
        }

        Iterable<District> districts = geoService.listDistrictCodes(cityId);
        return districts;
    }

    /**
     * 获得指定的乡镇、街道办信息
     * 
     * @return
     */
    @GET
    @Path("/town/{openId}")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Town getTown(@PathParam("openId") long openId) {
        if (openId < 10000000000L || openId > 90000000000L) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "openId");
        }
        Town town = geoService.getTown(openId);
        return town;
    }

    /**
     * 获得指定省份的城市信息列表
     * 
     * @return
     */
    @GET
    @Path("/towns")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Iterable<Town> listTowns(@QueryParam("district_id") int districtId) {
        if (districtId < 100000000 || districtId > 900000000) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "district_id");
        }

        Iterable<Town> towns = geoService.listTowns(districtId);
        return towns;
    }

    /**
     * 获得指定区县行政级别下的乡镇街道办名称列表
     * 
     * @return
     */
    @GET
    @Path("/towns/name")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Iterable<Town> listTownNames(@QueryParam("district_id") int districtId) {
        if (districtId < 100000000 || districtId > 900000000) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "district_id");
        }

        Iterable<Town> towns = geoService.listTownCodes(districtId);
        return towns;
    }

    /**
     * 获得所有的省份、州名称列表(中国的行政区划)
     * 
     * @return
     */
    @GET
    @Path("/china/provinces/name")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Iterable<Province> listProvinceNames4China() {
        int countryId = 137;
        Iterable<Province> provinces = geoService.listProvinceCodes(countryId);
        return provinces;
    }

    /**
     * 获得指定省份下的城市名称列表(中国的行政区划)
     * 
     * @return
     */
    @GET
    @Path("/china/cities/name")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Iterable<City> listCityNames4China(@QueryParam("province_id") int provinceId) {
        if (provinceId < 10000 || provinceId > 90000) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "province_id");
        }

        Iterable<City> cities = geoService.listCityCodes(provinceId);
        return cities;
    }

    /**
     * 获得指定城市行政级别下的区县名称列表(中国的行政区划)
     * 
     * @return
     */
    @GET
    @Path("/china/districts/name")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Iterable<District> listDistrictNames4China(@QueryParam("city_id") int cityId) {
        if (cityId < 1000000 || cityId > 9000000) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "city_id");
        }

        Iterable<District> districts = geoService.listDistrictCodes(cityId);
        return districts;
    }

    /**
     * 获得指定区县行政级别下的乡镇街道办名称列表(中国的行政区划)
     * 
     * @return
     */
    @GET
    @Path("/china/towns/name")
    @Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
    public Iterable<Town> listTownNames4China(@QueryParam("district_id") int districtId) {
        if (districtId < 100000000 || districtId > 900000000) {
            throw new AWSClientException(ClientError.NOT_GET_DATA, ClientReason.Over_Range, "district_id");
        }

        Iterable<Town> towns = geoService.listTownCodes(districtId);
        return towns;
    }
}
