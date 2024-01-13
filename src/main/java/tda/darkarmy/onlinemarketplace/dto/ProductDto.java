package tda.darkarmy.onlinemarketplace.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductDto {
    private String name;
    private String manufacturingDate;
    private String description;
    private double price;
    private String brand;
    private String category;
    private int stock;
    private String color;
    private String returnPolicy;
    private MultipartFile image;

    public ProductDto(String name, String manufacturingDate, String description, double price, String brand, String category, int stock, String color, String returnPolicy, MultipartFile image) {
        this.name = name;
        this.manufacturingDate = manufacturingDate;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.category = category;
        this.stock = stock;
        this.color = color;
        this.returnPolicy = returnPolicy;
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getReturnPolicy() {
        return returnPolicy;
    }

    public void setReturnPolicy(String returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(String manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "name='" + name + '\'' +
                ", manufacturingDate='" + manufacturingDate + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", type='" + category + '\'' +
                ", stock=" + stock +
                ", color='" + color + '\'' +
                ", returnPolicy='" + returnPolicy + '\'' +
                ", image=" + image +
                '}';
    }
}
