/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author ronron
 */
public class Metodos {
     final String ruta="D:\\Diccionario\\palabras.txt";

    public void guardarUnArchivo(String clave, String valor) {
        try {
            FileWriter fw = new FileWriter(ruta, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(clave + ":" + valor);
            pw.close();
        } catch (Exception e) {
        }
    }

    public ArrayList<String> leerArchivo() {
        ArrayList<String> claveValor = new ArrayList<>();
        try {
            File file = new File(ruta);

            try ( Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    claveValor.add(data);
                    //agreagar al array de string
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            //return  array
            return claveValor;
        }
        //return array string
        return claveValor;
    }

    public void borrarLinea(String clave) {
        try {
            File file = new File("D:\\Diccionario\\palabras.txt");
            Path path = file.toPath();
            List<String> lineasViejas = Files.readAllLines(path);
            List<String> nuevasLineas=new ArrayList<>();
            
            for (int i=0;i<lineasViejas.size();i++){
                String linea=lineasViejas.get(i);
                String[] segments=linea.split(":");
                if(clave.compareTo(segments[0].toLowerCase())!=0){
                    nuevasLineas.add(linea.trim());
                } 
            }
            
            String joined = String.join("\n", nuevasLineas);
            joined=joined+"\n";

            FileWriter writerObj = new FileWriter(file);
            writerObj.write(joined);           
            writerObj.close();
        } catch (IOException ex) {
            Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
