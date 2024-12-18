package pl.mikomak.resizeinator.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class ResetSizeUtil {

    public static void resetSize(final Player player) {
        // getting the current size of the player by using appropriate GENERIC_SCALE attribute
        final AttributeInstance scaleAttribute = player.getAttribute(Attribute.GENERIC_SCALE);
        final double currentSize = scaleAttribute.getBaseValue();

        // if current size is greater than 1, player's size will be reset
        if (currentSize > 1) {
            scaleAttribute.setBaseValue(1.0);
        }
    }

}
