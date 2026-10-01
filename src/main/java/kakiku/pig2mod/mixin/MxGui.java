package kakiku.pig2mod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import kakiku.pig2mod.entity.Pig2;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Gui.class})
public abstract class MxGui {
    @Inject(
        method = {"renderHotbar"},
        at = {@At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V",
            ordinal = 0,
            shift = Shift.BEFORE
        )}
    )
    private void beforeHotbarBackground(float pPartialTick, GuiGraphics pGuiGraphics, CallbackInfo ci) {
        if (Pig2.isMissingClient()) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.5F);
        }
    }

    @Inject(
        method = {"renderHotbar"},
        at = {@At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V",
            ordinal = 0,
            shift = Shift.AFTER
        )}
    )
    private void afterHotbarBackground(float pPartialTick, GuiGraphics pGuiGraphics, CallbackInfo ci) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
