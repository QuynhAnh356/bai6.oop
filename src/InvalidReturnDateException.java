// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
import java.time.LocalDate;

public class InvalidReturnDateException extends BorrowingException {
    private final LocalDate returnedDate;
    private final LocalDate borrowedDate;

    public InvalidReturnDateException(LocalDate returnedDate, LocalDate borrowedDate) {
        super(String.format("Ngày trả (%s) không hợp lệ vì xảy ra trước ngày mượn (%s).", returnedDate, borrowedDate));
        this.returnedDate = returnedDate;
        this.borrowedDate = borrowedDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }
}
