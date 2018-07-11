/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.dto;

import java.sql.Date;
import java.util.Hashtable;

/**
 *
 * @author Javier-PC
 */
public class RegistroHarvesterDTO {

    public Integer idRegistroForwarder;
    public Integer folio;
    public Date fechaRegistro;
    public Integer idFaena;
    public String faena;
    public Integer idMaquina;
    public String maquina;
    public Integer idOperador;
    public String operador;
    public String turno;
    public Integer idJornada;
    public String jornada;
    public Integer idPredio;
    public String predio;
    public double horometroInicial;
    public double horometroFinal;

    public double mArbol;
    public double hMaqPlan;
    public double hMaqReal;
    public double arbHrPlan;
    public double arbHrReal;
    public double arbPlan;
    public double arbReal;
    public double mHrPlan;
    public double mHrReal;
    public double mPlan;
    public double mReal;

    public String observacion;

    private Hashtable<Double, Double> diseño;

    public RegistroHarvesterDTO() {
        diseño = new Hashtable<Double, Double>();
        diseño.put(0.0, 0.0);
        diseño.put(0.03, 130.0);
        diseño.put(0.04, 125.0);
        diseño.put(0.05, 120.0);
        diseño.put(0.06, 110.0);
        diseño.put(0.07, 102.0);
        diseño.put(0.08, 100.0);
        diseño.put(0.09, 98.0);
        diseño.put(0.1, 96.0);
        diseño.put(0.11, 94.0);
        diseño.put(0.12, 92.0);
        diseño.put(0.13, 91.0);
        diseño.put(0.14, 88.0);
        diseño.put(0.15, 85.0);
        diseño.put(0.16, 82.0);
        diseño.put(0.17, 76.0);
        diseño.put(0.18, 73.3076923076923);
        diseño.put(0.19, 70.50649350649351);
        diseño.put(0.2, 77.0);
        diseño.put(0.21, 67.0);
        diseño.put(0.22, 74.99142857142857);
        diseño.put(0.23, 68.55506904681711);
        diseño.put(0.24, 76.71012529630885);
        diseño.put(0.25, 69.23381542699725);
        diseño.put(0.26, 65.44250776922037);
        diseño.put(0.27, 63.414264036418814);
        diseño.put(0.28, 63.60705073086845);
        diseño.put(0.29, 62.11439842209073);
        diseño.put(0.3, 65.43542435424354);
        diseño.put(0.31, 62.553752535496955);
        diseño.put(0.32, 57.437722419928825);
        diseño.put(0.33, 62.99337748344371);
        diseño.put(0.34, 55.92151162790697);
        diseño.put(0.35, 62.75135135135135);
        diseño.put(0.36, 60.63492063492063);
        diseño.put(0.37, 59.341059602649004);
        diseño.put(0.38, 56.35);
        diseño.put(0.39, 56.41428571428571);
        diseño.put(0.4, 52.366279069767444);
        diseño.put(0.41, 59.71022727272727);
        diseño.put(0.42, 51.58);
        diseño.put(0.43, 49.46017699115044);
        diseño.put(0.44, 47.28813559322034);
        diseño.put(0.45, 35.68181818181818);
        diseño.put(0.46, 33.476190476190474);
        diseño.put(0.47, 36.625);
        diseño.put(0.48, 42.73076923076923);
        diseño.put(0.49, 44.578947368421055);
        diseño.put(0.5, 20.0);
        diseño.put(0.51, 34.03846153846154);
        diseño.put(0.52, 37.1);
        diseño.put(0.53, 34.0);
        diseño.put(0.54, 37.73);
        diseño.put(0.55, 21.238095238095237);
        diseño.put(0.56, 51.0);
        diseño.put(0.57, 53.17);
        diseño.put(0.58, 28.4);
        diseño.put(0.59, 17.666666666666668);
        diseño.put(0.6, 47.5);
        diseño.put(0.61, 55.82);
        diseño.put(0.62, 40.29);
        diseño.put(0.63, 46.64);
        diseño.put(0.64, 64.13);
        diseño.put(0.65, 46.13);
        diseño.put(0.66, 50.82);
        diseño.put(0.68, 31.92);
        diseño.put(0.69, 32.55);
        diseño.put(0.7, 33.0);
        diseño.put(0.71, 20.25);
        diseño.put(0.72, 33.0);
        diseño.put(0.73, 16.0);
        diseño.put(0.74, 34.27);
        diseño.put(0.75, 33.0);
        diseño.put(0.76, 33.0);
        diseño.put(0.77, 33.0);
        diseño.put(0.79, 33.0);
        diseño.put(0.8, 19.0);
        diseño.put(0.81, 19.0);
        diseño.put(0.82, 19.0);
        diseño.put(0.83, 20.0);
        diseño.put(0.84, 21.0);
        diseño.put(0.85, 25.18);
        diseño.put(0.86, 25.0);
        diseño.put(0.87, 25.0);
        diseño.put(0.88, 26.0);
        diseño.put(0.89, 26.0);
        diseño.put(0.9, 30.0);
        diseño.put(0.91, 30.0);
        diseño.put(0.92, 31.13);
        diseño.put(0.93, 26.8);
        diseño.put(0.94, 26.8);
        diseño.put(0.95, 27.0);
        diseño.put(0.96, 27.0);
        diseño.put(0.97, 26.0);
        diseño.put(0.98, 25.0);
        diseño.put(0.99, 25.0);
        diseño.put(1.0, 24.0);
        diseño.put(1.01, 24.0);
        diseño.put(1.02, 23.0);
        diseño.put(1.03, 23.0);
        diseño.put(1.04, 22.0);
        diseño.put(1.05, 22.0);
        diseño.put(1.06, 21.0);
        diseño.put(1.07, 21.0);
        diseño.put(1.08, 21.0);
        diseño.put(1.09, 20.0);
        diseño.put(1.1, 20.0);
        diseño.put(1.12, 20.0);
        diseño.put(1.14, 15.0);
        diseño.put(1.15, 15.0);
        diseño.put(1.15, 15.0);
        diseño.put(1.19, 15.0);
        diseño.put(1.21, 14.0);
        diseño.put(1.14, 14.0);
        diseño.put(1.15, 14.0);
        diseño.put(1.135, 13.0);
        diseño.put(1.19, 13.0);
        diseño.put(1.2, 13.0);
        diseño.put(1.21, 12.0);
        diseño.put(1.25, 12.0);
        diseño.put(1.26, 12.0);
        diseño.put(1.27, 11.0);
        diseño.put(1.28, 11.0);
        diseño.put(1.29, 11.0);
        diseño.put(1.3, 10.0);
        diseño.put(1.31, 10.0);
        diseño.put(1.32, 10.0);
        diseño.put(1.33, 10.0);
        diseño.put(1.34, 10.0);
        diseño.put(1.35, 9.0);
        diseño.put(1.351, 9.0);
        diseño.put(1.36, 9.0);
        diseño.put(1.37, 9.0);
    }

    public Integer getIdRegistroForwarder() {
        return idRegistroForwarder;
    }

    public void setIdRegistroForwarder(Integer idRegistroForwarder) {
        this.idRegistroForwarder = idRegistroForwarder;
    }

    public Integer getFolio() {
        return folio;
    }

    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdFaena() {
        return idFaena;
    }

    public void setIdFaena(Integer idFaena) {
        this.idFaena = idFaena;
    }

    public String getFaena() {
        return faena;
    }

    public void setFaena(String faena) {
        this.faena = faena;
    }

    public Integer getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Integer idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public Integer getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Integer idOperador) {
        this.idOperador = idOperador;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Integer getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(Integer idJornada) {
        this.idJornada = idJornada;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public Integer getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(Integer idPredio) {
        this.idPredio = idPredio;
    }

    public String getPredio() {
        return predio;
    }

    public void setPredio(String predio) {
        this.predio = predio;
    }

    public double getHorometroInicial() {
        return horometroInicial;
    }

    public void setHorometroInicial(double horometroInicial) {
        this.horometroInicial = horometroInicial;
    }

    public double getHorometroFinal() {
        return horometroFinal;
    }

    public void setHorometroFinal(double horometroFinal) {
        this.horometroFinal = horometroFinal;
    }

    public double getmArbol() {
        return mArbol;
    }

    public void setmArbol(double mArbol) {
        this.mArbol = mArbol;
    }

    public void setmArbol(double mReal, double arbReal) {
        if (arbReal > 0) {
            mArbol = mReal / arbReal;
            int cifras = (int) Math.pow(10, 2); //0.00
            mArbol = Math.rint(mArbol * cifras) / cifras;
        }
    }

    public double gethMaqPlan() {
        return hMaqPlan;
    }

    public void sethMaqPlan(double hMaqPlan) {
        this.hMaqPlan = hMaqPlan;
    }

    public double gethMaqReal() {
        return hMaqReal;
    }

    public void sethMaqReal(double hMaqReal) {
        this.hMaqReal = hMaqReal;
    }

    public void sethMaqReal(double horometroFinal, double horometroInicial) {
        hMaqReal = horometroFinal - horometroInicial;
    }

    public double getArbHrPlan() {
        return arbHrPlan;
    }

    public void setArbHrPlan(double arbHrPlan) {
        this.arbHrPlan = arbHrPlan;
    }

    public void setArbHrPlanCalcular(double mArbol) {
        //try{
        arbHrPlan = diseño.get(mArbol);
        /*}catch(NullPointerException e){
         arbHrPlan = 0;
         }*/
        int cifras = (int) Math.pow(10, 2); //0.00
        arbHrPlan = Math.rint(arbHrPlan * cifras) / cifras;
    }

    public double getArbHrReal() {
        return arbHrReal;
    }

    public void setArbHrReal(double arbHrReal) {
        this.arbHrReal = arbHrReal;
    }

    public void setArbHrReal(double arbReal, double hrMaqReal) {
        if (hrMaqReal > 0) {
            this.arbHrReal = arbReal / hrMaqReal;
        }
    }

    public double getArbPlan() {
        return arbPlan;
    }

    public void setArbPlan(double arbPlan) {
        this.arbPlan = arbPlan;
    }

    public void setArbPlan(double hrMaqPlan, double arbHrPlan) {
        arbPlan = hrMaqPlan * arbHrPlan;
    }

    public double getArbReal() {
        return arbReal;
    }

    public void setArbReal(double arbReal) {
        this.arbReal = arbReal;
    }

    public double getmHrPlan() {
        return mHrPlan;
    }

    public void setmHrPlan(double mHrPlan) {
        this.mHrPlan = mHrPlan;
    }

    public void setmHrPlan(double mPlan, double hrMaqPlan) {
        if (hrMaqPlan > 0) {
            mHrPlan = mPlan / hrMaqPlan;
            int cifras = (int) Math.pow(10, 2); //0.00
            mHrPlan = Math.rint(mHrPlan * cifras) / cifras;
        }
    }

    public double getmHrReal() {
        return mHrReal;
    }

    public void setmHrReal(double mHrReal) {
        this.mHrReal = mHrReal;
    }

    public void setmHrReal(double mReal, double hrMaqReal) {
        if (hrMaqReal > 0) {
            mHrReal = mReal / hrMaqReal;
        }
    }

    public double getmPlan() {
        return mPlan;
    }

    public void setmPlan(double mPlan) {
        this.mPlan = mPlan;
    }

    public void setmPlan(double mArbol, double arbPlan) {
        mPlan = mArbol * arbPlan;
    }

    public double getmReal() {
        return mReal;
    }

    public void setmReal(double mReal) {
        this.mReal = mReal;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
