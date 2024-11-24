package pl.mikomak.resizeinator;

import org.bukkit.plugin.java.JavaPlugin;
import pl.mikomak.resizeinator.command.ResizeCommand;
import pl.mikomak.resizeinator.listener.PlayerJoinListener;
import pl.mikomak.resizeinator.listener.PlayerQuitListener;
import pl.mikomak.resizeinator.config.ConfigManager;

public final class ResizeInator extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        final ConfigManager configuration = new ConfigManager(this.getConfig());
        this.getCommand("resize").setExecutor(new ResizeCommand(configuration));

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(configuration), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(configuration), this);
    }

}