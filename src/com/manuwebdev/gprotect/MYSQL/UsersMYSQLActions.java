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

import com.manuwebdev.gprotect.GProtect;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.entity.Player;

/**
 * Provides queries for MYSQL
 *
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
    public final String TABLE_NAME = mysqlInterface.getPrefix() + "users";

    /**
     *
     * @param mysqlInterface Connection with MYSQL
     */
    public UsersMYSQLActions(MYSQLInterface mysqlInterface) {
        this.mysqlInterface = mysqlInterface;
        //Get Connection to MYSQL
        this.conn = mysqlInterface.getMYSQLConnection();
    }

    /**
     * Checks if a player has already been assigned an ID
     *
     * @param playerName Name to look up
     * @return true if player has an id
     */
    public boolean isPlayerInDatabase(String playerName) {
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
     * Get ID of player returns -1 if player does not have an ID
     *
     * @param playerName Player to lookup
     * @return ID of player
     */
    public int getPlayerID(String playerName) {
        //Statement
        final String QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE NAME = ?";
        try {
            //Get the statement
            PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
            //Set playername
            ps.setString(1, playerName);
            //Execute
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersMYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    /**
     * Check if player is allowed
     *
     * @param ID Player to check for
     * @param p Player who owns block
     * @return
     */
    public boolean isAllowed(int ID, Player p) {
        //Statement
        final String QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE NAME = ? AND ID = ?";
        try {
            //Get the statement
            PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
            //set platyername as part of query
            ps.setString(1, p.getName());
            //Set ID to look for
            ps.setInt(2, ID);
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
     * Check if a player is allowed
     *
     * @param isallowed Player to check for
     * @param p block owner
     * @return
     */
    public boolean isAllowed(Player isallowed, Player p) {
        //Statement
        final String QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE NAME = ? AND ID = ?";
        try {
            int ID = getPlayerID(isallowed.getName());
            //Get the statement
            PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
            //set platyername as part of query
            ps.setString(1, p.getName());
            //Set ID to look for
            ps.setInt(2, ID);
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
     * Add a player to an allowed list
     *
     * @param p Player who is the owner of the block
     * @param toadd Player to add
     */
    public void addAllowedPlayer(Player p, Player toadd) {
        if (isAllowed(toadd, p) == false) {
            //Statement
            final String QUERY = "UPDATE " + TABLE_NAME + " SET ALLOWED = ? WHERE NAME = ?";
            try {
                //Get the statement
                PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
                int ID = getPlayerID(toadd.getName());
                ps.setString(1, getAllowedPlayerIDsString(p) + String.valueOf(ID));
                ps.setString(2, p.getName());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UsersMYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            p.sendRawMessage(GProtect.MessageColor + "Player already allowed.");
        }

    }

    /**
     * Add a player to an allowed list
     *
     * @param p Player who is the owner of the block
     * @param toadd Player to add
     */
    public void addAllowedPlayer(Player p, String toadd) {
        if (isAllowed(getPlayerID(toadd), p) == false) {
            //Statement
            final String QUERY = "UPDATE " + TABLE_NAME + " SET ALLOWED = ? WHERE NAME = ?";
            try {
                //Get the statement
                PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
                int ID = getPlayerID(toadd);
                ps.setString(1, getAllowedPlayerIDsString(p) + String.valueOf(ID));
                ps.setString(2, p.getName());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UsersMYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            p.sendRawMessage(GProtect.MessageColor + "Player already allowed.");
        }

    }

    /**
     * Get IDs allowed by player
     *
     * @param p
     * @return
     */
    public ArrayList<Integer> getAllowedPlayerIDs(Player p) {
        //Statement
        final String QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE NAME = ?";
        ArrayList<Integer> allowed = new ArrayList<Integer>();
        try {
            //Get the statement
            PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
            //Set playername
            ps.setString(1, p.getName());
            //Execute
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String allowedstring = rs.getString("ALLOWED");
                String[] parts = allowedstring.split(":");
                for (String part : parts) {
                    allowed.add(Integer.parseInt(part));
                }
            }
            return allowed;
        } catch (SQLException ex) {
            Logger.getLogger(UsersMYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String getAllowedPlayerIDsString(Player p) {
        //Statement
        final String QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE NAME = ?";
        try {
            //Get the statement
            PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
            //Set playername
            ps.setString(1, p.getName());
            //Execute
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("ALLOWED");

            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersMYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get a player name by using its ID. Returns null if the player is not
     * found
     *
     * @param ID
     * @return
     */
    public String getPlayerbyID(int ID) {
        //Statement
        final String QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";
        try {
            //Get the statement
            PreparedStatement ps = (PreparedStatement) mysqlInterface.getMYSQLConnection().prepareStatement(QUERY);
            //Set playername
            ps.setInt(1, ID);
            //Execute
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("NAME");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersMYSQLActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
     * Builds a table if it does not exist
     */
    public void createTableIfNeeded() {
        if (doesTableExist(TABLE_NAME) == false) {
            try {
                Statement stmt = mysqlInterface.getMYSQLConnection().createStatement();

                String sql = "CREATE TABLE " + TABLE_NAME + "("
                        + "ID                INTEGER, "
                        + "NAME              VARCHAR(16)"
                        + "ALLOWED           VARCHAR(254))";

                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                //NOM NOM NOM
            }
        }
    }
}
