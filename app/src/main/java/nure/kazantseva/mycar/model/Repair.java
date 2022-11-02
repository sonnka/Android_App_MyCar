package nure.kazantseva.mycar.model;

import java.time.LocalDate;

public class Repair {
    private int id;
    private int auto_id;
    private LocalDate date;
    private long run;
    private String description;
    private double price;

    public Repair(int auto_id, LocalDate date, long run, String description, double price) {
        this.auto_id = auto_id;
        this.date = date;
        this.run = run;
        this.description = description;
        this.price = price;
    }

    public Repair() {
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
        return  "Дата ремонту : " + date + '\n' +
                "Пробіг на момент ремонту : " + run + " км" + '\n' +
                "Опис ремонту : " + description + '\n' +
                "Вартість : " + price + " грн";
    }
}
