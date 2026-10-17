// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
public class InvalidBorrowPeriodException extends BorrowingException {
    private final String deviceId;
    private final int requestedDays;
    private final int maxAllowedDays;

    public InvalidBorrowPeriodException(String deviceId, int requestedDays, int maxAllowedDays) {
        super(String.format("Thời gian mượn thiết bị '%s' không hợp lệ: Yêu cầu %d ngày, tối đa cho phép là %d ngày.",
                deviceId, requestedDays, maxAllowedDays));
        this.deviceId = deviceId;
        this.requestedDays = requestedDays;
        this.maxAllowedDays = maxAllowedDays;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public int getRequestedDays() {
        return requestedDays;
    }

    public int getMaxAllowedDays() {
        return maxAllowedDays;
    }
}
