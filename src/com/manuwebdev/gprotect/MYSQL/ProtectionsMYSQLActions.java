/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.gprotect.MYSQL;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides queries for MYSQL
 * @author Manuel Gauto
 */
public class ProtectionsMYSQLActions {
    /**
     * Bridge with MYSQL
     */
    MYSQLInterface mysqlInterface;
    
    /**
     * Connection to MYSQL
     */
    Connection conn;
    
    /**
     * Table name to use
     */
    public final String TABLE_NAME=mysqlInterface.getPrefix()+"protections";
    
    /**
     * 
     * @param mysqlInterface Connection with MYSQL
     */
    public ProtectionsMYSQLActions(MYSQLInterface mysqlInterface){
        this.mysqlInterface=mysqlInterface;
        //Get Connection to MYSQL
        this.conn=mysqlInterface.getMYSQLConnection();
    }    
    
    public boolean doesTableExist(String Table) {
        try {
            DatabaseMetaData dbm = mysqlInterface.getMYSQLConnection().getMetaData();
            // check if table is there
            ResultSet tables = dbm.getTables(null, null, Table, null);
            if (tables.next()) {
                // Table exists
                return true;
            } else {
                // Table does not exist
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProtectionsMYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void createTableIfNeeded() {
        if (doesTableExist(TABLE_NAME) == false) {
            try {
                Statement stmt = mysqlInterface.getMYSQLConnection().createStatement();

                String sql = "CREATE TABLE " + TABLE_NAME + "("
                        + "OWNER             VARCHAR(254), "
                        + "NAME              VARCHAR(254), "
                        + "X                 INTEGER, "
                        + "Y                 INTEGER, "
                        + "Z                 INTEGER, "
                        + "WORLD             VARCHAR(254))";

                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                //NOM NOM NOM
            }
        }
    }
}
