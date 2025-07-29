package bluesoftBank.repository;

import bluesoftBank.entity.Cuenta;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByNumero(String numero);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Cuenta> findWithLockingByNumero(String numero);
}
