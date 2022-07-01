package entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Room {
    @Id
    @Column(name = "Room_Id")
    @NonNull
    private String roomTypeId;
    @Column(name = "Room_Type")
    @NonNull
    private String type;
    @Column(name = "Key_Money")
    @NonNull
    private double keyMoney;
    @Column(name = "qty",columnDefinition = "INT UNSIGNED")
    @NonNull
    private int qty;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservationList =new ArrayList<>();
}
