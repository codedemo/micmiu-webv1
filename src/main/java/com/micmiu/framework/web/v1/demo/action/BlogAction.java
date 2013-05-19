package com.micmiu.framework.web.v1.demo.action;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.micmiu.framework.utils.RefTools;
import com.micmiu.framework.web.GlobalConstant;
import com.micmiu.framework.web.v1.demo.DemoConstant;
import com.micmiu.framework.web.v1.demo.entity.Blog;
import com.micmiu.framework.web.v1.demo.service.BlogService;
import com.micmiu.framework.web.v1.demo.vo.BlogQuery;
import com.micmiu.modules.support.easyui.BeanConvert;
import com.micmiu.modules.support.easyui.GridColumn;
import com.micmiu.modules.support.easyui.PropertyGridData;
import com.micmiu.modules.support.springmvc.view.CsvView;
import com.micmiu.modules.support.springmvc.view.JxlExcelView;
import com.micmiu.modules.support.springmvc.view.PdfiText5View;
import com.micmiu.modules.support.springmvc.view.PoiExcelView;

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

	@Autowired
	private MessageSource messageSource;

	@RequiresPermissions("demo_common:view")
	@RequestMapping(params = { "method=dg4base" })
	public String list4base(Model model) {
		List<Blog> list = blogService.findAll();
		model.addAttribute("blogs", list);
		return PREFIX + ".blog.list.base";
	}

	@RequiresPermissions("demo_common:view")
	@RequestMapping(params = { "method=dg4tb" })
	public String list4tb(Model model) {
		List<Blog> list = blogService.findAll();
		model.addAttribute("blogs", list);
		return PREFIX + ".blog.list.tb";
	}

	@RequiresPermissions("demo_common:view")
	@RequestMapping(params = { "method=dg4export" })
	public String list4export(Model model) {
		List<Blog> list = blogService.findAll();
		model.addAttribute("blogs", list);
		return PREFIX + ".blog.list.export";
	}

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
	public List<GridColumn> getGridColumns(Model model,
			HttpServletRequest request) {
		return RefTools.getBeanColumns(Blog.class, messageSource,
				RequestContextUtils.getLocale(request));
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
	public Map<String, Object> getViewData(Long id, HttpServletRequest request) {
		Map<String, String> showMap = new HashMap<String, String>();
		RefTools.getBeanAnnoMap(Blog.class, showMap, messageSource,
				RequestContextUtils.getLocale(request));

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
	public String save(Blog blog, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		if (null == blog.getId()) {
			blog.setCreater(SecurityUtils.getSubject().getPrincipal()
					.toString());
		}
		blogService.saveOrUpdate(blog);
		MessageFormat mf = new MessageFormat(messageSource.getMessage(
				DemoConstant.DEMO_BLOG_MSG_ADD_SUC, null,
				RequestContextUtils.getLocale(request)));
		String message = mf.format(new String[] { blog.getTitle() });
		redirectAttributes.addFlashAttribute("message", message);
		return message;
	}

	@RequiresPermissions("demo_common:view")
	@RequestMapping(params = "method=deleteBatch")
	@ResponseBody
	public String deleteBatch(String ids, HttpServletRequest request) {
		String message = null;
		try {
			blogService.delBatch(ids);
			message = messageSource.getMessage(GlobalConstant.MSG_SUCC, null,
					RequestContextUtils.getLocale(request));
		} catch (Exception e) {
			message = messageSource.getMessage(GlobalConstant.MSG_FAILED, null,
					RequestContextUtils.getLocale(request));
		}
		return message;
	}

	@RequiresPermissions("demo_common:view")
	@RequestMapping(params = { "method=delete" })
	public String delete(Long id, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		blogService.delete(id);
		redirectAttributes.addFlashAttribute("message", messageSource
				.getMessage(GlobalConstant.MSG_SUCC, null,
						RequestContextUtils.getLocale(request)));
		return "redirect:/system/blog.do?method=list";
	}

	@RequestMapping(params = { "method=export" }, method = RequestMethod.POST)
	public ModelAndView export(HttpServletRequest request, BlogQuery pageQuery,
			String exportType) {
		blogService.exportPageQuery(pageQuery);
		List<Blog> list = pageQuery.getQueryResults();
		Map<String, Object> model = this.parseExportModel(request);
		model.put(PoiExcelView.ROW_DATA, list);
		if ("POI".equals(exportType)) {
			return new ModelAndView(new PoiExcelView(), model);
		} else if ("JXL".equals(exportType)) {
			return new ModelAndView(new JxlExcelView(), model);
		} else if ("CSV".equals(exportType)) {
			return new ModelAndView(new CsvView(), model);
		}
		return new ModelAndView(new PdfiText5View(), model);
	}

	private Map<String, Object> parseExportModel(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put(PoiExcelView.FILE_NAME, messageSource.getMessage(
				DemoConstant.DEMO_BLOG_EXCEL_FILENAME, null,
				RequestContextUtils.getLocale(request)));
		model.put(PoiExcelView.SHEET_NAME, messageSource.getMessage(
				DemoConstant.DEMO_BLOG_EXCEL_SHEETNAME, null,
				RequestContextUtils.getLocale(request)));
		model.put(PoiExcelView.TITLE, "");
		Map<String, String> showMap = new LinkedHashMap<String, String>();
		RefTools.getBeanAnnoMap(Blog.class, showMap, messageSource,
				RequestContextUtils.getLocale(request));
		model.put(PoiExcelView.COLUMN_MAP, showMap);

		return model;
	}

}
