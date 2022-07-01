package dto;

import entity.Reservation;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class roomDto {
    @NonNull
    private String roomTypeId;
    @NonNull
    private String roomtype;
    @NonNull
    private double keyMoney;
    @NonNull
    private int qty;

    private List<Reservation> reservations = new ArrayList<>();

}
