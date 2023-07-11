/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arboles;

import Exception.ClaveNoExisteException;
import InterfaceDebusqueda.NodoBinario;
import java.util.List;

/**
 *
 * @author ronron
 * @param <K>
 * @param <V>
 */
public class AVL<K extends Comparable<K>, V> extends ArbolBinarioBusqueda<K, V> {

    private static final int LIMITE_MAX = 1;      
    
    public AVL(){
    }
    
    public AVL(boolean isConPreOrden, List<K> llavesRecInOrden, List<V> valoresRecInOrden, List<K> llavesRecNoInOrden, List<V> ValoresRecNoInOrden) {
        super(isConPreOrden, llavesRecInOrden, valoresRecInOrden, llavesRecNoInOrden, ValoresRecNoInOrden);
    }
    
    

    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {                   
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("clave a insertar  no puede ser nula");
        }

        if (valorAsociado == null) {
            throw new IllegalArgumentException("valor asociado no puede ser nulo");
        }
       this.raiz= insertar(this.raiz,claveAInsertar, valorAsociado);
    }
    private NodoBinario<K,V>insertar(NodoBinario<K,V> nodoActual,K claveAInsertar,V valorAsociado){
        if(NodoBinario.isNodoVacio(nodoActual)){
            NodoBinario <K,V>nuevoNodo=new NodoBinario<>(claveAInsertar, valorAsociado);
            return nuevoNodo;
        }
        K claveDelNodoActual=nodoActual.getClave();
        if(claveAInsertar.compareTo(claveDelNodoActual)<0){
            NodoBinario<K,V>supuestoNodoHijoIzquierdo=insertar(nodoActual.getHijoIzquierdo(), claveAInsertar,valorAsociado);
            nodoActual.setHijoIzquierdo(supuestoNodoHijoIzquierdo);
            return balancear(nodoActual);
        }
        if(claveAInsertar.compareTo(claveDelNodoActual)>0){
            NodoBinario<K,V> supuestoNodoHijoDerecho=insertar(nodoActual.getHijoDerecho(), claveAInsertar, valorAsociado);
            nodoActual.setHijoDerecho(supuestoNodoHijoDerecho);
            return balancear(nodoActual);
        }
        nodoActual.setValor(valorAsociado);
        return nodoActual;
    }
    
    private NodoBinario<K,V> balancear(NodoBinario<K,V>nodoActual){
        int alturaPorIzquierda=alturaV2(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha=alturaV2(nodoActual.getHijoDerecho());
        int diferenciasDeAlturas=alturaPorIzquierda-alturaPorDerecha;
        
        if(diferenciasDeAlturas>LIMITE_MAX){// 
            //Rama izquierda mas larga,entonces hay que rotar a la derecha
            //Averiguemos si es rotacion doble o simple
            NodoBinario<K,V>hijoIzqdelNodoActual=nodoActual.getHijoIzquierdo();
            alturaPorIzquierda=alturaV2(hijoIzqdelNodoActual.getHijoIzquierdo());
            alturaPorDerecha=alturaV2(hijoIzqdelNodoActual.getHijoDerecho());
            if(alturaPorDerecha>alturaPorIzquierda){
                return rotacionDobleALaDerecha(nodoActual);
            }
            return rotacionSimpleALaDerecha(nodoActual);
            
        }else{
            diferenciasDeAlturas=alturaPorDerecha-alturaPorIzquierda;
            if(diferenciasDeAlturas>LIMITE_MAX){
                //rama derecha mas larga hay que hacer una rotacion a la izquierda
                //averiguemos si la rotacion es doble o simple
                NodoBinario<K,V> hijoDerDelNodoActual=nodoActual.getHijoDerecho();
                alturaPorIzquierda=alturaV2(hijoDerDelNodoActual.getHijoIzquierdo());
                alturaPorDerecha=alturaV2(hijoDerDelNodoActual.getHijoDerecho());
                if(alturaPorIzquierda>alturaPorDerecha){
                    return rotacionDobleALaIzquierda(nodoActual);
                }
                return rotacionSimpleALaIzquierda(nodoActual);
            }
            
        }
        return nodoActual;
    }
    
    //aqui esta e problema
    private NodoBinario<K,V>rotacionSimpleALaDerecha(NodoBinario<K,V> nodoActual){
        NodoBinario<K,V> nodoQueRota=nodoActual.getHijoIzquierdo();
      //editado
        //if(!nodoQueRota.isHijoDerechoVacio()){
        nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDerecho());
        //}
        nodoQueRota.setHijoDerecho(nodoActual);
        return nodoQueRota;
    }
    
    private NodoBinario<K,V>rotacionDobleALaDerecha(NodoBinario<K,V>NodoActual){
        NodoBinario<K,V> primerNodoQueRota=rotacionSimpleALaIzquierda(NodoActual.getHijoIzquierdo());
        NodoActual.setHijoIzquierdo(primerNodoQueRota);
        return rotacionSimpleALaDerecha(NodoActual);
    }
    
    private NodoBinario<K,V> rotacionSimpleALaIzquierda(NodoBinario<K,V>nodoActual){
        NodoBinario<K,V>nodoQueRota=nodoActual.getHijoDerecho();
       //editado
        //if(!nodoQueRota.isHIjoIzquierdoVacio()){
        nodoActual.setHijoDerecho(nodoQueRota.getHijoIzquierdo());
        //}
        nodoQueRota.setHijoIzquierdo(nodoActual);
        return nodoQueRota;
    }
    
    private NodoBinario<K,V> rotacionDobleALaIzquierda(NodoBinario<K,V> nodoActual){
        NodoBinario<K,V> primerNodoQueRota=rotacionSimpleALaDerecha(nodoActual.getHijoDerecho());
        nodoActual.setHijoDerecho(primerNodoQueRota);
        return rotacionSimpleALaIzquierda(nodoActual);
        
    }
    

    @Override

    public V eliminar(K claveAEliminar) throws ClaveNoExisteException {
                V valorAEliminar = buscar(claveAEliminar);

        if (claveAEliminar == null) {
            throw new IllegalArgumentException("clave a eliminar no puede ser nula");
        }

        if (valorAEliminar == null) {
            throw new IllegalArgumentException("valor no puede ser nulo");
        }
   eliminar(this.raiz, claveAEliminar);
        return valorAEliminar;

    }
        private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {
        K claveDeNodoActual = nodoActual.getClave();
        
                if (claveAEliminar.compareTo(claveDeNodoActual) < 0) {
            NodoBinario<K, V> supuestoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoHijoIzquierdo);
            return balancear(nodoActual);
        }
        if (claveAEliminar.compareTo(claveDeNodoActual) > 0) {
            NodoBinario<K, V> supuestoHijoDerecho = eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(supuestoHijoDerecho);
            return balancear(nodoActual);
        }
            if (nodoActual.isHoja()) {
            return NodoBinario.nodoVacio();
        }
        //caso2
        if (!nodoActual.isHIjoIzquierdoVacio() && nodoActual.isHijoDerechoVacio()) {
            return balancear(nodoActual.getHijoIzquierdo());
        }
        //caso2b
        if (nodoActual.isHIjoIzquierdoVacio() && !nodoActual.isHijoDerechoVacio()) {
            return balancear(nodoActual.getHijoDerecho());
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
}
