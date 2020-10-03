package com.learn.myunittesting.viewmodel;

import com.learn.myunittesting.model.CuboidModel;

public class MainViewModel {

    private final CuboidModel cuboidModel;

    public MainViewModel(CuboidModel cuboidModel) {
        this.cuboidModel = cuboidModel;
    }

    public void save(double l, double w, double h) {
        cuboidModel.save(l, w, h);
    }

    public double getCircumference() {
        return cuboidModel.getCircumference();
    }

    public double getSurfaceArea() {
        return cuboidModel.getSurfaceArea();
    }

    public double getVolume() {
        return cuboidModel.getVolume();
    }
}
