package controller;


import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import model.JoinVO;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class JoinController implements Initializable{

	@FXML
	private ComboBox<String> cbEsection;
	@FXML
	private TextField txtEname;
	@FXML
	private TextField txtEcode;
	@FXML
	private PasswordField pwEpasswd;
	@FXML
	private PasswordField pwEpasswdOk;
	@FXML
	private Button btnRegisterOk;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnOverlap;
	@FXML
	private DatePicker dpEnter;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		dpEnter.setValue(LocalDate.now());
		
		//중복코드를 누르기 전에는 등록버튼을 막음
		btnRegisterOk.setDisable(true);
		
		//숫자만 입력, 6자 제한
		DecimalFormat format = new DecimalFormat("######");
		txtEcode.setTextFormatter(new TextFormatter<>(event->{
			if(event.getControlNewText().isEmpty()) {
				return event;
			}
			
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(),parsePosition);
			
			if(object == null || parsePosition.getIndex()<event.getControlNewText().length()
					||event.getControlNewText().length()==7) {
				return null;
			}else {
				return event;
			}
			
		}));
		
		cbEsection.setItems(FXCollections.observableArrayList("직원","관리자"));
		
		btnRegisterOk.setOnAction(event->handlerBtnRegisterOkAction(event));
		btnOverlap.setOnAction(event->handlerBtnOverlapAction(event));
		btnExit.setOnAction(event->handlerBtnExitAction(event));
		
	}

	//직원 등록
	private void handlerBtnRegisterOkAction(ActionEvent event) {
		JoinVO jvo = null;
		JoinDAO jdao = null;
		
		boolean joinSucess = false;
		
		//패스워드 확인
		if(pwEpasswd.getText().trim().equals(pwEpasswdOk.getText().trim())) {
			jvo = new JoinVO(txtEname.getText().trim(),cbEsection.getSelectionModel().getSelectedItem(),
					txtEcode.getText().trim(),pwEpasswd.getText().trim(),dpEnter.getValue().toString());
			jdao = new JoinDAO();
			
			try {
				joinSucess = jdao.getEmployeeRegiste(jvo);
				
				if(joinSucess) {
					handlerBtnExitAction(event);
				}
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	//코드 중복확인
	private void handlerBtnOverlapAction(ActionEvent event) {
		
		JoinDAO jDao = null;
		
		String searchCode = "";
		boolean searchResult = true;
		
		try {
			searchCode = txtEcode.getText().trim();
			jDao = new JoinDAO();
			searchResult = (boolean) jDao.getCodeOverlap(searchCode);
			
			if(!searchResult && !searchCode.equals("")) {
				txtEcode.setDisable(true);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("코드 중복검사");
				alert.setHeaderText(null);
				alert.setContentText(searchCode+" 코드를 사용할 수 있습니다.");
				alert.showAndWait();
				
				btnRegisterOk.setDisable(false);
				pwEpasswd.requestFocus();
			}else if(searchCode.equals("")) {
				btnRegisterOk.setDisable(true);
				
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("코드 중복검사");
				alert.setHeaderText(null);
				alert.setContentText("등록할 코드를 입력하세요.");
				alert.showAndWait();
				
				txtEcode.requestFocus();
				
			}else {
				btnRegisterOk.setDisable(true);
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("코드 중복검사");
				alert.setHeaderText(null);
				alert.setContentText("해당 코드가 이미 존재합니다.");
				alert.showAndWait();
				
				txtEcode.requestFocus();
			}
		}catch(Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("코드 중복검사 오류");
			alert.setHeaderText(null);
			alert.setContentText("다시 시도해주세요.");
			alert.showAndWait();
		}
	}

	//등록창 닫기
	private void handlerBtnExitAction(ActionEvent event) {
		Stage oldStage = (Stage) btnExit.getScene().getWindow();
		oldStage.close();
	}
	
	
	
}
