package cl.forestcenter.sistema.controladores.mantenedores.nucleo;

import java.util.ArrayList;

/**
 * 
 */
public interface Operaciones {
	
	/**
	 * 
	 * @param args
	 * @return
	 */
	public abstract int insert(String... args);
	/**
	 * 
	 * @param args
	 * @return
	 */
	public abstract int update(String... args);
	/**
	 * 
	 * @param args
	 * @return
	 */
	public abstract int getCantidad(String where);
	/**
	 * 
	 * @param args
	 * @return
	 */
	public abstract ArrayList get(int fila, String where);
	
	
	
}
