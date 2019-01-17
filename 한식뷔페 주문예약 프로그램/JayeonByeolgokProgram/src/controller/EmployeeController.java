package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.PromptData;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.EmployeeVO;
import model.JoinVO;

public class EmployeeController implements Initializable {

	@FXML
	private TableView<EmployeeVO> tableViewEmployee;
	@FXML
	private DatePicker dpResignation;//�����
	@FXML
	private Button btnResignation;//����ư
	@FXML
	private Button btnExit;//�ݱ��ư
	@FXML
	private Button btnPromotion;//�����±�
	
	EmployeeVO evo = new EmployeeVO();
	ObservableList<EmployeeVO> data = FXCollections.observableArrayList();
	ObservableList<EmployeeVO> selectDelete = FXCollections.observableArrayList();
	
	int no;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnPromotion.setDisable(true);
		dpResignation.setValue(null);
		dpResignation.setEditable(false);
		btnResignation.setDisable(true);
		
		//���� ���̺��
		tableViewEmployee.setEditable(false);
		
		TableColumn colNo = new TableColumn("NO.");
		colNo.setMaxWidth(40);
		colNo.setStyle("-fx-alignment: CENTER");
		colNo.setCellValueFactory(new PropertyValueFactory<>("e_no"));
		
		TableColumn colName = new TableColumn("�̸�");
		colName.setMaxWidth(60);
		colName.setStyle("-fx-alignment: CENTER");
		colName.setCellValueFactory(new PropertyValueFactory<>("e_name"));
		
		TableColumn colSection = new TableColumn("����");
		colSection.setMaxWidth(60);
		colSection.setStyle("-fx-alignment: CENTER");
		colSection.setCellValueFactory(new PropertyValueFactory<>("e_section"));
		
		TableColumn colCode = new TableColumn("�����ڵ�");
		colCode.setMaxWidth(90);
		colCode.setStyle("-fx-alignment: CENTER");
		colCode.setCellValueFactory(new PropertyValueFactory<>("e_code"));
		
		TableColumn colEntr = new TableColumn("�Ի���");
		colEntr.setMinWidth(130);
		colEntr.setStyle("-fx-alignment: CENTER");
		colEntr.setCellValueFactory(new PropertyValueFactory<>("e_entrada"));
		
		TableColumn colResig = new TableColumn("�����");
		colResig.setMinWidth(130);
		colResig.setStyle("-fx-alignment: CENTER");
		colResig.setCellValueFactory(new PropertyValueFactory<>("e_resignation"));

		tableViewEmployee.setItems(data);
		tableViewEmployee.getColumns().addAll(colNo,colName,colSection,colCode,colEntr,colResig);
		
		employeeList();
		
		
		//��ư �׼�
		btnPromotion.setOnAction(event->handlerBtnPromoion(event));//�±޹�ư
		tableViewEmployee.setOnMouseClicked(event->handlertClickAction(event));//���̺�� ����
		btnResignation.setOnAction(event->handlerResignationAction(event));//����ư
		btnExit.setOnAction(event->handlerBtnExitAction(event));//�ݱ��ư
	}

	
	
	//�±�
	private void handlerBtnPromoion(ActionEvent event) {

		btnPromotion.setDisable(true);
		dpResignation.setValue(null);
		dpResignation.setEditable(false);
	
		EmployeeVO evo = null;
		EmployeeDAO edao = new EmployeeDAO();
		
		try {
			
			evo = tableViewEmployee.getSelectionModel().getSelectedItem();
			selectDelete = tableViewEmployee.getSelectionModel().getSelectedItems();
			
			if(evo.getE_section().equals("������")) {
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("���� �±�");
				alert.setHeaderText(null);
				alert.setContentText("�̹� �ְ� �����Դϴ�.");
				alert.showAndWait();
				
			}else if(!evo.getE_resignation().equals("-")) {
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("���� �±�");
				alert.setHeaderText(null);
				alert.setContentText("����� ������ �±��� �� �����ϴ�.");
				alert.showAndWait();
			}else{
				edao.getEmployeePromotion(no);
				data.removeAll(data);
				employeeList();
				dpResignation.setValue(null);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}




	//����ư
	private void handlerResignationAction(ActionEvent event) {
		btnResignation.setDisable(true);
		btnPromotion.setDisable(true);
		
		EmployeeVO evo = null;
		EmployeeDAO edao = new EmployeeDAO();
		
		try {
			
			evo = tableViewEmployee.getSelectionModel().getSelectedItem();
			selectDelete = tableViewEmployee.getSelectionModel().getSelectedItems();
			
			if(!evo.getE_resignation().equals("-")) {
				Alert alert = new Alert (AlertType.WARNING);
				alert.setTitle("��� ���");
				alert.setHeaderText(null);
				alert.setContentText("�̹� ����� �����Դϴ�.");
				alert.showAndWait();
				
			}else if(!evo.getE_resignation().equals("")){
				evo = new EmployeeVO(dpResignation.getValue().toString());
				edao.getEmployeeResignation(evo, no);
				data.removeAll(data);
				employeeList();
				dpResignation.setValue(null);
				dpResignation.setEditable(false);
			}
			
		}catch(NullPointerException e) {
			Alert alert = new Alert (AlertType.WARNING);
			alert.setTitle("��� ���");
			alert.setHeaderText(null);
			alert.setContentText("������� �������ּ���.");
			alert.showAndWait();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}



	//�ݱ��ư
	private void handlerBtnExitAction(ActionEvent event) {
		Stage oldStage = (Stage) btnExit.getScene().getWindow();
		oldStage.close();
	}


	//���̺�� Ŭ��
	private void handlertClickAction(MouseEvent event) {

		try {
			selectDelete = tableViewEmployee.getSelectionModel().getSelectedItems();
			no = selectDelete.get(0).getE_no();
			btnResignation.setDisable(false);
			btnPromotion.setDisable(false);
		}catch(Exception e) {
			
		}
		
	}

	//��ü���
	public void employeeList() {
		Object[][] totalData;
		
		EmployeeDAO edao = new EmployeeDAO();
		EmployeeVO evo = new EmployeeVO();
		ArrayList<String> title;
		ArrayList<EmployeeVO> list;
		
		title = edao.getEmployeeColumnName();
		int columnCount = title.size();
		
		list = edao.getEmployeeList();
		int rowCount = list.size();
		
		totalData = new Object[rowCount][columnCount];
		
		for(int index = 0; index<rowCount; index++) {
			evo = list.get(index);
			data.add(evo);
		}
	}
	
	
	
}
