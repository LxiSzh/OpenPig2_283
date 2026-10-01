package kakiku.pig2mod.mixin;

import kakiku.pig2mod.MyLib;
import kakiku.pig2mod.entity.Pig2;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Entity.class})
public abstract class MxEntity {
    @Unique
    public Entity myEntity = (Entity)(Object)this;
    @Shadow
    public RemovalReason removalReason;
    @Shadow
    public EntityInLevelCallback levelCallback;
    @Shadow
    public SynchedEntityData entityData;
    @Shadow
    public Level level;

    @Inject(
        method = {"isRemoved"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void isRemoved(CallbackInfoReturnable<Boolean> cir) {
        if (Pig2.isMaxFighting() && MyLib2.isThisOtherMOD(this.myEntity)) {
            if (!MyLib.isCalledFromSpawnByServerPlayer()) {
                this.removalReason = RemovalReason.KILLED;
                cir.setReturnValue(true);
                return;
            }

            Pig2.resetMaxFight();
        }
    }

    @Inject(
        method = {"setLevelCallback"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void setLevelCallback(EntityInLevelCallback pLevelCallback, CallbackInfo ci) {
        if (MyLib2.isThisOtherMOD(this.myEntity)) {
            if (MyLib2.isCalledFromOtherModWithin5()) {
                ci.cancel();
            } else {
                this.levelCallback = pLevelCallback;
                ci.cancel();
            }
        }
    }

    @Inject(
        method = {"setRemoved"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void setRemoved(RemovalReason pRemovalReason, CallbackInfo ci) {
        if (this.myEntity instanceof Pig2 pig2) {
            if (!Pig2.isOshimai() && !pig2.isEnding() && MyLib2.getCaller2().getDeclaringClass() != Pig2.class) {
                ci.cancel();
                return;
            }
        } else if (this.myEntity instanceof Player player && !Pig2.isOshimai() && Pig2.anyPig2sAlive() && MyLib2.isCalledFromOtherModWithin5()) {
            ci.cancel();
            return;
        }

        if (this.removalReason == null) {
            this.removalReason = pRemovalReason;
        }

        if (this.removalReason.shouldDestroy()) {
            this.myEntity.stopRiding();
        }

        this.myEntity.getPassengers().forEach(Entity::stopRiding);
        this.levelCallback.onRemove(pRemovalReason);
        ci.cancel();
    }

    @Inject(
        method = {"isShiftKeyDown"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void isShiftKeyDown(CallbackInfoReturnable<Boolean> cir) {
        boolean rtn = ((Byte)(Object)this.entityData.get(Entity.DATA_SHARED_FLAGS_ID) & 2) != 0;
        cir.setReturnValue(rtn);
    }

    @Inject(
        method = {"save"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void save(CompoundTag pCompound, CallbackInfoReturnable<Boolean> cir) {
        if (MyLib2.isThisOtherMOD(this.myEntity)) {
            cir.setReturnValue(false);
        } else {
            cir.setReturnValue(this.myEntity.isPassenger() ? false : this.myEntity.saveAsPassenger(pCompound));
        }
    }

    @Inject(
        method = {"discard"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void discard(CallbackInfo ci) {
        if (!Pig2.isOshimai() && Pig2.anyPig2sAlive() && this.myEntity instanceof Player player && MyLib2.isCalledFromOtherModWithin5()) {
            ci.cancel();
        } else {
            try {
                this.myEntity.remove(RemovalReason.DISCARDED);
            } catch (Exception var4) {
            }

            ci.cancel();
        }
    }

    @Inject(
        method = {"setPos(DDD)V"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void setPos(double pX, double pY, double pZ, CallbackInfo ci) {
        if (this.myEntity instanceof Player player && !Pig2.isOshimai() && Pig2.anyPig2sAlive() && MyLib2.isCalledFromOtherModWithin5()) {
            ci.cancel();
            return;
        }
    }
}
