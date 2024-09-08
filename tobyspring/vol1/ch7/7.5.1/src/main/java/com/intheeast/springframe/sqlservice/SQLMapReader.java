package com.intheeast.springframe.sqlservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;

@Component
public class SQLMapReader {
    private SQLMap sqlMap;

    public SQLMapReader() {
        // JSON 파일을 resources 디렉토리에서 읽어와서 SQLMap 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // ClassPathResource로 resources/sqlmap.json 파일을 읽어옴
// C:\development\Workspace\codes\spring_legacy\tobyspring6\vol1\ch7\7.5.1\src\main\resources\sqlmap.json
            ClassPathResource resource = new ClassPathResource("sqlmap.json");

            // InputStream으로 파일을 읽고 ObjectMapper로 변환
            InputStream inputStream = resource.getInputStream();
            this.sqlMap = objectMapper.readValue(inputStream, SQLMap.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SQLMap getSqlMap() {
        return sqlMap;
    }
}