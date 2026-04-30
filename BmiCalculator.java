import java.util.Scanner;
import java.util.Locale;
import java.text.DecimalFormat;

public class BmiCalculator {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
        DecimalFormat df = new DecimalFormat("0.00");
		boolean active = true;
		
		
        System.out.println("===================================");
        System.out.println("      ||  MYFIT BMI SYSTEM ||      ");
        System.out.println(" Stay disciplined. Stay dangerous. ");
        System.out.println("===================================\n");


        while(active){
            System.out.println("Main Menu:");
            System.out.println("[1] Calculate BMI");
            System.out.println("[2] Exit");
            System.out.print("Choose an option: ");

            if(scanner.hasNextInt()){
                int mainChoice = scanner.nextInt();
                switch(mainChoice){
                    case 1:
					System.out.println("What's your name: ");
					String name = scanner.nextLine();
					scanner.nextLine();
					
					int age = (int) getValidInput(scanner, "Enter your age: ", 1, 100);
					scanner.nextLine();
					System.out.println("Enter your ethnicity: ");
					String ethnicity = scanner.nextLine();
                        activeBmiCalculator(scanner, df, name, age, ethnicity);
                          break;
                    case 2:
                        active = false;
                        System.out.println("\nExiting AlphaFit... Stay sharp");
                          break;
                    default:
                        System.out.println("\nInvalid option. Try again.\n");
                }
            }else{
                System.out.println("Invalid input. Please enter 1 or 2.");
                scanner.next(); 
            }
        }
        scanner.close();
    }
	
    private static void activeBmiCalculator(Scanner scanner, DecimalFormat df, String name, int age, String ethnicity){
        int unitChoice = getUnitChoice(scanner);
        double weight, heightTotal;
        if(unitChoice == 1){
            weight = getValidInput(scanner, "Enter weight in Kilograms (kg): ", 10, 600);
            heightTotal = getValidInput(scanner, "Enter height in Meters (m): ", 0.5, 2.5);
        }else{
            weight = getValidInput(scanner, "Enter weight in Pounds (lbs): ", 22, 1300);
            double feet = getValidInput(scanner, "Enter height (Feet): ", 1, 8);
            double inches = getValidInput(scanner, "Enter height (Inches): ", 0, 11);
            heightTotal = (feet * 12) + inches;
        }
		
		double bmi = calculateBMI(unitChoice, weight, heightTotal);
		String categoryLabel = getCategoryOnly(bmi);
        String categoryDisplay = getCategoryWithEmoji(bmi); 
		
		
        System.out.println("\n------- PATIENT HEALTH REPORT -------");
        System.out.println("Name           : " + name);
        System.out.println("Age            : " + age);
        System.out.println("Ethnicity      : " + ethnicity);
        System.out.printf("BMI Score      : %.2f\n", bmi);
        System.out.println("Category : " + categoryDisplay);
		System.out.println("-------------------------------------");
		displayFeedback(categoryLabel);
        displayBmiBar(bmi);
        displayHealthyWeightRange(unitChoice, heightTotal);
    }

    public static int getUnitChoice(Scanner scanner){
        while(true){
            System.out.println("\nSelect Measurement System:");
            System.out.println("[1] Metric (kg/m)");
            System.out.println("[2] Imperial (lb/ft/in)");
            if(scanner.hasNextInt()){
                int choice = scanner.nextInt();
                if(choice == 1 || choice == 2) return choice;
            }else{
                scanner.next();
            } 
			System.out.println("Invalid choice. Please choose [1] or [2]");
        }
    }

    public static double getValidInput(Scanner scanner, String prompt, double min, double max){
        double value;
        while(true){
            System.out.println(prompt);
            if(scanner.hasNextDouble()){
                value = scanner.nextDouble();
                if(value >= min && value <= max) return value;
                System.out.printf("Please enter a value between %.1f and %.1f.\n", min, max);
            }else{
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    public static double calculateBMI(int unitChoice, double weight, double height) {
        if (unitChoice == 1) {
            return weight / (height * height);
        } else {
            return (weight * 703) / (height * height);
        }
    }
		public static String getCategoryWithEmoji (double totalBMI){
        if(totalBMI < 18.5) return "category: Underweight (︶︹︺) ";          
        if(totalBMI <= 24.9) return "category: Healthy (0◡0) ";
        if(totalBMI <= 29.9) return "category: Overweight (>_<) ";
		if(totalBMI <= 34.9) return "category: Obese Class I (--_--) ";
		if(totalBMI <= 39.9) return "category: Obese Class II (--_--) ";
		return "category: Obese Class III (╥_╥) ";
    }
	
	  public static String getCategoryOnly(double bmi){
        if (bmi < 18.5) return "Underweight";
        if (bmi <= 24.9) return "Healthy";
        if (bmi <= 29.9) return "Overweight";
        if (bmi <= 34.9) return "Obese Class I";
        if (bmi <= 39.9) return "Obese Class II";
        return "Obese Class III";
	  }
	
	public static void displayFeedback(String category){
		System.out.print("Feedback    : ");
		switch(category){
	        case "Underweight": System.out.println("You are underweight. Do better! Eat more protein."); break;
		    case "Healthy":	System.out.println("Good job! You are at a normal weight. Keep training."); break;
		    case "Overweight": System.out.println("You're going overboard. Let's get that cardio up."); break;
			case "Obese Class I": System.out.println("Damn, you're Obese. Come on now, discipline is key!"); break;
			case "Obese Class II": System.out.println("Damn, you're Obese. Come on now, discipline is key!"); break;
			case "Obese Class III":	System.out.println("This is dangerous. Let's fix that immediately!"); break;
		}
	        System.out.println();
	}

	    public static void displayBmiBar(double bmi){
        System.out.print("[");
        int pos = (int) (bmi / 2);
        for (int i = 0; i < 20; i++){
            if (i == pos) System.out.print("|YOU|");
            else if (i < 9) System.out.print("-"); 
            else System.out.print("==="); 
        }
        System.out.println("]");
        System.out.println("18.5       24.9       29.9       34.9       39.9       40+");
    }
	
    public static void displayHealthyWeightRange(int unitChoice, double height){
        double minWeight, maxWeight;
        if (unitChoice == 1){
            minWeight = 18.5 * (height * height);
            maxWeight = 24.9 * (height * height);
            System.out.printf("Recommended weight for your height: %.1fkg - %.1fkg\n", minWeight, maxWeight);
        }else{
            minWeight = (18.5 * (height * height)) / 703;
            maxWeight = (24.9 * (height * height)) / 703;
            System.out.printf("Recommended weight for your height: %.1flbs - %.1flbs\n", minWeight, maxWeight);
        }
    }	
}