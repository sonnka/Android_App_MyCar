package nure.kazantseva.mycar.model;

public class Auto {
    private int id;
    private String uniqueCode;
    private String userEmail;
    private String brand;
    private String model;
    private int year;
    private String fuel;
    private long run;

    public Auto(String userEmail,String uniqueCode, String brand, String model, int year,
                String fuel, long run) {
        this.userEmail = userEmail;
        this.uniqueCode = uniqueCode;
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

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
                ", uniqueCode=" + uniqueCode +
                ", userEmail='" + userEmail + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", fuel='" + fuel + '\'' +
                ", run=" + run +
                '}';
    }
}
