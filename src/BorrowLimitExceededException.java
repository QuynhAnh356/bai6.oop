// Phát sinh khi người mượn đã đạt số lượng thiết bị tối đa cho phép
public class BorrowLimitExceededException extends BorrowingException {
    private final String borrowerId;
    private final int maximumDevices;

    public BorrowLimitExceededException(String borrowerId, int maximumDevices) {
        super(String.format("Người mượn '%s' đã đạt giới hạn tối đa (%d thiết bị/phiếu).", borrowerId, maximumDevices));
        this.borrowerId = borrowerId;
        this.maximumDevices = maximumDevices;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public int getMaximumDevices() {
        return maximumDevices;
    }
}