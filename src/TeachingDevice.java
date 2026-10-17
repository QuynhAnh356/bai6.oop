// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
public abstract class TeachingDevice extends Device {
    protected int maxBorrowDays;

    public TeachingDevice(String deviceId, String name, int yearOfUse, double purchasePrice, DeviceStatus status, int maxBorrowDays) {
        super(deviceId, name, yearOfUse, purchasePrice, status);
        if (maxBorrowDays <= 0) {
            throw new IllegalArgumentException("Số ngày mượn tối đa phải lớn hơn 0.");
        }
        this.maxBorrowDays = maxBorrowDays;
    }

    public int getMaxBorrowDays() {
        return maxBorrowDays;
    }

    public boolean isAvailable() {
        return this.status == DeviceStatus.Available;
    }

    public void markAsBorrowed() {
        this.status = DeviceStatus.Borrowed;
    }

    public void markAsAvailable() {
        this.status = DeviceStatus.Available;
    }
}
