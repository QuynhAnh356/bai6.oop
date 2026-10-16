// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Lớp quản lý phiếu mượn thiết bị và xử lý giao dịch mượn/trả
public class BorrowingSlip {
    // Mức phí phạt trả muộn quy định: 50.000 VNĐ / ngày trễ
    public static final double DAILY_LATE_FEE = 50000.0;

    private final String slipId;
    private final IBorrower borrower;
    private final String teachingRoom;
    private final LocalDate borrowedDate;
    private final List<BorrowItem> items;

    public BorrowingSlip(String slipId, IBorrower borrower, String teachingRoom, LocalDate borrowedDate) {
        if (slipId == null || slipId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống.");
        }
        if (borrower == null) {
            throw new IllegalArgumentException("Người mượn không được null.");
        }
        if (teachingRoom == null || teachingRoom.trim().isEmpty()) {
            throw new IllegalArgumentException("Phòng học không được để trống.");
        }
        if (borrowedDate == null) {
            throw new IllegalArgumentException("Ngày mượn không được để trống.");
        }

        this.slipId = slipId.trim();
        this.borrower = borrower;
        this.teachingRoom = teachingRoom.trim();
        this.borrowedDate = borrowedDate;
        this.items = new ArrayList<>();
    }

    public String getSlipId() {
        return slipId;
    }

    public IBorrower getBorrower() {
        return borrower;
    }

    public String getTeachingRoom() {
        return teachingRoom;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    // Trả về danh sách dạng chỉ đọc để bảo vệ tính đóng gói
    public List<BorrowItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    // Thêm thiết bị vào phiếu mượn, đảm bảo tính nhất quán (Atomic / Transaction Safety)
    public void addDevice(TeachingDevice device, int borrowDays) throws BorrowingException {
        // 1. Kiểm tra thiết bị không được null
        if (device == null) {
            throw new IllegalArgumentException("Thiết bị không được null.");
        }

        // 2. Kiểm tra số ngày mượn phải dương và không vượt quá giới hạn của thiết bị
        if (borrowDays <= 0 || borrowDays > device.getMaxBorrowDays()) {
            throw new InvalidBorrowPeriodException(device.getDeviceId(), borrowDays, device.getMaxBorrowDays());
        }

        // 3. Kiểm tra thiết bị chưa tồn tại trong phiếu này
        for (BorrowItem item : items) {
            if (item.getDevice().getDeviceId().equalsIgnoreCase(device.getDeviceId())) {
                throw new DuplicateDeviceException(device.getDeviceId());
            }
        }

        // 4. Kiểm tra người mượn chưa đạt giới hạn số lượng thiết bị cho phép
        if (items.size() >= borrower.getMaximumDevices()) {
            throw new BorrowLimitExceededException(borrower.getBorrowerId(), borrower.getMaximumDevices());
        }

        // 5. Kiểm tra thiết bị phải đang ở trạng thái sẵn sàng (Available)
        if (!device.isAvailable()) {
            throw new DeviceUnavailableException(device.getDeviceId(), device.getStatus());
        }

        // Khi toàn bộ 5 điều kiện hợp lệ mới thực hiện cập nhật hệ thống
        LocalDate dueDate = borrowedDate.plusDays(borrowDays);
        BorrowItem newItem = new BorrowItem(device, borrowedDate, dueDate);

        device.markAsBorrowed(); // Chuyển trạng thái thiết bị thành Borrowed
        items.add(newItem);      // Thêm dòng mượn vào phiếu
    }

    // Trả thiết bị và tính phí phạt trễ hạn nếu có
    public double returnDevice(String deviceId, LocalDate returnedDate) throws BorrowingException {
        if (deviceId == null || deviceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã thiết bị cần trả không được để trống.");
        }

        // 1. Tìm BorrowItem tương ứng trong phiếu
        BorrowItem targetItem = null;
        for (BorrowItem item : items) {
            if (item.getDevice().getDeviceId().equalsIgnoreCase(deviceId.trim())) {
                targetItem = item;
                break;
            }
        }

        // 2. Không tìm thấy thiết bị trong phiếu
        if (targetItem == null) {
            throw new DeviceNotInSlipException(deviceId.trim());
        }

        // 3. Thiết bị đã được trả trước đó
        if (targetItem.isReturned()) {
            throw new DeviceAlreadyReturnedException(deviceId.trim());
        }

        // 4. Kiểm tra ngày trả không được trước ngày mượn
        if (returnedDate == null || returnedDate.isBefore(borrowedDate)) {
            throw new InvalidReturnDateException(returnedDate, borrowedDate);
        }

        // 5. Ghi nhận trả thiết bị (bên trong hàm này sẽ tự chuyển thiết bị về Available)
        targetItem.returnDevice(returnedDate);

        // 6. Tính phí trễ hạn cho thiết bị này
        return targetItem.getLateDays() * DAILY_LATE_FEE;
    }

    // Tính tổng chi phí phạt trả muộn của toàn bộ thiết bị trong phiếu
    public double calculateTotalLateFee() {
        double totalFee = 0;
        for (BorrowItem item : items) {
            totalFee += item.getLateDays() * DAILY_LATE_FEE;
        }
        return totalFee;
    }

    // Kiểm tra xem phiếu có thiết bị nào chưa trả hay không
    public boolean hasUnreturnedItems() {
        for (BorrowItem item : items) {
            if (!item.isReturned()) {
                return true;
            }
        }
        return false;
    }

    // Kiểm tra xem phiếu có thiết bị nào đang bị trễ hạn hay không
    public boolean isOverdue() {
        for (BorrowItem item : items) {
            if (item.isOverdue()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("PHIẾU MƯỢN [%s]\n", slipId));
        sb.append(String.format("Người mượn: %s | Phòng: %s | Ngày mượn: %s\n",
                borrower.getFullName(), teachingRoom, borrowedDate));
        sb.append("Danh sách thiết bị:\n");
        for (int i = 0; i < items.size(); i++) {
            sb.append(String.format("  %d. %s\n", i + 1, items.get(i).toString()));
        }
        double totalLateFee = calculateTotalLateFee();
        if (totalLateFee > 0) {
            sb.append(String.format(">> TỔNG TIỀN PHẠT TRỄ HẠN: %,.0f VNĐ\n", totalLateFee));
        }
        return sb.toString();
    }
}