package com.micmiu.framework.web.v1.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.micmiu.framework.utils.MenuPermUtil;
import com.micmiu.framework.web.v1.system.entity.Menu;
import com.micmiu.framework.web.v1.system.entity.Permssion;
import com.micmiu.framework.web.v1.system.entity.Role;
import com.micmiu.framework.web.v1.system.entity.User;
import com.micmiu.framework.web.v1.system.service.MenuService;
import com.micmiu.framework.web.v1.system.service.UserService;
import com.micmiu.framework.web.v1.system.vo.TreeNode;
import com.micmiu.framework.web.v1.system.vo.UserQuery;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
@Controller
@RequestMapping(value = "/system/user.do")
public class UserAction {

	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;

	@RequestMapping(params = { "method=list" })
	public String list(Model model) {
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		return "user.list";
	}

	@RequestMapping(params = { "method=query" })
	@ResponseBody
	public Map<String, Object> query(Model model, UserQuery pageQuery) {
		List<User> users = userService.pageQuery(pageQuery);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("total", pageQuery.getTotalCount());
		retMap.put("rows", users);
		return retMap;
	}

	@RequestMapping(params = { "method=showForm" })
	public String showForm(Long id, Model model) {
		if (null != id) {
			model.addAttribute("user", userService.findById(id));
		} else {
			model.addAttribute("user", new User());
		}
		return "user.form";
	}

	@RequestMapping(params = { "method=showView" })
	public String showView(Long id, Model model) {
		model.addAttribute("user", userService.findById(id));
		return "user.view";
	}

	@RequestMapping(params = { "method=save" })
	@ResponseBody
	public String save(User user, RedirectAttributes redirectAttributes) {
		userService.save(user);
		String message = "用户：" + user.getLoginName() + " 操作成功";
		redirectAttributes.addFlashAttribute("message", message);
		return message;
	}

	@RequestMapping(params = "method=batchDel")
	@ResponseBody
	public String batchDelete(String ids) {
		String message = null;
		try {
			userService.batchDel(ids);
			message = "删除成功";
		} catch (Exception e) {
			message = "删除用户失败：\n" + e.getMessage();
		}
		return message;
	}

	@RequestMapping(params = { "method=delete" })
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		userService.delete(id);
		redirectAttributes.addFlashAttribute("message", "删除用户成功");
		return "redirect:/system/user.do?method=list";
	}

	@RequestMapping(params = { "method=checkLoginName" })
	@ResponseBody
	public String checkLoginName(
			@RequestParam("oldLoginName") String oldLoginName,
			@RequestParam("loginName") String loginName) {
		if (loginName.equals(oldLoginName)) {
			return "true";
		} else if (userService.getUserByLoginName(loginName) == null) {
			return "true";
		}

		return "false";
	}

	@RequestMapping(params = { "method=getUserMenu" })
	@ResponseBody
	public List<TreeNode> getUserTreeMenu(HttpServletRequest request) {
		String contextPath = request.getSession().getServletContext()
				.getContextPath();
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		TreeNode index = new TreeNode();
		index.setId("0");
		index.setText("<a href='" + contextPath + "/index.do'>" + "首页</a>");
		treeNodeList.add(index);

		String loginName = SecurityUtils.getSubject().getPrincipal().toString();
		User currUser = userService.getUserByLoginName(loginName);

		MenuPermUtil
				.parseUserMenu(menuService.getRootMenuByOrder(), treeNodeList,
						parseMenuIds(currUser.getRoleList()), contextPath);

		return treeNodeList;
	}

	private Set<Long> parseMenuIds(List<Role> roles) {
		Set<Long> ids = new HashSet<Long>();
		for (Role role : roles) {
			for (Permssion perm : role.getPermssions()) {
				recParseMenuIds(ids, perm.getMenu());
			}
		}
		return ids;

	}

	private void recParseMenuIds(Set<Long> menuIds, Menu menu) {
		menuIds.add(menu.getId());
		if (null != menu.getParent()) {
			recParseMenuIds(menuIds, menu.getParent());
		}
	}
}
