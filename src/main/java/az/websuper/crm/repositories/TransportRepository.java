package az.websuper.crm.repositories;

import az.websuper.crm.models.Customer;
import az.websuper.crm.models.Transport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportRepository extends JpaRepository<Transport, Long> {
    Page<Transport> findByCompanyId(Pageable pageable, Long companyId);
}
