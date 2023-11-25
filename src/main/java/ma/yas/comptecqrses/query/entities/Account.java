package ma.yas.comptecqrses.query.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.yas.comptecqrses.common_api.enums.AccountStatus;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Account  {
    @Id
    private String id;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    @OneToMany(mappedBy ="account" )
    private Collection<Operation> operations;

}
