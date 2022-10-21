package nure.kazantseva.mycar.model;

import java.time.LocalDate;

public class Washer {
    private int id;
    private int auto_id;
    private LocalDate date;
    private double price;

    public Washer(int auto_id, LocalDate date, double price) {
        this.auto_id = auto_id;
        this.date = date;
        this.price = price;
    }

    public Washer() {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Washer{" +
                "id=" + id +
                ", auto_id=" + auto_id +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
