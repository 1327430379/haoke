package cn.haoke.mgmt.graphql;

import graphql.schema.DataFetchingEnvironment;

public interface MyDataFetcher {

    /**
     * GraphQL中查询的名称
     * @return
     */
    String filedName();

    /**
     * 数据的查询
     * @param environment
     * @return
     */
    Object dataFetcher(DataFetchingEnvironment environment);
}
