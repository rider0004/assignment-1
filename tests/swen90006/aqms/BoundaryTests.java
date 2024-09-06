package swen90006.aqms;

import java.util.List;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileSystems;

import org.junit.*;
import static org.junit.Assert.*;

//By extending PartitioningTests, we inherit the tests from that class
public class BoundaryTests extends PartitioningTests
{
    //Add another test
    @Test public void anotherTest()
    {
        //include a message for better feedback
        final int expected = 2;
        final int actual = 2;
        assertEquals("Some failure message", expected, actual);
    }

    @Override
    public void validRegister() throws Throwable {
        aqms.register("User", "Password1!", "1234");
        assertTrue(aqms.isUser("User"));
    }

    @Test(expected = InvalidUsernameException.class)
    public void invalidUsernameInRegister() throws Throwable {
        aqms.register("Usn", "Password1!", "1234");
    }

    @Test
    public void validRegisterPasswordAndDeviceID() throws Throwable {
        aqms.register("UserName", "Pass123!", "1234");
        assertTrue(aqms.isUser("User"));
    }

    @Test(expected = InvalidPasswordException.class)
    public void invalidPasswordInRegister() throws Throwable {
        aqms.register("UserName", "Pass12!", "1234");
    }

    @Test(expected = InvalidDeviceIDException.class)
    public void invalidRegisterDeviceIDShorter() throws Throwable{
        aqms.register("UserName", "Pass123!", "123");
    }

    @Test(expected = InvalidDeviceIDException.class)
    public void invalidRegisterDeviceIDLonger() throws Throwable {
        aqms.register("User", "Pass123!", "12345");
    }

    @Override
    public void upgradeRole() throws Throwable {
        super.upgradeRole();
    }

    @Override
    public void assignSameRoleUser() throws Throwable {
        super.assignSameRoleUser();
    }

    @Test
    public void validGetDataIndex0() throws Throwable {
        aqms.register("NewUserName", "Password1!", "4321");
        aqms.assignRole("NewUserName", AQMS.Role.ADMIN);
        aqms.login("NewUserName", "Password1!", "4321");
        List<Integer> data = new ArrayList<>();
        data.add(0);
        aqms.addData("NewUserName", data);
        aqms.getData("NewUserName", 0);
    }

    @Test
    public void validGetDataIndexSizeMinus1() throws Throwable {
        aqms.register("NewUserName", "Password1!", "4321");
        aqms.assignRole("NewUserName", AQMS.Role.ADMIN);
        aqms.login("NewUserName", "Password1!", "4321");
        List<Integer> data = new ArrayList<>();
        data.add(0);
        data.add(1);
        aqms.addData("NewUserName", data);
        aqms.getData("NewUserName", 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void validGetDataIndexMinus1() throws Throwable {
        aqms.register("NewUserName", "Password1!", "4321");
        aqms.assignRole("NewUserName", AQMS.Role.ADMIN);
        aqms.getData("NewUserName", -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void validGetDataIndexSize() throws Throwable {
        aqms.register("NewUserName", "Password1!", "4321");
        aqms.assignRole("NewUserName", AQMS.Role.ADMIN);
        aqms.login("NewUserName", "Password1!", "4321");
        List<Integer> data = new ArrayList<>();
        data.add(0);
        data.add(1);
        aqms.addData("NewUserName", data);
        aqms.getData("NewUserName", 2);
    }
}
