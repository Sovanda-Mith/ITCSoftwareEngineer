import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;;

public class App {
    private static List<Reservation> reservations = new ArrayList<>();
    private static int reservationId = 1;

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            showMenu();
            try {
                option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1:
                        showReservations();
                        break;
                    case 2:
                        System.out.println("Adding new reservation...");
                        addReservation(scanner);
                        break;
                    case 3:
                        System.out.println("Remove reservation");
                        removeReservation(scanner);
                        break;
                    case 4:
                        System.out.println("Update reservation");
                        updateReservation(scanner);
                        break;
                    case 5:
                        System.out.println("Swap room");
                        break;
                    default:
                        if (option != 6) {
                            System.out.println("Invalid option");
                        } else {
                            System.out.println("Exiting...");
                        }
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid option");
                option = 0; // Reset the option to allow the loop to continue
            } catch (NumberFormatException e) {
                System.out.println("Invalid option");
                option = 0; // Reset the option to allow the loop to continue
            }
        } while (option != 6);
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("1. List all reservations");
        System.out.println("2. Add new reservation");
        System.out.println("3. Remove reservation");
        System.out.println("4. Update reservation");
        System.out.println("5. Swap room");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");

    }

    private static void showReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    private static void addReservation(Scanner scanner) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        formatter.setLenient(false);
        System.out.print("Enter room number(e.g. A-123, F-456): ");
        String roomNumber = scanner.nextLine();

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        Date startReserve = null;
        while (startReserve == null) {
            try {
                System.out.print("Enter check in date(e.g. 2024/11/25 12:00): ");
                String checkIn = scanner.nextLine();
                startReserve = formatter.parse(checkIn);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }

        Date endReserve = null;
        while (endReserve == null) {
            try {
                System.out.print("Enter check out date(e.g. 2024/11/27 12:00): ");
                String checkOut = scanner.nextLine();
                endReserve = formatter.parse(checkOut);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
        System.out.print("Enter other details: ");
        String other = scanner.nextLine();
        try {
            Reservation reservation = new Reservation(reservationId, roomNumber, name, startReserve, endReserve, other);
            reservations.add(reservation);
            System.out.println("Reservation added successfully");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Reservation not added. Please try again");
            return;
        }
        reservationId++;
    }

    private static void removeReservation(Scanner scanner) {
        Date now = new Date();
        for (Reservation reservation : reservations) {
            if (reservation.getStartReserve().after(now)) {
                System.out.println(reservation);
            }
        }
        try {
            System.out.print("Enter reservation ID to remove: ");
            int id = Integer.parseInt(scanner.nextLine());

            boolean removed = false;
            for (Reservation reservation : reservations) {
                if (reservation.getId() == id) {
                    reservations.remove(reservation);
                    System.out.println("Reservation removed successfully");
                    removed = true;
                    break;
                }
            }
            if (!removed) {
                System.out.println("Reservation not found");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid reservation ID. Please try again.");
        }
    }

    private static void updateReservation(Scanner scanner) {
        Date now = new Date();
        for (Reservation reservation : reservations) {
            if (reservation.getStartReserve().after(now)) {
                System.out.println(reservation);
            }
        }
        try {
            System.out.print("Enter reservation ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            boolean updated = false;
            for (Reservation reservation : reservations) {
                if (reservation.getId() == id) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

                    System.out.print("Enter new room number: ");
                    String roomNum = scanner.nextLine();

                    System.out.print("Enter new name: ");
                    String name = scanner.nextLine();

                    Date startReserve = null;
                    while (startReserve == null) {
                        try {
                            System.out.print("Enter new check in date(e.g. 2024/11/25 12:00): ");
                            String checkIn = scanner.nextLine();
                            startReserve = formatter.parse(checkIn);
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please try again.");
                        }
                    }

                    Date endReserve = null;
                    while (endReserve == null) {
                        try {
                            System.out.print("Enter check out date(e.g. 2024/11/27 12:00): ");
                            String checkOut = scanner.nextLine();
                            endReserve = formatter.parse(checkOut);
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please try again.");
                        }
                    }

                    System.out.print("Enter other details: ");
                    String other = scanner.nextLine();

                    // update
                    try {
                        reservation.setRoomNumber(roomNum);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }

                    try {
                        reservation.setName(name);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }

                    try {
                        reservation.setStartReserve(startReserve);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }

                    try {
                        reservation.setEndReserve(endReserve);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }

                    reservation.setOther(other);
                    System.out.println("Reservation updated successfully");
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                System.out.println("Reservation not updated.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid reservation ID. Please try again.");
        }
    }

}
