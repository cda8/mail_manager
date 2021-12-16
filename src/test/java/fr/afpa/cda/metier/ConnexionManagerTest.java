package fr.afpa.cda.metier;

import fr.afpa.cda.exception.InvalidMDPException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConnexionManagerTest {

    ConnexionManager connexionManager;

    @Before
    public void setUp() throws Exception {
      connexionManager = new ConnexionManager();
    }

    @Test
    public void validationMDPCorrect() throws InvalidMDPException {
        assertTrue(connexionManager.validationMDP("12345678"));
        assertFalse(connexionManager.validationMDP("123456"));
    }



}