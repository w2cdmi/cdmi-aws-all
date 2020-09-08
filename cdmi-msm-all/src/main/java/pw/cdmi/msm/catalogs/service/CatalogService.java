package pw.cdmi.msm.catalogs.service;

import org.springframework.data.domain.Sort;

import pw.cdmi.core.db.RelationDeleteEvent;
import pw.cdmi.msm.catalogs.model.SupportItemType;
import pw.cdmi.msm.catalogs.model.entities.Catalog;

public interface CatalogService {

	/**
	 * 
	 * 向目录微服务中添加一条目录信息
	 * 
	 * @param alarm
	 *            待添加的目录信息
	 */
	public String createCatalog(Catalog catalog, String appId);

	/**
	 * 
	 * 更新录信息.
	 *
	 * @param alarm
	 *            待更新的目录信息
	 */
	public void updateCatalog(Catalog catalog, String appId);

	/**
	 * 
	 * 删除目录信息.
	 *
	 * @param id
	 *            待删除的目录信息编号
	 */
	public void deleteCatalog(String id, SupportItemType itemtype, RelationDeleteEvent event, String appId);

	/**
	 * 
	 * 获得指定的目录信息.
	 *
	 * @param id
	 *            指定的目录信息编号
	 * @return 目录信息
	 */
	public Catalog getCatalog(String id, String appId);

	/**
	 * 
	 * 获得目录列表.
	 *
	 * @param cursor
	 *            要获取的目录列表的起始行数
	 * @param maxcount
	 *            要获取的目录列表的最大数量
	 * @return 目录列表
	 */
	public Iterable<Catalog> listCatalogs(int cursor, int maxcount, Sort.Direction direction, String appId);
	
	/**
	 * 
	 * 改变指定目录的排序信息.
	 *
	 * @param id
	 *            指定的目录信息编号
	 * @return 目录信息
	 */
	public float changeSortNumber(String id, String target_id, String target2_id, String appId);
}
