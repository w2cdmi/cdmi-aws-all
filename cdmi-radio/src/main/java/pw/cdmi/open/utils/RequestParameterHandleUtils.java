package pw.cdmi.open.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.msm.geo.ClientReason;
import pw.cdmi.open.ClientError;

@Component
public class RequestParameterHandleUtils {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public <T> T convertRequestParams2Entity(T entity, String params) {
        try {
            params = URLDecoder.decode(params, "UTF-8");
            Map<String, String> map = convertSting2Map(params);

            Class<?> c = entity.getClass();
            Field[] field = c.getDeclaredFields();
            String name = null;
            String type = null;
            for (int i = 0; i < field.length; i++) {
                name = field[i].getName();
                type = field[i].getGenericType().toString();
                Type type1 = field[i].getGenericType();
                field[i].setAccessible(true);  // 可访问私有变量

                if (type.equals("class java.lang.String")) {
                    field[i].set(entity, (String) map.get(name));
                } else if (type.equals("class java.util.Date")) {
                    if (StringUtils.isNotBlank((String) map.get(name))) {
                        field[i].set(entity, dateFormat.parse((String) map.get(name)));
                    }
                } else if (type.equals("class java.lang.Integer")) {
                    if (StringUtils.isBlank((map.get(name)))) {
                        field[i].set(entity, null);
                    }else{
                        field[i].set(entity, Integer.parseInt((String) map.get(name)));
                    }
                } else if (type.equals("class java.lang.Short")) {
                    field[i].set(entity, Short.parseShort((String) map.get(name)));
                } else if (type.equals("class java.lang.Float")) {
                    field[i].set(entity, Float.parseFloat((String) map.get(name)));
                } else if (type.equals("class java.lang.Double")) {
                    field[i].set(entity, Double.parseDouble((String) map.get(name)));
                } else if (type.equals("class java.lang.Boolean")) {
                    field[i].set(entity, map.get(name));
                } else if (type.equals("byte")) {
                    if (map.get(name) != null) {
                        field[i].set(entity, Byte.parseByte((String) map.get(name)));
                    }
                } else {
                    Class<?> cs = (Class<?>) type1;
                    Object[] t = cs.getEnumConstants();
                    for (int j = 0; j < t.length; j++) {
                        if (!(map.get(name) == null) && map.get(name).equals(t[j].toString())) {
                            field[i].set(entity, t[j]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        return entity;
    }

    private Map<String, String> convertSting2Map(String str) {
        Map<String, String> map = new HashMap<String, String>();
        String[] params = str.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] arr = params[i].split("=");
            if (arr.length > 1) {
                map.put(arr[0], arr[1]);
            }
        }
        return map;
    }

}
