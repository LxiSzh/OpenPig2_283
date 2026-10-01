package kakiku.pig2mod.mixin;

import kakiku.pig2mod.entity.Pig2;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.entity.EntityAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
    targets = {"net.minecraft.world.level.entity.TransientEntitySectionManager$Callback"}
)
public abstract class MxTransientEntitySectionManager$Callback<T extends EntityAccess> {
    @Shadow
    private T entity;

    @Inject(
        method = {"onRemove"},
        at = {@At("HEAD")},
        cancellable = true,
        remap = false
    )
    public void onRemove(RemovalReason pReason, CallbackInfo ci) {
        if (this.entity instanceof Pig2 pig2 && !Pig2.isOshimai() && !pig2.isEnding() && Pig2.getAliveServerPigIDs(pig2.level).contains(pig2.id)) {
            ci.cancel();
            return;
        }
    }
}
