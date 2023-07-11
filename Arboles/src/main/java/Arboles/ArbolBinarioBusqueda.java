/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arboles;

import Exception.ClaveNoExisteException;
import InterfaceDebusqueda.IArbolDeBusqueda;
import InterfaceDebusqueda.NodoBinario;
import java.util.ArrayList;
import java.util.Arrays;
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
public class ArbolBinarioBusqueda<K extends Comparable<K>, V> implements IArbolDeBusqueda<K, V> {

    protected NodoBinario<K, V> raiz;
    
    public ArbolBinarioBusqueda(){
    }

    public ArbolBinarioBusqueda(boolean isConPreOrden, List<K> llavesRecInOrden,
            List<V> valoresRecInOrden, List<K> llavesRecNoInOrden, List<V> ValoresRecNoInOrden) {

        if (llavesRecInOrden == null || llavesRecNoInOrden == null) {
            throw new IllegalArgumentException("las listas de clavex no pueden ser nulas");
        }
        if (llavesRecInOrden.size() != llavesRecNoInOrden.size()) {
            throw new IllegalArgumentException("las listas  de claves no pueden tener tamaños diferentes");
        }
        if (isConPreOrden) {
            this.raiz = reconstruirConPreOrden(llavesRecInOrden, valoresRecInOrden, llavesRecNoInOrden, ValoresRecNoInOrden);
        } else {
            this.raiz = reconstruirConPostOrden(llavesRecInOrden, valoresRecInOrden, llavesRecNoInOrden, ValoresRecNoInOrden);
        }
    }

    private int obtenerPosicionDeclave(List<K> listaDeClaves, K clave) {
        for (int i = 0; i < listaDeClaves.size(); i++) {
            K claveEnTurno = listaDeClaves.get(i);
            if (claveEnTurno.compareTo(clave) == 0) {
                return i;
            }
        }
        return -1;
    }

    private NodoBinario<K, V> reconstruirConPostOrden(List<K> llavesRecInOrden,
            List<V> valoresRecInOrden, List<K> llavesRePostOrden, List<V> ValoresRecPostOrden) {

        if (llavesRecInOrden.isEmpty()) {
            return NodoBinario.nodoVacio();
        }
        int posicionEnTurnoPostOrden = llavesRePostOrden.size() - 1;
        K claveDelNodoActual = llavesRePostOrden.get(posicionEnTurnoPostOrden);
        V valorDelNodoActual = ValoresRecPostOrden.get(posicionEnTurnoPostOrden);

        int posicionEnTurnoEnInOrden = obtenerPosicionDeclave(llavesRecInOrden, claveDelNodoActual);
        //para armar rama Izquierda
        List<K> llavesEnInOrdenXIzq = llavesRecInOrden.subList(0, posicionEnTurnoEnInOrden);
        List<V> valoresEnInOrdenXIzq = valoresRecInOrden.subList(0, posicionEnTurnoEnInOrden);

        List<K> llavesEnPostOrdenXIzq = llavesRePostOrden.subList(0, posicionEnTurnoEnInOrden);
        List<V> valoresEnPostOrdenXIzq = ValoresRecPostOrden.subList(0, posicionEnTurnoEnInOrden);

        NodoBinario<K, V> HijoIzquierdoDelNodoActual = reconstruirConPostOrden(llavesEnInOrdenXIzq, valoresEnInOrdenXIzq,
                llavesEnPostOrdenXIzq, valoresEnPostOrdenXIzq);
        //par armar la rama derecha
        
        List<K>llavesEnInOrdenXDer=llavesRecInOrden.subList(posicionEnTurnoEnInOrden+1, llavesRecInOrden.size()-1);
        List<V>valoresEnInOrdenXDer=valoresRecInOrden.subList(posicionEnTurnoEnInOrden+1,ValoresRecPostOrden.size() -1);
        
        List<K>llavesEnPostXDer=llavesRePostOrden.subList(posicionEnTurnoEnInOrden,posicionEnTurnoPostOrden);
        List<V>valoresEnPostOrdenXDer=ValoresRecPostOrden.subList(posicionEnTurnoEnInOrden, posicionEnTurnoPostOrden);
        
        NodoBinario<K,V>hijoDerechoDelNodoActual=reconstruirConPostOrden(llavesEnInOrdenXDer,valoresEnInOrdenXDer,
                llavesEnPostXDer,valoresEnPostOrdenXDer);
        
        NodoBinario<K,V>nodoActual=new NodoBinario<>(claveDelNodoActual,valorDelNodoActual);
        nodoActual.setHijoIzquierdo(HijoIzquierdoDelNodoActual);
        nodoActual.setHijoDerecho(hijoDerechoDelNodoActual);
       
       return nodoActual;
    }
    
    private  NodoBinario<K,V>reconstruirConPreOrden(List <K>llavesRecEnInOrden,List <V>valoresRecEnInOrden,
            List<K>llavesRecConPreOrden,List<V>valoresRecConPreOrden){
        if(llavesRecEnInOrden.isEmpty()){
            return NodoBinario.nodoVacio();
        }
        
        int posicionEnTurnoEnPreOrden=0;
        
        K claveDelNodoActual=llavesRecConPreOrden.get(posicionEnTurnoEnPreOrden);
        V valorDelNodoActual=valoresRecConPreOrden.get(posicionEnTurnoEnPreOrden);
        
        int posicionEnTurnoEnInOrden=obtenerPosicionDeclave(llavesRecEnInOrden, claveDelNodoActual);
        //para armar por la izquierda
        List<K> llavesEnInOrdenXIzq = llavesRecEnInOrden.subList(0, posicionEnTurnoEnInOrden);
        List<V> valoresEnInOrdenXIzq = valoresRecEnInOrden.subList(0, posicionEnTurnoEnInOrden);
        
        List<K>llavesEnPreOrdenXIzq=llavesRecConPreOrden.subList(posicionEnTurnoEnPreOrden+1,posicionEnTurnoEnInOrden);
        List<V>valoresEnPreOrdenXIzq=valoresRecConPreOrden.subList(posicionEnTurnoEnInOrden+1, posicionEnTurnoEnInOrden);
        
        
        NodoBinario<K,V>hijoIzquierdoDelNodoActual=reconstruirConPreOrden(llavesEnInOrdenXIzq, valoresEnInOrdenXIzq, 
                llavesEnPreOrdenXIzq, valoresEnPreOrdenXIzq);
        //para rama derecha
        List<K>llavesEnInOrdenXDer=llavesRecEnInOrden.subList(posicionEnTurnoEnInOrden+1, llavesRecEnInOrden.size()-1);
        List<V>valoresEnInOrdenXDer=valoresRecEnInOrden.subList(posicionEnTurnoEnInOrden+1,valoresRecEnInOrden.size() -1);
        
        List<K>llavesEnPreOrdenXDer=llavesRecConPreOrden.subList(posicionEnTurnoEnInOrden,llavesRecConPreOrden.size()-1);
        List<V>valoresEnPreOrdenXDer=valoresRecConPreOrden.subList(posicionEnTurnoEnInOrden,llavesRecConPreOrden.size()-1);
        
        NodoBinario<K,V>hijoDerechoDelNodoActual=reconstruirConPreOrden(llavesEnInOrdenXDer, valoresEnInOrdenXDer,
                llavesEnPreOrdenXDer, valoresEnPreOrdenXDer);
        
        NodoBinario<K,V>nodoActual=new NodoBinario<>(claveDelNodoActual, valorDelNodoActual);
        nodoActual.setHijoIzquierdo(hijoIzquierdoDelNodoActual);
        nodoActual.setHijoDerecho(hijoDerechoDelNodoActual);
        
        return nodoActual;
    }

    @Override
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    @Override
    public boolean isArbolVacio() {
        return NodoBinario.isNodoVacio(this.raiz);
    }

    @Override
    public V buscar(K claveABuscar) {
        if (claveABuscar == null) {
            throw new IllegalArgumentException("clave a  buscar no puede ser nula");
        }
        
        NodoBinario<K, V> nodoActual = this.raiz;
        while (!NodoBinario.isNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            if (claveABuscar.compareTo(claveActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveABuscar.compareTo(claveActual) > 0) {
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                return nodoActual.getValor();
            }
        }
        return null;
    }

    @Override
    public boolean contiene(K claveABuscar) {
        return buscar(claveABuscar) != null;
    }

    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("clave a insertar no puede ser nula");
        }
        if (valorAsociado == null) {
            throw new IllegalArgumentException("valor asociado a la clave no puede ser nulo");
        }
        if (this.isArbolVacio()) {
            this.raiz = new NodoBinario<>(claveAInsertar, valorAsociado);
            return;
        }
        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        NodoBinario<K, V> nodoActual = this.raiz;
        while (!NodoBinario.isNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            if (claveAInsertar.compareTo(claveActual) < 0) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveAInsertar.compareTo(claveActual) > 0) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                nodoActual.setValor(valorAsociado);
                return;
            }

            //y si llego hasta acá es por que ya se que la clave no hay en el árbol
            // entonces debo insertarlo en el arbol en un nuevo nodo enlazado
            //al nodo anterior como su padre de ese nuevo nodoBinario
        }
        NodoBinario<K, V> nuevoNodoBinario = new NodoBinario<>(claveAInsertar, valorAsociado);
        K claveDelNodoAnterior = nodoAnterior.getClave();
        if (claveAInsertar.compareTo(claveDelNodoAnterior) < 0) {
            nodoAnterior.setHijoIzquierdo(nuevoNodoBinario);
        } else {
            nodoAnterior.setHijoDerecho(nuevoNodoBinario);
        }
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new ArrayList<>();

        if (!this.isArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);

            while (!colaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                recorrido.add(nodoActual.getClave());
                 

                if (!nodoActual.isHIjoIzquierdoVacio()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }

                if (!nodoActual.isHijoDerechoVacio()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                  

                }
            }
        }
        return recorrido;
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new ArrayList<>();
        if (!this.isArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            pilaDeNodos.push(this.raiz);

            while (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
                recorrido.add(nodoActual.getClave());

                if (!nodoActual.isHijoDerechoVacio()) {
                    pilaDeNodos.push(nodoActual.getHijoDerecho());
                }

                if (!nodoActual.isHIjoIzquierdoVacio()) {
                    pilaDeNodos.push(nodoActual.getHijoIzquierdo());
                }
            }
        }
        return recorrido;
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new ArrayList<>();
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        this.apilarParaInOrden(pilaDeNodos, this.raiz);

        while (!pilaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            this.apilarParaInOrden(pilaDeNodos, nodoActual.getHijoDerecho());

        }
        return recorrido;
    }

    private void apilarParaInOrden(Stack<NodoBinario<K, V>> pilaDeNodos, NodoBinario<K, V> nodoActual) {
        while (!NodoBinario.isNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();

        }
    }

    //recorridoEnINOrdeV2 es un recorrido recursivo
    public List<K> recorridoEnInOrdenV2() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnInOrdenV2(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnInOrdenV2(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        if (NodoBinario.isNodoVacio(nodoActual)) {
            return;
        }
        recorridoEnInOrdenV2(nodoActual.getHijoIzquierdo(), recorrido);
        recorrido.add(nodoActual.getClave());
        recorridoEnInOrdenV2(nodoActual.getHijoDerecho(), recorrido);
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new ArrayList<>();
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        this.apilarParaPostOrden(pilaDeNodos, this.raiz);
        NodoBinario<K, V> nodoAnterior = raiz;

        while (!pilaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaDeNodos.peek();
            if (!NodoBinario.isNodoVacio(nodoActual.getHijoDerecho()) 
                    && nodoActual.getHijoDerecho().getClave() != nodoAnterior.getClave()) {
                apilarParaPostOrden(pilaDeNodos, nodoActual.getHijoDerecho());
            } else {
                nodoAnterior = pilaDeNodos.pop();
                recorrido.add(nodoActual.getClave());
            }
        }
        return recorrido;
    }

    private void apilarParaPostOrden(Stack<NodoBinario<K, V>> pilaDeNodos, NodoBinario<K, V> nodoActual) {

        while (!NodoBinario.isNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        }
    }

    //recorridoEnPostOrdenV2 es un recorrdio recursivo
    public List<K> recorridoEnPostOrdenV2() {
        List<K> recorrido = new ArrayList<>();
        recorridoParaPostOrden(raiz, recorrido);
        return recorrido;

    }

    private void recorridoParaPostOrden(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        if (NodoBinario.isNodoVacio(nodoActual)) {
            return;
        }
        recorridoParaPostOrden(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoParaPostOrden(nodoActual.getHijoDerecho(), recorrido);
        recorrido.add(nodoActual.getClave());

    }

    @Override
    public int zize() {
        int nroDeNodos = 0;
        if (!this.isArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);

            while (!colaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                nroDeNodos++;

                if (!nodoActual.isHIjoIzquierdoVacio()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.isHijoDerechoVacio()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
            }
        }
        return nroDeNodos;
    }

    public int zizeV2() {
        return zizeV2(this.raiz);

    }

    private int zizeV2(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.isNodoVacio(nodoActual)) {
            return 0;
        }
        int numeroDeNodosPorIzquierda = zizeV2(nodoActual.getHijoIzquierdo());
        int numeroDeNodosPorDerecha = zizeV2(nodoActual.getHijoDerecho());
        return numeroDeNodosPorIzquierda + numeroDeNodosPorDerecha + 1;

    }

    @Override
    public int altura() {
        int altura = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        if (!isArbolVacio()) {
            colaDeNodos.offer(this.raiz);

            while (!colaDeNodos.isEmpty()) {
                int longitud = colaDeNodos.size();

                while (longitud > 0) {
                    NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                    if (!nodoActual.isHIjoIzquierdoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    }
                    if (!nodoActual.isHijoDerechoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoDerecho());
                        longitud--;
                    }

                }
                altura++;
            }
        }
        return altura;
    }

    public int alturaV2() {
        return alturaV2(this.raiz);
    }

    public int alturaV2(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.isNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaPorIzquierda = alturaV2(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = alturaV2(nodoActual.getHijoDerecho());
        return alturaPorIzquierda > alturaPorDerecha ? alturaPorIzquierda + 1 : alturaPorDerecha + 1;

    }

    @Override

    public V eliminar(K claveAEliminar) throws ClaveNoExisteException {
        if (claveAEliminar == null) {
            throw new IllegalArgumentException("clave a eliminar no puede ser nula");
        }

        V valorAEliminar = buscar(claveAEliminar);
        if (valorAEliminar == null) {
            throw new ClaveNoExisteException();
            
        }

        this.raiz=eliminar(this.raiz, claveAEliminar);
        return valorAEliminar;

    }

    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {
        K claveDeNodoActual = nodoActual.getClave();

        if (claveAEliminar.compareTo(claveDeNodoActual) < 0) {
            NodoBinario<K, V> supuestoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoHijoIzquierdo);
            return nodoActual;
        }
        if (claveAEliminar.compareTo(claveDeNodoActual) > 0) {
            NodoBinario<K, V> supuestoHijoDerecho = eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(supuestoHijoDerecho);
            return nodoActual;
        }
        //caso1
        if (nodoActual.isHoja()) {
            return NodoBinario.nodoVacio();
        }
        //caso2
        if (!nodoActual.isHIjoIzquierdoVacio() && nodoActual.isHijoDerechoVacio()) {
            return nodoActual.getHijoIzquierdo();
        }
        //caso2b
        if (nodoActual.isHIjoIzquierdoVacio() && !nodoActual.isHijoDerechoVacio()) {
            return nodoActual.getHijoDerecho();
        }
        
        //caso3       
        NodoBinario<K, V> nodoDondeEstaElSucesor = obtenerSucesor(nodoActual.getHijoDerecho());
      NodoBinario<K, V> supuestoNodoHijoDerecho= eliminar(nodoActual.getHijoDerecho(), nodoDondeEstaElSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoNodoHijoDerecho);
     
        nodoActual.setClave(nodoDondeEstaElSucesor.getClave());
        nodoActual.setValor(nodoDondeEstaElSucesor.getValor());
        
         return nodoDondeEstaElSucesor;
    }

    protected NodoBinario<K, V> obtenerSucesor(NodoBinario<K, V> nodoEnTurno) {
        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        while (!NodoBinario.isNodoVacio(nodoEnTurno)) {
            nodoAnterior = nodoEnTurno;
            nodoEnTurno = nodoEnTurno.getHijoIzquierdo();
        }
        return nodoAnterior;
    }

    @Override
    public int nivel() {
        int nivel = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        if (!isArbolVacio()) {
            colaDeNodos.offer(this.raiz);

            while (!colaDeNodos.isEmpty()) {
                System.out.println(Arrays.toString(colaDeNodos.toArray()));
                int longitud = colaDeNodos.size();

                while (longitud > 0) {
                    NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                    if (!nodoActual.isHIjoIzquierdoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    }
                    if (!nodoActual.isHijoDerechoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoDerecho());
                    }
                    longitud--;
                }
               
                nivel++;
            }
        }
        return nivel - 1;
    }

    @Override
    public String toString() {
        return "nivel: "+nivel();
    }



    @Override
    public int nodosNoVacios(int n) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
