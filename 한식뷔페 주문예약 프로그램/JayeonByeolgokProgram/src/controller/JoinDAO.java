package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.JoinVO;

public class JoinDAO {

	//�������
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
				alert.setTitle("���� ���");
				alert.setHeaderText("ȯ���մϴ�!");
				alert.setContentText(jvo.getE_name()+" ���� ���� �ڵ��\n"+jvo.getE_conde()+" �Դϴ�.");
				alert.showAndWait();
				
				joinVo = new JoinVO();
				joinSucess = true;
				
			}else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("���� ���");
				alert.setHeaderText(null);
				alert.setContentText("���� ��� ����!");
				alert.showAndWait();
				
				joinVo = null;
				
			}
		}catch(SQLException e) {
			System.out.println(e);
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("���� ��� ����");
			alert.setHeaderText(null);
			alert.setContentText("��Ȯ�� �Է��ϼ���.");
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
	
	//�ڵ� �ߺ� üũ
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
				codeOverlapResult = true; //�ߺ��� ���̵� ����
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
