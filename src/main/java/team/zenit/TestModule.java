package team.zenit;

import team.zenit.event.annotations.EventTarget;
import team.zenit.event.events.EventRender2D;
import team.zenit.utils.IMinecraft;
import team.zenit.utils.font.FontManager;
import team.zenit.utils.font.CFontRender;

public class TestModule implements IMinecraft {
    public TestModule() {
        DFM.instance.getEventManager().register(this);
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        CFontRender test12 = FontManager.getRenderer("Sf-Regular",12);
        test12.drawString("TestTTT中文测试",10,10,-1);
    }
}
