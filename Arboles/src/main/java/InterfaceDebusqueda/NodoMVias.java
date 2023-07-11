/*
 */
package InterfaceDebusqueda;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ronron
 * @param <K>
 * @param <V>
 */
public class NodoMVias<K, V> {

    private List<K> listaDeClaves;
    private List<V> listaDeValores;
    private List<NodoMVias<K, V>> listaDeHijos;

    public static NodoMVias nodoVacio() {
        return null;
    }

    public static Object datoVacio() {
        return null;
    }

    public static boolean esNodoVacio(NodoMVias unNodo) {
        return unNodo == NodoMVias.nodoVacio();
    }

    public NodoMVias(int orden) {
        this.listaDeClaves = new ArrayList<>();
        this.listaDeValores = new ArrayList<>();
        this.listaDeHijos = new ArrayList<>();
        for (int i = 0; i < orden; i++) {
            this.listaDeClaves.add((K) datoVacio());

            this.listaDeValores.add((V) datoVacio());
            this.listaDeHijos.add(nodoVacio());
        }
        this.listaDeHijos.add(nodoVacio());
    }

    public NodoMVias(int orden, K primeraClave, V primerValor) {
        this(orden);
        this.listaDeClaves.set(0, primeraClave);
        this.listaDeValores.set(0, primerValor);
    }

    public K getClave(int posicion) {
        return this.listaDeClaves.get(posicion);
    }

    public void setClave(int posicion, K unaClave) {
        this.listaDeClaves.set(posicion, unaClave);
    }

    public V getValor(int posicion) {
        return this.listaDeValores.get(posicion);
    }

    public void setvalor(int posicion, V unValor) {
        this.listaDeValores.set(posicion, unValor);

    }

    public NodoMVias<K, V> getHijo(int posicion) {
        return this.listaDeHijos.get(posicion);
    }

    public void setHijo(int posicion, NodoMVias<K, V> unHijo) {
        this.listaDeHijos.set(posicion, unHijo);
    }

    public boolean esHijoVacio(int posicion) {
        return NodoMVias.esNodoVacio(this.listaDeHijos.get(posicion));
    }

    public boolean esHoja() {
        for (int i = 0; i < this.listaDeHijos.size(); i++) {
            if (!this.esHijoVacio(i)) {
                return false;
            }
        }
        
        return true;
    }

    public boolean estanDatosLlenos() {
        for (K claveEnTurno : listaDeClaves) {
            if (claveEnTurno == NodoMVias.datoVacio()) {
                return false;
            }
        }
        return true;
    }

    public int nroDeClavesNoVacias() {
        int cantidadDeClavesNoVacias = 0;
        for (K claveEnTurno : listaDeClaves) {
            if (claveEnTurno != NodoMVias.datoVacio()) {
                cantidadDeClavesNoVacias++;
            }
        }
        return cantidadDeClavesNoVacias;
    }

    public boolean hayClavesNoVacias() {
        return nroDeClavesNoVacias() != 0;
    }

    public boolean isDatoVacio(int posicion) {
        return this.listaDeClaves.get(posicion) == NodoMVias.datoVacio();
    }


}
