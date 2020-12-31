package ficherosProperties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;

import java.util.Properties;

public class Propertyes {
	
	public static Properties config = new Properties();
    public static InputStream configInput = null;
    public static OutputStream configOutput = null;
    public void loadConfig(){
        try{
            configInput = new FileInputStream("config.properties");
            config.load(configInput);
            System.out.println(config.getProperty("first_start"));
            System.out.println(config.getProperty("look_and_feel"));
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error cargando configuración\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void setPropertyValue(String property, String value){
        try{
            configOutput = new FileOutputStream("data/config.properties");
            config.setProperty(property, value);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error guardando configuración\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
