package com.mybatisgenerator;

import com.mybatisgenerator.sqlEdit.SqlEditTemplate;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;


public class MapperPlugin extends PluginAdapter {

    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement parentElement = document.getRootElement();
        parentElement.addElement(SqlEditTemplate.g_SelectAll(introspectedTable));
        parentElement.addElement(SqlEditTemplate.g_SelectByCondiction(introspectedTable));
        parentElement.addElement(SqlEditTemplate.g_deleteByGuid(introspectedTable));
        parentElement.addElement(SqlEditTemplate.g_selectByGuid(introspectedTable));
        parentElement.addElement(SqlEditTemplate.g_updateByGuid(introspectedTable));
        parentElement.addElement(SqlEditTemplate.g_updateByGuidSelective(introspectedTable));
        return true;
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
                + introspectedTable.getBaseRecordType() + ",String>");

        FullyQualifiedJavaType imp = new FullyQualifiedJavaType(
                "com.mybatisgenerator.base.BaseMapper");

        interfaze.addSuperInterface(fqjt);
        interfaze.addImportedType(imp);

        interfaze.getMethods().clear();
        interfaze.getAnnotations().clear();
        return true;
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }
}
