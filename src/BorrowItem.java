import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Quản lý chi tiết việc mượn và trả của một thiết bị cụ thể trong phiếu
public class BorrowItem {
    private final TeachingDevice device;
    private final LocalDate borrowedDate;
    private final LocalDate dueDate;
    private int fromPeriod;
    private int toPeriod;
    private LocalDate returnedDate;

    public BorrowItem(TeachingDevice device, LocalDate borrowedDate, LocalDate dueDate) {
        if (device == null) {
            throw new IllegalArgumentException("Thiết bị mượn không được null.");
        }
        if (borrowedDate == null) {
            throw new IllegalArgumentException("Ngày mượn không được để trống.");
        }
        if (dueDate == null || dueDate.isBefore(borrowedDate)) {
            throw new IllegalArgumentException("Hạn trả phải sau hoặc bằng ngày mượn.");
        }

        this.device = device;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.returnedDate = null; // Ban đầu chưa trả, mặc định là null
    }

    public TeachingDevice getDevice() {
        return device;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public int getFromPeriod() {
        return fromPeriod;
    }

    public void setFromPeriod(int fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public int getToPeriod() {
        return toPeriod;
    }

    public void setToPeriod(int toPeriod) {
        if (toPeriod < fromPeriod) {
            throw new IllegalArgumentException("Tiết kết thúc không được nhỏ hơn tiết bắt đầu.");
        }
        this.toPeriod = toPeriod;
    }

    // Kiểm tra xem thiết bị này đã được trả hay chưa
    public boolean isReturned() {
        return this.returnedDate != null;
    }

    // Ghi nhận trả thiết bị
    public void returnDevice(LocalDate returnedDate) {
        if (isReturned()) {
            throw new IllegalStateException("Thiết bị này đã được trả trước đó.");
        }
        if (returnedDate == null) {
            throw new IllegalArgumentException("Ngày trả không được để trống.");
        }
        if (returnedDate.isBefore(borrowedDate)) {
            throw new IllegalArgumentException("Ngày trả không được trước ngày mượn.");
        }

        this.returnedDate = returnedDate;
        this.device.markAsAvailable(); // Trả xong chuyển thiết bị về trạng thái sẵn sàng
    }

    // Kiểm tra xem món đồ này trả có bị trễ hạn hay không
    public boolean isOverdue() {
        if (returnedDate != null) {
            return returnedDate.isAfter(dueDate);
        }
        // Nếu chưa trả, so sánh với ngày hôm nay
        return LocalDate.now().isAfter(dueDate);
    }

    // Tính số ngày trễ hạn thực tế
    public int getLateDays() {
        if (returnedDate != null && returnedDate.isAfter(dueDate)) {
            return (int) ChronoUnit.DAYS.between(dueDate, returnedDate);
        }
        return 0;
    }

    @Override
    public String toString() {
        String statusText = isReturned()
                ? "Đã trả ngày " + returnedDate + (isOverdue() ? " (Trễ " + getLateDays() + " ngày)" : " (Đúng hạn)")
                : "Chưa trả (Hạn: " + dueDate + ")";
        return String.format("%s | Mượn: %s | %s", device.toString(), borrowedDate, statusText);
    }
}