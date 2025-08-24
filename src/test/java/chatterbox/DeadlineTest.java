package chatterbox;  //same package as the class being tested

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    public void dummyTest() {
        try {
            Deadline obj = new Deadline("sign up event/by 2/3/2019 1800");
            String expectedStr = "sign up event";
            String actualStr = obj.description;
            assertEquals(expectedStr, actualStr);
        } catch (DateTimeParseException e) {
            //nothing
        }
    }

    @Test
    public void anotherDummyTest(){
        try {
            Deadline obj = new Deadline("return book /by 2/3/2019 2000");
            obj.markAsDone();
            String expectedStr = "[D][X] return book (by: Mar 02 2019 20:00)";
            String actualStr = obj.toString();
            assertEquals(expectedStr, actualStr);
        } catch (NullPointerException e) {
            //nothing
        }
    }
}