import java.util.List;
import java.util.Random;

public abstract class Animal implements Actor{
    // Se o animal está vivo ou não
    protected boolean alive;
    // A posição do animal
    protected Location location;
    // Idade do animal
    private int age;
    // Um gerador de números aleatórios compartilhado para controlar a reprodução.
    private static final Random rand = new Random();    
    // executa a ação do animal
    abstract public void act (Field currentField, Field updatedField, List newFoxes);

    abstract protected int getBreedingAge();
    abstract protected int getMaxAge();
    abstract protected int getMaxLitterSize();
    abstract protected double getBreedingProbability();

    //cria um animal com a idade 0
    public Animal () {
        age = 0;
        alive = true;
    }
    // deixa o animal doente, perto do fim da vida.
    public void deixarDoente() {
        age = getMaxAge() - 2;
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
    // Mata o animal
    public void setDead() {
        alive = false;
    }

    /**
     * Gere um número que representa o número de nascimentos,
     * se pode procriar.
     * @return O número de nascimentos (pode ser zero).
     */
    protected int breed() {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
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

    @Override 
    public boolean isAtive() {
        return isAlive();
    }
    
}
