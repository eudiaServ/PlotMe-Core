package com.worldcretornica.plotme_core.commands;

import com.worldcretornica.plotme_core.PermissionNames;
import com.worldcretornica.plotme_core.PlotMe_Core;
import com.worldcretornica.plotme_core.api.IPlayer;

import java.util.UUID;

public class CmdWEAnywhere extends PlotCommand {

    public CmdWEAnywhere(PlotMe_Core instance) {
        super(instance);
    }

    public boolean exec(IPlayer player) {
        if (player.hasPermission(PermissionNames.ADMIN_WEANYWHERE) && plugin.getServerBridge().getPlotWorldEdit() != null) {
            String name = player.getName();
            UUID uuid = player.getUniqueId();

            boolean defaultWEAnywhere = plugin.getConfig().getBoolean("defaultWEAnywhere");
            boolean playerIgnoringWELimit = manager.isPlayerIgnoringWELimit(player);
            if (playerIgnoringWELimit && !defaultWEAnywhere || !playerIgnoringWELimit && defaultWEAnywhere) {
                manager.removePlayerIgnoringWELimit(uuid);
                plugin.getServerBridge().getPlotWorldEdit().setMask(player);
            } else {
                manager.addPlayerIgnoringWELimit(uuid);
                plugin.getServerBridge().getPlotWorldEdit().removeMask(player);
            }
            if (manager.isPlayerIgnoringWELimit(player)) {
                player.sendMessage(C("MsgWorldEditAnywhere"));
                if (isAdvancedLogging()) {
                    plugin.getLogger().info(name + "enabled WorldEdit Anywhere");
                }
            } else {
                player.sendMessage(C("MsgWorldEditInYourPlots"));
                if (isAdvancedLogging()) {
                    plugin.getLogger().info(name + "disabled WorldEdit Anywhere");
                }
            }
        } else {
            player.sendMessage(C("MsgPermissionDenied"));
            return false;
        }
        return true;
    }
}
