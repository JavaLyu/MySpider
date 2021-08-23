package com.nsh.dao;

import com.nsh.pojo.Student;

import java.util.List;

/**
 * @author NSH
 * @create 2021-08-16 15:31
 */
public interface StudentMapper {
    int insertStudent(Student student);

    List<String> selectAllGradeNow(Integer year);
}
