package com.lxp.util;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class QueryUtil {
    private static final Map<String, String> queries = new HashMap<>();

    static {
//        loadQueries("queries/category_queries.xml");
        loadQueries("queries/course_queries.xml");
        loadQueries("queries/section_queries.xml");
        loadQueries("queries/lecture_queries.xml");
        loadQueries("queries/tag_queries.xml");
//        loadQueries("queries/enrollment_queries.xml");
        loadQueries("queries/tag_queries.xml");
        loadQueries("queries/user_queries.xml");
    }

    private static void loadQueries(String xmlPath) {
        try {
            InputStream inputStream = QueryUtil.class.getClassLoader().getResourceAsStream(xmlPath);
            if (inputStream == null) {
                throw new RuntimeException("not found: " + xmlPath);
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("query");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element queryElement = (Element) nodeList.item(i);

                String key = queryElement.getAttribute("key");
                String sql = queryElement.getTextContent().trim();

                queries.put(key, sql);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("error loading queries.xml");
        }
    }

    public static String getQuery(String key) {
        String query = queries.get(key);
        if (query == null) {
            throw new RuntimeException("Query not found: " + key);
        }
        return query;
    }

    public static String buildInQuery(String key, int paramCount) {
        String query = getQuery(key);
        String placeholders = String.join(",", Collections.nCopies(paramCount, "?"));
        return query.replace(":in_clause", placeholders);
    }
}
