// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
public class DuplicateDeviceException extends BorrowingException {
    private final String deviceId;

    public DuplicateDeviceException(String deviceId) {
        super(String.format("Thiết bị '%s' đã tồn tại trong phiếu mượn này.", deviceId));
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
