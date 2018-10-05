package com.mybatisgenerator.sqlEdit;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class SqlEditTemplate {

    public static XmlElement g_SelectAll(IntrospectedTable introspectedTable)
    {
        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", "selectByStringCondition"));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        select.addAttribute(new Attribute("parameterType","java.lang.String"));
        select.addElement(new TextElement("select "));
        // select.addElement(elInClude(introspectedTable.getBaseColumnListId()));
        select.addElement(elInClude("All_Column_List"));
        select.addElement(new TextElement("from "+ introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        select.addElement(new TextElement("where 1=1 ${_parameter}"));
        return select;
    }

    public static XmlElement g_SelectByCondiction(IntrospectedTable introspectedTable)
    {
        XmlElement selectByCondition = new XmlElement("select");
        selectByCondition.addAttribute(new Attribute("id", "selectByCondition"));
        selectByCondition.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        selectByCondition.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        selectByCondition.addElement(new TextElement("select "));
        //selectByCondition.addElement(elInClude(introspectedTable.getBaseColumnListId()));
        selectByCondition.addElement(elInClude("All_Column_List"));
        selectByCondition.addElement(new TextElement("from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));

        XmlElement el_where = elWhere();
        XmlElement el_if_1 = elIf("true"," 1=1 ");
        el_where.addElement(el_if_1);

        for (IntrospectedColumn introspectedColumn : introspectedTable.getBaseColumns())
        {
            String colunName = MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn);
            String mysqlType = MyBatis3FormattingUtilities.getParameterClause(introspectedColumn);
            XmlElement el_if = elIf(toCamalCase(introspectedColumn.getActualColumnName()) + " !=null ","    and " + colunName + " = " + mysqlType);
            el_where.addElement(el_if);
        }
        selectByCondition.addElement(el_where);
        return selectByCondition;
    }

    public static XmlElement g_deleteByGuid(IntrospectedTable introspectedTable)
    {
        XmlElement delete = new XmlElement("delete");
        delete.addAttribute(new Attribute("id", "deleteByGuid"));
        delete.addAttribute(new Attribute("parameterType", "java.lang.String"));
        delete.addElement(new TextElement("delete from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        delete.addElement(new TextElement(" where guid = #{guid,jdbcType=VARCHAR}"));
        return delete;
    }


    public static XmlElement g_selectByGuid(IntrospectedTable introspectedTable)
    {
        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", "selectByGuid"));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        select.addAttribute(new Attribute("parameterType", "java.lang.String"));
        select.addElement(new TextElement("select "));
        //select.addElement(elInClude(introspectedTable.getBaseColumnListId()));
        select.addElement(elInClude("All_Column_List"));
        select.addElement(new TextElement(" from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        select.addElement(new TextElement(" where guid = #{guid,jdbcType=VARCHAR}"));
        return select;
    }


    public static XmlElement g_updateByGuidSelective(IntrospectedTable introspectedTable)
    {
        XmlElement updateSelecive = new XmlElement("update");
        updateSelecive.addAttribute(new Attribute("id", "updateByGuidSelective"));
        updateSelecive.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        updateSelecive.addElement(new TextElement("update " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));

        XmlElement el_trim = elTrim("","",",");
        XmlElement el_set = elSet();

        for(IntrospectedColumn introspectedColumn : introspectedTable.getBaseColumns())
        {
            String _columnName = introspectedColumn.getActualColumnName();
            if(!_columnName.toLowerCase().equals("guid")&&!_columnName.toLowerCase().equals("createtime"))
            {
                String _mySqlcolunName = MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn);
                String _mysqlType = MyBatis3FormattingUtilities.getParameterClause(introspectedColumn);


                XmlElement el_if = elIf(toCamalCase(_columnName) + " != null","  " + _mySqlcolunName + " = " + _mysqlType + ",");
                el_trim.addElement(el_if);
            }
        }
        el_set.addElement(el_trim);
        updateSelecive.addElement(el_set);
        updateSelecive.addElement(new TextElement(" where guid = #{guid,jdbcType=VARCHAR}"));
        return updateSelecive;
    }


    public static XmlElement g_updateByGuid(IntrospectedTable introspectedTable)
    {
        XmlElement update = new XmlElement("update");
        update.addAttribute(new Attribute("id", "updateByGuid"));
        update.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        update.addElement(new TextElement("update " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        update.addElement(new TextElement("set"));

        XmlElement el_trim = elTrim("","",",");

        int totalCount = introspectedTable.getBaseColumns().size();
        int _index = 1;
        String _suffix = ",";

        for(IntrospectedColumn introspectedColumn : introspectedTable.getBaseColumns())
        {
            if(_index==totalCount) _suffix="";
            String _mySqlcolumnName = MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn);

            String _mysqlType = MyBatis3FormattingUtilities.getParameterClause(introspectedColumn);
            if(!_mySqlcolumnName.toLowerCase().equals("createtime")&&!_mySqlcolumnName.toLowerCase().equals("guid"))
            {
                el_trim.addElement(new TextElement(" " + _mySqlcolumnName + " = " + _mysqlType  +_suffix));
            }
            _index++;
        }
        update.addElement(el_trim);
        update.addElement(new TextElement(" where guid = #{guid,jdbcType=VARCHAR}"));
        return update;
    }


    public static XmlElement g_Insert(IntrospectedTable introspectedTable)
    {
        XmlElement sql = new XmlElement("insert");
        sql.addAttribute(new Attribute("id", "insert"));
        sql.addAttribute(new Attribute("parameterType",introspectedTable.getBaseRecordType()));

        StringBuilder _insertSql = new StringBuilder();
        _insertSql.append("insert into " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " (\n");
        _insertSql.append("   %s " + "\n");
        _insertSql.append("   ) values ( " + "\n");
        _insertSql.append("   %s " + "\n");
        _insertSql.append("   )");
        StringBuilder sbColumn = new StringBuilder();
        StringBuilder sbValue = new StringBuilder();

        int _index = 1;
        int _count = introspectedTable.getAllColumns().size();
        String _suffix = ",";
        for(IntrospectedColumn introspectedColumn:introspectedTable.getAllColumns())
        {
            String _mysqlType = MyBatis3FormattingUtilities.getParameterClause(introspectedColumn);
            if(_index==_count) _suffix="";
            sbColumn.append(introspectedColumn.getActualColumnName() + _suffix);
            sbValue.append(_mysqlType + _suffix);
            _index++;
        }

        String result = String.format(_insertSql.toString(),sbColumn.toString(),sbValue.toString());
        sql.addElement(new TextElement(result));
        return sql;
    }

    public static XmlElement g_AllColunmsList(IntrospectedTable introspectedTable)
    {
        XmlElement sql = new XmlElement("sql");
        sql.addAttribute(new Attribute("id", "All_Column_List"));
        StringBuilder sb = new StringBuilder();
        String _suffix = ",";
        int _index = 1;
        int _count = introspectedTable.getAllColumns().size();
        for(IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns())
        {
            if(_index==_count) _suffix="";
            sb.append(introspectedColumn.getActualColumnName() + _suffix);
            _index++;
        }
        sql.addElement(new TextElement(sb.toString()));
        return sql;
    }





    public static XmlElement elInClude(String refid)
    {
        XmlElement el = new XmlElement("include");
        el.addAttribute(new Attribute("refid",refid));
        return el;
    }


    public static XmlElement elTrim(String px,String sx,String ov)
    {
        XmlElement eltrim = new XmlElement("trim");
        if(!px.isEmpty())
            eltrim.addAttribute(new Attribute("prefix",px));
        if(!sx.isEmpty())
            eltrim.addAttribute(new Attribute("suffix",sx));
        if(!ov.isEmpty())
            eltrim.addAttribute(new Attribute("suffixOverrides",ov));
        return eltrim;
    }

    public static XmlElement elIf(String condition,String content)
    {
        XmlElement elif = new XmlElement("if");
        if(!condition.isEmpty())
            elif.addAttribute(new Attribute("test",condition));
        if(!content.isEmpty())
            elif.addElement(new TextElement(content));
        return elif;
    }

    public static XmlElement elSet()
    {
        return new XmlElement("set");
    }

    public static XmlElement elWhere()
    {
        return new XmlElement("where");
    }

    public static String toCamalCase(String word)
    {
        String _s = word.substring(0,1);
        String _end = word.substring(1,word.length());
        String _ls = _s.toLowerCase();
        return _ls.concat(_end);
    }

}
