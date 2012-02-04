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

package com.manuwebdev.gprotect.Commands;

import com.manuwebdev.gprotect.GProtect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/**
 *
 * @author Manuel Gauto 
 */
public class GProtectCommandExecutor implements CommandExecutor{
    /**
     * Plugin reference
     */
    GProtect plugin;
    
    /**
     * Default
     * @param plugin Plugin reference
     */
    public GProtectCommandExecutor(GProtect plugin){
        this.plugin=plugin;
    }
    
    /**
     * Triggered when command is recieved
     * @param sender
     * @param cmd
     * @param string
     * @param strings
     * @return 
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] strings) {
        Player player = null;

        //Check if the sender is a player of the console
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if (cmd.getName().equalsIgnoreCase(CommandList.TOGGLE_COMMAND)) {
            //Console sent command
            if (player == null) {
                sender.sendMessage("This command can only be run by a player");
            }
            
            //Player sent command
            if (player.hasPermission(CommandList.TOGGLE_COMMAND_PERMISSION) || player.isOp()) {
                //TODO what command does
            }
            return true;
        }
        
        //None of them executed
        return false;

    }
    
}

