package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GateDAO {
    private final Connection conn;

    public GateDAO( Connection conn ) { this.conn=conn; }

    public List<String> getGates() throws SQLException {

         final String query= "SELECT * FROM gate AS g ORDER BY substring(g.numerogate from 2)::int";
         List<String> resultDB = new ArrayList<String>();


        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        while ( rs.next() ) {
            resultDB.add(rs.getString("numerogate"));
        }
        return resultDB;
            }//Parentesi getGates
}//Parentesi Finale
