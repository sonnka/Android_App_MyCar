package nure.kazantseva.mycar.model;

public class Auto {
    private int id;
    private String user_email;
    private String brand;
    private String model;
    private int year;
    private String fuel;
    private long run;

    public Auto(String user_email, String brand, String model, int year, String fuel, long run) {
        this.user_email = user_email;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.fuel = fuel;
        this.run = run;
    }

    public Auto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public long getRun() {
        return run;
    }

    public void setRun(long run) {
        this.run = run;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "id=" + id +
                ", user_email=" + user_email +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", fuel='" + fuel + '\'' +
                ", run=" + run +
                '}';
    }
}
