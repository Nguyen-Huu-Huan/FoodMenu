import java.io.*;
import java.util.Scanner;

public class Food {

    private String foodID;

    private String foodName;

    private String foodDescription;

    private double foodPrice;

    private int foodCalorie;

    private String foodType;

    public Food (String foodID, String foodName, String foodDescription, double foodPrice, int foodCalorie, String foodType) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.foodDescription = foodDescription;
        this.foodPrice = foodPrice;
        this.foodCalorie = foodCalorie;
        this.foodType = foodType;
    }

    public boolean AddFood () {
        // Add the information of food to a TXT file
        File file = new File("food-database.txt");
        try {
            // Read the file (if existed)
            Scanner scanner = new Scanner(file);
            // Loop through each record in the file, if the foodID is existed, return false
            while (scanner.hasNextLine()) {
                String oldFoodData = scanner.nextLine();
                String[] oldFoodDataArray = oldFoodData.split(",");
                // [0] foodID [1] foodName [2] foodDescription [3] foodPrice [4] foodCalorie [5] foodType;
                // If the food ID already exists, return false
                if (oldFoodDataArray[0].equals(this.getFoodID())) {
                    scanner.close();
                    return false;
                }
            }
            scanner.close();
        } catch (IOException e) {
            // File not found
        }
        try {
            // Validate before adding the food
            this.validateFoodBeforeAdding(this.getFoodName(),this.getFoodDescription(),this.getFoodType(),this.getFoodCalorie(),this.getFoodPrice());
            // Append the new food record to the file
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter out = new PrintWriter(bufferedWriter);
            out.println(this.toString());

            // Close connection and return true
            out.close();
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public boolean UpdateFood (String foodName, String foodDescription, double foodPrice, int foodCalorie, String foodType) {
        try {
            // Read file using scanner
            File file = new File("food-database.txt");
            Scanner scanner = new Scanner(file);
            // Variable to store the new content of file
            StringBuffer fileContent = new StringBuffer();

            // isFound variable is used to check if the food is found in the record
            boolean isFound = false;

            // Loop through each food in the record
            // If the foodID matches, append the new food information to the fileContent,
            // Otherwise, append old records to the fileContent
            while (scanner.hasNextLine()) {
                String oldFoodData = scanner.nextLine();
                String[] oldFoodDataArray = oldFoodData.split(",");
                // [0] foodID [1] foodName [2] foodDescription [3] foodPrice [4] foodCalorie [5] foodType;
                // Check if the food id matches with the food id that we want to update
                if (oldFoodDataArray[0].equals(this.getFoodID())) {
                    // It should not be possible to increase the food price by more than 10%.
                    if (110.00 / 100.00 * Double.parseDouble(oldFoodDataArray[3]) <= foodPrice) {
                        scanner.close();
                        return false;
                    // It should not be possible to change the food calorie.
                    }else if (Integer.parseInt(oldFoodDataArray[4]) != foodCalorie){
                        scanner.close();
                        return false;
                    // It should not be possible to change the type of food to “Kid Food”, but it can be possible to change “Kid Food” to any other type.
                    }else if (foodType.equals("Kid Food") && !foodType.equals(oldFoodDataArray[5])) {
                        scanner.close();
                        return false;
                    }
                    // Validate the food information before updating
                    this.validateFoodBeforeAdding(foodName, foodDescription, foodType, foodCalorie, foodPrice);
                    // Append the new food information to the fileContent and set isFound to true
                    isFound = true;
                    fileContent.append(this.toString()+ System.lineSeparator());
                }else {
                    fileContent.append(oldFoodData + System.lineSeparator());
                }
            }

            //closing the Scanner object
            scanner.close();
            // If the food is not found, return false
            if (isFound == false) {
                return false;
            }

            // Write the new file content to the file
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(fileContent.toString());

            // Flush, close connection, and return true
            printWriter.flush();
            printWriter.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void validateFoodBeforeAdding(String foodName, String foodDescription, String foodType, int foodCalorie, double foodPrice) {
        this.setFoodName(foodName);
        this.setFoodDescription(foodDescription);
        this.setFoodType(foodType);
        this.setFoodCalorie(foodCalorie);
        this.setFoodPrice(foodPrice);
    }

    @Override
    public String toString () {
        // Overide the toString method to return the food information in a string format (for appending to the file)
        return foodID + "," + foodName + "," + foodDescription + "," + foodPrice + "," + foodCalorie + "," + foodType;
    }

    public String getFoodID () {
        return foodID;
    }

    public void setFoodID (String foodID) {
        this.foodID = foodID;
    }

    public String getFoodName () {
        return foodName;
    }

    public void setFoodName (String foodName) {
        // Validate the food name before setting
        // Food name should be between 5 and 30 characters
        if (foodName.length() >= 5 && foodName.length() <= 30) {
            this.foodName = foodName;
        } else {
            throw new IllegalArgumentException("Invalid food name");
        }
    }

    public String getFoodDescription () {
        return foodDescription;
    }

    public void setFoodDescription (String foodDescription) {
        // Validate the food description before setting
        // Food description should be between 5 and 50 words
        if (foodDescription.split("\\s+").length >= 5 && foodDescription.split("\\s+").length <= 50) {
            this.foodDescription = foodDescription;
        } else {
            throw new IllegalArgumentException("Invalid food description");
        }
    }

    public double getFoodPrice () {
        return foodPrice;
    }

    public void setFoodPrice (double foodPrice) {
        // Validate the food price before setting with the following conditions:
        // The food price range should be between $5 and $150.
        // Foods with more than 1000 calories should be less than $100.
        if (this.foodCalorie <= 1000) {
            if (foodPrice >= 5 && foodPrice <= 150) {
                this.foodPrice = foodPrice;
            } else {
                throw new IllegalArgumentException("The price of each food should be between $5 and $150");
            }
        } else {
            if (foodPrice < 100) {
                this.foodPrice = foodPrice;
            } else {
                throw new IllegalArgumentException("The price of foods with more than 1000 calories should be less than $100");
            }
        }
    }

    public int getFoodCalorie () {
        return foodCalorie;
    }

    public void setFoodCalorie (int foodCalorie) {
        // Validate the food calorie before setting with the following conditions:
        // foods should not be more than 1500 calorie
        // If the type of food is “Kid Food”, their calorie should be less than 800.
        if (!this.foodType.equals("Kid Food")) {
            if (foodCalorie <= 1500) {
                this.foodCalorie = foodCalorie;
            } else {
                throw new IllegalArgumentException("Cannot add food with more than 1500 calories");
            }
        } else {
            if (foodCalorie < 800) {
                this.foodCalorie = foodCalorie;
            } else {
                throw new IllegalArgumentException("Cannot add Kid Food with 800 calories and above");
            }
        }
    }

    public String getFoodType () {
        return foodType;
    }

    public void setFoodType (String foodType) {
        // Validate the food type before setting
        // Food type must be "Kid Food", "Adult Food", "Healthy Food", or "Elderly Food"
        if (foodType.equals("Kid Food") || foodType.equals("Adult Food") || foodType.equals("Healthy Food") || foodType.equals("Elderly Food")) {
            this.foodType = foodType;
        } else {
            throw new IllegalArgumentException("Invalid food type");
        }
    }
}