package cmsc256;

import java.util.Objects;

public class Dog implements Comparable<Dog> {
    private String dogName;
    private int count;

    public Dog(String dogName, int count) {
        this.dogName = dogName;
        this.count = count;
    }

    @Override
    public int compareTo(Dog o) {
        return this.dogName.compareTo(o.dogName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return Objects.equals(dogName, dog.dogName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dogName);
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toString() {
        return String.format("%s is registered %d times.", dogName, count);
    }
}
