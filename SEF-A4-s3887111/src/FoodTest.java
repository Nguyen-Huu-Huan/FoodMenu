import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    @BeforeEach
    void resetFile() {
        try {
            Files.deleteIfExists(Paths.get("food-database.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Nested
    class AddFoodTest{

        @Test
        void testAddFood_testCase_1 () {
            // Test case 1 (valid parameters)
            Food testAddFood_case1_1 = new Food("1", "Pho with Beef and Brisket", "V i e t n a m e s e f o o d", 50, 600, "Healthy Food");
            Food testAddFood_case1_2 = new Food("2", "Chocolate Egg", "A t y p e o f c a n d y", 50, 600, "Kid Food");
            assertAll("Test cases 1 (valid)", () -> assertTrue(testAddFood_case1_1.AddFood()), () -> assertTrue(testAddFood_case1_2.AddFood()));
        }

        @Test
        void testAddFood_testCase_2 () {
            // Test case 2 (invalid due to adding existing food (same food id))
            Food testAddFood_case2_1 = new Food("1", "Pho with Beef and Brisket", "V i e t n a m e s e f o o d", 50, 600, "Healthy Food");
            Food testAddFood_case2_2 = new Food("1", "Chocolate Egg", "A t y p e o f c a n d y", 50, 600, "Kid Food");
            assertAll("Test cases 2 (invalid due to adding existing food (same food id))", () -> assertTrue(testAddFood_case2_1.AddFood()), () -> assertFalse(testAddFood_case2_2.AddFood()));
        }

        @Test
        void testAddFood_testCase_3 () {
            // Test case 3-1 (invalid food name with less than 5 characters)
            Food testAddFood_case3_1 = new Food("1", "dj10", "fewfe", 50, 300, "Healthy Food");
            // Test case 3-2 (invalid food name with more than 30 characters)
            Food testAddFood_case3_2 = new Food("2", "DO74n4VyZStXs0SskEULNikW7R20IBY0IIA", "fewfe", 50, 300, "Healthy Food");

            assertAll("Test cases 3 (invalid food name)", () -> assertFalse(testAddFood_case3_1.AddFood()), () -> assertFalse(testAddFood_case3_2.AddFood()));
        }

        @Test
        void testAddFood_testCase_4 () {
            // Test case 4-1 (invalid food description with less than 5 words)
            Food testAddFood_case4_1 = new Food("1", "fjiwaof", "f e w f", 50, 300, "Healthy Food");
            // Test case 4-2 (invalid food description with more than 50 words)
            Food testAddFood_case4_2 = new Food("2", "fjiwaof", "m X U t U I 5 W k n s X D j S t 5 D e S z 9 0 p X Y 3 V D T h 2 U n z b D 3 A Y W 0 2 a w n G d 8 t X c I n D", 50, 300, "Healthy Food");
            assertAll("Test cases 4 (invalid food description)", () -> assertFalse(testAddFood_case4_1.AddFood()), () -> assertFalse(testAddFood_case4_2.AddFood()));
        }

        @Test
        void testAddFood_testCase_5 () {
            // Test case 5-1 (invalid food type)
            Food testAddFood_case5_1 = new Food("2", "fjiwaof", "fewffwfw", 50, 300, "hello world");
            assertAll("Test cases 5 (invalid food type)", () -> assertFalse(testAddFood_case5_1.AddFood()));
        }

        @Test
        void testAddFood_testCase_6 () {
            // Test case 6-1 (invalid food calorie over 1500)
            Food testAddFood_case6_1 = new Food("1", "fjiwaof", "fewfe", 50, 9000, "Adult Food");
            // Test case 6-2 (invalid food calorie over 800 for Kid Food)
            Food testAddFood_case6_2 = new Food("2", "fjiwaof", "fewfe", 50, 900, "Kid Food");
            assertAll("Test cases 6 (invalid food calorie)", () -> assertFalse(testAddFood_case6_1.AddFood()), () -> assertFalse(testAddFood_case6_2.AddFood()));
        }

        @Test
        void testAddFood_testCase_7 () {
            // Test case 7-1 (invalid food price greater than $150)
            Food testAddFood_case7_1 = new Food("1", "fjiwaof", "fewfe", 500, 900, "Adult Food");
            // Test case 7-2 (invalid food price less than $5)
            Food testAddFood_case7_2 = new Food("2", "fjiwaof", "fewfe", 1, 900, "Adult Food");
            assertAll("Test cases 7 (invalid food price with calorie less than 1000)", () -> assertFalse(testAddFood_case7_1.AddFood()), () -> assertFalse(testAddFood_case7_2.AddFood()));
        }
        @Test
        void testAddFood_testCase_8 () {
            // Test case 8-1 (invalid food price not less than $100 for over 1000 calorie-food)
            Food testAddFood_case8_1 = new Food("2", "fjiwaof", "fewfe", 500, 1200, "Adult Food");
            assertAll("Test cases 8 (invalid food price for food over 1000 calories)", () -> assertFalse(testAddFood_case8_1.AddFood()));
        }
    }

    /*-----------------------------------------------------------------------------------------------*/
    /*-------------------------------------UPDATE FOOD-----------------------------------------------*/
    /*-----------------------------------------------------------------------------------------------*/

    @Nested
    class testUpdateFood{

        Food defaultFoodForUpdate_1 = new Food("1", "Pho with Beef and Brisket", "V i e t n a m e s e f o o d", 50, 600, "Healthy Food");
        Food defaultFoodForUpdate_2 = new Food("2", "Chocolate Egg", "A t y p e o f c a n d y", 50, 600, "Kid Food");

        @Test
        void testUpdateFood_testCase_1 () {
            defaultFoodForUpdate_1.AddFood();
            defaultFoodForUpdate_2.AddFood();
            // Valid test case
            assertAll("Test case 1 (valid test case)", () -> {
                // Valid test case
                assertTrue(defaultFoodForUpdate_1.UpdateFood("Special Pho", "V i e t n a m e s e f o o d", 50, 600, "Healthy Food"));
            }, () -> {
                // Valid test case (change "Kid Food" to "Adult Food)
                assertTrue(defaultFoodForUpdate_2.UpdateFood("Chocolate Egg", "A t y p e o f c a n d y", 50, 600, "Adult Food"));
            });
        }

        @Test
        void testUpdateFood_testCase_2 () {
            defaultFoodForUpdate_1.AddFood();
            assertAll("Test case 2 (food price is increased more than 10%)", () -> assertFalse(
                    defaultFoodForUpdate_1.UpdateFood("Special Pho", "V i e t n a m e s e f o o d", 56, 600, "Healthy Food")
            ));
        }

        @Test
        void testUpdateFood_testCase_3 () {
            defaultFoodForUpdate_1.AddFood();
            // Invalid test case
            assertAll("Test case 3 (food calorie is changed)", () -> {
                assertFalse(defaultFoodForUpdate_1.UpdateFood("Special Pho", "V i e t n a m e s e f o o d", 50, 900, "Healthy Food"));
            });
        }

        @Test
        void testUpdateFood_testCase_4 () {
            defaultFoodForUpdate_1.AddFood();
            // Invalid test case
            assertAll("Test case 4 (food type is changed to 'Kid Food')", () -> {
                // Invalid test case (food type is changed to "Kid Food")
                assertFalse(defaultFoodForUpdate_1.UpdateFood("Special Pho", "V i e t n a m e s e f o o d", 50, 600, "Kid Food"));
            });
        }

        @Test
        void testUpdateFood_testCase_5 () {
            // Invalid test case
            assertAll("Test case 5 (cannot update food when there is no existing record or the file does not exists)", () -> {
                // Invalid test case (cannot update food when there is no record or the file does not exists)
                assertFalse(defaultFoodForUpdate_1.UpdateFood("Special Pho", "V i e t n a m e s e f o o d", 50, 600, "Kid Food"));
            });
        }

        @Test
        void testUpdateFood_testCase_6 () {
            // Invalid test case
            defaultFoodForUpdate_2.AddFood();
            assertAll("Test case 6 (cannot update food when there is no record with matching id)", () -> {
                // Invalid test case (cannot update food when there is no record or the file does not exists)
                assertFalse(defaultFoodForUpdate_1.UpdateFood("Special Pho", "V i e t n a m e s e f o o d", 50, 600, "Kid Food"));
            });
        }
    }
}