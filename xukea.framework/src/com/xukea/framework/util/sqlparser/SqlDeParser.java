package com.xukea.framework.util.sqlparser;

import java.util.Iterator;

import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

/**
 * SQL 语句反解析器<br>
 * 根据解析后的SQL对象，生成SQL语句
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @date   2013.08.21
 */
public class SqlDeParser extends StatementDeParser {

	public SqlDeParser(StringBuilder buffer) {
		super(buffer);
	}

	/**
	 * 生成查询语句<br>
	 * 用重构后的SelectDeParser来解析表别名，去除表别名前的AS，防止ORACLE中出错
	 */
	@Override
	public void visit(Select select) {
//		SelectDeParser selectDeParser = new SelectDeParser();
		MySelectDeParser selectDeParser = new MySelectDeParser();
		selectDeParser.setBuffer(getBuffer());
		ExpressionDeParser expressionDeParser = new ExpressionDeParser(selectDeParser, getBuffer());
		selectDeParser.setExpressionVisitor(expressionDeParser);
		if (select.getWithItemsList() != null && !select.getWithItemsList().isEmpty()) {
			getBuffer().append("WITH ");
			for (Iterator<WithItem> iter = select.getWithItemsList().iterator(); iter.hasNext();) {
				WithItem withItem = iter.next();
				getBuffer().append(withItem);
				if (iter.hasNext()) {
					getBuffer().append(",");
				}
				getBuffer().append(" ");
			}
		}
		select.getSelectBody().accept(selectDeParser);

	}
}