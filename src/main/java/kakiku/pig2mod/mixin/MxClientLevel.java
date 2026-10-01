package kakiku.pig2mod.mixin;

import java.util.List;
import java.util.function.Supplier;
import kakiku.pig2mod.entity.Pig2;
import kakiku.pig2mod.map.MyArrayList;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ClientLevel.ClientLevelData;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientLevel.class})
public abstract class MxClientLevel {
    @Unique
    ClientLevel thisClientLevel = (ClientLevel)(Object)this;
    @Unique
    private String thisPackeageName = this.getClass().getPackageName();
    @Shadow
    public List<AbstractClientPlayer> players;

    @Inject(
        method = {"<init>"},
        at = {@At("TAIL")}
    )
    private void onInit(
        ClientPacketListener pConnection,
        ClientLevelData pClientLevelData,
        ResourceKey<Level> pDimension,
        Holder<DimensionType> pDimensionType,
        int pViewDistance,
        int pServerSimulationDistance,
        Supplier<ProfilerFiller> pProfiler,
        LevelRenderer pLevelRenderer,
        boolean pIsDebug,
        long pBiomeZoomSeed,
        CallbackInfo ci
    ) {
        this.players = new MyArrayList<>(this.players, ClientLevel.class);
    }

    @Inject(
        method = {"removeEntity"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void removeEntity(int pEntityId, RemovalReason pReason, CallbackInfo ci) {
        if (Pig2.getAliveServerPigIDs(this.thisClientLevel).contains(pEntityId)
            && !Pig2.isOshimai()
            && !MyLib2.isCalledFromTheClassAndMethod("net.minecraft.client.multiplayer.ClientLevel.addEntity")) {
            ci.cancel();
        }
    }

    @Inject(
        method = {"unload"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void unload(LevelChunk pChunk, CallbackInfo ci) {
        for (ChunkPos pig2ChunkPos : Pig2.getAliveServerPigChunkPoss(this.thisClientLevel)) {
            if (pig2ChunkPos.equals(pChunk.getPos()) && !Pig2.isOshimai()) {
                ci.cancel();
                return;
            }
        }
    }

    @Redirect(
        method = {"tickNonPassenger"},
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;tick()V"
        )
    )
    public void wrapCall_Entity_tick(Entity pEntity) {
        try {
            pEntity.tick();
        } catch (Throwable var5) {
            try {
                pEntity.discard();
            } catch (Throwable var4) {
            }
        }
    }

    @ModifyVariable(
        method = {"addEntity"},
        at = @At("HEAD"),
        argsOnly = true,
        index = 2
    )
    public Entity addEntity_arg2(Entity pEntityToSpawn) {
        if (pEntityToSpawn instanceof WitherSkull pWitherSkull && pWitherSkull.getClass() != WitherSkull.class) {
            WitherSkull witherSkull = new WitherSkull(pWitherSkull.level, (LivingEntity)pWitherSkull.getOwner(), 0.0, 0.0, 0.0);
            witherSkull.xPower = pWitherSkull.xPower;
            witherSkull.yPower = pWitherSkull.yPower;
            witherSkull.zPower = pWitherSkull.zPower;
            witherSkull.setOwner(pWitherSkull.getOwner());
            if (pWitherSkull.isDangerous()) {
                witherSkull.setDangerous(true);
            }

            witherSkull.setPosRaw(pWitherSkull.getX(), pWitherSkull.getY(), pWitherSkull.getZ());
            return witherSkull;
        }

        return pEntityToSpawn;
    }
}
