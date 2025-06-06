package model;

import database.ConnessioneDatabase;
import java.sql.Connection;

public class test {
    public static void main(String[] args) {
        try {
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            if (conn != null) {
                System.out.println("✅ Connessione al database riuscita.");
            }
        } catch (Exception e) {
            System.err.println("❌ Connessione fallita: " + e.getMessage());
        }
    }
}

