package kakiku.pig2mod.mixin;

import kakiku.pig2mod.mixinsub.MyInterfaceOwnerLivingEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({AttributeMap.class})
public abstract class MxAttributeMap implements MyInterfaceOwnerLivingEntity {
    @Unique
    AttributeMap thisAttributeMap = (AttributeMap)(Object)this;
    @Unique
    public LivingEntity myOwner = null;

    @Shadow
    public abstract void onAttributeModified(AttributeInstance var1);

    @Override
    public LivingEntity getOwner() {
        return this.myOwner;
    }

    @Override
    public void setOwner(LivingEntity pOwner) {
        this.myOwner = pOwner;
    }

    @Inject(
        method = {"getInstance(Lnet/minecraft/world/entity/ai/attributes/Attribute;)Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void getInstance(Attribute pAttribute, CallbackInfoReturnable<AttributeInstance> cir) {
        AttributeInstance instance = this.thisAttributeMap
            .attributes
            .computeIfAbsent(pAttribute, p_22188_ -> this.thisAttributeMap.supplier.createInstance(this::onAttributeModified, p_22188_));
        if (instance != null) {
            ((MyInterfaceOwnerLivingEntity)instance).setOwner(this.myOwner);
        }

        cir.setReturnValue(instance);
    }
}
