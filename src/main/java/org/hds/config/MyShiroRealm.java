package org.hds.config;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.hds.mapper.userMapper;
import org.hds.model.user;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyShiroRealm extends AuthorizingRealm {
	@Resource
	private userMapper userMapper;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	// 权限信息，包括角色以及权限
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 如果身份认证的时候没有传入User对象，这里只能取到userName
		// 也就是SimpleAuthenticationInfo构造的时候第一个参数传递需要User对象
		user user = (user) principals.getPrimaryPrincipal();
		if (user.getIssuperuser() == 1) {
			authorizationInfo.addRole("admin");
			authorizationInfo.addStringPermission("advManger");
			authorizationInfo.addStringPermission("auditManger");
			authorizationInfo.addStringPermission("publishManger");
			authorizationInfo.addStringPermission("groupManger");
			authorizationInfo.addStringPermission("userManger");
			authorizationInfo.addStringPermission("taxiManger");
		} else {
			authorizationInfo.addRole("user");

			String adminpermission = user.getAdminpermission();
			for (int i = 0; i < adminpermission.length(); i++) {
				char c = adminpermission.charAt(i);
				if (c == '0') {
					continue;
				}
				switch (i) {
				case 0:
					authorizationInfo.addStringPermission("advManger");
					break;
				case 1:
					authorizationInfo.addStringPermission("auditManger");
					break;
				case 2:
					authorizationInfo.addStringPermission("publishManger");
					break;
				case 3:
					authorizationInfo.addStringPermission("groupManger");
					break;
				case 4:
					authorizationInfo.addStringPermission("userManger");
					break;
				case 5:
					authorizationInfo.addStringPermission("taxiManger");
					break;
				}
			}
		}
//		for (SysRole role : user.getRoleList()) {
//			authorizationInfo.addRole(role.getRole());
//			for (SysPermission p : role.getPermissions()) {
//				authorizationInfo.addStringPermission(p.getPermission());
//			}
//		}
		return authorizationInfo;
	}

	/* 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
		// 获取用户的输入的账号.
		String userName = (String) token.getPrincipal();
		System.out.println(token.getCredentials());
		// 通过username从数据库中查找 User对象.
		// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		user user = userMapper.selectByPrimaryName(userName);
		System.out.println("----->>user=" + user);
		if (user == null) {
			return null;
		}

		/*
		 * 数据库密码加密,暂时数据库存的明文,所以暂时加密后比对
		 */
//		String hashAlgorithmNameString = "MD5";
//		Object credentials = user.getAdminpwd();
//		Object salt = ByteSource.Util.bytes(user.getAdminname());
//		int hashIterations = 1024;
//		Object result = new SimpleHash(hashAlgorithmNameString, credentials, salt, hashIterations);

		// AuthenticatingRealm类assertCredentialsMatch方法密码比对

		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, // 这里传入的是user对象，比对的是用户名，直接传入用户名也没错，但是在授权部分就需要自己重新从数据库里取权限
				user.getAdminpwd(), // 密码
				ByteSource.Util.bytes(user.getAdminname()), // salt=username+salt
				getName() // realm name
		);
		return authenticationInfo;
	}
}