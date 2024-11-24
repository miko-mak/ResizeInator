package pl.mikomak.resizeinator.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.mikomak.resizeinator.config.ConfigManager;
import pl.mikomak.resizeinator.util.ResetSizeUtil;

public class PlayerJoinListener implements Listener {

    private final ConfigManager configuration;

    public PlayerJoinListener(final ConfigManager config) {
        this.configuration = config;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final boolean resetSizeOnQuit = configuration.shouldResetSize();

        if (resetSizeOnQuit) {
            ResetSizeUtil.resetSize(event.getPlayer());
        }
    }

}