package com.thesys.common.security.decisionmanager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import com.thesys.base.core.util.Pair;
/** 
 * @description  资源源数据定义，将所有的资源和权限对应关系建立起来，即定义某一资源可以被哪些角色访问 
 */  
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource{
    /* 保存资源和权限的对应关系  key-资源url  value-权限 */  
    private Map<String,Collection<ConfigAttribute>> resourceMap = null;   
    private AntPathMatcher urlMatcher = new AntPathMatcher();
    
    public MySecurityMetadataSource() {  
        loadResourcesDefine();  
    }  
     
    
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
		 //获取请求的url地址  
        String url = ((FilterInvocation)obj).getRequestUrl();  
        Pair<String,String> pair = doOpCalMethorName(url);
        System.out.println("methorname:"+pair.getLeft()+";right="+pair.getRight());
        System.out.println("MySecurityMetadataSource:getAttributes()---------------请求地址为："+url);  
        Iterator<String> it = resourceMap.keySet().iterator();  
        while(it.hasNext()){  
            String _url = it.next();  
            if(_url.indexOf("?")!=-1){  
                _url = _url.substring(0, _url.indexOf("?"));  
            }  
            if(urlMatcher.match(_url,url))  
                return resourceMap.get(_url);  
        }         
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		System.out.println("MySecurityMetadataSource.supports()---------------------");  
		return true;
	}
	/**
      ConfigAttribute configAttribute1 = new SecurityConfig("ROLE_QUOTATION_TOLIST");  
      List<ConfigAttribute> details= new ArrayList<ConfigAttribute>();
      details.add(configAttribute1);  
      resourceMap.put("/sell/quotation/quotation/quotation/toListQuotation.action", details);
	 */
    private void loadResourcesDefine(){
    	
       resourceMap = new HashMap<String,Collection<ConfigAttribute>>();  
       
       /*
       List<AclMethodUrl> items=aclMethodUrlService.findAll();
       
       for(AclMethodUrl item:items){
          List<ConfigAttribute> details= new ArrayList<ConfigAttribute>();
          details.add(new SecurityConfig(item.getButton().getActionmethodright()));   
          resourceMap.put(item.getUrl(), details);
       } */       
    }
    
    private Pair<String,String> doOpCalMethorName(String url){
    	Integer lastindex=url.lastIndexOf("/");
    	if(lastindex<0){
    		return Pair.create(url, url);
    	}
    	String leftmethorname = url.substring(lastindex);
    	String rightmethorname = url.substring(lastindex+1);
    	
    	return Pair.create(leftmethorname, rightmethorname);
    }

	public Map<String, Collection<ConfigAttribute>> getResourceMap() {
		return resourceMap;
	}


	public void setResourceMap(Map<String, Collection<ConfigAttribute>> resourceMap) {
		this.resourceMap = resourceMap;
	}


	public AntPathMatcher getUrlMatcher() {
		return urlMatcher;
	}


	public void setUrlMatcher(AntPathMatcher urlMatcher) {
		this.urlMatcher = urlMatcher;
	}  	

}
