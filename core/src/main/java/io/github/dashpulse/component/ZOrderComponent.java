package io.github.dashpulse.component;

import com.badlogic.ashley.core.Component;

public class ZOrderComponent implements Component {
    public int zOrder = 0;

    public ZOrderComponent(int zOrder) {
        this.zOrder = zOrder;
    }
}
