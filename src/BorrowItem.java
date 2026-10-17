// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

    public boolean isReturned() {
        return this.returnedDate != null;
    }

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
        this.device.markAsAvailable(); 
    }

    public boolean isOverdue() {
        LocalDate compareDate = (returnedDate != null) ? returnedDate : LocalDate.now();
        return compareDate.isAfter(dueDate);
    }

    public int getLateDays() {
        LocalDate compareDate = (returnedDate != null) ? returnedDate : LocalDate.now();
        if (compareDate.isAfter(dueDate)) {
            return (int) ChronoUnit.DAYS.between(dueDate, compareDate);
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
