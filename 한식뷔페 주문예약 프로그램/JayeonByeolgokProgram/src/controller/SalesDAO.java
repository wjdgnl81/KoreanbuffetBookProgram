package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import model.OrderVO;
import model.SalesVO;


public class SalesDAO {

	//날짜별 매출
	public ArrayList<SalesVO> getDateSales(){
		ArrayList<SalesVO> list = new ArrayList<SalesVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pay_date, sum(total) as total_count, sum(total_price) as day_sales ");
		sql.append("from book_order where pay_date is not null group by pay_date order by pay_date desc");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SalesVO svo = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs= pstmt.executeQuery();
			while(rs.next()) {
				svo = new SalesVO();
				svo.setPay_date(rs.getString("pay_date"));
				svo.setTotal_count(rs.getInt("total_count"));
				svo.setDay_sales(rs.getInt("day_sales"));
				
				list.add(svo);
			}
		}catch(SQLException se) {
			System.out.println(se);
		}catch(Exception e) {
			System.err.println(e);
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
	
	//날짜별 매출 칼럼 갯수
	public ArrayList<String> getDateColumnName(){
		ArrayList<String> columnName = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pay_date, count(total) as total_count, sum(total_price) as day_sales ");
		sql.append("from book_order where pay_date is not null group by pay_date order by pay_date");
		
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
	
	//날짜검색
	public SalesVO getSaleDateSearch(String pay_date)throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select pay_date, count(total) as total_count, sum(total_price) as day_sales ");
		sql.append("from book_order where pay_date is not null and pay_date like ? group by pay_date order by pay_date");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SalesVO svo = null;
		
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+pay_date+"%");
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				svo = new SalesVO();
				svo.setPay_date(rs.getString("pay_date"));
				svo.setTotal_count(rs.getInt("total_count"));
				svo.setDay_sales(rs.getInt("day_sales"));
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
		
		return svo;
		
	}
	
	//직원별 매출
	public ArrayList<SalesVO> getEmployeeSales(){
		ArrayList<SalesVO> list = new ArrayList<SalesVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select e_name, count(e_name) as e_count, sum(total_price) as e_sales ");
		sql.append("from book_order where pay = 'Y' group by e_name order by e_name");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SalesVO svo = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs= pstmt.executeQuery();
			while(rs.next()) {
				svo = new SalesVO();
				svo.setE_name(rs.getString("e_name"));
				svo.setE_count(rs.getInt("e_count"));
				svo.setE_sales(rs.getInt("e_sales"));
				
				list.add(svo);
			}
	}catch(SQLException se) {
		System.out.println(se);
	}catch(Exception e) {
		System.err.println(e);
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
	
	//직원별 매출 칼럼수
	public ArrayList<String> getEmployeeColumnName(){
		ArrayList<String> columnName = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select e_name, count(e_name) as e_count, sum(total_price) as e_sales ");
		sql.append("from book_order where pay = 'Y' group by e_name order by e_name");
		
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

	//카드사별 매출
	public ArrayList<SalesVO> getCardSales(){
		ArrayList<SalesVO> list = new ArrayList<SalesVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select card, count(card) c_count, sum(total_price) c_sales ");
		sql.append("from book_order where pay='Y' and card is not null group by card order by card");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SalesVO svo = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs= pstmt.executeQuery();
			while(rs.next()) {
				svo = new SalesVO();
				svo.setCard(rs.getString("card"));
				svo.setC_count(rs.getInt("c_count"));
				svo.setC_sales(rs.getInt("c_sales"));
				
				list.add(svo);
			}
			
		}catch(SQLException se) {
			System.out.println(se);
		}catch(Exception e) {
			System.err.println(e);
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

	//카드사별 매출 칼럼수
	public ArrayList<String> getCardColumnName(){
		ArrayList<String> columnName = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select card, count(card) c_count, sum(total_price) c_sales ");
		sql.append("from book_order where pay='Y' and card is not null group by card order by card");
		
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
}
