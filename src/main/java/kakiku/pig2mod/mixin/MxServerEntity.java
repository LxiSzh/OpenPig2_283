package kakiku.pig2mod.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import kakiku.pig2mod.entity.Pig2;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ServerEntity.class})
public abstract class MxServerEntity {
    @Unique
    ServerEntity thisServerEntity = (ServerEntity)(Object)this;
    @Shadow
    private Entity entity;

    @Inject(
        method = {"removePairing"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void removePairing(ServerPlayer pPlayer, CallbackInfo ci) {
        if (this.entity instanceof Pig2 pig2
            && !Pig2.isOshimai()
            && !pig2.isEnding()
            && !MyLib2.isCalledFromTheClassAndMethod("net.minecraft.server.level.ServerLevel.removePlayerImmediately")
            && !MyLib2.isCalledFromTheClassAndMethod("net.minecraft.server.level.ChunkMap$TrackedEntity.removePlayer")) {
            ci.cancel();
            return;
        }

        try {
            this.entity.stopSeenByPlayer(pPlayer);
        } catch (Throwable var5) {
        }

        pPlayer.connection.send(new ClientboundRemoveEntitiesPacket(new int[]{this.entity.getId()}));
        ForgeEventFactory.onStopEntityTracking(this.entity, pPlayer);
        ci.cancel();
    }

    @Inject(
        method = {"addPairing"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void addPairing(ServerPlayer pPlayer, CallbackInfo ci) {
        List<Packet<ClientGamePacketListener>> list = new ArrayList<>();
        this.thisServerEntity.sendPairingData(pPlayer, list::add);
        pPlayer.connection.send(new ClientboundBundlePacket(list));

        try {
            this.entity.startSeenByPlayer(pPlayer);
        } catch (Throwable var5) {
        }

        ForgeEventFactory.onStartEntityTracking(this.entity, pPlayer);
        ci.cancel();
    }

    @Inject(
        method = {"sendPairingData"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void sendPairingData(ServerPlayer pPlayer, Consumer<Packet<ClientGamePacketListener>> pConsumer, CallbackInfo ci) {
        if (this.entity instanceof Pig2 pig2
            && !Pig2.isOshimai()
            && !pig2.isEnding()
            && !MyLib2.isCalledFromTheClassAndMethod("net.minecraft.server.level.ServerEntity.addPairing")) {
            ci.cancel();
            return;
        }
    }

    @Inject(
        method = {"broadcastAndSend"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void broadcastAndSend(Packet<?> pPacket, CallbackInfo ci) {
        if (this.entity instanceof Pig2 pig2
            && !Pig2.isOshimai()
            && !pig2.isEnding()
            && !MyLib2.isCalledFromTheClassAndMethod("net.minecraft.server.level.ServerEntity.sendChanges")) {
            ci.cancel();
            return;
        }
    }
}
