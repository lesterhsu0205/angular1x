package com.lester.core.dao;

import java.util.List;

import com.lester.core.model.CfgSystemConfig;
import com.lester.core.model.StatusDefault;

public interface ICfgSysConfigDao {

	public List<CfgSystemConfig> queryByCodeCate(String codeCate) throws Exception;
	
	public StatusDefault queryDefaultStatus() throws Exception;
	
}
