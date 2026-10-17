// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh

public class Lecturer implements IBorrower {
    private final String borrowerId;
    private final String fullName;

    public Lecturer(String borrowerId, String fullName) {
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

    @Override
    public int getMaximumDevices() {
        return 3;
    }

    @Override
    public String toString() {
        return String.format("Giảng viên: [%s] %s (Tối đa: %d thiết bị)", borrowerId, fullName, getMaximumDevices());
    }
}
