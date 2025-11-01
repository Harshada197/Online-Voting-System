package votingOnline;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Load initial voter data
        RegisterVoter.loadInitialData();
        while (true) {
            System.out.println("\n============================");
            System.out.println(" || 1. Admin Login        ||");
            System.out.println(" || 2. Voter Registration ||");
            System.out.println(" || 3. Voter Login        ||");
            System.out.println(" || 4. View Results       ||");
            System.out.println(" || 5. Exit               ||");
            System.out.println("=============================");
            
            int choice; 
            while(true) {
                System.out.print("Enter your choice: ");
                if(sc.hasNextInt()) {
                    choice = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    if(choice >= 1 && choice <= 5) {
                        break;
                    } else {
                        System.out.println("Enter Valid Choice (1-5)");
                    }
                } else {
                    System.out.println("Invalid Input .. Try Again..");
                    sc.next(); // Clear invalid input
                }
            }

            switch(choice) {
                case 1 -> { // Admin Login Logic
                    if(Admin.adminLogin()) {
                        Admin.main(args);
                    }
                }
                case 2 -> { // Voter Registration Logic
                        RegisterVoter.registerNewVoter(sc);
                   
                }
                case 3 -> { // Voter Login Logic
                    if(RegisterVoter.loginVoter(sc)) {
                        // Get the current voter
                        System.out.print("Enter your Voter ID again to continue: ");
                        String voterId = sc.nextLine().trim();
                        RegisterVoter.Voter currentVoter = RegisterVoter.voters.get(voterId);
                        if(currentVoter != null) {
                            RegisterVoter.afterLoginVoter(currentVoter);
                        }
                    }
                }
                case 4 -> { // View Results Logic
                    if(Admin.end_Vote) {
                        System.out.println("\n=== ELECTION RESULTS ===");
                        if(Admin.candidates.isEmpty()) {
                            System.out.println("No candidates available.");
                        } else {
                            for(Admin.Candidate c : Admin.candidates.values()) {
                                System.out.printf("Candidate: %s, Party: %s, Votes: %d%n", 
                                    c.Name, c.partyName, c.Votes);
                            }
                        }
                    } else {
                        System.out.println("Results are not available until voting ends.");
                    }
                }
                case 5 -> { // Exit
                    System.out.println("Thank you for using the Online Voting System!");
                    sc.close();
                    System.exit(0);
                }
            }
        }
    }
}
