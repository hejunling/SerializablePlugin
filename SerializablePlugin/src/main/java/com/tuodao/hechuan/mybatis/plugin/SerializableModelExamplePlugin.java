package com.tuodao.hechuan.mybatis.plugin;

import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 对mybatis自动生成的model，example在分布式系统中都需要进行序列化 扩展一个 mybatis generator
 * 插件，用于不仅仅在生成的实体类 还有 *Example 类都序列化
 * 
 * @author hechuan
 *
 * @created 2017年3月30日
 *
 * @since CLOUD-1.0.0
 */
public class SerializableModelExamplePlugin extends PluginAdapter {

	private FullyQualifiedJavaType serializable;
	private FullyQualifiedJavaType gwtSerializable;
	private boolean addGWTInterface;
	private boolean suppressJavaInterface;

	public SerializableModelExamplePlugin() {
		super();
		serializable = new FullyQualifiedJavaType("java.io.Serializable"); //$NON-NLS-1$
		gwtSerializable = new FullyQualifiedJavaType("com.google.gwt.user.client.rpc.IsSerializable"); //$NON-NLS-1$
	}

	public boolean validate(List<String> warnings) {
		// this plugin is always valid
		return true;
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		addGWTInterface = Boolean.valueOf(properties.getProperty("addGWTInterface")); //$NON-NLS-1$
		suppressJavaInterface = Boolean.valueOf(properties.getProperty("suppressJavaInterface")); //$NON-NLS-1$
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		makeSerializable(topLevelClass, introspectedTable);
		return true;
	}

	@Override
	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		makeSerializable(topLevelClass, introspectedTable);
		return true;
	}

	@Override
	public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		makeSerializable(topLevelClass, introspectedTable);
		return true;
	}

	/**
	 * 添加给Example类序列化的方法
	 * 
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return
	 */
	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		makeSerializable(topLevelClass, introspectedTable);

		for (InnerClass innerClass : topLevelClass.getInnerClasses()) {
			if ("GeneratedCriteria".equals(innerClass.getType().getShortName())) { //$NON-NLS-1$
				innerClass.addSuperInterface(serializable);
			}
			if ("Criteria".equals(innerClass.getType().getShortName())) { //$NON-NLS-1$
				innerClass.addSuperInterface(serializable);
			}
			if ("Criterion".equals(innerClass.getType().getShortName())) { //$NON-NLS-1$
				innerClass.addSuperInterface(serializable);
			}
		}

		return true;
	}

	protected void makeSerializable(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		if (addGWTInterface) {
			topLevelClass.addImportedType(gwtSerializable);
			topLevelClass.addSuperInterface(gwtSerializable);
		}

		if (!suppressJavaInterface) {
			topLevelClass.addImportedType(serializable);
			topLevelClass.addSuperInterface(serializable);

			Field field = new Field();
			field.setFinal(true);
			field.setInitializationString("1L"); //$NON-NLS-1$
			field.setName("serialVersionUID"); //$NON-NLS-1$
			field.setStatic(true);
			field.setType(new FullyQualifiedJavaType("long")); //$NON-NLS-1$
			field.setVisibility(JavaVisibility.PRIVATE);
			context.getCommentGenerator().addFieldComment(field, introspectedTable);

			topLevelClass.addField(field);
		}
	}

}
