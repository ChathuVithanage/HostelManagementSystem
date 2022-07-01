package entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Reservation {
    @Id
    @Column(name = "Reservation_Id")
    @NonNull
    private String reservationId;
    @Column(name = "Date")
    @CreationTimestamp
    private LocalDate date;
    @Column(name = "Status")
    @NonNull
    private String status;
    @Column(name = "Paid")
    @NonNull
    private boolean paid;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "studentId")
    private Student student;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "roomTypeId")
    private Room room;

}
