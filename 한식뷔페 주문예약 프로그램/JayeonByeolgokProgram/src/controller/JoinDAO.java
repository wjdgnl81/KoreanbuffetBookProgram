package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.JoinVO;

public class JoinDAO {

	//직원등록
	public boolean getEmployeeRegiste(JoinVO jvo) throws Exception{
		String sql = "insert into employee values"+"(employee_seq.nextval,?,?,?,?,?,'-')";
	
		Connection con = null;
		PreparedStatement pstmt = null;
		JoinVO joinVo = null;
		
		boolean joinSucess = false;
		
		try {
			con = DBUtil.getConnection();
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, jvo.getE_name());
			pstmt.setString(2, jvo.getE_section());
			pstmt.setString(3, jvo.getE_conde());
			pstmt.setString(4, jvo.getE_password());
			pstmt.setString(5, jvo.getE_entrada());
			
			int i = pstmt.executeUpdate();
			
			if(i==1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("직원 등록");
				alert.setHeaderText("환영합니다!");
				alert.setContentText(jvo.getE_name()+" 님의 직원 코드는\n"+jvo.getE_conde()+" 입니다.");
				alert.showAndWait();
				
				joinVo = new JoinVO();
				joinSucess = true;
				
			}else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 등록");
				alert.setHeaderText(null);
				alert.setContentText("직원 등록 실패!");
				alert.showAndWait();
				
				joinVo = null;
				
			}
		}catch(SQLException e) {
			System.out.println(e);
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("직원 등록 오류");
			alert.setHeaderText(null);
			alert.setContentText("정확히 입력하세요.");
			alert.showAndWait();
		}catch(Exception e) {
			System.out.println("e=["+e+"]");
		}finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(con != null)
					con.close();
			}catch(SQLException e) {
				
			}
		}
		
		return joinSucess;
		
	}
	
	//코드 중복 체크
	public boolean getCodeOverlap(String codeOverlap) throws Exception{
		String sql = "select * from employee where e_resignation = '-' and e_code = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean codeOverlapResult = false;
		
		try {
			con=DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, codeOverlap);
			rs = pstmt.executeQuery();
			
			if(rs.next())
				codeOverlapResult = true; //중복된 아이디 없음
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
		
		return codeOverlapResult;
	}
	
}
