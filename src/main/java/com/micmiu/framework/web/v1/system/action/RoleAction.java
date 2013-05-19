package com.micmiu.framework.web.v1.system.action;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.micmiu.framework.web.GlobalConstant;
import com.micmiu.framework.web.v1.system.entity.Role;
import com.micmiu.framework.web.v1.system.service.RoleService;
import com.micmiu.framework.web.v1.system.vo.Module;
import com.micmiu.framework.web.v1.system.vo.Permission;
import com.micmiu.framework.web.v1.system.vo.RoleQuery;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
@Controller
@RequestMapping(value = "/system/role.do")
public class RoleAction {

	@Autowired
	private RoleService roleService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(params = { "method=list" })
	public String list(Model model) {
		List<Role> roles = roleService.findAll();
		model.addAttribute("roles", roles);
		return "role.list";
	}

	@RequestMapping(params = { "method=query" })
	@ResponseBody
	public Map<String, Object> query(Model model, RoleQuery pageQuery) {
		List<Role> roles = roleService.pageQuery(pageQuery);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("total", pageQuery.getTotalCount());
		retMap.put("rows", roles);
		return retMap;
	}

	@RequestMapping(params = { "method=showForm" })
	public String showForm(Long id, Model model) {
		if (null != id) {
			model.addAttribute("role", roleService.findById(id));
		} else {
			model.addAttribute("role", new Role());
			Map<String, String> permMap = new LinkedHashMap<String, String>();
			for (Module mod : Module.values()) {
				for (Permission p : Permission.values()) {
					permMap.put(mod.getValue() + ":" + p.getValue(),
							mod.getDisplayName() + ":" + p.getDisplayName());
				}
			}

			model.addAttribute("permissionMap", permMap);
		}
		return "role.form";
	}

	@RequestMapping(params = { "method=showView" })
	public String showView(Long id, Model model) {
		model.addAttribute("role", roleService.findById(id));
		return "role.view";
	}

	@RequestMapping(params = { "method=save" })
	@ResponseBody
	public String save(Role role, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		roleService.save(role);
		String message = messageSource.getMessage(GlobalConstant.MSG_SUCC,
				null, RequestContextUtils.getLocale(request));
		redirectAttributes.addFlashAttribute("message", message);
		return message;
	}

	@RequestMapping(params = "method=deleteBatch")
	@ResponseBody
	public String deleteBatch(String ids, HttpServletRequest request) {
		String message = null;
		try {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				roleService.delete(Long.parseLong(id));
			}
			message = messageSource.getMessage(GlobalConstant.MSG_SUCC, null,
					RequestContextUtils.getLocale(request));
		} catch (Exception e) {
			message = messageSource.getMessage(GlobalConstant.MSG_FAILED, null,
					RequestContextUtils.getLocale(request));
		}
		return message;
	}

	@RequestMapping(params = { "method=delete" })
	public String delete(Long id, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		roleService.delete(id);
		redirectAttributes.addFlashAttribute("message", messageSource
				.getMessage(GlobalConstant.MSG_SUCC, null,
						RequestContextUtils.getLocale(request)));
		return "redirect:/system/role.do?method=list";
	}

	@RequestMapping(params = { "method=checkRoleName" })
	@ResponseBody
	public String checkRoleName(
			@RequestParam("oldRoleName") String oldRoleName,
			@RequestParam("roleName") String roleName) {
		if (roleName.equals(oldRoleName)) {
			return "true";
		} else if (roleService.getRoleByName(roleName) == null) {
			return "true";
		}

		return "false";
	}

}
