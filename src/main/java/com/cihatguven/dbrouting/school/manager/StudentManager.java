package com.cihatguven.dbrouting.school.manager;

import com.cihatguven.dbrouting.school.controller.requests.SaveStudentRequest;
import com.cihatguven.dbrouting.school.database.DataSourceRouting;
import com.cihatguven.dbrouting.school.entity.Student;
import com.cihatguven.dbrouting.school.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentManager {
    private final StudentRepository studentRepository;
    private final DataSourceRouting dataSourceRouting;

    public Student createStudent(SaveStudentRequest request) {
        dataSourceRouting.setDbName(request.getDbName());
        dataSourceRouting.determineTargetDataSource();
        return studentRepository.save(Student.create(request));
    }
}
