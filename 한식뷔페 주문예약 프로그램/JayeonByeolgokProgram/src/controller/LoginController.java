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
		
		// �����ڵ�� ���ڸ�(6������)
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

	// �α���
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
			alert.setTitle("�α��� ����");
			alert.setHeaderText("���̵�, ��й�ȣ ���Է�");
			alert.setContentText("���̵�� ��й�ȣ �� �Է����� ���� �׸� ����" + "\n �ٽ� �Է��ϼ���.");
			alert.setResizable(false);
			alert.showAndWait();
		}

		
		// �α��� ������ ���� ��������
		if (sucess) {
			
			if(esection.equals("����")) {
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scene = new Scene(mainView);
				Stage mainStage = new Stage();
				
				try {
				mainStage.setTitle("�ڿ����� �ֹ�/���� ����");
				mainStage.setResizable(false);
				mainStage.setScene(scene);
				Stage oldStage = (Stage) btnLogin.getScene().getWindow();
				oldStage.close();
				mainStage.show();
				
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("WELCOME");
				alert.setHeaderText(null);
				alert.setContentText(ename+" ����Ʈ�� �α��� ����");
				alert.setResizable(false);
				alert.showAndWait();
			
				
				}catch(Exception e) {
				}
			} catch (IOException e) {
				System.err.println("����" + e);
			}
			
			}else{
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainManager.fxml"));
					Parent mainView = (Parent) loader.load();
					Scene scene = new Scene(mainView);
					Stage mainStage = new Stage();
					
					try {
					mainStage.setTitle("�ڿ����� �ֹ�/�������");
					mainStage.setResizable(false);
					mainStage.setScene(scene);
					Stage oldStage = (Stage) btnLogin.getScene().getWindow();
					oldStage.close();
					mainStage.show();
					
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("WELCOME");
					alert.setHeaderText(null);
					alert.setContentText(ename+" �����ڴ� �α��� ����");
					alert.setResizable(false);
					alert.showAndWait();
				
					
					}catch(Exception e) {
					}
				} catch (IOException e) {
					System.err.println("����" + e);
				}
			}
		} else {
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("�α��� ����");
			alert.setHeaderText("���̵�, ��й�ȣ ����ġ");
			alert.setContentText("���̵�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			alert.setResizable(false);
			alert.showAndWait();

			txtCode.clear();
			pwPasswd.clear();
		}
	}

	// ���� ���â���� ��ȯ
	private void handlerBtnRegisterEAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Registe.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("���� ���");
			mainStage.setScene(scene);
			mainStage.initModality(Modality.WINDOW_MODAL);
			mainStage.initOwner(btnRegisterE.getScene().getWindow());
			mainStage.setResizable(false);
			mainStage.show();
		} catch (IOException e) {
			System.err.println("����" + e);
		}
	}
	
	//����
	private void handlerBtnExitAction(ActionEvent event) {

		Platform.exit();

	}

}
