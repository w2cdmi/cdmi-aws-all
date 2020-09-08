
/*
 * 版权声明(Copyright Notice)：
 * Copyright(C) 2017-2018 聚数科技成都有限公司。保留所有权利。
 * Copyright(C) 2017-2018 www.cdmi.pw Inc. All rights reserved.
 * 警告：本内容仅限于聚数科技成都有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package pw.cdmi.core.encryption;

import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.wcc.crypt.Crypter;
import org.wcc.crypt.CrypterFactory;

/************************************************************
 * @Description:
 * <pre>适配SpringBoot, 自定义配置项解密方法</pre>
 * @author Rox
 * @version 3.0.1
 * @Project Alpha CDMI Service Platform, cdmi-dws-web Component. 2018/4/1
 ************************************************************/
public class AesCBCEncryptor implements StringEncryptor {
    @Override
    public String encrypt(String s) {
        throw new IllegalStateException("This method is not supported yet.");
    }

    @Override
    public String decrypt(String s) {
        //配置项格式为 value,key， 要求key和value中不能含有逗号(,)
        int index = s.indexOf(",");
        if(index == -1) {
            throw new IllegalStateException(String.format("Required Encryption property is invalid, need value and key: %s", s));
        }

        String value = s.substring(0, index);
        if(StringUtils.isBlank(value)) {
            throw new IllegalStateException(String.format("Required Encryption value is blank string: %s", s));
        }

        String key = s.substring(index + 1);
        if(StringUtils.isBlank(key)) {
            throw new IllegalStateException(String.format("Required Encryption Key is blank string: %s", s));
        }

        Crypter crypter = CrypterFactory.getCrypter(CrypterFactory.AES_CBC);
        String realKey = crypter.decryptByRootKey(key);
        return crypter.decrypt(value, realKey);
    }
}
