package kakiku.pig2mod.mixin;

import java.util.Set;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(
    targets = {"net.minecraft.server.level.ChunkMap$TrackedEntity"}
)
public interface MxAccessorTrackedEntity {
    @Accessor("seenBy")
    Set<ServerPlayerConnection> getSeenBy();

    @Accessor("seenBy")
    void setSeenBy(Set<ServerPlayerConnection> var1);

    @Accessor("entity")
    Entity getEntity();
}
