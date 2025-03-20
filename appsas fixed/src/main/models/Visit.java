package models;

public class Visit {
    private int id;
    private String date;
    private String time;
    private String client;
    private String notes;
    private double income;

    public Visit(int id, String date, String time, String client, String notes, double income) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.client = client;
        this.notes = notes;
        this.income = income;
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getClient() { return client; }
    public String getNotes() { return notes; }
    public double getIncome() { return income; }

    public void setId(int id) { this.id = id; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setClient(String client) { this.client = client; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setIncome(double income) { this.income = income; }
}
