package com.anan_kobayashi.scalefree_network.JFXInterface.compositions;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

/*
 *
 * Created by marian on 5/7/2017.
 *
 */

// TODO: This is not finished. Should be a class to make a line with an arrowhead.
class LineArrow {

    Line line_center;
    // Polyline head;
    Polygon head;
    private double head_size = 20;
    private double head_angle = 45;

    public LineArrow() {
        line_center = new Line();
        head = new Polygon();
        head.getPoints().addAll(0.0, 0.0,
                0.0, 0.0,
                0.0, 0.0);
    }

    /**
     * Setting the size of arrowhead in px. Size is the height of the triangle.
     *
     * @param size size of the head
     */
    public void setHeadSize(double size) {
        if (size < 0) {
            size = 0.0;
        }

        this.head_size = size;
    }

    public void setHeadAngle(double angle) {
        this.head_angle = angle % 360;
    }

    public void setStartPos(double x, double y) {
        line_center.setStartX(x);
        line_center.setStartY(y);
    }

    public void setEndPos(double x, double y) {
        line_center.setEndX(x);
        line_center.setEndY(y);

        double arrowhead_left_tip_x = (getEndX() - getStartX()) / this.head_size;

        Point2D arrowhead_left_tip = new Point2D(getEndX() - getStartX(), getEndY() - getStartY());


        //    head.getPoints().set(0, line_center.getEndX())
    }


    public double getStartX() {
        return this.line_center.getStartX();
    }

    public double getStartY() {
        return this.line_center.getStartY();
    }

    public double getEndX() {
        return this.line_center.getEndX();
    }

    public double getEndY() {
        return this.line_center.getEndY();
    }
}
