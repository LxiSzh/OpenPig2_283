package kakiku.pig2mod.mixin;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import kakiku.pig2mod.entity.Pig2;
import kakiku.pig2mod.map.MyLong2ObjectOpenHashMap;
import kakiku.pig2mod.map.MyLongSet;
import kakiku.pig2mod.map.MyQueue;
import kakiku.pig2mod.map.MySet;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.entity.ChunkEntities;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntityPersistentStorage;
import net.minecraft.world.level.entity.EntitySectionStorage;
import net.minecraft.world.level.entity.LevelCallback;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import net.minecraft.world.level.entity.Visibility;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({PersistentEntitySectionManager.class})
public abstract class MxPersistentEntitySectionManager<T extends EntityAccess> {
    @Unique
    PersistentEntitySectionManager<T> thisPESManager = (PersistentEntitySectionManager<T>)(Object)this;
    @Unique
    private String thisPackeageName = this.getClass().getPackageName();
    @Shadow
    public Set<UUID> knownUuids;
    @Shadow
    public Queue<ChunkEntities<T>> loadingInbox;
    @Shadow
    public Long2ObjectMap<Visibility> chunkVisibility;
    @Shadow
    public LongSet chunksToUnload;
    @Shadow
    public EntitySectionStorage<T> sectionStorage;
    @Unique
    private ServerLevel myServerLevel = null;

    @Shadow
    protected abstract boolean addEntity(T var1, boolean var2);

    @Inject(
        method = {"<init>"},
        at = {@At("TAIL")}
    )
    private void onInit(Class<T> pEntityClass, LevelCallback<T> pCallbacks, EntityPersistentStorage<T> pPermanentStorage, CallbackInfo ci) {
        this.knownUuids = new MySet<>(this.knownUuids, PersistentEntitySectionManager.class);
        this.loadingInbox = new MyQueue<>(this.loadingInbox, PersistentEntitySectionManager.class);
        this.chunkVisibility = new MyLong2ObjectOpenHashMap<Visibility>(this.chunkVisibility, PersistentEntitySectionManager.class);
        this.sectionStorage.intialSectionVisibility = this.chunkVisibility;
        this.thisPESManager.chunkLoadStatuses = new MyLong2ObjectOpenHashMap(this.thisPESManager.chunkLoadStatuses, PersistentEntitySectionManager.class);
        this.chunksToUnload = new MyLongSet(this.chunksToUnload, PersistentEntitySectionManager.class);
    }

    @Inject(
        method = {"stopTracking"},
        at = {@At("HEAD")},
        cancellable = true
    )
    void stopTracking(T entityAccess, CallbackInfo ci) {
        if (entityAccess instanceof Pig2 pig2 && !Pig2.isOshimai() && !pig2.isEnding()) {
            ci.cancel();
            return;
        }
    }

    @Inject(
        method = {"stopTicking"},
        at = {@At("HEAD")},
        cancellable = true
    )
    void stopTicking(T entityAccess, CallbackInfo ci) {
        if (entityAccess instanceof Pig2 pig2 && !Pig2.isOshimai() && !pig2.isEnding()) {
            ci.cancel();
            return;
        }
    }

    @Inject(
        method = {"unloadEntity"},
        at = {@At("HEAD")},
        cancellable = true
    )
    void unloadEntity(T entityAccess, CallbackInfo ci) {
        if (entityAccess instanceof Pig2 pig2 && !Pig2.isOshimai() && !pig2.isEnding()) {
            ci.cancel();
            return;
        }
    }

    @Inject(
        method = {"updateChunkStatus(Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/world/level/entity/Visibility;)V"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void updateChunkStatus(ChunkPos pPos, Visibility pVisibility, CallbackInfo ci) {
        if (pVisibility == Visibility.HIDDEN || pVisibility == Visibility.TRACKED) {
            for (ChunkPos pig2ChunkPos : Pig2.getAliveServerPigChunkPoss(this.myServerLevel())) {
                if (pig2ChunkPos.equals(pPos) && !Pig2.isOshimai()) {
                    ci.cancel();
                    return;
                }
            }
        }
    }

    @Inject(
        method = {"processChunkUnload"},
        at = {@At("HEAD")},
        cancellable = true
    )
    private void processChunkUnload(long pChunkPosValue, CallbackInfoReturnable<Boolean> cir) {
        for (ChunkPos pig2ChunkPos : Pig2.getAliveServerPigChunkPoss(this.myServerLevel())) {
            if (pig2ChunkPos.toLong() == pChunkPosValue && !Pig2.isOshimai()) {
                cir.setReturnValue(false);
                return;
            }
        }
    }

    @Inject(
        method = {"addEntityWithoutEvent(Lnet/minecraft/world/level/entity/EntityAccess;Z)Z"},
        at = {@At("HEAD")},
        cancellable = true,
        remap = false
    )
    public void addEntityWithoutEvent(T pEntity, boolean pWorldGenSpawned, CallbackInfoReturnable<Boolean> cir) {
        if (MyLib2.isCalledFromOtherModWithin5()
            && !MyLib2.getCaller2().getMethodName().equals("addEntity")
            && pEntity instanceof Entity entity
            && MinecraftForge.EVENT_BUS.post(new EntityJoinLevelEvent(entity, entity.level(), pWorldGenSpawned))) {
            cir.setReturnValue(false);
            return;
        }
    }

    @Unique
    private ServerLevel myServerLevel() {
        if (this.myServerLevel != null) {
            return this.myServerLevel;
        } else {
            for (ServerLevel sLevel : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
                if (sLevel.entityManager == this.thisPESManager) {
                    this.myServerLevel = sLevel;
                }
            }

            return this.myServerLevel;
        }
    }
}
