package org.activecodebase.jactiverecord.main;

/**
 * @author Sam Woodard
 *
 */
public class Attribute {
	private String _attributeClassName;
	private Object _value;

	public Object getValue() {
		return _value;
	}

	public void setValue(Object value) {
		_value = value;
	}

	public String getAttributeClassName() {
		return _attributeClassName;
	}
	
	public void setAttributeClassName(String attributeClassName) {
		_attributeClassName = attributeClassName;
	}
	
}
