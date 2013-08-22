package com.xukea.framework.util.sqlparser;

import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Pivot;
import net.sf.jsqlparser.util.deparser.SelectDeParser;

/**
 * SELECT SQL 处理<br>
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @date   2013.08.21
 */
public class MySelectDeParser extends SelectDeParser {

	public MySelectDeParser() {
	}
	
	public MySelectDeParser(ExpressionVisitor expressionVisitor, StringBuilder buffer) {
		super(expressionVisitor, buffer);
	}
	
	/**
	 * 拼接表名<br>
	 * 重写visit(Table)，表别名前不加AS，防止Oracle中出错
	 */
	@Override
	public void visit(Table tableName) {
		getBuffer().append(tableName.getWholeTableName());
        Pivot pivot = tableName.getPivot();
        if (pivot != null) {
            pivot.accept(this);
        }
		String alias = tableName.getAlias();
		if (alias != null && !alias.isEmpty()) {
//			getBuffer().append(" AS ").append(alias);
			getBuffer().append(" ").append(alias);
		}
	}
}