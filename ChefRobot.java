public class ChefRobot extends Robot implements CookInterface {
   public void recharge() {
      ++this.stamina;
   }

   public void cooking() {
      System.out.println("Chef robot finished a cooking task!");
      --this.stamina;
   }
}
