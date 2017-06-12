package uz.hasan.domain.pojos.report;

/**
 * @author : hasan @date: 5/29/17.
 */
public class ProductDeliveryReport {
    private String companyName;
    private String date;
    private Long id;
    private String productName;
    private Long productQty;
    private String docDate;
    private String clientFirstName;
    private String clientLastName;
    private String phoneNumber;
    private String districtName;
    private String address;
    private String carModel;
    private String carNumber;
    private String driverFirstName;
    private String driverLastName;
    private String sentToDCTime;
    private String deliveredTime;
    private String deliveryTookTime;
    private Long companyId;

    public ProductDeliveryReport() {
    }

    public ProductDeliveryReport(String companyName,
                                 String date,
                                 Long id,
                                 String productName,
                                 Long productQty,
                                 String docDate,
                                 String clientFirstName,
                                 String clientLastName,
                                 String phoneNumber,
                                 String districtName,
                                 String address,
                                 String carModel,
                                 String carNumber,
                                 String driverFirstName,
                                 String driverLastName,
                                 String sentToDCTime,
                                 String deliveredTime,
                                 String deliveryTookTime,
                                 Long companyId) {
        this.companyName = companyName;
        this.date = date;
        this.id = id;
        this.productName = productName;
        this.productQty = productQty;
        this.docDate = docDate;
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.phoneNumber = phoneNumber;
        this.districtName = districtName;
        this.address = address;
        this.carModel = carModel;
        this.carNumber = carNumber;
        this.driverFirstName = driverFirstName;
        this.driverLastName = driverLastName;
        this.sentToDCTime = sentToDCTime;
        this.deliveredTime = deliveredTime;
        this.deliveryTookTime = deliveryTookTime;
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductQty() {
        return productQty;
    }

    public void setProductQty(Long productQty) {
        this.productQty = productQty;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getDriverFirstName() {
        return driverFirstName;
    }

    public void setDriverFirstName(String driverFirstName) {
        this.driverFirstName = driverFirstName;
    }

    public String getDriverLastName() {
        return driverLastName;
    }

    public void setDriverLastName(String driverLastName) {
        this.driverLastName = driverLastName;
    }

    public String getSentToDCTime() {
        return sentToDCTime;
    }

    public void setSentToDCTime(String sentToDCTime) {
        this.sentToDCTime = sentToDCTime;
    }

    public String getDeliveredTime() {
        return deliveredTime;
    }

    public void setDeliveredTime(String deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getDeliveryTookTime() {
        return deliveryTookTime;
    }

    public void setDeliveryTookTime(String deliveryTookTime) {
        this.deliveryTookTime = deliveryTookTime;
    }
}
