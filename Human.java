public class Human implements CookInterface, CleanInterface {
   int stamina = 2;

   public void cooking() {
      System.out.println("Human finished a cooking task!");
      --this.stamina;
   }

   public void cleaning() {
      System.out.println("Human finished a cleaning task!");
      --this.stamina;
   }
}
