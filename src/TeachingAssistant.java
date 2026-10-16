// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
// Lớp đại diện cho Trợ giảng mượn thiết bị
public class TeachingAssistant implements IBorrower {
    private final String borrowerId;
    private final String fullName;

    public TeachingAssistant(String borrowerId, String fullName) {
        if (borrowerId == null || borrowerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã người mượn không được để trống.");
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống.");
        }

        this.borrowerId = borrowerId.trim();
        this.fullName = fullName.trim();
    }

    @Override
    public String getBorrowerId() {
        return borrowerId;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    // Trợ giảng được mượn tối đa 2 thiết bị trên một phiếu
    @Override
    public int getMaximumDevices() {
        return 2;
    }

    @Override
    public String toString() {
        return String.format("Trợ giảng: [%s] %s (Tối đa: %d thiết bị)", borrowerId, fullName, getMaximumDevices());
    }
}