package com.micmiu.framework.web.v1.system.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.common.collect.Lists;
import com.micmiu.framework.anno.ShowParam;
import com.micmiu.framework.web.v1.base.entity.IdEntity;
import com.micmiu.framework.web.v1.system.vo.Permission;

@Entity
@Table(name = "T_SYS_ROLE")
public class Role extends IdEntity {

	@ShowParam("角色名称")
	@Column(name = "ROLE_NAME")
	private String roleName;

	private List<Permssion> permssions = new ArrayList<Permssion>();

	@Column(name = "ROLE_NAME", length = 50)
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 操作权限集合.
	 */
	@ManyToMany
	@JoinTable(name = "T_SYS_R2P", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "PERM_ID") })
	@Fetch(FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Permssion> getPermssions() {
		return permssions;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setPermssions(List<Permssion> permssions) {
		this.permssions = permssions;
	}

	@ShowParam(value = "操作权限", width = 500)
	@Transient
	public String getPermissionNames() {
		List<String> permissionNameList = Lists.newArrayList();
		for (Permssion permssion : getPermssions()) {
			try {
				permissionNameList
						.add(permssion.getResCnName()
								+ ":"
								+ Permission.parse(permssion.getOperation()).displayName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return StringUtils.join(permissionNameList, ",");
	}

}
