package android.trizleo.mlawmina;

import android.trizleo.mlawmina.models.Case;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hebi525 on 08-Mar-17.
 */

public class UserSession implements Serializable {

    private List<Case> userCaseList = new ArrayList<>();

    public List<Case> getUserCaseList() {
        return userCaseList;
    }

    public UserSession() {
        //TODO: this is a temporary implementation until server and database are available
        for (int i = 0; i < 20; i++){
            userCaseList.add(new Case("CASE "+i, 0001, "PRACTICE AREA 0", "testDescription", "plaintiff", "defendant", "03/02/1995", "03/02/1995", 1, null));
        }
        //TODO: ...
    }
}
