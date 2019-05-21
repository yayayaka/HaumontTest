import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.models.PatientModel;
import org.junit.Test;

import java.util.List;

public class PatientModelTest {
    @Test
    public void getAllTest() {
        PatientModel patientModel = new PatientModel();
        Patient patient = new Patient(10, "name", "secname",
                "midname", 1234567890);
        boolean boo = patientModel.addOne(patient);
        Patient patient1 = patientModel.getOne(1);
        List<Patient> patients = patientModel.getAll();
    }
}
