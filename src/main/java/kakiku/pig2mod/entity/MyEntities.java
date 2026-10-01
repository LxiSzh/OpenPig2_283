package kakiku.pig2mod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class MyEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "pig2mod");
    public static final RegistryObject<EntityType<Pig2>> PIG2 = ENTITY_TYPES.register("pig2", () -> Builder.of(Pig2::new, MobCategory.CREATURE).build("pig2"));
    public static final RegistryObject<EntityType<Snowball2>> SNOWBALL2 = ENTITY_TYPES.register(
        "snowball2", () -> Builder.of((EntityType<Snowball2> pEntityType, Level pLevel) -> new Snowball2(pEntityType, pLevel), MobCategory.MISC).build("snowball2")
    );

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
