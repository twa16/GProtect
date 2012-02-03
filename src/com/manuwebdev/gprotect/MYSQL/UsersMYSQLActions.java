/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuwebdev.gprotect.MYSQL;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;

/**
 * Provides queries for MYSQL
 * @author Manuel Gauto
 */
public class UsersMYSQLActions {
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
    public final String TABLE_NAME=mysqlInterface.getPrefix()+"users";
    
    /**
     * 
     * @param mysqlInterface Connection with MYSQL
     */
    public UsersMYSQLActions(MYSQLInterface mysqlInterface){
        this.mysqlInterface=mysqlInterface;
        //Get Connection to MYSQL
        this.conn=mysqlInterface.getMYSQLConnection();
    }    
    
    /**
     * Checks if a player has already been
     * assigned an ID
     * @param playerName Name to look up
     * @return true if player has an id
     */
    public boolean isPlayerInDatabase(String playerName){
        //Statement
        final String QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE NAME = ?";
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
    
    /**
     * Get ID of player returns -1 if
     * player does not have an ID
     * @param playerName Player to lookup
     * @return ID of player 
     */
    public int getPlayerID(String playerName){
        //Statement
        final String QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE NAME = ?";
        try {
            //Get the statement
            PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
            //Set playername
            ps.setString(1, playerName);
            //Execute
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt("ID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersMYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    /**
     * Checks if the table exists
     * 
     * @param Table Table to look for
     * @return if table exists
     */
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
            Logger.getLogger(UsersMYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Builds a table if it does 
     * not exist
     */
    public void createTableIfNeeded() {
        if (doesTableExist(TABLE_NAME) == false) {
            try {
                Statement stmt = mysqlInterface.getMYSQLConnection().createStatement();

                String sql = "CREATE TABLE " + TABLE_NAME + "("
                        + "ID                INTEGER, "
                        + "NAME              VARCHAR(254))";

                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                //NOM NOM NOM
            }
        }
    }
}
