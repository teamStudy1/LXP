package com.lxp;

import com.lxp.api.controller.CourseController;
import com.lxp.infrastructure.dao.CourseDao;
import com.lxp.infrastructure.dao.JdbcCourseDao;
import com.lxp.handler.CourseHandler;
import com.lxp.service.CourseService;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;


public class Main {
    public static void main(String[] args) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://localhost:3306/project_lxp"); // 본인 DB 주소
        mysqlDataSource.setUser("root"); // 본인 DB 아이디
        mysqlDataSource.setPassword("goilla"); // 본인 DB 비밀번호

        DataSource dataSource = mysqlDataSource;
        CourseDao courseDao = new JdbcCourseDao(dataSource);
        CourseService courseService = new CourseService(courseDao);
        CourseController courseController = new CourseController(courseService);
        CourseHandler courseHandler = new CourseHandler(courseController);

        courseHandler.start();

        System.out.println("프로그램을 종료합니다.");
    }
}