package com.micmiu.framework.utils;

import java.util.List;
import java.util.Set;

import com.micmiu.framework.web.v1.system.entity.Menu;
import com.micmiu.framework.web.v1.system.vo.Permission;
import com.micmiu.framework.web.v1.system.vo.TreeNode;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class MenuPermUtil {

	/**
	 * 转化用户菜单
	 * 
	 * @param allMenus
	 * @param userMenus
	 * @param ids
	 * @param contextPath
	 */
	public static void parseUserMenu(List<Menu> allMenus,
			List<TreeNode> userMenus, Set<Long> ids, String contextPath) {
		for (Menu menu : allMenus) {
			if (ids.contains(menu.getId())) {
				userMenus.add(recParseMenu(ids, menu, contextPath));
			}
		}
	}

	private static TreeNode recParseMenu(Set<Long> ids, Menu menu,
			String contextPath) {
		TreeNode vo = new TreeNode();
		vo.setId(menu.getId() + "");
		String text = "";
		if (null != menu.getMenuURL() && !"".equals(menu.getMenuURL())) {
			if (menu.getMenuURL().startsWith("/")) {
				text = "<a href ='" + contextPath + menu.getMenuURL() + "'>"
						+ menu.getMenuName() + "</a>";
			} else {
				text = "<a href ='" + contextPath + "/" + menu.getMenuURL()
						+ "'>" + menu.getMenuName() + "</a>";
			}
		} else {
			text = menu.getMenuName();
		}
		vo.setText(text);
		if (!menu.getChildren().isEmpty()) {
			for (Menu childMenu : menu.getChildren()) {
				if (ids.contains(childMenu.getId())) {
					vo.getChildren().add(
							recParseMenu(ids, childMenu, contextPath));
				}
			}
		}
		return vo;
	}

	/**
	 * 转化所有的权限树形结构
	 * 
	 * @param allMenus
	 * @param permTree
	 * @param hasPerms
	 */
	public static void parseMenuPermTree(List<Menu> allMenus,
			List<TreeNode> permTree) {
		for (Menu menu : allMenus) {
			permTree.add(recPermTree(menu));

		}
	}

	private static TreeNode recPermTree(Menu menu) {
		TreeNode vo = new TreeNode();
		vo.setId(menu.getId() + "");
		vo.setText(menu.getMenuName());
		if (!menu.getChildren().isEmpty()) {
			for (Menu childMenu : menu.getChildren()) {
				vo.getChildren().add(recPermTree(childMenu));
			}
		} else {
			for (Permission p : Permission.values()) {
				TreeNode permNode = new TreeNode();
				permNode.setId(menu.getId() + ":" + menu.getAliasName() + ":"
						+ p.getValue());
				permNode.setText(p.getDisplayName());
				vo.getChildren().add(permNode);
			}
		}
		return vo;
	}

	/**
	 * 转化已有角色的权限树形结构
	 * 
	 * @param allMenus
	 * @param permTree
	 * @param hasPerms
	 */
	public static void parseMenuPermTree(List<Menu> allMenus,
			List<TreeNode> permTree, Set<String> hasPerms) {
		for (Menu menu : allMenus) {
			permTree.add(recMenuPermTree(menu, hasPerms));

		}
	}

	private static TreeNode recMenuPermTree(Menu menu, Set<String> hasPerms) {
		TreeNode vo = new TreeNode();
		vo.setId(menu.getId() + "");
		vo.setText(menu.getMenuName());
		if (!menu.getChildren().isEmpty()) {
			for (Menu childMenu : menu.getChildren()) {
				vo.getChildren().add(recMenuPermTree(childMenu, hasPerms));
			}
		} else {
			for (Permission p : Permission.values()) {
				TreeNode permNode = new TreeNode();
				String nodeId = menu.getId() + ":" + menu.getAliasName() + ":"
						+ p.getValue();
				permNode.setId(nodeId);
				if (hasPerms.contains(nodeId)) {
					permNode.setChecked(true);
				}
				permNode.setText(p.getDisplayName());
				vo.getChildren().add(permNode);
			}
		}
		return vo;
	}
}
