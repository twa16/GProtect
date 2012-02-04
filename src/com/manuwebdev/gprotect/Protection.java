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
package com.manuwebdev.gprotect;

import com.manuwebdev.gprotect.MYSQL.UsersMYSQLActions;
import java.util.ArrayList;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author Manuel Gauto
 */
public class Protection {
    /**
     * Block
     */
    Block b;
    
    /**
     * Owner
     */
    String owner;
    
    /**
     * Allowed Player IDs
     */
    ArrayList<Integer> allowed=new ArrayList<Integer>();
    
    /**
     * 
     * @param b 
     */
    public Protection(Player p, Block b){
        this.b=b;
        this.owner=p.getName();
        
    }
    
    /**
     * Get block that is protected
     * @return 
     */
    public Block getBlock(){
        return b;
    }
    
    /**
     * Get Block Owner
     * @return 
     */
    public String getOwnerName(){
        return owner;
    }
    
    /**
     * Check if a player is allowed
     * @param ID Player ID
     * @return 
     */
    public boolean isPlayerAllowed(int ID){
        //Check Allowed list for user ID
        for(int i=0;i<allowed.size();i++){
            //User foudn so they are allowed
            if(ID==allowed.get(i)) return true;
        }
        //User Not allowed
        return false;
    }
    
    /**
     * Check if player is allowed to edit
     * @param p Player
     * @param actions
     * @return 
     */
    public boolean isPlayerAllowed(Player p, UsersMYSQLActions actions){
        int ID=actions.getPlayerID(p.getName());
        //Check Allowed list for user ID
        for(int i=0;i<allowed.size();i++){
            //User foudn so they are allowed
            if(ID==allowed.get(i)) return true;
        }
        //User Not allowed
        return false;
    }
        
}
