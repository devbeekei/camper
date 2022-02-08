package com.ss.camper.common.config;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StringType;

public class CustomMySQL5Dialect extends MySQL5Dialect {

    public CustomMySQL5Dialect() {
        super();
        this.registerFunction("group_concat", new StandardSQLFunction("group_concat", new StringType()));
    }



}
