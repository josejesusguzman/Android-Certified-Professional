package com.example.josejesus.acppersistenciaalmacenamiento.BD;

/**
 * Created by josejesus on 6/21/2016.
 */
public class Persona {

    private Integer id;
    private String nombre;
    private String apellido;
    private Integer edad;

    public Persona() {
    }

    public Persona(String nombre, String apellido, Integer edad) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "nombre = " + nombre + "\n" +
                "apellido = " + apellido + "\n" +
                "edad = " + edad;
    }
}
