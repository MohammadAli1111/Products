/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Mohammad
 */
public class Product {
   private int id;
   private String name;
   private String type;
   private byte []photo;
   private float price;

    public Product() {
    }

    public Product(String name, String type, byte[] photo, float price) {
        this.name = name;
        this.type = type;
        this.photo = photo;
        this.price = price;
    }

    public Product(int id, String name, String type, byte[] photo, float price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.photo = photo;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    

   
}
