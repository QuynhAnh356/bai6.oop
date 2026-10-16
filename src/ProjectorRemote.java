// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
// Thiết bị điều khiển máy chiếu
public class ProjectorRemote extends TeachingDevice {

    public ProjectorRemote(String deviceId, String name, int yearOfUse, double purchasePrice, DeviceStatus status, int maxBorrowDays) {
        super(deviceId, name, yearOfUse, purchasePrice, status, maxBorrowDays);
    }

    // Chi phí bảo trì dự kiến hàng năm (ước tính 8% giá mua thiết bị)
    @Override
    public double calculateAnnualMaintenanceCost() {
        return purchasePrice * 0.08;
    }
}