package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.EmployeeVO;

public class EmployeeDAO {

	//직원목록
	public ArrayList<EmployeeVO> getEmployeeList(){
		ArrayList<EmployeeVO> list = new ArrayList<EmployeeVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select e_no,e_name,e_section,e_code,e_entrada,e_resignation ");
		sql.append("from employee order by e_no");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployeeVO evo = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				evo = new EmployeeVO();
				evo.setE_no(rs.getInt("e_no"));
				evo.setE_name(rs.getString("e_name"));
				evo.setE_section(rs.getString("e_section"));
				evo.setE_code(rs.getString("e_code"));
				evo.setE_entrada(rs.getString("e_entrada"));
				evo.setE_resignation(rs.getString("e_resignation"));
				
				list.add(evo);
			}
		}catch(SQLException se) {
			System.out.println(se);
		}catch(Exception e) {
			System.out.println(e);
		}finally {
			try {
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
				if(con!=null)
					con.close();
			}catch(SQLException se) {
				
			}
		}
		
		return list;
		
	}
	
	
	//직원목록 칼럼 갯수
	public ArrayList<String> getEmployeeColumnName(){
		ArrayList<String> columnName = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select e_no,e_name,e_section,e_code,e_entrada,e_resignation ");
		sql.append("from employee order by e_no");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				columnName.add(rsmd.getColumnName(i));
			}
		}catch(SQLException se) {
			System.out.println(se);
		}catch(Exception e) {
			System.out.println(e);
		}finally {
			try {
				if(rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(con != null)
					con.close();
			}catch(SQLException e) {
			}
		}
		
		return columnName;
	}

	
	//퇴사직원 퇴사일 업데이트
	public EmployeeVO getEmployeeResignation(EmployeeVO evo, int e_no)throws Exception{
		
		StringBuffer sql = new StringBuffer();
		sql.append("update employee set ");
		sql.append("e_resignation = ? where e_no = ?");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		EmployeeVO retval = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, evo.getE_resignation());
			pstmt.setInt(2, e_no);
			
			int i = pstmt.executeUpdate();
			
			if(i == 1) {
				Alert alert = new Alert (AlertType.INFORMATION);
				alert.setTitle("직원 퇴사");
				alert.setHeaderText(null);
				alert.setContentText("퇴사 등록 완료");
				alert.showAndWait();
			}else{
				System.out.println("등록 실패");
			}
		}catch (SQLException e) {
			System.out.println("e=["+e+"]");
		}catch(Exception e) {
			System.out.println("e=["+e+"]");
		}finally {
			try {
				if(pstmt!=null)
					pstmt.close();
				if(con!=null)
					con.close();
			}catch(SQLException e) {
				
			}
		}
		
		return retval;
		
	}
	
	
	//직원 승급
	public EmployeeVO getEmployeePromotion(int e_no)throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("update employee set ");
		sql.append("e_section = '관리자' where e_no = ?");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		EmployeeVO retval = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			retval = new EmployeeVO();
			pstmt.setInt(1, e_no);
			
			int i = pstmt.executeUpdate();
			
			if(i == 1) {
				Alert alert = new Alert (AlertType.INFORMATION);
				alert.setTitle("직원 승급");
				alert.setHeaderText(null);
				alert.setContentText("해당 직원이 관리자로 승급되었습니다.");
				alert.showAndWait();
			}else{
				System.out.println("등록 실패");
			}
		}catch (SQLException e) {
			System.out.println("e=["+e+"]");
		}catch(Exception e) {
			System.out.println("e=["+e+"]");
		}finally {
			try {
				if(pstmt!=null)
					pstmt.close();
				if(con!=null)
					con.close();
			}catch(SQLException e) {
				
			}
		}
		
		return retval;
	}
}
