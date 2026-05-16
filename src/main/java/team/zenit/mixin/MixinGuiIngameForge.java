package team.zenit.mixin;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.GuiIngameForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.zenit.DFM;
import team.zenit.event.events.EventRender2D;

import static team.zenit.utils.IMinecraft.mc;

@Mixin(GuiIngameForge.class)
public abstract class MixinGuiIngameForge {
    @Inject(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/GuiIngameForge;renderTitle(IIF)V", shift = At.Shift.AFTER, remap = false)
    )
    private void renderGameOverlay(float partialTicks, CallbackInfo callbackInfo) {
        DFM.instance.getEventManager().call(new EventRender2D(partialTicks,new ScaledResolution(mc)));
    }
}
