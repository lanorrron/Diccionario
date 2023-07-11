/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package InterfaceDebusqueda;

import Exception.ClaveNoExisteException;
import java.util.List;

/**
 *
 * @author ronron
 * @param <K> 
 * @param <V>
 */
public interface IArbolDeBusqueda<K extends Comparable<K>,V>{
    void insertar(K clave,V valor);
    V eliminar(K clave)throws ClaveNoExisteException;
    V buscar (K clave);
    boolean contiene(K clave);
    int zize();
    int altura();
    void vaciar();
    boolean isArbolVacio();
    int nivel();
    int nodosNoVacios(int n);
    List<K>recorridoEnInOrden();
    List<K>recorridoEnPreOrden();
    List<K>recorridoEnPostOrden();
    List<K>recorridoPorNiveles();
            
}
