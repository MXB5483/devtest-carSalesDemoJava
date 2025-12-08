
package com.broadcom.demo.vehicles.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Vehicle {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String brand;
private String model;
@Column(name = "manufacture_year")
private int year;
private BigDecimal price;
private boolean sold;
private String imageUrl;
public Vehicle() {}
public Vehicle(Long id,String brand,String model,int year,BigDecimal price,boolean sold,String imageUrl){
this.id=id;this.brand=brand;this.model=model;
this.year=year;this.price=price;this.sold=sold;this.imageUrl=imageUrl;
}
public Long getId(){return id;} public void setId(Long id){this.id=id;}
public String getBrand(){return brand;} public void setBrand(String brand){this.brand=brand;}
public String getModel(){return model;} public void setModel(String model){this.model=model;}
public int getYear(){return year;} public void setYear(int year){this.year=year;}
public java.math.BigDecimal getPrice(){return price;} public void setPrice(java.math.BigDecimal price){this.price=price;}
public boolean isSold(){return sold;} public void setSold(boolean sold){this.sold=sold;}
public String getImageUrl(){return imageUrl;} public void setImageUrl(String imageUrl){this.imageUrl=imageUrl;}
}
