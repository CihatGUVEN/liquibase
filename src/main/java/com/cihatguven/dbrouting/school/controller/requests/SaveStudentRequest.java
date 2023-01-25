package com.cihatguven.dbrouting.school.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveStudentRequest {
    private String email;
    private String username;
    private String dbName;
}
