package az.websuper.crm.repositories;

import az.websuper.crm.enums.OrderStatus;
import az.websuper.crm.models.Customer;
import az.websuper.crm.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByCompanyIdAndOrderStatus(Pageable pageable, Long companyId, OrderStatus orderStatus);
    Order findByIdAndCompanyId(Long id, Long companyId);
    Page<Order> findByCompanyId(Pageable pageable, Long companyId);
}
