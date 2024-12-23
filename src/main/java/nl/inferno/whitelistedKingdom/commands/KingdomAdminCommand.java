package nl.inferno.whitelistedKingdom.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KingdomAdminCommand implements CommandExecutor {
    private final WhitelistedKingdom plugin;

    public KingdomAdminCommand(WhitelistedKingdom plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("kingdom.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        if (args.length == 0) {
            sendAdminHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "delete" -> handleDelete(sender, args);
            case "reload" -> handleReload(sender);
            case "forcesave" -> handleForceSave(sender);
            case "setlevel" -> handleSetLevel(sender, args);
            case "resetdata" -> handleResetData(sender, args);
        }

        return true;
    }
}
