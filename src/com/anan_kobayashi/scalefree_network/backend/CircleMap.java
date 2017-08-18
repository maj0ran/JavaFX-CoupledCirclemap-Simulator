/*
 *
 * Created by marian on 5/7/2017.
 *
 */

package com.anan_kobayashi.scalefree_network.backend;



import javafx.beans.property.SimpleDoubleProperty;

import java.io.Serializable;

public class CircleMap implements Serializable {

    private SimpleDoubleProperty externalFrequency = new SimpleDoubleProperty();
    private SimpleDoubleProperty amplitude = new SimpleDoubleProperty();
    private SimpleDoubleProperty phase = new SimpleDoubleProperty();


    public double getExternalFrequency() {
        return externalFrequency.get();
    }
    public void setExternalFrequency(double externalFrequency) {
        this.externalFrequency.set(externalFrequency);
    }

    public double getAmplitude() {
        return amplitude.get();
    }
    public final void setAmplitude(double amplitude) {
        this.amplitude.set(amplitude);
    }

    public double getPhase() {
        return phase.get();
    }
    public void setPhase(double phase) {
        this.phase.set(phase);
    }

    public SimpleDoubleProperty externalFrequencyProperty() {
        return externalFrequency;
    }
    public SimpleDoubleProperty phaseProperty() { return phase; }

    public CircleMap()
    {
        this.setPhase(0);
        this.setAmplitude(1);
        this.setExternalFrequency(0);
    }

    public CircleMap(double theta, double amplitude, double external_frequency) {
        this.setPhase(theta);
        this.setAmplitude(amplitude);
        this.setExternalFrequency(external_frequency);
    }


    // CircleMap Equation
    public double get_next_val() {
        return (getPhase() + getExternalFrequency() - (getAmplitude() / (2 * Math.PI)) * Math.sin(2 * Math.PI * getPhase()));
    }


    public CircleMap(CircleMap copyfrom) // copy-constructor
    {
        this.externalFrequency.set(copyfrom.getExternalFrequency());
        this.amplitude.set(copyfrom.getAmplitude());
        this.phase.set(copyfrom.getPhase());
    }

}

