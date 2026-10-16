// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
import java.time.Year;

// Lớp trừu tượng quản lý thông tin thiết bị
public abstract class Device {
    protected final String deviceId;
    protected String name;
    protected int yearOfUse;
    protected double purchasePrice;
    protected DeviceStatus status;

    public Device(String deviceId, String name, int yearOfUse, double purchasePrice, DeviceStatus status) {
        // Kiểm tra các ràng buộc hợp lệ của thiết bị
        if (deviceId == null || deviceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã thiết bị không được để trống.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên thiết bị không được để trống.");
        }
        int currentYear = Year.now().getValue();
        if (yearOfUse > currentYear) {
            throw new IllegalArgumentException("Năm đưa vào sử dụng không được lớn hơn năm hiện tại.");
        }
        if (purchasePrice <= 0) {
            throw new IllegalArgumentException("Giá mua phải lớn hơn 0.");
        }

        this.deviceId = deviceId.trim();
        this.name = name.trim();
        this.yearOfUse = yearOfUse;
        this.purchasePrice = purchasePrice;
        this.status = (status != null) ? status : DeviceStatus.Available;
    }

    // Phương thức trừu tượng tính chi phí bảo trì dự kiến trong một năm
    public abstract double calculateAnnualMaintenanceCost();

    // Lấy mã thiết bị phục vụ kiểm tra trùng lặp và tìm kiếm
    public String getDeviceId() {
        return deviceId;
    }

    // Lấy trạng thái hoạt động để kiểm tra thiết bị có cần bảo trì không
    public DeviceStatus getStatus() {
        return status;
    }

    // Lấy năm đưa vào sử dụng để tính thời gian thiết bị đã dùng
    public int getYearOfUse() {
        return yearOfUse;
    }

    // Biểu diễn thông tin thiết bị dưới dạng chuỗi
    @Override
    public String toString() {
        return String.format("[%s] %s | Năm SD: %d | Giá: %,.0f VNĐ | Trạng thái: %s",
                deviceId, name, yearOfUse, purchasePrice, status);
    }
}