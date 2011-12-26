package org.activecodebase.jactiverecord.main;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sam Woodard
 *
 */
public abstract class Base {
	private static String _tableName;
	
	private Map<String, Object> _attributes;
	
	public Base() {
		this(new HashMap<String, Object>());
	}
	
	private Base(Map<String,Object> attributes) {
		_attributes = attributes;
	}

	public Object get(String attribute) {
		return _attributes.get(attribute);
	}

	public void setAttributes(Map<String, Object> attributes) {
		_attributes.putAll(attributes);
	}

	public static void setTableName(String tableName) {
		_tableName = tableName;
	}

	public static String getTableName(Class c) {
		return _tableName;
	}
}
