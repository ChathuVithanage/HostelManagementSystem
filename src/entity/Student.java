package entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Student {
    @Id
    @Column(name = "Student_Id")
    @NonNull
    private String studentId;
    @Column(name = "Student_Name")
    @NonNull
    private String name;
    @Column(name = "Address")
    @NonNull
    private String address;
    @Column(name = "Contact_No")
    @NonNull
    private String contactNo;
    @Column(name = "DOB")
    @NonNull
    private String dob;
    @Column(name = "Gender")
    @NonNull
    private String gender;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Reservation> reservationList =new ArrayList<>();


}
