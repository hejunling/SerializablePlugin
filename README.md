### maven配置

```
<properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <mybatis-spring-boot.version>1.2.0</mybatis-spring-boot.version>
        <entity.version>1.0.0</entity.version>
        <plugin.version>1.0.0</plugin.version>
    </properties>
    <dependencies>

        <!-- spring boot mybatis -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${mybatis-spring-boot.version}</version>
        </dependency>

        <!-- 分页插件 -->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
        </dependency>

        <!-- JDBC连接池组件 -->
        <dependency>
            <groupId>com.zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
            <!-- 版本号可以不用指定，Spring Boot会选用合适的版本 -->
        </dependency>

        <!-- mysql数据库连接组件 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <!-- https://mvnrepository.com/artifact/io.zipkin.brave/brave-mysql -->
        <dependency>
            <groupId>io.zipkin.brave</groupId>
            <artifactId>brave-mysql</artifactId>
            <version>4.1.0</version>
        </dependency>


        <dependency>
            <groupId>com.tuodao.hechuan.mybatis.plugin</groupId>
            <artifactId>SerializablePlugin</artifactId>
            <version>${plugin.version}</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.2</version>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>5.1.39</version>
                    </dependency>
                    <dependency>
                        <groupId>tk.mybatis</groupId>
                        <artifactId>mapper</artifactId>
                        <version>3.3.6</version>
                    </dependency>
                    <dependency>
                        <groupId>com.tuodao.hechuan.mybatis.plugin</groupId>
                        <artifactId>SerializablePlugin</artifactId>
                        <version>1.0.0</version>
                    </dependency>
                </dependencies>
                <configuration>
                    <configurationFile>src/main/resources/mybatis-generator.xml</configurationFile>
                    <overwrite>true</overwrite>
                    <verbose>true</verbose>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

### mybatis-generator.xml 配置


```
 <!DOCTYPE generatorConfiguration 
      PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
      "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>

	<context id="generatorTables" targetRuntime="MyBatis3">
	
		<plugin type="com.tuodao.hechuan.mybatis.plugin.SerializableModelExamplePlugin"></plugin>
		
		<commentGenerator>
			<property name="suppressAllComments" value="true" />
		</commentGenerator>
		<jdbcConnection driverClass="com.mysql.jdbc.Driver"
			connectionURL="jdbc:mysql://121.41.36.41:3306/kaka?useUnicode=true&amp;characterEncoding=UTF-8"
			userId="kaka" password="kaka">
		</jdbcConnection>
		<javaTypeResolver>
			<property name="forceBigDecimals" value="false" />
		</javaTypeResolver>
		
		 <javaModelGenerator targetPackage="org.tdsquad.kaka.transport.db.model.user"
			targetProject="src/main/java">
			<property name="enableSubPackages" value="true" />
			<property name="trimStrings" value="true" />
		</javaModelGenerator>

		<sqlMapGenerator targetPackage="xml.user" targetProject="src/main/resources">
			<property name="enableSubPackages" value="true" />
		</sqlMapGenerator>

		<javaClientGenerator type="XMLMAPPER"
			targetPackage="org.tdsquad.kaka.transport.db.mapper.user" targetProject="src/main/java">
			<property name="enableSubPackages" value="true" />
		</javaClientGenerator>
		
<!-- 		<table tableName="u_user_info" enableCountByExample="true" enableSelectByExample="true" enableUpdateByExample="true" enableDeleteByExample="true"/> -->
		<table tableName="TRYKEY" enableCountByExample="true" enableSelectByExample="true" enableUpdateByExample="true" enableDeleteByExample="true">
		<generatedKey column="ID" sqlStatement=" select LAST_INSERT_ID() as val" identity="ID" type="pre"/>
		</table>


	</context>

</generatorConfiguration>
```
