package com.example.CSCB07Project;

import com.example.CSCB07Project.DoctorFiles.LoginDoctorActivity;
import com.example.CSCB07Project.DoctorFiles.LoginDoctorModel;
import com.example.CSCB07Project.DoctorFiles.LoginDoctorPresenter;
import com.example.CSCB07Project.PatientFiles.LoginPatientActivity;
import com.example.CSCB07Project.PatientFiles.LoginPatientModel;
import com.example.CSCB07Project.PatientFiles.LoginPatientPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class LoginUnitTest {

    @Mock
    LoginPatientActivity patientView;

    @Mock
    LoginPatientModel patientModel;

    @Mock
    LoginDoctorActivity doctorView;

    @Mock
    LoginDoctorModel doctorModel;


    @Test
    public void testPatientUserIdEmpty() {
        when(patientView.getUserId()).thenReturn("");
        //when(patientView.displayMessage(anyString())).thenCallRealMethod();
        //when(patientModel.usernameNotFound(patientView.getUserId(), Callback c));
        when(patientView.toDashboard()).thenReturn(true);

        LoginPatientPresenter presenter = new LoginPatientPresenter(patientModel, patientView);
        presenter.checkUsernamePassword(data -> patientView.toDashboard());

        verify(patientView).displayMessage("Invalid Username");
    }

    @Test
    public void testPatientUserIdInvalid() {
        //when(patientModel.usernameIsFound(patientView.getUserId())).thenReturn(false);

        LoginPatientPresenter presenter = new LoginPatientPresenter(patientModel, patientView);
        //presenter.checkUsernamePassword();

        verify(patientView).displayMessage("Invalid Username");
    }

    @Test
    public void testPatientExistsWrongPassword() {
        //when(patientModel.usernameIsFound(patientView.getUserId())).thenReturn(true);
        //when(patientModel.passwordIsFound(patientView.getPassword())).thenReturn(false);

        LoginPatientPresenter presenter = new LoginPatientPresenter(patientModel, patientView);
        //presenter.checkUsernamePassword();

        verify(patientView).displayMessage("Wrong Password");
    }

    @Test
    public void testPatientExistsRightPassword() {
        //when(patientModel.usernameIsFound(patientView.getUserId())).thenReturn(true);
        //when(patientModel.passwordIsFound(patientView.getPassword())).thenReturn(true);
        //when(patientModel.usernameMatchPassword(patientView.getUserId(), patientView.getPassword()))
                //.thenReturn(true);

        LoginPatientPresenter presenter = new LoginPatientPresenter(patientModel, patientView);
        //presenter.checkUsernamePassword();

        //assertTrue(presenter.checkUsernamePassword());
    }

    @Test
    public void testDoctorUserIdEmpty() {
        when(doctorView.getUserId()).thenReturn("");
        //when(doctorModel.usernameIsFound(doctorView.getUserId())).thenReturn(false);

        LoginDoctorPresenter presenter = new LoginDoctorPresenter(doctorModel, doctorView);
        //presenter.checkUsernamePassword();

        verify(doctorView).displayMessage("Invalid Username");
    }

    @Test
    public void testDoctorUserIdInvalid() {
        //when(doctorModel.usernameIsFound(doctorView.getUserId())).thenReturn(false);

        LoginDoctorPresenter presenter = new LoginDoctorPresenter(doctorModel, doctorView);
        //presenter.checkUsernamePassword();

        verify(doctorView).displayMessage("Invalid Username");
    }

    @Test
    public void testDoctorExistsWrongPassword() {
        //when(doctorModel.usernameIsFound(doctorView.getUserId())).thenReturn(true);
        //when(doctorModel.passwordIsFound(doctorView.getPassword())).thenReturn(false);

        LoginDoctorPresenter presenter = new LoginDoctorPresenter(doctorModel, doctorView);
        //presenter.checkUsernamePassword();

        verify(doctorView).displayMessage("Wrong Password");
    }

    @Test
    public void testDoctorExistsRightPassword() {
        //when(doctorModel.usernameIsFound(doctorView.getUserId())).thenReturn(true);
        //when(doctorModel.passwordIsFound(doctorView.getPassword())).thenReturn(true);
        //when(doctorModel.usernameMatchPassword(doctorView.getUserId(), doctorView.getPassword()))
                //.thenReturn(true);

        LoginDoctorPresenter presenter = new LoginDoctorPresenter(doctorModel, doctorView);
        //presenter.checkUsernamePassword();

        //assertTrue(presenter.checkUsernamePassword());
    }
}