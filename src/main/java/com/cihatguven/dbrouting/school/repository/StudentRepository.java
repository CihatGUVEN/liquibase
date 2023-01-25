package com.cihatguven.dbrouting.school.repository;

import com.cihatguven.dbrouting.school.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {

}
