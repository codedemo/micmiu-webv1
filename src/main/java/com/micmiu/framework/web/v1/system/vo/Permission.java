package com.micmiu.framework.web.v1.system.vo;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public enum Permission {
	VIEW("view", "查看"), EDIT("edit", "修改"), ADD("add", "添加"),

	DELETE("delete", "删除"), EXPORT("export", "导出"), PRINT("print", "打印");

	private static Map<String, Permission> valueMap = Maps.newHashMap();

	public String value;
	public String displayName;

	static {
		for (Permission permission : Permission.values()) {
			valueMap.put(permission.value, permission);
		}
	}

	Permission(String value, String displayName) {
		this.value = value;
		this.displayName = displayName;
	}

	public static Permission parse(String value) {
		return valueMap.get(value);
	}

	public String getValue() {
		return value;
	}

	public String getDisplayName() {
		return displayName;
	}
}
