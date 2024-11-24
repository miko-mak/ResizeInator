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
import pl.mikomak.resizeinator.config.ConfigManager;

public class ResizeCommand implements CommandExecutor {

    private final ConfigManager configuration;

    public ResizeCommand(final ConfigManager configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            final String accessDenied = configuration.getAccessDeniedMessage();

            commandSender.sendMessage(accessDenied);
            return false;
        }

        final Player sender = (Player) commandSender;
        final String resizeCommandPermission = configuration.getResizeCommandPermission();

        if (!sender.hasPermission(resizeCommandPermission)) {
            final String noPermission = configuration.getNoPermissionMessage();
            sender.sendMessage(MiniMessage.miniMessage().deserialize(noPermission));
            return false;
        }

        if (args.length != 2) {
            final String invalidUsage = configuration.getInvalidUsageMessage();
            sender.sendMessage(MiniMessage.miniMessage().deserialize(invalidUsage));
            return false;
        }

        double targetSize;

        try {
            targetSize = Double.parseDouble(args[1]);

        } catch (Exception e) {
            final String invalidUsage = configuration.getInvalidUsageMessage();
            sender.sendMessage(MiniMessage.miniMessage().deserialize(invalidUsage));
            return false;
        }

        final Player targetPlayer = Bukkit.getPlayer(args[0]);

        if (targetPlayer == null) {
            final String targetOffline = configuration.getPlayerOfflineMessage();
            sender.sendMessage(MiniMessage.miniMessage().deserialize(targetOffline));
            return false;
        }

        final double maxSize = configuration.getMaximumSize();
        final double minSize = configuration.getMinimumSize();

        if (targetSize > maxSize || targetSize < minSize) {
            final String outOfRange = configuration.getOutOfRangeMessage();
            sender.sendMessage(MiniMessage.miniMessage().deserialize(outOfRange));
            return false;
        }

        if (targetPlayer.getAttribute(Attribute.GENERIC_SCALE).getBaseValue() == targetSize) {
            final String sameSizeMessage = configuration.getSameSizeMessage();
            sender.sendMessage(MiniMessage.miniMessage().deserialize(sameSizeMessage));
            return false;
        }

        final String playerName = targetPlayer.getName();

        if (targetPlayer != sender) {
            final String resizedPlayer = configuration.getResizedPlayerMessage().replace("{PLAYER}", playerName);
            final String resized = configuration.getResizedMessage();

            sender.sendMessage(MiniMessage.miniMessage().deserialize(resizedPlayer));
            targetPlayer.sendMessage(MiniMessage.miniMessage().deserialize(resized));

        } else {
            final String resized = configuration.getResizedMessage();
            sender.sendMessage(MiniMessage.miniMessage().deserialize(resized));
        }

        final String sound = configuration.getSizeChangedSound();
        final String[] soundData = sound.split(" ");

        final Sound soundName = Sound.valueOf(soundData[0]);
        final float soundVolume = Float.parseFloat(soundData[1]);
        final float soundPitch = Float.parseFloat(soundData[2]);

        targetPlayer.playSound(targetPlayer.getLocation(), soundName, soundVolume, soundPitch);
        targetPlayer.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(targetSize);
        return true;
    }

}