package ma.yas.comptecqrses.query.repository;

import ma.yas.comptecqrses.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
