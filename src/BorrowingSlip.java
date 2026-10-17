// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BorrowingSlip {
  
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

    public List<BorrowItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addDevice(TeachingDevice device, int borrowDays) throws BorrowingException {
        if (device == null) {
            throw new IllegalArgumentException("Thiết bị không được null.");
        }

        if (borrowDays <= 0 || borrowDays > device.getMaxBorrowDays()) {
            throw new InvalidBorrowPeriodException(device.getDeviceId(), borrowDays, device.getMaxBorrowDays());
        }

        for (BorrowItem item : items) {
            if (item.getDevice().getDeviceId().equalsIgnoreCase(device.getDeviceId())) {
                throw new DuplicateDeviceException(device.getDeviceId());
            }
        }

        if (items.size() >= borrower.getMaximumDevices()) {
            throw new BorrowLimitExceededException(borrower.getBorrowerId(), borrower.getMaximumDevices());
        }

        if (!device.isAvailable()) {
            throw new DeviceUnavailableException(device.getDeviceId(), device.getStatus());
        }

        LocalDate dueDate = borrowedDate.plusDays(borrowDays);
        BorrowItem newItem = new BorrowItem(device, borrowedDate, dueDate);

        device.markAsBorrowed(); // Chuyển trạng thái thiết bị thành Borrowed
        items.add(newItem);      // Thêm dòng mượn vào phiếu
    }

    public double returnDevice(String deviceId, LocalDate returnedDate) throws BorrowingException {
        if (deviceId == null || deviceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã thiết bị cần trả không được để trống.");
        }

        BorrowItem targetItem = null;
        for (BorrowItem item : items) {
            if (item.getDevice().getDeviceId().equalsIgnoreCase(deviceId.trim())) {
                targetItem = item;
                break;
            }
        }

        if (targetItem == null) {
            throw new DeviceNotInSlipException(deviceId.trim());
        }

        if (targetItem.isReturned()) {
            throw new DeviceAlreadyReturnedException(deviceId.trim());
        }

        if (returnedDate == null || returnedDate.isBefore(borrowedDate)) {
            throw new InvalidReturnDateException(returnedDate, borrowedDate);
        }

        targetItem.returnDevice(returnedDate);
        return targetItem.getLateDays() * DAILY_LATE_FEE;
    }
    public double calculateTotalLateFee() {
        double totalFee = 0;
        for (BorrowItem item : items) {
            totalFee += item.getLateDays() * DAILY_LATE_FEE;
        }
        return totalFee;
    }

    public boolean hasUnreturnedItems() {
        for (BorrowItem item : items) {
            if (!item.isReturned()) {
                return true;
            }
        }
        return false;
    }

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
