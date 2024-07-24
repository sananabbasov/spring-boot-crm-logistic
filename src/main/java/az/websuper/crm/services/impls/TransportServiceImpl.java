package az.websuper.crm.services.impls;

import az.websuper.crm.dtos.customer.CustomerDto;
import az.websuper.crm.dtos.customer.CustomerUpdateDto;
import az.websuper.crm.dtos.transport.TransportCreateDto;
import az.websuper.crm.dtos.transport.TransportDto;
import az.websuper.crm.dtos.transport.TransportUpdateDto;
import az.websuper.crm.models.Customer;
import az.websuper.crm.models.Transport;
import az.websuper.crm.models.User;
import az.websuper.crm.payloads.ApiResponse;
import az.websuper.crm.payloads.PaginationPayload;
import az.websuper.crm.repositories.TransportRepository;
import az.websuper.crm.repositories.UserRepository;
import az.websuper.crm.services.TransportService;
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
public class TransportServiceImpl implements TransportService {

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;


    @Override
    public PaginationPayload<TransportDto> getTransports(Integer pageNumber, Integer pageSize, String sortBy, Principal principal) {
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
        Page<Transport> transports = transportRepository.findByCompanyId(pageable,findUserCompany.getCompany().getId());
        List<Transport> result = transports.getContent();
        List<TransportDto> transportDtos = result.stream().map(c -> modelMapper.map(c, TransportDto.class)).collect(Collectors.toList());

        PaginationPayload<TransportDto> postResponse = new PaginationPayload<>();
        postResponse.setData(transportDtos);
        postResponse.setPageNumber(pageable.getPageNumber());
        postResponse.setPageSize(pageable.getPageNumber());
        postResponse.setTotalElements(pageable.getPageSize());
        postResponse.setTotalPages(pageable.getPageSize());
        postResponse.setLastPage(pageable.isPaged());

        return postResponse;
    }

    @Override
    public ApiResponse createTransport(String userEmail, TransportCreateDto transportCreateDto) {
        User findUserCompany = userRepository.findByEmail(userEmail);
        Transport transport = modelMapper.map(transportCreateDto,Transport.class);
        transport.setCompany(findUserCompany.getCompany());
        transportRepository.save(transport);
        return new ApiResponse("Added",true);
    }

    @Override
    public ApiResponse updateTransport(String userEmail, Long id, TransportUpdateDto transportUpdateDto) {
        try {
            Transport findCustomer = transportRepository.findById(id).orElseThrow();
            User findUserCompany = userRepository.findByEmail(userEmail);
            if (!findUserCompany.getCompany().getName().equals(findCustomer.getCompany().getName())){
                return new ApiResponse("Siz bu istifadecini yenileye bilmezsiz",false);
            }
            findCustomer.setTransportType(transportUpdateDto.getTransportType());
            findCustomer.setRegistrationPlate(transportUpdateDto.getRegistrationPlate());
            transportRepository.save(findCustomer);
            return new ApiResponse("Elave olundu",true);
        }catch (Exception e){
            return new ApiResponse(e.getMessage(),false);
        }
    }

    @Override
    public TransportUpdateDto getUpdateTransport(String userEmail, Long id) {
        try {
            Transport findTransport = transportRepository.findById(id).orElseThrow();
            User findUserCompany = userRepository.findByEmail(userEmail);
            if (!findUserCompany.getCompany().getName().equals(findTransport.getCompany().getName())){
                return null;
            }
            TransportUpdateDto result = modelMapper.map(findTransport, TransportUpdateDto.class);
            return result;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public ApiResponse removeTransport(String userEmail, Long id) {
        try{
            Transport findTransport = transportRepository.findById(id).orElseThrow();
            User findUserCompany = userRepository.findByEmail(userEmail);
            if (!findUserCompany.getCompany().getName().equals(findTransport.getCompany().getName())){
                return new ApiResponse("Siz bu istifadecini yenileye bilmezsiz",false);
            }
            findTransport.setDeleted(true);
            transportRepository.save(findTransport);
            return new ApiResponse("Musteri silindi",true);
        }catch (Exception e){
            return new ApiResponse(e.getMessage(),false);
        }
    }
}
