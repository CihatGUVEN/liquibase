package com.cihatguven.dbrouting.school.manager;

import com.cihatguven.dbrouting.school.controller.requests.SaveSchoolRequest;
import com.cihatguven.dbrouting.school.controller.requests.UpdateSchoolRequest;
import com.cihatguven.dbrouting.school.database.DataSourceRouting;
import com.cihatguven.dbrouting.school.database.DbSettings;
import com.cihatguven.dbrouting.school.database.SchoolDbInitializer;
import com.cihatguven.dbrouting.school.entity.School;
import com.cihatguven.dbrouting.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolManager {

    private final SchoolRepository schoolRepository;
    private final SchoolDbInitializer schoolDbInitializer;
    private final DbSettings dbSettings;

    private final DataSourceRouting dataSourceRouting;

    public void createSchoolDatabase(String schoolId) {
        School school = schoolRepository.findById(schoolId).orElseThrow(RuntimeException::new);
        try {
            schoolDbInitializer.init(school.getDbName());
            dataSourceRouting.execute(school);
        } catch (Exception e) {
            log.warn("exception", e);
        }
    }

    public School createSchool(SaveSchoolRequest request) {
        School school = schoolRepository.save(School.create(request, dbSettings.getDbNamePrefix()));
        createSchoolDatabase(school.getId());
        return school;
    }

    public void saveSchool(School school) {
        schoolRepository.save(school);
    }

    public School getSchoolById(String schoolId) {
        return schoolRepository.findById(schoolId).orElseThrow(() -> new RuntimeException("User not found by id: " + schoolId));
    }

    public School updateSchool(String id, UpdateSchoolRequest request) {
        School school = schoolRepository
                .findById(id).orElseThrow(() -> new RuntimeException("User not found by id: " + id));
        return schoolRepository.save(school.update(request));
    }

    public void deleteSchool(String id) {
        schoolRepository.deleteById(id);
    }

    public Boolean schoolExistsByCode(String code) {
        return schoolRepository.existsByCode(code);
    }

}
