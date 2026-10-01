package kakiku.pig2mod.entity;

import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class Pig2Model<T extends Entity> extends PigModel<T> {
    public Pig2Model(ModelPart pRoot) {
        super(pRoot);
        this.head = new Pig2ModelPart(this.head);
        this.body = new Pig2ModelPart(this.body);
        this.leftFrontLeg = new Pig2ModelPart(this.leftFrontLeg);
        this.rightFrontLeg = new Pig2ModelPart(this.rightFrontLeg);
        this.leftHindLeg = new Pig2ModelPart(this.leftHindLeg);
        this.rightHindLeg = new Pig2ModelPart(this.rightHindLeg);
    }
}
