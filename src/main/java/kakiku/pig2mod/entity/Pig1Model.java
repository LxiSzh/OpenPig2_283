package kakiku.pig2mod.entity;

import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Pig1Model<T extends Entity> extends PigModel<T> {
    public Pig1Model(ModelPart pRoot) {
        super(pRoot);
    }
}
