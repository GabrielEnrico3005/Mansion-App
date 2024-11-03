class Animal {
    public void makeSound() {
        System.out.println("Animal makes a generic sound");
    }
}

class Dog extends Animal {
    // Overrides the makeSound method in the superclass
    @Override
    public void makeSound() {
        System.out.println("Dog barks");
    }
}

public class test {
    public static void main(String[] args) {
        Animal animal = new Dog(); // Reference of type Animal, object of type Dog
        animal.makeSound();        // Calls the overridden method in Dog class
    }
}
