package pw.cdmi.msm.catalogs.service.impl;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import pw.cdmi.core.db.RelationDeleteEvent;
import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.ClientReason;
import pw.cdmi.core.http.exception.GlobalHttpClientError;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.catalogs.model.SupportItemType;
import pw.cdmi.msm.catalogs.model.entities.Catalog;
import pw.cdmi.msm.catalogs.repositories.CatalogRepository;
import pw.cdmi.msm.catalogs.service.CatalogService;

@Component
@Transactional
public class CatalogServiceImpl implements CatalogService {

	@Autowired
	private CatalogRepository catalogRepository;

	@Override
	public String createCatalog(Catalog catalog, String appId) {
		if (StringUtils.isBlank(catalog.getParentId())) {
			// 检查父对象是否存在
			Catalog parent = this.getCatalog(catalog.getParentId(), appId);
			if (parent == null) {
				throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
			}
		}
		catalog.setAppId(appId);
		catalog = catalogRepository.save(catalog);
		if (catalog == null) {
			throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
		}
		return catalog.getId();
	}

	@Override
	public void updateCatalog(Catalog catalog, String appId) {
		if (StringUtils.isBlank(catalog.getId())) {
			throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
		}
		Catalog old = this.getCatalog(catalog.getId(), appId);
		if (!StringUtils.isBlank(catalog.getName())) {
			old.setName(catalog.getName());
		}
		if (!StringUtils.isBlank(catalog.getIconImage())) {
			old.setIconImage(catalog.getIconImage());
		}
		if (!StringUtils.isBlank(catalog.getSummary())) {
			old.setSummary(catalog.getSummary());
		}
		if (!StringUtils.isBlank(catalog.getParentId())) {
			Catalog parent = this.getCatalog(catalog.getParentId(), appId);
			if (parent == null) {
				throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
			}
			old.setParentId(catalog.getParentId());
		}
		try {
			catalogRepository.save(old);
		} catch (Exception e) {
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

	@Override
	public void deleteCatalog(String id, SupportItemType itemtype, RelationDeleteEvent event, String appId) {
		try {
			// FIXME
			if (RelationDeleteEvent.CLEAR.equals(event)) {
				// TODO 从子对象表中将起类型值删除掉
			}
			catalogRepository.delete(id);
		} catch (Exception e) {
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

	@Override
	public Catalog getCatalog(String id, String appId) {
		try {
			return catalogRepository.findOne(id);
		} catch (Exception e) {
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

	@Override
	public Iterable<Catalog> listCatalogs(int cursor, int maxcount, Sort.Direction direction, String appId) {
		Sort.Order order = new Sort.Order(direction, "sortValue");
		Sort sort = new Sort(order);
		Pageable pageRequest = new PageRequest(cursor, maxcount, sort);
		try {
			return catalogRepository.findAllByAppId(pageRequest, appId);
		} catch (Exception e) {
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

	@Override
	public float changeSortNumber(String id, String target_id, String target2_id, String appId) {
		if (!StringUtils.isBlank(id) || StringUtils.isBlank(target_id)) {
			throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
		}
		try {
			Catalog current = catalogRepository.findOne(id);

			if (current == null) {
				throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
			}

			Catalog target = catalogRepository.findOne(target_id);
			if (target == null) {
				throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
			}

			Catalog catalog2 = null;

			if (!StringUtils.isBlank(target2_id)) {
				catalog2 = catalogRepository.findOne(id);
				float current_sortvalue = current.getSortValue();
				float target_sortvalue = target.getSortValue();
				float target2_sortvalue = catalog2.getSortValue();
				current_sortvalue = (target_sortvalue + target2_sortvalue) / 2;
				current.setSortValue(current_sortvalue);
				catalogRepository.save(current);
			} else {
				// 用于交换的临时变量
				float sortvalue = current.getSortValue();
				current.setSortValue(target.getSortValue());
				target.setSortValue(sortvalue);
				catalogRepository.save(current);
				catalogRepository.save(target);
			}
			return current.getSortValue();
		} catch (Exception e) {
			throw new AWSServiceException(SystemReason.SQLError);
		}
	}

}
