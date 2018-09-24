package com.mybatisgenerator;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.List;


public class MapperPlugin extends PluginAdapter {

    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", "selectAll"));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        select.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        select.addElement(new TextElement("select * from "+ introspectedTable.getFullyQualifiedTableNameAtRuntime()));

        XmlElement selectByCondition = new XmlElement("select");
        selectByCondition.addAttribute(new Attribute("id", "selectByCondiction"));
        selectByCondition.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        selectByCondition.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        selectByCondition.addElement(new TextElement("select * from "));
        selectByCondition.addElement(new TextElement(introspectedTable.getFullyQualifiedTableNameAtRuntime()));

        selectByCondition.addElement(new TextElement(" <where> "));
        selectByCondition.addElement(new TextElement("  <if test=\"true\">"));
        selectByCondition.addElement(new TextElement("    1=1 "));
        selectByCondition.addElement(new TextElement("  </if>"));
        for (IntrospectedColumn introspectedColumn : introspectedTable.getBaseColumns()) {

            String colunName = MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn);
            String mysqlType = MyBatis3FormattingUtilities.getParameterClause(introspectedColumn);

            selectByCondition.addElement(new TextElement("  <if test=\"" + introspectedColumn.getActualColumnName() + " !=null \">"));
            selectByCondition.addElement(new TextElement("    and " + colunName + " = " + mysqlType));
            selectByCondition.addElement(new TextElement("  </if>"));
        }
        selectByCondition.addElement(new TextElement(" </where>"));

        XmlElement parentElement = document.getRootElement();
        parentElement.addElement(select);
        parentElement.addElement(selectByCondition);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("BaseModel");
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType(
                "com.mybatisgenerator.base.BaseModel");
        topLevelClass.setSuperClass(fqjt);
        topLevelClass.addImportedType(imp);
        return true;
    }



    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if(field.getName().equals("id")||field.getName().equals("guid"))
            return false;
        return true;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType)
    {
        return false;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("BaseMapper<"
                + introspectedTable.getBaseRecordType() + ">");

        FullyQualifiedJavaType imp = new FullyQualifiedJavaType(
                "com.mybatisgenerator.base.BaseMapper");

        interfaze.addSuperInterface(fqjt);
        interfaze.addImportedType(imp);

        interfaze.getMethods().clear();
        interfaze.getAnnotations().clear();
        return true;
    }
}
