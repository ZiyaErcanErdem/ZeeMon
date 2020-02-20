package org.zee.app.zeemon.extensions.parser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.util.TablesNamesFinder;

public class SqlParserUtil {

	public static List<String> extractColumnNames(String sql) throws JSQLParserException{
		List<String> columns = new ArrayList<>();
		if (null == sql || StringUtils.isEmpty(sql)) {
			return columns;
		}
		
		CCJSqlParserManager pm = new CCJSqlParserManager();
		Statement statement = pm.parse(new StringReader(sql));
		if (!(statement instanceof Select)) {
			return columns;
		}
		
		Select selectStatement = (Select) statement;
		PlainSelect selectBody = (PlainSelect)selectStatement.getSelectBody();
		if (null == selectBody) {
			return columns;
		}
		
		List<SelectItem> columnList = selectBody.getSelectItems();
		if (null == columnList || columnList.isEmpty()) {
			return columns;
		}
		
		columnList.forEach(si -> {
			if (!(si instanceof SelectExpressionItem)) {
				return;
			}
			SelectExpressionItem sei = (SelectExpressionItem)si;
			String columnName = null;
			Alias alias = sei.getAlias();
			if (null == alias) {
				columnName = sei.toString();
			} else {
				columnName = alias.getName();
			}
			columns.add(columnName);
		});
		
		return columns;
	}
	
	public static List<String> extractTableNames(String sql) throws JSQLParserException{
		if (null == sql || StringUtils.isEmpty(sql)) {
			return new ArrayList<>();
		}
		
		CCJSqlParserManager pm = new CCJSqlParserManager();
		Statement statement = pm.parse(new StringReader(sql));
		if (!(statement instanceof Select)) {
			return new ArrayList<>();
		}
		
		Select selectStatement = (Select) statement;
		
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
		List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
		return tableList;

	}
	
	public static List<String> extractParameters(String sql) throws JSQLParserException{
		if (null == sql || StringUtils.isEmpty(sql)) {
			return new ArrayList<>();
		}
		
		CCJSqlParserManager pm = new CCJSqlParserManager();
		Statement statement = pm.parse(new StringReader(sql));
		if (!(statement instanceof Select)) {
			return new ArrayList<>();
		}
		
		List<String> paramList = new ArrayList<>();
		statement.accept(new StatementVisitorAdapter() {
			
            @Override
            public void visit(Select select) {
                select.getSelectBody().accept(new SelectVisitorAdapter() {
                	
                    @Override
                    public void visit(PlainSelect plainSelect) {
                        plainSelect.getWhere().accept(new ExpressionVisitorAdapter() {
                            @Override
                            public void visit(JdbcNamedParameter parameter) {
                            	String paramName = parameter.getName();
                            	paramList.add(paramName);
                            }

                        });
                        
                        plainSelect.getSelectItems().forEach(si -> {
                        	si.accept(new ExpressionVisitorAdapter() {
                                 @Override
                                public void visit(JdbcNamedParameter parameter) {
                                	String paramName = parameter.getName();
                                	paramList.add(paramName);
                                }

                            });
                        });
                    }
                });
            }
        });
		return paramList;
	}
}
