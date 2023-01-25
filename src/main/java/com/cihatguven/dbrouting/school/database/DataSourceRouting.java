package com.cihatguven.dbrouting.school.database;

import com.cihatguven.dbrouting.school.entity.School;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class DataSourceRouting extends AbstractDataSource {

    private static final ThreadLocal<School> currentSchool = new ThreadLocal<>();

    private final HashMap<String, DataSource> dataSourceHashMap = new HashMap<>();

    private final DbSettings dbSettings;

    private String dbName;

    @Override
    public Connection getConnection() throws SQLException {
        return this.determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return this.determineTargetDataSource().getConnection(username, password);
    }

    public DataSource determineTargetDataSource() {
        String lookupKey = dbName;

        DataSource dataSource = dataSourceHashMap.put(lookupKey, createDataSource(lookupKey));
        if (dataSource == null) {
            dataSource = createDataSource(lookupKey);
            dataSourceHashMap.put(lookupKey, dataSource);
        }

        return dataSource;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public static void execute(School school) {
        try {
            currentSchool.set(school);
        } finally {
            currentSchool.remove();
        }
    }

    public DataSource createDataSource(String dbName) {
        if (dbName == null) {
            dbName = dbSettings.getDbNamePrefix().concat("default");
        }

        return DataSourceBuilder
                .create()
                .url(dbSettings.getSchoolDatabaseName()
                        .concat(dbName)
                        .concat(dbSettings.getDbNamePostfix()))
                .username(dbSettings.getUsername())
                .password(dbSettings.getPassword())
                .build();
    }

    protected String determineDbName() {
        try {
            return currentSchool.get().getDbName();
        } catch (Exception e) {
            //
        }
        return null;
    }

}
