package controller;

import java.awt.Checkbox;
import java.awt.Dialog;
import java.awt.Label;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.naming.directory.InvalidSearchFilterException;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.LocalDateStringConverter;
import javafx.scene.control.Alert.AlertType;
import model.EmployeeVO;
import model.JoinVO;
import model.OrderVO;
import model.SalesVO;

public class MainControllerManager implements Initializable {

	//주문 정보
	@FXML
	private TextField txtAdult;
	@FXML
	private ToggleGroup adultGroup;
	@FXML
	private RadioButton rbLAdult;
	@FXML
	private RadioButton rbDAdult;
	@FXML
	private TextField txtScChild;
	@FXML
	private TextField txtPsChild;
	@FXML
	private TextField txtSide;
	@FXML
	private Button btnOrderOk;
	@FXML
	private TextField txtTotal;
	@FXML
	private TextField txtDayTotalPrice;
	
	
	//테이블정보
	@FXML
	private ComboBox<String> cbTableNo;
	@FXML
	private TextField txtEname;
	@FXML
	private ToggleGroup customerGroup;
	@FXML
	private RadioButton rbVisit;
	@FXML
	private RadioButton rbBook;
	@FXML
	private Button btnTableOk;
	
	//예약정보
	@FXML
	private TextField txtBname;
	@FXML
	private TextField txtBphone;
	@FXML
	private DatePicker dpBday;
	@FXML
	private ComboBox<String> cbHour;
	@FXML
	private ComboBox<String> cbMinute;
	@FXML
	private Button btnBookOk;
	
	//주문등록
	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancle;
	@FXML
	private Button btnDelete;
	
	//결제
	@FXML
	private Button btnPayCard;
	@FXML
	private Button btnPayCash;
	
	//검색
	@FXML
	private Button btnShow;
	@FXML
	private TextField txtSearch;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnVsearch;
	@FXML
	private Button btnBsearch;
	@FXML
	private ToggleGroup payGroup;
	@FXML
	private RadioButton rbPayY;
	@FXML
	private RadioButton rbPayN;
	
	//예약현황
	/*@FXML
	private Button btnSaveDirBook;
	@FXML
	private TextField txtSaveDirBook;
	@FXML
	private Button btnSavePDFBook;*/
	@FXML
	private Button btnBarChart;
	
	@FXML
	private Tab tabSecond;
	
	//매출현황
	@FXML
	private Tab tabThird;
	@FXML
	private TextField txtTotalPrice;//오늘매출
	@FXML
	private Button btnOrder;//주문예약
	@FXML
	private Button btnLogOut;//로그아웃
	@FXML
	private Button btnExit;//종료
	@FXML
	private Button btnSaveDirSales;
	@FXML
	private TextField txtSaveDirSales;
	@FXML
	private Button btnSaveExcelSales;
	@FXML
	private Button btnSavePDFSales;
	
	//날짜별 매출
	@FXML
	private TableView<SalesVO> tableViewDay;//날짜별 테이블뷰
	@FXML
	private Button btnMonth;//월별차트
	@FXML
	private Button btnQuarter;//분기별차트
	@FXML
	private DatePicker dpSaleDate;//날짜선택
	@FXML
	private Button btnSaleDateSearch;//날짜검색
	@FXML
	private Button btnShowSaleDate;//전체검색
	
	//직원별매출
	@FXML
	private TableView<SalesVO> tableViewEmployee;//직원별 매출 테이블뷰
	@FXML
	private Button btnEmployee;//직원관리
	
	//카드사별매출
	@FXML
	private TableView<SalesVO> tableViewCard;//카드사별 매출 테이블뷰
	
	//테이블뷰
	@FXML
	private TableView<OrderVO> tableViewMain; //메인
	@FXML
	private TableView<OrderVO> tableViewBook; //예약 현황
	
	OrderVO orderVo = new OrderVO();
	ObservableList<OrderVO> data = FXCollections.observableArrayList();
	ObservableList<OrderVO> selectOrder;
	ObservableList<OrderVO> bookData = FXCollections.observableArrayList();
	
	SalesVO svo = new SalesVO();
	ObservableList<SalesVO> dayData = FXCollections.observableArrayList();
	ObservableList<SalesVO> EmployeeData = FXCollections.observableArrayList();
	ObservableList<SalesVO> CardData = FXCollections.observableArrayList();
	
	private Stage primaryStage;
	
	final int AL = 13900;//성인점심 가격
	final int AD = 19900;//성인저녁 가격
	final int SC = 9900;//아동 가격
	final int PS = 6500;//유아 가격
	final int SD = 3900;//사이드가격
	
	int no; //삭제시 테이블에서 선택한 주문번호 저장
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		//예약정보 입력란 막기
		txtBname.setDisable(true);
		txtBphone.setDisable(true);
		dpBday.setDisable(true);
		cbHour.setDisable(true);
		cbMinute.setDisable(true);
		btnBookOk.setDisable(true);
		
		//주문정보 입력란막기
		rbLAdult.setDisable(true);
		rbDAdult.setDisable(true);
		txtAdult.setDisable(true);
		txtScChild.setDisable(true);
		txtPsChild.setDisable(true);
		txtSide.setDisable(true);
		btnOrderOk.setDisable(true);
		
		//총 인원수, 가격 입력, 나머지 버튼 막기
		txtTotal.setEditable(false);
		txtTotalPrice.setEditable(false);
		btnOk.setDisable(true);
		btnDelete.setDisable(true);
		btnPayCard.setDisable(true);
		btnPayCash.setDisable(true);
		
		btnSaveExcelSales.setDisable(true);
		btnSavePDFSales.setDisable(true);
		txtSaveDirSales.setEditable(true);
		
		//성인가격 처음 선택은 점심값으로
		adultGroup.selectToggle(rbLAdult);
		
		//날짜 디폴트값
		dpBday.setValue(LocalDate.of(1000, 01, 01));
		
		//테이블번호 콤보박스
		cbTableNo.setItems(FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10",
				"11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27",
				"28","29","30"));
		//시간 콤보박스
		cbHour.setItems(FXCollections.observableArrayList("10","11","12","13","14","15","16","17","18",
				"19","20","21"));
		//분 콤보박스
		cbMinute.setItems(FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08",
				"09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26",
				"27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44",
				"45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"));
		
		//시간 디폴트값
		cbHour.getSelectionModel().select("-");
		cbMinute.getSelectionModel().select("-");
		
		//숫자만 입력, 2자 제한
		//성인
		DecimalFormat format = new DecimalFormat("##");
		txtAdult.setTextFormatter(new TextFormatter<>(event->{
			if(event.getControlNewText().isEmpty()) {
				return event;
			}
			
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(),parsePosition);
			
			if(object == null || parsePosition.getIndex()<event.getControlNewText().length()
					||event.getControlNewText().length()==3) {
				return null;
			}else {
				return event;
			}
			
		}));
		
		//아동
		txtScChild.setTextFormatter(new TextFormatter<>(event->{
			if(event.getControlNewText().isEmpty()) {
				return event;
			}
			
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(),parsePosition);
			
			if(object == null || parsePosition.getIndex()<event.getControlNewText().length()
					||event.getControlNewText().length()==3) {
				return null;
			}else {
				return event;
			}
			
		}));
		
		//유아
		txtPsChild.setTextFormatter(new TextFormatter<>(event->{
			if(event.getControlNewText().isEmpty()) {
				return event;
			}
			
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(),parsePosition);
			
			if(object == null || parsePosition.getIndex()<event.getControlNewText().length()
					||event.getControlNewText().length()==3) {
				return null;
			}else {
				return event;
			}
			
		}));
		
		//사이드
		txtSide.setTextFormatter(new TextFormatter<>(event->{
			if(event.getControlNewText().isEmpty()) {
				return event;
			}
			
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(),parsePosition);
			
			if(object == null || parsePosition.getIndex()<event.getControlNewText().length()
					||event.getControlNewText().length()==3) {
				return null;
			}else {
				return event;
			}
			
		}));
		
		//주문 테이블뷰
		tableViewMain.setEditable(false);
		
		TableColumn colNo = new TableColumn("NO.");
		colNo.setMaxWidth(40);
		colNo.setStyle("-fx-alignment: CENTER");
		colNo.setCellValueFactory(new PropertyValueFactory<>("bo_no"));
		
		TableColumn colSec = new TableColumn("방문/예약");
		colSec.setMaxWidth(60);
		colSec.setStyle("-fx-alignment: CENTER");
		colSec.setCellValueFactory(new PropertyValueFactory<>("c_section"));
		
		TableColumn colTable = new TableColumn("테이블");
		colTable.setMaxWidth(50);
		colTable.setStyle("-fx-alignment: CENTER");
		colTable.setCellValueFactory(new PropertyValueFactory<>("table_no"));
		
		TableColumn colTotal = new TableColumn("총인원");
		colTotal.setMaxWidth(50);
		colTotal.setStyle("-fx-alignment: CENTER");
		colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		
		TableColumn colTotalPrice = new TableColumn("총가격");
		colTotalPrice.setMaxWidth(80);
		colTotalPrice.setStyle("-fx-alignment: CENTER");
		colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));
		
		TableColumn colAdult = new TableColumn("성인");
		colAdult.setMaxWidth(40);
		colAdult.setStyle("-fx-alignment: CENTER");
		colAdult.setCellValueFactory(new PropertyValueFactory<>("adult"));
		
		TableColumn colScChild = new TableColumn("아동");
		colScChild.setMaxWidth(40);
		colScChild.setStyle("-fx-alignment: CENTER");
		colScChild.setCellValueFactory(new PropertyValueFactory<>("sc_child"));
		
		TableColumn colPsChild = new TableColumn("유아");
		colPsChild.setMaxWidth(40);
		colPsChild.setStyle("-fx-alignment: CENTER");
		colPsChild.setCellValueFactory(new PropertyValueFactory<>("ps_child"));
		
		TableColumn colSide = new TableColumn("사이드");
		colSide.setMaxWidth(50);
		colSide.setStyle("-fx-alignment: CENTER");
		colSide.setCellValueFactory(new PropertyValueFactory<>("side"));
		
		TableColumn colBName = new TableColumn("예약자");
		colBName.setMaxWidth(70);
		colBName.setStyle("-fx-alignment: CENTER");
		colBName.setCellValueFactory(new PropertyValueFactory<>("b_name"));
		
		TableColumn colBDay = new TableColumn("예약일");
		colBDay.setMinWidth(130);
		colBDay.setStyle("-fx-alignment: CENTER");
		colBDay.setCellValueFactory(new PropertyValueFactory<>("b_date"));
		
		TableColumn colBTime = new TableColumn("예약시간");
		colBTime.setMaxWidth(70);
		colBTime.setStyle("-fx-alignment: CENTER");
		colBTime.setCellValueFactory(new PropertyValueFactory<>("b_time"));
		
		TableColumn colBPhone = new TableColumn("연락처");
		colBPhone.setMinWidth(110);
		colBPhone.setStyle("-fx-alignment: CENTER");
		colBPhone.setCellValueFactory(new PropertyValueFactory<>("b_phone"));
		
		TableColumn colPay = new TableColumn("결제완료");
		colPay.setMaxWidth(60);
		colPay.setStyle("-fx-alignment: CENTER");
		colPay.setCellValueFactory(new PropertyValueFactory<>("pay"));
		
		TableColumn colEName = new TableColumn("담당자");
		colEName.setMaxWidth(60);
		colEName.setStyle("-fx-alignment: CENTER");
		colEName.setCellValueFactory(new PropertyValueFactory<>("e_name"));
		
		tableViewMain.setItems(data);
		tableViewMain.getColumns().addAll(colNo,colSec,colTable,colTotal,colTotalPrice,colAdult,
				colScChild,colPsChild,colSide,colBName,colBDay,colBTime,colBPhone,colPay,colEName);
		
		totalList();
		
		
		//예약 테이블뷰
		tableViewBook.setEditable(false);
		
		TableColumn colBNo = new TableColumn("NO.");
		colBNo.setMaxWidth(40);
		colBNo.setStyle("-fx-alignment: CENTER");
		colBNo.setCellValueFactory(new PropertyValueFactory<>("bo_no"));
		
		TableColumn colBbDay = new TableColumn("날짜");
		colBbDay.setMinWidth(120);
		colBbDay.setStyle("-fx-alignment: CENTER");
		colBbDay.setCellValueFactory(new PropertyValueFactory<>("b_date"));
		
		TableColumn colBbTime = new TableColumn("시간");
		colBbTime.setMaxWidth(70);
		colBbTime.setStyle("-fx-alignment: CENTER");
		colBbTime.setCellValueFactory(new PropertyValueFactory<>("b_time"));
		
		TableColumn colBAdult = new TableColumn("성인");
		colBAdult.setMaxWidth(40);
		colBAdult.setStyle("-fx-alignment: CENTER");
		colBAdult.setCellValueFactory(new PropertyValueFactory<>("adult"));
		
		TableColumn colBScChild = new TableColumn("아동");
		colBScChild.setMaxWidth(40);
		colBScChild.setStyle("-fx-alignment: CENTER");
		colBScChild.setCellValueFactory(new PropertyValueFactory<>("sc_child"));
		
		TableColumn colBPsChild = new TableColumn("유아");
		colBPsChild.setMaxWidth(40);
		colBPsChild.setStyle("-fx-alignment: CENTER");
		colBPsChild.setCellValueFactory(new PropertyValueFactory<>("ps_child"));
		
		TableColumn colBTotal = new TableColumn("총인원");
		colBTotal.setMaxWidth(40);
		colBTotal.setStyle("-fx-alignment: CENTER");
		colBTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		
		tableViewBook.setItems(bookData);
		tableViewBook.getColumns().addAll(colBNo,colBbDay,colBbTime,colBAdult,colBScChild,colBPsChild,colBTotal);
		
		tabSecond.setOnSelectionChanged(new EventHandler<Event>() {
			
			@Override
			public void handle(Event event) {
				BookList();
			}
		});
		
		
		//날짜별매출
		tableViewDay.setEditable(false);
				
		TableColumn colDay = new TableColumn("날짜");
		colDay.setMinWidth(110);
		colDay.setStyle("-fx-alignment: CENTER");
		colDay.setCellValueFactory(new PropertyValueFactory<>("pay_date"));
				
		TableColumn colTotalCount = new TableColumn("방문 인원");
		colTotalCount.setMaxWidth(70);
		colTotalCount.setStyle("-fx-alignment: CENTER");
		colTotalCount.setCellValueFactory(new PropertyValueFactory<>("total_count"));
				
		TableColumn colDayTotalPrice = new TableColumn("매출");
		colDayTotalPrice.setMinWidth(90);
		colDayTotalPrice.setStyle("-fx-alignment: CENTER");
		colDayTotalPrice.setCellValueFactory(new PropertyValueFactory<>("day_sales"));
		
		tableViewDay.setItems(dayData);
		tableViewDay.getColumns().addAll(colDay,colTotalCount,colDayTotalPrice);
		
				
				
		//직원별 매출 테이블 뷰
		tableViewEmployee.setEditable(false);
				
		TableColumn colEname = new TableColumn("직원");
		colEname.setMinWidth(70);
		colEname.setStyle("-fx-alignment: CENTER");
		colEname.setCellValueFactory(new PropertyValueFactory<>("e_name"));
				
		TableColumn colECount = new TableColumn("건수");
		colECount.setMinWidth(60);
		colECount.setStyle("-fx-alignment: CENTER");
		colECount.setCellValueFactory(new PropertyValueFactory<>("e_count"));
				
		TableColumn colESales = new TableColumn("총 매출");
		colESales.setMinWidth(110);
		colESales.setStyle("-fx-alignment: CENTER");
		colESales.setCellValueFactory(new PropertyValueFactory<>("e_sales"));
				
		tableViewEmployee.setItems(EmployeeData);
		tableViewEmployee.getColumns().addAll(colEname,colECount,colESales);
				

				
		//카드사별 매출 테이블 뷰
		tableViewCard.setEditable(false);
				
		TableColumn colCard = new TableColumn("카드사");
		colCard.setMinWidth(90);
		colCard.setStyle("-fx-alignment: CENTER");
		colCard.setCellValueFactory(new PropertyValueFactory<>("card"));
				
		TableColumn colCCount = new TableColumn("건수");
		colCCount.setMinWidth(60);
		colCCount.setStyle("-fx-alignment: CENTER");
		colCCount.setCellValueFactory(new PropertyValueFactory<>("c_count"));
				
		TableColumn colCSales = new TableColumn("총 매출");
		colCSales.setMinWidth(110);
		colCSales.setStyle("-fx-alignment: CENTER");
		colCSales.setCellValueFactory(new PropertyValueFactory<>("c_sales"));
				
		tableViewCard.setItems(CardData);
		tableViewCard.getColumns().addAll(colCard,colCCount,colCSales);
		
		
		tabThird.setOnSelectionChanged(new EventHandler<Event>() {
			
			@Override
			public void handle(Event event) {
				txtSaveDirSales.clear();
				btnSaveExcelSales.setDisable(true);
				btnSavePDFSales.setDisable(true);
				
				dayData.removeAll(dayData);
				DaySalesList();
				EmployeeData.removeAll(EmployeeData);
				EmployeeSalesList();
				CardData.removeAll(CardData);
				CardSalesList();
				
				//오늘매출 표시
				txtDayTotalPrice.setEditable(false);
							
				DecimalFormat df = new DecimalFormat("###,###");
						
				for(int i = 0; i<dayData.size();i++) {
					if(dayData.get(i).getPay_date().equals(LocalDate.now().toString())) {
						String format1 = df.format(dayData.get(i).getDay_sales());
						txtDayTotalPrice.setText("￦ "+format1+"");
					}else if(txtDayTotalPrice.getText().equals("")) {
						txtDayTotalPrice.setText(0+"");
					}
				}
			}
		});
		
		
		//버튼 액션
		btnTableOk.setOnAction(event->handlerBtnTableOkAction(event));//테이블정보 등록
		btnBookOk.setOnAction(event->handlerBtnBookOkAction(event));//예약정보 등록
		btnOrderOk.setOnAction(event->handlerBtnOrderOkAction(event));//주문정보 등록
		btnOk.setOnAction(event->handlerBtnOkAction(event));//등록
		btnCancle.setOnAction(event->handlerBtnCancleAction(event));//취소(초기화)
		btnDelete.setOnAction(event->handlerBtnDeleteAction(event));//삭제
		tableViewMain.setOnMouseClicked(event->handlerClickAction(event));//주문 선택
		
		btnPayCash.setOnAction(event->handlerBtnPayCash(event));//현금결제
		btnPayCard.setOnAction(event->handlerBtnPayCard(event));//카드결제
		
		btnShow.setOnAction(event->handlerBtnShowAction(event));//전체목록
		btnSearch.setOnAction(event->handlerBtnSearchAction(event));//검색
		rbPayY.setOnAction(event->handlerBtnRbPayY(event));//결제완료고객 조회
		rbPayN.setOnAction(event->handlerBtnRbPayN(event));//결제미완료고객 조회
		btnVsearch.setOnAction(event->handlerBtnVsearchAction(event));//방문고객 조회
		btnBsearch.setOnAction(event->handlerBtnBsearchAction(event));//예약고객 조회
		
		btnExit.setOnAction(event->handlerBtnExitAction(event));//종료
		btnLogOut.setOnAction(event->handlerBtnLogOutAction(event));//로그아웃
		
		btnBarChart.setOnAction(event->handlerBtnBarChart(event));//시간대 바차트
		
		//매출탭 버튼액션
		btnSaleDateSearch.setOnAction(event->handlerBtnSaleDateSearchAction(event));//날짜검색
		btnShowSaleDate.setOnAction(event->handlerBtnShowSaleDateAction(event));//전체목록 보기
		btnMonth.setOnAction(event->handlerBtnMonthAction(event));//월별매출 바차트
		btnQuarter.setOnAction(event->handlerBtnQuarterAction(event));//분기별매출 바차트
		btnEmployee.setOnAction(event->handlerBtnEmployeeAction(event));//직원관리
		
		btnSaveDirSales.setOnAction(event->handlerBtnSaveDirSalesAction(event));//매출 파일저장폴더
		btnSaveExcelSales.setOnAction(event->handlerExcelSalesAction(event));//매출 엑셀파일 생성
		btnSavePDFSales.setOnAction(event->handlerPDFSalesAction(event));//매출 PDF파일 생성
	}





	//매출 파일저장폴더 선택
	private void handlerBtnSaveDirSalesAction(ActionEvent event) {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		final File selectedDirectory = directoryChooser.showDialog(primaryStage);
		
		if(selectedDirectory != null) {
			txtSaveDirSales.setText(selectedDirectory.getAbsolutePath());
			btnSaveExcelSales.setDisable(false);
			btnSavePDFSales.setDisable(false);
		}
	}
	
	
	//매출 엑셀파일 생성
	private void handlerExcelSalesAction(ActionEvent event) {

		SalesDAO sdao = new SalesDAO();
		boolean saveSuccess;
		
		ArrayList<SalesVO> list;
		list = sdao.getDateSales();
		SalesExcel excelWriter = new SalesExcel();
		
		//xlsx 파일 쓰기
		saveSuccess = excelWriter.xlsxWriter(list, txtSaveDirSales.getText());
		if(saveSuccess) {
			Alert alert = new Alert (AlertType.INFORMATION);
			alert.setTitle("엑셀 파일 생성");
			alert.setHeaderText(null);
			alert.setContentText("날짜별 매출 엑셀 파일 생성 성공");
			alert.showAndWait();
		}
		txtSaveDirSales.clear();
		btnSaveExcelSales.setDisable(true);
		btnSavePDFSales.setDisable(true);
		
	}


	//매출 pdf파일 생성
	private void handlerPDFSalesAction(ActionEvent event) {
		try {
			
		FXMLLoader loaderPdf = new FXMLLoader();
		loaderPdf.setLocation(getClass().getResource("/view/pdfImage.fxml"));
		
		Stage dialogPdf = new Stage(StageStyle.UTILITY);
		dialogPdf.initModality(Modality.WINDOW_MODAL);
		dialogPdf.initOwner(btnSavePDFSales.getScene().getWindow());
		dialogPdf.setTitle("날짜 별 매출 그래프 이미지 선택");
		
		Parent parentPdf = (Parent) loaderPdf.load();
		
		Button btnPdfSave = (Button) parentPdf.lookup("#btnPdfSave");
		CheckBox cbMonthImage = (CheckBox) parentPdf.lookup("#cbMonthImage");
		CheckBox cbQuqrterImage = (CheckBox) parentPdf.lookup("#cbQuqrterImage");
		
		Scene scene = new Scene(parentPdf);
		dialogPdf.setScene(scene);
		dialogPdf.setResizable(false);
		dialogPdf.show();
		
		btnPdfSave.setOnAction(e->{
			
			try {
			
			
			//pdf document 선언
			Document document = new Document(PageSize.A4, 0, 0, 30, 30);
			
			//pdf 파일 저장할 공간 선언. 파일 생성 후 스트링으로 저장
			String strReportPDFName = "dateSales_"+System.currentTimeMillis()+".pdf";
			PdfWriter.getInstance(document, new FileOutputStream(txtSaveDirSales.getText()+"\\"
					+strReportPDFName));
			//document를 열어 pdf문서를 쓸 수 있도록
			document.open();
			//한글지원폰트 설정
			BaseFont bf = BaseFont.createFont("font/MALGUN.TTF",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
			Font font = new Font(bf,8,Font.NORMAL);
			Font font2 = new Font(bf,14,Font.BOLD);
			//타이틀
			Paragraph title = new Paragraph("날짜 별 매출",font2);
			//중간정렬
			title.setAlignment(Element.ALIGN_CENTER);
			//문서에 추가
			document.add(title);
			document.add(new Paragraph("\r\n"));
			//생성 날짜
			LocalDate date = LocalDate.now();
			Paragraph writeDay = new Paragraph(date.toString(),font);
			//오른쪽 정렬
			writeDay.setAlignment(Element.ALIGN_RIGHT);
			//문서에 추가
			document.add(writeDay);
			document.add(new Paragraph("\r\n"));
			
			//테이블 생성
			//컬럼 수
			PdfPTable table = new PdfPTable(3);
			//컬럼 크기
			table.setWidths(new int[] {110, 70, 90});
			//컬럼 타이틀
			PdfPCell header1 = new PdfPCell(new Paragraph("날짜",font));
			PdfPCell header2 = new PdfPCell(new Paragraph("방문 인원",font));
			PdfPCell header3 = new PdfPCell(new Paragraph("매출",font));
	
			//가로정렬
			header1.setHorizontalAlignment(Element.ALIGN_CENTER);
			header2.setHorizontalAlignment(Element.ALIGN_CENTER);
			header3.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			//세로정렬
			header1.setVerticalAlignment(Element.ALIGN_CENTER);
			header2.setVerticalAlignment(Element.ALIGN_CENTER);
			header3.setVerticalAlignment(Element.ALIGN_CENTER);
			
			//테이블에 추가
			table.addCell(header1);
			table.addCell(header2);
			table.addCell(header3);
			
			//DB 연결 및 리스트 선택
			SalesDAO sdao = new SalesDAO();
			SalesVO svo = new SalesVO();
			ArrayList<SalesVO> list;
			list = sdao.getDateSales();
			int rowCount = list.size();
			
			PdfPCell cell1 = null;
			PdfPCell cell2 = null;
			PdfPCell cell3 = null;
			
			for(int index=0; index<rowCount; index++) {
				svo = list.get(index);
				
				cell1 = new PdfPCell(new Paragraph(svo.getPay_date(),font));
				cell2 = new PdfPCell(new Paragraph(svo.getTotal_count()+"",font));
				cell3 = new PdfPCell(new Paragraph(svo.getDay_sales()+"",font));
				
				//가로정렬
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				//세로정렬
				cell1.setVerticalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_CENTER);
				
				//테이블에 셀 추가
				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
			}
			
			//문서에 테이블 추가
			document.add(table);
			document.add(new Paragraph("\r\n"));
			Alert alert = new Alert(AlertType.INFORMATION);
			
			
			if(cbMonthImage.isSelected()&&cbQuqrterImage.isSelected()) {
				
				//월 매출 그래프 이미지 추가
				Paragraph barImageTitle = new Paragraph("2018년 월 매출 막대 그래프",font);
				barImageTitle.setAlignment(Element.ALIGN_CENTER);
				document.add(barImageTitle);
				document.add(new Paragraph("\r\n"));
				final String barImageUrl = "chartImage/monthSalesBarChart.png";
				com.itextpdf.text.Image barImage;
				try {
					if(com.itextpdf.text.Image.getInstance(barImageUrl)!=null) {
						barImage = com.itextpdf.text.Image.getInstance(barImageUrl);
						barImage.setAlignment(Element.ALIGN_CENTER);
						barImage.scalePercent(30f);
						document.add(barImage);
						document.add(new Paragraph("\r\n"));
					}
				}catch(IOException ee) {
				}
			
				//분기별 매출 그래프 이미지 추가
				Paragraph barImageTitle2 = new Paragraph("2018년 분기별 매출 막대 그래프",font);
				barImageTitle2.setAlignment(Element.ALIGN_CENTER);
				document.add(barImageTitle2);
				document.add(new Paragraph("\r\n"));
				final String barImageUrl2 = "chartImage/QuarterSalesBarChart.png";
				com.itextpdf.text.Image barImage2;
				try {
					if(com.itextpdf.text.Image.getInstance(barImageUrl2)!=null) {
						barImage2 = com.itextpdf.text.Image.getInstance(barImageUrl2);
						barImage2.setAlignment(Element.ALIGN_CENTER);
						barImage2.scalePercent(30f);
						document.add(barImage2);
						document.add(new Paragraph("\r\n"));
					}
				}catch(IOException ee) {
				}
			
			}else if(cbMonthImage.isSelected()) {
				
				//월 매출 그래프 이미지 추가
				Paragraph barImageTitle = new Paragraph("2018년 월 매출 막대 그래프",font);
				barImageTitle.setAlignment(Element.ALIGN_CENTER);
				document.add(barImageTitle);
				document.add(new Paragraph("\r\n"));
				final String barImageUrl = "chartImage/monthSalesBarChart.png";
				com.itextpdf.text.Image barImage;
				try {
					if(com.itextpdf.text.Image.getInstance(barImageUrl)!=null) {
						barImage = com.itextpdf.text.Image.getInstance(barImageUrl);
						barImage.setAlignment(Element.ALIGN_CENTER);
						barImage.scalePercent(30f);
						document.add(barImage);
						document.add(new Paragraph("\r\n"));
					}
				}catch(IOException ee) {
				}
				
			}else if(cbQuqrterImage.isSelected()) {
				
				//분기별 매출 그래프 이미지 추가
				Paragraph barImageTitle2 = new Paragraph("2018년 분기별 매출 막대 그래프",font);
				barImageTitle2.setAlignment(Element.ALIGN_CENTER);
				document.add(barImageTitle2);
				document.add(new Paragraph("\r\n"));
				final String barImageUrl2 = "chartImage/QuarterSalesBarChart.png";
				com.itextpdf.text.Image barImage2;
				try {
					if(com.itextpdf.text.Image.getInstance(barImageUrl2)!=null) {
						barImage2 = com.itextpdf.text.Image.getInstance(barImageUrl2);
						barImage2.setAlignment(Element.ALIGN_CENTER);
						barImage2.scalePercent(30f);
						document.add(barImage2);
						document.add(new Paragraph("\r\n"));
					}
				}catch(IOException ee) {
				}
			}
			
			
			//문서 닫기
			document.close();
			
			dialogPdf.close();
			txtSaveDirSales.clear();
			btnSavePDFSales.setDisable(true);
			btnSaveExcelSales.setDisable(true);
			
			alert.setTitle("PDF 파일 생성");
			alert.setHeaderText(null);
			alert.setContentText("날짜별 매출 PDF 파일 생성 성공");
			alert.showAndWait();
		}catch(FileNotFoundException e1) {
			e1.printStackTrace();
		}catch(DocumentException e1) {
			e1.printStackTrace();
		}catch(IOException e1) {
			e1.printStackTrace();
		}
			
		});
		
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	
	}
	
	
	//---------탭3---------


	//직원관리 창 열기
	private void handlerBtnEmployeeAction(ActionEvent event) {
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Employee.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scene = new Scene(mainView);
				Stage mainStage = new Stage();
				mainStage.setTitle("직원 관리");
				mainStage.setScene(scene);
				mainStage.initModality(Modality.WINDOW_MODAL);
				mainStage.initOwner(btnEmployee.getScene().getWindow());
				mainStage.setResizable(false);
				mainStage.show();
			} catch (IOException e) {
				System.err.println("오류" + e);
			}
			
		}
		
		
	//분기별 바차트
	private void handlerBtnQuarterAction(ActionEvent event) {
			
			int one = 0;
			int two = 0;
			int three = 0;
			int four = 0;
			
			try {
				Stage dialog = new Stage(StageStyle.UTILITY);
				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.initOwner(btnMonth.getScene().getWindow());
				dialog.setTitle("분기 별 매출");
				
				Parent parent = FXMLLoader.load(getClass().getResource("/view/QuarterBarChart.fxml"));
				
				BarChart barChart = (BarChart) parent.lookup("#barChartQuarter");
				
				XYChart.Series series2018 = new XYChart.Series();
				series2018.setName("2018년 분기별 매출");
			
			for(int i = 0; i<dayData.size();i++) {
				if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("01")
						||dayData.get(i).getPay_date().toString().substring(5, 7).equals("02")
						||dayData.get(i).getPay_date().toString().substring(5, 7).equals("03")) {
					one += dayData.get(i).getDay_sales();
				}else if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("04")
						||dayData.get(i).getPay_date().toString().substring(5, 7).equals("05")
						||dayData.get(i).getPay_date().toString().substring(5, 7).equals("06")) {
					two += dayData.get(i).getDay_sales();
				}else if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("07")
						||dayData.get(i).getPay_date().toString().substring(5, 7).equals("08")
						||dayData.get(i).getPay_date().toString().substring(5, 7).equals("09")) {
					three += dayData.get(i).getDay_sales();
				}else if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("10")
						||dayData.get(i).getPay_date().toString().substring(5, 7).equals("11")
						||dayData.get(i).getPay_date().toString().substring(5, 7).equals("12")) {
					four += dayData.get(i).getDay_sales();
				}
			}
			
			
			series2018.getData().add(new XYChart.Data("1분기",one));
			series2018.getData().add(new XYChart.Data("2분기",two));
			series2018.getData().add(new XYChart.Data("3분기",three));
			series2018.getData().add(new XYChart.Data("4분기",four));
			
			barChart.getData().add(series2018);
			
			Button btnClose = (Button) parent.lookup("#btnClose");
			btnClose.setOnAction(e->dialog.close());
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
			//막대 그래프 이미지 저장
			WritableImage snapShot = scene.snapshot(null);
			ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png",
					new File("chartImage/QuarterSalesBarChart.png"));
			
			}catch(IOException e) {
				
			}
			
		}
		
		
	//월별 매출 차트
	private void handlerBtnMonthAction(ActionEvent event) {
			
			int salesJan = 0;
			int salesFeb = 0;
			int salesMar = 0;
			int salesApr = 0;
			int salesMay = 0;
			int salesJun = 0;
			int salesJul = 0;
			int salesAug = 0;
			int salesSep = 0;
			int salesOct = 0;
			int salesNov = 0;
			int salesDec = 0;
			
			try {
				Stage dialog = new Stage(StageStyle.UTILITY);
				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.initOwner(btnMonth.getScene().getWindow());
				dialog.setTitle("월 별 매출");
				
				Parent parent = FXMLLoader.load(getClass().getResource("/view/MonthBarChart.fxml"));
				
				BarChart barChart = (BarChart) parent.lookup("#barChartMonth");
				
				XYChart.Series series2018 = new XYChart.Series();
				series2018.setName("2018년 월 매출");
				for(int i = 0; i<dayData.size();i++) {
					if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("01")) {
						salesJan += dayData.get(i).getDay_sales();
					}else if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("02")) {
						salesFeb += dayData.get(i).getDay_sales();
					}else if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("03")) {
						salesMar += dayData.get(i).getDay_sales();
					}else if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("04")) {
						salesApr += dayData.get(i).getDay_sales();
					}else if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("05")) {
						salesMay += dayData.get(i).getDay_sales();
					}else if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("06")) {
						salesJun += dayData.get(i).getDay_sales();
					}else if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("07")) {
						salesJul += dayData.get(i).getDay_sales();
					}else if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("08")) {
						salesAug += dayData.get(i).getDay_sales();
					}else if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("09")) {
						salesSep += dayData.get(i).getDay_sales();
					}else if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("10")) {
						salesOct += dayData.get(i).getDay_sales();
					}else if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("11")) {
						salesNov += dayData.get(i).getDay_sales();
					}else if(dayData.get(i).getPay_date().toString().substring(5, 7).equals("12")) {
						salesDec += dayData.get(i).getDay_sales();
					}
				}
				
				series2018.getData().add(new XYChart.Data("1월",salesJan));
				series2018.getData().add(new XYChart.Data("2월",salesFeb));
				series2018.getData().add(new XYChart.Data("3월",salesMar));
				series2018.getData().add(new XYChart.Data("4월",salesApr));
				series2018.getData().add(new XYChart.Data("5월",salesMay));
				series2018.getData().add(new XYChart.Data("6월",salesJun));
				series2018.getData().add(new XYChart.Data("7월",salesJul));
				series2018.getData().add(new XYChart.Data("8월",salesAug));
				series2018.getData().add(new XYChart.Data("9월",salesSep));
				series2018.getData().add(new XYChart.Data("10월",salesOct));
				series2018.getData().add(new XYChart.Data("11월",salesNov));
				series2018.getData().add(new XYChart.Data("12월",salesDec));
				
				barChart.getData().add(series2018);
				
				Button btnClose = (Button) parent.lookup("#btnClose");
				btnClose.setOnAction(e->dialog.close());
				
				Scene scene = new Scene(parent);
				dialog.setScene(scene);
				dialog.show();
				
				//막대 그래프 이미지 저장
				WritableImage snapShot = scene.snapshot(null);
				ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png",
						new File("chartImage/monthSalesBarChart.png"));
				
				
			}catch(IOException e) {
				
			}
		}
	
		
	//날짜별매출 전체 보기
	private void handlerBtnShowSaleDateAction(ActionEvent event) {

			try {
				dayData.removeAll(dayData);
				DaySalesList();
			}catch(Exception e) {
				
			}
			
		}
		
		
	//날짜 검색
	private void handlerBtnSaleDateSearchAction(ActionEvent event) {

			SalesVO svo = new SalesVO();
			SalesDAO sdao = null;
			
			Object[][] totalDate = null;
			
			String searchDate ="";
			boolean result=false;
			
			try {
				
				searchDate = dpSaleDate.getValue().toString();
				sdao = new SalesDAO();
				svo = sdao.getSaleDateSearch(searchDate);
				
				if(!searchDate.equals("")&&(svo!=null)) {
					ArrayList<String> title;
					ArrayList<SalesVO> list;
					
					title = sdao.getDateColumnName();
					int columnCount = title.size();
					
					list = sdao.getDateSales();
					int rowCount = list.size();
					
					totalDate = new Object[rowCount][columnCount];
					
					if(svo.getPay_date().equals(searchDate)) {
						dayData.removeAll(dayData);
						for(int index=0;index<rowCount;index++) {
							svo = list.get(index);
							if(svo.getPay_date().equals(searchDate)) {
								dayData.add(svo);
								result=true;
							}
						}
					}
				}
				if(!result) {
					Alert alert = new Alert (AlertType.INFORMATION);
					alert.setTitle("날짜 검색");
					alert.setHeaderText(null);
					alert.setContentText("해당 날짜에 매출이 없습니다.");
					alert.showAndWait();
				}
			}catch(NullPointerException e) {
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("날짜 검색");
				alert.setHeaderText(null);
				alert.setContentText("날짜를 선택해주세요.");
				alert.showAndWait();

			}catch(Exception e) {
				System.out.println(e);
			}
			
		}
		
		
	//날짜별 매출 리스트
	private void DaySalesList() {
			
			Object[][] totalData;
			
			SalesDAO sdao = new SalesDAO();
			SalesVO svo = new SalesVO();
			ArrayList<String> title;
			ArrayList<SalesVO> list;
			
			title = sdao.getDateColumnName();
			int columnCount = title.size();
			
			list = sdao.getDateSales();
			int rowCount = list.size();
			
			
			totalData = new Object[rowCount][columnCount];
			
			for(int index=0; index<rowCount; index++) {
				svo = list.get(index);
				dayData.add(svo);
			}
		}
		
		
	//직원별 매출 리스트
	private void EmployeeSalesList() {
			Object[][] totalData;
			
			SalesDAO sdao = new SalesDAO();
			SalesVO svo = new SalesVO();
			ArrayList<String> title;
			ArrayList<SalesVO> list;
			
			title = sdao.getEmployeeColumnName();
			int columnCount = title.size();
			
			list = sdao.getEmployeeSales();
			int rowCount = list.size();
			
			
			totalData = new Object[rowCount][columnCount];
			
			for(int index=0; index<rowCount; index++) {
				svo = list.get(index);
				EmployeeData.add(svo);
			}
		}

		
	//카드사별 매출 리스트
	private void CardSalesList() {
		Object[][] totalData;
			
			SalesDAO sdao = new SalesDAO();
			SalesVO svo = new SalesVO();
			ArrayList<String> title;
			ArrayList<SalesVO> list;
			
			title = sdao.getCardColumnName();
			int columnCount = title.size();
			
			list = sdao.getCardSales();
			int rowCount = list.size();
			
			
			totalData = new Object[rowCount][columnCount];
			
			for(int index=0; index<rowCount; index++) {
				svo = list.get(index);
				CardData.add(svo);
			}
		}
		
		
		
	
	
	//---------탭1---------
	
	
	//로그아웃 버튼
	private void handlerBtnLogOutAction(ActionEvent event) {

		try {

			Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene(root);
			Stage mainStage = new Stage();
			mainStage.setTitle("로그인");
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			Stage oldStage = (Stage) btnLogOut.getScene().getWindow();
			oldStage.close();
			mainStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	//시간대별 바차트
	private void handlerBtnBarChart(ActionEvent event) {

		int total10 = 0;
		int total11 = 0;
		int total12 = 0;
		int total13 = 0;
		int total14 = 0;
		int total15 = 0;
		int total16 = 0;
		int total17 = 0;
		int total18 = 0;
		int total19 = 0;
		int total20 = 0;
		int total21 = 0;
		
		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnBarChart.getScene().getWindow());
			dialog.setTitle("시간별 인원 수");
			
			Parent parent = FXMLLoader.load(getClass().getResource("/view/barchart.fxml"));
			
			BarChart barChart = (BarChart) parent.lookup("#barChart");
			
			XYChart.Series seriesTime = new XYChart.Series();
			seriesTime.setName("인원 수");
			for(int i = 0; i<data.size();i++) {
				if(data.get(i).getB_time().substring(0, 2).equals("10")
						&&data.get(i).getB_date().toString().equals(LocalDate.now().toString())) {
					total10 += data.get(i).getTotal();
				}else if(data.get(i).getB_time().substring(0, 2).equals("11")
						&&data.get(i).getB_date().toString().equals(LocalDate.now().toString())) {
					total11 += data.get(i).getTotal();
				}else if(data.get(i).getB_time().substring(0, 2).equals("12")
						&&data.get(i).getB_date().toString().equals(LocalDate.now().toString())) {
					total12 += data.get(i).getTotal();
				}else if(data.get(i).getB_time().substring(0, 2).equals("13")
						&&data.get(i).getB_date().toString().equals(LocalDate.now().toString())) {
					total13 += data.get(i).getTotal();
				}else if(data.get(i).getB_time().substring(0, 2).equals("14")
						&&data.get(i).getB_date().toString().equals(LocalDate.now().toString())) {
					total14 += data.get(i).getTotal();
				}else if(data.get(i).getB_time().substring(0, 2).equals("15")
						&&data.get(i).getB_date().toString().equals(LocalDate.now().toString())) {
					total15 += data.get(i).getTotal();
				}else if(data.get(i).getB_time().substring(0, 2).equals("16")
						&&data.get(i).getB_date().toString().equals(LocalDate.now().toString())) {
					total16 += data.get(i).getTotal();
				}else if(data.get(i).getB_time().substring(0, 2).equals("17")
						&&data.get(i).getB_date().toString().equals(LocalDate.now().toString())) {
					total17 += data.get(i).getTotal();
				}else if(data.get(i).getB_time().substring(0, 2).equals("18")
						&&data.get(i).getB_date().toString().equals(LocalDate.now().toString())) {
					total18 += data.get(i).getTotal();
				}else if(data.get(i).getB_time().substring(0, 2).equals("19")
						&&data.get(i).getB_date().toString().equals(LocalDate.now().toString())) {
					total19 += data.get(i).getTotal();
				}else if(data.get(i).getB_time().substring(0, 2).equals("20")
						&&data.get(i).getB_date().toString().equals(LocalDate.now().toString())) {
					total20 += data.get(i).getTotal();
				}else if(data.get(i).getB_time().substring(0, 2).equals("21")
						&&data.get(i).getB_date().toString().equals(LocalDate.now().toString())) {
					total21 += data.get(i).getTotal();
				}
			}
			seriesTime.getData().add(new XYChart.Data("10시",total10));
			seriesTime.getData().add(new XYChart.Data("11시",total11));
			seriesTime.getData().add(new XYChart.Data("12시",total12));
			seriesTime.getData().add(new XYChart.Data("1시",total13));
			seriesTime.getData().add(new XYChart.Data("2시",total14));
			seriesTime.getData().add(new XYChart.Data("3시",total15));
			seriesTime.getData().add(new XYChart.Data("4시",total16));
			seriesTime.getData().add(new XYChart.Data("5시",total17));
			seriesTime.getData().add(new XYChart.Data("6시",total18));
			seriesTime.getData().add(new XYChart.Data("7시",total19));
			seriesTime.getData().add(new XYChart.Data("8시",total20));
			seriesTime.getData().add(new XYChart.Data("9시",total21));
			barChart.getData().add(seriesTime);
			
			Button btnClose = (Button) parent.lookup("#btnClose");
			btnClose.setOnAction(e->dialog.close());
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
		}catch(IOException e) {
			
		}
		
	}

	//예약목록
	private void BookList() {
		
		try {
		
		Object[][] BookData;
		boolean searchResult = false;
		String day = LocalDate.now().toString();
		
		OrderDAO odao = new OrderDAO();
		OrderVO ovo = new OrderVO();
		
		ArrayList<String> title;
		ArrayList<OrderVO> list;
		
		title = odao.getColumnName();
		int columnCount = title.size();
		ovo = odao.getBookTotal(day);
		
		list = odao.getOrderTotal();
		int rowCount = list.size();
		
		BookData = new Object[rowCount][columnCount];
		
		if(ovo.getB_date().equals(day)) {
			bookData.removeAll(bookData);
			for(int index=0; index<rowCount; index++) {
			ovo = list.get(index);
			if(ovo.getB_date().equals(day)) {
				bookData.add(ovo);
			searchResult=true;
			}
		}
		}
		
		if(!searchResult) {
			bookData.removeAll(bookData);
		}
		
		}catch(Exception e) {
			
		}
	}


	//결제미완료 고객 조회
	private void handlerBtnRbPayN(ActionEvent event) {
		
		btnBsearch.setDisable(false);
		btnVsearch.setDisable(false);
		btnDelete.setDisable(true);
		btnPayCard.setDisable(true);
		btnPayCash.setDisable(true);
		
		OrderVO ovo = new OrderVO();
		OrderDAO odao = null;
		
		Object[][] totalData = null;
		
		String pay = "N";
		boolean searchResult = false;
		
		try {
			odao = new OrderDAO();
			ovo = odao.getPaySearch(pay);
			
			if(ovo!=null) {
			ArrayList<String> title;
			ArrayList<OrderVO> list;
			
			title = odao.getColumnName();
			int columnCount = title.size();
			
			list = odao.getOrderTotal();
			int rowCount = list.size();
			
			totalData = new Object[rowCount][columnCount];
			
			if(ovo.getPay().equals(pay)) {
				data.removeAll(data);
				for(int index = 0; index<rowCount; index++) {
					ovo = list.get(index);
					if(ovo.getPay().equals(pay)) {
						data.add(ovo);
						searchResult = true;
					}
				}
			}
			
			}
			
			if(!searchResult) {
				data.removeAll(data);
			}
			
		}catch(Exception e) {
			
		}
	}


	//결제완료고객 조회
	private void handlerBtnRbPayY(ActionEvent event) {

		btnBsearch.setDisable(false);
		btnVsearch.setDisable(false);
		btnDelete.setDisable(true);
		btnPayCard.setDisable(true);
		btnPayCash.setDisable(true);
		
		OrderVO ovo = new OrderVO();
		OrderDAO odao = null;
		
		Object[][] totalData = null;
		
		String pay = "Y";
		boolean searchResult = false;
		
		try {
			odao = new OrderDAO();
			ovo = odao.getPaySearch(pay);
			
			if(ovo!=null) {
			ArrayList<String> title;
			ArrayList<OrderVO> list;
			
			title = odao.getColumnName();
			int columnCount = title.size();
			
			list = odao.getOrderTotal();
			int rowCount = list.size();
			
			totalData = new Object[rowCount][columnCount];
			
			if(ovo.getPay().equals(pay)) {
				data.removeAll(data);
				for(int index = 0; index<rowCount; index++) {
					ovo = list.get(index);
					if(ovo.getPay().equals(pay)) {
						data.add(ovo);
						searchResult = true;
					}
				}
			}
			
			}
			
			if(!searchResult) {
				data.removeAll(data);
			}
			
		}catch(Exception e) {
			
		}
	
	}


	//방문고객 조회
	private void handlerBtnVsearchAction(ActionEvent event) {
		
		btnVsearch.setDisable(true);
		btnBsearch.setDisable(false);
		payGroup.selectToggle(null);
		btnDelete.setDisable(true);
		btnPayCard.setDisable(true);
		btnPayCash.setDisable(true);
		
		OrderVO ovo = new OrderVO();
		OrderDAO odao = null;
		
		Object[][] totalData = null;
		
		String section = "방문";
		boolean searchResult = false;
		
		try {
			odao = new OrderDAO();
			ovo = odao.getBookSearch(section);
			
			if(ovo!=null) {
			ArrayList<String> title;
			ArrayList<OrderVO> list;
			
			title = odao.getColumnName();
			int columnCount = title.size();
			
			list = odao.getOrderTotal();
			int rowCount = list.size();
			
			totalData = new Object[rowCount][columnCount];
			
			if(ovo.getC_section().equals(section)) {
				data.removeAll(data);
				for(int index = 0; index<rowCount; index++) {
					ovo = list.get(index);
					if(ovo.getC_section().equals(section)) {
						data.add(ovo);
						searchResult = true;
					}
				}
			}
			
			}
			
			if(!searchResult) {
				data.removeAll(data);
			}
			
		}catch(Exception e) {
			
		}
	}


	//예약고객 조회
	private void handlerBtnBsearchAction(ActionEvent event) {

		btnBsearch.setDisable(true);
		btnVsearch.setDisable(false);
		payGroup.selectToggle(null);
		btnDelete.setDisable(true);
		btnPayCard.setDisable(true);
		btnPayCash.setDisable(true);
		
		OrderVO ovo = new OrderVO();
		OrderDAO odao = null;
		
		Object[][] totalData = null;
		
		String section = "예약";
		boolean searchResult = false;
		
		try {
			odao = new OrderDAO();
			ovo = odao.getBookSearch(section);
			
			if(ovo!=null) {
			ArrayList<String> title;
			ArrayList<OrderVO> list;
			
			title = odao.getColumnName();
			int columnCount = title.size();
			
			list = odao.getOrderTotal();
			int rowCount = list.size();
			
			totalData = new Object[rowCount][columnCount];
			
			if(ovo.getC_section().equals(section)) {
				data.removeAll(data);
				for(int index = 0; index<rowCount; index++) {
					ovo = list.get(index);
					if(ovo.getC_section().equals(section)) {
						data.add(ovo);
						searchResult = true;
					}
				}
			}
			
			}
			
			if(!searchResult) {
				data.removeAll(data);
			}
			
		}catch(Exception e) {
			
		}
	}


	//예약자명으로 검색
	private void handlerBtnSearchAction(ActionEvent event) {

		btnDelete.setDisable(true);
		btnPayCard.setDisable(true);
		btnPayCash.setDisable(true);
		
		OrderVO oVo = new OrderVO();
		OrderDAO oDao = null;
		
		Object[][] totalData = null;
		
		String searchBname = "";
		boolean searchResult = false;
		
		try {
			searchBname = txtSearch.getText().trim();
			oDao = new OrderDAO();
			oVo = oDao.getOrderNameSearch(searchBname);
			
			if(searchBname.equals("")) {
				searchResult=true;
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("예약자 검색");
				alert.setHeaderText(null);
				alert.setContentText("예약자명을 입력해주세요.");
				alert.showAndWait();
			}
			
			if(!searchBname.equals("")&&(oVo!=null)) {
				ArrayList<String> title;
				ArrayList<OrderVO> list;
				
				title = oDao.getColumnName();
				int columnCount = title.size();
				
				list = oDao.getOrderTotal();
				int rowCount = list.size();
				
				totalData = new Object[rowCount][columnCount];
				
				if(oVo.getB_name().equals(searchBname)) {
					txtSearch.clear();
					data.removeAll(data);
					for(int index = 0; index<rowCount; index++) {
						oVo = list.get(index);
						if(oVo.getB_name().equals(searchBname)) {
						data.add(oVo);
						searchResult = true;
						}
					}
				}
			}
			
			if(!searchResult) {
				txtSearch.clear();
				Alert alert = new Alert (AlertType.INFORMATION);
				alert.setTitle("예약자 검색");
				alert.setHeaderText(null);
				alert.setContentText("해당 예약자가 없습니다.");
				alert.showAndWait();
			}
			
		}catch(Exception e) {
			Alert alert = new Alert (AlertType.WARNING);
			alert.setTitle("검색 오류");
			alert.setHeaderText(null);
			alert.setContentText("다시 시도해주세요.");
			alert.showAndWait();
		}
	}

	
	//전체고객 조회
	private void handlerBtnShowAction(ActionEvent event) {

		btnBsearch.setDisable(false);
		btnVsearch.setDisable(false);
		payGroup.selectToggle(null);
		btnDelete.setDisable(true);
		btnPayCard.setDisable(true);
		btnPayCash.setDisable(true);
		
		try {
			data.removeAll(data);
			totalList();
		}catch(Exception e) {
			
		}
		
	}

	
	//현금결제
	private void handlerBtnPayCash(ActionEvent event) {
		
		try {
			
			//새 창 띄우기
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PayCash.fxml"));
			
			Stage mainStage = new Stage();
			mainStage.setTitle("현금결제");
			mainStage.initModality(Modality.WINDOW_MODAL);
			mainStage.initOwner(btnPayCash.getScene().getWindow());
			
			Parent mainView = (Parent) loader.load();
			OrderVO orderPayCash = tableViewMain.getSelectionModel().getSelectedItem();
			selectOrder = tableViewMain.getSelectionModel().getSelectedItems();
			
			if(orderPayCash.getPay().equals("Y")) {
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("결제");
				alert.setHeaderText(null);
				alert.setContentText("결제 완료 된 고객입니다.");
				alert.showAndWait();
			}else {
			
				
				
			//입력창
			TextField no = (TextField) mainView.lookup("#txtNo");
			TextField payTotalPrice = (TextField) mainView.lookup("#txtTotalPrice");
			TextField money = (TextField) mainView.lookup("#txtMoney");
			TextField change = (TextField) mainView.lookup("#txtChange");
			DatePicker editPayDay = (DatePicker) mainView.lookup("#dpPayDay");
			
			//입력제한
			DecimalFormat format = new DecimalFormat("########");
			money.setTextFormatter(new TextFormatter<>(e1->{
				if(e1.getControlNewText().isEmpty()) {
					return e1;
				}
				
				ParsePosition parsePosition = new ParsePosition(0);
				Object object = format.parse(e1.getControlNewText(),parsePosition);
				
				if(object == null || parsePosition.getIndex()<e1.getControlNewText().length()
						||e1.getControlNewText().length()==9) {
					return null;
				}else {
					return e1;
				}
			}));
			
			payTotalPrice.setText(orderPayCash.getTotal_price()+"");
			payTotalPrice.setEditable(false);
			change.setDisable(true);
			editPayDay.setValue(LocalDate.now());
			editPayDay.setEditable(false);
			no.setDisable(true);
			no.setText(orderPayCash.getBo_no()+"");
			
			//버튼
			Button btnOk = (Button) mainView.lookup("#btnOk");
			Button btnCancle = (Button) mainView.lookup("#btnCancle");
			Button btnComplete = (Button) mainView.lookup("#btnComplete");
			
			btnComplete.setDisable(true);
			
			
			//거스름돈 계산
			btnOk.setOnAction(e->{
				
				if(money.getText().equals("")||Integer.parseInt(money.getText().trim())<=0) {
					Alert alert = new Alert (AlertType.WARNING);
					alert.setTitle("받은 금액 입력");
					alert.setHeaderText(null);
					alert.setContentText("받은 금액은 0보다 커야합니다.");
					alert.showAndWait();
				
				}else if(Integer.parseInt(money.getText().trim())<Integer.parseInt(payTotalPrice.getText().trim())) {
					Alert alert = new Alert (AlertType.WARNING);
					alert.setTitle("금액 부족");
					alert.setHeaderText(null);
					alert.setContentText("금액이 부족합니다. 다시 입력하세요.");
					alert.showAndWait();
				
				}else {
					int result;
					
					result = Integer.parseInt(money.getText())-Integer.parseInt(payTotalPrice.getText());
					change.setText(result+"");
					
					btnComplete.setDisable(false);
					
				}
				
			});
			
			//결제완료 버튼
			btnComplete.setOnAction(e->{
				OrderVO ovo = null;
				OrderDAO odao = null;
				
				TextField txtNo = (TextField) mainView.lookup("#txtNo");
				DatePicker PayDay = (DatePicker) mainView.lookup("#dpPayDay");
				
				data.remove(selectOrder);
				
				try {
					ovo = new OrderVO(Integer.parseInt(txtNo.getText()),"Y","현금",
							PayDay.getValue().toString());
					
					mainStage.close();
					
					odao = new OrderDAO();
					odao.getOrderCashUpdate(ovo, ovo.getBo_no());
					
					data.removeAll(data);
					totalList();
					btnVsearch.setDisable(false);
					btnBsearch.setDisable(false);
					payGroup.selectToggle(null);
					btnDelete.setDisable(true);
					btnPayCard.setDisable(true);
					btnPayCash.setDisable(true);
					
				}catch(Exception e2) {
					e2.printStackTrace();
				}
				
			});
			
			//취소버튼
			btnCancle.setOnAction(e->{
				Stage oldStage = (Stage) btnCancle.getScene().getWindow();
				oldStage.close();
				btnDelete.setDisable(true);
				btnPayCard.setDisable(true);
				btnPayCash.setDisable(true);
			});
			
			Scene scene = new Scene(mainView);
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			mainStage.show();
			
			}
		} catch (IOException e) {
			System.err.println("오류" + e);
		}
	}

	
	//카드결제
	private void handlerBtnPayCard(ActionEvent event) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PayCard.fxml"));
			
			Stage mainStage = new Stage();
			mainStage.setTitle("카드결제");
			mainStage.initModality(Modality.WINDOW_MODAL);
			mainStage.initOwner(btnPayCash.getScene().getWindow());
			
			Parent mainView = (Parent) loader.load();
			OrderVO orderPayCard = tableViewMain.getSelectionModel().getSelectedItem();
			selectOrder = tableViewMain.getSelectionModel().getSelectedItems();
			
			if(orderPayCard.getPay().equals("Y")) {
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("결제");
				alert.setHeaderText(null);
				alert.setContentText("결제 완료 된 고객입니다.");
				alert.showAndWait();
			}else {
			
			//입력창
			TextField no = (TextField) mainView.lookup("#txtNo");
			TextField payTotalPrice = (TextField) mainView.lookup("#txtTotalPrice");
			ComboBox<String> card = (ComboBox<String>) mainView.lookup("#cbCard");
			TextField cardNumber = (TextField) mainView.lookup("#txtCardNumber");
			DatePicker editPayDay = (DatePicker) mainView.lookup("#dpPayDay");
			
			//입력제한
			DecimalFormat format = new DecimalFormat("###############");
			cardNumber.setTextFormatter(new TextFormatter<>(e1->{
				if(e1.getControlNewText().isEmpty()) {
					return e1;
				}
				
				ParsePosition parsePosition = new ParsePosition(0);
				Object object = format.parse(e1.getControlNewText(),parsePosition);
				
				if(object == null || parsePosition.getIndex()<e1.getControlNewText().length()
						||e1.getControlNewText().length()==16) {
					return null;
				}else {
					return e1;
				}
			}));
			
			no.setDisable(true);
			no.setText(orderPayCard.getBo_no()+"");
			payTotalPrice.setText(orderPayCard.getTotal_price()+"");
			payTotalPrice.setEditable(false);
			card.setItems(FXCollections.observableArrayList("농협","우리","하나","신한",
					"카카오뱅크","K뱅크","현대","롯데","국민"));
			editPayDay.setValue(LocalDate.now());
			editPayDay.setEditable(false);
			
			//버튼
			Button btnOk = (Button) mainView.lookup("#btnOk");
			Button btnCancle = (Button) mainView.lookup("#btnCancle");
			Button btnComplete = (Button) mainView.lookup("#btnComplete");
			
			btnComplete.setDisable(true);
			
			
			//승인버튼
			btnOk.setOnAction(e->{
				
				if(card.getSelectionModel().getSelectedItem()==null
						|| cardNumber.getText()==null) {
					Alert alert = new Alert (AlertType.WARNING);
					alert.setTitle("입력 오류");
					alert.setHeaderText(null);
					alert.setContentText("정확히 입력했는지 확인해주세요.");
					alert.showAndWait();
				}else {
					Alert alert = new Alert (AlertType.INFORMATION);
					alert.setTitle("카드 승인");
					alert.setHeaderText(null);
					alert.setContentText("승인 되었습니다.");
					alert.showAndWait();
					
					btnOk.setDisable(true);
					editPayDay.setDisable(true);
					
					btnComplete.setDisable(false);
				}
				
			});
			
			
			//결제완료
			btnComplete.setOnAction(e->{
				
				OrderVO ovo = null;
				OrderDAO odao = null;
				
				TextField txtNo = (TextField) mainView.lookup("#txtNo");
				ComboBox<String> cbCard = (ComboBox<String>) mainView.lookup("#cbCard");
				TextField txtCardNumber = (TextField) mainView.lookup("#txtCardNumber");
				DatePicker dpPayDay = (DatePicker) mainView.lookup("#dpPayDay");
				
				data.removeAll(selectOrder);
				
				try {
					ovo = new OrderVO(Integer.parseInt(txtNo.getText().trim()),"Y",
							"카드",dpPayDay.getValue().toString(),
							cbCard.getSelectionModel().getSelectedItem());
					
					mainStage.close();
					
					odao = new OrderDAO();
					odao.getOrderCardUpdate(ovo, ovo.getBo_no());
					
					data.removeAll(data);
					totalList();
					btnVsearch.setDisable(false);
					btnBsearch.setDisable(false);
					payGroup.selectToggle(null);
					btnDelete.setDisable(true);
					btnPayCard.setDisable(true);
					btnPayCash.setDisable(true);
					
				}catch(Exception e2) {
					e2.printStackTrace();
				}
				
			});
			
			
			//취소버튼
			btnCancle.setOnAction(e->{
				Stage oldStage = (Stage) btnCancle.getScene().getWindow();
				oldStage.close();
				btnDelete.setDisable(true);
				btnPayCard.setDisable(true);
				btnPayCash.setDisable(true);
			});
			
			
			Scene scene = new Scene(mainView);
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			mainStage.show();
			}
		} catch (IOException e) {
			System.err.println("오류" + e);
		}
	}

	
	//테이블뷰 클릭
	private void handlerClickAction(MouseEvent event) {

		try {
			
			selectOrder = tableViewMain.getSelectionModel().getSelectedItems();
			no = selectOrder.get(0).getBo_no();
			btnDelete.setDisable(false);
			btnPayCard.setDisable(false);
			btnPayCash.setDisable(false);
			
		}catch(Exception e) {
			
		}
		
	}


	//주문 삭제
	private void handlerBtnDeleteAction(ActionEvent event) {

		OrderDAO odao = null;
		odao = new OrderDAO();
		
		try {
			odao.getOrderDelete(no);
			data.removeAll(data);
			totalList();
			btnVsearch.setDisable(false);
			btnBsearch.setDisable(false);
			payGroup.selectToggle(null);
			
			btnDelete.setDisable(true);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}


	//예약정보 등록
	private void handlerBtnBookOkAction(ActionEvent event) {
		
		OrderDAO odao =null;
		
		int searchTable_no = 0;
		String searchB_date = "-";
		
		boolean sucess=false;
		
		
		//예약정보 미입력 막기
		if(txtBname.getText().equals("-")||txtBphone.getText().equals("-")
				||cbHour.getSelectionModel().getSelectedItem().equals("-")
				||cbMinute.getSelectionModel().getSelectedItem().equals("-")) {
			
			Alert alert = new Alert (AlertType.WARNING);
			alert.setTitle("입력 오류");
			alert.setHeaderText(null);
			alert.setContentText("미입력된 곳을 확인해주세요.");
			alert.showAndWait();
			
		}else {
		
			if(dpBday.getValue().isBefore(LocalDate.now())) {
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("날짜 오류");
				alert.setHeaderText(null);
				alert.setContentText("오늘 이전 날짜는 예약 할 수 없습니다.");
				alert.showAndWait();
			}else {
			
		//예약날짜, 테이블 중복 검사
		try {
			
			searchTable_no = Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim());
			searchB_date = dpBday.getValue().toString();
			
			odao = new OrderDAO();
			sucess = odao.getBookOverlap(searchTable_no, searchB_date);
			
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		Alert alert;
		
		if(searchB_date.equals("-")) {
			alert = new Alert (AlertType.WARNING);
			alert.setTitle("입력 오류");
			alert.setHeaderText(null);
			alert.setContentText("예약날짜를 설정해주세요.");
			alert.showAndWait();
		}
		
		if(!sucess) {
		
		//예약정보 입력 막기
		cbTableNo.setDisable(true);
		txtBname.setDisable(true);
		txtBphone.setDisable(true);
		dpBday.setDisable(true);
		cbHour.setDisable(true);
		cbMinute.setDisable(true);
		btnBookOk.setDisable(true);
		//주문정보 입력 풀기
		txtAdult.setDisable(false);
		rbLAdult.setDisable(false);
		rbDAdult.setDisable(false);
		txtScChild.setDisable(false);
		txtPsChild.setDisable(false);
		txtSide.setDisable(false);
		btnOrderOk.setDisable(false);
		
		if(Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=1
				&&Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=20) {
		//1~20번 테이블 알람	
		alert = new Alert (AlertType.INFORMATION);
		alert.setTitle("테이블 정보 입력");
		alert.setHeaderText(cbTableNo.getSelectionModel().getSelectedItem()+"번 테이블 선택(5인석)");
		alert.setContentText(customerGroup.getSelectedToggle().getUserData().toString()+" 고객님");
		alert.showAndWait();
		
		}else if(Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=21
				&&Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=30) {
		//21~30번 테이블 알람	
		alert = new Alert (AlertType.INFORMATION);
		alert.setTitle("테이블 정보 입력");
		alert.setHeaderText(cbTableNo.getSelectionModel().getSelectedItem()+"번 테이블 선택(7인석)");
		alert.setContentText(customerGroup.getSelectedToggle().getUserData().toString()+" 고객님");
		alert.showAndWait();
		
		}
		
		}else {
			alert = new Alert (AlertType.WARNING);
			alert.setTitle("예약 불가");
			alert.setHeaderText(null);
			alert.setContentText("이미 예약된 테이블입니다.");
			alert.showAndWait();
		}
		
		}	
		}
	}


	//주문목록 리스트
	private void totalList() {

		Object[][] totalData;
		
		OrderDAO odao = new OrderDAO();
		OrderVO ovo = new OrderVO();
		ArrayList<String> title;
		ArrayList<OrderVO> list;
		
		//열
		title = odao.getColumnName();
		int columnCount = title.size();
		
		//행
		list = odao.getOrderTotal();
		int rowCount = list.size();
		
		totalData = new Object[rowCount][columnCount];
		
		for(int index = 0; index<rowCount; index++) {
			ovo = list.get(index);
			data.add(ovo);
		}
		
	}


	//주문등록
	private void handlerBtnOkAction(ActionEvent event) {

		btnOk.setDisable(true);
		btnDelete.setDisable(true);
		
		try {
			
			data.removeAll(data);
			OrderVO ovo = null;
			OrderDAO odao = null;
			
				ovo = new OrderVO(customerGroup.getSelectedToggle().getUserData().toString(),
						cbTableNo.getSelectionModel().getSelectedItem(),Integer.parseInt(txtTotal.getText().trim()),
						Integer.parseInt(txtTotalPrice.getText().trim()),Integer.parseInt(txtAdult.getText().trim())
						,Integer.parseInt(txtScChild.getText().trim()),Integer.parseInt(txtPsChild.getText().trim()),
						Integer.parseInt(txtSide.getText().trim()),txtBname.getText(),dpBday.getValue().toString(),
						cbHour.getSelectionModel().getSelectedItem()+":"+cbMinute.getSelectionModel().getSelectedItem(),
						txtBphone.getText(),"N",txtEname.getText());
				
				if(dpBday.getValue().equals(LocalDate.of(1000, 01, 01))){
					ovo.setB_date("-");
				}
				
				odao = new OrderDAO();
				odao.getOrderRegisteB(ovo);
				
				if(odao != null) {
					totalList();
					handlerBtnCancleAction(event);
					btnVsearch.setDisable(false);
					btnBsearch.setDisable(false);
					payGroup.selectToggle(null);
				}
				
		}catch(Exception e) {
			System.out.println("e=["+e+"]");
			Alert alert = new Alert (AlertType.WARNING);
			alert.setTitle("입력 오류");
			alert.setHeaderText(null);
			alert.setContentText("정보를 정확히 입력하세요.");
			alert.showAndWait();
		}
		
	}

	
	//테이블정보 확인
	private void handlerBtnTableOkAction(ActionEvent event) {

		OrderDAO odao = null;
		int searchBTable = 0;//방문고객 테이블번호 중복검사
		String searchEName = "";//담당자 이름 검사
		boolean searchBResult = true;
		boolean searchEResult = true;
		
		try {
			
			
			//담당자 이름 검사
			try {
				
				searchEName = txtEname.getText();
				odao = new OrderDAO();
				searchEResult = (boolean) odao.getEnameOverlap(searchEName);
				
				if(searchEResult && !searchEName.equals("")) {
					
					//방문고객
					if(customerGroup.getSelectedToggle().getUserData().toString().equals("방문")) {
						
				
				//테이블번호 중복 검사
				try {
					
					searchBTable = Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim());
					odao = new OrderDAO();
					searchBResult = (boolean) odao.getTableNoOverlap(searchBTable);
					
					
					
					if(!searchBResult && searchBTable!=0) {
						cbTableNo.setDisable(true);
						txtEname.setDisable(true);
						rbBook.setDisable(true);
						rbVisit.setDisable(true);
						btnTableOk.setDisable(true);
						
						txtAdult.setDisable(false);
						rbLAdult.setDisable(false);
						rbDAdult.setDisable(false);
						txtScChild.setDisable(false);
						txtPsChild.setDisable(false);
						txtSide.setDisable(false);
						btnOrderOk.setDisable(false);
						
						if(Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=1
								&&Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=20) {
						//1~20번 테이블 알람	
						Alert alert = new Alert (AlertType.INFORMATION);
						alert.setTitle("테이블 정보 입력");
						alert.setHeaderText(cbTableNo.getSelectionModel().getSelectedItem()+"번 테이블 선택(5인석)");
						alert.setContentText(customerGroup.getSelectedToggle().getUserData().toString()+" 고객님");
						alert.showAndWait();
						
						}else if(Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=21
								&&Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=30) {
						//21~30번 테이블 알람	
						Alert alert = new Alert (AlertType.INFORMATION);
						alert.setTitle("테이블 정보 입력");
						alert.setHeaderText(cbTableNo.getSelectionModel().getSelectedItem()+"번 테이블 선택(7인석)");
						alert.setContentText(customerGroup.getSelectedToggle().getUserData().toString()+" 고객님");
						alert.showAndWait();
						
						}
						
					}else if(searchBTable==0) {
						Alert alert = new Alert (AlertType.WARNING);
						alert.setTitle("입력 오류");
						alert.setHeaderText(null);
						alert.setContentText("테이블 번호를 선택해 주세요.");
						alert.showAndWait();
					}else {
						cbTableNo.getSelectionModel().clearSelection();
						
						Alert alert = new Alert (AlertType.WARNING);
						alert.setTitle("테이블 중복");
						alert.setHeaderText(null);
						alert.setContentText("이미 입석한 테이블입니다.");
						alert.showAndWait();
					}
					
				}catch(Exception e) {
					Alert alert = new Alert (AlertType.WARNING);
					alert.setTitle("입력 오류");
					alert.setHeaderText(null);
					alert.setContentText("정확하게 입력하세요.");
					alert.showAndWait();
				}
				
				
			//예약고객	
			}else if(customerGroup.getSelectedToggle().getUserData().toString().equals("예약")) {
				
				
				if(cbTableNo.getSelectionModel().getSelectedItem().equals("")) {
					Alert alert = new Alert (AlertType.WARNING);
					alert.setTitle("입력 오류");
					alert.setHeaderText(null);
					alert.setContentText("테이블 번호를 선택해주세요.");
					alert.showAndWait();
				}else {
				txtEname.setDisable(true);
				rbBook.setDisable(true);
				rbVisit.setDisable(true);
				btnTableOk.setDisable(true);
					
				dpBday.setValue(LocalDate.now());
				txtBname.setDisable(false);
				txtBphone.setDisable(false);
				dpBday.setDisable(false);
				cbHour.setDisable(false);
				cbMinute.setDisable(false);
				btnBookOk.setDisable(false);
				}
			}
					
				}else if(searchEName.equals("")) {
					Alert alert = new Alert (AlertType.WARNING);
					alert.setTitle("입력 오류");
					alert.setHeaderText(null);
					alert.setContentText("담당자 이름을 입력해주세요.");
					alert.showAndWait();
					
				}else {
					Alert alert = new Alert (AlertType.WARNING);
					alert.setTitle("입력 오류");
					alert.setHeaderText(null);
					alert.setContentText("해당 직원은 존재하지 않거나 퇴사한 직원입니다.");
					alert.showAndWait();
					txtEname.requestFocus();
				}
			
			
			}catch(Exception e) {
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("입력 오류");
				alert.setHeaderText(null);
				alert.setContentText("정확하게 입력하세요.");
				alert.showAndWait();
			}
			
		}catch(Exception e) {
			Alert alert = new Alert (AlertType.WARNING);
			alert.setTitle("입력 오류");
			alert.setHeaderText(null);
			alert.setContentText("정확하게 입력하세요.");
			alert.showAndWait();
			
			cbTableNo.setDisable(false);
			txtEname.setDisable(false);
			rbBook.setDisable(false);
			rbVisit.setDisable(false);
		}
		
	}


	//메뉴확인
	private void handlerBtnOrderOkAction(ActionEvent event) {
		
		try {
		
		int adult = Integer.parseInt(txtAdult.getText());
		int childSc = Integer.parseInt(txtScChild.getText());
		int childPs = Integer.parseInt(txtPsChild.getText());
		int side = Integer.parseInt(txtSide.getText());
		
		//총 인원
		int totalCount;
		totalCount = adult+childSc+childPs;
		txtTotal.setText(totalCount+"");
		
		if(totalCount!=0) {
		
		//총 가격
		int totalPrice;
		
		//점심값
		if(adultGroup.getSelectedToggle().getUserData().toString().equals("점심")) {
			
			totalPrice = (adult*AL)+(childSc*SC)+(childPs*PS)+(side*SD);
			
			//최대인원 제한
			if((Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=1 
				&& Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=20
				&& totalCount<=5) || (Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=21&&
					Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=30 && totalCount<=7)) {
				
				txtTotalPrice.setText(totalPrice+"");
				btnOk.setDisable(false);
				//주문정보 입력 막기
				txtAdult.setDisable(true);
				txtScChild.setDisable(true);
				txtPsChild.setDisable(true);
				txtSide.setDisable(true);
				btnOrderOk.setDisable(true);
				
			}else if((Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=1 
					&& Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=20
					&& totalCount>=5) || (Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=21&&
							Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=30 && totalCount>=7)) {
				
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("최대인원 초과");
				alert.setHeaderText("최대인원을 초과했습니다. 인원 수를 확인해주세요.");
				alert.setContentText("1~20번 테이블 최대인원 5명"+"\n 21~30테이블 최대인원 7명");
				alert.showAndWait();
				
			}
			
			
		//저녁값	
		}else if(adultGroup.getSelectedToggle().getUserData().toString().equals("저녁")) {
			
			totalPrice = (adult*AD)+(childSc*SC)+(childPs*PS)+(side*SD);
			
			//최대인원 제한
			if((Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=1 
					&& Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=20
					&& totalCount<=5) || (Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=21&&
							Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=30 && totalCount<=7)) {
				
				txtTotalPrice.setText(totalPrice+"");
				btnOk.setDisable(false);
				//주문정보 입력 막기
				txtAdult.setDisable(true);
				txtScChild.setDisable(true);
				txtPsChild.setDisable(true);
				txtSide.setDisable(true);
				btnOrderOk.setDisable(true);
				
				return;
			
				}else if((Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=1 
					&& Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=20
					&& totalCount>=5) || (Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=21&&
							Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=30 && totalCount>=7)){
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("최대인원 초과");
				alert.setHeaderText("최대인원을 초과했습니다. 인원 수를 확인해주세요.");
				alert.setContentText("1~20번 테이블 최대인원 5명"+"\n 21~30테이블 최대인원 7명");
				alert.showAndWait();
				}
		}
		
		}else {
			Alert alert = new Alert (AlertType.WARNING);
			alert.setTitle("수량 입력");
			alert.setHeaderText(null);
			alert.setContentText("수량을 입력해주세요.");
			alert.showAndWait();
		}
		
		}catch(Exception e) {
			Alert alert = new Alert (AlertType.WARNING);
			alert.setTitle("입력 오류");
			alert.setHeaderText(null);
			alert.setContentText("입력하지 않은 곳이 있습니다.");
			alert.showAndWait();
		}
	
	}

	
	//취소
	private void handlerBtnCancleAction(ActionEvent event) {

		//테이블정보 초기화
		cbTableNo.setDisable(false);
		cbTableNo.getSelectionModel().clearSelection();
		txtEname.setDisable(false);
		txtEname.clear();
		customerGroup.selectToggle(null);
		rbBook.setDisable(false);
		rbVisit.setDisable(false);
		btnTableOk.setDisable(false);
		
		//예약정보 초기화
		dpBday.setValue(LocalDate.of(1000, 01, 01));
		cbHour.getSelectionModel().select("-");
		cbMinute.getSelectionModel().select("-");
		txtBname.setText("-");
		txtBphone.setText("-");
		dpBday.setDisable(true);
		cbHour.setDisable(true);
		cbMinute.setDisable(true);
		txtBname.setDisable(true);
		txtBphone.setDisable(true);
		btnBookOk.setDisable(true);
		
		//주문정보 초기화
		adultGroup.selectToggle(rbLAdult);
		txtAdult.setText(0+"");
		txtScChild.setText(0+"");
		txtPsChild.setText(0+"");
		txtSide.setText(0+"");
		txtTotal.clear();
		txtTotalPrice.clear();
		rbDAdult.setDisable(true);
		rbLAdult.setDisable(true);
		txtAdult.setDisable(true);
		txtScChild.setDisable(true);
		txtPsChild.setDisable(true);
		txtSide.setDisable(true);
		btnOk.setDisable(true);
		
		btnDelete.setDisable(true);
		btnPayCard.setDisable(true);
		btnPayCash.setDisable(true);
		
	}
	
	
	//프로그램 종료
	private void handlerBtnExitAction(ActionEvent event) {

		Platform.exit();
	
	}

}
