package com.lester.core.service.impl;

import com.lester.config.SysConst;
import com.lester.core.dao.ICfgSysConfigDao;
import com.lester.core.model.CfgSystemConfig;
import com.lester.core.service.ITicketCommonService;
import com.lester.core.viewModel.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketCommonServiceImpl implements ITicketCommonService{


	@Autowired
	private ICfgSysConfigDao cfgSysConfigDao;
	
	@Override
	public List<CfgSystemConfig> getCityItems() throws Exception {
		List<CfgSystemConfig> items = cfgSysConfigDao.queryByCodeCate(SysConst.CodeCate.CITY);
		return items;
	}

	@Override
	public Map<Long, List<CfgSystemConfig>> getTownItems() throws Exception {
		Map<Long, List<CfgSystemConfig>> allItems = new HashMap<Long, List<CfgSystemConfig>>();
		List<CfgSystemConfig> items = cfgSysConfigDao.queryByCodeCate(SysConst.CodeCate.TOWN);
		if(items != null && items.size() > 0){
			for(CfgSystemConfig item : items){
				if(item != null){
					Long key = item.getParentId();
					List<CfgSystemConfig> tempItems = allItems.get(key);
					if(tempItems == null){
						tempItems = new ArrayList<>();
					}
					tempItems.add(item);
					allItems.put(key, tempItems);
				}
			}
		}
		return allItems;
	}

	@Override
	public List<CfgSystemConfig> getCaseTypeItems() throws Exception {
		List<CfgSystemConfig> items = cfgSysConfigDao.queryByCodeCate(SysConst.CodeCate.CASE_TYPE);
		return items;
	}

	@Override
	public List<CfgSystemConfig> getCaseGroupItems() throws Exception {
		List<CfgSystemConfig> items = cfgSysConfigDao.queryByCodeCate(SysConst.CodeCate.CASE_GROUP);
		return items;
	}

	@Override
	public List<CfgSystemConfig> getStatusItems() throws Exception {
		List<CfgSystemConfig> items = cfgSysConfigDao.queryByCodeCate(SysConst.CodeCate.STATUS);
		return items;
	}

	@Override
	public Map<Long, List<CfgSystemConfig>> getSubStatusItems() throws Exception {
		Map<Long, List<CfgSystemConfig>> allItems = new HashMap<Long, List<CfgSystemConfig>>();
		List<CfgSystemConfig> items = cfgSysConfigDao.queryByCodeCate(SysConst.CodeCate.SUB_STATUS);
		if(items != null && items.size() > 0){
			for(CfgSystemConfig item : items){
				if(item != null){
					Long key = item.getParentId();
					List<CfgSystemConfig> tempItems = allItems.get(key);
					if(tempItems == null){
						tempItems = new ArrayList<>();
					}
					tempItems.add(item);
					allItems.put(key, tempItems);
				}
			}
		}
		return allItems;
	}

	@Override
	public List<CfgSystemConfig> getFileTypeItems() throws Exception {
		List<CfgSystemConfig> items = cfgSysConfigDao.queryByCodeCate(SysConst.CodeCate.FILE_TYPE);
		return items;
	}

	@Override
	public List<Option<Long, String>> getYearItems() throws Exception {
		Long yearRangeUpper = 1L; //預設年度區間上限(預設1)
		Long yearRangeLower = 3L; //預設年度區間下限(預設3)

		List<CfgSystemConfig> defaultConfigs = cfgSysConfigDao.queryByCodeCate(SysConst.CodeCate.DEFAULT_CONFIG);
		if(defaultConfigs != null){
			for(CfgSystemConfig defaultConfig : defaultConfigs){
				String code = defaultConfig.getCode();
				String value = defaultConfig.getCodeValue();
				if(value != null){
					if (SysConst.Code.DEFAULT_CONFIG_YEAR_RANGE_UPPER.equals(code)) {
						//預設年度區間上限
						yearRangeUpper = Long.valueOf(value);
					} else if (SysConst.Code.DEFAULT_CONFIG_YEAR_RANGE_LOWER.equals(code)) {
						//預設年度區間下限
						yearRangeLower = Long.valueOf(value);
					}
				}
			}
		}
		
		//年度
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int startYear = year - yearRangeUpper.intValue();
		int endYear = year + yearRangeLower.intValue();
		List<Option<Long, String>> yearItems = new ArrayList<>();
		for(int i=startYear ; i<=endYear ; i++){
			int chinayear = i-1911;
			yearItems.add(new Option<>(new Long(i), chinayear + "年度"));
		}
		
		return yearItems;
	}

	@Override
	public List<Option<Long, Long>> getSeasonItems() throws Exception {

		Long seasonCount = 5L; //預設期別數量(預設5)
		List<CfgSystemConfig> defaultConfigs = cfgSysConfigDao.queryByCodeCate(SysConst.CodeCate.DEFAULT_CONFIG);
		if(defaultConfigs != null){
			for(CfgSystemConfig defaultConfig : defaultConfigs){
				String code = defaultConfig.getCode();
				String value = defaultConfig.getCodeValue();
				if(value != null){
					if (SysConst.Code.DEFAULT_CONFIG_SEASON_COUNT.equals(code)) {
						//預設期別數量
						seasonCount = Long.valueOf(value);
					}
				}
			}
		}
		
		//期別
		List<Option<Long, Long>> seasonItems = new ArrayList<>();
		for(int i=1 ; i<=seasonCount ; i++){
			seasonItems.add(new com.lester.core.viewModel.Option<>(new Long(i), new Long(i)));
		}
		return seasonItems;
	}	
	
}
