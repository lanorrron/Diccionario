/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arboles;

import Exception.ClaveNoExisteException;
import InterfaceDebusqueda.NodoMVias;
import Exception.OrdenInvalidoException;
import java.util.Stack;

/**
 *
 * @author ronron
 */
public class ArbolB<K extends Comparable<K>, V> extends ArbolMViasBusqueda<K, V> {

    private int nroMaxDeDatos;
    private int nroMinDeHijos;
    private int nroMinDeDatos;

    public ArbolB() {
        super.orden = 3;
        this.nroMaxDeDatos = 2;
        this.nroMinDeDatos = 1;
        this.nroMinDeHijos = 2;
    }

    public ArbolB(int orden) throws OrdenInvalidoException {
        super(orden);
        this.nroMaxDeDatos = super.orden - 1;
        this.nroMinDeDatos = nroMaxDeDatos / 2;
        this.nroMinDeHijos = nroMinDeDatos + 1;

    }

    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("clave a insertar no puede ser nula");
        }
        if (valorAsociado == null) {
            throw new IllegalArgumentException("valor a insertar no puede ser nulo");
        }
        if (this.isArbolVacio()) {
            this.raiz = new NodoMVias<>(orden + 1, claveAInsertar, valorAsociado);
            return;
        }

        Stack<NodoMVias<K, V>> pilaDeAncestros = new Stack<>();

        NodoMVias<K, V> nodoActual = this.raiz;

        do {
            int posicionDeLaClaveAInsertar = super.obtenerPosicionDeClave(claveAInsertar, nodoActual);

            if (posicionDeLaClaveAInsertar != ArbolMViasBusqueda.POSICION_NO_VALIDA) {
                nodoActual.setvalor(posicionDeLaClaveAInsertar, valorAsociado);
                nodoActual = NodoMVias.nodoVacio();

            } else if (nodoActual.esHoja()) {
                insertarClaveYvalorEnNodoOrdenado(nodoActual, claveAInsertar, valorAsociado);
                if (nodoActual.nroDeClavesNoVacias() > this.nroMaxDeDatos + 1) {//editadole puse mas 1al numero maximo de datos
                    this.dividir(nodoActual, pilaDeAncestros);  //tarea pa la casa
                }
                nodoActual = NodoMVias.nodoVacio();
            } else {
                int posicionPorDondeBajar = super.obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
                pilaDeAncestros.push(nodoActual);
                nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
            }

        } while (!NodoMVias.esNodoVacio(nodoActual));

    }

    private void dividir(NodoMVias<K, V> nodoActual, Stack<NodoMVias<K, V>> pilaDeAncestros) {

        if (nodoActual.nroDeClavesNoVacias() <= nroMinDeDatos) {
            return;
        }
        while (nodoActual.nroDeClavesNoVacias() > nroMaxDeDatos) {
            K claveAsubir = nodoActual.getClave(nroMinDeDatos);
            V valorAsociadoASubir = nodoActual.getValor(nroMinDeDatos);
            if (nodoActual == this.raiz) {
                NodoMVias<K, V> nuevaRaiz = new NodoMVias<K, V>(orden + 1, claveAsubir, valorAsociadoASubir);
                NodoMVias<K, V> nuevoHijo = new NodoMVias<K, V>(orden + 1);
                nodoHijoDeDivision(nodoActual, nuevoHijo);
                nuevaRaiz.setHijo(0, nodoActual);
                nuevaRaiz.setHijo(1, nuevoHijo);
                this.raiz = nuevaRaiz;
                nodoActual = this.raiz;
            } else {
                // corregir este caso
                NodoMVias<K, V> nodoPadre = pilaDeAncestros.peek();
                insertarClaveYvalorEnNodoOrdenado(nodoPadre, claveAsubir, valorAsociadoASubir);
                NodoMVias<K, V> nuevoHijo = new NodoMVias<>(orden + 1);
                nodoHijoDeDivision(nodoActual, nuevoHijo);
                int posicionDelDatoSubido = posicionDelDato(nodoPadre, claveAsubir);

                for (int i = posicionDelDatoSubido + 1; i < nodoPadre.nroDeClavesNoVacias(); i++) {
                    NodoMVias<K, V> guardarHijo = nodoPadre.getHijo(i);
                    nodoPadre.setHijo(i, nuevoHijo);
                    nuevoHijo = guardarHijo;
                }
                nodoPadre.setHijo(nodoPadre.nroDeClavesNoVacias(), nuevoHijo);
                nodoActual = pilaDeAncestros.pop();

            }

        }
    }

    private void nodoHijoDeDivision(NodoMVias<K, V> nodoADividir, NodoMVias<K, V> nuevoHijo) {
        int j = 0;
        int cantidadDeDatos = nodoADividir.nroDeClavesNoVacias();
        for (int i = nroMinDeDatos + 1; i < cantidadDeDatos; i++) {
            nuevoHijo.setClave(j, nodoADividir.getClave(i));
            nuevoHijo.setvalor(j, nodoADividir.getValor(i));
            nuevoHijo.setHijo(j, nodoADividir.getHijo(i));
            
            nodoADividir.setClave(i, (K) NodoMVias.datoVacio());
            nodoADividir.setvalor(i,(V) NodoMVias.datoVacio());
            nodoADividir.setHijo(i, NodoMVias.nodoVacio());
            j++;
        }
        nuevoHijo.setHijo(j, nodoADividir.getHijo(cantidadDeDatos));
        nodoADividir.setHijo(cantidadDeDatos, NodoMVias.nodoVacio());
        nodoADividir.setClave(nroMinDeDatos, (K) NodoMVias.datoVacio());
    }

    private int posicionDelDato(NodoMVias<K, V> nodoActual, K clave) { //trabajar aqui  este punto es que esta dando problemas
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            if (clave.compareTo(nodoActual.getClave(i)) == 0) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public V eliminar(K claveAEliminar) throws NullPointerException, ClaveNoExisteException {
        if (claveAEliminar == null) {
            throw new NullPointerException("clave a eliminar es nula");
        }
        Stack<NodoMVias<K, V>> pilaDeAncestros = new Stack<>();
        NodoMVias<K, V> nodoActual = this.obtenerNodoDeLaClave(claveAEliminar, pilaDeAncestros);
         
        if (NodoMVias.esNodoVacio(nodoActual)) {
            throw new ClaveNoExisteException();
        }
        int posicionDeLaClaveAEliminar = super.obtenerPosicionPorDondeBajar(nodoActual,claveAEliminar)-1;
        V valorARetornar = nodoActual.getValor(posicionDeLaClaveAEliminar);
        
        if (nodoActual.esHoja()) {
            super.eliminarClaveValorDePosicion(nodoActual, posicionDeLaClaveAEliminar);
           
            if (nodoActual.nroDeClavesNoVacias() < nroMinDeDatos) {
               
                if (pilaDeAncestros.isEmpty()) {          // nodoActual es igual a la raiz
                  
                    if (nodoActual.nroDeClavesNoVacias()==0) {
                        super.vaciar();
                    }
                } else {
                    NodoMVias<K,V> nodoPadre=pilaDeAncestros.peek();
                    prestarOFusionar(pilaDeAncestros, nodoActual, obtenerPosicionPorDondeBajar(nodoPadre, claveAEliminar));
                }
            }
           
        } else {
            pilaDeAncestros.push(nodoActual);
            NodoMVias<K, V> nodoDelPredecesor = this.obtenerNodoPredecesorInOrden(pilaDeAncestros, nodoActual.getHijo(posicionDeLaClaveAEliminar), claveAEliminar);
            int posicionDeClavePredecesora = nodoDelPredecesor.nroDeClavesNoVacias() -1;
            K clavePredecesora = nodoDelPredecesor.getClave(posicionDeClavePredecesora);
            V valorAsociadoDelPredecesor = nodoDelPredecesor.getValor(posicionDeClavePredecesora);
            
            super.eliminarClaveValorDePosicion(nodoDelPredecesor, posicionDeClavePredecesora);
            nodoActual.setClave(posicionDeLaClaveAEliminar, clavePredecesora);
            nodoActual.setvalor(posicionDeLaClaveAEliminar, valorAsociadoDelPredecesor);
            if (nodoDelPredecesor.nroDeClavesNoVacias() < nroMinDeDatos) {

                prestarOFusionar(pilaDeAncestros, nodoActual, posicionDeClavePredecesora);
            }
        }
        return valorARetornar;
    }
     public NodoMVias<K,V> obtenerNodoDeLaClave(K claveAEliminar,Stack<NodoMVias<K,V>>pilaDeAncestros){
               NodoMVias<K,V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            int posicion = posicionDelDato(nodoActual, claveAEliminar);
            if (posicion != -1) {
                return nodoActual;
            }
            pilaDeAncestros.push(nodoActual);
            int posicionParaBajar = super.obtenerPosicionPorDondeBajar(nodoActual, claveAEliminar);
            nodoActual = nodoActual.getHijo(posicionParaBajar);
        }
        return nodoActual;
    }
    
    private NodoMVias<K,V> obtenerNodoPredecesorInOrden(Stack<NodoMVias<K,V>> pilaDeAncestros, NodoMVias<K,V> nodoActual, K clave) {
        NodoMVias<K,V> nodoAnterior = nodoActual;
        int posicionDelDatoEnElNodo = posicionDelDato(nodoActual, clave);
        pilaDeAncestros.push(nodoActual);
        nodoActual = nodoActual.getHijo(posicionDelDatoEnElNodo);
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            pilaDeAncestros.push(nodoActual);
            nodoActual = nodoActual.getHijo(nodoActual.nroDeClavesNoVacias());
        }
        return nodoAnterior;
    }
    private void prestarOFusionar(Stack<NodoMVias<K,V>> pilaDeAncestros, NodoMVias<K,V> nodoActual, int posicionNodoActual) {
        NodoMVias<K,V> nodoPadre = pilaDeAncestros.peek();
        int posicionDeHermano = posicionDeHermanoPrestador(nodoPadre, nodoActual, posicionNodoActual);
        if (posicionDeHermano == -1) {//no tiene quien le preste
            fusionar(pilaDeAncestros, nodoActual, posicionNodoActual);
            return;
        }
        NodoMVias<K,V> hermano = nodoPadre.getHijo(posicionDeHermano);
        if (posicionDeHermano > posicionNodoActual) {//hermano adelante
            K clavePadre = nodoPadre.getClave(posicionNodoActual);
            V ValorAsociadoDeClavePadre = nodoPadre.getValor(posicionNodoActual);
            nodoActual.setClave(nodoActual.nroDeClavesNoVacias(), clavePadre);
            nodoActual.setvalor(nodoActual.nroDeClavesNoVacias(),ValorAsociadoDeClavePadre);
          
            nodoPadre.setClave(posicionNodoActual, hermano.getClave(0));
            nodoPadre.setvalor(nodoActual.nroDeClavesNoVacias(), hermano.getValor(0));
            
            for (int i = 0; i < hermano.nroDeClavesNoVacias()- 1; i++) {
                hermano.setClave(i, hermano.getClave(i + 1));
            }
            hermano.setClave(hermano.nroDeClavesNoVacias()-1, (K) NodoMVias.datoVacio());
            return;
        }
        //hermano atras
        K clavePadre = nodoPadre.getClave(posicionNodoActual - 1);
        V valorAsociadoALaClavePadre=(V) nodoPadre.getClave(posicionNodoActual-1);
        nodoActual.setClave(nodoActual.nroDeClavesNoVacias(),clavePadre);
        nodoActual.setvalor(nodoActual.nroDeClavesNoVacias(), valorAsociadoALaClavePadre);
        
        nodoPadre.setClave(posicionNodoActual - 1, hermano.getClave(hermano.nroDeClavesNoVacias()-1));
        nodoPadre.setvalor(posicionNodoActual-1, (V) hermano.getValor(hermano.nroDeClavesNoVacias()-1));
        
        hermano.setClave(hermano.nroDeClavesNoVacias()-1, (K) NodoMVias.datoVacio());
        hermano.setvalor(hermano.nroDeClavesNoVacias()-1,(V) NodoMVias.datoVacio());
    }

    private void fusionar(Stack<NodoMVias<K,V>> pilaDeAncestros, NodoMVias<K,V> nodoActual, int posicionNodoActual) {
        while (nodoActual.nroDeClavesNoVacias()< nroMinDeDatos ||
                (!nodoActual.esHoja() && nodoActual.nroDeClavesNoVacias()< nroMinDeHijos)) {
            NodoMVias<K,V> nodoPadre = pilaDeAncestros.peek();
            NodoMVias<K,V> hermano;
            K clavePadre = (K) NodoMVias.datoVacio();
            V valorAsociadoALaClavePadre=(V)NodoMVias.datoVacio();
                    
            if (posicionNodoActual == nodoPadre.nroDeClavesNoVacias()) {//cuando es el nodo al final
                clavePadre = nodoPadre.getClave(posicionNodoActual - 1);
                hermano = nodoPadre.getHijo(posicionNodoActual - 1);
                hermano.setClave(hermano.nroDeClavesNoVacias(), clavePadre);
                hermano.setvalor(hermano.nroDeClavesNoVacias(), valorAsociadoALaClavePadre);
                
                
                for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                    hermano.setHijo(hermano.nroDeClavesNoVacias(), nodoActual.getHijo(i));
                    hermano.setClave(hermano.nroDeClavesNoVacias(), nodoActual.getClave(i));
                    hermano.setvalor(hermano.nroDeClavesNoVacias(), nodoActual.getValor(i));
                }
                hermano.setHijo(hermano.nroDeClavesNoVacias(), nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
                nodoPadre.setHijo(nodoPadre.nroDeClavesNoVacias(), NodoMVias.nodoVacio());
                
                nodoPadre.setClave(nodoPadre.nroDeClavesNoVacias()- 1, (K) NodoMVias.datoVacio());
                nodoPadre.setvalor(nodoPadre.nroDeClavesNoVacias()-1,(V)NodoMVias.datoVacio());

            } else {
                clavePadre = nodoPadre.getClave(posicionNodoActual);
                valorAsociadoALaClavePadre=nodoPadre.getValor(posicionNodoActual);
                nodoActual.setClave(nodoActual.nroDeClavesNoVacias(), clavePadre);
                nodoActual.setvalor(nodoActual.nroDeClavesNoVacias(), valorAsociadoALaClavePadre);
                
                hermano = nodoPadre.getHijo(posicionNodoActual + 1);
                int cantidadDatosHermano=hermano.nroDeClavesNoVacias();
                for (int i = 0; i < cantidadDatosHermano; i++) {
                    nodoActual.setHijo(nodoActual.nroDeClavesNoVacias(), hermano.getHijo(i));
                    nodoActual.setClave(nodoActual.nroDeClavesNoVacias(), hermano.getClave(i));
                    nodoActual.setvalor(nodoActual.nroDeClavesNoVacias(), hermano.getValor(i));
                    
                    hermano.setClave(i,(K)NodoMVias.datoVacio());
                    hermano.setvalor(i,(V)NodoMVias.datoVacio());
                }
                nodoActual.setHijo(nodoActual.nroDeClavesNoVacias(), hermano.getHijo(cantidadDatosHermano));
                for (int i = posicionNodoActual; i < nodoPadre.nroDeClavesNoVacias(); i++) {
                    if(i!=nodoPadre.nroDeClavesNoVacias()-1) {
                        nodoPadre.setClave(i, nodoPadre.getClave(i + 1));
                        nodoPadre.setvalor(i, nodoPadre.getValor(i + 1));
                    }
                    if (i != posicionNodoActual) {
                        nodoPadre.setHijo(i, nodoPadre.getHijo(i + 1));
                    }
                }
                nodoPadre.setHijo(nodoPadre.nroDeClavesNoVacias(),NodoMVias.nodoVacio());
                nodoPadre.setClave(nodoPadre.nroDeClavesNoVacias()- 1, (K) NodoMVias.datoVacio());
                nodoPadre.setvalor(nodoPadre.nroDeClavesNoVacias()- 1, (V) NodoMVias.datoVacio());
            }

            if(!nodoActual.estanDatosLlenos()&&!nodoPadre.estanDatosLlenos()&&nodoPadre==raiz){
                this.raiz=hermano;
                return;
            }
            if(!hermano.estanDatosLlenos()&&!nodoPadre.estanDatosLlenos()&&nodoPadre==raiz){
                this.raiz=nodoActual;
                return;
            }
            K guardarDatoParaNuevaBajada=nodoActual.getClave(0);
            nodoActual = pilaDeAncestros.pop();
            if(!pilaDeAncestros.isEmpty()) {
                posicionNodoActual = super.obtenerPosicionPorDondeBajar(pilaDeAncestros.peek(), guardarDatoParaNuevaBajada);
            }


        }
    }
    
    
       private int posicionDeHermanoPrestador(NodoMVias<K,V> nodoPadre, NodoMVias<K,V> nodoActual, int posicionNodoActual) {
        if (posicionNodoActual != nodoPadre.nroDeClavesNoVacias()) {//nodo  NO final
            if (nodoPadre.getHijo(posicionNodoActual + 1).nroDeClavesNoVacias()> nroMinDeDatos) {
                return posicionNodoActual + 1;
            }
        }
        if (posicionNodoActual != 0) {//nodo  NO inicial
            if (nodoPadre.getHijo(posicionNodoActual - 1).nroDeClavesNoVacias()> nroMinDeDatos) {
                return posicionNodoActual - 1;
            }
        }
        return -1;
    }


}
