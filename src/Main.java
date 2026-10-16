// Mã sinh viên: 202418841
// Họ tên: Dương Thị Quỳnh Anh
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // 1. KIỂM THỬ LUỒNG THÀNH CÔNG
        System.out.println("KIỂM THỬ LUỒNG THÀNH CÔNG");
        try {
            // Bước 1: Tạo một giảng viên
            Lecturer gv = new Lecturer("GV001", "TS. Nguyễn Văn A");
            System.out.println("1. Tạo giảng viên: " + gv);

            // Bước 2: Tạo điều khiển máy chiếu, điều hòa và micro không dây
            ProjectorRemote remoteMC = new ProjectorRemote("MC01", "Điều khiển máy chiếu Sony", 2023, 600000, DeviceStatus.Available, 3);
            AirConditionerRemote remoteDH = new AirConditionerRemote("DH01", "Điều khiển điều hòa Daikin", 2022, 450000, DeviceStatus.Available, 3);
            Microphone micKD = new Microphone("MIC01", "Micro không dây Shure", 2024, 1500000, DeviceStatus.Available, 1);
            System.out.println("2. Đã khởi tạo 3 thiết bị: Máy chiếu, Điều hòa, Micro.");
            LocalDate today = LocalDate.now();

            // Bước 3: Tạo phiếu mượn cho phòng dạy
            BorrowingSlip slip1 = new BorrowingSlip("PM001", gv, "Phòng 402", today);
            System.out.println("3. Tạo phiếu mượn: " + slip1.getSlipId() + " tại " + slip1.getTeachingRoom());

            // Bước 4: Thêm điều khiển máy chiếu (mượn 2 ngày) và micro (mượn 1 ngày) vào phiếu
            slip1.addDevice(remoteMC, 2);
            slip1.addDevice(micKD, 1);
            System.out.println("4. Thêm điều khiển máy chiếu và micro vào phiếu PM001 thành công.");

            // Bước 5: Trả điều khiển máy chiếu đúng hạn
            double feeMC = slip1.returnDevice("MC01", today.plusDays(2));
            System.out.printf("5. Trả máy chiếu 'MC01' đúng hạn. Tiền phạt: %,.0f VNĐ\n", feeMC);

            // Bước 6: Trả micro muộn 2 ngày
            double feeMic = slip1.returnDevice("MIC01", today.plusDays(3));
            System.out.printf("6. Trả micro 'MIC01' muộn 2 ngày. Tiền phạt: %,.0f VNĐ\n", feeMic);

            // Tạo thêm phiếu mượn thứ hai chứa Điều hòa (chưa trả) để phục vụ bước 7
            BorrowingSlip slip2 = new BorrowingSlip("PM002", gv, "Phòng 501", today);
            slip2.addDevice(remoteDH, 3);

            // Gom các phiếu vào danh sách quản lý
            List<BorrowingSlip> allSlips = List.of(slip1, slip2);

            // Bước 7: Hiển thị các phiếu trả muộn, chưa trả
            System.out.println("\n7. HIỂN THỊ CÁC PHIẾU TRẢ MUỘN VÀ CHƯA TRẢ:");
            System.out.println("\nCÁC PHIẾU CÓ THIẾT BỊ TRẢ MUỘN / QUÁ HẠN:");
            for (BorrowingSlip slip : allSlips) {
                if (slip.isOverdue() || slip.calculateTotalLateFee() > 0) {
                    System.out.println(slip);
                }
            }
            System.out.println("CÁC PHIẾU CÓ THIẾT BỊ CHƯA TRẢ");
            for (BorrowingSlip slip : allSlips) {
                if (slip.hasUnreturnedItems()) {
                    System.out.println(slip);
                }
            }
        } catch (BorrowingException e) {
            System.out.println("Lỗi nghiệp vụ: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi hệ thống: " + e.getMessage());
        }


        // 2. KIỂM THỬ CÁC LUỒNG NGOẠI LỆ

        System.out.println("\nKIỂM THỬ CÁC TÌNH HUỐNG PHÁT SINH NGOẠI LỆ:");
        LocalDate today = LocalDate.now();
        Lecturer gv = new Lecturer("GV002", "PGS. Lê Văn C");
        TeachingAssistant ta = new TeachingAssistant("TA001", "Trần Thị B");

        // Tình huống 1: Mượn điều khiển điều hòa đang bảo trì
        System.out.println("\nTình huống 1: Mượn thiết bị đang bảo trì");
        try {
            AirConditionerRemote acHong = new AirConditionerRemote("DH02", "Điều hòa Panasonic", 2021, 500000, DeviceStatus.UnderMaintenance, 3);
            BorrowingSlip slip1 = new BorrowingSlip("PM-EX01", gv, "Phòng 201", today);
            slip1.addDevice(acHong, 2);
        } catch (DeviceUnavailableException e) {
            System.out.println("Bắt ngoại lệ thành công: " + e.getMessage());
        } catch (BorrowingException e) {
            System.out.println("Lỗi khác: " + e.getMessage());
        }

        // Tình huống 2: Trợ giảng mượn thiết bị thứ ba
        System.out.println("\nTình huống 2: Trợ giảng mượn thiết bị thứ ba");
        try {
            BorrowingSlip slip2 = new BorrowingSlip("PM-EX02", ta, "Phòng 202", today);
            ProjectorRemote d1 = new ProjectorRemote("MC02", "Máy chiếu 1", 2023, 500000, DeviceStatus.Available, 3);
            ProjectorRemote d2 = new ProjectorRemote("MC03", "Máy chiếu 2", 2023, 500000, DeviceStatus.Available, 3);
            ProjectorRemote d3 = new ProjectorRemote("MC04", "Máy chiếu 3", 2023, 500000, DeviceStatus.Available, 3);

            slip2.addDevice(d1, 1);
            slip2.addDevice(d2, 1);
            slip2.addDevice(d3, 1);
        } catch (BorrowLimitExceededException e) {
            System.out.println("Bắt ngoại lệ thành công: " + e.getMessage());
        } catch (BorrowingException e) {
            System.out.println("Lỗi khác: " + e.getMessage());
        }

        // Tình huống 3: Thêm cùng một micro hai lần vào phiếu
        System.out.println("\nTình huống 3: Thêm cùng một micro hai lần");
        try {
            BorrowingSlip slip3 = new BorrowingSlip("PM-EX03", gv, "Phòng 203", today);
            Microphone mic = new Microphone("MIC02", "Micro phòng 203", 2023, 1000000, DeviceStatus.Available, 1);

            slip3.addDevice(mic, 1);
            slip3.addDevice(mic, 1);
        } catch (DuplicateDeviceException e) {
            System.out.println("Bắt ngoại lệ thành công: " + e.getMessage());
        } catch (BorrowingException e) {
            System.out.println("Lỗi khác: " + e.getMessage());
        }

        // Tình huống 4: Mượn micro 3 ngày dù giới hạn là 1 ngày
        System.out.println("\nTình huống 4: Mượn micro 3 ngày dù giới hạn 1 ngày");
        try {
            BorrowingSlip slip4 = new BorrowingSlip("PM-EX04", gv, "Phòng 204", today);
            Microphone mic1Day = new Microphone("MIC03", "Micro Shure", 2024, 1200000, DeviceStatus.Available, 1);

            slip4.addDevice(mic1Day, 3);
        } catch (InvalidBorrowPeriodException e) {
            System.out.println("Bắt ngoại lệ thành công: " + e.getMessage());
        } catch (BorrowingException e) {
            System.out.println("Lỗi khác: " + e.getMessage());
        }

        // Tình huống 5: Trả thiết bị không thuộc phiếu
        System.out.println("\nTình huống 5: Trả thiết bị không thuộc phiếu");
        try {
            BorrowingSlip slip5 = new BorrowingSlip("PM-EX05", gv, "Phòng 205", today);
            ProjectorRemote dTrongPhieu = new ProjectorRemote("MC05", "Máy chiếu 5", 2023, 500000, DeviceStatus.Available, 3);
            slip5.addDevice(dTrongPhieu, 1);

            slip5.returnDevice("MC999", today);
        } catch (DeviceNotInSlipException e) {
            System.out.println("Bắt ngoại lệ thành công: " + e.getMessage());
        } catch (BorrowingException e) {
            System.out.println("Lỗi khác: " + e.getMessage());
        }

        // Tình huống 6: Trả cùng một thiết bị hai lần
        System.out.println("\nTình huống 6: Trả cùng một thiết bị hai lần");
        try {
            BorrowingSlip slip6 = new BorrowingSlip("PM-EX06", gv, "Phòng 206", today);
            ProjectorRemote dTra2Lan = new ProjectorRemote("MC06", "Máy chiếu 6", 2023, 500000, DeviceStatus.Available, 3);
            slip6.addDevice(dTra2Lan, 1);

            slip6.returnDevice("MC06", today);
            slip6.returnDevice("MC06", today);
        } catch (DeviceAlreadyReturnedException e) {
            System.out.println("Bắt ngoại lệ thành công: " + e.getMessage());
        } catch (BorrowingException e) {
            System.out.println("Lỗi khác: " + e.getMessage());
        }

        // Tình huống 7: Trả thiết bị với ngày trả trước ngày mượn
        System.out.println("\nTình huống 7: Trả thiết bị trước ngày mượn");
        try {
            BorrowingSlip slip7 = new BorrowingSlip("PM-EX07", gv, "Phòng 207", today);
            ProjectorRemote dNgaySai = new ProjectorRemote("MC07", "Máy chiếu 7", 2023, 500000, DeviceStatus.Available, 3);
            slip7.addDevice(dNgaySai, 2);

            LocalDate ngayTruoc = today.minusDays(1);
            slip7.returnDevice("MC07", ngayTruoc);
        } catch (InvalidReturnDateException e) {
            System.out.println("Bắt ngoại lệ thành công: " + e.getMessage());
        } catch (BorrowingException e) {
            System.out.println("Lỗi khác: " + e.getMessage());
        }
    }
}