// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
import java.time.Year;

public abstract class Device {
    protected final String deviceId;
    protected String name;
    protected int yearOfUse;
    protected double purchasePrice;
    protected DeviceStatus status;

    public Device(String deviceId, String name, int yearOfUse, double purchasePrice, DeviceStatus status) {

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

    public abstract double calculateAnnualMaintenanceCost();
    public String getDeviceId() {
        return deviceId;
    }
    public DeviceStatus getStatus() {
        return status;
    }

    public int getYearOfUse() {
        return yearOfUse;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Năm SD: %d | Giá: %,.0f VNĐ | Trạng thái: %s",
                deviceId, name, yearOfUse, purchasePrice, status);
    }
}
