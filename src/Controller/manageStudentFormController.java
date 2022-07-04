package Controller;

import bo.BoFactory;
import bo.custom.ReservationBo;
import bo.custom.RoomBo;
import bo.custom.StudentBo;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.reservationDto;
import dto.roomDto;
import dto.studentDto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tdm.reservationTDM;
import tdm.studentTDM;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class manageStudentFormController {
    public AnchorPane mainPane;
    public ComboBox cmbGender;
    public JFXTextField txtUniId;
    public JFXTextField txtStuName;
    public JFXTextField txtConNum;
    public JFXTextField txtAddress;
    public JFXTextField txtDob;

    public ComboBox<String> cmbRTypeId;
    public JFXTextField txtRType;
    public JFXTextField txtKeyMoney;
    public ComboBox<String> cmbStatus;

    public TableView<studentTDM> tblStudent;
    public TableColumn<studentTDM,String> colStuUniId;
    public TableColumn<studentTDM,String> colStuName;
    public TableColumn<studentTDM,String> colContact;
    public TableColumn<studentTDM,String> colAddress;
    public TableColumn<studentTDM,String> colGender;
    public TableColumn<studentTDM,String> colDob;

    public JFXTextField txtUpStId;
    public JFXTextField txtUpStName;
    public JFXTextField txtUpContact;
    public JFXTextField txtUpAddress;
    public JFXTextField txtUpDob;
    public JFXTextField txtUpGender;

    public TableView<reservationTDM> tblReserve;
    public TableColumn<reservationTDM,String> colReserveId;
    public TableColumn<reservationTDM,String> colReStuName;
    public TableColumn<reservationTDM,String> colReRoom;
    public TableColumn<reservationTDM,String> colReStatus;
    public TableColumn<reservationTDM,LocalDate> colReDate;
    public TableColumn<reservationTDM,Boolean> colRePaid;
    public JFXComboBox avlRoomType;

    public RadioButton paidRd;
    public RadioButton payLaterRd;
    public Label resIdLbl;
    public JFXTextField avlKeyMoney;
    public JFXTextField avlRoomCount;
    public Label yesOrNo;
    public JFXTextField txtRoomQty;
    public JFXTextField txtTotal;
    public JFXComboBox cmbSearchId;
    public TableView tblSearch;
    public TableColumn colSearchRType;
    public TableColumn colQty;
    public TableColumn colKeyMoney;
    public TableColumn colStatus;
    public TableColumn colTotal;
    public JFXTextField txtSearchQty;
    public JFXComboBox cmbSearchStatus;
    public JFXTextField txtSearchTotal;

    private ReservationBo reservationBO;
    private StudentBo studentBO;
    private RoomBo roomBO;

    private ObservableList<String> roomIdList = FXCollections.observableArrayList();
    private ObservableList<studentTDM> studentList = FXCollections.observableArrayList();
    private ObservableList<reservationTDM> reservationList = FXCollections.observableArrayList();


    public manageStudentFormController() throws IOException {
        studentBO = (StudentBo) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.STUDENT);
        roomBO = (RoomBo) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.ROOM);
        reservationBO = (ReservationBo) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.RESERVATION);
    }

    public void initialize() throws IOException, SQLException, ClassNotFoundException {

        setRegId();

        tblStudent.setItems(studentList);
        tblReserve.setItems(reservationList);

        cmbRTypeId.setItems(roomIdList);
        avlRoomType.setItems(roomIdList);

        final ToggleGroup group = new ToggleGroup();
        paidRd.setToggleGroup(group);
        payLaterRd.setToggleGroup(group);
        paidRd.setSelected(true);
        
        cmbGender.getItems().add("Male");
        cmbGender.getItems().add("Female");

        ObservableList<String> statusList = FXCollections.observableArrayList();
        statusList.add("Paid");
        statusList.add("Pending");
        statusList.add("Booked");
        statusList.add("Deposit");
        cmbStatus.setItems(statusList);

        colStuUniId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStuName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        colReserveId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colReStuName.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colReRoom.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colReStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colReDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRePaid.setCellValueFactory(new PropertyValueFactory<>("paid"));

        loadAllStudents();
        loadAllReservations();
        loadAllRooms();
    }

    public void navigateToBack(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("../MainView/dashboardForm.fxml");
        Parent mainPane = FXMLLoader.load(resource);
        Scene scene = new Scene(mainPane);
        Stage primaryStage = (Stage) (this.mainPane.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void registerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        studentDto student = new studentDto();
        reservationDto reservation = new reservationDto();
        // initializing room
        roomDto room = new roomDto();
        room.setRoomTypeId(cmbRTypeId.getValue());

            // initializing student
            student.setStudentId(txtUniId.getText());
            student.setName(txtStuName.getText());
            student.setContactNo(txtConNum.getText());
            student.setAddress(txtAddress.getText());
            student.setDob(txtDob.getText());
            student.setGender(cmbGender.getValue().toString());
            studentBO.save(student);

            loadAllStudents();


        // initializing reservation
        reservation.setResId(resIdLbl.getText());
        reservation.setStatus(cmbStatus.getValue());
        reservation.setPaid(paidRd.isArmed());
        reservation.setStudent(student);
        reservation.setRoom(room);


        if(reservationBO.save(reservation)){
            new Alert(Alert.AlertType.INFORMATION,"reservation saved!").show();
            clearUIAfterReserving();
        }


        // updating room qty
        roomDto roomDTO = roomBO.search(room.getRoomTypeId());
        roomDTO.setQty(roomDTO.getQty()-1);
        roomBO.update(roomDTO);

        //clearUI();

        loadAllRooms();
        loadAllReservations();

        setRegId();
    }


    public void roomIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        roomDto roomDTO = roomBO.search(String.valueOf(cmbRTypeId.getValue()));
        if(roomDTO!=null){
            txtRType.setText(roomDTO.getRoomtype());
            txtKeyMoney.setText(String.valueOf(roomDTO.getKeyMoney()));
        }
    }

    // todo
    public void studentUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        studentDto student = new studentDto();

        // initializing student
        student.setStudentId(txtUpStId.getText());
        student.setName(txtUpStName.getText());
        student.setContactNo(txtUpContact.getText());
        student.setAddress(txtUpAddress.getText());
        student.setDob(txtUpDob.getText());
        student.setGender(txtUpGender.getText());

        if(studentBO.update(student)){
            new Alert(Alert.AlertType.INFORMATION,"student updated!").show();
        }

        clearUIAfterUpdating();

        loadAllStudents();
    }

    void setRegId() throws SQLException, ClassNotFoundException {
        resIdLbl.setText(reservationBO.generateNewID());
    }

    // todo
    public void studentRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<reservationDto> reservations = reservationBO.getAll();
        reservations.removeIf(n -> (!n.getStudent().getStudentId().equals(tblStudent.getSelectionModel().getSelectedItem().getStudentId())));
        for (reservationDto dto: reservations) {
            roomDto roomDTO = roomBO.search(dto.getRoom().getRoomTypeId());
            roomDTO.setQty(roomDTO.getQty()+1);
            roomBO.update(roomDTO);
        }
        if(studentBO.delete(tblStudent.getSelectionModel().getSelectedItem().getStudentId())){
            new Alert(Alert.AlertType.INFORMATION,"student deleted!").show();
        }

        loadAllStudents();
        loadAllReservations();
    }

    void loadAllReservations() throws SQLException, ClassNotFoundException {
        reservationList.clear();
        ArrayList<reservationDto> all = reservationBO.getAll();
        for (reservationDto dto:
                all) {
            reservationList.add(new reservationTDM(dto.getResId(),  dto.getStudent().getStudentId(), dto.getRoom().getRoomTypeId(), dto.getStatus(), dto.getDate(), dto.isPaid()));
        }
    }
    void loadAllStudents() throws SQLException, ClassNotFoundException {
        studentList.clear();
        ArrayList<studentDto> all = studentBO.getAll();
        for (studentDto dto:
                all) {
            studentList.add(new studentTDM(dto.getStudentId(), dto.getName(), dto.getAddress(), dto.getContactNo(), dto.getDob(), dto.getGender()));
        }
    }


    void loadAllRooms() throws SQLException, ClassNotFoundException {
        roomIdList.clear();
        ArrayList<roomDto> all = roomBO.getAll();
        for (roomDto dto:
                all) {
            if(dto.getQty()>0){
                roomIdList.add(dto.getRoomTypeId());
            }
        }
        avlKeyMoney.clear();
        avlRoomCount.clear();
        yesOrNo.setText("");
    }

    public void isAvailable(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        // if room qty is greater than 0
        try {
            roomDto search = roomBO.search(avlRoomType.getValue().toString());
            if(search!=null) {
                if (search.getQty() > 0) {
                    yesOrNo.setText("YES");

                } else yesOrNo.setText("NO");
                avlKeyMoney.setText(String.valueOf(search.getKeyMoney()));
                avlRoomCount.setText(String.valueOf(search.getQty()));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void fillTiUpdateStudent(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        studentDto st = studentBO.search(txtUpStId.getText());
        if(st!=null) {
            txtUpStName.setText(st.getName());
            txtUpContact.setText(st.getContactNo());
            txtUpAddress.setText(st.getAddress());
            txtUpDob.setText(st.getDob());
            txtUpGender.setText(st.getGender());
        }else{
            new Alert(Alert.AlertType.WARNING,"student not found!").show();
        }
    }

    public void reservationRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(reservationBO.delete(tblReserve.getSelectionModel().getSelectedItem().getReservationId())){
            new Alert(Alert.AlertType.INFORMATION,"reservation deleted!").show();
        }
        roomDto roomDTO = roomBO.search(tblReserve.getSelectionModel().getSelectedItem().getRoomType());
        roomDTO.setQty(roomDTO.getQty()+1);
        roomBO.update(roomDTO);
        loadAllReservations();
        loadAllRooms();
    }

    void clearUIAfterUpdating(){
        txtUpStId.clear();
        txtUpStName.clear();
        txtUpContact.clear();
        txtUpAddress.clear();
        txtUpDob.clear();
        txtUpGender.clear();
    }

    void clearUIAfterReserving(){
        txtStuName.clear();
        txtDob.clear();
        txtAddress.clear();
        txtUniId.clear();
        txtRType.clear();
        txtConNum.clear();
        txtKeyMoney.clear();
        cmbGender.getSelectionModel().clearSelection();
        cmbRTypeId.getSelectionModel().clearSelection();
        cmbStatus.getSelectionModel().clearSelection();
    }

    public void updateRoom(ActionEvent actionEvent) {
    }

    public void updateReservation(ActionEvent actionEvent) {
    }

    public void removeRoom(ActionEvent actionEvent) {
    }

    public void txtUniIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        studentDto st = studentBO.search(txtUniId.getText());
        if(st!=null) {
            txtStuName.setText(st.getName());
            txtConNum.setText(st.getContactNo());
            txtAddress.setText(st.getAddress());
            txtDob.setText(st.getDob());
            cmbGender.setValue(st.getGender());
        }else{
            new Alert(Alert.AlertType.WARNING,"student not found!").show();
        }
    }
}
