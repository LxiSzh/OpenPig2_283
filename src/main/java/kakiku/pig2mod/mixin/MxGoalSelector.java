package kakiku.pig2mod.mixin;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import kakiku.pig2mod.entity.Pig2;
import kakiku.pig2mod.mixinsub.MyInterfaceOwnerLivingEntity;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({GoalSelector.class})
public abstract class MxGoalSelector implements MyInterfaceOwnerLivingEntity {
    @Unique
    GoalSelector thisGoalSelector = (GoalSelector)(Object)this;
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
        method = {"removeAllGoals"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void removeAllGoals(Predicate<Goal> pFilter, CallbackInfo ci) {
        if (this.getOwner() instanceof Pig2 pig2 && !Pig2.isOshimai() && !pig2.isEnding()) {
            ci.cancel();
            return;
        }
    }

    @Inject(
        method = {"getAvailableGoals"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void getAvailableGoals(CallbackInfoReturnable<Set<WrappedGoal>> cir) {
        if (this.getOwner() instanceof Pig2 pig2 && !Pig2.isOshimai() && !pig2.isEnding() && MyLib2.isCalledFromOtherModWithin5()) {
            cir.setReturnValue(new HashSet());
            return;
        }
    }
}
