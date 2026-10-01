package kakiku.pig2mod.mixin;

import kakiku.pig2mod.entity.Pig2;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({EntityRenderer.class})
public abstract class MxEntityRenderer {
    @Redirect(
        method = {"render"},
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/eventbus/api/IEventBus;post(Lnet/minecraftforge/eventbus/api/Event;)Z"
        )
    )
    private boolean pig2$redirectPost(IEventBus eventBus, Event event) {
        if (event instanceof RenderNameTagEvent renderNameTagEvent && renderNameTagEvent.getEntity() instanceof Pig2) {
            return false;
        }

        return eventBus.post(event);
    }
}
