import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class Main {
        private static CSVWriter writer;
        private static CSVReader reader;
        private static ArrayList<String[]> userBufferList;
        private static BufferedReader readBufferInput;
        private static Scanner readScannerInput;
        private static ArrayList<String[]> insertUserList;

        static {
                try {
                        writer = new CSVWriter(new FileWriter("data/danhba.csv", true));
                        reader = new CSVReader(new FileReader("data/danhba.csv"));
                        userBufferList = new ArrayList<>();
                        readBufferInput = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
                        readScannerInput = new Scanner(System.in);
                        insertUserList = new ArrayList<>();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        public static void main(String[] args) {
                showMain();
        }

        public static void showMain() {
                try {
                        String loopMain = "loop";
                        while (!loopMain.equals("8")) {
                                System.out.println("----Chương trình quản lý danh bạ----");
                                System.out.println("Chọn chức năng theo số");
                                System.out.println("1. Xem danh sách");
                                System.out.println("2. Thêm mới");
                                System.out.println("3. Cập nhật");
                                System.out.println("4. Xóa");
                                System.out.println("5. Tìm kiếm");
                                System.out.println("6. Đọc từ file");
                                System.out.println("7. Ghi vào file");
                                System.out.println("8. Thoát");
                                System.out.print("chọn chức năng: ");
                                loopMain = readBufferInput.readLine();
                                switchCaseUserChoice(loopMain);
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        public static void switchCaseUserChoice(String loopMain) {
                switch (loopMain) {
                        case "1":
                                //do xem danh sach
                                if (userBufferList.isEmpty()) {
                                        System.out.println("Doc tu file truoc roi moi xem duoc danh sach");
                                } else {
                                        xemDanhSach();
                                }
                                break;
                        case "2":
                                //do them moi
                                try {
                                        if (userBufferList.isEmpty()) {
                                                System.out.println("Doc tu file truoc moi duoc them moi");
                                        } else {
                                                User userInput = getInputFromUser();
                                                userBufferList.add(userInput.getArray());
                                                System.out.println("Them moi thanh. ban co muon ghi vao file khong? Neu khong ban co the ghi bang chuc nang so 7");
                                                System.out.println("1. Yes");
                                                System.out.println("2. No");
                                                String isWriteFile = readBufferInput.readLine();
                                                if (isWriteFile.equals("1")) {
                                                        overrideFile(userBufferList);
                                                }
                                        }

                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                                break;
                        case "3":
                                //cap nhat
                                if (userBufferList.isEmpty()) {
                                        System.out.println("Doc tu file truoc roi moi cap nhat duoc danh sach");
                                } else {
                                        //do some thing
                                        int userIndex = findUser();
                                        if (userIndex == -1) {
                                                System.out.println("can't find user");
                                        } else {
                                                System.out.println("user at " + userIndex);
                                                //do update
                                                User userInputUpdate = getInputFromUser();
                                                userBufferList.set(userIndex, userInputUpdate.getArray());
                                                //ghi de file
                                                overrideFile(userBufferList);
                                        }
                                }
                                break;
                        case "4":
                                //xoa
                                if (userBufferList.isEmpty()) {
                                        System.out.println("Doc tu file truoc roi moi xoa duoc danh sach");
                                } else {
                                        int deleteUser = findUser();
                                        if (deleteUser == -1) {
                                                System.out.println("can't find user");
                                        } else {
                                                System.out.println("user at " + deleteUser);
                                                //do delete
                                                userBufferList.remove(deleteUser);
                                                //ghi de file
                                                overrideFile(userBufferList);
                                        }
                                }

                                break;
                        case "5":
                                //tim kiem
                                if (userBufferList.isEmpty()) {
                                        System.out.println("Doc file truoc roi moi tim kiem!");
                                } else {
                                        int findUser = findUser();
                                        if (findUser != -1) {
                                                System.out.println("User can tim co thong tin la: ");
                                                System.out.println(displayContactDetail(userBufferList.get(findUser)));
                                        } else {
                                                System.out.println("Khong the tim thay user!");
                                        }
                                }

                                break;
                        case "6":
                                //doc tu file
                                try {
                                        System.out.println("neu doc file du lieu tu bo nho dem se mat:");
                                        System.out.println("1. Dong y!");
                                        System.out.println("2. Tu choi");
                                        String docFile = "2";
                                        docFile = readBufferInput.readLine();
                                        if (docFile.equals("1")) {
                                                docFile();
                                                System.out.println("doc file thanh cong");
                                        }
                                        break;
                                } catch (IOException e) {
                                        System.out.println("khong the doc file");
                                        break;
                                }
                        case "7":
                                //ghi vao file
                                try {
                                        if (userBufferList.isEmpty()) {
                                                System.out.println("Khong co du lieu de ghi");
                                        } else {
                                                overrideFile(userBufferList);
                                                System.out.println("ghi thanh cong");
                                        }
                                        break;
                                } catch (Exception e) {
                                        e.printStackTrace();
                                        break;
                                }

                        case "8":
                                // cam on
                                System.out.println("Welcome!");
                                try {
                                        writer.flush();
                                        writer.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                                break;
                }
        }

        public static void docFile() {

                try {
                        List<String[]> exportList = new ArrayList<>();
                        exportList = reader.readAll();
                        userBufferList = new ArrayList<>(exportList);

                } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("can't read from file");
                }
        }

        public static void xemDanhSach() {
                try {
                        String enter = "-1";
                        int count = 1;
                        while (count < userBufferList.size()) {
                                System.out.println("phone number | group | full name | gender | address | birthday | email");
                                for (int i = count; (i < count + 5) && i < userBufferList.size(); i++) {
                                        //System.out.println(Arrays.toString(userBufferList.get(i)));
                                        System.out.println(displayContactDetail(userBufferList.get(i)));
                                }

                                System.out.println("Press Enter: ");

                                while (!enter.equals("")) {
                                        enter = readBufferInput.readLine();
                                }
                                enter = "-1";
                                count += 5;
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        public static User getInputFromUser() {

                try {

                        System.out.println("Nhap ten day du: ");
                        String fullName = readBufferInput.readLine();
                        System.out.println("Nhap Group: ");
                        String group = readBufferInput.readLine();

                        System.out.println("Nhap so dien thoai: ");
                        boolean checkPhoneNumber = false;
                        String phoneNumber = "";
                        while (checkPhoneNumber == false) {
                                System.out.println("so dien thoai co tu 9 den 11 so khong bao gom ky tu:");
                                phoneNumber = readBufferInput.readLine();
                                checkPhoneNumber = verifyPhoneNumber(phoneNumber);
                        }

                        System.out.println("Nhap gender: ");
                        System.out.println("1. male");
                        System.out.println("2. female");
                        System.out.println("3. other");
                        String checkGender = readBufferInput.readLine();
                        String gender = "";
                        if (checkGender.equals("1")) {
                                gender = "male";
                        } else if (checkGender.equals("2")) {
                                gender = "female";
                        } else {
                                gender = "other";
                        }


                        System.out.println("Nhap address: ");
                        String address = readBufferInput.readLine();

                        System.out.println("Nhap Email: ");
                        boolean checkEmail = false;
                        String email = "";
                        while (checkEmail == false) {
                                System.out.println("Email bat dau bang chu cai, ket thuc phai la(org | com | vn):");
                                email = readBufferInput.readLine();
                                checkEmail = verifyEmail(email);
                        }


                        System.out.println("Nhap Ngay Sinh: ");
                        boolean checkNgay = false;
                        int day = -1;
                        while (checkNgay == false) {
                                System.out.println("ngay sinh tu 1 - 31");
                                day = readScannerInput.nextInt();
                                checkNgay = checkNgaySinh(day);
                        }


                        System.out.println("Nhap Thang Sinh: ");
                        boolean checkThang = false;
                        int month = -1;
                        while (checkThang == false) {
                                System.out.println("thang sinh tu 1 - 12");
                                month = readScannerInput.nextInt();
                                checkThang = checkThangSinh(month);
                        }

                        System.out.println("Nhap Thang Sinh: ");
                        boolean checkNam = false;
                        int year = -1;
                        while (checkNam == false) {
                                System.out.println("nam sinh tu 1900 - 12");
                                year = readScannerInput.nextInt();
                                checkNam = checkNamSinh(year);
                        }

                        LocalDate birthDay = LocalDate.of(year, month, day);

                        User newUser = new User(fullName, phoneNumber, address, email, group, gender, birthDay);
                        return newUser;

                } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Can't input");
                        return null;
                }

        }

        public static int findUser() {

                try {
                        System.out.println("Nhap ten,so dien thoai or email user can thao tac");
                        String findString = readBufferInput.readLine();
                        int findUser = -1;

                        for (int i = 0; i < userBufferList.size(); i++) {
                                for (int j = 0; j < userBufferList.get(i).length; j++) {
                                        if (userBufferList.get(i)[j].equals(findString)) {
                                                findUser = i;
                                                break;
                                        }
                                }
                        }
                        return findUser;
                } catch (IOException e) {
                        e.printStackTrace();
                        return -1;
                }

        }

        public static boolean checkNgaySinh(int day) {
                if (day >= 1 && day <= 31)
                        return true;
                else return false;
        }

        public static boolean checkThangSinh(int month) {
                if (month >= 1 && month <= 12)
                        return true;
                else return false;
        }

        public static boolean checkNamSinh(int year) {
                if (year >= 1900 && year < 2020)
                        return true;
                else return false;
        }

        public static boolean verifyEmail(String email) {
                //String expression="^[\\w\\.]+@[\\w\\.]+(com|vn|org)$}";
                String expression = "^[a-zA-Z0-9\\.]+@[a-z\\.]+(com|org|vn)";
                Boolean isMatchEmail = Pattern.matches(expression, email);
                return isMatchEmail;
        }

        public static boolean verifyPhoneNumber(String phone) {
                String expression = "[0-9]{9,11}";
                Boolean isMatchPhone = Pattern.matches(expression, phone);
                return isMatchPhone;
        }

        public static void overrideFile(ArrayList<String[]> userBufferList) {
                try {
                        CSVWriter newWriter = new CSVWriter(new FileWriter("data/danhba.csv"));

                        for (int i = 0; i < userBufferList.size(); i++) {
                                newWriter.writeNext(userBufferList.get(i));
                        }
                        newWriter.flush();
                } catch (IOException e) {
                        e.printStackTrace();
                }

        }

        public static String displayContactDetail(String[] line) {
                String displayString = String.join(" | ", line);
                return displayString;
        }

}