package org.activecodebase.jactiverecord.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.activecodebase.jactivereocrd.support.Inflector;

/**
 * @author Sam Woodard
 *
 */
public abstract class JActiveRecord {
	private Map<String, Attribute> _attributes;
	private static Connection _connection;
	private static String[] _columnNames;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jactiverecord", "root", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public JActiveRecord() {
		this(new HashMap<String, Attribute>());
	}
	
	private JActiveRecord(Map<String, Attribute> attributes) {
		_attributes = attributes;
	}

	public Object get(String attribute) {
		return _attributes.get(attribute).getValue();
	}

	public Attribute getAttribute(String attributeName) {
		return _attributes.get(attributeName);
	}
	
	public void setAttribute(String attribute, Attribute value) {
		_attributes.put(attribute, value);
	}
	
	public void setAttributes(Map<String, Attribute> attributes) {
		_attributes.putAll(attributes);
	}

	public static String getTableName(Class<?> klass) {
		TableName tableName = klass.getAnnotation(TableName.class);
		if (tableName != null) {
			return tableName.value();
		}
		
		return tableName(klass);
	}
	
	public String getTableName() {
		return getTableName(getClass());
	}

	private static String tableName(Class<?> klass) {
		Inflector inflector = new Inflector();
		return inflector.pluralize(inflector.underscore(klass.getSimpleName()));
	}

	// TOOD wrap connection
	public static Connection connection() {
		return _connection;
	}

	public String[] getColumnNames() throws SQLException {
		if (_columnNames != null) {
			return _columnNames;
		}

		PreparedStatement statement = connection().prepareStatement("select * from " + getTableName());
		ResultSet rs = statement.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		rs.next();
		int numColumns = rsmd.getColumnCount();
		String[] columnNames = new String[numColumns];
		
		for (int i = 1; i < numColumns + 1; i++) {
			Attribute attribute = new Attribute();
			attribute.setAttributeClassName(rsmd.getColumnClassName(i));
			columnNames[i-1] = rsmd.getColumnName(i);
			setAttribute(rsmd.getColumnName(i), attribute);
		}
		return columnNames;
	}

	public static String[] getTableNames() throws SQLException {
		PreparedStatement statement = connection().prepareStatement("show tables");
		ResultSet rs = statement.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		String[] tableNames = new String[rsmd.getColumnCount()];
		int i = 0;
		while(rs.next()) {
			tableNames[i] = rs.getString(1);
		}
		return tableNames;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends JActiveRecord> T fetch(T arObject) throws InstantiationException, IllegalAccessException, SQLException {
		int id = Integer.parseInt((String)arObject.get("id"));
		arObject = (T)arObject.getClass().newInstance();
		PreparedStatement statement = connection().prepareStatement("SELECT * FROM " + arObject.getTableName() + " WHERE id = ?");
		statement.setInt(1, id);
		ResultSet rs = statement.executeQuery();
		rs.next();
		ResultSetMetaData rsmd = rs.getMetaData();
		int numColumns = rsmd.getColumnCount();
		for (int i = 1; i < numColumns + 1; i++) {
			Attribute attribute = new Attribute();
			attribute.setAttributeClassName(rsmd.getColumnClassName(i));
			attribute.setValue(rs.getObject(i));
			arObject.setAttribute(rsmd.getColumnName(i), attribute);
		}
		return arObject;
	}
}
