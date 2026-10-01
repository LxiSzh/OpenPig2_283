package kakiku.pig2mod.mixin;

import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import java.util.List;
import kakiku.pig2mod.entity.Pig2;
import kakiku.pig2mod.map.MyLong2ObjectOpenHashMap;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntitySection;
import net.minecraft.world.level.entity.EntitySectionStorage;
import net.minecraft.world.level.entity.Visibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntitySectionStorage.class})
public abstract class MxEntitySectionStorage<T extends EntityAccess> {
    @Unique
    EntitySectionStorage<T> myEntitySectionStorage = (EntitySectionStorage<T>)(Object)this;
    @Shadow
    public Long2ObjectMap<EntitySection<T>> sections;

    @Inject(
        method = {"<init>"},
        at = {@At("TAIL")}
    )
    public void onInit(Class<T> pEntityClass, Long2ObjectFunction<Visibility> pInitialSectionVisibility, CallbackInfo ci) {
        this.sections = new MyLong2ObjectOpenHashMap<EntitySection<T>>(this.sections, EntitySectionStorage.class);
    }

    @Inject(
        method = {"getOrCreateSection"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void getOrCreateSection(long pSectionPos, CallbackInfoReturnable<EntitySection<T>> cir) {
        if (this.sections instanceof MyLong2ObjectOpenHashMap<EntitySection<T>> mySections) {
            cir.setReturnValue(mySections.myComputeIfAbsent(pSectionPos, this.myEntitySectionStorage::createSection));
        }
    }

    @Inject(
        method = {"remove"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void remove(long pSectionId, CallbackInfo ci) {
        EntitySection<T> section = (EntitySection<T>)(Object)this.sections.get(pSectionId);
        if (section != null && !section.isEmpty()) {
            List<T> pig2List = (List<T>)section.storage.byClass.get(Pig2.class);
            if (pig2List != null && !pig2List.isEmpty() && !Pig2.isOshimai()) {
                ci.cancel();
                return;
            }
        }
    }
}
