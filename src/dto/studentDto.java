package dto;

import entity.Reservation;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class studentDto {

    private String studentId;

    private String name;

    private String address;

    private String contactNo;

    private String dob;

    private String gender;

    //private List<Reservation> reservations = new ArrayList<>();
}
