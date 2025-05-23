public class Main {
    public static void main(String[] args) {
    //载入登录窗口
    new LoginUI();
        new MenutSystemUI("admin", "管理员");
        new MenutSystemUI("user", "普通用户");

    }
}