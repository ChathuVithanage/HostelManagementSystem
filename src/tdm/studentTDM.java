package tdm;

import entity.Reservation;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class studentTDM {
    @NonNull
    private String studentId;
    @NonNull
    private String name;
    @NonNull
    private String contactNo;
    @NonNull
    private String address;
    @NonNull
    private String dob;
    @NonNull
    private String gender;

    private List<Reservation> reservations = new ArrayList<>();
}
