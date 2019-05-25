import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.models.PatientModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PatientModelTest {
    Patient searchTestPatient;
    Patient addOneTestPatient;
    Patient getAllTestPatient;
    PatientModel patientModel;

    @Before
    public void prepare() {
        patientModel = new PatientModel();
        searchTestPatient = new Patient(-1, "name1", "secname3",
                "midname1", 1234567891);
        addOneTestPatient = new Patient(-1, "name2", "secname3",
                "midname2", 1234567892);
        getAllTestPatient = new Patient(-1, "name3", "secname3",
                "midname3", 1234567893);
    }
    @Test
    public void searchByFieldsTest() {
        boolean boo = patientModel.addOne(searchTestPatient);
        Patient takenPatient = patientModel.searchByFields(searchTestPatient);
        Assert.assertEquals(searchTestPatient, takenPatient);
    }

    @Test
    public void addOneTest() {
        boolean hasSuccess = patientModel.addOne(addOneTestPatient);
        Assert.assertTrue(hasSuccess);
    }

    @Test
    public void getAllTest() {
        patientModel.addOne(getAllTestPatient);
        List<Patient> all = patientModel.getAll();
        boolean hasSuccess = false;
        for (Patient one : all) {
            if (one.equals(getAllTestPatient)) {
                hasSuccess = true;
                break;
            }
        }
        Assert.assertTrue(hasSuccess);
    }

    @After
    public void after() {
        long firstId = patientModel.getId(searchTestPatient);
        patientModel.deleteOne(firstId);
        long secondId = patientModel.getId(addOneTestPatient);
        patientModel.deleteOne(secondId);
        long thirdId = patientModel.getId(getAllTestPatient);
        patientModel.deleteOne(thirdId);
    }
}
