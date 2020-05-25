package org.hds.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig {
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置不会被拦截的链接 顺序判断，因为前端模板采用了thymeleaf，这里不能直接使用 ("/static/**",
		// "anon")来配置匿名访问，必须配置到每个静态目录
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");

		filterChainDefinitionMap.put("/templates/**", "anon");
		filterChainDefinitionMap.put("/bootstrap/**", "anon");
		filterChainDefinitionMap.put("/bootstrap-table/**", "anon");
		filterChainDefinitionMap.put("/font-awesome-4.7.0/**", "anon");
		filterChainDefinitionMap.put("/ion.rangeSlider/**", "anon");
		filterChainDefinitionMap.put("/jquery-ui/**", "anon");
		filterChainDefinitionMap.put("/jquery/**", "anon");
		filterChainDefinitionMap.put("/spin/**", "anon");
		filterChainDefinitionMap.put("/tinymce/**", "anon");
		filterChainDefinitionMap.put("/jcanvas/**", "anon");
		filterChainDefinitionMap.put("/javascript/**", "anon");
		filterChainDefinitionMap.put("/login/**", "anon");
		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		// <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/**", "authc");
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/index");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/main");

		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
		defaultSecurityManager.setRealm(customRealm());
		return defaultSecurityManager;
	}

	/**
	 * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了 ）
	 * 
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(1024);// 散列的次数，比如散列两次，相当于 md5(md5(""));
		return hashedCredentialsMatcher;
	}

	@Bean
	public MyShiroRealm customRealm() {
		MyShiroRealm customRealm = new MyShiroRealm();
		// customRealm.setCredentialsMatcher(hashedCredentialsMatcher());//md5加密
		return customRealm;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * *
	 * 寮�鍚疭hiro鐨勬敞瑙�(濡侤RequiresRoles,@RequiresPermissions),闇�鍊熷姪SpringAOP鎵弿浣跨敤Shiro娉ㄨВ鐨勭被,骞跺湪蹇呰鏃惰繘琛屽畨鍏ㄩ�昏緫楠岃瘉
	 * *
	 * 閰嶇疆浠ヤ笅涓や釜bean(DefaultAdvisorAutoProxyCreator(鍙��)鍜孉uthorizationAttributeSourceAdvisor)鍗冲彲瀹炵幇姝ゅ姛鑳�
	 * * @return
	 */
	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor" })
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}
//	@Bean(name = "simpleMappingExceptionResolver")
//	public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
//		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
//		Properties mappings = new Properties();
//		mappings.setProperty("DatabaseException", "databaseError");// 数据库异常处理
//		mappings.setProperty("UnauthorizedException", "/user/403");
//		r.setExceptionMappings(mappings); // None by default
//		r.setDefaultErrorView("error"); // No default
//		r.setExceptionAttribute("exception"); // Default is "exception"
//		// r.setWarnLogCategory("example.MvcLogger"); // No default
//		return r;
//	}
}
