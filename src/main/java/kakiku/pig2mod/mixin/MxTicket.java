package kakiku.pig2mod.mixin;

import kakiku.pig2mod.entity.Pig2;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.server.level.Ticket;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Ticket.class})
public abstract class MxTicket<T> {
    @Shadow
    public TicketType<T> type;
    @Shadow
    public T key;

    @Inject(
        method = {"setCreatedTick"},
        at = {@At("HEAD")},
        cancellable = true
    )
    public void setCreatedTick(long pTimestamp, CallbackInfo ci) {
        if (this.key instanceof ChunkPos chunkPos
            && this.type.timeout() != 0L
            && !Pig2.isOshimai()
            && Pig2.anyPig2sAlive()
            && !MyLib2.isCalledFromTheClassAndMethod("net.minecraft.server.level.DistanceManager.addTicket")) {
            ci.cancel();
            return;
        }
    }
}
