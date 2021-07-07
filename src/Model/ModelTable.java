/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.scene.image.ImageView;

/**
 *
 * @author Mohammad
 */
public class ModelTable {
   private int id;
   private String name;
   private String type;
   private ImageView imageView;
   private float price;

    public ModelTable() {
    }

    public ModelTable(String name, String type, ImageView imageView, float price) {
        this.name = name;
        this.type = type;
        this.imageView = imageView;
        this.price = price;
    }

    public ModelTable(int id, String name, String type, ImageView imageView, float price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.imageView = imageView;
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

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
   
   
}
