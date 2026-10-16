// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
// Lớp trừu tượng quản lý các thiết bị giảng dạy phục vụ mượn - trả
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

    // Kiểm tra thiết bị có đang ở trạng thái sẵn sàng để mượn hay không
    public boolean isAvailable() {
        return this.status == DeviceStatus.Available;
    }

    // Đánh dấu thiết bị chuyển sang trạng thái đang được mượn
    public void markAsBorrowed() {
        this.status = DeviceStatus.Borrowed;
    }

    // Đánh dấu thiết bị được trả về trạng thái sẵn sàng
    public void markAsAvailable() {
        this.status = DeviceStatus.Available;
    }
}