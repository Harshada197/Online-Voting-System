package votingOnline;

import java.util.*;

public class RegisterVoter {
    //voter datatype
    public static class Voter {
        public String voterId;
        public String password;
        public String name;
        public int age;
        public String gender;
        public String contactNumber;
        public String district;
        public String state;
        public boolean aadhaarPresent;

        public Voter(String voterId, String password, String name, int age, String gender, String contactNumber, String district, String state, boolean aadhaarPresent) {
            this.voterId = voterId;
            this.password = password;
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.contactNumber = contactNumber;
            this.district = district;
            this.state = state;
            this.aadhaarPresent = aadhaarPresent;
        }
    }

   //Hash-map to store all unique voter details
    public static HashMap<String, Voter> voters = new HashMap<>();

    //Initially registered voters
    public static void loadInitialData() {
        voters.put("V101", new Voter("V101", "pass1Aa", "Riya Sharma", 25, "Female", "9876543210", "Mumbai", "Maharashtra", true));
        voters.put("V102", new Voter("V102", "pass2Aa", "Arjun Kumar", 32, "Male", "9812345678", "New Delhi", "Delhi", true));
        voters.put("V103", new Voter("V103", "pass3Aa", "Saanvi Patel", 19, "Female", "9001122334", "Bengaluru", "Karnataka", true));
        voters.put("V104", new Voter("V104", "pass4Aa", "Rahul Singh", 29, "Male", "9123456780", "Lucknow", "Uttar Pradesh", true));
        voters.put("V105", new Voter("V105", "pass5Aa", "Ananya Roy", 22, "Female", "9988776655", "Kolkata", "West Bengal", true));
        voters.put("V106", new Voter("V106", "pass6Aa", "Karan Mehta", 31, "Male", "9090909090", "Ahmedabad", "Gujarat", true));
        voters.put("V107", new Voter("V107", "pass7Aa", "Sneha Iyer", 27, "Female", "9191919191", "Chennai", "Tamil Nadu", true));
        voters.put("V108", new Voter("V108", "pass8Aa", "Vikram Singh", 35, "Male", "9222223333", "Pune", "Maharashtra", true));
    }

    // Method for registering new voter (retries on duplicate voterId)
    public static void registerNewVoter(Scanner sc) {
        System.out.println("\n=== NEW VOTER REGISTRATION ===");

        //Accepting Voter Id
        String voterId;
        while (true) {
            //Assuming Voter Id to be 4 digit alphanumeric
            System.out.print("Enter Your Voter ID (e.g., V104):");
            voterId = sc.nextLine().trim();
            if (voterId.isEmpty()) {
                System.out.println("Voter ID cannot be empty. Try again.");
                continue;
            }
            if (voters.containsKey(voterId)) {
                System.out.println("This voter ID already exists! Please choose a different ID.");
                continue;
            }
            break;
        }

        //Accepting Voter Name
        String name;
        while (true) {
            System.out.print("Enter Full Name: ");
            name = sc.nextLine().trim();
            if (name.isEmpty() || !name.matches("[a-zA-Z\\s]+")) {
                System.out.println("Please enter a valid name (letters and spaces only).");
            } else {
                break;
            }
        }

        // Accept Age
        int age;
        while (true) {
            System.out.print("Enter Age: ");
            String ageInput = sc.nextLine().trim();
            try {
                age = Integer.parseInt(ageInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number for age.");
            }
        }

        // Check if voter is eligible or not
        if (age < 18) {
            System.out.println("You must be at least 18 years old to register!");
            return;
        }

        // --- Additional registration details ---
        String gender;
        while(true){
            System.out.print("Enter Gender (Male/Female/Other): ");
            gender = sc.nextLine().trim();
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("Other")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter Male, Female, or Other.");
            }
        }
        //Accepting Contact Number
        String contactNumber;
        while(true){
            System.out.print("Enter Contact Number: ");
            contactNumber = sc.nextLine().trim();
            if (contactNumber.matches("\\d{10}")) {
                break;
            } else {
                System.out.println("Please enter a valid 10-digit contact number.");
            }
        }
        //Accepting input for district
        String district;
        while(true){
            System.out.print("Enter District: ");
            district = sc.nextLine().trim();
            if (district.isEmpty()) {
                System.out.println("District cannot be empty. Try again.");
            } else {
                break;
            }
        }

        //Accepting input for state
        String state;
        while(true){
            System.out.print("Enter State: ");
            state = sc.nextLine().trim();
            if (state.isEmpty()) {
                System.out.println("State cannot be empty. Try again.");
            } else {
                break;
            }
        }

        // Check whether Voter has Aadhaar card
        boolean hasAadhaar;
        while(true) {
            System.out.print("Do you have an Aadhaar card? (y/n): ");
            String input = sc.nextLine().trim().toLowerCase();
            
            if (input.equals("y") || input.equals("yes")) {
                hasAadhaar = true;
                break;
            } else if (input.equals("n") || input.equals("no")) {
                System.out.println("You must have an Aadhaar card to register online.");
                return;
            } else {
                System.out.println("Invalid input. Please enter 'y' for Yes or 'n' for No.");
            }
        }
       

       //Enter Password
       String password;
       System.out.println("Set your account password:");
       System.out.println("\nNOTE: Password must use 6+ characters, with a capital letter, number, and symbol—no spaces.\n");
        while (true) {
            System.out.print("Enter your password: ");
            password = sc.nextLine();

            if (isValidPassword(password)) {
                System.out.println("Password is valid!");
                break;
            } else {
                System.out.println(" Invalid password. Try again.");
                System.out.println("Rules: 8+ characters, 1 uppercase, 1 number, 1 symbol, no spaces.\n");
            }
        }

        // Create new Voter object and add it to HashMap
        Voter newVoter = new Voter(voterId, password, name, age, gender, contactNumber, district, state,hasAadhaar);
        voters.put(voterId, newVoter);

        System.out.println("\nRegistration successful for " + name + "! Your Voter ID: " + voterId);

    }

   //Method for login process
    public static boolean loginVoter(Scanner sc) {
        System.out.println("\n=== VOTER LOGIN ===");

        String voterId;
        while (true) {
            //Assuming Voter Id to be 4 digit alphanumeric
            System.out.print("Enter Your Voter ID (e.g., V104):");
            voterId = sc.nextLine().trim();
            if (voterId.isEmpty()) {
                System.out.println("Voter ID cannot be empty. Try again.");
            }else{
               if (!voters.containsKey(voterId)) {
                 System.out.println("No voter found with this ID. Please register first.");
                 return false;
               } 
               break;
            }
            
        }

        //Creating Voter object for the entered voterId
        Voter v = voters.get(voterId);
        String password;
        while (true) {
            System.out.print("Enter your password: ");
            password = sc.nextLine();

            if (isValidPassword(password) && password.equals(v.password)) {
                break; // Successful login
            } else {
                System.out.println(" Invalid password. Try again.");
                System.out.println("Rules: 8+ characters, 1 uppercase, 1 number, 1 symbol, no spaces.\n");
            }
        }
        System.out.println("Login successful !!! Welcome, " + v.name + "!");
        return true;
    }

    //Password validation method
     public static boolean isValidPassword(String password) {
        return password.length() >= 6 &&
               password.matches(".*[A-Z].*") &&  // Capital Letter
               password.matches(".*\\d.*") &&	// 1-10 Number
               !password.contains(" ");	//Spaces
    }

     int l=1;
   
    //After login operation for voter
    public static void afterLoginVoter(Voter v) {
        Scanner sc = new Scanner (System.in);
        System.out.println("\n=== VOTER MENU ===");
        System.out.println("1. View Profile");
        System.out.println("2. Update Profile");
        System.out.println("3. Vote");
        System.out.println("4. Logout");

        int choice;
        while(true){
            System.out.print("Enter your choice: ");
            if(sc.hasNextInt()){
                choice = sc.nextInt();
                if(choice >= 1 && choice <= 4) {
                    break;
                }else{
                    System.out.println("Enter Valid Choice");
                }
            }else{
                System.out.println("Invalid Input .. Try Again..");
                sc.next(); // Clear invalid input
            }
        }

        switch(choice){
            case 1->{// Show up his profile details
                System.out.println("=== PROFILE DETAILS ===");
                System.out.println("Name: " + v.name);
                System.out.println("Age: " + v.age);
                System.out.println("Gender: " + v.gender);
                System.out.println("Contact Number: " + v.contactNumber);
                System.out.println("District: " + v.district);
                System.out.println("State: " + v.state);
                System.out.println("Aadhaar Linked: " + (v.aadhaarPresent ? "Yes" : "No"));
            }
            case 2->{//update his/her profile details
                System.out.println("\n=== UPDATE PROFILE ===");
                System.out.println("1. Update Contact Number");
                System.out.println("2. Update District");
                System.out.println("3. Update State");
                System.out.println("4. Back to Main Menu");

                int updateChoice;
                while(true) {
                    System.out.print("Enter your choice: ");
                    if(sc.hasNextInt()) {
                        updateChoice = sc.nextInt();
                        sc.nextLine(); // Clear buffer
                        if(updateChoice >= 1 && updateChoice <= 4) {
                            break;
                        } else {
                            System.out.println("Please enter a valid choice (1-4)");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a number.");
                        sc.next(); // Clear invalid input
                    }
                }

                switch(updateChoice) {
                    case 1 -> {
                        while(true) {
                            System.out.print("Enter new Contact Number: ");
                            String newContact = sc.nextLine().trim();
                            if(newContact.matches("\\d{10}")) {
                                v.contactNumber = newContact;
                                System.out.println("Contact number updated successfully!");
                                break;
                            } else {
                                System.out.println("Please enter a valid 10-digit contact number.");
                            }
                        }
                    }
                    case 2 -> {
                        System.out.print("Enter new District: ");
                        String newDistrict = sc.nextLine().trim();
                        if(!newDistrict.isEmpty()) {
                            v.district = newDistrict;
                            System.out.println("District updated successfully!");
                        }
                    }
                    case 3 -> {
                        System.out.print("Enter new State: ");
                        String newState = sc.nextLine().trim();
                        if(!newState.isEmpty()) {
                            v.state = newState;
                            System.out.println("State updated successfully!");
                        }
                    }
                    case 4 -> System.out.println("Returning to main menu...");
                }
            }
            case 3->{ //voting process
                System.out.println("=== VOTING ===");
                if(Admin.start_Vote && !Admin.end_Vote){
                    System.out.println("You can now cast your vote.");
                    // Display available candidates
                    if(Admin.candidates.isEmpty()) {
                        System.out.println("No candidates available to vote for.");
                        return;
                    }
                    
                    System.out.println("\nAvailable Candidates:");
                    for(Admin.Candidate c : Admin.candidates.values()) {
                        System.out.printf("ID: %s | Name: %s | Party: %s%n", 
                            c.candidateId, c.Name, c.partyName);
                    }

                    // Get voter's choice
                    System.out.print("\nEnter the Candidate ID you want to vote for: ");
                    sc.nextLine(); // Clear buffer
                    String candidateId = sc.nextLine().trim();

                    if(Admin.candidates.containsKey(candidateId)) {
                        Admin.Candidate selectedCandidate = Admin.candidates.get(candidateId);
                        selectedCandidate.Votes++;
                        
                        // Log the voting record with timestamp
                        String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(new java.util.Date());
                        String logEntry = String.format("Voter ID: %s | Name: %s | District: %s | State: %s | Time: %s",
                            v.voterId, v.name, v.district, v.state, timestamp);
                        Admin.auditLog.add(logEntry);
                        
                        System.out.println("Your vote has been cast successfully!");
                    } else {
                        System.out.println("Invalid Candidate ID. Vote not cast.");
                    }
                } else if(Admin.end_Vote) {
                    System.out.println("Voting has ended. You can no longer cast a vote.");
                } else {
                    System.out.println("Voting has not started yet. Please wait for the admin to start the voting process.");
                }
            }
            case 4->{ //Logout
                System.out.println("Logging out...");
                System.out.println("Thank you for using the Online Voting System!");
            }
        }
     
    }
}

