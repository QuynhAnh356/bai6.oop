import java.time.Year;
import java.util.ArrayList;
import java.util.List;

// Quản lý thông tin phòng học và danh sách thiết bị kết tập trong phòng
public class ClassRoom {
    private String roomId;
    private String roomName;
    private int capacity;
    private List<Device> devices;

    public ClassRoom(String roomId, String roomName, int capacity) {
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phòng không được để trống.");
        }
        if (roomName == null || roomName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên phòng không được để trống.");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Sức chứa phòng học phải lớn hơn 0.");
        }

        this.roomId = roomId.trim();
        this.roomName = roomName.trim();
        this.capacity = capacity;
        this.devices = new ArrayList<>();
    }

    // Thêm thiết bị vào phòng học, không chấp nhận null và không trùng mã
    public void addDevice(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("Thiết bị không được null.");
        }
        if (findDevice(device.getDeviceId()) != null) {
            throw new IllegalArgumentException("Thiết bị có mã " + device.getDeviceId() + " đã tồn tại trong phòng.");
        }
        devices.add(device);
    }

    // Xóa thiết bị khỏi phòng học theo mã thiết bị
    public boolean removeDevice(String deviceId) {
        if (deviceId == null) {
            return false;
        }
        Device target = findDevice(deviceId);
        if (target != null) {
            return devices.remove(target);
        }
        return false;
    }

    // Tìm kiếm thiết bị trong phòng học theo mã
    public Device findDevice(String deviceId) {
        if (deviceId == null) {
            return null;
        }
        for (Device d : devices) {
            if (d.getDeviceId().equalsIgnoreCase(deviceId.trim())) {
                return d;
            }
        }
        return null;
    }

    // Tính tổng chi phí bảo trì dự kiến trong năm của toàn bộ thiết bị (áp dụng tính đa hình)
    public double calculateAnnualMaintenanceCost() {
        double totalCost = 0;
        for (Device d : devices) {
            totalCost += d.calculateAnnualMaintenanceCost();
        }
        return totalCost;
    }

    // Lọc danh sách thiết bị cần bảo trì: đang UnderMaintenance hoặc đã dùng trên 5 năm
    public List<Device> getDevicesRequiringMaintenance() {
        List<Device> result = new ArrayList<>();
        int currentYear = Year.now().getValue();

        for (Device d : devices) {
            int yearsInUse = currentYear - d.getYearOfUse();
            if (d.getStatus() == DeviceStatus.UnderMaintenance || yearsInUse > 5) {
                result.add(d);
            }
        }
        return result;
    }
}