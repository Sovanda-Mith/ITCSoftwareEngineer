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
                        swapReservation(scanner);
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
            System.out.println("No reservations found\n");
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
            System.out.println("Reservation added successfully\n");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Reservation not added. Please try again\n");
            return;
        }
        reservationId++;
    }

    private static void removeReservation(Scanner scanner) {
        Date now = new Date();
        boolean foundReservations = false;
        System.out.println("Reservations that can be removed(i.e. not yet started): \n");
        for (Reservation reservation : reservations) {
            if (reservation.getStartReserve().after(now)) {
                System.out.println(reservation);
                foundReservations = true;
            }
        }
        if (foundReservations) {
            try {
                System.out.print("Enter reservation ID to remove: ");
                int id = Integer.parseInt(scanner.nextLine());

                boolean removed = false;
                for (Reservation reservation : reservations) {
                    if (reservation.getId() == id) {
                        reservations.remove(reservation);
                        System.out.println("Reservation removed successfully\n");
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
        } else {
            System.out.println("No reservations to remove\n");
        }
    }

    private static void updateReservation(Scanner scanner) {
        Date now = new Date();
        boolean foundReservations = false;
        System.out.println("Reservations that can be updated(i.e. not yet started): \n");
        for (Reservation reservation : reservations) {
            if (reservation.getStartReserve().after(now)) {
                System.out.println(reservation);
                foundReservations = true;
            }
        }
        if (foundReservations) {
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
                            reservation.setName(name);
                            reservation.setStartReserve(startReserve);
                            reservation.setEndReserve(endReserve);
                            reservation.setOther(other);

                            System.out.println("Reservation updated successfully\n");
                            updated = true;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error updating reservation: " + e.getMessage());
                        }
                        break;
                    }
                }
                if (!updated) {
                    System.out.println("Reservation not updated.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid reservation ID. Please try again.");
            }
        } else {
            System.out.println("No reservations to update\n");
        }
    }

    private static void swapReservation(Scanner scanner) {
        System.out.println("List of available reservations: \n");
        boolean foundReservations = false;
        for (Reservation reservation : reservations) {
            if (reservation.getStartReserve().after(new Date())) {
                System.out.println(reservation);
                foundReservations = true;
            }
        }
        if (foundReservations) {
            System.out.print("Enter a reservation ID to swap:");
            int id1 = Integer.parseInt(scanner.nextLine());

            Reservation reservation1 = getReservationById(id1);

            if (reservation1 == null) {
                System.out.println("Reservation not found");
                return;
            }

            System.out.println("These are the reservations that can be swap with your reservation");

            boolean foundReservations2 = false;
            for (Reservation reservation : reservations) {
                if (reservation1.getStartReserve().equals(reservation.getStartReserve())
                        && reservation1.getEndReserve().equals(reservation.getEndReserve())
                        && reservation.getId() != reservation1.getId()) {
                    System.out.println(reservation);
                    foundReservations2 = true;
                }
            }

            if (foundReservations2) {
                System.out.print("Enter another reservation ID to swap: ");
                int id2 = Integer.parseInt(scanner.nextLine());

                Reservation reservation2 = getReservationById(id2);

                if (reservation2 == null) {
                    System.out.println("Reservation not found");
                    return;
                }

                // Perform the swap
                if (reservation1 != null && reservation2 != null) {
                    try {
                        swapRoom(reservation1, reservation2);
                        swapName(reservation1, reservation2);
                        System.out.println("Swapped reservations successfully\n");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error swapping reservations: " + e.getMessage());
                    }
                } else {
                    System.out.println("Cannot swap reservations\n");
                }
            } else {
                System.out.println("No reservations available to swap\n");
            }

        } else {
            System.out.println("No reservations available to swap\n");
        }
    }

    private static void swapRoom(Reservation reservation1, Reservation reservation2) {
        if (reservation1.getStartReserve().equals(reservation2.getStartReserve())
                && reservation1.getEndReserve().equals(reservation2.getEndReserve())) {
            String tempRoomNumber = reservation1.getRoomNumber();
            reservation1.setRoomNumber(reservation2.getRoomNumber());
            reservation2.setRoomNumber(tempRoomNumber);
        } else {
            throw new IllegalArgumentException(
                    "Reservations must have the same start and end date/time to swap rooms.");
        }
    }

    private static void swapName(Reservation reservation1, Reservation reservation2) {
        String tempName = reservation1.getName();
        reservation1.setName(reservation2.getName());
        reservation2.setName(tempName);
    }

    private static Reservation getReservationById(int id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId() == id) {
                return reservation;
            }
        }
        // Return null if no reservation with with specified ID is found
        return null;
    }

}
