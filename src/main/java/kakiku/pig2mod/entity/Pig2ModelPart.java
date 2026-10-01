package kakiku.pig2mod.entity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class Pig2ModelPart extends ModelPart {
    public Pig2ModelPart(ModelPart pParent) {
        super(pParent.cubes, pParent.children);
        this.x = pParent.x;
        this.y = pParent.y;
        this.z = pParent.z;
        this.xRot = pParent.xRot;
        this.yRot = pParent.yRot;
        this.zRot = pParent.zRot;
        this.xScale = pParent.xScale;
        this.yScale = pParent.yScale;
        this.zScale = pParent.zScale;
        this.visible = pParent.visible;
        this.skipDraw = pParent.skipDraw;
        this.cubes = pParent.cubes;
        this.children = pParent.children;
        this.initialPose = pParent.initialPose;
    }
}
