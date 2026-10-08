import java.util.Scanner;

class Largest {

    int findLargest(int a, int b, int c) {
        if (a >= b && a >= c)
            return a;
        else if (b >= a && b >= c)
            return b;
        else
            return c;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first number: ");
        int a = sc.nextInt();

        System.out.print("Enter second number: ");
        int b = sc.nextInt();

        System.out.print("Enter third number: ");
        int c = sc.nextInt();

        Largest obj = new Largest();
        int largest = obj.findLargest(a, b, c);

        System.out.println("Largest number = " + largest);

        sc.close();
    }
}
