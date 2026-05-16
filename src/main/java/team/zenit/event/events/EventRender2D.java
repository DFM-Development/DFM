package team.zenit.event.events;


import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;
import team.zenit.event.impl.Event;

@Getter
public class EventRender2D implements Event {
    private final float partialTicks;
    private final ScaledResolution scaledResolution;

    public EventRender2D(float partialTicks, ScaledResolution scaledresolution) {
        this.partialTicks = partialTicks;
        this.scaledResolution = scaledresolution;
    }
}
