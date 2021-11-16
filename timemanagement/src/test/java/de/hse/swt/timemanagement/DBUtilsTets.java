package de.hse.swt.timemanagement;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class DBUtilsTets {

    @Test
    public void logInUserTest() throws IOException {
        DBUtils.logInUser("chmeit02@hs-esslingen.de", "Sommer");
        assertEquals(1, DBUtils.usr.getID());
        assertEquals("Christoph", DBUtils.usr.getFirstName());
        assertEquals("Merck", DBUtils.usr.getLastName());
        assertEquals("chmeit02@hs-esslingen.de", DBUtils.usr.getEMail());
        assertEquals("employee", DBUtils.usr.getHierarchy());
        assertEquals(4, DBUtils.usr.getGroupID());
        assertEquals(30, DBUtils.usr.getVacDays());
    }

}
