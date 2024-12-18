package pl.mikomak.resizeinator.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.mikomak.resizeinator.config.PluginConfiguration;
import pl.mikomak.resizeinator.util.ResetSizeUtil;

public class PlayerQuitListener implements Listener {

    private final PluginConfiguration configuration;

    // dependency injection
    public PlayerQuitListener(final PluginConfiguration configuration) {
        this.configuration = configuration;
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        // checking if player's size should be reset after quitting
        final boolean resetSizeOnQuit = configuration.shouldResetSize();

        if (resetSizeOnQuit) {
            ResetSizeUtil.resetSize(event.getPlayer());
        }
    }

}