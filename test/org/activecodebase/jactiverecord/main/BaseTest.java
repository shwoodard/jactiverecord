package org.activecodebase.jactiverecord.main;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.activecodebase.jactiverecord.test.models.Vehicle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sam
 *
 */
public class BaseTest {

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
		Map<String, Object> attributes = new HashMap<String,Object>();
		attributes.put("color", "red");
		Vehicle vehicle = new Vehicle();
		vehicle.setAttributes(attributes);
		assertEquals("red", vehicle.get("color"));
	}

	@Test
	public void testSetTableName() {
		
	}
}
