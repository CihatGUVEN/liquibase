package com.cihatguven.liquibase;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.DiffResult;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.DiffToChangeLog;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LiquiBaseDiffAsSql {

    private static void emptyTheFileBeforeDiff() {

        File f = new File("log.mysql.sql");
        try {
            if (f.createNewFile())
                System.out.println("File created: " + f.getName());
            else {
                System.out.println("File already exists.");
                new PrintWriter("log.mysql.sql").close();
            }
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

    public static void diff()
            throws LiquibaseException, IOException, ParserConfigurationException, SQLException, SQLException {

        emptyTheFileBeforeDiff();

        Connection referenceConnection =
                DriverManager.getConnection("jdbc:mariadb://localhost:3306/country", "root", "12345");

        Connection targetConnection =
                DriverManager.getConnection("jdbc:mariadb://localhost:3306/empty_country", "root", "12345");

        Liquibase liquibase = null;

        try {

            Database referenceDatabase = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(referenceConnection));
            Database targetDatabase = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(targetConnection));

            liquibase = new Liquibase("", new FileSystemResourceAccessor(), referenceDatabase);
            DiffResult diffResult = liquibase.diff(referenceDatabase, targetDatabase, new CompareControl());
            new DiffToChangeLog(diffResult, new DiffOutputControl()).print("log.mysql.sql");

        } finally {
            if (liquibase != null) {
                liquibase.forceReleaseLocks();
            }
        }

    }
}
