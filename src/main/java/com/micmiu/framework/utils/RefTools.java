package com.micmiu.framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.micmiu.framework.anno.ShowParam;
import com.micmiu.framework.web.v1.system.entity.Role;
import com.micmiu.modules.support.easyui.GridColumn;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class RefTools {

	/**
	 * 
	 * @param oldObj
	 * @param newObj
	 * @return
	 * @throws Exception
	 */
	public static void getBeanAnnoMap(Class<?> clazz,
			Map<String, String> showMap) {

		Field fields[] = clazz.getDeclaredFields();

		for (Field field : fields) {
			String fieldName = field.getName();
			if (!field.isAnnotationPresent(ShowParam.class)) {
				continue;
			}
			String cname = field.getAnnotation(ShowParam.class).value();
			if (!StringUtils.isEmpty(cname)) {
				showMap.put(fieldName, cname);
			} else {
				showMap.put(fieldName, fieldName);
			}
		}

		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (!method.isAnnotationPresent(ShowParam.class)) {
				continue;
			}
			String cname = method.getAnnotation(ShowParam.class).value();
			String name = method.getName();
			if (name.startsWith("get")) {
				String propertyName = StringUtils.uncapitalize(name
						.substring(3));
				if (!StringUtils.isEmpty(cname)) {
					showMap.put(propertyName, cname);
				} else {
					showMap.put(propertyName, propertyName);
				}
			}

		}

	}

	/**
	 * 
	 * @param oldObj
	 * @param newObj
	 * @return
	 * @throws Exception
	 */
	public static void getBeanAnnoMap(Object obj, Map<String, String> showMap) {
		getBeanAnnoMap(obj.getClass(), showMap);

	}

	/**
	 * 
	 * @param oldObj
	 * @param newObj
	 * @return
	 * @throws Exception
	 */
	public static List<GridColumn> getBeanColumns(Class<?> clazz) {
		List<GridColumn> columns = new ArrayList<GridColumn>();
		Field fields[] = clazz.getDeclaredFields();

		for (Field field : fields) {
			String fieldName = field.getName();
			if (!field.isAnnotationPresent(ShowParam.class)) {
				continue;
			}
			GridColumn column = new GridColumn();
			column.setField(fieldName);
			String cname = field.getAnnotation(ShowParam.class).value();
			column.setTitle(cname);
			column.setAlign(field.getAnnotation(ShowParam.class).align());
			column.setSortable(field.getAnnotation(ShowParam.class).sortable());
			column.setWidth(field.getAnnotation(ShowParam.class).width());
			columns.add(column);
		}

		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (!method.isAnnotationPresent(ShowParam.class)) {
				continue;
			}

			String name = method.getName();
			if (!name.startsWith("get")) {
				continue;
			}

			String fieldName = StringUtils.uncapitalize(name.substring(3));

			GridColumn column = new GridColumn();
			column.setField(fieldName);
			String cname = method.getAnnotation(ShowParam.class).value();
			column.setTitle(cname);
			column.setAlign(method.getAnnotation(ShowParam.class).align());
			column.setSortable(method.getAnnotation(ShowParam.class).sortable());
			column.setWidth(method.getAnnotation(ShowParam.class).width());
			columns.add(column);

		}

		return columns;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> showMap = new HashMap<String, String>();
		RefTools.getBeanAnnoMap(Role.class, showMap);

	}
}
