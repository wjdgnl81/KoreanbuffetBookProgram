package model;

public class SalesVO {
	
	private String pay_date;//결제날짜
	private int total_count;//방문인원
	private int day_sales;//날짜별 매출
	private String e_name;//직원이름
	private String e_section;//직급
	private int e_count;//직원 건수
	private int e_sales;//직원별 매출
	private String card;//카드사
	private int c_count;//카드사 건수
	private int c_sales;//카드사 매출
	
	
	
	public SalesVO() {
		super();
	}
	
	public SalesVO(String pay_date, int total_count, int day_sales) {
		super();
		this.pay_date = pay_date;
		this.total_count = total_count;
		this.day_sales = day_sales;
	}
	

	public SalesVO(String pay_date, int day_sales, String e_name, String e_section, int e_count) {
		super();
		this.pay_date = pay_date;
		this.day_sales = day_sales;
		this.e_name = e_name;
		this.e_section = e_section;
		this.e_count = e_count;
	}
	

	public SalesVO(String pay_date, int day_sales, String card, int c_count) {
		super();
		this.pay_date = pay_date;
		this.day_sales = day_sales;
		this.card = card;
		this.c_count = c_count;
	}
	
	

	public SalesVO(String pay_date, int total_count, int day_sales, String e_name, String e_section, int e_count,
			int e_sales, String card, int c_count, int c_sales) {
		super();
		this.pay_date = pay_date;
		this.total_count = total_count;
		this.day_sales = day_sales;
		this.e_name = e_name;
		this.e_section = e_section;
		this.e_count = e_count;
		this.e_sales = e_sales;
		this.card = card;
		this.c_count = c_count;
		this.c_sales = c_sales;
	}

	public String getE_name() {
		return e_name;
	}

	public void setE_name(String e_name) {
		this.e_name = e_name;
	}

	public String getE_section() {
		return e_section;
	}

	public void setE_section(String e_section) {
		this.e_section = e_section;
	}

	public int getE_count() {
		return e_count;
	}

	public void setE_count(int e_count) {
		this.e_count = e_count;
	}

	public int getE_sales() {
		return e_sales;
	}

	public void setE_sales(int e_sales) {
		this.e_sales = e_sales;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public int getC_count() {
		return c_count;
	}

	public void setC_count(int c_count) {
		this.c_count = c_count;
	}

	public int getC_sales() {
		return c_sales;
	}

	public void setC_sales(int c_sales) {
		this.c_sales = c_sales;
	}

	public String getPay_date() {
		return pay_date;
	}
	
	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}
	
	public int getTotal_count() {
		return total_count;
	}
	
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	
	public int getDay_sales() {
		return day_sales;
	}
	
	public void setDay_sales(int day_sales) {
		this.day_sales = day_sales;
	}
	
	
	
	

}
