package fr.afpa.cda.metier;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ValidationMdpTest {
  ConnexionManager connexionManager;

  @Before
  public void setUp() throws Exception {
    connexionManager = new ConnexionManager();
  }

  @Test
  public void testValidationMdp() {
    assertTrue(connexionManager.validationMdp("azertyuiop"));
    assertFalse(connexionManager.validationMdp("azerty"));

  }

}
