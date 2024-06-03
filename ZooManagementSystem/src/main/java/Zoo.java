/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DAVID'S-PC
 */
public class Zoo {
    public static void main(String[] args) {
        // Create instances of different animals
        Animal lion = new Lion("Kabaka", 5);
        Animal elephant = new Elephant("Mulondo", 10);
        Animal monkey = new Monkey("Walusimbi", 3);

        // Demonstrate polymorphism with overloaded methods
        lion.makeSound();
        lion.makeSound(3); // Roar 3 times
        lion.eat();
        lion.eat("meat");

        elephant.makeSound();
        elephant.makeSound(2); // Trumpet 2 times
        elephant.eat();
        elephant.eat("grass");

        monkey.makeSound();
        monkey.makeSound(4); // Chatter 4 times
        monkey.eat();
        monkey.eat("bananas");
    }
}

