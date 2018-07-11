/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Javier J
 */
public class DataSetsGraficoLinea {
    public String label;
    public String fillColor;
    public String strokeColor;
    public String pointColor;
    public String pointStrokeColor;
    public String pointHighlightFill;
    public String pointHighlightStroke;
    public List<Object> data;
    public List<Object> listaMeses;

    public DataSetsGraficoLinea() {
        data = new ArrayList<Object>();
        listaMeses = new ArrayList<Object>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public String getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
    }

    public String getPointColor() {
        return pointColor;
    }

    public void setPointColor(String pointColor) {
        this.pointColor = pointColor;
    }

    public String getPointStrokeColor() {
        return pointStrokeColor;
    }

    public void setPointStrokeColor(String pointStrokeColor) {
        this.pointStrokeColor = pointStrokeColor;
    }

    public String getPointHighlightFill() {
        return pointHighlightFill;
    }

    public void setPointHighlightFill(String pointHighlightFill) {
        this.pointHighlightFill = pointHighlightFill;
    }

    public String getPointHighlightStroke() {
        return pointHighlightStroke;
    }

    public void setPointHighlightStroke(String pointHighlightStroke) {
        this.pointHighlightStroke = pointHighlightStroke;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public List<Object> getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(List<Object> listaMeses) {
        this.listaMeses = listaMeses;
    }
}
