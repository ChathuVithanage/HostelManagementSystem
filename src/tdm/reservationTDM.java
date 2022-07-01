package tdm;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class reservationTDM {
    @NonNull
    private String reservationId;
    private String studentID;
    private String roomType;
    private String status;
    private LocalDate date;
    private boolean paid;
}
