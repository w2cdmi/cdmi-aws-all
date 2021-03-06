package pw.cdmi.paas.developer.service;

import pw.cdmi.paas.developer.model.entities.Developer;

public interface DeveloperService {
	/**
	 * 创建开发者账号
	 * @param developer
	 * @return
	 */
	public String createDeveloper(Developer developer);
	/**
	 * 用id查找开发者
	 * @param developerId
	 * @return
	 */
	public Developer findDeveloperById(String developerId);
}
