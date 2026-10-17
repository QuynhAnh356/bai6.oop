// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

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

    public void addDevice(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("Thiết bị không được null.");
        }
        if (findDevice(device.getDeviceId()) != null) {
            throw new IllegalArgumentException("Thiết bị có mã " + device.getDeviceId() + " đã tồn tại trong phòng.");
        }
        devices.add(device);
    }

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

    public double calculateAnnualMaintenanceCost() {
        double totalCost = 0;
        for (Device d : devices) {
            totalCost += d.calculateAnnualMaintenanceCost();
        }
        return totalCost;
    }

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
