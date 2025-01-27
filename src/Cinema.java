import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    private static int rows;
    private static int seats;
    private static char[][] cinema;
    private static boolean isRunning = true;
    private static final Scanner scanner = new Scanner(System.in);

    private static final char freeSeat = 'S';
    private static final char boughtSeat = 'B';

    private static int cinemaSeats;

    private static int purchasedTickets;
    private static double percentage;
    private static int currentIncome;
    private static int totalIncome;

    public static void main(String[] args) {
        // Read the number of rows and seats
        while (true) {
            try {
                System.out.println("Enter the number of rows: ");
                rows = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Enter the number of seats in each row: ");
                seats = scanner.nextInt();
                scanner.nextLine();

                if (rows <= 0 || rows > 100 || seats <= 0 || seats > 100) {
                    System.out.println("Please enter a number between 1 and 100");
                    continue;
                }

                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
                scanner.nextLine();
            }
        }

        //  Create a 2D array to represent the cinema
        createCinema();

        // Loop for display main menu
        while (isRunning) {
            System.out.println(""" 
            
            1. Show the seats
            2. Buy a ticket
            3. Statistics
            0. Exit
            """);

            int choice = scanner.nextInt();
            action(choice);
        }
        scanner.close();
    }

    // Method to create an array for the cinema
    public static void createCinema() {
        cinema = new char[rows][seats];
        for (char[] chars : cinema) {
            Arrays.fill(chars, freeSeat);
        }

        cinemaSeats = rows * seats;


        if (cinemaSeats >= 60) {
            bigCinema();
        } else {
            totalIncome = cinemaSeats * 10;
        }

        /*
         It used to look like this
         for (int i = 0; i < cinema.length; i++) {
             for (int j = 0; j < cinema[i].length; j++) {
                 cinema[i][j] = 'S';
             }
         }
        */
    }
    
    public static void action(int choice) {
            switch (choice) {
                case 1 -> representCinema();
                case 2 -> chooseSeat();
                case 3 -> statistics();
                case 0 -> isRunning = false;
                default -> System.out.println("Invalid choice");
            }
    }

    public static void chooseSeat() {
        int ticketRow = 0;
        int ticketSeat = 0;
        boolean isSeatAvailable = false;
        int[] userSeat = new int[2];

        do {
            try {
                System.out.println("\nEnter a row number: ");
                ticketRow = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Enter a seat number in that row: ");
                ticketSeat = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("\nWrong input!\n");
            }

            if (ticketRow > 0 && ticketRow <= rows && ticketSeat > 0 && ticketSeat <= seats) {

                isSeatAvailable = true;
            } else {
                System.out.println("\nWrong input!\n");
            }
        } while (!isSeatAvailable);

        ticketRow -= 1;
        ticketSeat -= 1;

        userSeat[0] = ticketRow;
        userSeat[1] = ticketSeat;
        buyTicket(userSeat);
    }

    // Method to buy a ticket
    public static void buyTicket(int[] seat) {
        boolean seatAvailable = true;

        if (cinema[seat[0]][seat[1]] == boughtSeat) {
            seatAvailable = false;
            System.out.println("That ticket has already been purchased!\n");
            chooseSeat();
        }

        if (seatAvailable) {
            int frontHalfRows = rows / 2;

            int ticketPrice = 10;

            if (cinemaSeats >= 60) {
                if (seat[0] >= frontHalfRows) {
                    ticketPrice = 8;
                }
            }

            System.out.println("\nTicket price: $" + ticketPrice);

            cinema[seat[0]][seat[1]] = boughtSeat;

            purchasedTickets++;
            currentIncome += ticketPrice;
            percentage = (100.0 / cinemaSeats) * purchasedTickets;
        }
    }

    // Method to represent the cinema seats
    public static void representCinema() {
        System.out.println("\nCinema:");

        for (int i = 0; i <= cinema[0].length; i++) {
            if (i == 0) {
                System.out.print("  ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();

        for (int i = 0; i < cinema.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < cinema[i].length; j++) {
                System.out.print(cinema[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void bigCinema() {
        int halfOfRows = rows / 2;

        int frontHalf = halfOfRows * seats;
        int backHalf = cinemaSeats - frontHalf;

        totalIncome = (frontHalf * 10) + (backHalf * 8);
    }

    public static void statistics() {
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.println(String.format("Percentage: %.2f", percentage) + '%');
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }
}




