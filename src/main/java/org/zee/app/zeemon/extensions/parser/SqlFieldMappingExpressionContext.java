package org.zee.app.zeemon.extensions.parser;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.zee.app.zeemon.domain.FieldMapping;
import org.zee.app.zeemon.extensions.expression.ExpressionContext;

public class SqlFieldMappingExpressionContext extends ExpressionContext<Object, ResultSet>{
	
	private int index ;
	private FieldMapping fieldMapping;
	
	public SqlFieldMappingExpressionContext(Object dbValue, ResultSet rs, int rsIndex, FieldMapping fieldMapping) {
		super(dbValue, rs);
		this.index = rsIndex;
		this.fieldMapping = fieldMapping;
	}
	
	public Object rsRead(int columnIndex) throws SQLException {
		return this.ctx.getObject(columnIndex);
	}
	
	public Object rsReadByName(String columnLabel) throws SQLException {
		return this.ctx.getObject(columnLabel);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public FieldMapping getFieldMapping() {
		return fieldMapping;
	}

	public void setFieldMapping(FieldMapping fieldMapping) {
		this.fieldMapping = fieldMapping;
	}
	
	
}
