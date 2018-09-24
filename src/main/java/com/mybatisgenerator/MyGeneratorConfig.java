package com.mybatisgenerator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.Calendar;
import java.util.Properties;

public class MyGeneratorConfig implements CommentGenerator {

    private Properties properties;

    public MyGeneratorConfig()
    {
        super();
        properties = new Properties();
    }


    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

        String remark = introspectedColumn.getRemarks();
        if(remark.isEmpty())
            return;
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" @Description:" + remark);
        field.addJavaDocLine("*/");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        topLevelClass.addImportedType("lombok.Data");
        String tableName = introspectedTable.getRemarks();
        //introspectedTable.getFullyQualifiedTable().getIntrospectedTableName();
        if(tableName.isEmpty())
            return;
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" @Table:" + tableName);
        topLevelClass.addJavaDocLine(" @Date:" + Calendar.getInstance().getTime());
        topLevelClass.addJavaDocLine(" */");
        topLevelClass.addJavaDocLine("@Data");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {

    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        compilationUnit.addFileCommentLine("/**");
        compilationUnit.addFileCommentLine(" * @Date:" + Calendar.getInstance().getTime());
        compilationUnit.addFileCommentLine(" * @Description:由generator自动生成");
        compilationUnit.addFileCommentLine(" */");
    }

    @Override
    public void addComment(XmlElement xmlElement) {

    }

    @Override
    public void addRootComment(XmlElement xmlElement) {

    }
}
