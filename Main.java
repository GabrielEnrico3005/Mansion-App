import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
   Scanner scan;
   ArrayList<Robot> robots;
   ArrayList<Human> humans;
   int move;
   int cookingTask;
   int cleaningTask;

   public static void main(String[] args) {
      new Main();
   }

   public void initGame() {
      this.move = 10;
      this.cookingTask = (int)(Math.random() * 5.0D + 2.0D);
      this.cleaningTask = 8 - this.cookingTask;
   }

   public void displayLine(int n) {
      for(int i = 0; i < n; ++i) {
         System.out.print("-");
      }

      System.out.println();
   }

   public void enterToContinue() {
      System.out.println("Press enter to continue...");
      this.scan.nextLine();
   }

   public void checkObjective() {
      if (this.move != 0 || this.cookingTask <= 0 && this.cleaningTask <= 0) {
         if (this.cookingTask == 0 && this.cleaningTask == 0) {
            System.out.println("You Win");
            System.exit(0);
         }
      } else {
         System.out.println("You Lose");
         System.exit(0);
      }

   }

   public void displayObjective() {
      System.out.println("Remaining moves: " + this.move);
      System.out.println("Cooking  tasks: " + this.cookingTask);
      System.out.println("Cleaning tasks: " + this.cleaningTask);
   }

   public void displayHelpers() {
      this.displayLine(30);
      System.out.printf("%-20s %-10s\n", "Helper type", "Stamina");
      this.displayLine(30);
      Iterator var2 = this.humans.iterator();

      while(var2.hasNext()) {
         Human human = (Human)var2.next();
         System.out.printf("%-20s %-10d\n", human.getClass(), human.stamina);
      }

      var2 = this.robots.iterator();

      while(var2.hasNext()) {
         Robot robot = (Robot)var2.next();
         System.out.printf("%-20s %-10d\n", robot.getClass(), robot.stamina);
      }

      this.displayLine(30);
   }

   public void displayRobots() {
      this.displayLine(35);
      System.out.printf("%-5s %-20s %-10s\n", "No.", "Helper type", "Stamina");
      this.displayLine(35);

      for(int i = 0; i < this.robots.size(); ++i) {
         System.out.printf("%-5d %-20s %-10d\n", i + 1, ((Robot)this.robots.get(i)).getClass(), ((Robot)this.robots.get(i)).stamina);
      }

      this.displayLine(35);
   }

   public void buyRobot() {
      if (this.robots.size() == 2) {
         System.out.println("You cannot buy more robots!");
      } else {
         System.out.println("Choose robot type: ");
         System.out.println("1. Chef Robot");
         System.out.println("2. Cleaner Robot");
         System.out.print(">>");
         int c = this.scan.nextInt();
         this.scan.nextLine();
         if (c == 1) {
            this.robots.add(new ChefRobot());
            System.out.println("You bought a chef robot!");
         } else {
            this.robots.add(new CleanerRobot());
            System.out.println("You bought a cleaner robot!");
         }
      }

      this.enterToContinue();
   }

   public void rechargeRobot() {
      if (this.robots.size() == 0) {
         System.out.println("You do not have any robots!");
      } else {
         this.displayRobots();
         boolean var1 = true;

         int c;
         do {
            do {
               System.out.print("Enter robot number to recharge: ");
               c = this.scan.nextInt();
               this.scan.nextLine();
            } while(c < 0);
         } while(c > this.robots.size());

         if (((Robot)this.robots.get(c - 1)).stamina < 3) {
            ((Robot)this.robots.get(c - 1)).recharge();
            --this.move;
            System.out.println("This robot is recharged (+1 stamina)");
         } else {
            System.out.println("This robot has full stamina");
         }
      }

      this.enterToContinue();
   }

   public boolean isAvailableHumanHelper() {
      Iterator var2 = this.humans.iterator();

      while(var2.hasNext()) {
         Human human = (Human)var2.next();
         if (human.stamina > 0) {
            return true;
         }
      }

      return false;
   }

   public boolean isAvailableChefRobot() {
      Iterator var2 = this.robots.iterator();

      Robot robot;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         robot = (Robot)var2.next();
      } while(!robot.getClass().equals(ChefRobot.class) || robot.stamina <= 0);

      return true;
   }

   public boolean isAvailableCleanerRobot() {
      Iterator var2 = this.robots.iterator();

      Robot robot;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         robot = (Robot)var2.next();
      } while(!robot.getClass().equals(CleanerRobot.class) || robot.stamina <= 0);

      return true;
   }

   public boolean isAvailableCookingHelper() {
      if (this.isAvailableHumanHelper()) {
         return true;
      } else {
         return this.isAvailableChefRobot();
      }
   }

   public boolean isAvailableCleaningHelper() {
      if (this.isAvailableHumanHelper()) {
         return true;
      } else {
         return this.isAvailableCleanerRobot();
      }
   }

   public void performCookingTask() {
      if (this.cookingTask == 0) {
         System.out.println("There are no cooking tasks left!");
      } else if (!this.isAvailableCookingHelper()) {
         System.out.println("There are no available helpers to cook!");
      } else {
         Iterator var2;
         if (this.isAvailableChefRobot()) {
            var2 = this.robots.iterator();

            while(var2.hasNext()) {
               Robot robot = (Robot)var2.next();
               if (robot.getClass().equals(ChefRobot.class) && robot.stamina > 0) {
                  ((ChefRobot)robot).cooking();
                  break;
               }
            }
         } else if (this.isAvailableHumanHelper()) {
            var2 = this.humans.iterator();

            while(var2.hasNext()) {
               Human human = (Human)var2.next();
               if (human.stamina > 0) {
                  human.cooking();
                  break;
               }
            }
         }

         --this.cookingTask;
         --this.move;
      }

      this.enterToContinue();
   }

   public void performCleaningTask() {
      if (this.cleaningTask == 0) {
         System.out.println("There are no cleaning tasks left!");
      } else if (!this.isAvailableCleaningHelper()) {
         System.out.println("There are no available helpers to clean!");
      } else {
         Iterator var2;
         if (this.isAvailableCleanerRobot()) {
            var2 = this.robots.iterator();

            while(var2.hasNext()) {
               Robot robot = (Robot)var2.next();
               if (robot.getClass().equals(CleanerRobot.class) && robot.stamina > 0) {
                  ((CleanerRobot)robot).cleaning();
                  break;
               }
            }
         } else if (this.isAvailableHumanHelper()) {
            var2 = this.humans.iterator();

            while(var2.hasNext()) {
               Human human = (Human)var2.next();
               if (human.stamina > 0) {
                  human.cleaning();
                  break;
               }
            }
         }

         --this.cleaningTask;
         --this.move;
      }

      this.enterToContinue();
   }

   public Main() {
      this.scan = new Scanner(System.in);
      this.robots = new ArrayList();
      this.humans = new ArrayList();
      this.humans.add(new Human());
      this.humans.add(new Human());
      int menu = 0;
      this.initGame();
      
      do {
         this.displayObjective();
         this.checkObjective();
         this.displayHelpers();
         System.out.println("1. Buy new robot");
         System.out.println("2. Recharge a robot");
         System.out.println("3. Perform a cooking task");
         System.out.println("4. Perform a cleaning task");
         System.out.println("5. Exit");
         System.out.print(">>");
         menu = this.scan.nextInt();
         this.scan.nextLine();
         switch(menu) {
         case 1:
            this.buyRobot();
            break;
         case 2:
            this.rechargeRobot();
            break;
         case 3:
            this.performCookingTask();
            break;
         case 4:
            this.performCleaningTask();
         }
      } while(menu != 5);

   }
}
