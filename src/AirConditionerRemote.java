// Thiết bị điều khiển điều hòa
public class AirConditionerRemote extends TeachingDevice {

    public AirConditionerRemote(String deviceId, String name, int yearOfUse, double purchasePrice, DeviceStatus status, int maxBorrowDays) {
        super(deviceId, name, yearOfUse, purchasePrice, status, maxBorrowDays);
    }

    // Chi phí bảo trì dự kiến hàng năm (ước tính 5% giá mua thiết bị)
    @Override
    public double calculateAnnualMaintenanceCost() {
        return purchasePrice * 0.05;
    }
}