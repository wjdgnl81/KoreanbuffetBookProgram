package model;

public class JoinVO {
	
	private int e_no;				//���� ��ȣ
	private String e_name;			//���� �̸�
	private String e_section;		//���� ����
	private String e_conde;			//���� �ڵ�
	private String e_password;		//��й�ȣ
	private String e_entrada;		//�Ի���
	private String e_resignation;	 //�����
	
	
	public JoinVO() {
		super();
	}
	
	
	public JoinVO(String e_name) {
		super();
		this.e_name = e_name;
	}

	
	public JoinVO(String e_name, String e_section, String e_conde, String e_password) {
		super();
		this.e_name = e_name;
		this.e_section = e_section;
		this.e_conde = e_conde;
		this.e_password = e_password;
	}


	public JoinVO(int e_no, String e_name, String e_section, String e_conde, String e_password) {
		super();
		this.e_no = e_no;
		this.e_name = e_name;
		this.e_section = e_section;
		this.e_conde = e_conde;
		this.e_password = e_password;
	}


	public JoinVO(String e_name, String e_section, String e_conde, String e_password, String e_entrada) {
		super();
		this.e_name = e_name;
		this.e_section = e_section;
		this.e_conde = e_conde;
		this.e_password = e_password;
		this.e_entrada = e_entrada;
	}


	public JoinVO(int e_no, String e_name, String e_section, String e_conde, String e_password, String e_entrada,
			String e_resignation) {
		super();
		this.e_no = e_no;
		this.e_name = e_name;
		this.e_section = e_section;
		this.e_conde = e_conde;
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


	public String getE_conde() {
		return e_conde;
	}


	public void setE_conde(String e_conde) {
		this.e_conde = e_conde;
	}


	public String getE_password() {
		return e_password;
	}


	public void setE_password(String e_password) {
		this.e_password = e_password;
	}
	
	

}
