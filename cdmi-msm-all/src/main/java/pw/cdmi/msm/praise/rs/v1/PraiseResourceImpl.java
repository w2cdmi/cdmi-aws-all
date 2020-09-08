package pw.cdmi.msm.praise.rs.v1;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;

import org.apache.commons.lang3.StringUtils;

import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.ClientReason;
import pw.cdmi.core.http.exception.GlobalHttpClientError;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.praise.model.SupportTargetType;
import pw.cdmi.msm.praise.model.entities.Praise;
import pw.cdmi.msm.praise.rs.ListPraiserResponse;
import pw.cdmi.msm.praise.rs.PraiseRequest;

@Path("/praise/v1")
public class PraiseResourceImpl implements PraiseResource {

	@Override
	public void praise(PraiseRequest praise) {
		// 参数合法性检查
		if (praise == null || StringUtils.isBlank(praise.getOwnerId()) || praise.getTarget() == null
				|| StringUtils.isBlank(praise.getTarget().getId())
				|| StringUtils.isBlank(praise.getTarget().getType())) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
		}

		// 检查type是否在支持列表中
		SupportTargetType support_target_type = SupportTargetType.fromName(praise.getTarget().getType());
		if (support_target_type == null) {
			// FIXME 修改为不支持的点赞目标类型
			throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
		}

		// 根据点赞目标类型，判断点赞目标对象是否存在
		switch (support_target_type) {
		case Tenancy_File:
			// 获取租户文件，並鎖定刪除，以及鎖定當前操作用戶刪除
			
			// 如果租戶文件存在，新增一個點贊信息，釋放鎖定刪除
			break;
		case Comment:
			// 获取评论信息，並鎖定刪除，以及鎖定當前操作用戶刪除
			
			// 如果评论存在，新增一個點贊信息，釋放鎖定刪除
			break;
		case Tenancy_User:
			// 获取租户用户，並鎖定刪除，以及鎖定當前操作用戶刪除
			
			// 如果租戶用戶存在，新增一個點贊信息，釋放鎖定刪除
			break;
		default:
			throw new AWSServiceException(SystemReason.UnImplemented);
		}

	}

	@Override
	public int getPraiseAmount(String id, String type) {
		// 参数合法性检查
		if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
		}
		
		// 根據目標ID和類型，在點贊記錄表中查找符合條件的記錄
		Praise praise = null;
		
		// 點贊記錄是否存在，存在獲取其數量，不存在返回零
		return 0;
	}

	@Override
	public List<ListPraiserResponse> listPraiser(String id, String type,int maxNumber) {
		// 参数合法性检查
		if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
		}
		// 如果沒有設置最大顯示點贊人數，則讀取系統默認配置
		if(maxNumber == 0) {
			maxNumber = VIEW_MAX_PRAISE_NUMBER;
		}
		
		// 根據目標ID和類型，在點贊記錄表中查找符合條件的記錄
		List<String> praiser_ids = new ArrayList<String>();
		
		// 根據返回的點贊者ID列表，獲取點贊者的名稱，頭像等信息
//		List<SiteAccount> praisers = new ArrayList<SiteAccount>();
		
		// 將返回的點贊者信息，削減后返回前端
		List<ListPraiserResponse> list = null;
		return list;
	}

}
