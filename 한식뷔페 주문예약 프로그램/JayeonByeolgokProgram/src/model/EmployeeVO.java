package model;

public class EmployeeVO {
	
	private int e_no;				//���� ��ȣ
	private String e_name;			//���� �̸�
	private String e_section;		//���� ����
	private String e_code;			//���� �ڵ�
	private String e_password;		//��й�ȣ
	private String e_entrada;		//�Ի���
	private String e_resignation;	 //�����
	
	
	public EmployeeVO() {
		super();
	}
	
	
	
	public EmployeeVO(String e_resignation) {
		super();
		this.e_resignation = e_resignation;
	}


	public EmployeeVO(String e_name, String e_section, String e_code, String e_password) {
		super();
		this.e_name = e_name;
		this.e_section = e_section;
		this.e_code = e_code;
		this.e_password = e_password;
	}


	public EmployeeVO(int e_no, String e_name, String e_section, String e_code, String e_password) {
		super();
		this.e_no = e_no;
		this.e_name = e_name;
		this.e_section = e_section;
		this.e_code = e_code;
		this.e_password = e_password;
	}


	public EmployeeVO(String e_name, String e_section, String e_code, String e_password, String e_entrada) {
		super();
		this.e_name = e_name;
		this.e_section = e_section;
		this.e_code = e_code;
		this.e_password = e_password;
		this.e_entrada = e_entrada;
	}


	public EmployeeVO(int e_no, String e_name, String e_section, String e_code, String e_password, String e_entrada,
			String e_resignation) {
		super();
		this.e_no = e_no;
		this.e_name = e_name;
		this.e_section = e_section;
		this.e_code = e_code;
		this.e_password = e_password;
		this.e_entrada = e_entrada;
		this.e_resignation = e_resignation;
	}

	
	public String getE_entrada() {
		return e_entrada;
	}


	public void setE_entrada(String e_entrada) {
		this.e_entrada = e_entrada;
	}


	public String getE_resignation() {
		return e_resignation;
	}


	public void setE_resignation(String e_resignation) {
		this.e_resignation = e_resignation;
	}


	public int getE_no() {
		return e_no;
	}


	public void setE_no(int e_no) {
		this.e_no = e_no;
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


	public String getE_code() {
		return e_code;
	}


	public void setE_code(String e_code) {
		this.e_code = e_code;
	}


	public String getE_password() {
		return e_password;
	}


	public void setE_password(String e_password) {
		this.e_password = e_password;
	}
	
	

}
