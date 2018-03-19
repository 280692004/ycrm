package com.thesys.core.architecture.base.authority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.thesys.architecture.core.context.ApplicationContextHolder;
import com.thesys.base.core.util.Pair;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.sysmag.aclbutton.domain.AclButton;
import com.thesys.base.sysmag.aclbutton.service.AclButtonService;
import com.thesys.base.sysmag.aclright.service.AclRightService;
import com.thesys.core.dto.ButtonDto;
import com.thesys.core.dto.JqGridResultDataDto;

public class BaseIcSuppAuthority {

	@Autowired
	private static AclRightService aclRightService;
	@Autowired
	private static AclButtonService aclButtonService;
	
	
	/**
	 * 基础按钮 新增，修改，删除
	 */
	public static String COMMONBUTS_KEY_OGNL="commonbuts";
	
	public static Map<String, Boolean> commonbutsMap = new HashMap<String, Boolean>();
	static{
		commonbutsMap.put("update", true);
		commonbutsMap.put("delete", true);
	}
	
	/**
	 * 判断用户是否有对应的权限
	 * @return
	 */
	public static Boolean hasRight(String modulecode,String buttoncode){
		String username=SecurityContextHolder.getContext().getAuthentication().getName();		
		return getAclRightService().hasRight(modulecode, buttoncode, username);
	}
	
	/**
	 * 查询当前模块拥有的按钮
	 * @return
	 */
	public static List<AclButton> findCurrentHasButs(String modulecode){
		return getAclButtonService().findAclButtonByModelCode(modulecode);
	}
	
	/**
	 * 初始化按钮
	 * @param object 
	 * @param moduleCode 模块名 与系统创建的模块code保持一致
	 * @param object 新增修改用到的对象主键，如id 还是 naturalI
	 * @param statusFilterMap 状态控制【按钮事件,允许操作的单据状态】 如   【'update',N】
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Pair<List<String>, List<ButtonDto>>> doOpInitButtons(JqGridResultDataDto resultDatas,String moduleCode,String moduleKey,Map<String, String> statusFilterMap){

		Boolean haRightAdd = hasRight(moduleCode, "add");
		resultDatas.setHasAdd(haRightAdd);
		
		ArrayList rows = (ArrayList) resultDatas.getRows();
		if(ValidateUtil.isEmpty(rows)||rows.isEmpty()){
			return null;
		}
		List<AclButton> currentHasButs  = findCurrentHasButs(moduleCode);
		//为了不循环查询数据库 查询一次就记录 当前用户+模块+按钮是否有权限 下一条记录判定是否有权限 直接在这里取值
		Map<String, Boolean> butsAuthority = new HashMap<String, Boolean>();
		//最终需要的有权限的操作按钮集合
		Map<String, Pair<List<String>, List<ButtonDto>>> buts = new HashMap<String, Pair<List<String>, List<ButtonDto>>>();
		
		for (Object entity : rows) {
			String key = ValidateUtil.format(moduleKey, entity);
			//常用按钮
			List<String> commonbuts = new ArrayList<String>();
			//其他按钮
			List<ButtonDto> rowbuts = new ArrayList<ButtonDto>();
			for (AclButton aclButton : currentHasButs) {
				String buttoncode = aclButton.getCode();
				String status = ValidateUtil.format("status", entity);
				if(commonbutsMap.containsKey(buttoncode)){
					if(statusFilterMap.containsKey(buttoncode) && statusFilterMap.get(buttoncode).equals(status)){
						commonbuts.add(buttoncode);
					}
					continue;
				}
				//如果之前查询过 这里就直接取值 不再与数据库交涉
				if(butsAuthority.containsKey(buttoncode)){

					if(statusFilterMap.containsKey(buttoncode) && 
							(statusFilterMap.get(buttoncode).equals(status)||statusFilterMap.get(buttoncode).equals(BaseIcSuppAuthorityConText.FILTER_STATUS_ALL))){
						ButtonDto butoon = new ButtonDto(aclButton.getCode().toLowerCase(), aclButton.getCname(), aclButton.getIconName());
						rowbuts.add(butoon);
					}

					continue;
				}
				
				Boolean hasRight = hasRight(moduleCode, buttoncode);
				if(hasRight){
					if(statusFilterMap.containsKey(buttoncode) && statusFilterMap.get(buttoncode).equals(status)){
						ButtonDto butoon = new ButtonDto(aclButton.getCode(), aclButton.getCname(), aclButton.getIconName());
						rowbuts.add(butoon);
					}
				}
				butsAuthority.put(buttoncode, hasRight);
			}
			
			if(!rowbuts.isEmpty()||!commonbuts.isEmpty()){
				buts.put(key, Pair.create(commonbuts, rowbuts));
			}
		}
		
 		return (0 == buts.size())?null:buts;
		
	}
	
	
	public static AclRightService getAclRightService() {
		if(ValidateUtil.isEmpty(aclRightService)){
			aclRightService = (AclRightService)ApplicationContextHolder.getBean("aclRightService");
		}
		return aclRightService;
	}
	
	public static AclButtonService getAclButtonService() {
		if(ValidateUtil.isEmpty(aclButtonService)){
			aclButtonService = (AclButtonService)ApplicationContextHolder.getBean("aclButtonService");
		}
		return aclButtonService;
	}
}
