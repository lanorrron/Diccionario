/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exception;

/**
 *
 * @author ronron
 */
public class OrdenInvalidoException  extends Exception{
        public OrdenInvalidoException(){
        super(" orden invalida");
    }
            public OrdenInvalidoException(String msg){
        super(msg);
    }
    
}
