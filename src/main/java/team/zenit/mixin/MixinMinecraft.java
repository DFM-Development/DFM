package team.zenit.mixin;

import team.zenit.DFM;
import team.zenit.init.Initializer;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({Minecraft.class})
public abstract class MixinMinecraft {

    @Inject(method = "startGame", at = {@At("RETURN")})
    private void startGame(CallbackInfo callbackInfo) {
        new DFM();
    }
}
