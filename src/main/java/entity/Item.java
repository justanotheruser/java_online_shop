package entity;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "items", schema = "public", catalog = "online_shop")
@NamedQuery(name = "Items.byCategory", query = "SELECT i FROM Item i WHERE i.category = ?1")
public class Item {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Basic
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "category")
    private String category;
    @Basic
    @Column(name = "brand")
    private String brand;
    @Basic
    @Column(name = "manufacturer")
    private String manufacturer;
    @Basic
    @Column(name = "part_number")
    private int partNumber;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "price")
    private double price;
    @Basic
    @Column(name = "quantity")
    private Integer quantity;
    @Basic
    @Column(name = "base64_image")
    private String base64Image;
    @Basic
    @Column(name = "average_rating")
    private Float averageRating;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item that = (Item) o;

        if (id != that.id) return false;
        if (partNumber != that.partNumber) return false;
        if (price != that.price) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (brand != null ? !brand.equals(that.brand) : that.brand != null) return false;
        if (manufacturer != null ? !manufacturer.equals(that.manufacturer) : that.manufacturer != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (base64Image != null ? !base64Image.equals(that.base64Image) : that.base64Image != null) return false;
        if (averageRating != null ? !averageRating.equals(that.averageRating) : that.averageRating != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (manufacturer != null ? manufacturer.hashCode() : 0);
        result = 31 * result + partNumber;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int)price;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + base64Image.hashCode();
        result = 31 * result + (averageRating != null ? averageRating.hashCode() : 0);
        return result;
    }
}
