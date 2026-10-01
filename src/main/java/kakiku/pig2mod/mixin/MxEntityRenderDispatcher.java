package kakiku.pig2mod.mixin;

import kakiku.pig2mod.entity.Pig2;
import kakiku.pig2mod.entity.Pig2Renderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin({EntityRenderDispatcher.class})
public abstract class MxEntityRenderDispatcher {
    @Unique
    EntityRenderDispatcher thisEntityRenderDispatcher = (EntityRenderDispatcher)(Object)this;
    @Unique
    private Context myContext = null;

    @Inject(
        method = {"getRenderer"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public <T extends Entity> void getRenderer(T pEntity, CallbackInfoReturnable<EntityRenderer<? super T>> cir) {
        if (pEntity instanceof Pig2 && this.myContext != null) {
            cir.setReturnValue((EntityRenderer<? super T>)(Object)new Pig2Renderer(this.myContext));
        }
    }

    @Inject(
        method = {"onResourceManagerReload"},
        at = {@At("TAIL")},
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void onResourceManagerReload(ResourceManager pResourceManager, CallbackInfo ci, Context entityrendererprovider$context) {
        this.myContext = entityrendererprovider$context;
    }
}
