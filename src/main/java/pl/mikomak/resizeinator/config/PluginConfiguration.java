package pl.mikomak.resizeinator.config;

import org.bukkit.configuration.file.FileConfiguration;

public class PluginConfiguration {

    private final FileConfiguration configuration;

    public PluginConfiguration(final FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getResizeCommandPermission() {
        return this.configuration.getString("resizeCommandPermission");
    }

    public boolean shouldResetSize() {
        return this.configuration.getBoolean("resetSizeOnQuit");
    }

    public double getMaximumSize() {
        return this.configuration.getDouble("maximumSize");
    }

    public double getMinimumSize() {
        return this.configuration.getDouble("minimumSize");
    }

    public String getSizeChangedSound() {
        return this.configuration.getString("sizeChangedSound");
    }

    public String getResizedMessage() {
        return this.configuration.getString("resizedMessage");
    }

    public String getResizedPlayerMessage() {
        return this.configuration.getString("resizedPlayerMessage");
    }

    public String getNoPermissionMessage() {
        return this.configuration.getString("noPermissionMessage");
    }

    public String getAccessDeniedMessage() {
        return this.configuration.getString("accessDeniedMessage");
    }

    public String getInvalidUsageMessage() {
        return this.configuration.getString("invalidUsageMessage");
    }

    public String getPlayerOfflineMessage() {
        return this.configuration.getString("playerOfflineMessage");
    }

    public String getSameSizeMessage() {
        return this.configuration.getString("sameSizeMessage");
    }

    public String getOutOfRangeMessage() {
        return this.configuration.getString("outOfRangeMessage");
    }

}