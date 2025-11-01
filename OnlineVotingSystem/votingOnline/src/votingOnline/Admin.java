package votingOnline;
import java.util.*;

public class Admin {
    static Scanner sc = new Scanner(System.in);
    static boolean start_Vote;
    static boolean end_Vote;

    //User defined class for Candidate
    public static class Candidate{
    String candidateId;
    String Name;
    String partyName;
    int Votes;
        //Constructor for initializing Candidate info
         public Candidate(String candidateId, String name, String partyName) {
            this.candidateId = candidateId;
            this.Name = name;
            this.partyName = partyName;
            this.Votes = 0;
         }
    }
    
    //Map to store candidates with candidateId as key
    static Map<String, Candidate> candidates = new HashMap<>();
    
    //List to store voting audit logs
    static List<String> auditLog = new ArrayList<>();

    //Admin Login Method
    public static boolean adminLogin() {
        System.out.println("\n=== ADMIN LOGIN ===");
        String adminUsername = "admin";
        String adminPassword = "Admin@123"; // Example password

        System.out.print("Enter Admin Username: ");
        String username = sc.nextLine().trim();

        System.out.print("Enter Admin Password: ");
        String password = sc.nextLine().trim();

        if (username.equals(adminUsername) && password.equals(adminPassword)) {
            System.out.println("Admin login successful!!!!");
            return true;
        } else {
            System.out.println("Invalid admin credentials. Access denied.");
            return false;
        }
    }

    //Method to add candidates
    public static void addCandidate() { 
        System.out.println("\n=== ADD CANDIDATE ===");
        System.out.print("Enter Candidate ID: ");
        String candidateId = sc.nextLine().trim();

        System.out.print("Enter Candidate Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter Party Name: ");
        String partyName = sc.nextLine().trim();

        Candidate newCandidate = new Candidate(candidateId, name, partyName);
        candidates.put(candidateId, newCandidate);

        System.out.println("Candidate " + name + " added successfully!");
    }
    
    //Method to Track Audit Log
    public static void viewAuditLog() {
        System.out.println("\n=== VOTING AUDIT LOG ===");
        if (auditLog.isEmpty()) {
            System.out.println("No voting records available.");
        } else {
            System.out.println("Voting Records:");
            for (String record : auditLog) {
                System.out.println(record);
            }
        }
    }
    //Operations that An Admin can handle
    public static void main(String[] args) {
            System.out.println(" =========== Welcome to Admin Menu ===========");
			System.out.println("1.Add Candidate");
			System.out.println("2.Remove Candidate");
			System.out.println("3.View All Candidates");
			System.out.println("4.Start Voting");
			System.out.println("5.Stop Voting");
			System.out.println("6.Declare Result");
			System.out.println("7.View Audit Log");
			System.out.println("8.Back to Main");
           
            int choice;
            while(true){
                System.out.print("Enter your choice: ");
                if(sc.hasNextInt()){
                    choice = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    if(choice >= 1 && choice <= 8) {
                        break;
                    }else{
                        System.out.println("Enter Valid Choice");
                    }
                }else{
                    System.out.println("Invalid Input .. Try Again..");
                    sc.next(); // Clear invalid input
                }
            }

            switch (choice) {
                case 1->{//List Will Never have Pre registered Candidates . We Ensure that Only Valid Candidates ensured by admin are added
                    int count ;
                    while(true){
                        System.out.print("How many Candidates do you want to add?: ");
                        if(sc.hasNextInt()){
                            count = sc.nextInt();
                            if(count > 0 &&  count <=20) {
                                break;
                            }else{
                                System.out.println("Enter Valid Number between 1 and 20");
                            }
                        }else{
                            System.out.println("Invalid Input .. Try Again..");
                            sc.next(); // Clear invalid input
                        }
                    }
                    sc.nextLine(); // Consume newline
                    for(int i=0; i<count; i++){
                        addCandidate();
                    }
                    System.out.println("All Candidates added successfully!!!");
                }
                case 2->{ // Remove Candidate
                    System.out.print("Enter Candidate ID to remove: ");
                    String candidateId = sc.nextLine().trim();
                    if(candidates.containsKey(candidateId)){
                        candidates.remove(candidateId);
                        System.out.println("Candidate with ID " + candidateId + " removed successfully!");
                    }else{
                        System.out.println("No candidate found with this ID.");
                    }
                }
                case 3->{// Viewing List of All candidates
                    System.out.println("\n=== LIST OF ALL CANDIDATES ===");
                    if(candidates.isEmpty()){
                        System.out.println("No candidates available.\nPlease Create List to proceed.");
                    }else{
                        for(Candidate c : candidates.values()){
                            System.out.println("ID: " + c.candidateId + ", Name: " + c.Name + ", Party: " + c.partyName + ", Votes: " + c.Votes);
                        }
                    }
                }
                case 4->{// Start voting
                    if(!candidates.isEmpty() && start_Vote==false && end_Vote==false){
                        start_Vote = true;
                        System.out.println("Voting has started.");
                    }else if(candidates.isEmpty() && start_Vote==false && end_Vote==false){
                        System.out.println("No candidates available to start voting. Please add candidates first.");
                    }else{
                        System.out.println("Voting is already in progress...");
                    }
                }

                case 5 ->{// Stop Voting
                    if(start_Vote==true && end_Vote==false){
                        end_Vote = true;
                        System.out.println("Voting has ended.");
                    }else{
                        System.out.println("Voting has not yet started...");
                    }
                }

                case 6 ->{//Declare Results
                    if(end_Vote==true){
                        // Create PriorityQueue with custom comparator to sort by votes (highest first)
                        PriorityQueue<Candidate> resultQueue = new PriorityQueue<>((a, b) -> b.Votes - a.Votes);
                        
                        // Add all candidates from HashMap to PriorityQueue
                        resultQueue.addAll(candidates.values());
                        
                        // Display results in order of votes (highest to lowest)
                        System.out.println("\n=== ELECTION RESULTS ===");
                        System.out.println("Ranked by number of votes:\n");
                        
                        int rank = 1;
                        while (!resultQueue.isEmpty()) {
                            Candidate c = resultQueue.poll();
                            System.out.printf("\n%20s %20s %20s %20d", 
                                rank++, c.Name, c.partyName, c.Votes);
                        }
                        
                        // Determine and announce winner
                        if (candidates.isEmpty()) {
                            System.out.println("\nNo candidates participated in the election.");
                        } else {
                            System.out.println("\nElection process completed successfully!");
                        }
                    } else {
                        System.out.println("Voting is still in progress or has not started yet.");
                    }
                }
                case 7->{//Audit Log Viewing
                    // Here we are displying which all voters voted [for whom is not shown for privacy]
                    System.out.println("\n=== AUDIT LOG ===");
                    for(String logEntry : auditLog){
                        System.out.println(logEntry);
                    }
                }
                case 8->{//Back to Main Menu
                    System.out.println("Returning to Main Menu...");
                    break;
                }
            }
    }
}
