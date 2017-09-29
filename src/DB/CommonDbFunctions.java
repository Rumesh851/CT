/*
 * ICBT Student ID : CL/MSCIT/11/03
 * University ID : 20127172
 * Author : Susiri Indika Gunasekara
 */

package DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommonDbFunctions {

    /**
     * To get the maximum value of the field in the database and create the next
     * value with the format
     *
     * @param idField Name of the field to get the max no.
     * @param table Name of the table
     * @param format Format of the value to be formated.
     * @param strWhere Filtering
     * @return Next value for the table
     * @throws SQLException should handle when call
     */
    public static String getNextNo(String idField, String table, String format, String strWhere) throws SQLException {
        String stm = "";
        int maxValue = 1;
        String maxValueString = "";
        stm = "SELECT IFNULL(MAX(" + idField + "),0) FROM " + table;
        if (strWhere.equals("") == false || strWhere == null) {
            stm = stm + " Where " + strWhere;
        }
        ConnectionProvider objConnection = ConnectionProvider.getInstance();

        maxValue = Integer.parseInt(objConnection.executeSQLScaler(stm)) + 1; // increment for the next id number
        maxValueString = Integer.toString(maxValue);
int fl,ml;
fl=format.length();
ml=maxValueString.length();

        if (fl >ml ) {
            maxValueString = format.substring(0, fl-ml) + maxValueString;
        }
        return maxValueString;
    }

    /**
     * To check data exists in the table
     * @param table Name of the table
     * @param strWhere Filtering
     * @return Exists or not
     * @throws SQLException should handle when call
     */
    public static boolean isExists(String table,String strWhere) throws SQLException {
        String stm = "";
        stm = "SELECT * FROM " + table;
        if (strWhere.equals("") == false || strWhere == null) {
            stm = stm + " Where " + strWhere;
        }
        ConnectionProvider objConnection = ConnectionProvider.getInstance();
        ResultSet rs= objConnection.executeSQLResultSet(stm);
        
        return rs.next();
    }
    

}
