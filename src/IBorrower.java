// Interface đại diện cho đối tượng người mượn thiết bị
public interface IBorrower {
    // Lấy mã người mượn
    String getBorrowerId();

    // Lấy họ và tên người mượn
    String getFullName();

    // Lấy số lượng thiết bị tối đa được phép mượn trên một phiếu
    int getMaximumDevices();
}