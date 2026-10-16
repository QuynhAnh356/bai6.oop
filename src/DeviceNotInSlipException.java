// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
// Phát sinh khi yêu cầu trả một thiết bị không nằm trong phiếu mượn
public class DeviceNotInSlipException extends BorrowingException {
    private final String deviceId;

    public DeviceNotInSlipException(String deviceId) {
        super(String.format("Thiết bị '%s' không thuộc phiếu mượn này.", deviceId));
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }
}