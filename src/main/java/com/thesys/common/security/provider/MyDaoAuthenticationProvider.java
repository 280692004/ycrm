package com.thesys.common.security.provider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@Transactional(noRollbackFor = AuthenticationException.class)
public class MyDaoAuthenticationProvider extends DaoAuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication paramAuthentication)	throws AuthenticationException {
		Authentication result = null;

		try {
			// 用户登录成功
			result = super.authenticate(paramAuthentication);
			SecurityContextHolder.getContext().setAuthentication(result);
			// System.out.println(" 正确日志记录");
		} catch (RuntimeException e) {			
			// throw new
			// RestRuntimeException(e.getMessage(),e.getMessage(),Status.INTERNAL_SERVER_ERROR,997);
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}


	
}
