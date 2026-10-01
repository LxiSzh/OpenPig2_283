package kakiku.pig2mod;

import javax.annotation.Nullable;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class MyLib {
    public static final boolean MY_DEBUG_MODE = false;

    public static void MyLog(String string) {
    }

    @Nullable
    public static Player getPlayer() {
        Player player = Minecraft.instance.player;
        if (MyLib2.isClientThread()) {
            return player;
        } else if (player == null) {
            return null;
        } else {
            String playerName = player.getName().getString();
            IntegratedServer is = Minecraft.instance.getSingleplayerServer();
            return is == null ? null : is.getPlayerList().getPlayerByName(playerName);
        }
    }

    @Nullable
    public static Level getLevel() {
        Player player = getPlayer();
        return player == null ? null : player.level;
    }

    public static boolean isCalledFromSpawnByServerPlayer() {
        return !MyLib2.isServerThread()
            ? false
            : (MyLib2.isCalledFromTheClass("EntityType") || MyLib2.isCalledFromTheClass("ForgeHooks"))
                    && MyLib2.isCalledFromTheClass("ItemStack")
                    && MyLib2.isCalledFromTheClass("ServerPlayerGameMode")
                || MyLib2.isCalledFromTheClass("SummonCommand") && MyLib2.isCalledFromTheClass("ServerGamePacketListenerImpl");
    }
}
