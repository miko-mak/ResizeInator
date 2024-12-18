package pl.mikomak.resizeinator;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mikomak.resizeinator.command.ResizeCommand;
import pl.mikomak.resizeinator.listener.PlayerJoinListener;
import pl.mikomak.resizeinator.listener.PlayerQuitListener;
import pl.mikomak.resizeinator.config.PluginConfiguration;

public final class ResizeInator extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        final PluginConfiguration configuration = new PluginConfiguration(this.getConfig());
        final PluginManager pluginManager = getServer().getPluginManager();

        this.getCommand("resize").setExecutor(new ResizeCommand(configuration));

        pluginManager.registerEvents(new PlayerJoinListener(configuration), this);
        pluginManager.registerEvents(new PlayerQuitListener(configuration), this);
    }

}