package az.websuper.crm.services.impls;

import az.websuper.crm.dtos.customer.CustomerCreateDto;
import az.websuper.crm.dtos.customer.CustomerDto;
import az.websuper.crm.dtos.customer.CustomerUpdateDto;
import az.websuper.crm.models.Customer;
import az.websuper.crm.models.User;
import az.websuper.crm.payloads.ApiResponse;
import az.websuper.crm.payloads.PaginationPayload;
import az.websuper.crm.repositories.CustomerRepository;
import az.websuper.crm.repositories.UserRepository;
import az.websuper.crm.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;


    @Override
    public PaginationPayload<CustomerDto> getCustomers(Integer pageNumber, Integer pageSize, String sortBy, Principal principal) {
        Sort sort = null;
        if (!sortBy.equalsIgnoreCase("asc"))
        {
            sort = Sort.by(sortBy).ascending();
        }else
        {
            sort = Sort.by(sortBy).descending();
        }
        User findUserCompany = userRepository.findByEmail(principal.getName());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Customer> customers = customerRepository.findByCompanyId(pageable,findUserCompany.getCompany().getId());
        List<Customer> result = customers.getContent();
        List<CustomerDto> customerDtos = result.stream().map(c -> modelMapper.map(c, CustomerDto.class)).collect(Collectors.toList());

        PaginationPayload<CustomerDto> postResponse = new PaginationPayload<>();
        postResponse.setData(customerDtos);
        postResponse.setPageNumber(pageable.getPageNumber());
        postResponse.setPageSize(pageable.getPageNumber());
        postResponse.setTotalElements(pageable.getPageSize());
        postResponse.setTotalPages(pageable.getPageSize());
        postResponse.setLastPage(pageable.isPaged());

        return postResponse;
    }

    @Override
    public ApiResponse createCustomer(String userEmail, CustomerCreateDto customerCreateDto) {
        User findUserCompany = userRepository.findByEmail(userEmail);
        Customer customer = modelMapper.map(customerCreateDto,Customer.class);
        customer.setCompany(findUserCompany.getCompany());
        customerRepository.save(customer);
        return null;
    }

    @Override
    public ApiResponse updateCustomer(Long id, CustomerUpdateDto customerUpdateDto) {
        try {
            Customer findCustomer = customerRepository.findById(id).orElseThrow();

            findCustomer.setName(customerUpdateDto.getName());
            findCustomer.setEmail(customerUpdateDto.getEmail());
            findCustomer.setLastname(customerUpdateDto.getLastname());
            customerRepository.save(findCustomer);
            return new ApiResponse("Elave olundu",true);
        }catch (Exception e){
            return new ApiResponse(e.getMessage(),true);
        }
    }
}
