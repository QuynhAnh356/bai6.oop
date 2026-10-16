// Thiết bị Micro giảng dạy
public class Microphone extends TeachingDevice {

    public Microphone(String deviceId, String name, int yearOfUse, double purchasePrice, DeviceStatus status, int maxBorrowDays) {
        super(deviceId, name, yearOfUse, purchasePrice, status, maxBorrowDays);
    }

    // Chi phí bảo trì dự kiến hàng năm (ước tính 10% giá mua thiết bị do hao mòn đầu thu/pin)
    @Override
    public double calculateAnnualMaintenanceCost() {
        return purchasePrice * 0.10;
    }
}