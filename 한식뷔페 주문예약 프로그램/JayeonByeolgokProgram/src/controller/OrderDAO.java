package controller;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.plaf.synth.SynthSliderUI;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.OrderVO;

public class OrderDAO {

	//�ֹ�(�����) ���
	public OrderVO getOrderRegisteB(OrderVO ovo) throws Exception{
		String dml = "insert into book_order (bo_no,c_section,table_no,total,"
				+ "total_price,adult,sc_child,ps_child,side,b_name,b_time,b_phone,e_name,b_date,pay)"
				+"values (book_order_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		OrderVO retval = null;
		
		try {
			con = DBUtil.getConnection();
			
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, ovo.getC_section());
			pstmt.setString(2, ovo.getTable_no());
			pstmt.setInt(3, ovo.getTotal());
			pstmt.setInt(4, ovo.getTotal_price());
			pstmt.setInt(5, ovo.getAdult());
			pstmt.setInt(6, ovo.getSc_child());
			pstmt.setInt(7, ovo.getPs_child());
			pstmt.setInt(8, ovo.getSide());
			pstmt.setString(9, ovo.getB_name());
			pstmt.setString(10, ovo.getB_time());
			pstmt.setString(11, ovo.getB_phone());
			pstmt.setString(12, ovo.getE_name());
			pstmt.setString(13, ovo.getB_date());
			pstmt.setString(14, ovo.getPay());
			
			int i = pstmt.executeUpdate();
			
			if(i==1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("�ֹ� ���");
				alert.setHeaderText(null);
				alert.setContentText(ovo.getTable_no()+"�� ���̺� ��� �Ϸ�.");
				alert.showAndWait();
				
				retval = new OrderVO();
				
			}else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("�ֹ� ���");
				alert.setHeaderText(null);
				alert.setContentText("�ֹ� ��� ����!");
				alert.showAndWait();
				
				retval = null;
			}
			
		}catch(SQLException e) {
			System.out.println("e=["+e+"]");
		}catch(Exception e) {
			System.out.println("e=["+e+"]");
		}finally {
			try {
				if(pstmt !=null)
					pstmt.close();
				if(con!=null)
					con.close();
			}catch(SQLException e) {
				
			}
		}
		
		return retval;
		
	}
	
	
	//��ü ����Ʈ
	public ArrayList<OrderVO> getOrderTotal(){
		ArrayList<OrderVO> list = new ArrayList<OrderVO>();
		StringBuffer sql = new StringBuffer(); 
		
		sql.append("select bo_no,c_section,table_no,total,total_price,adult,sc_child,ps_child,side,");
		sql.append("b_name,b_date,b_time,b_phone,pay,");
		sql.append("e_name from book_order order by pay,bo_no desc");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderVO ovo = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ovo = new OrderVO();
				ovo.setBo_no(rs.getInt("bo_no"));
				//�ֹ�����
				ovo.setC_section(rs.getString("c_section"));
				ovo.setTable_no(rs.getString("table_no"));
				ovo.setTotal(rs.getInt("total"));
				ovo.setTotal_price(rs.getInt("total_price"));
				ovo.setAdult(rs.getInt("adult"));
				ovo.setSc_child(rs.getInt("sc_child"));
				ovo.setPs_child(rs.getInt("ps_child"));
				ovo.setSide(rs.getInt("side"));
				
				//��������
				ovo.setB_name(rs.getString("b_name"));
				ovo.setB_date(rs.getString("b_date"));
				ovo.setB_time(rs.getString("b_time"));
				ovo.setB_phone(rs.getString("b_phone"));
				
				//��������
				ovo.setPay(rs.getString("pay"));
				ovo.setE_name(rs.getString("e_name"));
				
				list.add(ovo);
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
	
	
	//�ֹ���� Į�� ����
	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select bo_no,c_section,table_no,total,total_price,adult,sc_child,ps_child,side,");
		sql.append("b_name,b_date,b_time,b_phone,pay,pay_way,pay_date,");
		sql.append("e_name from book_order");
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

	
	//���̺��ȣ �ߺ� üũ
	public boolean getTableNoOverlap(int tableOverlap) throws Exception{
		
		StringBuffer sql = new StringBuffer();
		sql.append("select bo_no,c_section,table_no,total,total_price,adult,sc_child,ps_child,side,");
		sql.append("b_name,b_date,b_time,b_phone,pay,");
		sql.append("e_name from book_order where pay = 'N' and c_section = '�湮' and table_no = ?");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean tableOverlapResult = false;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, tableOverlap);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				tableOverlapResult = true;
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
		return tableOverlapResult;
	}
	
	
	//����� �̸� �˻�
	public boolean getEnameOverlap(String EnameOverlap) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from employee where e_resignation = '-' and e_name = ?");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean enameOverlapResult = false;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, EnameOverlap);
			rs = pstmt.executeQuery();
			
			if(rs.next())
				enameOverlapResult = true;
			
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
		
		return enameOverlapResult;
	}
	
	
	//���� �ߺ� �˻�
	public boolean getBookOverlap(int tableOverlap,String bookDayOverlap) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select bo_no,c_section,table_no,total,total_price,adult,sc_child,ps_child,side,");
		sql.append("b_name,b_date,b_time,b_phone,pay,");
		sql.append("e_name from book_order where pay = 'N' and table_no = ? and b_date = ?");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean bookOverlapResult = false;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, tableOverlap);
			pstmt.setString(2, bookDayOverlap);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bookOverlapResult = true;
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
		
		return bookOverlapResult;
		
	}
	
	
	//�����Ϸ�� ����(����)
	public OrderVO getOrderCashUpdate(OrderVO ovo, int bo_no)throws Exception{
		
		StringBuffer sql = new StringBuffer();
		sql.append("update book_order set ");
		sql.append("pay=?, pay_way=?, pay_date=? where bo_no = ?");
		Connection con = null;
		PreparedStatement pstmt = null;
		OrderVO retval = null;
		
		try {
			con = DBUtil.getConnection();
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, ovo.getPay());
			pstmt.setString(2, ovo.getPay_way());
			pstmt.setString(3, ovo.getPay_date());
			pstmt.setInt(4, bo_no);
			
			int i = pstmt.executeUpdate();
			
			if(i == 1) {
				Alert alert = new Alert (AlertType.INFORMATION);
				alert.setTitle("�����Ϸ�");
				alert.setHeaderText(null);
				alert.setContentText("������ �Ϸ�Ǿ����ϴ�.");
				alert.showAndWait();
			}else{
				System.out.println("��������");
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
	
	//�����Ϸ�� ����(ī��)
	public OrderVO getOrderCardUpdate(OrderVO ovo, int bo_no)throws Exception{
			
			StringBuffer sql = new StringBuffer();
			sql.append("update book_order set ");
			sql.append("pay=?, pay_way=?, pay_date=?, card=? where bo_no = ?");
			Connection con = null;
			PreparedStatement pstmt = null;
			OrderVO retval = null;
			
			try {
				con = DBUtil.getConnection();
				
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, ovo.getPay());
				pstmt.setString(2, ovo.getPay_way());
				pstmt.setString(3, ovo.getPay_date());
				pstmt.setString(4, ovo.getCard());
				pstmt.setInt(5, bo_no);
				
				int i = pstmt.executeUpdate();
				
				if(i == 1) {
					Alert alert = new Alert (AlertType.INFORMATION);
					alert.setTitle("�����Ϸ�");
					alert.setHeaderText(null);
					alert.setContentText("������ �Ϸ�Ǿ����ϴ�.");
					alert.showAndWait();
				}else{
					System.out.println("��������");
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
	
	
	//�����ڸ��� �޾� ���� ��ȸ
	public OrderVO getOrderNameSearch(String b_name)throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select bo_no,c_section,table_no,total,total_price,adult,sc_child,ps_child,side,");
		sql.append("b_name,b_date,b_time,b_phone,pay,");
		sql.append("e_name from book_order where b_name like ");
		sql.append("? order by bo_no desc");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderVO ovo = null;
		
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+b_name+"%");
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				ovo = new OrderVO();
				
				ovo.setBo_no(rs.getInt("bo_no"));
				//�ֹ�����
				ovo.setC_section(rs.getString("c_section"));
				ovo.setTable_no(rs.getString("table_no"));
				ovo.setTotal(rs.getInt("total"));
				ovo.setTotal_price(rs.getInt("total_price"));
				ovo.setAdult(rs.getInt("adult"));
				ovo.setSc_child(rs.getInt("sc_child"));
				ovo.setPs_child(rs.getInt("ps_child"));
				ovo.setSide(rs.getInt("side"));
				
				//��������
				ovo.setB_name(rs.getString("b_name"));
				ovo.setB_date(rs.getString("b_date"));
				ovo.setB_time(rs.getString("b_time"));
				ovo.setB_phone(rs.getString("b_phone"));
				
				//��������
				ovo.setPay(rs.getString("pay"));
				ovo.setE_name(rs.getString("e_name"));
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
		
		return ovo;
		
	}
	
	
	//����,�湮�� ��ȸ
	public OrderVO getBookSearch(String c_section)throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select bo_no,c_section,table_no,total,total_price,adult,sc_child,ps_child,side,");
		sql.append("b_name,b_date,b_time,b_phone,pay,");
		sql.append("e_name from book_order where c_section like ");
		sql.append("? order by pay, b_date desc");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderVO ovo = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+c_section+"%");
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				ovo = new OrderVO();
				
				ovo.setBo_no(rs.getInt("bo_no"));
				//�ֹ�����
				ovo.setC_section(rs.getString("c_section"));
				ovo.setTable_no(rs.getString("table_no"));
				ovo.setTotal(rs.getInt("total"));
				ovo.setTotal_price(rs.getInt("total_price"));
				ovo.setAdult(rs.getInt("adult"));
				ovo.setSc_child(rs.getInt("sc_child"));
				ovo.setPs_child(rs.getInt("ps_child"));
				ovo.setSide(rs.getInt("side"));
				
				//��������
				ovo.setB_name(rs.getString("b_name"));
				ovo.setB_date(rs.getString("b_date"));
				ovo.setB_time(rs.getString("b_time"));
				ovo.setB_phone(rs.getString("b_phone"));
				
				//��������
				ovo.setPay(rs.getString("pay"));
				ovo.setE_name(rs.getString("e_name"));
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
		
		return ovo;
		
	}
	
	
	//�����Ϸ�,�̿Ϸ� �� ��ȸ
	public OrderVO getPaySearch(String pay)throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select bo_no,c_section,table_no,total,total_price,adult,sc_child,ps_child,side,");
		sql.append("b_name,b_date,b_time,b_phone,pay,");
		sql.append("e_name from book_order where pay like ");
		sql.append("? order by bo_no desc");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderVO ovo = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+pay+"%");
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				ovo = new OrderVO();
				
				ovo.setBo_no(rs.getInt("bo_no"));
				//�ֹ�����
				ovo.setC_section(rs.getString("c_section"));
				ovo.setTable_no(rs.getString("table_no"));
				ovo.setTotal(rs.getInt("total"));
				ovo.setTotal_price(rs.getInt("total_price"));
				ovo.setAdult(rs.getInt("adult"));
				ovo.setSc_child(rs.getInt("sc_child"));
				ovo.setPs_child(rs.getInt("ps_child"));
				ovo.setSide(rs.getInt("side"));
				
				//��������
				ovo.setB_name(rs.getString("b_name"));
				ovo.setB_date(rs.getString("b_date"));
				ovo.setB_time(rs.getString("b_time"));
				ovo.setB_phone(rs.getString("b_phone"));
				
				//��������
				ovo.setPay(rs.getString("pay"));
				ovo.setE_name(rs.getString("e_name"));
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
		
		return ovo;
		
	}
	
	
	//������Ȳ ��ȸ
	public OrderVO getBookTotal(String b_date) throws Exception{
		ArrayList<OrderVO> list = new ArrayList<OrderVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select bo_no,b_date,b_time,adult,sc_child,ps_child,total ");
		sql.append("from book_order where pay like 'N' and c_section like ");
		sql.append("'����' and b_date like ? order by bo_no desc");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderVO ovo = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+b_date+"%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				ovo = new OrderVO();
				
				ovo.setBo_no(rs.getInt("bo_no"));
				ovo.setB_date(rs.getString("b_date"));
				ovo.setB_time(rs.getString("b_time"));
				ovo.setAdult(rs.getInt("adult"));
				ovo.setSc_child(rs.getInt("sc_child"));
				ovo.setPs_child(rs.getInt("ps_child"));
				ovo.setTotal(rs.getInt("total"));
				
				list.add(ovo);
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
		
		return ovo;
	}
	
	//�ֹ� ����
	public void getOrderDelete(int no) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("delete from book_order where bo_no = ?");
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBUtil.getConnection();
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, no);
			
			int i = pstmt.executeUpdate();
			
			if(i==1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("����");
				alert.setHeaderText(null);
				alert.setContentText("���� �Ǿ����ϴ�.");
				alert.showAndWait();
			}else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("����");
				alert.setHeaderText(null);
				alert.setContentText("���� ����!");
				alert.showAndWait();
			}
		}catch(SQLException e) {
			System.out.println("e=["+e+"]");
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
		
	}

}
