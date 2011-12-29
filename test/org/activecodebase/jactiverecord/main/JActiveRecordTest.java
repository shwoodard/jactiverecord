package org.activecodebase.jactiverecord.main;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.activecodebase.jactiverecord.test.models.ElectricCar;
import org.activecodebase.jactiverecord.test.models.Person;
import org.activecodebase.jactiverecord.test.models.Vehicle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sam
 *
 */
public class JActiveRecordTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReturnsSetAttributes() {
		Map<String, Attribute> attributes = new HashMap<String,Attribute>();
		Attribute attribute = new Attribute();
		attribute.setValue("red");
		attributes.put("color", attribute);
		Vehicle vehicle = new Vehicle();
		vehicle.setAttributes(attributes);
		assertEquals("red", vehicle.get("color"));
	}

	@Test
	public void testInstanceGetTableName() {
		Vehicle vehicle = new Vehicle();
		assertEquals("vehicles", vehicle.getTableName());
	}

	@Test
	public void testStaticGetTableName() {
		assertEquals("vehicles", Vehicle.getTableName(Vehicle.class));
	}

	@Test
	public void testTableNameWithMultipleWords() {
		assertEquals("electric_cars", ElectricCar.getTableName(ElectricCar.class));
	}

	@Test
	public void testIrregularTableName() {
		assertEquals("people", Person.getTableName(Person.class));
	}

	@Test
	public void testGetColumnNames() {
		Person person = new Person();
		try {
			String[] columnNames = person.getColumnNames();
			String[] expected = {"id", "name"};
			assertArrayEquals(expected, columnNames);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFetch() {
		try {
			Person person = new Person();
			Attribute attribute = new Attribute();
			attribute.setValue("1");
			person.setAttribute("id", attribute);
			person = Person.fetch(person);
			assertEquals(new Long(1), (Long)person.get("id"));
			assertEquals("John", (String)person.get("name"));
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetTableNames() throws SQLException {
		String[] actual = JActiveRecord.getTableNames();
		String[] expected = {"people"};
		assertArrayEquals(expected, actual);
	}
}
