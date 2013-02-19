package com.ifunshow.dbc.classloader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author 于亚丰
 * 此类主要解决对相同数据库不同版本的调用相应的jar问题，
 * 目前这部分是写在代码中的，有可能的话要配置实现，见ScannerHelper.decideDriverLoader方法
 */
public class JdbcDriverLoader {
	public static void loadDriver(ClassLoader classLoader,
			String driverClassName) throws SecurityException,
			NoSuchMethodException, ClassNotFoundException, SQLException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		Class<?> loadedClass = classLoader.loadClass(driverClassName);
		Driver.class.isAssignableFrom(loadedClass);
		Class<Driver> driverClass = (Class<Driver>) loadedClass;
		Constructor<Driver> driverConstructor = driverClass.getConstructor();
		boolean isConstructorAccessible = driverConstructor.isAccessible();
		if (!isConstructorAccessible) {
			driverConstructor.setAccessible(true);
		}
		try {
			Driver driver = driverConstructor.newInstance();
			DriverManager.registerDriver(new DriverProxy(driver));
		} finally {
			driverConstructor.setAccessible(isConstructorAccessible);
		}
	}
}
