package pw.cdmi.msm.user.rs.v1;


import javax.ws.rs.Encoded;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;


/**
 * **********************************************************
 * <br/>
 * TODO.控制类，提供对站点用户信息的一些操作<br/>
 * 
 * @author Administrator
 * @version cdm Service Platform, 2016年5月27日
 ***********************************************************
 */

@Path("/v1/siteUser")
@Encoded
@Component("siteUserResource")
public class SiteUserResource {
    private static Log log = LogFactory.getLog(SiteUserResource.class);

    @Path("/")
    @POST
    public String creatSiteUser(@PathParam("username") String username){
        
        return username;
        
    }
    
    
}
