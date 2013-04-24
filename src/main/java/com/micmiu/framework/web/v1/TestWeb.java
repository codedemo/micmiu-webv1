package com.micmiu.framework.web.v1;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.micmiu.framework.web.v1.system.entity.User;
import com.micmiu.framework.web.v1.system.service.UserService;

public class TestWeb {
	private static AtomicInteger atom = new AtomicInteger();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath*:/applicationContext-test.xml");
		UserService userService = (UserService) ctx.getBean("userService");
		System.out.println(userService.findAll().size());
		User user = genRandomUser();
		
		userService.save(user);
		System.out.println(user.getId());
		System.out.println(userService.findAll().size());
		
	}
	public static User genRandomUser() {
		long randomKey = System.nanoTime() + atom.addAndGet(1);
		User user = new User();
		user.setLoginName("Michael" + randomKey);
		user.setEmail("sjsky" + randomKey + "@micmiu.com");
		user.setPassword("micmiu");
		user.setOther("test");
		return user;
	}
}
