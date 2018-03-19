package com.thesys.base.sysmag.aclmenu.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesys.architecture.core.annotation.ServiceMapper;
import com.thesys.architecture.service.impl.BaseServiceImpl;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.core.util.classify.ClassifyUtil;
import com.thesys.base.core.util.collection.ListUtil;
import com.thesys.base.core.util.collection.MapUtil;
import com.thesys.base.core.util.reflect.IFilter;
import com.thesys.base.sysmag.aclmenu.dao.AclMenuMapper;
import com.thesys.base.sysmag.aclmenu.domain.AclMenu;
import com.thesys.base.sysmag.aclmenu.service.AclMenuService;
import com.thesys.base.sysmag.aclmodule.domain.AclModule;
import com.thesys.base.sysmag.acluser.service.AclUserService;
@Service("aclMenuService")
@ServiceMapper(AclMenuMapper.class)
public class AclMenuServiceImpl extends BaseServiceImpl<AclMenu> implements AclMenuService{
	@Autowired
	private AclUserService aclUserService;
	
	@Override
	public Boolean hasRight(String modulecode, String acluserId) {
		Long count=this.getMapper().doOpAclMenuCount(modulecode, acluserId);
		return count>0?true:false;
	}

	@Override
	public void doOpAddOrUpdateAclMenu(String aclUserId){
		
		List<AclModule> founds=findUserAclMenu(aclUserId);
		Map<String,AclModule> foundmaps= MapUtil.createOgnlMap(founds, "id");
		
		List<AclModule> details= aclUserService.findAclUserHasRightAclModule(aclUserId);
		final Map<String,AclModule> maps= MapUtil.createOgnlMap(details, "id");

		List<AclModule> additems = new ArrayList<AclModule>();
		for(Entry<String,AclModule> entry:maps.entrySet()){
			String key=entry.getKey();
			if(foundmaps.containsKey(key)){
				continue;
			}
			
			additems.add(entry.getValue());
		}

		List<AclModule> delitems = ListUtil.filter(founds, new IFilter<AclModule>() {
			@Override
			public boolean doFilter(AclModule obj) {
				return !maps.containsKey(obj.getId().toString());
			}
		});
		if(!additems.isEmpty()){
			this.addAclMenu(aclUserId, additems);
		}
		
		this.delAclMenu(aclUserId, delitems);
	}
	
	@Override
	public void addAclMenu(String acluserId,List<AclModule> details){
		List<AclMenu> items = new ArrayList<AclMenu>();
		for(AclModule detail:details){
			items.add(new AclMenu(acluserId, detail.getId().toString()).setSeq(detail.getOrderNo()));
		}
		
		this.getMapper().addAclMenu(items);
	}

	@Override
	public void delAclMenu(String acluserId,List<AclModule> details){
		for(AclModule detail:details){
			this.getMapper().delAclMenu(new AclMenu(acluserId,detail.getId().toString()));
		}
	}
	
	@Override
	public void deleteAclMenuByModuleId(Integer moduleId){
		this.getMapper().deleteAclMenuByModuleId(moduleId);
	}
	
	@Override
	public List<AclModule> findUserAclMenu(String acluserId, String parentCode,Boolean withsubmenu) {	
		List<AclModule> details=this.getMapper().findAclMenuByUserId(acluserId);
		Map<String,List<AclModule>> maps = ClassifyUtil.classify(details, "parent.code");
	
		return filter(maps,new AclModule().setCode(parentCode));
	}
	
	@Override
	public List<AclModule> findUserAclMenu(String acluserId){
		return this.getMapper().findAclMenuByUserId(acluserId);
	}
	
	private List<AclModule> filter(Map<String,List<AclModule>> maps,AclModule aclModule ){

		List<AclModule> details = filterSubMenu(maps,aclModule);
		
		for(AclModule detail:details){
			detail.getDetails().addAll(filter(maps,detail));
		}
		
		return details;
	}
	
	private List<AclModule> filterSubMenu(Map<String,List<AclModule>> maps,AclModule aclModule ){
		
		String key = ValidateUtil.format("code", aclModule);
		List<AclModule> details = maps.containsKey(key)?maps.get(key):new ArrayList<AclModule>();
		
		return details;   
	}

	@SuppressWarnings("all")
	@Override
	protected AclMenuMapper getMapper() {
		return (AclMenuMapper)super.getMapper();
	}
	
	

}