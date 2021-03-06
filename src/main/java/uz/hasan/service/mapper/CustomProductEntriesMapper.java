package uz.hasan.service.mapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.Address;
import uz.hasan.domain.ProductEntry;
import uz.hasan.repository.*;
import uz.hasan.service.dto.ProductEntryDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: hasan @date: 3/11/17.
 */
@Service
@Transactional
public class CustomProductEntriesMapper {
    private final ProductEntryMapper productEntryMapper;
    private final ReceiptRepository receiptRepository;
    private final CarRepository carRepository;
    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final AddressMapper addressMapper;

    public CustomProductEntriesMapper(ProductEntryMapper productEntryMapper,
                                      ReceiptRepository receiptRepository,
                                      ClientMapper clientMapper,
                                      CompanyRepository companyRepository,
                                      CarRepository carRepository,
                                      UserRepository userRepository,
                                      ClientRepository clientRepository,
                                      AddressRepository addressRepository,
                                      SellerRepository sellerRepository,
                                      ProductRepository productRepository,
                                      AddressMapper addressMapper) {
        this.productEntryMapper = productEntryMapper;
        this.receiptRepository = receiptRepository;
        this.carRepository = carRepository;
        this.companyRepository = companyRepository;
        this.clientMapper = clientMapper;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.addressMapper = addressMapper;
    }

    public ProductEntryDTO ProductEntryToProductEntryDTO(ProductEntry entry) {
        return new ProductEntryDTO();
    }

    public ProductEntry productEntryDTOToProductEntry(ProductEntryDTO productEntryDTO) {
        if (productEntryDTO == null) {
            return null;
        }

        ProductEntry productEntry = new ProductEntry();
        productEntry.setId(productEntryDTO.getId());
        if (productEntryDTO.getAddressId() != null ) {
            productEntry.setAddress(addressRepository.findOne(productEntryDTO.getAddressId()));
        }
        if (productEntryDTO.getAddress() != null && productEntryDTO.getAddress().getId()!=null) {
            productEntry.setAddress(addressMapper.addressDTOToAddress(productEntryDTO.getAddress()));
        }
        productEntry.setAttachedCar(productEntryDTO.getAttachedCarId() != null ? carRepository.findOne(productEntryDTO.getAttachedCarId()) : null);
        productEntry.setAttachedToCarTime(productEntryDTO.getAttachedToCarTime());
        productEntry.setAttachedToDriverBy(productEntryDTO.getAttachedToDriverById() != null ? userRepository.findOne(productEntryDTO.getAttachedToDriverById()) : null);
        productEntry.setMarkedAsDeliveredBy(productEntryDTO.getMarkedAsDeliveredById() != null ? userRepository.findOne(productEntryDTO.getMarkedAsDeliveredById()) : null);
        productEntry.setCancelled(productEntryDTO.getCancelled());
        productEntry.setReason(productEntryDTO.getReason());
        productEntry.setReceipt(productEntryDTO.getReceiptId() != null ? receiptRepository.findOne(productEntryDTO.getReceiptId()) : null);
        productEntry.setDefectFlag(productEntryDTO.getDefectFlag());
        productEntry.setHallFlag(productEntryDTO.getHallFlag());
        productEntry.setDeliveryFlag(productEntryDTO.getDeliveryFlag());
        productEntry.setDeliveryStartTime(productEntryDTO.getDeliveryStartTime());
        productEntry.setDeliveryEndTime(productEntryDTO.getDeliveryEndTime());
        productEntry.setDeliveryItemsSentBy(productEntryDTO.getDeliveryItemsSentById() != null ? userRepository.findOne(productEntryDTO.getDeliveryItemsSentById()) : null);
        productEntry.setGuid(productEntryDTO.getGuid());
        productEntry.setPrice(productEntryDTO.getPrice());
        productEntry.setQty(productEntryDTO.getQty());
        productEntry.setSellerID(productEntryDTO.getSellerIDId() != null ? sellerRepository.findOne(productEntryDTO.getSellerIDId()) : null);
        productEntry.setProduct(productEntryDTO.getProductId() != null ? productRepository.findOne(productEntryDTO.getProductId()) : null);
        productEntry.setCompany(productEntryDTO.getCompanyId() != null ? companyRepository.findOne(productEntryDTO.getCompanyId()) : null);
        productEntry.setVirtualFlag(productEntryDTO.getVirtualFlag());
        productEntry.setStatus(productEntryDTO.getStatus());
        productEntry.setComment(productEntryDTO.getComment());
        productEntry.setDeliveryDate(productEntryDTO.getDeliveryDate());

        return productEntry;
    }

    public List<ProductEntry> productEntryDTOsToProductEntries(List<ProductEntryDTO> productEntryDTOS) {
        if (productEntryDTOS == null || productEntryDTOS.isEmpty()) {
            return Collections.emptyList();
        }
        List<ProductEntry> productEntries = new ArrayList<>();
        for (ProductEntryDTO productEntryDTO : productEntryDTOS) {
            productEntries.add(productEntryDTOToProductEntry(productEntryDTO));
        }
        return productEntries;
    }
}
