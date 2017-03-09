package android.trizleo.mlawmina.models;

import java.util.List;

/**
 * Created by hebi525 on 05-Mar-17.
 */

public class Case {
    private String name;
    private int number;
    private String type;
    private String description;
    private String timeOfCreation;
    private int userOfCreation;
    private String timeOfUpdate;
    private String dateOfOpening;
    private int practiceArea;
    private List<Object> lawyerList;
    private String plaintiff;
    private String defendant;
    private int client;
    private String limitations;

    public Case(String name, int number, String type, String description, String plaintiff, String defendant, String limitations, String dateOfOpening, int client, List<Object> lawyerList) {
        this.name = name;
        this.number = number;
        this.type = type;
        this.description = description;
        this.plaintiff = plaintiff;
        this.defendant = defendant;
        this.limitations = limitations;
        this.dateOfOpening = dateOfOpening;
        this.client = client;
        this.lawyerList = lawyerList;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
