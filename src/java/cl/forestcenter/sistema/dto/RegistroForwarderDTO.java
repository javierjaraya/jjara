/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.dto;

import java.sql.Date;

/**
 *
 * @author Javier-PC
 */
public class RegistroForwarderDTO {

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
    public Integer hMaqPlan;
    public double hMaqReal;
    public double cicloHrPlan;
    public double cicloHrReal;
    public double cicloPlan;
    public double cicloReal;
    public double mHrPlan;
    public double mHrReal;
    public double mPlan;
    public double mReal;
    public String observacion;

    public RegistroForwarderDTO() {
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

    /**
     *
     * @param hMaqPlan VALOR 5 o 10, 5 media jornada y 10 jornada completa
     */
    public void sethMaqPlan(Integer hMaqPlan) {
        this.hMaqPlan = hMaqPlan;
    }

    public int gethMaqPlan() {
        return hMaqPlan;
    }

    public void sethMaqReal(double hMaqReal) {
        this.hMaqReal = hMaqReal;
    }

    public void sethMaqReal(double horometroFinal, double horometroInicial) {
        hMaqReal = 0;
        if (horometroFinal >= 0 && horometroInicial >= 0) {
            hMaqReal = (horometroFinal - horometroInicial);
        }
    }

    public double gethMaqReal() {
        return hMaqReal;
    }

    public double getCicloHrPlan() {
        return cicloHrPlan;
    }

    public void setCicloHrPlan(double cicloHrPlan) {
        this.cicloHrPlan = cicloHrPlan;
    }

    /**
     *
     * @param tipoFaena : 1 = Raleo y/o 2 = Cosecha
     * @param largo: 6 metros ; 4 metros ; 2.4 metros
     * @return Ciclos horas plan
     */
    public void setCicloHrPlan(int tipoFaena, double largo) {
        cicloHrPlan = 0;
        if (tipoFaena == 1) {
            if (largo == 6) {
                cicloHrPlan = 2;
            } else if (largo == 4) {
                cicloHrPlan = 2.5;
            } else if (largo == 2.44) {
                cicloHrPlan = 2;
            }
        } else if (tipoFaena == 2) {
            if (largo == 6) {
                cicloHrPlan = 2.2;
            } else if (largo == 4) {
                cicloHrPlan = 2.3;
            } else if (largo == 2.44) {
                cicloHrPlan = 2.3;
            }
        }
    }

    public void setCicloHrReal(double cicloHrReal) {
        this.cicloHrReal = cicloHrReal;
    }

    public void setCicloHrReal(double cicloReal, double hMaqReal) {
        if (gethMaqReal() > 0) {
            cicloHrReal = cicloReal / hMaqReal;
        }
    }

    public double getCicloHrReal() {
        return cicloHrReal;
    }

    public double getCicloPlan() {
        return cicloPlan;
    }

    public void setCicloPlan(double cicloHrPlan, int hMaqPlan) {
        cicloPlan = cicloHrPlan * hMaqPlan;
    }

    public void setCicloPlan(double cicloPlan) {
        this.cicloPlan = cicloPlan;
    }

    public double getCicloReal() {
        return cicloReal;
    }

    /*
     REPORTE DIARIO TIENE EL VALOR
     */
    public void setCicloReal(double cicloReal) {
        this.cicloReal = cicloReal;
    }

    public double getmHrPlan() {
        return mHrPlan;
    }

    public void setmHrPlan(String codigoMaquina, double cicloHrPlan, double largo) {
        if (largo == 6) {
            if (codigoMaquina.equalsIgnoreCase("Ele 01")) {
                mHrPlan = cicloHrPlan * 19;
            } else if (codigoMaquina.equalsIgnoreCase("Val1") || codigoMaquina.equalsIgnoreCase("Val2") || codigoMaquina.equalsIgnoreCase("Val3")) {
                mHrPlan = cicloHrPlan * 16;
            } else if (codigoMaquina.equalsIgnoreCase("Gre1") ||  codigoMaquina.equalsIgnoreCase("Gre2") || codigoMaquina.equalsIgnoreCase("Gre3")) {
                mHrPlan = cicloHrPlan * 14;
            } else  if (codigoMaquina.equalsIgnoreCase("Elk 01") ||  codigoMaquina.equalsIgnoreCase("Elk 02") || codigoMaquina.equalsIgnoreCase("Elk 03")) {
                mHrPlan = cicloHrPlan * 14;
            } else  if (codigoMaquina.equalsIgnoreCase("Elk 04") ||  codigoMaquina.equalsIgnoreCase("Elk 04")) {
                mHrPlan = cicloHrPlan * 16;
            } else  if (codigoMaquina.equalsIgnoreCase("Buf 01") ||  codigoMaquina.equalsIgnoreCase("Buf 02")) {
                mHrPlan = cicloHrPlan * 16;
            }
        } else if (largo == 4) {
            if (codigoMaquina.equalsIgnoreCase("Ele 01")) {
                mHrPlan = cicloHrPlan * 13.7;
            } else if (codigoMaquina.equalsIgnoreCase("Val1") || codigoMaquina.equalsIgnoreCase("Val2") || codigoMaquina.equalsIgnoreCase("Val3")) {
                mHrPlan = cicloHrPlan * 12;
            } else if (codigoMaquina.equalsIgnoreCase("Gre1") ||  codigoMaquina.equalsIgnoreCase("Gre2") || codigoMaquina.equalsIgnoreCase("Gre3")) {
                mHrPlan = cicloHrPlan * 10;
            } else  if (codigoMaquina.equalsIgnoreCase("Elk 01") ||  codigoMaquina.equalsIgnoreCase("Elk 02") || codigoMaquina.equalsIgnoreCase("Elk 03")) {
                mHrPlan = cicloHrPlan * 10;
            } else  if (codigoMaquina.equalsIgnoreCase("Elk 04") ||  codigoMaquina.equalsIgnoreCase("Elk 04")) {
                mHrPlan = cicloHrPlan * 12;
            } else  if (codigoMaquina.equalsIgnoreCase("Buf 01") ||  codigoMaquina.equalsIgnoreCase("Buf 02")) {
                mHrPlan = cicloHrPlan * 12;
            }
        } else if (largo == 2.44) {
            if (codigoMaquina.equalsIgnoreCase("Ele 01")) {
                mHrPlan = cicloHrPlan * 17.5;
            } else if (codigoMaquina.equalsIgnoreCase("Val1") || codigoMaquina.equalsIgnoreCase("Val2") || codigoMaquina.equalsIgnoreCase("Val3")) {
                mHrPlan = cicloHrPlan * 14;
            } else if (codigoMaquina.equalsIgnoreCase("Gre1") ||  codigoMaquina.equalsIgnoreCase("Gre2") || codigoMaquina.equalsIgnoreCase("Gre3")) {
                mHrPlan = cicloHrPlan * 12;
            } else  if (codigoMaquina.equalsIgnoreCase("Elk 01") ||  codigoMaquina.equalsIgnoreCase("Elk 02") || codigoMaquina.equalsIgnoreCase("Elk 03")) {
                mHrPlan = cicloHrPlan * 12;
            } else  if (codigoMaquina.equalsIgnoreCase("Elk 04") ||  codigoMaquina.equalsIgnoreCase("Elk 04")) {
                mHrPlan = cicloHrPlan * 14;
            } else  if (codigoMaquina.equalsIgnoreCase("Buf 01") ||  codigoMaquina.equalsIgnoreCase("Buf 02")) {
                mHrPlan = cicloHrPlan * 14;
            }
        }
    }

    public void setmHrPlan(double mHrPlan) {
        this.mHrPlan = mHrPlan;
    }

    public double getmHrReal() {
        return mHrReal;
    }

    public void setmHrReal(double mReal, double hMaqReal) {
        if (hMaqReal > 0) {
            mHrReal = mReal / hMaqReal;
        }
    }

    public void setmHrReal(double mHrReal) {
        this.mHrReal = mHrReal;
    }

    public double getmPlan() {
        return mPlan;
    }

    public void setmPlan(int hMaqPlan, double mHrPlan) {
        mPlan = hMaqPlan * mHrPlan;
    }

    public void setmPlan(double mPlan) {
        this.mPlan = mPlan;
    }

    /**
     *
     * @param codigoMaquina Elk 01 // Elk 04 //ELE 01 = Ponsse // Gre1 // Gre2
     * // Gre3 // Val1 // Val2
     * @return
     */
    public void setmReal(String codigoMaquina, double cilcoReal, double largo) {
        if (largo == 6) {
            if (codigoMaquina.equalsIgnoreCase("Ele 01")) {
                mReal = cilcoReal * 19;
            } else if (codigoMaquina.equalsIgnoreCase("Val1") || codigoMaquina.equalsIgnoreCase("Val2") || codigoMaquina.equalsIgnoreCase("Val3")) {
                mReal = cilcoReal * 16;
            } else if (codigoMaquina.equalsIgnoreCase("Gre1") ||  codigoMaquina.equalsIgnoreCase("Gre2") || codigoMaquina.equalsIgnoreCase("Gre3")) {
                mReal = cilcoReal * 14;
            } else  if (codigoMaquina.equalsIgnoreCase("Elk 01") ||  codigoMaquina.equalsIgnoreCase("Elk 02") || codigoMaquina.equalsIgnoreCase("Elk 03")) {
                mReal = cilcoReal * 14;
            } else  if (codigoMaquina.equalsIgnoreCase("Elk 04") ||  codigoMaquina.equalsIgnoreCase("Elk 04")) {
                mReal = cilcoReal * 16;
            } else  if (codigoMaquina.equalsIgnoreCase("Buf 01") ||  codigoMaquina.equalsIgnoreCase("Buf 02")) {
                mReal = cilcoReal * 16;
            }
        } else if (largo == 4) {
            if (codigoMaquina.equalsIgnoreCase("Ele 01")) {
                mReal = cilcoReal * 13.7;
            } else if (codigoMaquina.equalsIgnoreCase("Val1") || codigoMaquina.equalsIgnoreCase("Val2") || codigoMaquina.equalsIgnoreCase("Val3")) {
                mReal = cilcoReal * 12;
            } else if (codigoMaquina.equalsIgnoreCase("Gre1") ||  codigoMaquina.equalsIgnoreCase("Gre2") || codigoMaquina.equalsIgnoreCase("Gre3")) {
                mReal = cilcoReal * 10;
            } else  if (codigoMaquina.equalsIgnoreCase("Elk 01") ||  codigoMaquina.equalsIgnoreCase("Elk 02") || codigoMaquina.equalsIgnoreCase("Elk 03")) {
                mReal = cilcoReal * 10;
            } else  if (codigoMaquina.equalsIgnoreCase("Elk 04") ||  codigoMaquina.equalsIgnoreCase("Elk 04")) {
                mReal = cilcoReal * 12;
            } else  if (codigoMaquina.equalsIgnoreCase("Buf 01") ||  codigoMaquina.equalsIgnoreCase("Buf 02")) {
                mReal = cilcoReal * 12;
            }
        } else if (largo == 2.44) {
            if (codigoMaquina.equalsIgnoreCase("Ele 01")) {
                mReal = cilcoReal * 17.5;
            } else if (codigoMaquina.equalsIgnoreCase("Val1") || codigoMaquina.equalsIgnoreCase("Val2") || codigoMaquina.equalsIgnoreCase("Val3")) {
                mReal = cilcoReal * 14;
            } else if (codigoMaquina.equalsIgnoreCase("Gre1") ||  codigoMaquina.equalsIgnoreCase("Gre2") || codigoMaquina.equalsIgnoreCase("Gre3")) {
                mReal = cilcoReal * 12;
            } else  if (codigoMaquina.equalsIgnoreCase("Elk 01") ||  codigoMaquina.equalsIgnoreCase("Elk 02") || codigoMaquina.equalsIgnoreCase("Elk 03")) {
                mReal = cilcoReal * 12;
            } else  if (codigoMaquina.equalsIgnoreCase("Elk 04") ||  codigoMaquina.equalsIgnoreCase("Elk 04")) {
                mReal = cilcoReal * 14;
            } else  if (codigoMaquina.equalsIgnoreCase("Buf 01") ||  codigoMaquina.equalsIgnoreCase("Buf 02")) {
                mReal = cilcoReal * 14;
            }
        }

    }

    public void setmReal(double mReal) {
        this.mReal = mReal;
    }

    public double getmReal() {
        return mReal;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
