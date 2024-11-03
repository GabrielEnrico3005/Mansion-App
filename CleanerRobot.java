public class CleanerRobot extends Robot implements CleanInterface {
   public void recharge() {
      this.stamina += 1;
   }

   public void cleaning() {
      System.out.println("Cleaner robot finished a cleaning task!");
      this.stamina -= 1;
   }
}
