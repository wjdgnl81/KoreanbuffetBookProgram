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

	//�ֹ� ����
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
	
	
	//���̺�����
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
	
	//��������
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
	
	//�ֹ����
	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancle;
	@FXML
	private Button btnDelete;
	
	//����
	@FXML
	private Button btnPayCard;
	@FXML
	private Button btnPayCash;
	
	//�˻�
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
	
	//������Ȳ
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
	
	//������Ȳ
	@FXML
	private Tab tabThird;
	@FXML
	private TextField txtTotalPrice;//���ø���
	@FXML
	private Button btnOrder;//�ֹ�����
	@FXML
	private Button btnLogOut;//�α׾ƿ�
	@FXML
	private Button btnExit;//����
	@FXML
	private Button btnSaveDirSales;
	@FXML
	private TextField txtSaveDirSales;
	@FXML
	private Button btnSaveExcelSales;
	@FXML
	private Button btnSavePDFSales;
	
	//��¥�� ����
	@FXML
	private TableView<SalesVO> tableViewDay;//��¥�� ���̺��
	@FXML
	private Button btnMonth;//������Ʈ
	@FXML
	private Button btnQuarter;//�б⺰��Ʈ
	@FXML
	private DatePicker dpSaleDate;//��¥����
	@FXML
	private Button btnSaleDateSearch;//��¥�˻�
	@FXML
	private Button btnShowSaleDate;//��ü�˻�
	
	//����������
	@FXML
	private TableView<SalesVO> tableViewEmployee;//������ ���� ���̺��
	@FXML
	private Button btnEmployee;//��������
	
	//ī��纰����
	@FXML
	private TableView<SalesVO> tableViewCard;//ī��纰 ���� ���̺��
	
	//���̺��
	@FXML
	private TableView<OrderVO> tableViewMain; //����
	@FXML
	private TableView<OrderVO> tableViewBook; //���� ��Ȳ
	
	OrderVO orderVo = new OrderVO();
	ObservableList<OrderVO> data = FXCollections.observableArrayList();
	ObservableList<OrderVO> selectOrder;
	ObservableList<OrderVO> bookData = FXCollections.observableArrayList();
	
	SalesVO svo = new SalesVO();
	ObservableList<SalesVO> dayData = FXCollections.observableArrayList();
	ObservableList<SalesVO> EmployeeData = FXCollections.observableArrayList();
	ObservableList<SalesVO> CardData = FXCollections.observableArrayList();
	
	private Stage primaryStage;
	
	final int AL = 13900;//�������� ����
	final int AD = 19900;//�������� ����
	final int SC = 9900;//�Ƶ� ����
	final int PS = 6500;//���� ����
	final int SD = 3900;//���̵尡��
	
	int no; //������ ���̺��� ������ �ֹ���ȣ ����
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		//�������� �Է¶� ����
		txtBname.setDisable(true);
		txtBphone.setDisable(true);
		dpBday.setDisable(true);
		cbHour.setDisable(true);
		cbMinute.setDisable(true);
		btnBookOk.setDisable(true);
		
		//�ֹ����� �Է¶�����
		rbLAdult.setDisable(true);
		rbDAdult.setDisable(true);
		txtAdult.setDisable(true);
		txtScChild.setDisable(true);
		txtPsChild.setDisable(true);
		txtSide.setDisable(true);
		btnOrderOk.setDisable(true);
		
		//�� �ο���, ���� �Է�, ������ ��ư ����
		txtTotal.setEditable(false);
		txtTotalPrice.setEditable(false);
		btnOk.setDisable(true);
		btnDelete.setDisable(true);
		btnPayCard.setDisable(true);
		btnPayCash.setDisable(true);
		
		btnSaveExcelSales.setDisable(true);
		btnSavePDFSales.setDisable(true);
		txtSaveDirSales.setEditable(true);
		
		//���ΰ��� ó�� ������ ���ɰ�����
		adultGroup.selectToggle(rbLAdult);
		
		//��¥ ����Ʈ��
		dpBday.setValue(LocalDate.of(1000, 01, 01));
		
		//���̺��ȣ �޺��ڽ�
		cbTableNo.setItems(FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10",
				"11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27",
				"28","29","30"));
		//�ð� �޺��ڽ�
		cbHour.setItems(FXCollections.observableArrayList("10","11","12","13","14","15","16","17","18",
				"19","20","21"));
		//�� �޺��ڽ�
		cbMinute.setItems(FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08",
				"09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26",
				"27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44",
				"45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"));
		
		//�ð� ����Ʈ��
		cbHour.getSelectionModel().select("-");
		cbMinute.getSelectionModel().select("-");
		
		//���ڸ� �Է�, 2�� ����
		//����
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
		
		//�Ƶ�
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
		
		//����
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
		
		//���̵�
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
		
		//�ֹ� ���̺��
		tableViewMain.setEditable(false);
		
		TableColumn colNo = new TableColumn("NO.");
		colNo.setMaxWidth(40);
		colNo.setStyle("-fx-alignment: CENTER");
		colNo.setCellValueFactory(new PropertyValueFactory<>("bo_no"));
		
		TableColumn colSec = new TableColumn("�湮/����");
		colSec.setMaxWidth(60);
		colSec.setStyle("-fx-alignment: CENTER");
		colSec.setCellValueFactory(new PropertyValueFactory<>("c_section"));
		
		TableColumn colTable = new TableColumn("���̺�");
		colTable.setMaxWidth(50);
		colTable.setStyle("-fx-alignment: CENTER");
		colTable.setCellValueFactory(new PropertyValueFactory<>("table_no"));
		
		TableColumn colTotal = new TableColumn("���ο�");
		colTotal.setMaxWidth(50);
		colTotal.setStyle("-fx-alignment: CENTER");
		colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		
		TableColumn colTotalPrice = new TableColumn("�Ѱ���");
		colTotalPrice.setMaxWidth(80);
		colTotalPrice.setStyle("-fx-alignment: CENTER");
		colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));
		
		TableColumn colAdult = new TableColumn("����");
		colAdult.setMaxWidth(40);
		colAdult.setStyle("-fx-alignment: CENTER");
		colAdult.setCellValueFactory(new PropertyValueFactory<>("adult"));
		
		TableColumn colScChild = new TableColumn("�Ƶ�");
		colScChild.setMaxWidth(40);
		colScChild.setStyle("-fx-alignment: CENTER");
		colScChild.setCellValueFactory(new PropertyValueFactory<>("sc_child"));
		
		TableColumn colPsChild = new TableColumn("����");
		colPsChild.setMaxWidth(40);
		colPsChild.setStyle("-fx-alignment: CENTER");
		colPsChild.setCellValueFactory(new PropertyValueFactory<>("ps_child"));
		
		TableColumn colSide = new TableColumn("���̵�");
		colSide.setMaxWidth(50);
		colSide.setStyle("-fx-alignment: CENTER");
		colSide.setCellValueFactory(new PropertyValueFactory<>("side"));
		
		TableColumn colBName = new TableColumn("������");
		colBName.setMaxWidth(70);
		colBName.setStyle("-fx-alignment: CENTER");
		colBName.setCellValueFactory(new PropertyValueFactory<>("b_name"));
		
		TableColumn colBDay = new TableColumn("������");
		colBDay.setMinWidth(130);
		colBDay.setStyle("-fx-alignment: CENTER");
		colBDay.setCellValueFactory(new PropertyValueFactory<>("b_date"));
		
		TableColumn colBTime = new TableColumn("����ð�");
		colBTime.setMaxWidth(70);
		colBTime.setStyle("-fx-alignment: CENTER");
		colBTime.setCellValueFactory(new PropertyValueFactory<>("b_time"));
		
		TableColumn colBPhone = new TableColumn("����ó");
		colBPhone.setMinWidth(110);
		colBPhone.setStyle("-fx-alignment: CENTER");
		colBPhone.setCellValueFactory(new PropertyValueFactory<>("b_phone"));
		
		TableColumn colPay = new TableColumn("�����Ϸ�");
		colPay.setMaxWidth(60);
		colPay.setStyle("-fx-alignment: CENTER");
		colPay.setCellValueFactory(new PropertyValueFactory<>("pay"));
		
		TableColumn colEName = new TableColumn("�����");
		colEName.setMaxWidth(60);
		colEName.setStyle("-fx-alignment: CENTER");
		colEName.setCellValueFactory(new PropertyValueFactory<>("e_name"));
		
		tableViewMain.setItems(data);
		tableViewMain.getColumns().addAll(colNo,colSec,colTable,colTotal,colTotalPrice,colAdult,
				colScChild,colPsChild,colSide,colBName,colBDay,colBTime,colBPhone,colPay,colEName);
		
		totalList();
		
		
		//���� ���̺��
		tableViewBook.setEditable(false);
		
		TableColumn colBNo = new TableColumn("NO.");
		colBNo.setMaxWidth(40);
		colBNo.setStyle("-fx-alignment: CENTER");
		colBNo.setCellValueFactory(new PropertyValueFactory<>("bo_no"));
		
		TableColumn colBbDay = new TableColumn("��¥");
		colBbDay.setMinWidth(120);
		colBbDay.setStyle("-fx-alignment: CENTER");
		colBbDay.setCellValueFactory(new PropertyValueFactory<>("b_date"));
		
		TableColumn colBbTime = new TableColumn("�ð�");
		colBbTime.setMaxWidth(70);
		colBbTime.setStyle("-fx-alignment: CENTER");
		colBbTime.setCellValueFactory(new PropertyValueFactory<>("b_time"));
		
		TableColumn colBAdult = new TableColumn("����");
		colBAdult.setMaxWidth(40);
		colBAdult.setStyle("-fx-alignment: CENTER");
		colBAdult.setCellValueFactory(new PropertyValueFactory<>("adult"));
		
		TableColumn colBScChild = new TableColumn("�Ƶ�");
		colBScChild.setMaxWidth(40);
		colBScChild.setStyle("-fx-alignment: CENTER");
		colBScChild.setCellValueFactory(new PropertyValueFactory<>("sc_child"));
		
		TableColumn colBPsChild = new TableColumn("����");
		colBPsChild.setMaxWidth(40);
		colBPsChild.setStyle("-fx-alignment: CENTER");
		colBPsChild.setCellValueFactory(new PropertyValueFactory<>("ps_child"));
		
		TableColumn colBTotal = new TableColumn("���ο�");
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
		
		
		//��¥������
		tableViewDay.setEditable(false);
				
		TableColumn colDay = new TableColumn("��¥");
		colDay.setMinWidth(110);
		colDay.setStyle("-fx-alignment: CENTER");
		colDay.setCellValueFactory(new PropertyValueFactory<>("pay_date"));
				
		TableColumn colTotalCount = new TableColumn("�湮 �ο�");
		colTotalCount.setMaxWidth(70);
		colTotalCount.setStyle("-fx-alignment: CENTER");
		colTotalCount.setCellValueFactory(new PropertyValueFactory<>("total_count"));
				
		TableColumn colDayTotalPrice = new TableColumn("����");
		colDayTotalPrice.setMinWidth(90);
		colDayTotalPrice.setStyle("-fx-alignment: CENTER");
		colDayTotalPrice.setCellValueFactory(new PropertyValueFactory<>("day_sales"));
		
		tableViewDay.setItems(dayData);
		tableViewDay.getColumns().addAll(colDay,colTotalCount,colDayTotalPrice);
		
				
				
		//������ ���� ���̺� ��
		tableViewEmployee.setEditable(false);
				
		TableColumn colEname = new TableColumn("����");
		colEname.setMinWidth(70);
		colEname.setStyle("-fx-alignment: CENTER");
		colEname.setCellValueFactory(new PropertyValueFactory<>("e_name"));
				
		TableColumn colECount = new TableColumn("�Ǽ�");
		colECount.setMinWidth(60);
		colECount.setStyle("-fx-alignment: CENTER");
		colECount.setCellValueFactory(new PropertyValueFactory<>("e_count"));
				
		TableColumn colESales = new TableColumn("�� ����");
		colESales.setMinWidth(110);
		colESales.setStyle("-fx-alignment: CENTER");
		colESales.setCellValueFactory(new PropertyValueFactory<>("e_sales"));
				
		tableViewEmployee.setItems(EmployeeData);
		tableViewEmployee.getColumns().addAll(colEname,colECount,colESales);
				

				
		//ī��纰 ���� ���̺� ��
		tableViewCard.setEditable(false);
				
		TableColumn colCard = new TableColumn("ī���");
		colCard.setMinWidth(90);
		colCard.setStyle("-fx-alignment: CENTER");
		colCard.setCellValueFactory(new PropertyValueFactory<>("card"));
				
		TableColumn colCCount = new TableColumn("�Ǽ�");
		colCCount.setMinWidth(60);
		colCCount.setStyle("-fx-alignment: CENTER");
		colCCount.setCellValueFactory(new PropertyValueFactory<>("c_count"));
				
		TableColumn colCSales = new TableColumn("�� ����");
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
				
				//���ø��� ǥ��
				txtDayTotalPrice.setEditable(false);
							
				DecimalFormat df = new DecimalFormat("###,###");
						
				for(int i = 0; i<dayData.size();i++) {
					if(dayData.get(i).getPay_date().equals(LocalDate.now().toString())) {
						String format1 = df.format(dayData.get(i).getDay_sales());
						txtDayTotalPrice.setText("�� "+format1+"");
					}else if(txtDayTotalPrice.getText().equals("")) {
						txtDayTotalPrice.setText(0+"");
					}
				}
			}
		});
		
		
		//��ư �׼�
		btnTableOk.setOnAction(event->handlerBtnTableOkAction(event));//���̺����� ���
		btnBookOk.setOnAction(event->handlerBtnBookOkAction(event));//�������� ���
		btnOrderOk.setOnAction(event->handlerBtnOrderOkAction(event));//�ֹ����� ���
		btnOk.setOnAction(event->handlerBtnOkAction(event));//���
		btnCancle.setOnAction(event->handlerBtnCancleAction(event));//���(�ʱ�ȭ)
		btnDelete.setOnAction(event->handlerBtnDeleteAction(event));//����
		tableViewMain.setOnMouseClicked(event->handlerClickAction(event));//�ֹ� ����
		
		btnPayCash.setOnAction(event->handlerBtnPayCash(event));//���ݰ���
		btnPayCard.setOnAction(event->handlerBtnPayCard(event));//ī�����
		
		btnShow.setOnAction(event->handlerBtnShowAction(event));//��ü���
		btnSearch.setOnAction(event->handlerBtnSearchAction(event));//�˻�
		rbPayY.setOnAction(event->handlerBtnRbPayY(event));//�����Ϸ�� ��ȸ
		rbPayN.setOnAction(event->handlerBtnRbPayN(event));//�����̿Ϸ�� ��ȸ
		btnVsearch.setOnAction(event->handlerBtnVsearchAction(event));//�湮�� ��ȸ
		btnBsearch.setOnAction(event->handlerBtnBsearchAction(event));//����� ��ȸ
		
		btnExit.setOnAction(event->handlerBtnExitAction(event));//����
		btnLogOut.setOnAction(event->handlerBtnLogOutAction(event));//�α׾ƿ�
		
		btnBarChart.setOnAction(event->handlerBtnBarChart(event));//�ð��� ����Ʈ
		
		//������ ��ư�׼�
		btnSaleDateSearch.setOnAction(event->handlerBtnSaleDateSearchAction(event));//��¥�˻�
		btnShowSaleDate.setOnAction(event->handlerBtnShowSaleDateAction(event));//��ü��� ����
		btnMonth.setOnAction(event->handlerBtnMonthAction(event));//�������� ����Ʈ
		btnQuarter.setOnAction(event->handlerBtnQuarterAction(event));//�б⺰���� ����Ʈ
		btnEmployee.setOnAction(event->handlerBtnEmployeeAction(event));//��������
		
		btnSaveDirSales.setOnAction(event->handlerBtnSaveDirSalesAction(event));//���� ������������
		btnSaveExcelSales.setOnAction(event->handlerExcelSalesAction(event));//���� �������� ����
		btnSavePDFSales.setOnAction(event->handlerPDFSalesAction(event));//���� PDF���� ����
	}





	//���� ������������ ����
	private void handlerBtnSaveDirSalesAction(ActionEvent event) {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		final File selectedDirectory = directoryChooser.showDialog(primaryStage);
		
		if(selectedDirectory != null) {
			txtSaveDirSales.setText(selectedDirectory.getAbsolutePath());
			btnSaveExcelSales.setDisable(false);
			btnSavePDFSales.setDisable(false);
		}
	}
	
	
	//���� �������� ����
	private void handlerExcelSalesAction(ActionEvent event) {

		SalesDAO sdao = new SalesDAO();
		boolean saveSuccess;
		
		ArrayList<SalesVO> list;
		list = sdao.getDateSales();
		SalesExcel excelWriter = new SalesExcel();
		
		//xlsx ���� ����
		saveSuccess = excelWriter.xlsxWriter(list, txtSaveDirSales.getText());
		if(saveSuccess) {
			Alert alert = new Alert (AlertType.INFORMATION);
			alert.setTitle("���� ���� ����");
			alert.setHeaderText(null);
			alert.setContentText("��¥�� ���� ���� ���� ���� ����");
			alert.showAndWait();
		}
		txtSaveDirSales.clear();
		btnSaveExcelSales.setDisable(true);
		btnSavePDFSales.setDisable(true);
		
	}


	//���� pdf���� ����
	private void handlerPDFSalesAction(ActionEvent event) {
		try {
			
		FXMLLoader loaderPdf = new FXMLLoader();
		loaderPdf.setLocation(getClass().getResource("/view/pdfImage.fxml"));
		
		Stage dialogPdf = new Stage(StageStyle.UTILITY);
		dialogPdf.initModality(Modality.WINDOW_MODAL);
		dialogPdf.initOwner(btnSavePDFSales.getScene().getWindow());
		dialogPdf.setTitle("��¥ �� ���� �׷��� �̹��� ����");
		
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
			
			
			//pdf document ����
			Document document = new Document(PageSize.A4, 0, 0, 30, 30);
			
			//pdf ���� ������ ���� ����. ���� ���� �� ��Ʈ������ ����
			String strReportPDFName = "dateSales_"+System.currentTimeMillis()+".pdf";
			PdfWriter.getInstance(document, new FileOutputStream(txtSaveDirSales.getText()+"\\"
					+strReportPDFName));
			//document�� ���� pdf������ �� �� �ֵ���
			document.open();
			//�ѱ�������Ʈ ����
			BaseFont bf = BaseFont.createFont("font/MALGUN.TTF",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
			Font font = new Font(bf,8,Font.NORMAL);
			Font font2 = new Font(bf,14,Font.BOLD);
			//Ÿ��Ʋ
			Paragraph title = new Paragraph("��¥ �� ����",font2);
			//�߰�����
			title.setAlignment(Element.ALIGN_CENTER);
			//������ �߰�
			document.add(title);
			document.add(new Paragraph("\r\n"));
			//���� ��¥
			LocalDate date = LocalDate.now();
			Paragraph writeDay = new Paragraph(date.toString(),font);
			//������ ����
			writeDay.setAlignment(Element.ALIGN_RIGHT);
			//������ �߰�
			document.add(writeDay);
			document.add(new Paragraph("\r\n"));
			
			//���̺� ����
			//�÷� ��
			PdfPTable table = new PdfPTable(3);
			//�÷� ũ��
			table.setWidths(new int[] {110, 70, 90});
			//�÷� Ÿ��Ʋ
			PdfPCell header1 = new PdfPCell(new Paragraph("��¥",font));
			PdfPCell header2 = new PdfPCell(new Paragraph("�湮 �ο�",font));
			PdfPCell header3 = new PdfPCell(new Paragraph("����",font));
	
			//��������
			header1.setHorizontalAlignment(Element.ALIGN_CENTER);
			header2.setHorizontalAlignment(Element.ALIGN_CENTER);
			header3.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			//��������
			header1.setVerticalAlignment(Element.ALIGN_CENTER);
			header2.setVerticalAlignment(Element.ALIGN_CENTER);
			header3.setVerticalAlignment(Element.ALIGN_CENTER);
			
			//���̺� �߰�
			table.addCell(header1);
			table.addCell(header2);
			table.addCell(header3);
			
			//DB ���� �� ����Ʈ ����
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
				
				//��������
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				//��������
				cell1.setVerticalAlignment(Element.ALIGN_CENTER);
				cell2.setVerticalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_CENTER);
				
				//���̺� �� �߰�
				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
			}
			
			//������ ���̺� �߰�
			document.add(table);
			document.add(new Paragraph("\r\n"));
			Alert alert = new Alert(AlertType.INFORMATION);
			
			
			if(cbMonthImage.isSelected()&&cbQuqrterImage.isSelected()) {
				
				//�� ���� �׷��� �̹��� �߰�
				Paragraph barImageTitle = new Paragraph("2018�� �� ���� ���� �׷���",font);
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
			
				//�б⺰ ���� �׷��� �̹��� �߰�
				Paragraph barImageTitle2 = new Paragraph("2018�� �б⺰ ���� ���� �׷���",font);
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
				
				//�� ���� �׷��� �̹��� �߰�
				Paragraph barImageTitle = new Paragraph("2018�� �� ���� ���� �׷���",font);
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
				
				//�б⺰ ���� �׷��� �̹��� �߰�
				Paragraph barImageTitle2 = new Paragraph("2018�� �б⺰ ���� ���� �׷���",font);
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
			
			
			//���� �ݱ�
			document.close();
			
			dialogPdf.close();
			txtSaveDirSales.clear();
			btnSavePDFSales.setDisable(true);
			btnSaveExcelSales.setDisable(true);
			
			alert.setTitle("PDF ���� ����");
			alert.setHeaderText(null);
			alert.setContentText("��¥�� ���� PDF ���� ���� ����");
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
	
	
	//---------��3---------


	//�������� â ����
	private void handlerBtnEmployeeAction(ActionEvent event) {
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Employee.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scene = new Scene(mainView);
				Stage mainStage = new Stage();
				mainStage.setTitle("���� ����");
				mainStage.setScene(scene);
				mainStage.initModality(Modality.WINDOW_MODAL);
				mainStage.initOwner(btnEmployee.getScene().getWindow());
				mainStage.setResizable(false);
				mainStage.show();
			} catch (IOException e) {
				System.err.println("����" + e);
			}
			
		}
		
		
	//�б⺰ ����Ʈ
	private void handlerBtnQuarterAction(ActionEvent event) {
			
			int one = 0;
			int two = 0;
			int three = 0;
			int four = 0;
			
			try {
				Stage dialog = new Stage(StageStyle.UTILITY);
				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.initOwner(btnMonth.getScene().getWindow());
				dialog.setTitle("�б� �� ����");
				
				Parent parent = FXMLLoader.load(getClass().getResource("/view/QuarterBarChart.fxml"));
				
				BarChart barChart = (BarChart) parent.lookup("#barChartQuarter");
				
				XYChart.Series series2018 = new XYChart.Series();
				series2018.setName("2018�� �б⺰ ����");
			
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
			
			
			series2018.getData().add(new XYChart.Data("1�б�",one));
			series2018.getData().add(new XYChart.Data("2�б�",two));
			series2018.getData().add(new XYChart.Data("3�б�",three));
			series2018.getData().add(new XYChart.Data("4�б�",four));
			
			barChart.getData().add(series2018);
			
			Button btnClose = (Button) parent.lookup("#btnClose");
			btnClose.setOnAction(e->dialog.close());
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
			//���� �׷��� �̹��� ����
			WritableImage snapShot = scene.snapshot(null);
			ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png",
					new File("chartImage/QuarterSalesBarChart.png"));
			
			}catch(IOException e) {
				
			}
			
		}
		
		
	//���� ���� ��Ʈ
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
				dialog.setTitle("�� �� ����");
				
				Parent parent = FXMLLoader.load(getClass().getResource("/view/MonthBarChart.fxml"));
				
				BarChart barChart = (BarChart) parent.lookup("#barChartMonth");
				
				XYChart.Series series2018 = new XYChart.Series();
				series2018.setName("2018�� �� ����");
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
				
				series2018.getData().add(new XYChart.Data("1��",salesJan));
				series2018.getData().add(new XYChart.Data("2��",salesFeb));
				series2018.getData().add(new XYChart.Data("3��",salesMar));
				series2018.getData().add(new XYChart.Data("4��",salesApr));
				series2018.getData().add(new XYChart.Data("5��",salesMay));
				series2018.getData().add(new XYChart.Data("6��",salesJun));
				series2018.getData().add(new XYChart.Data("7��",salesJul));
				series2018.getData().add(new XYChart.Data("8��",salesAug));
				series2018.getData().add(new XYChart.Data("9��",salesSep));
				series2018.getData().add(new XYChart.Data("10��",salesOct));
				series2018.getData().add(new XYChart.Data("11��",salesNov));
				series2018.getData().add(new XYChart.Data("12��",salesDec));
				
				barChart.getData().add(series2018);
				
				Button btnClose = (Button) parent.lookup("#btnClose");
				btnClose.setOnAction(e->dialog.close());
				
				Scene scene = new Scene(parent);
				dialog.setScene(scene);
				dialog.show();
				
				//���� �׷��� �̹��� ����
				WritableImage snapShot = scene.snapshot(null);
				ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png",
						new File("chartImage/monthSalesBarChart.png"));
				
				
			}catch(IOException e) {
				
			}
		}
	
		
	//��¥������ ��ü ����
	private void handlerBtnShowSaleDateAction(ActionEvent event) {

			try {
				dayData.removeAll(dayData);
				DaySalesList();
			}catch(Exception e) {
				
			}
			
		}
		
		
	//��¥ �˻�
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
					alert.setTitle("��¥ �˻�");
					alert.setHeaderText(null);
					alert.setContentText("�ش� ��¥�� ������ �����ϴ�.");
					alert.showAndWait();
				}
			}catch(NullPointerException e) {
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("��¥ �˻�");
				alert.setHeaderText(null);
				alert.setContentText("��¥�� �������ּ���.");
				alert.showAndWait();

			}catch(Exception e) {
				System.out.println(e);
			}
			
		}
		
		
	//��¥�� ���� ����Ʈ
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
		
		
	//������ ���� ����Ʈ
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

		
	//ī��纰 ���� ����Ʈ
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
		
		
		
	
	
	//---------��1---------
	
	
	//�α׾ƿ� ��ư
	private void handlerBtnLogOutAction(ActionEvent event) {

		try {

			Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene(root);
			Stage mainStage = new Stage();
			mainStage.setTitle("�α���");
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			Stage oldStage = (Stage) btnLogOut.getScene().getWindow();
			oldStage.close();
			mainStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	//�ð��뺰 ����Ʈ
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
			dialog.setTitle("�ð��� �ο� ��");
			
			Parent parent = FXMLLoader.load(getClass().getResource("/view/barchart.fxml"));
			
			BarChart barChart = (BarChart) parent.lookup("#barChart");
			
			XYChart.Series seriesTime = new XYChart.Series();
			seriesTime.setName("�ο� ��");
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
			seriesTime.getData().add(new XYChart.Data("10��",total10));
			seriesTime.getData().add(new XYChart.Data("11��",total11));
			seriesTime.getData().add(new XYChart.Data("12��",total12));
			seriesTime.getData().add(new XYChart.Data("1��",total13));
			seriesTime.getData().add(new XYChart.Data("2��",total14));
			seriesTime.getData().add(new XYChart.Data("3��",total15));
			seriesTime.getData().add(new XYChart.Data("4��",total16));
			seriesTime.getData().add(new XYChart.Data("5��",total17));
			seriesTime.getData().add(new XYChart.Data("6��",total18));
			seriesTime.getData().add(new XYChart.Data("7��",total19));
			seriesTime.getData().add(new XYChart.Data("8��",total20));
			seriesTime.getData().add(new XYChart.Data("9��",total21));
			barChart.getData().add(seriesTime);
			
			Button btnClose = (Button) parent.lookup("#btnClose");
			btnClose.setOnAction(e->dialog.close());
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
		}catch(IOException e) {
			
		}
		
	}

	//������
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


	//�����̿Ϸ� �� ��ȸ
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


	//�����Ϸ�� ��ȸ
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


	//�湮�� ��ȸ
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
		
		String section = "�湮";
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


	//����� ��ȸ
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
		
		String section = "����";
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


	//�����ڸ����� �˻�
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
				alert.setTitle("������ �˻�");
				alert.setHeaderText(null);
				alert.setContentText("�����ڸ��� �Է����ּ���.");
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
				alert.setTitle("������ �˻�");
				alert.setHeaderText(null);
				alert.setContentText("�ش� �����ڰ� �����ϴ�.");
				alert.showAndWait();
			}
			
		}catch(Exception e) {
			Alert alert = new Alert (AlertType.WARNING);
			alert.setTitle("�˻� ����");
			alert.setHeaderText(null);
			alert.setContentText("�ٽ� �õ����ּ���.");
			alert.showAndWait();
		}
	}

	
	//��ü�� ��ȸ
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

	
	//���ݰ���
	private void handlerBtnPayCash(ActionEvent event) {
		
		try {
			
			//�� â ����
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PayCash.fxml"));
			
			Stage mainStage = new Stage();
			mainStage.setTitle("���ݰ���");
			mainStage.initModality(Modality.WINDOW_MODAL);
			mainStage.initOwner(btnPayCash.getScene().getWindow());
			
			Parent mainView = (Parent) loader.load();
			OrderVO orderPayCash = tableViewMain.getSelectionModel().getSelectedItem();
			selectOrder = tableViewMain.getSelectionModel().getSelectedItems();
			
			if(orderPayCash.getPay().equals("Y")) {
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("����");
				alert.setHeaderText(null);
				alert.setContentText("���� �Ϸ� �� ���Դϴ�.");
				alert.showAndWait();
			}else {
			
				
				
			//�Է�â
			TextField no = (TextField) mainView.lookup("#txtNo");
			TextField payTotalPrice = (TextField) mainView.lookup("#txtTotalPrice");
			TextField money = (TextField) mainView.lookup("#txtMoney");
			TextField change = (TextField) mainView.lookup("#txtChange");
			DatePicker editPayDay = (DatePicker) mainView.lookup("#dpPayDay");
			
			//�Է�����
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
			
			//��ư
			Button btnOk = (Button) mainView.lookup("#btnOk");
			Button btnCancle = (Button) mainView.lookup("#btnCancle");
			Button btnComplete = (Button) mainView.lookup("#btnComplete");
			
			btnComplete.setDisable(true);
			
			
			//�Ž����� ���
			btnOk.setOnAction(e->{
				
				if(money.getText().equals("")||Integer.parseInt(money.getText().trim())<=0) {
					Alert alert = new Alert (AlertType.WARNING);
					alert.setTitle("���� �ݾ� �Է�");
					alert.setHeaderText(null);
					alert.setContentText("���� �ݾ��� 0���� Ŀ���մϴ�.");
					alert.showAndWait();
				
				}else if(Integer.parseInt(money.getText().trim())<Integer.parseInt(payTotalPrice.getText().trim())) {
					Alert alert = new Alert (AlertType.WARNING);
					alert.setTitle("�ݾ� ����");
					alert.setHeaderText(null);
					alert.setContentText("�ݾ��� �����մϴ�. �ٽ� �Է��ϼ���.");
					alert.showAndWait();
				
				}else {
					int result;
					
					result = Integer.parseInt(money.getText())-Integer.parseInt(payTotalPrice.getText());
					change.setText(result+"");
					
					btnComplete.setDisable(false);
					
				}
				
			});
			
			//�����Ϸ� ��ư
			btnComplete.setOnAction(e->{
				OrderVO ovo = null;
				OrderDAO odao = null;
				
				TextField txtNo = (TextField) mainView.lookup("#txtNo");
				DatePicker PayDay = (DatePicker) mainView.lookup("#dpPayDay");
				
				data.remove(selectOrder);
				
				try {
					ovo = new OrderVO(Integer.parseInt(txtNo.getText()),"Y","����",
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
			
			//��ҹ�ư
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
			System.err.println("����" + e);
		}
	}

	
	//ī�����
	private void handlerBtnPayCard(ActionEvent event) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PayCard.fxml"));
			
			Stage mainStage = new Stage();
			mainStage.setTitle("ī�����");
			mainStage.initModality(Modality.WINDOW_MODAL);
			mainStage.initOwner(btnPayCash.getScene().getWindow());
			
			Parent mainView = (Parent) loader.load();
			OrderVO orderPayCard = tableViewMain.getSelectionModel().getSelectedItem();
			selectOrder = tableViewMain.getSelectionModel().getSelectedItems();
			
			if(orderPayCard.getPay().equals("Y")) {
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("����");
				alert.setHeaderText(null);
				alert.setContentText("���� �Ϸ� �� ���Դϴ�.");
				alert.showAndWait();
			}else {
			
			//�Է�â
			TextField no = (TextField) mainView.lookup("#txtNo");
			TextField payTotalPrice = (TextField) mainView.lookup("#txtTotalPrice");
			ComboBox<String> card = (ComboBox<String>) mainView.lookup("#cbCard");
			TextField cardNumber = (TextField) mainView.lookup("#txtCardNumber");
			DatePicker editPayDay = (DatePicker) mainView.lookup("#dpPayDay");
			
			//�Է�����
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
			card.setItems(FXCollections.observableArrayList("����","�츮","�ϳ�","����",
					"īī����ũ","K��ũ","����","�Ե�","����"));
			editPayDay.setValue(LocalDate.now());
			editPayDay.setEditable(false);
			
			//��ư
			Button btnOk = (Button) mainView.lookup("#btnOk");
			Button btnCancle = (Button) mainView.lookup("#btnCancle");
			Button btnComplete = (Button) mainView.lookup("#btnComplete");
			
			btnComplete.setDisable(true);
			
			
			//���ι�ư
			btnOk.setOnAction(e->{
				
				if(card.getSelectionModel().getSelectedItem()==null
						|| cardNumber.getText()==null) {
					Alert alert = new Alert (AlertType.WARNING);
					alert.setTitle("�Է� ����");
					alert.setHeaderText(null);
					alert.setContentText("��Ȯ�� �Է��ߴ��� Ȯ�����ּ���.");
					alert.showAndWait();
				}else {
					Alert alert = new Alert (AlertType.INFORMATION);
					alert.setTitle("ī�� ����");
					alert.setHeaderText(null);
					alert.setContentText("���� �Ǿ����ϴ�.");
					alert.showAndWait();
					
					btnOk.setDisable(true);
					editPayDay.setDisable(true);
					
					btnComplete.setDisable(false);
				}
				
			});
			
			
			//�����Ϸ�
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
							"ī��",dpPayDay.getValue().toString(),
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
			
			
			//��ҹ�ư
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
			System.err.println("����" + e);
		}
	}

	
	//���̺�� Ŭ��
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


	//�ֹ� ����
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


	//�������� ���
	private void handlerBtnBookOkAction(ActionEvent event) {
		
		OrderDAO odao =null;
		
		int searchTable_no = 0;
		String searchB_date = "-";
		
		boolean sucess=false;
		
		
		//�������� ���Է� ����
		if(txtBname.getText().equals("-")||txtBphone.getText().equals("-")
				||cbHour.getSelectionModel().getSelectedItem().equals("-")
				||cbMinute.getSelectionModel().getSelectedItem().equals("-")) {
			
			Alert alert = new Alert (AlertType.WARNING);
			alert.setTitle("�Է� ����");
			alert.setHeaderText(null);
			alert.setContentText("���Էµ� ���� Ȯ�����ּ���.");
			alert.showAndWait();
			
		}else {
		
			if(dpBday.getValue().isBefore(LocalDate.now())) {
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("��¥ ����");
				alert.setHeaderText(null);
				alert.setContentText("���� ���� ��¥�� ���� �� �� �����ϴ�.");
				alert.showAndWait();
			}else {
			
		//���೯¥, ���̺� �ߺ� �˻�
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
			alert.setTitle("�Է� ����");
			alert.setHeaderText(null);
			alert.setContentText("���೯¥�� �������ּ���.");
			alert.showAndWait();
		}
		
		if(!sucess) {
		
		//�������� �Է� ����
		cbTableNo.setDisable(true);
		txtBname.setDisable(true);
		txtBphone.setDisable(true);
		dpBday.setDisable(true);
		cbHour.setDisable(true);
		cbMinute.setDisable(true);
		btnBookOk.setDisable(true);
		//�ֹ����� �Է� Ǯ��
		txtAdult.setDisable(false);
		rbLAdult.setDisable(false);
		rbDAdult.setDisable(false);
		txtScChild.setDisable(false);
		txtPsChild.setDisable(false);
		txtSide.setDisable(false);
		btnOrderOk.setDisable(false);
		
		if(Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=1
				&&Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=20) {
		//1~20�� ���̺� �˶�	
		alert = new Alert (AlertType.INFORMATION);
		alert.setTitle("���̺� ���� �Է�");
		alert.setHeaderText(cbTableNo.getSelectionModel().getSelectedItem()+"�� ���̺� ����(5�μ�)");
		alert.setContentText(customerGroup.getSelectedToggle().getUserData().toString()+" ����");
		alert.showAndWait();
		
		}else if(Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=21
				&&Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=30) {
		//21~30�� ���̺� �˶�	
		alert = new Alert (AlertType.INFORMATION);
		alert.setTitle("���̺� ���� �Է�");
		alert.setHeaderText(cbTableNo.getSelectionModel().getSelectedItem()+"�� ���̺� ����(7�μ�)");
		alert.setContentText(customerGroup.getSelectedToggle().getUserData().toString()+" ����");
		alert.showAndWait();
		
		}
		
		}else {
			alert = new Alert (AlertType.WARNING);
			alert.setTitle("���� �Ұ�");
			alert.setHeaderText(null);
			alert.setContentText("�̹� ����� ���̺��Դϴ�.");
			alert.showAndWait();
		}
		
		}	
		}
	}


	//�ֹ���� ����Ʈ
	private void totalList() {

		Object[][] totalData;
		
		OrderDAO odao = new OrderDAO();
		OrderVO ovo = new OrderVO();
		ArrayList<String> title;
		ArrayList<OrderVO> list;
		
		//��
		title = odao.getColumnName();
		int columnCount = title.size();
		
		//��
		list = odao.getOrderTotal();
		int rowCount = list.size();
		
		totalData = new Object[rowCount][columnCount];
		
		for(int index = 0; index<rowCount; index++) {
			ovo = list.get(index);
			data.add(ovo);
		}
		
	}


	//�ֹ����
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
			alert.setTitle("�Է� ����");
			alert.setHeaderText(null);
			alert.setContentText("������ ��Ȯ�� �Է��ϼ���.");
			alert.showAndWait();
		}
		
	}

	
	//���̺����� Ȯ��
	private void handlerBtnTableOkAction(ActionEvent event) {

		OrderDAO odao = null;
		int searchBTable = 0;//�湮�� ���̺��ȣ �ߺ��˻�
		String searchEName = "";//����� �̸� �˻�
		boolean searchBResult = true;
		boolean searchEResult = true;
		
		try {
			
			
			//����� �̸� �˻�
			try {
				
				searchEName = txtEname.getText();
				odao = new OrderDAO();
				searchEResult = (boolean) odao.getEnameOverlap(searchEName);
				
				if(searchEResult && !searchEName.equals("")) {
					
					//�湮��
					if(customerGroup.getSelectedToggle().getUserData().toString().equals("�湮")) {
						
				
				//���̺��ȣ �ߺ� �˻�
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
						//1~20�� ���̺� �˶�	
						Alert alert = new Alert (AlertType.INFORMATION);
						alert.setTitle("���̺� ���� �Է�");
						alert.setHeaderText(cbTableNo.getSelectionModel().getSelectedItem()+"�� ���̺� ����(5�μ�)");
						alert.setContentText(customerGroup.getSelectedToggle().getUserData().toString()+" ����");
						alert.showAndWait();
						
						}else if(Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=21
								&&Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=30) {
						//21~30�� ���̺� �˶�	
						Alert alert = new Alert (AlertType.INFORMATION);
						alert.setTitle("���̺� ���� �Է�");
						alert.setHeaderText(cbTableNo.getSelectionModel().getSelectedItem()+"�� ���̺� ����(7�μ�)");
						alert.setContentText(customerGroup.getSelectedToggle().getUserData().toString()+" ����");
						alert.showAndWait();
						
						}
						
					}else if(searchBTable==0) {
						Alert alert = new Alert (AlertType.WARNING);
						alert.setTitle("�Է� ����");
						alert.setHeaderText(null);
						alert.setContentText("���̺� ��ȣ�� ������ �ּ���.");
						alert.showAndWait();
					}else {
						cbTableNo.getSelectionModel().clearSelection();
						
						Alert alert = new Alert (AlertType.WARNING);
						alert.setTitle("���̺� �ߺ�");
						alert.setHeaderText(null);
						alert.setContentText("�̹� �Լ��� ���̺��Դϴ�.");
						alert.showAndWait();
					}
					
				}catch(Exception e) {
					Alert alert = new Alert (AlertType.WARNING);
					alert.setTitle("�Է� ����");
					alert.setHeaderText(null);
					alert.setContentText("��Ȯ�ϰ� �Է��ϼ���.");
					alert.showAndWait();
				}
				
				
			//�����	
			}else if(customerGroup.getSelectedToggle().getUserData().toString().equals("����")) {
				
				
				if(cbTableNo.getSelectionModel().getSelectedItem().equals("")) {
					Alert alert = new Alert (AlertType.WARNING);
					alert.setTitle("�Է� ����");
					alert.setHeaderText(null);
					alert.setContentText("���̺� ��ȣ�� �������ּ���.");
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
					alert.setTitle("�Է� ����");
					alert.setHeaderText(null);
					alert.setContentText("����� �̸��� �Է����ּ���.");
					alert.showAndWait();
					
				}else {
					Alert alert = new Alert (AlertType.WARNING);
					alert.setTitle("�Է� ����");
					alert.setHeaderText(null);
					alert.setContentText("�ش� ������ �������� �ʰų� ����� �����Դϴ�.");
					alert.showAndWait();
					txtEname.requestFocus();
				}
			
			
			}catch(Exception e) {
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("�Է� ����");
				alert.setHeaderText(null);
				alert.setContentText("��Ȯ�ϰ� �Է��ϼ���.");
				alert.showAndWait();
			}
			
		}catch(Exception e) {
			Alert alert = new Alert (AlertType.WARNING);
			alert.setTitle("�Է� ����");
			alert.setHeaderText(null);
			alert.setContentText("��Ȯ�ϰ� �Է��ϼ���.");
			alert.showAndWait();
			
			cbTableNo.setDisable(false);
			txtEname.setDisable(false);
			rbBook.setDisable(false);
			rbVisit.setDisable(false);
		}
		
	}


	//�޴�Ȯ��
	private void handlerBtnOrderOkAction(ActionEvent event) {
		
		try {
		
		int adult = Integer.parseInt(txtAdult.getText());
		int childSc = Integer.parseInt(txtScChild.getText());
		int childPs = Integer.parseInt(txtPsChild.getText());
		int side = Integer.parseInt(txtSide.getText());
		
		//�� �ο�
		int totalCount;
		totalCount = adult+childSc+childPs;
		txtTotal.setText(totalCount+"");
		
		if(totalCount!=0) {
		
		//�� ����
		int totalPrice;
		
		//���ɰ�
		if(adultGroup.getSelectedToggle().getUserData().toString().equals("����")) {
			
			totalPrice = (adult*AL)+(childSc*SC)+(childPs*PS)+(side*SD);
			
			//�ִ��ο� ����
			if((Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=1 
				&& Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=20
				&& totalCount<=5) || (Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=21&&
					Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=30 && totalCount<=7)) {
				
				txtTotalPrice.setText(totalPrice+"");
				btnOk.setDisable(false);
				//�ֹ����� �Է� ����
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
				alert.setTitle("�ִ��ο� �ʰ�");
				alert.setHeaderText("�ִ��ο��� �ʰ��߽��ϴ�. �ο� ���� Ȯ�����ּ���.");
				alert.setContentText("1~20�� ���̺� �ִ��ο� 5��"+"\n 21~30���̺� �ִ��ο� 7��");
				alert.showAndWait();
				
			}
			
			
		//���ᰪ	
		}else if(adultGroup.getSelectedToggle().getUserData().toString().equals("����")) {
			
			totalPrice = (adult*AD)+(childSc*SC)+(childPs*PS)+(side*SD);
			
			//�ִ��ο� ����
			if((Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=1 
					&& Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=20
					&& totalCount<=5) || (Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())>=21&&
							Integer.parseInt(cbTableNo.getSelectionModel().getSelectedItem().trim())<=30 && totalCount<=7)) {
				
				txtTotalPrice.setText(totalPrice+"");
				btnOk.setDisable(false);
				//�ֹ����� �Է� ����
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
				alert.setTitle("�ִ��ο� �ʰ�");
				alert.setHeaderText("�ִ��ο��� �ʰ��߽��ϴ�. �ο� ���� Ȯ�����ּ���.");
				alert.setContentText("1~20�� ���̺� �ִ��ο� 5��"+"\n 21~30���̺� �ִ��ο� 7��");
				alert.showAndWait();
				}
		}
		
		}else {
			Alert alert = new Alert (AlertType.WARNING);
			alert.setTitle("���� �Է�");
			alert.setHeaderText(null);
			alert.setContentText("������ �Է����ּ���.");
			alert.showAndWait();
		}
		
		}catch(Exception e) {
			Alert alert = new Alert (AlertType.WARNING);
			alert.setTitle("�Է� ����");
			alert.setHeaderText(null);
			alert.setContentText("�Է����� ���� ���� �ֽ��ϴ�.");
			alert.showAndWait();
		}
	
	}

	
	//���
	private void handlerBtnCancleAction(ActionEvent event) {

		//���̺����� �ʱ�ȭ
		cbTableNo.setDisable(false);
		cbTableNo.getSelectionModel().clearSelection();
		txtEname.setDisable(false);
		txtEname.clear();
		customerGroup.selectToggle(null);
		rbBook.setDisable(false);
		rbVisit.setDisable(false);
		btnTableOk.setDisable(false);
		
		//�������� �ʱ�ȭ
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
		
		//�ֹ����� �ʱ�ȭ
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
	
	
	//���α׷� ����
	private void handlerBtnExitAction(ActionEvent event) {

		Platform.exit();
	
	}

}
