package dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class reservationDto {
    @NonNull
    private String resId;

    private LocalDate date;
    @NonNull
    private String status;

    private boolean paid;

    private studentDto student;

    private roomDto room;

}
