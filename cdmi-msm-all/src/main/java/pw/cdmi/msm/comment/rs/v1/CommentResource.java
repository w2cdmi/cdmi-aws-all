package pw.cdmi.msm.comment.rs.v1;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pw.cdmi.msm.comment.rs.CommentRequest;
import pw.cdmi.msm.comment.rs.ListCommentResponse;

@Path("/comments/v1")
public interface CommentResource {
	
	public static final int VIEW_MAX_COMMENT_NUMBER = 20;
	
	/**
	 * 为某个信息进行評論
	 */
	@POST
	@Path("")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void comment(CommentRequest comment);

	/**
	 * 获取某个信息所获得的評論数
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	@Path("/{target_id}/amount?type={target_type}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public int getCommentAmount(@PathParam("target_id") String id, @QueryParam("target_type") String type);

	/**
	 * 获取某个信息所获得的評論列表
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	@Path("/{target_id}/praiser?type={target_type}&limit={max_number}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<ListCommentResponse> listComment(@PathParam("target_id") String id,
			@QueryParam("target_type") String type);
}
