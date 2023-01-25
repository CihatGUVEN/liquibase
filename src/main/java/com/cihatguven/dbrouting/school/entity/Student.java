package com.cihatguven.dbrouting.school.entity;

import com.cihatguven.dbrouting.school.controller.requests.SaveStudentRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "username", name = "UK_USER_USERNAME"),
        @UniqueConstraint(columnNames = "email", name = "UK_USER_EMAIL")
})
public class Student{
    @Id
    @Column(nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String username;
    private String email;

    public static Student create(SaveStudentRequest request) {
        Student student = new Student();
        student.setEmail(request.getEmail());
        student.setUsername(request.getUsername());
        return student;
    }
}
