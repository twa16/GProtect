/*
 * The MIT License
 *
 * Copyright 2012 Manuel Gauto.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.manuwebdev.gprotect.MYSQL;

import com.manuwebdev.gprotect.Protection;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.block.Block;

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
    
    public Protection getProtection(Block b){
        
    } 
    
    public void addProtection(Protection p){
        //Statement
        final String QUERY = "INSERT INTO" + TABLE_NAME + " VALUES()";
        try {
           //Get the statement
            PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
            //set platyername as part of query
            ps.setString(1, playerName);
            //Get results
            ResultSet rs = ps.executeQuery();
            //Return if ResultSet is null
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UsersMYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
                        + "OWNER             VARCHAR(16), "
                        + "NAME              VARCHAR(50), "
                        + "ALLOWED           VARCHAR(254), "
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
