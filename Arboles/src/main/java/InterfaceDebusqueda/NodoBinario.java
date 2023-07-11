/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package InterfaceDebusqueda;

public class NodoBinario<K,V> {
    
    private NodoBinario<K,V> hijoIzquierdo;
    private K clave;
    private V valor;
    private NodoBinario<K,V>hijoDerecho;
    
    public NodoBinario(){
        
    }
    public NodoBinario(K clave,V valor){
        this.clave=clave;
        this.valor=valor;
    }
    public NodoBinario<K,V>getHijoIzquierdo(){
        return this.hijoIzquierdo;
    }
    public void setHijoIzquierdo(NodoBinario<K,V>hijoIzquierdo){
        this.hijoIzquierdo=hijoIzquierdo;
    }
    public K getClave(){
        return clave;
    }
    public void setClave(K clave){
        this.clave=clave;
    }
    public V getValor(){
        return valor;
    }
    public void setValor(V valor){
        this.valor=valor;
    }
    public NodoBinario<K,V>getHijoDerecho(){
        return this.hijoDerecho;
    }
    public void setHijoDerecho(NodoBinario<K,V>hijoDerrecho){
        this.hijoDerecho=hijoDerrecho;
    }
    public static NodoBinario nodoVacio(){
        return null;
    }
    public static boolean isNodoVacio(NodoBinario elNodo){
        return elNodo==NodoBinario.nodoVacio();
    }
    public boolean isHIjoIzquierdoVacio(){
        return NodoBinario.isNodoVacio(this.getHijoIzquierdo());
    }
    public boolean isHijoDerechoVacio(){
        return NodoBinario.isNodoVacio(this.getHijoDerecho());
    }
    public boolean isHoja(){
        return isHIjoIzquierdoVacio()&&isHijoDerechoVacio();
    }
    @Override
        public String toString() {
        return "nodo: "+getClave()+" "+getValor();
    }
    
}


