/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exception;

/**
 *
 * @author ronron
 */
public class ClaveNoExisteException extends Exception{
    
    
    public ClaveNoExisteException(){
    super("clave no existe");
    }
    public ClaveNoExisteException(String msg){
        super(msg);
    }

}
