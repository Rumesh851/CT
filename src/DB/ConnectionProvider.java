/*
 * ICBT Student ID : CL/MSCIT/11/03
 * University ID : 20127172
 * Author : Susiri Indika Gunasekara
 */

package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * This provide connection to sqlite database for data access
 *
 */
public class ConnectionProvider {

    /**
     * @return the transactionProcessing
     */
    public static boolean isTransactionProcessing() {
        return transactionProcessing;
    }

    public enum TransactionIssolation {

        TRANSACTION_NONE(0),
        TRANSACTION_READ_UNCOMMITTED(1),
        TRANSACTION_READ_COMMITTED(2),
        TRANSACTION_REPEATABLE_READ(4),
        TRANSACTION_SERIALIZABLE(8);
        private final int value;

        TransactionIssolation(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }
    private static Connection conn = null; // Sql Connection

    private static String error;
    private static boolean transactionProcessing;

    private ConnectionProvider() {
        getConnection();
    }

    public static ConnectionProvider getInstance() {

        return DalHolder.INSTANCE;
    }

    private static class DalHolder {

        private static final ConnectionProvider INSTANCE = new ConnectionProvider();
    }

    public static String getError() {
        return error;
    }

    public static Connection getConnection() {
        String url = "";

//            url = "jdbc:sqlite:" + new views.Settings().getSetting("dbFile"); // path of the database file
        url = "jdbc:sqlite:CT.db";

        // create a connection to the database
        try {

            if (conn == null) {
                conn = DriverManager.getConnection(url);
            }

        } catch (SQLException ex) {
            error = ex.getMessage();
        }
        return conn; // return the connection
    }

    public static void beginTranstion(TransactionIssolation issolation) {
        try {

            conn.setAutoCommit(false);// begin transaction     
            conn.setTransactionIsolation(issolation.value);
            transactionProcessing = true;
        } catch (SQLException ex) {
            error = ex.getMessage();
        }
    }

    public static void commitTranstion() {
        try {
            conn.commit(); // commit transaction
            conn.setAutoCommit(true);// end the transation  
            transactionProcessing = false;
        } catch (SQLException ex) {
            error = ex.getMessage();
        }
    }

    public static boolean executeSQL(String statement) throws SQLException {
        try {
            Statement stmt; // sql statement
            stmt = conn.createStatement();
            stmt.executeUpdate(statement);
            stmt.close();
            return true;
        } catch (SQLException ex) {
            error = ex.getMessage();
            throw ex;
        }
    }

    /**
     * To get a single value from a SQL statement ex: "select max(id) from
     * table"
     *
     * @param statement Sql statement to be executed.
     * @return a Single value of a SQL statement.
     * @throws SQLException should handle when call
     */
    public static String executeSQLScaler(String statement) throws SQLException {

        Statement stmt; // sql statement
        try {

            stmt = conn.createStatement();
            String value = "";
            ResultSet rs = stmt.executeQuery(statement);
            if (rs.next()) {
                value = rs.getString(1); // get the first collum value
            }
            stmt.close();
            return value; // returns the value as string
        } catch (SQLException ex) {
            error = ex.getMessage();
            throw ex;
        } finally {
            stmt = null;
        }
    }

    public static ResultSet executeSQLResultSet(String statement) throws SQLException {
        try {
            Statement stmt; // sql statement
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(statement);
            //stmt.close(); // release resource
            //System.out.println(rs.getString(0));
            return rs;
        } catch (SQLException ex) {
            error = ex.getMessage();
            throw ex;
        }
    }

    public static void rollbackTranstion() {
        try {
            conn.rollback();// rollback transaction
            conn.setAutoCommit(true);// end the transation  
            transactionProcessing = false;
        } catch (SQLException ex) {
            error = ex.getMessage();
        }
    }
}
