package team.zenit;

import lombok.Getter;
import team.zenit.event.EventManager;

@Getter
public class DFM {
    public DFM() {
        this.init();
    }

    public static DFM instance = new DFM();
    private EventManager eventManager;

    public void init() {
        instance = this;
        eventManager = new EventManager();
        new TestModule();
    }
}
