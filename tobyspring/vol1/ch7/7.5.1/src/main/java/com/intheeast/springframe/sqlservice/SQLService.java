package com.intheeast.springframe.sqlservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SQLService {

    private final SQLMapReader sqlMapReader;

    @Autowired
    public SQLService(SQLMapReader sqlMapReader) {
        this.sqlMapReader = sqlMapReader;
    }

    /*
     public class SQLQuery {
    	private String key;
    	private String query;
    }
     */
    // SQLMap -> List<SQLQuery> sqlmap;
    // 특정 SQL 쿼리를 key로 찾아서 반환하는 메서드
    public String getQueryByKey(String key) {
        return sqlMapReader.getSqlMap().getSqlmap().stream()
                .filter(sqlQuery -> sqlQuery.getKey().equals(key))
                .map(sqlQuery -> sqlQuery.getQuery())
                .findFirst()
                .orElse(null);
    }
}