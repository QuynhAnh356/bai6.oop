// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
public class ProjectorRemote extends TeachingDevice {

    public ProjectorRemote(String deviceId, String name, int yearOfUse, double purchasePrice, DeviceStatus status, int maxBorrowDays) {
        super(deviceId, name, yearOfUse, purchasePrice, status, maxBorrowDays);
    }

    @Override
    public double calculateAnnualMaintenanceCost() {
        return purchasePrice * 0.08;
    }
}
