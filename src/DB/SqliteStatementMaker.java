/*
 * ICBT Student ID : CL/MSCIT/11/03
 * University ID : 20127172
 * Author : Susiri Indika Gunasekara
 */

package DB;

import com.sun.org.apache.xpath.internal.operations.Equals;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SqliteStatementMaker {

    private List<Param> prmList;
    private PreparedStatement statement;

    public SqliteStatementMaker(PreparedStatement st) {
        prmList = new ArrayList<>();
        statement = st;
    }

    public void clear() {
        prmList.clear();
    }

    public void addParam(int index, Object value) {
        Param p = new Param(index, value);
        prmList.add(p);
    }

    /**
     * @return the prmList
     */
    public List<Param> getPrmList() {
        return prmList;
    }

    /**
     * @return the statement
     */
    public PreparedStatement getStatement() {

        try {

            for (Param p : prmList) {
       
                if (p.getParamValue().getClass().equals(String.class) )
                {                    
                    statement.setString(p.getParamIndex(),(String)p.getParamValue());
                    continue;
                }
                if (p.getParamValue().getClass().equals(Integer.class))
                {                    
                    statement.setInt(p.getParamIndex(),(Integer)p.getParamValue());
                    continue;
                }
                if (p.getParamValue().getClass().equals(Double.class))
                {                    
                    statement.setDouble(p.getParamIndex(),(Double)p.getParamValue());
                    continue;
                }
                if (p.getParamValue().getClass().equals(Date.class))
                {                    
                    statement.setDate(p.getParamIndex(),(Date)p.getParamValue());
                    continue;
                }
                if (p.getParamValue().getClass().equals(Boolean.class))
                {                    
                    statement.setBoolean(p.getParamIndex(),(Boolean)p.getParamValue());
                    continue;
                }
                if (p.getParamValue().getClass().equals(InputStream.class))
                {       
                   
                    statement.setBlob(p.getParamIndex(),(InputStream)p.getParamValue());
                }

        }

        return statement;
    }
    catch (SQLException ex

    
        ) {
            return statement;
    }
}

/**
 * @param statement the statement to set
 */
public void setStatement(PreparedStatement statement) {
        this.statement = statement;
    

}

    class Param {

    private int paramIndex;
    private Object paramValue;

    public Param(int index, Object value) {
        paramIndex = index;
        paramValue = value;

    }

    /**
     * @return the paramIndex
     */
    public int getParamIndex() {
        return paramIndex;
    }

    /**
     * @param paramIndex the paramIndex to set
     */
    public void setParamIndex(int paramIndex) {
        this.paramIndex = paramIndex;
    }

    /**
     * @return the paramValue
     */
    public Object getParamValue() {
        return paramValue;
    }

    /**
     * @param paramValue the paramValue to set
     */
    public void setParamValue(Object paramValue) {
        this.paramValue = paramValue;
    }

}
}
