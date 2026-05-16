package team.zenit.utils;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.*;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;

public class RenderUtil implements IMinecraft{

    public static void drawRect(int x,int y,int width,int height,int color) {
        Gui.drawRect(x,y,x + width,y + height,color);
    }

    public static void drawRect(int x,int y,int width,int height,Color color) {
        drawRect(x,y,width,height,color.getRGB());
    }

    public static void drawRect(float x, float y, float width, float height, int color) {
        if (color == 0) {
            return;
        }
        RenderUtil.setColor(color);
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y + height);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x + width, y);
        GL11.glEnd();
        GlStateManager.resetColor();
    }

    public static void drawRect(float x, float y, float width, float height, Color color) {
        drawRect(x, y, width, height, color.getRGB());
    }

    public static void drawRect(double x, double y, double width, double height, int color) {
        GlStateManager.resetColor();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL_GREATER, 0);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableTexture2D();

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(x, y, 0.0D).color(color >> 16 & 255, color >> 8 & 255, color & 255, color >> 24 & 255).endVertex();
        worldrenderer.pos(x, y + height, 0.0D).color(color >> 16 & 255, color >> 8 & 255, color & 255, color >> 24 & 255).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0D).color(color >> 16 & 255, color >> 8 & 255, color & 255, color >> 24 & 255).endVertex();
        worldrenderer.pos(x + width, y, 0.0D).color(color >> 16 & 255, color >> 8 & 255, color & 255, color >> 24 & 255).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect(double x, double y, double width, double height, Color color) {
        drawRect(x, y, width, height, color.getRGB());
    }

    public static void setColor(int argb) {
        float f = (float) (argb >> 24 & 0xFF) / 255.0f;
        float f2 = (float) (argb >> 16 & 0xFF) / 255.0f;
        float f3 = (float) (argb >> 8 & 0xFF) / 255.0f;
        float f4 = (float) (argb & 0xFF) / 255.0f;
        GlStateManager.color(f2, f3, f4, f);
    }
}
