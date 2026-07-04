package team.zenit;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import team.zenit.event.annotations.EventTarget;
import team.zenit.event.events.EventRender2D;
import team.zenit.utils.IMinecraft;
import team.zenit.utils.RenderUtil;
import team.zenit.utils.animation.Easing;
import team.zenit.utils.animation.EasingAnimation;
import team.zenit.utils.font.CFontRender;
import team.zenit.utils.font.FontManager;

import java.awt.*;


public class TestModule implements IMinecraft {
    public TestModule() {
        DFM.instance.getEventManager().register(this);
    }

    private final EasingAnimation healthAnimation = new EasingAnimation(Easing.EASE_OUT_CUBIC, 200, 0);

    private float lastRatio = 1.0f;
    private long damageTime = 0L;

    @EventTarget
    public void onRender2D(EventRender2D event) {
        if (mc.thePlayer == null) return;
        ScaledResolution sr = event.getScaledResolution();
        CFontRender name = FontManager.getRenderer("NotoSansR",12.5f);


        renderPlayer2D(20,sr.getScaledHeight() - 77,39,39, mc.thePlayer, 255);

        int fontColor = new Color(255,255,255,170).getRGB();

        name.drawStringWithShadow(mc.thePlayer.getName(),64,sr.getScaledHeight() - 38 - name.getFontHeight(),fontColor);

        int bgX = 64;
        int bgY = sr.getScaledHeight() - 38 - name.getFontHeight() - 13;
        int bgW = 166;
        int bgH = 8;

        RenderUtil.drawRect(bgX, bgY, bgW, bgH, new Color(0,0,0,110).getRGB());

        float health = mc.thePlayer.getHealth();
        float maxHealth = mc.thePlayer.getMaxHealth();
        float ratio = Math.max(0f, Math.min(1f, health / maxHealth));

        long now = System.currentTimeMillis();

        if (ratio > lastRatio) {
            lastRatio = ratio;
            damageTime = 0L;
        } else if (ratio < lastRatio) {
            if (damageTime == 0L) {
                damageTime = now;
            }
        }

        long delayMillis = 500L;
        if (damageTime != 0L && now - damageTime >= delayMillis) {
            lastRatio = ratio;
            damageTime = 0L;
        }

        //玩家血量矩形渲染
        int whiteBarLength = Math.max(0, (int) ((bgW - 2) * ratio));
        float displayedDelayedRatio = (float) healthAnimation.getValue(lastRatio);
        int delayedBarLength = Math.max(0, (int) ((bgW - 2) * displayedDelayedRatio));

        int delayedColor = new Color(98, 26, 17, 170).getRGB();

        int lowHealthColor = new Color(171, 47, 35, 170).getRGB();
        int mainBarColor = ratio <= 0.30f ? lowHealthColor : fontColor;

        RenderUtil.drawRect(bgX + 1, bgY + 1, delayedBarLength, Math.max(0, bgH - 2), delayedColor);

        RenderUtil.drawRect(bgX + 1, bgY + 1, whiteBarLength, Math.max(0, bgH - 2), mainBarColor);
    }

    private void renderPlayer2D(float x, float y, float width, float height, AbstractClientPlayer player, float masterAlpha) {
        mc.getTextureManager().bindTexture(player.getLocationSkin());
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1f, 1f, 1f, masterAlpha);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer buffer = tessellator.getWorldRenderer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y + height, 0).tex(8f / 64f, 16f / 64f).endVertex();
        buffer.pos(x + width, y + height, 0).tex(16f / 64f, 16f / 64f).endVertex();
        buffer.pos(x + width, y, 0).tex(16f / 64f, 8f / 64f).endVertex();
        buffer.pos(x, y, 0).tex(8f / 64f, 8f / 64f).endVertex();
        tessellator.draw();

        GlStateManager.disableBlend();
        GlStateManager.color(1f, 1f, 1f, 1f); // 重置状态

    }
}
