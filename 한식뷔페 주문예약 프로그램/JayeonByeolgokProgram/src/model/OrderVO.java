package model;

public class OrderVO {

	private int bo_no;//일련번호
	private String c_section;//고객 구분
	private String table_no;//테이블 번호
	private int total;//총 인원
	private int total_price;//총 가격
	private int adult;//성인 수
	private int sc_child;//아동 수
	private int ps_child;//유아 수
	private int side;//사이드 수
	private String b_name;//예약자 명
	private String b_date;//예약날짜
	private String b_time;//예약시간
	private String b_phone;//연락처
	private String pay;//결제여부
	private String pay_way;//결제수단
	private String pay_date;//결제일
	private String card;//카드사
	private String e_name;//직원이름
	
	//디폴트 생성자
	public OrderVO() {
		super();
	}

	//일련번호 미포함
	public OrderVO(String c_section, String table_no, int total, int total_price, int adult, int sc_child,
			int ps_child, int side, String b_name, String b_date, String b_time, String b_phone, String pay, String pay_way,
			String pay_date, String card, String e_name) {
		super();
		this.c_section = c_section;
		this.table_no = table_no;
		this.total = total;
		this.total_price = total_price;
		this.adult = adult;
		this.sc_child = sc_child;
		this.ps_child = ps_child;
		this.side = side;
		this.b_name = b_name;
		this.b_date = b_date;
		this.b_time = b_time;
		this.b_phone = b_phone;
		this.pay = pay;
		this.pay_way = pay_way;
		this.pay_date = pay_date;
		this.card = card;
		this.e_name = e_name;
	}
	
	//결제여부, 결제수단, 결제일 저장
	public OrderVO(int bo_no, String pay, String pay_way, String pay_date) {
		super();
		this.bo_no = bo_no;
		this.pay = pay;
		this.pay_way = pay_way;
		this.pay_date = pay_date;
	}

	
	//결제여부, 결제수단, 결제일, 카드사 저장
	public OrderVO(int bo_no, String pay, String pay_way, String pay_date, String card) {
		super();
		this.bo_no = bo_no;
		this.pay = pay;
		this.pay_way = pay_way;
		this.pay_date = pay_date;
		this.card = card;
	}

	//결제정보(결제여부, 결제수단, 결제일, 카드사 미포함)
	public OrderVO(String c_section, String table_no, int total, int total_price, int adult, int sc_child,
			int ps_child, int side, String b_name, String b_date, String b_time, String b_phone, String e_name) {
		super();
		this.c_section = c_section;
		this.table_no = table_no;
		this.total = total;
		this.total_price = total_price;
		this.adult = adult;
		this.sc_child = sc_child;
		this.ps_child = ps_child;
		this.side = side;
		this.b_name = b_name;
		this.b_date = b_date;
		this.b_time = b_time;
		this.b_phone = b_phone;
		this.e_name = e_name;
	}

	//예약정보(예약자명, 예약일, 예약시간, 연락처), 결제정보 미포함
	public OrderVO(String c_section, String table_no, int total, int total_price, int adult, int sc_child,
			int ps_child, int side, String e_name) {
		super();
		this.c_section = c_section;
		this.table_no = table_no;
		this.total = total;
		this.total_price = total_price;
		this.adult = adult;
		this.sc_child = sc_child;
		this.ps_child = ps_child;
		this.side = side;
		this.e_name = e_name;
	}
	
	//예약정보,결제정보, 주문정보 미포함
	public OrderVO(String c_section, String table_no, String e_name) {
		super();
		this.c_section = c_section;
		this.table_no = table_no;
		this.e_name = e_name;
	}
	
	//결제정보, 주문정보 미포함
	public OrderVO(String c_section, String table_no, String b_name, String b_date, String b_time, String b_phone,
			String e_name) {
		super();
		this.c_section = c_section;
		this.table_no = table_no;
		this.b_name = b_name;
		this.b_date = b_date;
		this.b_time = b_time;
		this.b_phone = b_phone;
		this.e_name = e_name;
	}
	
	//카드사 미포함
	public OrderVO(int bo_no, String c_section, String table_no, int total, int total_price, int adult,
			int sc_child, int ps_child,int side, String b_name, String b_date, String b_time, String b_phone, String pay,
			String pay_way, String pay_date, String e_name) {
		super();
		this.bo_no = bo_no;
		this.c_section = c_section;
		this.table_no = table_no;
		this.total = total;
		this.total_price = total_price;
		this.adult = adult;
		this.sc_child = sc_child;
		this.ps_child = ps_child;
		this.side = side;
		this.b_name = b_name;
		this.b_date = b_date;
		this.b_time = b_time;
		this.b_phone = b_phone;
		this.pay = pay;
		this.pay_way = pay_way;
		this.pay_date = pay_date;
		this.e_name = e_name;
	}
	

	public OrderVO(String c_section, String table_no, int total, int total_price, int adult, int sc_child,
			int ps_child, int side, String b_name, String b_date, String b_time, String b_phone, String pay, String e_name) {
		super();
		this.c_section = c_section;
		this.table_no = table_no;
		this.total = total;
		this.total_price = total_price;
		this.adult = adult;
		this.sc_child = sc_child;
		this.ps_child = ps_child;
		this.side = side;
		this.b_name = b_name;
		this.b_date = b_date;
		this.b_time = b_time;
		this.b_phone = b_phone;
		this.pay = pay;
		this.e_name = e_name;
	}

	public OrderVO(int bo_no, String c_section, String table_no, int total, int total_price, int adult,
			int sc_child, int ps_child, int side, String b_name, String b_date, String b_time, String b_phone, String pay,
			String pay_way, String pay_date, String card, String e_name) {
		super();
		this.bo_no = bo_no;
		this.c_section = c_section;
		this.table_no = table_no;
		this.total = total;
		this.total_price = total_price;
		this.adult = adult;
		this.sc_child = sc_child;
		this.ps_child = ps_child;
		this.side = side;
		this.b_name = b_name;
		this.b_date = b_date;
		this.b_time = b_time;
		this.b_phone = b_phone;
		this.pay = pay;
		this.pay_way = pay_way;
		this.pay_date = pay_date;
		this.card = card;
		this.e_name = e_name;
	}

	
	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public int getBo_no() {
		return bo_no;
	}

	public void setBo_no(int bo_no) {
		this.bo_no = bo_no;
	}

	public String getC_section() {
		return c_section;
	}

	public void setC_section(String c_section) {
		this.c_section = c_section;
	}

	public String getTable_no() {
		return table_no;
	}

	public void setTable_no(String table_no) {
		this.table_no = table_no;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotal_price() {
		return total_price;
	}

	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}

	public int getAdult() {
		return adult;
	}

	public void setAdult(int adult) {
		this.adult = adult;
	}

	public int getSc_child() {
		return sc_child;
	}

	public void setSc_child(int sc_child) {
		this.sc_child = sc_child;
	}

	public int getPs_child() {
		return ps_child;
	}

	public void setPs_child(int ps_child) {
		this.ps_child = ps_child;
	}

	public String getB_name() {
		return b_name;
	}

	public void setB_name(String b_name) {
		this.b_name = b_name;
	}

	public String getB_date() {
		return b_date;
	}

	public void setB_date(String b_date) {
		this.b_date = b_date;
	}

	public String getB_time() {
		return b_time;
	}

	public void setB_time(String b_time) {
		this.b_time = b_time;
	}

	public String getB_phone() {
		return b_phone;
	}

	public void setB_phone(String b_phone) {
		this.b_phone = b_phone;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public String getPay_way() {
		return pay_way;
	}

	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}

	public String getPay_date() {
		return pay_date;
	}

	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getE_name() {
		return e_name;
	}

	public void setE_name(String e_name) {
		this.e_name = e_name;
	}
	
	
}

