package com.cihatguven.dbrouting.school.controller;

import com.cihatguven.dbrouting.school.controller.requests.SaveStudentRequest;
import com.cihatguven.dbrouting.school.entity.Student;
import com.cihatguven.dbrouting.school.manager.StudentManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {
    private final StudentManager studentManager;

    @PostMapping("/create")
    public ResponseEntity<Student> insertStudent(@RequestBody SaveStudentRequest request) {
        return ResponseEntity.ok(studentManager.createStudent(request));
    }

}