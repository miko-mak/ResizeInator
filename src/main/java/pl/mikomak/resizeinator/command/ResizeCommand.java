package pl.mikomak.resizeinator.command;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.mikomak.resizeinator.config.PluginConfiguration;

public class ResizeCommand implements CommandExecutor {

    private final MiniMessage MINIMESSAGE = MiniMessage.miniMessage();
    private final PluginConfiguration configuration;

    // dependency injection
    public ResizeCommand(final PluginConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // if sender is not a player, code will stop

        if (!(commandSender instanceof Player)) {
            final String accessDenied = configuration.getAccessDeniedMessage();

            commandSender.sendMessage(accessDenied);
            return false;
        }

        final Player sender = (Player) commandSender;
        final String resizeCommandPermission = configuration.getResizeCommandPermission();

        // if the sender is not permitted to use the command, code will stop

        if (!sender.hasPermission(resizeCommandPermission)) {
            final String noPermission = configuration.getNoPermissionMessage();
            sender.sendMessage(MINIMESSAGE.deserialize(noPermission));
            return false;
        }

        // if an invalid amount of arguments is provided, code will stop

        if (args.length != 2) {
            final String invalidUsage = configuration.getInvalidUsageMessage();
            sender.sendMessage(MINIMESSAGE.deserialize(invalidUsage));
            return false;
        }

        final Player targetPlayer = Bukkit.getPlayer(args[0]);

        // if the target player is not found, code will stop

        if (targetPlayer == null) {
            final String targetOffline = configuration.getPlayerOfflineMessage();
            sender.sendMessage(MINIMESSAGE.deserialize(targetOffline));
            return false;
        }

        double targetSize;

        // trying to parse size argument to a number, if not possible - code will stop.

        try {
            targetSize = Double.parseDouble(args[1]);

        } catch (Exception e) {
            final String invalidUsage = configuration.getInvalidUsageMessage();
            sender.sendMessage(MINIMESSAGE.deserialize(invalidUsage));
            return false;
        }

        //

        final double maxSize = configuration.getMaximumSize();
        final double minSize = configuration.getMinimumSize();

        // if the target size is out of range, code will stop

        if (targetSize > maxSize || targetSize < minSize) {
            final String outOfRange = configuration.getOutOfRangeMessage();
            sender.sendMessage(MINIMESSAGE.deserialize(outOfRange));
            return false;
        }

        // if the target size is equal to current size - code will stop and size will not change

        if (targetPlayer.getAttribute(Attribute.GENERIC_SCALE).getBaseValue() == targetSize) {
            final String sameSizeMessage = configuration.getSameSizeMessage();
            sender.sendMessage(MINIMESSAGE.deserialize(sameSizeMessage));
            return false;
        }

        final String playerName = targetPlayer.getName();

        // sending different messages for the target player and the command sender

        if (targetPlayer != sender) {
            final String resizedPlayer = configuration.getResizedPlayerMessage().replace("{PLAYER}", playerName);
            final String resized = configuration.getResizedMessage();

            sender.sendMessage(MINIMESSAGE.deserialize(resizedPlayer));
            targetPlayer.sendMessage(MINIMESSAGE.deserialize(resized));

        } else {
            final String resized = configuration.getResizedMessage();
            sender.sendMessage(MINIMESSAGE.deserialize(resized));
        }

        // getting sound data by splitting String object at " "

        final String sound = configuration.getSizeChangedSound();
        final String[] soundData = sound.split(" ");

        final Sound soundName = Sound.valueOf(soundData[0]);
        final float soundVolume = Float.parseFloat(soundData[1]);
        final float soundPitch = Float.parseFloat(soundData[2]);

        // setting the target size

        targetPlayer.playSound(targetPlayer.getLocation(), soundName, soundVolume, soundPitch);
        targetPlayer.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(targetSize);
        return true;
    }

}