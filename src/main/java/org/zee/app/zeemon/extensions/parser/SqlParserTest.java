package org.zee.app.zeemon.extensions.parser;

import java.util.List;

import net.sf.jsqlparser.JSQLParserException;

public class SqlParserTest {

	public static void main(String[] args) {
		SqlParserTest tester = new SqlParserTest();
		try {
			tester.test1();
		} catch (JSQLParserException e) {
				e.printStackTrace();
		}

	}
	
	public void test1() throws JSQLParserException {
		// String sql = "SELECT * FROM MY_TABLE1, MY_TABLE2, (SELECT * FROM MY_TABLE3) LEFT OUTER JOIN MY_TABLE4 "+
		//		" WHERE ID = (SELECT MAX(ID) FROM MY_TABLE5) AND ID2 IN (SELECT * FROM MY_TABLE6)" ;
		String sql = 
			"		SELECT :ZEE as ZEE_ID, S.INST_ID I_ID, SUBSTR(S.USERNAME,1,10) USERNAME, SUBSTR(S.OSUSER,1,10) OSUSER, SUBSTR(S.MACHINE,1,40) MACHINE, S.SID DBSID, P.SPID DBPID, S.PROCESS APPPID, S.STATUS STATUS, ROUND(S.LAST_CALL_ET/60/60, 2) EX_TIME " +
			"	       , (CASE WHEN S.BLOCKING_SESSION IS NULL THEN NULL WHEN S.BLOCKING_SESSION IS NOT NULL THEN S.BLOCKING_SESSION || ' || ' || BLOCKING_INSTANCE END) BLCKNG_SESS_INFO " +
			"	       , S.SQL_ID SQL_ID, ROUND(W.TIME_REMAINING_MICRO/1000000) REMIN_SEC, SUBSTR(S.MODULE,1,10) MODULE, S.PROGRAM PROGRAM " +
			"		   , S.WAIT_CLASS WAIT_CLASS, SUBSTR(W.EVENT,1,25) EVENT, ROUND(W.WAIT_TIME_MICRO/1000000) W_TIME " +
			"	FROM GV$SESSION S  " +
			"		INNER JOIN GV$PROCESS P ON S.PADDR = P.ADDR " + 
			"		INNER JOIN GV$SESSION_WAIT W ON S.SID=W.SID AND S.INST_ID=W.INST_ID " +
			"	WHERE S.USERNAME IS NOT NULL " +
			"	   AND S.TYPE <> 'BACKGROUND' " + 
			"	   AND S.STATUS = :P_ACTIVE " +
			"	   AND :P_PASSIVE = S.STATUS " +
			"	ORDER BY S.LAST_CALL_ET DESC, S.SID;";
		
		this.parseSql(sql);
		
		// sql = "SELECT * FROM MYTABLE WHERE COLUMN_A = :paramA AND COLUMN_B <> :para";
	}
	
	public void parseSql(String sql) throws JSQLParserException {
		List<String> tableNames = SqlParserUtil.extractTableNames(sql);
		tableNames.forEach(tn -> {
			System.out.println("Table => " + tn);
		});
		
		List<String> columnNames = SqlParserUtil.extractColumnNames(sql);
		columnNames.forEach(cn -> {
			System.out.println("Column => " + cn);
		});
		
		List<String> paramNames = SqlParserUtil.extractParameters(sql);
		paramNames.forEach(cn -> {
			System.out.println("Param => " + cn);
		});
		
	}

}
