package entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "items", schema = "public", catalog = "online_shop")
@NamedQuery(name = "Items.byCategory", query = "SELECT i FROM Item i WHERE i.category = ?1")
@NamedQuery(name = "Items.byPartNumber", query = "SELECT i FROM Item i WHERE i.partNumber = ?1")
@NamedQuery(name = "Items.byPartNumberAndCategory", query = "SELECT i FROM Item i WHERE i.partNumber = ?1 AND i.category = ?2")
@NamedQuery(name = "Items.byDescription", query = "SELECT i FROM Item i WHERE i.description LIKE CONCAT('%',?1,'%')")
@NamedQuery(name = "Items.byDescriptionAndCategory", query = "SELECT i FROM Item i WHERE i.description LIKE CONCAT('%',?1,'%') AND i.category = ?2")
@NamedQuery(name = "Items.createdAfter", query = "SELECT i FROM Item i WHERE i.dateCreated >= ?1")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String partNumber;
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

    @Basic
    @Column(name = "date_created")
    private Date dateCreated;

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

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item that = (Item) o;

        if (id != that.id) return false;
        if (!Objects.equals(partNumber, that.partNumber)) return false;
        if (price != that.price) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(category, that.category)) return false;
        if (!Objects.equals(brand, that.brand)) return false;
        if (!Objects.equals(manufacturer, that.manufacturer)) return false;
        if (!Objects.equals(description, that.description)) return false;
        if (!Objects.equals(quantity, that.quantity)) return false;
        if (!Objects.equals(base64Image, that.base64Image)) return false;
        if (!Objects.equals(dateCreated, that.dateCreated)) return false;
        return Objects.equals(averageRating, that.averageRating);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (manufacturer != null ? manufacturer.hashCode() : 0);
        result = 31 * result + (partNumber != null ? partNumber.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) price;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + base64Image.hashCode();
        result = 31 * result + (averageRating != null ? averageRating.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        return result;
    }
}
