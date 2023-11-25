package ma.yas.comptecqrses.query.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.yas.comptecqrses.common_api.enums.OperationType;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;
}
