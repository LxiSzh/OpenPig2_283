package kakiku.pig2mod.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class Pig2Renderer extends MobRenderer<Pig2, Pig2Model<Pig2>> {
    private static final ResourceLocation PIG2_LOCATION = new ResourceLocation("pig2mod", "json/settings.json");
    private static Context gContext = null;

    public Pig2Renderer(Context pContext) {
        super(pContext, new Pig2Model(pContext.bakeLayer(ModelLayers.PIG)), 0.7F);
        gContext = pContext;
    }

    public ResourceLocation getTextureLocation(Pig2 pEntity) {
        return PIG2_LOCATION;
    }

    protected void setupRotations(Pig2 pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        if (pEntityLiving.deathTime > 0) {
            pEntityLiving.deathTime = 0;
            pEntityLiving.setHealth(10.0F);
        }

        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
    }

    public void render(Pig2 pig2, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pig2.deathTime > 0) {
            pig2.deathTime = 0;
            pig2.setHealth(10.0F);
        }

        super.render(pig2, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    public boolean shouldRender(Pig2 entity, Frustum frustum, double camX, double camY, double camZ) {
        return true;
    }
}
