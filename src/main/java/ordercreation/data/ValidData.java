package ordercreation.data;
import java.util.List;

public class ValidData {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public ValidData(String firstName, String lastName, String address, int metroStation,
                     String phone,
                     int rentTime, String deliveryDate, String comment, List <String> color){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public ValidData(){
    }
}