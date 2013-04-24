package com.micmiu.framework.web.v1.demo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.micmiu.framework.utils.RefTools;
import com.micmiu.framework.web.v1.demo.entity.Blog;
import com.micmiu.framework.web.v1.demo.service.BlogService;
import com.micmiu.framework.web.v1.demo.vo.BlogQuery;
import com.micmiu.modules.support.easyui.BeanConvert;
import com.micmiu.modules.support.easyui.GridColumn;
import com.micmiu.modules.support.easyui.PropertyGridData;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
@Controller
@RequestMapping(value = "/demo/blog.do")
public class BlogAction {

	private static final String PREFIX = "demo.dg";
	@Autowired
	private BlogService blogService;

	@RequiresPermissions("demo_common:view")
	@RequestMapping(params = { "method=list" })
	public String list(Model model) {
		List<Blog> list = blogService.findAll();
		model.addAttribute("blogs", list);
		return PREFIX + ".blog.list";
	}

	@RequiresPermissions("demo_common:view")
	@RequestMapping(params = { "method=query" })
	@ResponseBody
	public Map<String, Object> query(Model model, BlogQuery pageQuery) {
		List<Blog> roles = blogService.pageQuery(pageQuery);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("total", pageQuery.getTotalCount());
		retMap.put("rows", roles);
		return retMap;
	}

	@RequestMapping(params = { "method=getGridColumns" })
	@ResponseBody
	public List<GridColumn> getGridColumns(Model model) {
		return RefTools.getBeanColumns(Blog.class);
	}

	@RequiresPermissions("demo_common:view")
	@RequestMapping(params = { "method=showForm" })
	public String showForm(Long id, Model model) {
		if (null != id) {
			model.addAttribute("blog", blogService.findById(id));
		} else {
			Blog blog = new Blog();
			blog.setAuthor("Micahel");
			model.addAttribute("blog", blog);
		}
		return PREFIX + ".blog.form";
	}

	@RequiresPermissions("demo_common:view")
	@RequestMapping(params = { "method=showView" })
	public String showView(Long id, Model model) {
		model.addAttribute("blog", blogService.findById(id));
		return PREFIX + "blog.view";
	}

	@RequiresPermissions("demo_common:view")
	@RequestMapping(params = { "method=getViewData" })
	@ResponseBody
	public Map<String, Object> getViewData(Long id) {
		Map<String, String> showMap = new HashMap<String, String>();
		RefTools.getBeanAnnoMap(Blog.class, showMap);

		Map<String, Object> retMap = new HashMap<String, Object>();
		List<PropertyGridData> list = BeanConvert.convertPropertyGridData(
				blogService.findById(id), showMap);
		retMap.put("total", list.size());
		retMap.put("rows", list);
		return retMap;
	}

	@RequiresPermissions("demo_common:view")
	@RequestMapping(params = { "method=save" })
	@ResponseBody
	public String save(Blog blog, RedirectAttributes redirectAttributes) {
		if (null == blog.getId()) {
			blog.setCreater(SecurityUtils.getSubject().getPrincipal()
					.toString());
		}
		blogService.saveOrUpdate(blog);
		String message = "博客：" + blog.getTitle() + " 添加成功";
		redirectAttributes.addFlashAttribute("message", message);
		return message;
	}

	@RequiresPermissions("demo_common:view")
	@RequestMapping(params = "method=batchDel")
	@ResponseBody
	public String batchDelete(String ids) {
		String message = null;
		try {
			blogService.delBatch(ids);
			message = "删除成功";
		} catch (Exception e) {
			message = "删除失败：\n" + e.getMessage();
		}
		return message;
	}

	@RequiresPermissions("demo_common:view")
	@RequestMapping(params = { "method=delete" })
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		blogService.delete(id);
		redirectAttributes.addFlashAttribute("message", "角色删除成功");
		return "redirect:/system/blog.do?method=list";
	}

}
