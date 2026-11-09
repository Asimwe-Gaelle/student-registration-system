package com.example.student_registration_system.repository;

import com.example.student_registration_system.model.Student;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class StudentRepository {

    private final DataSource dataSource;

    public StudentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Student student) {
        String sql = "INSERT INTO students (full_name, age, email) VALUES (?, ?, ?)";
        String fullName = student.getFirstName() + " " + student.getLastName();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getEmail());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> findAll() {
        String sql = "select * from students";
        List<Student> students = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student();
                student.setAge(rs.getInt("age"));
                student.setEmail(rs.getString("email"));

                // Split full_name into first and last name
                String[] name = rs.getString("full_name").split(" ", 2);
                student.setFirstName(name[0]);
                student.setLastName(name.length > 1 ? name[1] : "");

                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
}