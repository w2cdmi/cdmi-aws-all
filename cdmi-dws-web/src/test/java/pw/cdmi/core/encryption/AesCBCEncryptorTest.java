
/*
 * 版权声明(Copyright Notice)：
 * Copyright(C) 2017-2018 聚数科技成都有限公司。保留所有权利。
 * Copyright(C) 2017-2018 www.cdmi.pw Inc. All rights reserved.
 * 警告：本内容仅限于聚数科技成都有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package pw.cdmi.core.encryption;

import org.junit.Assert;

/************************************************************
 * @Description:
 * <pre>AesCBCEncryptor测试类</pre>
 * @author Rox
 * @version 3.0.1
 * @Project Alpha CDMI Service Platform, cdmi-dws-web Component. 2018/4/1
 ************************************************************/

//@RunWith(JUnit4.class)
public class AesCBCEncryptorTest {
//    @Test

    public static void main(String[] args) {
        AesCBCEncryptor encryptor = new AesCBCEncryptor();

        String decode = encryptor.decrypt("d2NjX2NyeXB0ATQxNDU1MzVGNDM0MjQzOzMzMzM0NDMyMzM0MzMwNDY0MzM3MzUzMjQxMzczNTQyNDUzMTMyNDQ0NjQ2MzkzNjQxMzAzOTMyMzYzODM0Mzc7OzM1MzAzMDMwMzA7NzM5NkE3NkQyODQ4RkY5RjQ1MDYxQTg0QzczMTlGMjc7RTU2ODA0NTgxMjJGNzQzNDs,d2NjX2NyeXB0ATQxNDU1MzVGNDM0MjQzOzQyNDQ0MzMzMzkzMDMxNDE0NDMyNDUzNDMyMzYzMzM2MzIzMDM5NDQzODM0NDUzMjM4NDQzODM2MzczMjM1MzUzMDM1MzM0MzMwMzUzMTQyMzUzMzMyNDUzMjM3MzAzNDM0NDYzMDMwMzQ0MTQ0NDIzMjQzMzQ0MzQ2MzU0NTMzMzI0NTM0Mzg0MzM2NDE0NTM3NDYzODQxNDYzMDQ1MzU0MjM0MzUzMDMwMzczMzQ0Mzg0NjQxNDMzNzM5NDU0MTszMTM1MzAzMTM1MzczNDMyMzgzMzM5MzQzMjszNTMwMzAzMDMwO0RBOUE1OEY2RDY5Q0Q3MjlEOTRCMzYyNDk0NDNCOTQ4Ow");
        System.out.println(decode);
        Assert.assertNotNull(decode);
    }
}
