package Controller;

import bo.BoFactory;
import bo.custom.RoomBo;
import bo.custom.impl.RoomBoImpl;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.roomDto;
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
import tdm.roomTDM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;



public class manageRoomsController {

    public AnchorPane mainPane;
    public JFXTextField txtRoomTypeID;
    public JFXTextField txtRoomType;
    public JFXTextField txtKeyMoney;
    public JFXTextField txtRoomCount;

    public TableView<roomTDM> tblRoom;
    public TableColumn<roomTDM,String> colRoomId;
    public TableColumn<roomTDM,String> colRoomType;
    public TableColumn<roomTDM,Double> colKeyMoney;
    public TableColumn<roomTDM,Integer> colQty;

    public JFXTextField descriptionUp;
    public JFXTextField keyMoneyUp;
    public JFXComboBox<String> roomTypeComboUp;
    public JFXTextField qtyUp;

    private RoomBo roomBo;

    private ObservableList<roomTDM> roomList = FXCollections.observableArrayList();
    private ObservableList<String> roomIdList = FXCollections.observableArrayList();

    public manageRoomsController() throws IOException {
        roomBo = (RoomBo) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.ROOM);
    }

    public void initialize() throws SQLException, ClassNotFoundException {

        tblRoom.setItems(roomList);
        roomTypeComboUp.setItems(roomIdList);

        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomtype"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        loadAll();
    }

    public void loadAll() throws SQLException, ClassNotFoundException {
        roomList.clear();
        roomIdList.clear();
        ArrayList<roomDto> all = roomBo.getAll();
        for (roomDto dto:
                all) {
            roomIdList.add(dto.getRoomTypeId());
            roomList.add(new roomTDM(dto.getRoomTypeId(), dto.getRoomtype(), dto.getKeyMoney(), dto.getQty()));
        }
    }

    public void saveOnAcion(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id=String.valueOf(txtRoomTypeID.getText());
        String roomType=txtRoomType.getText();
        double keyMoney=Double.parseDouble(txtKeyMoney.getText());
        int qty=Integer.parseInt(txtRoomCount.getText());

        if(roomBo.save(new roomDto(id,roomType,keyMoney,qty))) {
            new Alert(Alert.AlertType.INFORMATION, "saved!").show();
        }
        loadAll();
        clearMainUI();
    }

    public void loadRoomDetails(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        roomDto room = roomBo.search(roomTypeComboUp.getValue());
        if(room!=null) {
            descriptionUp.setText(room.getRoomtype());
            keyMoneyUp.setText(String.valueOf(room.getKeyMoney()));
            qtyUp.setText(String.valueOf(room.getQty()));
        }
    }

    public void updateRoom(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id=String.valueOf(roomTypeComboUp.getValue());
        String roomType=descriptionUp.getText();
        double keyMoney=Double.parseDouble(keyMoneyUp.getText());
        int qty=Integer.parseInt(qtyUp.getText());

        if(roomBo.update(new roomDto(id,roomType,keyMoney,qty))) {
            new Alert(Alert.AlertType.INFORMATION, "updated!").show();
        }
        loadAll();
        clearUpdateUI();
    }

    public void delRoom(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        roomTDM tdm  = tblRoom.getSelectionModel().getSelectedItem();
        if(roomBo.delete(tdm.getRoomTypeId())){
            new Alert(Alert.AlertType.INFORMATION, "deleted!").show();
        }
        loadAll();
    }

    private void clearUpdateUI(){
        roomTypeComboUp.getSelectionModel().clearSelection();
        qtyUp.clear();
        keyMoneyUp.clear();
        descriptionUp.clear();
    }

    private void clearMainUI(){
        txtRoomTypeID.clear();
        txtRoomType.clear();
        txtRoomCount.clear();
        txtKeyMoney.clear();
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
}
