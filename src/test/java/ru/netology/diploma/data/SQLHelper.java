package ru.netology.diploma.data;

import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();
    private SQLHelper() {
    }
    private static Connection getConnMysql() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), System.getProperty("db.user"), System.getProperty("db.password"));
    }

    private static Connection getConnPostgres() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "app", "pass");
    }
    @SneakyThrows
    public static SQLHelper.StatusInfo getPaymentStatusInfoMysql() {
        var statusMysql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        var conn = getConnMysql();
        var status = runner.query(conn, statusMysql, new ScalarHandler<String>());
        return new SQLHelper.StatusInfo(status);
    }
    @SneakyThrows
    public static SQLHelper.StatusInfo getPaymentStatusInfoPostgres() {
        var statusPostgres = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        var conn = getConnPostgres();
        var status = runner.query(conn, statusPostgres, new ScalarHandler<String>());
        return new SQLHelper.StatusInfo(status);
    }
    @SneakyThrows
    public static SQLHelper.StatusInfo getCreditStatusInfoMysql() {
        var statusMysql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        var conn = getConnMysql();
        var status = runner.query(conn, statusMysql, new ScalarHandler<String>());
        return new SQLHelper.StatusInfo(status);
    }
    @SneakyThrows
    public static SQLHelper.StatusInfo getCreditStatusInfoPostgres() {
        var statusPostgres = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        var conn = getConnPostgres();
        var status = runner.query(conn, statusPostgres, new ScalarHandler<String>());
        return new SQLHelper.StatusInfo(status);
    }
    @Value
    public static class StatusInfo {
        String status;
    }
    @SneakyThrows
    public static SQLHelper.AmountInfo getAmountInfoMysql() {
        var amountMysql = "SELECT amount FROM payment_entity ORDER BY created DESC LIMIT 1";
        var conn = getConnMysql();
        var amount = runner.query(conn, amountMysql, new ScalarHandler<Integer>());
        return new SQLHelper.AmountInfo(amount);
    }
    @SneakyThrows
    public static SQLHelper.AmountInfo getAmountInfoPostgres() {
        var amountPostgres = "SELECT amount FROM payment_entity ORDER BY created DESC LIMIT 1";
        var conn = getConnPostgres();
        var amount = runner.query(conn, amountPostgres, new ScalarHandler<Integer>());
        return new SQLHelper.AmountInfo(amount);
    }
    @Value
    public static class AmountInfo {
        int amount;
    }
    @SneakyThrows
    public static void cleanDatabaseMysql() {
        var connection = getConnMysql();
        runner.execute(connection, "DELETE FROM payment_entity");
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");
    }
    @SneakyThrows
    public static void cleanDatabasePostgres() {
        var connection = getConnPostgres();
        runner.execute(connection, "DELETE FROM payment_entity");
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");
    }
}
