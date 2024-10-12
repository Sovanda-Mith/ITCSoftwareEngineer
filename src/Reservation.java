import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

public class Reservation {
    private int id;
    private String roomNumber;
    private String name;
    private Date startReserve;
    private Date endReserve;
    private String other;

    public Reservation() {
    }

    public Reservation(int id, String roomNumber, String name, Date startReserve, Date endReserve,
            String other) {
        setId(id);
        setRoomNumber(roomNumber);
        setName(name);
        setStartReserve(startReserve);
        setEndReserve(endReserve);
        setOther(other);
    }

    public Reservation(int id) {
        setId(id);
    }

    public Reservation(int id, String roomNumber, String name, Date startReserve, Date endReserve) {
        setId(id);
        setRoomNumber(roomNumber);
        setName(name);
        setStartReserve(startReserve);
        setEndReserve(endReserve);
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        if (roomNumber == null || roomNumber.isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be empty");
        } else {
            Pattern pattern = Pattern.compile("[A-Z]-\\d{3}");
            Matcher matcher = pattern.matcher(roomNumber);
            boolean valid = matcher.matches();
            if (valid) {
                this.roomNumber = roomNumber;
            } else {
                throw new IllegalArgumentException("Room number should be in the format A-123, F-456");
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        } else {
            Pattern pattern = Pattern.compile("[aeiou]+[bcdfghjklmnpqrstvwxyz]+|[bcdfghjklmnpqrstvwxyz]+[aeiou]+",
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(name);
            boolean valid = matcher.find();
            if (valid) {
                this.name = name;
            } else {
                throw new IllegalArgumentException("Please enter valid name");
            }
        }
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartReserve() {
        return startReserve;
    }

    public void setStartReserve(Date startReserve) {
        Date now = new Date();
        if (startReserve.before(now)) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        } else {
            this.startReserve = startReserve;
        }
    }

    public Date getEndReserve() {
        return endReserve;
    }

    public void setEndReserve(Date endReserve) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startReserve);
        cal.add(Calendar.HOUR, 1);
        Date startReserveDate = cal.getTime();
        if (endReserve.before(startReserveDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        } else {
            this.endReserve = endReserve;
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return id + " Reservation for " + "room number=" + roomNumber + ", name=" + name + ", check in="
                + formatter.format(startReserve) + ", check out=" + formatter.format(endReserve) + ", other=" + other;
    }

}
