package pw.cdmi.aws.geo.rs.v1;

import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.ApiResponse;

@RestSchema(schemaId = "geoMgrResource")
@RequestMapping(path = "/v1/xzqh", produces = MediaType.APPLICATION_JSON)
public class GeoMgrResource {

//	@PostMapping(path = "buyWithoutTransaction")
//    @ApiResponse(code = 400, response = String.class, message = "")
//    public boolean buyWithoutTransaction(@RequestParam(name = "userId") long userId,
//            @RequestParam(name = "price") double price) {
//        UserInfo info = userMapper.getUserInfo(userId);
//        if (info == null) {
//            throw new InvocationException(400, "", "user id not valid");
//        }
//        if (info.getTotalBalance() < price) {
//            throw new InvocationException(400, "", "user do not got so mush money");
//        }
//        info.setTotalBalance(info.getTotalBalance() - price);
//        userMapper.updateUserInfo(info);
//        return true;
//    }
}
