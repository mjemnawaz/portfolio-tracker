package CSV;

public class Stock {
	private int id = 0, userId = 0;
	private String name = "", abrev = "";
	private double holdings = 0, avgcost = 0;
	public Stock(int id, int userId, String name, String abrev, double holdings, double avgcost) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.abrev = abrev;
		this.holdings = holdings;
		this.avgcost = avgcost;
	}
	public int getUserId() {
		return userId;
	}
	public String getAbrev() {
		return abrev;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public double getHoldings() {
		return holdings;
	}
	public double getAvgcost() {
		return avgcost;
	}
}
