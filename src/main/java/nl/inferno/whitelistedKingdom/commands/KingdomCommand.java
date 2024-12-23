package nl.inferno.whitelistedKingdom.commands;

import nl.inferno.whitelistedKingdom.WhitelistedKingdom;
import nl.inferno.whitelistedKingdom.models.Kingdom;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import java.util.List;

public class KingdomCommand implements CommandExecutor, TabCompleter {
    private final WhitelistedKingdom plugin;

    public KingdomCommand(WhitelistedKingdom plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use kingdom commands!");
            return true;
        }

        if (args.length == 0) {
            plugin.getKingdomGUI().openMainMenu(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create" -> handleCreate(player, args);
            case "delete" -> handleDelete(player, args);
            case "invite" -> handleInvite(player, args);
            case "join" -> handleJoin(player, args);
            case "leave" -> handleLeave(player);
            case "claim" -> handleClaim(player);
            case "unclaim" -> handleUnclaim(player);
            case "sethome" -> handleSetHome(player);
            case "home" -> handleHome(player);
            case "bank" -> handleBank(player, args);
            case "war" -> handleWar(player, args);
            case "ally" -> handleAlly(player, args);
            case "chat" -> handleChat(player, args);
            case "upgrade" -> handleUpgrade(player, args);
            case "power" -> handlePower(player);
            case "top" -> handleTop(player);
            default -> plugin.getKingdomGUI().openMainMenu(player);
        }
        return true;
    }

    private void handleCreate(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("§cUsage: /kingdom create <name>");
            return;
        }

        String kingdomName = args[1];
        if (plugin.getKingdomManager().createKingdom(kingdomName, player)) {
            player.sendMessage("§aKingdom " + kingdomName + " created successfully!");
            plugin.getGuiManager().openMainMenu(player);
        } else {
            player.sendMessage("§cCouldn't create kingdom. Name might be taken or invalid.");
        }
    }

    private void handleDelete(Player player, String[] args) {
        Kingdom kingdom = plugin.getKingdomManager().getPlayerKingdom(player.getUniqueId());
        if (kingdom == null) {
            player.sendMessage("§cYou are not in a kingdom!");
            return;
        }

        if (!kingdom.getOwner().equals(player.getUniqueId())) {
            player.sendMessage("§cOnly the kingdom owner can delete the kingdom!");
            return;
        }

        plugin.getKingdomManager().deleteKingdom(kingdom);
        player.sendMessage("§aYour kingdom has been deleted!");
    }

    private void handleInvite(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("§cUsage: /kingdom invite <player>");
            return;
        }

        Kingdom kingdom = plugin.getKingdomManager().getPlayerKingdom(player.getUniqueId());
        if (kingdom == null) {
            player.sendMessage("§cYou are not in a kingdom!");
            return;
        }

        Player target = plugin.getServer().getPlayer(args[1]);
        if (target == null) {
            player.sendMessage("§cPlayer not found!");
            return;
        }

        kingdom.invitePlayer(target);
        player.sendMessage("§aInvited " + target.getName() + " to your kingdom!");
        target.sendMessage("§aYou have been invited to join " + kingdom.getName() + "!");
    }

    private void handleJoin(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("§cUsage: /kingdom join <kingdom>");
            return;
        }

        Kingdom kingdom = plugin.getKingdomManager().getKingdom(args[1]);
        if (kingdom == null) {
            player.sendMessage("§cKingdom not found!");
            return;
        }

        if (!kingdom.isInvited(player)) {
            player.sendMessage("§cYou haven't been invited to this kingdom!");
            return;
        }

        kingdom.addMember(player);
        player.sendMessage("§aYou have joined " + kingdom.getName() + "!");
    }

    private void handleLeave(Player player) {
        Kingdom kingdom = plugin.getKingdomManager().getPlayerKingdom(player.getUniqueId());
        if (kingdom == null) {
            player.sendMessage("§cYou are not in a kingdom!");
            return;
        }

        if (kingdom.getOwner().equals(player.getUniqueId())) {
            player.sendMessage("§cKingdom owners cannot leave their kingdom. Use /kingdom delete instead!");
            return;
        }

        kingdom.removeMember(player.getUniqueId());
        player.sendMessage("§aYou have left the kingdom!");
    }

    private void handleClaim(Player player) {
        Kingdom kingdom = plugin.getKingdomManager().getPlayerKingdom(player.getUniqueId());
        if (kingdom == null) {
            player.sendMessage("§cYou are not in a kingdom!");
            return;
        }

        if (plugin.getTerritoryManager().claimChunk(kingdom, player.getLocation().getChunk())) {
            player.sendMessage("§aChunk claimed for your kingdom!");
        } else {
            player.sendMessage("§cCannot claim this chunk!");
        }
    }

    private void handleUnclaim(Player player) {
        Kingdom kingdom = plugin.getKingdomManager().getPlayerKingdom(player.getUniqueId());
        if (kingdom == null) {
            player.sendMessage("§cYou are not in a kingdom!");
            return;
        }

        if (plugin.getTerritoryManager().unclaimChunk(kingdom, player.getLocation().getChunk())) {
            player.sendMessage("§aChunk unclaimed!");
        } else {
            player.sendMessage("§cThis chunk is not claimed by your kingdom!");
        }
    }

    private void handleSetHome(Player player) {
        Kingdom kingdom = plugin.getKingdomManager().getPlayerKingdom(player.getUniqueId());
        if (kingdom == null) {
            player.sendMessage("§cYou are not in a kingdom!");
            return;
        }

        if (!plugin.getTerritoryManager().isKingdomTerritory(kingdom, player.getLocation().getChunk())) {
            player.sendMessage("§cYou can only set home in your kingdom's territory!");
            return;
        }

        kingdom.setHome(player.getLocation());
        player.sendMessage("§aKingdom home set!");
    }

    private void handleHome(Player player) {
        Kingdom kingdom = plugin.getKingdomManager().getPlayerKingdom(player.getUniqueId());
        if (kingdom == null) {
            player.sendMessage("§cYou are not in a kingdom!");
            return;
        }

        if (kingdom.getHome() == null) {
            player.sendMessage("§cKingdom home has not been set!");
            return;
        }

        player.teleport(kingdom.getHome());
        player.sendMessage("§aTeleported to kingdom home!");
    }

    private void handleBank(Player player, String[] args) {
        if (args.length < 3 || (!args[1].equalsIgnoreCase("deposit") && !args[1].equalsIgnoreCase("withdraw"))) {
            player.sendMessage("§cUsage: /kingdom bank <deposit/withdraw> <amount>");
            return;
        }

        Kingdom kingdom = plugin.getKingdomManager().getPlayerKingdom(player.getUniqueId());
        if (kingdom == null) {
            player.sendMessage("§cYou are not in a kingdom!");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage("§cInvalid amount!");
            return;
        }

        if (args[1].equalsIgnoreCase("deposit")) {
            plugin.getEconomyManager().depositToKingdom(kingdom, player, amount);
        } else {
            plugin.getEconomyManager().withdrawFromKingdom(kingdom, player, amount);
        }
    }

    private void handleWar(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("§cUsage: /kingdom war <kingdom>");
            return;
        }

        Kingdom targetKingdom = plugin.getKingdomManager().getKingdom(args[1]);
        if (targetKingdom == null) {
            player.sendMessage("§cKingdom not found!");
            return;
        }

        plugin.getWarManager().declareWar(player, targetKingdom);
    }

    private void handleAlly(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("§cUsage: /kingdom ally <kingdom>");
            return;
        }

        Kingdom kingdom = plugin.getKingdomManager().getPlayerKingdom(player.getUniqueId());
        if (kingdom == null) {
            player.sendMessage("§cYou are not in a kingdom!");
            return;
        }

        Kingdom targetKingdom = plugin.getKingdomManager().getKingdom(args[1]);
        if (targetKingdom == null) {
            player.sendMessage("§cKingdom not found!");
            return;
        }

        plugin.getRelationManager().requestAlliance(kingdom, targetKingdom);
        player.sendMessage("§aAlliance request sent to " + targetKingdom.getName());
    }

    private void handleChat(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("§cUsage: /kingdom chat <kingdom/ally/global>");
            return;
        }

        switch (args[1].toLowerCase()) {
            case "kingdom" -> plugin.getChatManager().setChannel(player, ChatChannel.KINGDOM);
            case "ally" -> plugin.getChatManager().setChannel(player, ChatChannel.ALLY);
            case "global" -> plugin.getChatManager().setChannel(player, ChatChannel.GLOBAL);
            default -> player.sendMessage("§cInvalid chat channel!");
        }
    }

    private void handleUpgrade(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("§cUsage: /kingdom upgrade <type>");
            return;
        }

        Kingdom kingdom = plugin.getKingdomManager().getPlayerKingdom(player.getUniqueId());
        if (kingdom == null) {
            player.sendMessage("§cYou are not in a kingdom!");
            return;
        }

        plugin.getUpgradeManager().handleUpgrade(kingdom, player, args[1]);
    }

    private void handlePower(Player player) {
        Kingdom kingdom = plugin.getKingdomManager().getPlayerKingdom(player.getUniqueId());
        if (kingdom == null) {
            player.sendMessage("§cYou are not in a kingdom!");
            return;
        }

        double power = plugin.getPowerManager().getKingdomPower(kingdom);
        player.sendMessage("§aKingdom Power: " + power);
    }

    private void handleTop(Player player) {
        List<Kingdom> topKingdoms = plugin.getKingdomManager().getTopKingdoms(10);
        player.sendMessage("§6=== Top Kingdoms ===");
        for (int i = 0; i < topKingdoms.size(); i++) {
            Kingdom k = topKingdoms.get(i);
            player.sendMessage("§e" + (i + 1) + ". §f" + k.getName() + " §7- Power: §f" +
                    plugin.getPowerManager().getKingdomPower(k));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return List.of("create", "delete", "invite", "join", "leave", "claim", "unclaim",
                    "sethome", "home", "bank", "war", "ally", "chat", "upgrade", "power", "top");
        }
        return null;
    }
}
