package kakiku.pig2mod.mixin;

import kakiku.pig2mod.entity.Pig2;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderLivingEvent.Post;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntityRenderer.class})
public class MxLivingEntityRenderer {
    @Inject(
        method = {"getOverlayCoords"},
        at = {@At("HEAD")},
        cancellable = true
    )
    private static void getOverlayCoords(LivingEntity pLivingEntity, float pU, CallbackInfoReturnable<Integer> cir) {
        if (pLivingEntity instanceof Pig2 pig2) {
            cir.setReturnValue(OverlayTexture.pack(OverlayTexture.u(pU), OverlayTexture.v(false)));
        }
    }

    @Redirect(
        method = {"render"},
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/eventbus/api/IEventBus;post(Lnet/minecraftforge/eventbus/api/Event;)Z"
        )
    )
    private boolean pig2$redirectPost(IEventBus eventBus, Event event) {
        if (event instanceof Pre pre && pre.getEntity() instanceof Pig2) {
            return false;
        }

        if (event instanceof Post post && post.getEntity() instanceof Pig2) {
            return false;
        }

        return eventBus.post(event);
    }
}
