package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.EmployeeVO;
import model.JoinVO;
import model.OrderVO;

public class LoginDAO {

	//직원코드와 패스워드가 맞는지 체크
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
				loginResult = true; //아이디와 패스워드가 일치
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
	
	
	//직원코드로 이름을 검색
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

	//직원코드로 구분 검색
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
