/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arboles;

import Arboles.ArbolMViasBusqueda;
import Arboles.AVL;
import Arboles.AVL;
import Arboles.ArbolB;
import Arboles.ArbolBinarioBusqueda;
import Exception.ClaveNoExisteException;
import Exception.ClaveNoExisteException;
import InterfaceDebusqueda.IArbolDeBusqueda;
import InterfaceDebusqueda.IArbolDeBusqueda;
import Exception.OrdenInvalidoException;
import InterfaceDebusqueda.IArbolDeBusqueda;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import javax.swing.text.html.HTML;

/**
 *
 * @author ronron
 */
public class TestArbol {

    public static void main(String argumentos[]) throws ClaveNoExisteException, OrdenInvalidoException {
        IArbolDeBusqueda<Integer, String> arbolPueba;

        /*Scanner entrada = new Scanner(System.in);
        System.out.print("Elija un tipo de arbol (ABB,AVL");
        String tipoArbol = entrada.next();
        tipoArbol = tipoArbol.toUpperCase();*/
        arbolPueba=new ArbolMViasBusqueda<>(3);

      /*  switch (tipoArbol) {
            case "ABB":

                break;
            case "AVL":
           arbolPueba=new ArbolB<>(3);
                break;

            default:
                System.out.print("tipo dearbol inválido,eligiendo arbol binario de busqueda\n");
                arbolPueba=new AVL<>();
    
        }*/

        arbolPueba.insertar(20, "rojo");
        arbolPueba.insertar(10, "verde");
        arbolPueba.insertar(9, "chejche");
        arbolPueba.insertar(18, "morado");
        arbolPueba.insertar(60, "rosado");
        arbolPueba.insertar(70, "griss");
        arbolPueba.insertar(85, "lechuga");
       arbolPueba.insertar(25, "cacky");
       arbolPueba.insertar(7, "sonsillo");

        //System.out.println(arbolPueba.buscar(35));
        arbolPueba.eliminar(20);
       System.out.println(arbolPueba);

        System.out.println("recorridio en inOrden" + arbolPueba.recorridoPorNiveles());
       System.out.println("recorridio en inOrden" + arbolPueba.recorridoEnInOrden());

        System.out.println("recorridio en preOrden" + arbolPueba.recorridoEnPreOrden());
      System.out.println("recorridio por niveles" + arbolPueba.recorridoPorNiveles());
       System.out.println("recorrido en posorden" + arbolPueba.recorridoEnPostOrden());
               System.out.println("sonsillo");

       System.out.println("hijos no vacios del nivel " + arbolPueba.nodosNoVacios(0));
    }

}
