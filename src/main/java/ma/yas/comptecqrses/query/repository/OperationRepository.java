package ma.yas.comptecqrses.query.repository;

import ma.yas.comptecqrses.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
