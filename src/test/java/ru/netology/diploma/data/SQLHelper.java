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
    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), System.getProperty("db.user"), System.getProperty("db.password"));
    }

    @SneakyThrows
    public static SQLHelper.StatusInfo getPaymentStatusInfoSQL() {
        var statusSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        var conn = getConn();
        var status = runner.query(conn, statusSQL, new ScalarHandler<String>());
        return new SQLHelper.StatusInfo(status);
    }
    @SneakyThrows
    public static SQLHelper.StatusInfo getCreditStatusInfoSQL() {
        var statusSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        var conn = getConn();
        var status = runner.query(conn, statusSQL, new ScalarHandler<String>());
        return new SQLHelper.StatusInfo(status);
    }
    @Value
    public static class StatusInfo {
        String status;
    }
    @SneakyThrows
    public static SQLHelper.AmountInfo getAmountInfoSQL() {
        var amountSQL = "SELECT amount FROM payment_entity ORDER BY created DESC LIMIT 1";
        var conn = getConn();
        var amount = runner.query(conn, amountSQL, new ScalarHandler<Integer>());
        return new SQLHelper.AmountInfo(amount);
    }

    @Value
    public static class AmountInfo {
        int amount;
    }
    @SneakyThrows
    public static void cleanDatabaseSQL() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM payment_entity");
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");
    }

}
