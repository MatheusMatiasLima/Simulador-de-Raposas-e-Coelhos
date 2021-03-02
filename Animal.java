import java.util.List;

public abstract class Animal {
    // Whether the fox is alive or not.
    protected boolean alive;
    // The fox's position
    protected Location location;
    // Idade do animal
    private int age;
    // executa a ação do animal
    abstract public void act (Field currentField, Field updatedField, List newFoxes);
    // retorna o BREEDING AGE do animal
    abstract protected int getBreedingAge();
    // retorna o MAX_AGE do animal
    abstract protected int getMaxAge();



    //cria um animal com a idade 0
    public Animal () {
        age = 0;
        alive = true;
    }

    //retorna a idade
    public int getAge() {
        return age;
    }

    //define a idade
    public void setAge(int age) {
        this.age = age; 
    }

    // Um animal pode procriar se tiver atingido a idade reprodutiva.
    protected boolean canBreed() {
        return age >= getBreedingAge();
    }


    // Incremena a idade. Isso pode resultar na morte do animal.
    protected void incrementAge() {
        age++;
        if(age > getMaxAge()) {
            alive = false;
        }
    }

    

    /**
     * Set the animal's location.
     * @param row The vertical coordinate of the location.
     * @param col The horizontal coordinate of the location.
     */
    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    /**
     * Set the animal's location.
     * @param location The fox's location.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Check whether the animal is alive or not.
     * @return True if the animal is still alive.
     */
    public boolean isAlive() {
        return alive;
    }
    
}
