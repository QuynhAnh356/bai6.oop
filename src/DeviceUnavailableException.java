// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
// Phát sinh khi thiết bị không ở trạng thái sẵn sàng (Available)
public class DeviceUnavailableException extends BorrowingException {
    private final String deviceId;
    private final DeviceStatus currentStatus;

    public DeviceUnavailableException(String deviceId, DeviceStatus currentStatus) {
        super(String.format("Thiết bị '%s' hiện không sẵn sàng (Trạng thái hiện tại: %s).", deviceId, currentStatus));
        this.deviceId = deviceId;
        this.currentStatus = currentStatus;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public DeviceStatus getCurrentStatus() {
        return currentStatus;
    }
}