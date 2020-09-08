package pw.cdmi.msm.praise.rs.v1;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pw.cdmi.msm.praise.rs.ListPraiserResponse;
import pw.cdmi.msm.praise.rs.PraiseRequest;

@Path("/praise/v1")
public interface PraiseResource {
	
	public static final int VIEW_MAX_PRAISE_NUMBER = 50;
	
	/**
	 * 为某个信息进行点赞
	 */
	@POST
	@Path("")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void praise(PraiseRequest praise);

	/**
	 * 获取某个信息所获得的点赞数
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	@Path("/{target_id}/amount?type={target_type}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public int getPraiseAmount(@PathParam("target_id") String id, @QueryParam("target_type") String type);

	/**
	 * 获取某个信息所获得的点赞人员列表
	 * 
	 * @param id
	 * @param type
	 * @param maxNumber 最大顯示點贊人員數量
	 * @return
	 */
	@Path("/{target_id}/praiser?type={target_type}&limit={max_number}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<ListPraiserResponse> listPraiser(@PathParam("target_id") String id,
			@QueryParam("target_type") String type, @QueryParam("max_number") int maxNumber);
}
