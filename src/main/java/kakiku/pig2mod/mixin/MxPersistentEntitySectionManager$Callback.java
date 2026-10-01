package kakiku.pig2mod.mixin;

import kakiku.pig2mod.entity.Pig2;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.Visibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
    targets = {"net.minecraft.world.level.entity.PersistentEntitySectionManager$Callback"}
)
public abstract class MxPersistentEntitySectionManager$Callback<T extends EntityAccess> {
    @Unique
    private String thisPackeageName = this.getClass().getPackageName();
    @Shadow
    private T entity;

    @Inject(
        method = {"onRemove"},
        at = {@At("HEAD")},
        cancellable = true,
        remap = false
    )
    public void onRemove(RemovalReason pReason, CallbackInfo ci) {
        if (this.entity instanceof Pig2 pig2 && !Pig2.isOshimai() && !pig2.isEnding()) {
            ci.cancel();
            return;
        }
    }

    @Inject(
        method = {"updateStatus"},
        at = {@At("HEAD")},
        cancellable = true,
        remap = false
    )
    private void updateStatus(Visibility pOldVisibility, Visibility pNewVisibility, CallbackInfo ci) {
        if ((pNewVisibility == Visibility.HIDDEN || pNewVisibility == Visibility.TRACKED && pOldVisibility == Visibility.TICKING)
            && this.entity instanceof Pig2 pig2
            && !Pig2.isOshimai()
            && !pig2.isEnding()
            && !MyLib2.isCalledFromTheClassAndMethod("net.minecraft.world.level.entity.PersistentEntitySectionManager$Callback.onMove")) {
            ci.cancel();
        }
    }
}
