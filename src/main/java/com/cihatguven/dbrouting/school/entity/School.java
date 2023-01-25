package com.cihatguven.dbrouting.school.entity;

import com.cihatguven.dbrouting.school.controller.requests.SaveSchoolRequest;
import com.cihatguven.dbrouting.school.controller.requests.UpdateSchoolRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "dbName", name = "UK_SCHOOL_DB_NAME"),
})
public class School {
    @Id
    @Column(nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String dbName;

    private String code;

    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @PreRemove
    protected void onDelete() {
        deletedAt = LocalDateTime.now();
    }

    public static School create(SaveSchoolRequest request, String dbNamePrefix) {
        School school = new School();
        school.code = request.getCode();
        school.title = request.getTitle();
        school.dbName = dbNamePrefix.concat(request.getDbName());
        return school;
    }

    public School update(UpdateSchoolRequest request) {
        School school = this;
        school.code = request.getCode();
        school.title = request.getTitle();
        return school;
    }
}