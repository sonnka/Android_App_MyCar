package nure.kazantseva.mycar.model;

import java.time.LocalDate;

public class Other {
    private int id;
    private int auto_id;
    private LocalDate date;
    private String description;
    private double price;

    public Other(int auto_id, LocalDate date, String description, double price) {
        this.auto_id = auto_id;
        this.date = date;
        this.description = description;
        this.price = price;
    }

    public Other() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(int auto_id) {
        this.auto_id = auto_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    @Override
    public String toString() {
        return "Other{" +
                "id=" + id +
                ", auto_id=" + auto_id +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
