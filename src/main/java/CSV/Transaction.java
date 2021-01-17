package CSV;

import java.sql.Date;

public class Transaction {
	private int id;
	private String name;
	private double amount;
	private Date date;
	public Transaction(int id, String name, double amount, Date date) {
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public double getAmount() {
		return amount;
	}
	public Date getDate() {
		return date;
	}
}
