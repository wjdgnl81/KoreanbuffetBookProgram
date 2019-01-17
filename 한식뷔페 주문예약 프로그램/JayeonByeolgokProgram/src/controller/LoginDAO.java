package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.EmployeeVO;
import model.JoinVO;
import model.OrderVO;

public class LoginDAO {

	//�����ڵ�� �н����尡 �´��� üũ
	public boolean getLogin(String loginCode,String loginPassword) throws Exception{
		String sql = "select * from employee where e_code = ? and e_password = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean loginResult = false;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, loginCode);
			pstmt.setString(2, loginPassword);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				loginResult = true; //���̵�� �н����尡 ��ġ
			}
		}catch(SQLException e) {
			System.out.println("e=["+e+"]");
		}catch(Exception e) {
			System.out.println("e=["+e+"]");
		}finally {
			try {
				if(rs!= null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
				if(con!=null)
					con.close();
			}catch(SQLException e) {
				
			}
		}
		return loginResult;
	}
	
	
	//�����ڵ�� �̸��� �˻�
	public String getEname(String e_code)throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from employee where e_code like ?");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployeeVO evo = null;
		String ename = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, e_code);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				evo = new EmployeeVO();
				evo.setE_name(rs.getString("e_name"));
				
				ename = evo.getE_name();
			}
		}catch(SQLException e) {
			System.out.println("e=["+e+"]");
		}catch(Exception e) {
			System.out.println("e=["+e+"]");
		}finally {
			try {
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
				if(con!=null)
					con.close();
			}catch(SQLException e) {
				
			}
		}
		
		return ename;
	}

	//�����ڵ�� ���� �˻�
	public String getEsection(String e_code)throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from employee where e_code like ?");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployeeVO evo = null;
		String esection = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, e_code);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				evo = new EmployeeVO();
				evo.setE_section(rs.getString("e_section"));
				
				esection = evo.getE_section();
			}
		}catch(SQLException e) {
			System.out.println("e=["+e+"]");
		}catch(Exception e) {
			System.out.println("e=["+e+"]");
		}finally {
			try {
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
				if(con!=null)
					con.close();
			}catch(SQLException e) {
				
			}
		}
		
		return esection;
	}
	
}
