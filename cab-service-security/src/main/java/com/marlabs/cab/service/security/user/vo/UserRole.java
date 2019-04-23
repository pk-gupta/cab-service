package com.marlabs.cab.service.security.user.vo;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * User's Roles will be supplied from this class.
 * Role class implements Spring-Security GrantedAuthority interface for custom authentication feature
 * 
 * @author aakin
 * 
 */
public class UserRole implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	private String name;

	private List<UserPrivilege> privileges;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return this.name;
	}

	public List<UserPrivilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<UserPrivilege> privileges) {
		this.privileges = privileges;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Role [name=");
		builder.append(name);
		builder.append(", privileges=");
		builder.append(privileges);
		builder.append("]");
		return builder.toString();
	}

}
