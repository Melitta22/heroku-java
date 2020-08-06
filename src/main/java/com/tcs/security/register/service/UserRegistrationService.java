package com.tcs.security.register.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import com.tcs.security.dto.UserDetailDTO;

@Component
public class UserRegistrationService {
	@Value("${ldap.urls}")
	private String ldapUrls;
	
	@Value("${ldap.base.dn}")
	private String ldapBaseDn;
	
	@Value("${ldap.user.dn.pattern}")
	private String ldapUserDnPattern;
	
	DirContext context;

	public void addNewUserToLDAP(UserDetailDTO userDetails) throws Exception {
		getProperties();
		Attributes container = new BasicAttributes();
		Attribute objClasses = new BasicAttribute("objectClass");
		objClasses.add("inetOrgPerson");
		objClasses.add("top");
		objClasses.add("person");
		objClasses.add("organizationalPerson");
		//objClasses.add("user");
		container.put(objClasses);
		//Password
		Attribute userPassword =new BasicAttribute("userPassword");
		userPassword.add(userDetails.getPassword());
		container.put(userPassword);
		//First Name
		Attribute cn =new BasicAttribute("cn");
		cn.add(userDetails.getFirstName());
		container.put(cn);
		//Last Name
		Attribute sn =new BasicAttribute("sn");
		sn.add(userDetails.getLastName());
		container.put(sn);
		//Mobile
		Attribute tn =new BasicAttribute("telephoneNumber");
		tn.add(String.valueOf(userDetails.getMobileNumber()));
		container.put(tn);
		//Email
		Attribute email =new BasicAttribute("mail");
		email.add(userDetails.getEmail());
		container.put(email);
		//Created Date
		/*
		 * LocalDateTime date = LocalDateTime.now(); DateTimeFormatter formatter =
		 * DateTimeFormatter.ofPattern("yyyyMMdd"); String formatDateTime =
		 * date.format(formatter); Attribute createdTime =new
		 * BasicAttribute("createTimestamp"); createdTime.add(formatDateTime);
		 * container.put(createdTime);
		 */

		context.createSubcontext("uid="+userDetails.getUserName()+",ou=people",container);

		System.out.println("success");

	}

	public UserDetailDTO fetchUserDetails(String uid) throws Exception {
		UserDetailDTO user = new UserDetailDTO();
		user.setUserName(uid);
		getFetchProperties();
		SearchControls searchControl = new SearchControls();
		searchControl.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String[] attrIDs = {"cn", "sn", "mail", "telephonenumber","userPassword"};
		searchControl.setReturningAttributes(attrIDs);
		NamingEnumeration<SearchResult> answer = context.search(ldapBaseDn, "uid=" + uid, searchControl);
		if (answer.hasMore()) {
			Attributes attrs = answer.next().getAttributes();
			System.out.println(attrs.get("cn"));
			user.setPassword(getActualValue(attrs.get("userPassword").toString()));
			user.setFirstName(getActualValue(attrs.get("cn").toString()));
			user.setLastName(getActualValue(attrs.get("sn").toString()));
			user.setEmail(getActualValue(attrs.get("mail").toString()));
			user.setMobileNumber(Long.valueOf(getActualValue(attrs.get("telephonenumber").toString())));
		}
		return user;
	}
	
	public void getProperties() {
		Hashtable<String, String> ldapEnv = new Hashtable<>();
		  ldapEnv.put( Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		  ldapEnv.put(Context.PROVIDER_URL, ldapUrls+ldapBaseDn);
		  ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
		  ldapEnv.put(Context.SECURITY_PRINCIPAL , "uid=admin,ou= system");
		  ldapEnv.put(Context.SECURITY_CREDENTIALS, "secret");
		  try {
			context = new InitialDirContext(ldapEnv);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getFetchProperties() {
		Hashtable<String, String> ldapEnv = new Hashtable<>();
		  ldapEnv.put( Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		  ldapEnv.put(Context.PROVIDER_URL, ldapUrls);
		  ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
		  ldapEnv.put(Context.SECURITY_PRINCIPAL , "uid=admin,ou= system");
		  ldapEnv.put(Context.SECURITY_CREDENTIALS, "secret");
		  try {
			context = new InitialDirContext(ldapEnv);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getActualValue(String value) {
		String actualVal = value.substring(value.lastIndexOf(":")+1);
		System.out.println(actualVal);
		return actualVal.trim();
	}

	public void updateUserDetails(UserDetailDTO user) throws NamingException {
		getProperties();
		Attributes container = new BasicAttributes();
		Attribute objClasses = new BasicAttribute("objectClass");
		objClasses.add("inetOrgPerson");
		objClasses.add("top");
		objClasses.add("person");
		objClasses.add("organizationalPerson");
		//objClasses.add("user");
		container.put(objClasses);
		//Password
		Attribute userPassword =new BasicAttribute("userPassword", user.getPassword());
		//First Name
		Attribute cn =new BasicAttribute("cn", user.getFirstName());
		//Last Name
		Attribute sn =new BasicAttribute("sn", user.getLastName());
		//Mobile
		Attribute tn =new BasicAttribute("telephoneNumber", String.valueOf(user.getMobileNumber()));
		//Email
		Attribute email =new BasicAttribute("mail", user.getEmail());
		
		ModificationItem[] mods = new ModificationItem[5];

	    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, userPassword);
	    mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, cn);
	    mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, sn);
	    mods[3] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, tn);
	    mods[4] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, email);
		context.modifyAttributes("uid="+user.getUserName()+",ou=people",mods);
		
	}

	public boolean validateUserLDAP(String dn, String password) throws Exception{
		getFetchProperties();
		SearchControls searchControl = new SearchControls();
		searchControl.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String[] attrIDs = {"userPassword"};
		searchControl.setReturningAttributes(attrIDs);
		NamingEnumeration<SearchResult> answer = context.search(ldapBaseDn, "uid=" + dn, searchControl);
		if (answer.hasMore()) {
			Attributes attrs = answer.next().getAttributes();
			Attribute ldapPassword = attrs.get("userPassword");
			String ldapPwd = getActualValue(new String((byte[]) ldapPassword.get()));
			password = encodeUsingMD5(password);
			if(ldapPwd.equals(password)) {
				return true;
			}
		}
		return false;
//		Hashtable env = new Hashtable(11);
//
//	    boolean b = false;
//
//	    env.put(Context.INITIAL_CONTEXT_FACTORY,
//	    "com.sun.jndi.ldap.LdapCtxFactory");
//	    env.put(Context.PROVIDER_URL, ldapUrls);
//	    env.put(Context.SECURITY_AUTHENTICATION, "simple");
//	    env.put(Context.DNS_URL, ldapBaseDn);
//	    env.put(Context.SECURITY_PRINCIPAL, "uid="+ dn +",ou=person");
//	    env.put(Context.SECURITY_CREDENTIALS, password);
//
//	    // Create initial context
//	    DirContext ctx = new InitialDirContext(env);
//
//	    // Close the context when we're done
//	    b = true;
//	    ctx.close();
//	    return b;
	}

	private String encodeUsingMD5(String password) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("MD5");
	       digest.update(password.getBytes("UTF8"));
	       String md5Password = new String((byte[])Base64.encode(digest.digest()));
	       return "{MD5}" + md5Password;
		
	}
}
