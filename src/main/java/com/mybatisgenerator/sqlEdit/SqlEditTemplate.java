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
        select.addElement(new TextElement("select " + getInClude(introspectedTable.getBlobColumnListId())));
        select.addElement(new TextElement("from "+ introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        select.addElement(new TextElement("where 1=1 #{condition,jdbcType=VARCHAR}"));
        return select;
    }

    public static XmlElement g_SelectByCondiction(IntrospectedTable introspectedTable)
    {
        XmlElement selectByCondition = new XmlElement("select");
        selectByCondition.addAttribute(new Attribute("id", "selectByCondition"));
        selectByCondition.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        selectByCondition.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        selectByCondition.addElement(new TextElement("select " + getInClude(introspectedTable.getBlobColumnListId())));
        selectByCondition.addElement(new TextElement("from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        selectByCondition.addElement(new TextElement(" <where> "));
        selectByCondition.addElement(new TextElement("  <if test=\"true\">"));
        selectByCondition.addElement(new TextElement("    1=1 "));
        selectByCondition.addElement(new TextElement("  </if>"));

        for (IntrospectedColumn introspectedColumn : introspectedTable.getBaseColumns())
        {
            String colunName = MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn);
            String mysqlType = MyBatis3FormattingUtilities.getParameterClause(introspectedColumn);

            selectByCondition.addElement(new TextElement("  <if test=\"" + introspectedColumn.getActualColumnName() + " !=null \">"));
            selectByCondition.addElement(new TextElement("    and " + colunName + " = " + mysqlType));
            selectByCondition.addElement(new TextElement("  </if>"));
        }
        selectByCondition.addElement(new TextElement(" </where>"));
        return selectByCondition;
    }

    public static XmlElement g_deleteByGuid(IntrospectedTable introspectedTable)
    {
        XmlElement delete = new XmlElement("delete");
        delete.addAttribute(new Attribute("id", "deleteByGuid"));
        delete.addAttribute(new Attribute("parameterType", "java.lang.Integer"));
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
        select.addElement(new TextElement(getInClude(introspectedTable.getBaseColumnListId())));
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

        boolean _first = true;
        int totalCount = introspectedTable.getBaseColumns().size();
        int _index = 1;
        String _suffix = ",";
        for(IntrospectedColumn introspectedColumn : introspectedTable.getBaseColumns())
        {
            if(_index==totalCount) _suffix="";
            if(_first) {
                updateSelecive.addElement(new TextElement("<set> "));
                _first = false;
            }

            String _columnName = introspectedColumn.getActualColumnName();
            String _mySqlcolunName = MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn);
            String _mysqlType = MyBatis3FormattingUtilities.getParameterClause(introspectedColumn);

            updateSelecive.addElement(new TextElement(" <if test=\""+ _columnName +" != null\">"));
            updateSelecive.addElement(new TextElement("  " + _mySqlcolunName + " = " + _mysqlType + _suffix));
            updateSelecive.addElement(new TextElement(" </if>"));
            _index++;
        }
        updateSelecive.addElement(new TextElement("</set>"));
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
        int totalCount = introspectedTable.getBaseColumns().size();
        int _index = 1;
        String _suffix = ",";
        for(IntrospectedColumn introspectedColumn : introspectedTable.getBaseColumns())
        {
            if(_index==totalCount) _suffix="";
            String _mySqlcolunName = MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn);
            String _mysqlType = MyBatis3FormattingUtilities.getParameterClause(introspectedColumn);
            update.addElement(new TextElement(" " + _mySqlcolunName + " = " + _mysqlType +_suffix));
            _index++;
        }
        update.addElement(new TextElement(" where guid = #{guid,jdbcType=VARCHAR}"));
        return update;
    }


    public static String getInClude(String content)
    {
        return "<include refid=\"" + content + "\" />";
    }

}
