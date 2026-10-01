package kakiku.pig2mod.mixin;

import kakiku.pig2mod.entity.Pig2;
import kakiku.pig2mod.mixinsub.MyInterfaceOwnerLivingEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LazyOptional.class})
public abstract class MxLazyOptional implements MyInterfaceOwnerLivingEntity {
    @Unique
    public LivingEntity myOwner = null;

    @Override
    public LivingEntity getOwner() {
        return this.myOwner;
    }

    @Override
    public void setOwner(LivingEntity pOwner) {
        this.myOwner = pOwner;
    }

    @Inject(
        method = {"invalidate()V"},
        at = {@At("HEAD")},
        cancellable = true,
        remap = false
    )
    public void invalidate(CallbackInfo ci) {
        if (this.getOwner() instanceof Pig2 pig2 && !Pig2.isOshimai() && !pig2.isEnding()) {
            ci.cancel();
            return;
        }
    }
}
