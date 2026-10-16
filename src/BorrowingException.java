// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
// Ngoại lệ cơ sở cho toàn bộ nghiệp vụ mượn - trả thiết bị
public class BorrowingException extends Exception {
    public BorrowingException(String message) {
        super(message);
    }

    public BorrowingException(String message, Throwable cause) {
        super(message, cause);
    }
}