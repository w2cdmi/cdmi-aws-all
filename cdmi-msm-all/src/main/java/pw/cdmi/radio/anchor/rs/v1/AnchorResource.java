package pw.cdmi.radio.anchor.rs.v1;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pw.cdmi.radio.anchor.rs.ListAnchorRespone;

@Path("/anchors/v1")
public interface AnchorResource {
	
	/**
	 * 获取当前用户关注的主播列表
	 */
    @POST
    @Path("/focus")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public  List<ListAnchorRespone> listFocusAnchors();
}
