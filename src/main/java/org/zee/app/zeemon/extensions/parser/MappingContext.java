package org.zee.app.zeemon.extensions.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.zee.app.zeemon.domain.ContentMapper;
import org.zee.app.zeemon.domain.FieldMapping;

public class MappingContext {
	
	private List<FieldMapping> fieldMappings;
	private ContentMapper contentMapper;
	private Map<String, FieldMapping> mappedItems;
	
	public MappingContext(ContentMapper contentMapper, List<FieldMapping> fieldMappings) {
		super();
		this.contentMapper = contentMapper;
		this.fieldMappings = fieldMappings;
		this.initItems();
	}
	
	private void initItems() {	
		this.mappedItems =  this.fieldMappings.stream().collect(Collectors.toMap(fm -> fm.getTargetColName(), fm -> fm, (oldValue, newValue) -> oldValue));
	}
	
	public FieldMapping getFieldMappingWithTargetColName(String targetColName) {
		return this.mappedItems.get(targetColName);
	}
	
}
