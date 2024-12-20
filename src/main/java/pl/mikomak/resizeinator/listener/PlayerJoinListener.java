package pl.mikomak.resizeinator.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.mikomak.resizeinator.config.PluginConfiguration;
import pl.mikomak.resizeinator.util.ResetSizeUtil;

public class PlayerJoinListener implements Listener {

    private final PluginConfiguration configuration;

    public PlayerJoinListener(final PluginConfiguration configuration) {
        this.configuration = configuration;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final boolean resetSizeOnQuit = configuration.shouldResetSize();

        if (resetSizeOnQuit) {
            ResetSizeUtil.resetSize(event.getPlayer());
        }
    }

}