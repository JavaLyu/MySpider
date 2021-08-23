package com.nsh.service;

import com.nsh.dao.StudentMapper;
import com.nsh.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.Addressing;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author NSH
 * @create 2021-08-16 15:37
 */
@Service
public class StudentService {
    @Autowired
    StudentMapper studentMapper;

    public void addStudents(List<Student> students){
        for (Student student : students){
            studentMapper.insertStudent(student);
        }
    }
    //查询当前毕业同学的邮箱
    public List<String> selectAllGrade(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        Integer year = Integer.valueOf(dateFormat.format(date));
        return studentMapper.selectAllGradeNow(year);
    }
}
