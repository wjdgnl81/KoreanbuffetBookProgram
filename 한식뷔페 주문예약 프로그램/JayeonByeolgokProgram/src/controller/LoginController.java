package controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;

import javax.swing.JViewport;

import com.sun.javafx.image.impl.ByteIndexed.Getter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.EmployeeVO;
import model.JoinVO;
import model.SalesVO;

public class LoginController implements Initializable {

	@FXML
	private TextField txtCode;
	@FXML
	private PasswordField pwPasswd;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnRegisterE;
	@FXML
	private Button btnExit;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// 직원코드는 숫자만(6자제한)
		DecimalFormat format = new DecimalFormat("######");
		txtCode.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}

			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 7) {
				return null;
			} else {
				return event;
			}

		}));

		btnLogin.setOnAction(event -> handlerBtnLoginAction(event));
		btnRegisterE.setOnAction(event -> handlerBtnRegisterEAction(event));
		btnExit.setOnAction(event -> handlerBtnExitAction(event));

	}

	// 로그인
	private void handlerBtnLoginAction(ActionEvent event) {

		LoginDAO logDao = new LoginDAO();

		boolean sucess = false;
		String ename = null;
		String esection = null;

		try {
			sucess = logDao.getLogin(txtCode.getText().trim(), pwPasswd.getText().trim());
			ename = logDao.getEname(txtCode.getText().trim());
			esection = logDao.getEsection(txtCode.getText().trim());
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Alert alert;
		if (txtCode.getText().equals("") || pwPasswd.getText().equals("")) {
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("로그인 실패");
			alert.setHeaderText("아이디, 비밀번호 미입력");
			alert.setContentText("아이디와 비밀번호 중 입력하지 않은 항목 존재" + "\n 다시 입력하세요.");
			alert.setResizable(false);
			alert.showAndWait();
		}

		
		// 로그인 성공시 메인 페이지로
		if (sucess) {
			
			if(esection.equals("직원")) {
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scene = new Scene(mainView);
				Stage mainStage = new Stage();
				
				try {
				mainStage.setTitle("자연별곡 주문/예약 관리");
				mainStage.setResizable(false);
				mainStage.setScene(scene);
				Stage oldStage = (Stage) btnLogin.getScene().getWindow();
				oldStage.close();
				mainStage.show();
				
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("WELCOME");
				alert.setHeaderText(null);
				alert.setContentText(ename+" 메이트님 로그인 성공");
				alert.setResizable(false);
				alert.showAndWait();
			
				
				}catch(Exception e) {
				}
			} catch (IOException e) {
				System.err.println("오류" + e);
			}
			
			}else{
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainManager.fxml"));
					Parent mainView = (Parent) loader.load();
					Scene scene = new Scene(mainView);
					Stage mainStage = new Stage();
					
					try {
					mainStage.setTitle("자연별곡 주문/예약관리");
					mainStage.setResizable(false);
					mainStage.setScene(scene);
					Stage oldStage = (Stage) btnLogin.getScene().getWindow();
					oldStage.close();
					mainStage.show();
					
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("WELCOME");
					alert.setHeaderText(null);
					alert.setContentText(ename+" 관리자님 로그인 성공");
					alert.setResizable(false);
					alert.showAndWait();
				
					
					}catch(Exception e) {
					}
				} catch (IOException e) {
					System.err.println("오류" + e);
				}
			}
		} else {
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("로그인 실패");
			alert.setHeaderText("아이디, 비밀번호 불일치");
			alert.setContentText("아이디와 비밀번호가 일치하지 않습니다.");
			alert.setResizable(false);
			alert.showAndWait();

			txtCode.clear();
			pwPasswd.clear();
		}
	}

	// 직원 등록창으로 전환
	private void handlerBtnRegisterEAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Registe.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("직원 등록");
			mainStage.setScene(scene);
			mainStage.initModality(Modality.WINDOW_MODAL);
			mainStage.initOwner(btnRegisterE.getScene().getWindow());
			mainStage.setResizable(false);
			mainStage.show();
		} catch (IOException e) {
			System.err.println("오류" + e);
		}
	}
	
	//종료
	private void handlerBtnExitAction(ActionEvent event) {

		Platform.exit();

	}

}
