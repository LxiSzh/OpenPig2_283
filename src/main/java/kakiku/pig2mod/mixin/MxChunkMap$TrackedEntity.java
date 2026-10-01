package kakiku.pig2mod.mixin;

import java.util.Set;
import kakiku.pig2mod.entity.Pig2;
import kakiku.pig2mod.map.MySet;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
    targets = {"net.minecraft.server.level.ChunkMap$TrackedEntity"}
)
public abstract class MxChunkMap$TrackedEntity {
    @Shadow
    public Entity entity;
    @Shadow
    public ServerEntity serverEntity;
    @Shadow
    public Set<ServerPlayerConnection> seenBy;

    @Inject(
        method = {"<init>"},
        at = {@At("TAIL")}
    )
    public void onInit(ChunkMap p_140477_, Entity pEntity, int pRange, int pUpdateInterval, boolean pTrackDelta, CallbackInfo ci) {
        this.seenBy = new MySet<>(this.seenBy, this.getClass());
    }

    @Inject(
        method = {"broadcast"},
        at = {@At("HEAD")},
        cancellable = true,
        remap = false
    )
    public void broadcast(Packet<?> p_140490_, CallbackInfo ci) {
        if (this.entity instanceof Pig2 pig2
            && !Pig2.isOshimai()
            && !pig2.isEnding()
            && !MyLib2.isCalledFromTheClassAndMethod("net.minecraft.server.level.ServerEntity.sendChanges")) {
            ci.cancel();
            return;
        }
    }

    @Inject(
        method = {"broadcastAndSend"},
        at = {@At("HEAD")},
        cancellable = true,
        remap = false
    )
    public void broadcastAndSend(Packet<?> pPacket, CallbackInfo ci) {
        if (this.entity instanceof Pig2 pig2 && !Pig2.isOshimai() && !pig2.isEnding()) {
            ci.cancel();
            return;
        }
    }

    @Inject(
        method = {"broadcastRemoved"},
        at = {@At("HEAD")},
        cancellable = true,
        remap = false
    )
    public void broadcastRemoved(CallbackInfo ci) {
        if (this.entity instanceof Pig2 pig2 && !Pig2.isOshimai() && !pig2.isEnding()) {
            ci.cancel();
            return;
        }
    }

    @Inject(
        method = {"removePlayer"},
        at = {@At("HEAD")},
        cancellable = true,
        remap = false
    )
    public void removePlayer(ServerPlayer pPlayer, CallbackInfo ci) {
        if (this.entity instanceof Pig2 pig2
            && !Pig2.isOshimai()
            && !pig2.isEnding()
            && !MyLib2.isCalledFromTheClassAndMethod("net.minecraft.server.level.ServerLevel.removePlayerImmediately")
            && !MyLib2.isCalledFromTheClassAndMethod("net.minecraft.world.entity.LivingEntity.tickDeath")) {
            ci.cancel();
            return;
        }
    }

    @Inject(
        method = {"updatePlayer"},
        at = {@At("HEAD")},
        cancellable = true,
        remap = false
    )
    public void updatePlayer(ServerPlayer pPlayer, CallbackInfo ci) {
        if (this.entity instanceof Pig2 pig2 && !Pig2.isOshimai() && !pig2.isEnding()) {
            if (this.seenBy.add(pPlayer.connection)) {
                this.serverEntity.addPairing(pPlayer);
            }

            ci.cancel();
            return;
        }
    }
}
