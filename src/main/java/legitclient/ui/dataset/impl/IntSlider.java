package legitclient.ui.dataset.impl;

import legitclient.enums.ChatColors;
import legitclient.property.properties.IntProperty;
import legitclient.ui.dataset.Slider;

public class IntSlider extends Slider {
    private final IntProperty property;

    public IntSlider(IntProperty property) {
        this.property = property;
    }

    @Override
    public double getInput() {
        return property.getValue();
    }

    @Override
    public double getMin() {
        return property.getMinimum();
    }

    @Override
    public double getMax() {
        return property.getMaximum();
    }

    @Override
    public void setValue(double value) {
        property.setValue(new Double(value).intValue());
    }

    @Override
    public String getName() {
        return property.getName().replace("-", " ");
    }

    @Override
    public String getValueString() {
        return ChatColors.formatColor(property.formatValue());
    }

    @Override
    public double getIncrement() {
        return 1;
    }

    @Override
    public boolean isVisible() {
        return property.isVisible();
    }
}
