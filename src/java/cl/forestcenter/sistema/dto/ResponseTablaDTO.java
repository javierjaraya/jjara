/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.dto;

/**
 *
 * @author jjara
 */
public class ResponseTablaDTO {  //JSON
    public int total;
    public Object rows;
    public Object footer;    
    
    public Object getFooter() {
		return footer;
	}

	public void setFooter(Object footer) {
		this.footer = footer;
	}

	public ResponseTablaDTO(){
    	rows = new Object();
        total = 0;
    }

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}

}
