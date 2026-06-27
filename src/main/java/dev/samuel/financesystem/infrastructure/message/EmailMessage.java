package dev.samuel.financesystem.infrastructure.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessage {

       private Long userId;
       private String emailTo;
       private String emailSubject;
       private String body;

}
