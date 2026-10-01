package kakiku.pig2mod.mixin;

import kakiku.pig2mod.entity.Pig2;
import kakiku.pig2mod.mixinsub.MyInterfaceOwnerLivingEntity;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Brain.class})
public abstract class MxBrain implements MyInterfaceOwnerLivingEntity {
    @Unique
    Brain thisBrain = (Brain)(Object)this;
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
        method = {"clearMemories"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void clearMemories(CallbackInfo ci) {
        if (this.getOwner() instanceof Pig2 pig2) {
            if (!Pig2.isOshimai() && !pig2.isEnding()) {
                ci.cancel();
                return;
            }
        } else if (this.getOwner() instanceof Player player && MyLib2.isCalledFromOtherModWithin1ExceptMixin()) {
            ci.cancel();
            return;
        }
    }
}
