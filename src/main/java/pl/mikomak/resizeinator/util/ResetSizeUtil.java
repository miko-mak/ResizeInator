package pl.mikomak.resizeinator.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class ResetSizeUtil {

    public static void resetSize(final Player player) {
        final AttributeInstance scaleAttribute = player.getAttribute(Attribute.GENERIC_SCALE);
        final double currentSize = scaleAttribute.getBaseValue();

        if (currentSize > 1) {
            scaleAttribute.setBaseValue(1.0);
        }
    }

}
