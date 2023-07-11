/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arboles;

import Exception.ClaveNoExisteException;
import InterfaceDebusqueda.IArbolDeBusqueda;
import InterfaceDebusqueda.NodoMVias;
import Exception.OrdenInvalidoException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author ronron
 * @param <K>
 * @param <V>
 */
public class ArbolMViasBusqueda<K extends Comparable<K>, V> implements IArbolDeBusqueda<K, V> {

    protected NodoMVias<K, V> raiz;
    protected int orden;
    private static final int ORDEN_MINIMO = 3;
    protected static final int POSICION_NO_VALIDA = -1;

    public ArbolMViasBusqueda() {
        this.orden = ORDEN_MINIMO;
    }

    public ArbolMViasBusqueda(int orden) throws OrdenInvalidoException {
        if (orden < ORDEN_MINIMO) {
            throw new OrdenInvalidoException("necesita poner una orden mayor a 2");
        }
        this.orden = orden;
    }

    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("clave a insertar no puede ser nula");
        }
        if (valorAsociado == null) {
            throw new IllegalArgumentException("valor asociado no puede ser nulo");
        }
        if (this.isArbolVacio()) {
            this.raiz = new NodoMVias<>(orden, claveAInsertar, valorAsociado);
            return;
        }

        NodoMVias<K, V> nodoActual = this.raiz;
        do {
            int posicionDeLaClaveAInsertar = obtenerPosicionDeClave(claveAInsertar, nodoActual);

            if (posicionDeLaClaveAInsertar != ArbolMViasBusqueda.POSICION_NO_VALIDA) {
                // TODO si lca clave ya es igual, se debe setar, y si no es igual mover el calve actual
                nodoActual.setvalor(posicionDeLaClaveAInsertar, valorAsociado);
                nodoActual = NodoMVias.nodoVacio();
            } else if (nodoActual.esHoja()) {
                if (nodoActual.estanDatosLlenos()) {
                    int posicionPorDondeBajar = this.obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
                    NodoMVias<K, V> nuevoNodo = new NodoMVias<>(orden, claveAInsertar, valorAsociado);
                    nodoActual.setHijo(posicionPorDondeBajar, nuevoNodo);
                } else {
                    insertarClaveYvalorEnNodoOrdenado(nodoActual, claveAInsertar, valorAsociado);
                }
                nodoActual = NodoMVias.nodoVacio();
            } else {
                int posicionPorDondeBajar = this.obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
                if (nodoActual.esHijoVacio(posicionPorDondeBajar)) {
                    NodoMVias<K, V> nuevoNodo = new NodoMVias<>(orden, claveAInsertar, valorAsociado);
                    nodoActual.setHijo(posicionPorDondeBajar, nuevoNodo);
                    nodoActual = NodoMVias.nodoVacio();
                } else {
                    nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
                }

            }

        } while (!NodoMVias.esNodoVacio(nodoActual));
    }

    protected void insertarClaveYvalorEnNodoOrdenado(NodoMVias<K, V> nodoActual, K claveAInsertar, V valorAsociado) {
        int i = orden - 2; // asi es para arbol b y asi es para arbolMviasDeBusqueda int i=0rden-2;

        while (i >= 0) {
            K claveActual = nodoActual.getClave(i);
            if (claveActual != null && claveActual.compareTo(claveAInsertar) < 0) {
                nodoActual.setClave(i + 1, claveAInsertar);
                nodoActual.setvalor(i + 1, valorAsociado);

                return;
            }

            nodoActual.setClave(i + 1, nodoActual.getClave(i));
            nodoActual.setvalor(i + 1, nodoActual.getValor(i));

            i--;
        }

        nodoActual.setClave(0, claveAInsertar);
        nodoActual.setvalor(0, valorAsociado);
    }

    protected int obtenerPosicionPorDondeBajar(NodoMVias<K, V> nodoActual, K claveAinsertar) {// trabajar aqui
        int posicionPorDondeBajar = -1;
        for (int i = 0; i < orden; i++) {
            if (nodoActual.getClave(i) == null) {
                return i;
            }

            if (claveAinsertar.compareTo(nodoActual.getClave(i)) < 0) {
                return posicionPorDondeBajar = i;
            }
        }

        if (claveAinsertar.compareTo(nodoActual.getClave(orden - 1)) > 0) { //editado lo anterior y lo que era es orden-1
            posicionPorDondeBajar = orden;
        }

        return posicionPorDondeBajar;
    }

    protected int obtenerPosicionDeClave(K claveAInsertar, NodoMVias<K, V> nodoActual) {
        int posicionDeClave = -1;
        for (int i = 0; i < orden; i++) {
            if (nodoActual.isDatoVacio(i)) {
                break;
            } else if (claveAInsertar.compareTo(nodoActual.getClave(i)) == 0) {
                posicionDeClave = i;
                break;
            }
        }
        return posicionDeClave;
    }

    @Override
    public V eliminar(K claveAEliminar) throws ClaveNoExisteException {
        if (claveAEliminar == null) {
            throw new NullPointerException("la clave  a eliminar no puede ser nula");
        }
        V valorAEliminar = this.buscar(claveAEliminar);
        if (valorAEliminar == null) {
            throw new ClaveNoExisteException();
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorAEliminar;
    }

    private NodoMVias<K, V> eliminar(NodoMVias<K, V> nodoActual, K claveAEliminar) {

        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);

            if (claveAEliminar.compareTo(claveActual) == 0) {

                if (nodoActual.esHoja()) {     // caso 1
                    this.eliminarClaveValorDePosicion(nodoActual, i);

                    if (nodoActual.nroDeClavesNoVacias() == 0) {
                        return NodoMVias.nodoVacio();
                    }
                    return nodoActual;
                }
                //caso 2 o 3
                K claveDeReemplazo;
                if (this.hayHijosMasAdelanteDeLaPosicion(nodoActual, i)) {
                    //caso 2
                    claveDeReemplazo = this.obtenerClaveSucesoraInOrden(nodoActual, i); //editado
                } else {
                    //caso 3
                    claveDeReemplazo = obtenerClavePredecesoraInOrden(nodoActual, i);//editado
                }

                V valorAsociadoClaveDeReemplazo = this.buscar(claveDeReemplazo);
                nodoActual = eliminar(nodoActual, claveDeReemplazo);
                nodoActual.setClave(i, claveDeReemplazo);
                nodoActual.setvalor(i, valorAsociadoClaveDeReemplazo);
                return nodoActual;
            }
            if (claveAEliminar.compareTo(claveActual) < 0) {
                NodoMVias<K, V> supuestoNUevoHijo = eliminar(nodoActual.getHijo(i), claveAEliminar);
                nodoActual.setHijo(i, supuestoNUevoHijo);
                return nodoActual;
            }
        }// fin for
        NodoMVias<K, V> supuestoNuevoHijo = eliminar(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()), claveAEliminar);
        nodoActual.setHijo(nodoActual.nroDeClavesNoVacias(), supuestoNuevoHijo);//editado

        return nodoActual;
    }

    private K obtenerClaveSucesoraInOrden(NodoMVias<K, V> nodoActual, int posicionDeLaClave) {
        if (NodoMVias.esNodoVacio(nodoActual.getHijo(posicionDeLaClave + 1))) {
            return nodoActual.getClave(posicionDeLaClave + 1);
        }

        NodoMVias<K, V> nodoAnterior = nodoActual;

        nodoActual = nodoActual.getHijo(posicionDeLaClave + 1);
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijo(0);
        }

        return nodoAnterior.getClave(0);
    }

    private K obtenerClavePredecesoraInOrden(NodoMVias<K, V> nodoActual, int posicionDeLaClave) {
        if (NodoMVias.esNodoVacio(nodoActual.getHijo(posicionDeLaClave))) {
            return nodoActual.getClave(posicionDeLaClave - 1);
        }
        NodoMVias<K, V> nodoAnterior = nodoActual;
        nodoActual = nodoActual.getHijo(posicionDeLaClave);
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijo(orden - 1);
        }
        return nodoAnterior.getClave(nodoAnterior.nroDeClavesNoVacias() - 1);
    }

    private boolean hayHijosMasAdelanteDeLaPosicion(NodoMVias<K, V> nodoActual, int posicion) {
        for (int i = posicion + 1; i < nodoActual.nroDeClavesNoVacias(); i++) {
            if (!nodoActual.esHijoVacio(i)) {
                return true;
            }
        }
        return false;
    }

    protected void eliminarClaveValorDePosicion(NodoMVias<K, V> nodoActual, int posicion) { // verificar si este m'etodo est'a correcto
        nodoActual.setClave(posicion, null);
        nodoActual.setvalor(posicion, null);
    }

    @Override

    public V buscar(K claveABuscar) {
        if (claveABuscar == null) {
            throw new IllegalArgumentException("clave a buscar no puede ser nula");
        }
        NodoMVias<K, V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            boolean hayCambioDeNodoActual = false;
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias() && !hayCambioDeNodoActual; i++) {
                K claveActual = nodoActual.getClave(i);
                if (claveABuscar.compareTo(claveActual) == 0) {
                    return nodoActual.getValor(i);
                } else if (claveABuscar.compareTo(claveActual) < 0) {
                    nodoActual = nodoActual.getHijo(i);
                    hayCambioDeNodoActual = true;
                }
            }
            if (!hayCambioDeNodoActual) {
                nodoActual = nodoActual.getHijo(nodoActual.nroDeClavesNoVacias());
            }
        }

        return null;
    }

    @Override
    public boolean contiene(K clave) {
        return buscar(clave) != null;
    }

    @Override
    public int zize() {
        int nroHijosNoVacios = 0;
        if (!this.isArbolVacio()) {
            Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);

            while (!colaDeNodos.isEmpty()) {
                NodoMVias<K, V> nodoActual = colaDeNodos.poll();
                nroHijosNoVacios++;
                for (int i = 0; i < orden; i++) {
                    if (!nodoActual.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }
            }
        }
        return nroHijosNoVacios;
    }

    @Override
    public int altura() {
        return altura(this.raiz);

    }

    private int altura(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaMayor = 0;
        for (int i = 0; i < orden; i++) {
            int alturaDehijoEnTurno = altura(nodoActual.getHijo(i));
            if (alturaDehijoEnTurno > alturaMayor) {
                alturaMayor = alturaDehijoEnTurno;
            }
        }
        return alturaMayor + 1;
    }

    @Override
    public void vaciar() {
        this.raiz = NodoMVias.nodoVacio();
    }

    @Override
    public boolean isArbolVacio() {
        return NodoMVias.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
        return nivel(this.raiz);
    }

    private int nivel(NodoMVias<K, V> nodoActual) {
        int nivel = 0;
        for (int i = 0; i < orden; i++) {
            int alturaDehijoEnTurno = nivel(nodoActual.getHijo(i));
            if (alturaDehijoEnTurno > nivel) {
                nivel = alturaDehijoEnTurno;
            }
        }
        return nivel;

    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;

    }

    private void recorridoEnInOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            recorridoEnInOrden(nodoActual.getHijo(i), recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
        recorridoEnInOrden(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()), recorrido);
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;

    }

    private void recorridoEnPreOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            recorrido.add(nodoActual.getClave(i));
            recorridoEnPreOrden(nodoActual.getHijo(i), recorrido);
        }
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnPostOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        recorridoEnPostOrden(nodoActual.getHijo(0), recorrido);
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            recorridoEnPostOrden(nodoActual.getHijo(i + 1), recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new ArrayList<>();
        if (!this.isArbolVacio()) {
            Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);

            while (!colaDeNodos.isEmpty()) {
                NodoMVias<K, V> nodoActual = colaDeNodos.poll();

                for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                    recorrido.add(nodoActual.getClave(i));
                    if (!nodoActual.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoActual.getHijo(i));

                    }
                }
                if (!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())) {

                    colaDeNodos.offer(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));

                }
            }

        }
        return recorrido;

    }
    @Override
public int nodosNoVacios(int nivelN) {
    int nivelInicial = -1;
    int nodosNoVacios = 0;
    
    if (!this.isArbolVacio()) {
        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        
        while (!colaDeNodos.isEmpty()) {
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();
            
            if (nodoActual != null) {
                if (nivelInicial != nivelN) {
                    for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                        if (nodoActual.getHijo(i) != null) {
                            colaDeNodos.offer(nodoActual.getHijo(i));
                        }
                        if (nodoActual.getClave(orden - 1) != null) {
                            colaDeNodos.offer(nodoActual.getHijo(orden));
                        }
                    }
                }
                nivelInicial++;
                
                if (nivelInicial == nivelN) {
                    for (int i = 0; i < orden; i++) {
                        if (!nodoActual.esHijoVacio(i)) {
                            nodosNoVacios++;
                        }
                    }
                }
            }
        }
    }
    
    return nodosNoVacios;
}
}
