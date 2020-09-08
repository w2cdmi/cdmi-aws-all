package pw.cdmi.msm.catalogs.rs.v1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pw.cdmi.core.db.RelationDeleteEvent;
import pw.cdmi.core.db.SortStyle;
import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.core.http.exception.ClientReason;
import pw.cdmi.core.http.exception.GlobalHttpClientError;
import pw.cdmi.msm.catalogs.model.SupportItemType;
import pw.cdmi.msm.catalogs.model.entities.Catalog;
import pw.cdmi.msm.catalogs.rs.ListCatalogResponse;
import pw.cdmi.msm.catalogs.service.CatalogService;

@RestController
@RequestMapping("/catalogs/v1")
public class CatalogResource {

	private static Logger logger = LoggerFactory.getLogger(CatalogResource.class);

	@Autowired
	private CatalogService service;

	/**
	 * 创建一个新的目录
	 * 
	 * @param praise
	 * @return
	 */
	@PostMapping(value = "/new")
	public @ResponseBody String createNew(@RequestBody Catalog newcatalog) {
		if (newcatalog == null || StringUtils.isBlank(newcatalog.getName())) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
		}
		String appId = null;
		return service.createCatalog(newcatalog, appId);
	}

	/**
	 * 列举目录列表
	 * 
	 * @param praise
	 * @return
	 */
	@GetMapping(value = "")
	public @ResponseBody Iterable<ListCatalogResponse> listCatalogs(int cursor, int maxcount, String direction) {
		String appId = null;

		Sort.Direction o_direction = null;
		try {
			o_direction = Sort.Direction.fromString(direction);
		} catch (IllegalArgumentException ex) {
			throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
		}

		List<ListCatalogResponse> response = new ArrayList<ListCatalogResponse>();

		Iterable<Catalog> result = service.listCatalogs(cursor, maxcount, o_direction, appId);
		ListCatalogResponse item = null;
		for (Iterator<Catalog> iter = result.iterator(); iter.hasNext();) {
			Catalog catalog = iter.next();
			item = new ListCatalogResponse();
			item.setId(catalog.getId());
			item.setIcon_image(catalog.getIconImage());
			item.setName(catalog.getName());
			response.add(item);
		}
		return response;
	}

	/**
	 * 删除一个指定目录
	 * 
	 * @param
	 * @return
	 */
	@DeleteMapping(value = "/{catalog_id}")
	public @ResponseBody void deleteCatalog(@PathVariable("catalog_id") String id,
			@RequestParam("itemtype") String s_itemtype, @RequestParam("event_type") String s_eventtype) {
		String appId = null;

		if (StringUtils.isBlank(id)) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
		}

		SupportItemType item_type = null;
		if (!StringUtils.isBlank(s_itemtype)) {
			// 转化为支持的子对象枚举类型
			try {
				item_type = SupportItemType.fromString(s_itemtype);
			} catch (IllegalArgumentException ex) {
				throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
			}
		}

		RelationDeleteEvent event = RelationDeleteEvent.FORBIDWITHDATA;
		if (!StringUtils.isBlank(s_eventtype)) {
			// 判断类型
			try {
				event = RelationDeleteEvent.fromString(s_eventtype);
			} catch (IllegalArgumentException ex) {
				throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
			}
		}
		service.deleteCatalog(id, item_type, event, appId);
	}

	/**
	 * 对目录进行排序
	 * 
	 * @param
	 * @return
	 */
	@PutMapping(value = "/{catalog_id}/order")
	public @ResponseBody float orderCatalog(@PathVariable("catalog_id") String id,
			String target_id) {
		String appId = null;
		if (StringUtils.isBlank(id) || StringUtils.isBlank(target_id)) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
		}

//		SortStyle style = SortStyle.Exchange;
//		if (!StringUtils.isBlank(s_sortstyle)) {
//			try {
//				style = SortStyle.fromString(s_sortstyle);
//			} catch (IllegalArgumentException ex) {
//				throw new AWSClientException(GlobalClientError.InvalidParameter, ClientReason.InvalidParameter);
//			}
//		}
		return service.changeSortNumber(id, target_id, null, appId);
	}
}
