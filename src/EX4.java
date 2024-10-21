import java.util.Scanner;

public class EX4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input start: ");
        int startNum = Integer.parseInt(scanner.nextLine());
        System.out.print("Input end: ");
        int endNum = Integer.parseInt(scanner.nextLine());
        scanner.close();
    }
}
