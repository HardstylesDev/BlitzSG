package me.syesstyles.blitz.utils.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;

public class Database {

    private static String host = "104.128.53.75"; // The IP-address of the database host.
    private static String database = "s6_server"; // The name of the database.
    private static String user = "u6_hniIbS87Lb"; // The name of the database user.
    private static String pass = "u13wROasTK61@G.pRMqN^+mt"; // The password of the database user.


    static String jbdcUrl = String.format("jdbc:mysql://%s:3306/%s?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=CET", host, database);
    //Call the get connection method.
    private static DataSource dataSource;

    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    //Get the DataSource. If not available create the new one
    //It is not threadsafe. I didn't wanted to complicate things.
    public DataSource getDataSource() {
        if (null == dataSource) {
            System.out.println("No DataSource is available. We will create a new one.");
            createDataSource();
        }
        return dataSource;
    }

    //To create a DataSource and assigning it to variable dataSource.
    public void createDataSource() {
        HikariConfig hikariConfig = getHikariConfig();
        System.out.println("Configuration is ready.");
        System.out.println("Creating the HiakriDataSource and assigning it as the global");
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        dataSource = hikariDataSource;
    }

    public HikariConfig getHikariConfig() {
        System.out.println("Creating the config with HikariConfig with maximum pool size of 5");
        HikariConfig hikaConfig = new HikariConfig();

        //This is same as passing the Connection info to the DriverManager class.
        //your jdbc url. in my case it is mysql.
        hikaConfig.setJdbcUrl(jbdcUrl);
        //username

        hikaConfig.setUsername(user);
        //password
        hikaConfig.setPassword(pass);
        //driver class name
        hikaConfig.setDriverClassName("com.mysql.jdbc.Driver");

        // Information about the pool
        //pool name. This is optional you don't have to do it.
        hikaConfig.setPoolName("MysqlPool-1");

        //the maximum connection which can be created by or resides in the pool
        hikaConfig.setMaximumPoolSize(5);

        //how much time a user can wait to get a connection from the pool.
        //if it exceeds the time limit then a SQlException is thrown
        hikaConfig.setConnectionTimeout(Duration.ofSeconds(30).toMillis());

        //The maximum time a connection can sit idle in the pool.
        // If it exceeds the time limit it is removed form the pool.
        // If you don't want to retire the connections simply put 0.
        hikaConfig.setIdleTimeout(Duration.ofMinutes(2).toMillis());

        return hikaConfig;
    }
}
