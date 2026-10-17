// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
public class DeviceAlreadyReturnedException extends BorrowingException {
    private final String deviceId;

    public DeviceAlreadyReturnedException(String deviceId) {
        super(String.format("Thiết bị '%s' trong phiếu này đã được trả trước đó.", deviceId));
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
