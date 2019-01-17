package model;

public class OrderVO {

	private int bo_no;//�Ϸù�ȣ
	private String c_section;//�� ����
	private String table_no;//���̺� ��ȣ
	private int total;//�� �ο�
	private int total_price;//�� ����
	private int adult;//���� ��
	private int sc_child;//�Ƶ� ��
	private int ps_child;//���� ��
	private int side;//���̵� ��
	private String b_name;//������ ��
	private String b_date;//���೯¥
	private String b_time;//����ð�
	private String b_phone;//����ó
	private String pay;//��������
	private String pay_way;//��������
	private String pay_date;//������
	private String card;//ī���
	private String e_name;//�����̸�
	
	//����Ʈ ������
	public OrderVO() {
		super();
	}

	//�Ϸù�ȣ ������
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
	
	//��������, ��������, ������ ����
	public OrderVO(int bo_no, String pay, String pay_way, String pay_date) {
		super();
		this.bo_no = bo_no;
		this.pay = pay;
		this.pay_way = pay_way;
		this.pay_date = pay_date;
	}

	
	//��������, ��������, ������, ī��� ����
	public OrderVO(int bo_no, String pay, String pay_way, String pay_date, String card) {
		super();
		this.bo_no = bo_no;
		this.pay = pay;
		this.pay_way = pay_way;
		this.pay_date = pay_date;
		this.card = card;
	}

	//��������(��������, ��������, ������, ī��� ������)
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

	//��������(�����ڸ�, ������, ����ð�, ����ó), �������� ������
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
	
	//��������,��������, �ֹ����� ������
	public OrderVO(String c_section, String table_no, String e_name) {
		super();
		this.c_section = c_section;
		this.table_no = table_no;
		this.e_name = e_name;
	}
	
	//��������, �ֹ����� ������
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
	
	//ī��� ������
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

