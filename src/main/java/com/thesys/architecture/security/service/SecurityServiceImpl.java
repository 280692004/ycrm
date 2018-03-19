package com.thesys.architecture.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.core.util.collection.ListUtil;
import com.thesys.base.sysmag.aclbutton.domain.AclButton;
import com.thesys.base.sysmag.acluser.domain.AclUser;
import com.thesys.base.sysmag.acluser.service.AclUserService;
@SuppressWarnings({"unchecked","rawtypes"})
@Service("securityService")
public class SecurityServiceImpl  implements UserDetailsService{
	
	@Autowired
	private AclUserService aclUserService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AclUser aclUser=aclUserService.findAclUserByName(username);
		
		if(ValidateUtil.isEmpty("id",aclUser)){
			throw new RuntimeException("用户名错误！");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		List<String> authoritiesStrs = new ArrayList<String>();
		//= findAclUserAuthority(username);

		for(String authoritiesStr:authoritiesStrs){
			authorities.add(new SimpleGrantedAuthority(authoritiesStr));
		}
				
		return new User(aclUser.getName(), aclUser.getPassword().toLowerCase(), true, true, true, true,authorities); 
	}
	
	@SuppressWarnings("unused")
	private List<String> findAclUserAuthority(String username){

		List<AclButton> details = aclUserService.findAclButtons(username);		
		return ListUtil.getFormatProps((List)details, "actionmethodright");
	}
	

}
