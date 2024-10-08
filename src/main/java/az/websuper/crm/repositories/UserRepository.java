package az.websuper.crm.repositories;

import az.websuper.crm.models.Customer;
import az.websuper.crm.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Page<User> findByCompanyId(Pageable pageable, Long companyId);
}
