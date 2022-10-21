package nure.kazantseva.mycar.model;

import java.time.LocalDate;

public class Refill {
    private int id;
    private int auto_id;
    private LocalDate date;
    private long run;
    private double beforeRefill;
    private double addFuel;
    private double price;
    private String station;

    public Refill(int auto_id, LocalDate date, long run, double beforeRefill, double addFuel, double price, String station) {
        this.auto_id = auto_id;
        this.date = date;
        this.run = run;
        this.beforeRefill = beforeRefill;
        this.addFuel = addFuel;
        this.price = price;
        this.station = station;
    }

    public Refill() {
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

    public long getRun() {
        return run;
    }

    public void setRun(long run) {
        this.run = run;
    }

    public double getBeforeRefill() {
        return beforeRefill;
    }

    public void setBeforeRefill(double beforeRefill) {
        this.beforeRefill = beforeRefill;
    }

    public double getAddFuel() {
        return addFuel;
    }

    public void setAddFuel(double addFuel) {
        this.addFuel = addFuel;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "Refill{" +
                "id=" + id +
                ", auto_id=" + auto_id +
                ", date=" + date +
                ", run=" + run + '\'' +
                ", beforeRefill=" + beforeRefill +
                ", addFuel=" + addFuel +
                ", price=" + price +
                ", station='" + station + '\'' +
                '}';
    }
}
