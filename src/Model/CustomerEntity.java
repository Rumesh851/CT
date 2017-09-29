/*
 * ICBT Student ID : CL/MSCIT/11/03
 * University ID : 20127172
 * Author : Susiri Indika Gunasekara
 */
package Model;

import DB.ConnectionProvider;
import DB.CommonDbFunctions;
import DB.SqliteStatementMaker;
import java.sql.ResultSet;

public class CustomerEntity {

    public int CustomerId;

    public String FirstName;

    public String LastName;

    public String Telephone;

    public String Address1;

    public String Address2;

    public String Address3;

    public boolean IsActive;

    public String error;

    public boolean Save() {
        ConnectionProvider objConnection = ConnectionProvider.getInstance();
        try {

            if (CommonDbFunctions.isExists("Customer", "CustomerId='" + CustomerId
                    + "'") == false) {
                objConnection.beginTranstion(ConnectionProvider.TransactionIssolation.TRANSACTION_SERIALIZABLE); // begin the transaction

                String nextID = CommonDbFunctions.getNextNo("CustomerId", "Customer", "0", ""); // get the next store Id

                String sql = "INSERT INTO Customer ([CustomerId],[FirstName],[LastName]	,[Telephone],[Address1]	,[Address2]	,[Address3]	,[IsActive]	) "
                        + "Values (?,?,?,?,?,?,?,1)";

                SqliteStatementMaker ssm = new SqliteStatementMaker(objConnection.getConnection().prepareStatement(sql));
                ssm.addParam(1, nextID);
                ssm.addParam(2, FirstName);
                ssm.addParam(3, LastName);
                ssm.addParam(4, Telephone);
                ssm.addParam(5, Address1);
                ssm.addParam(6, Address2);
                ssm.addParam(7, Address3);

                ssm.getStatement().execute();// execute with parameters
                ssm = null;
                objConnection.commitTranstion(); // commit transaction
                return true;
            } else {
                objConnection.beginTranstion(ConnectionProvider.TransactionIssolation.TRANSACTION_READ_COMMITTED);
                String sql = "UPDATE Customer SET [FirstName]=?,[LastName]=?,[Telephone]=?,[Address1]=?,[Address2]=?,[Address3]=?,[IsActive]=? WHERE CustomerId='" + CustomerId + "')";

                SqliteStatementMaker ssm = new SqliteStatementMaker(objConnection.getConnection().prepareStatement(sql));

                ssm.addParam(1, FirstName);
                ssm.addParam(2, LastName);
                ssm.addParam(3, Telephone);
                ssm.addParam(4, Address1);
                ssm.addParam(5, Address2);
                ssm.addParam(6, Address3);

                ssm.getStatement().execute();// execute with parameters
                ssm = null;
                objConnection.commitTranstion(); // commit transaction
                return true;
            }

        } catch (Exception ex) {
            objConnection.rollbackTranstion(); // roleback Transaction
            error = ex.getMessage();
            return false;
        }
    }

    @Override
    public String toString() {
        return FirstName + " " + LastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false; // if obj is null return false
        }
        if (obj == this) { // if comparing object is this reutn true
            return true;
        }
        if (!(obj instanceof CustomerEntity)) {
            // if comparing object is not a instance of this return false
            return false;
        }
        return ((CustomerEntity) obj).CustomerId == this.CustomerId;
        // if the comparing object is equal return true

    }

    public boolean Get(int customerId) {
        ConnectionProvider objConnection = ConnectionProvider.getInstance();
        try {
            String sql = "SELECT * FROM CUSTOMER";
            ResultSet rs = objConnection.executeSQLResultSet(sql);
            while (rs.next()) {

                this.IsActive = rs.getBoolean("IsActive");
                this.FirstName = rs.getString("FirstName");
                this.LastName = rs.getString("LastName");
                this.Telephone = rs.getString("Telephone");
                this.Address1 = rs.getString("Address1");
                this.Address2 = rs.getString("Address2");
                this.Address3 = rs.getString("Address3");
                this.CustomerId = Integer.parseInt(rs.getString("CustomerId"));
            }
            return true;

        } catch (Exception ex) {
            error = ex.getMessage();
            return false;
        }

    }
}
