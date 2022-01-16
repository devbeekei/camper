package com.ss.camper.config;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StringType;

public class CustomMySQL57Dialect extends MySQL57Dialect {

    public CustomMySQL57Dialect() {
        super();
        this.registerFunction("group_concat", new StandardSQLFunction("group_concat", new StringType()));
    }

}
