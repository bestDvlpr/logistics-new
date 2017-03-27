package uz.hasan.service.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.*;
import uz.hasan.service.dto.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @author: hasan @date: 3/11/17.
 */
@Service
@Transactional
public class CarProductEntriesMapper {
    private final ProductEntryMapper productEntryMapper;
    private final ReceiptMapper receiptMapper;
    private CarMapper carMapper;
    private ClientMapper clientMapper;

    public CarProductEntriesMapper(ProductEntryMapper productEntryMapper,
                                   ReceiptMapper receiptMapper,
                                   ClientMapper clientMapper,
                                   CarMapper carMapper) {
        this.productEntryMapper = productEntryMapper;
        this.receiptMapper = receiptMapper;
        this.carMapper = carMapper;
        this.clientMapper = clientMapper;
    }

    public CarAndProductEntries carToCarAndProductEntries(Car car) {
        if (car == null) {
            return null;
        }
        CarDTO carDTO = carMapper.carToCarDTO(car);
        List<ProductEntry> productEntries = new ArrayList<>();
        productEntries.addAll(car.getProductEntries());
        CarAndProductEntries entriesDTO = new CarAndProductEntries();
        entriesDTO.setProductEntries(productEntryMapper.productEntriesToProductEntryDTOs(productEntries));
        BeanUtils.copyProperties(carDTO, entriesDTO);
        return entriesDTO;
    }

    public List<CarAndProductEntries> carsToCarAndProductEntries(List<Car> carList) {
        if (carList == null || carList.isEmpty()) {
            return Collections.emptyList();
        }
        List<CarAndProductEntries> carAndProductEntries = new ArrayList<>();
        for (Car car : carList) {
            carAndProductEntries.add(carToCarAndProductEntries(car));
        }
        return carAndProductEntries;
    }
}
