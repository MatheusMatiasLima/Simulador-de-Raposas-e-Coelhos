import java.util.List;
import java.util.Random;

/**
 * Modelo simples de um coelho.
 * Coelhos nascem, movem, procriam, e morrem.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-11
 */
public class Rabbit extends Animal {
    // Caracteristicas compartilhadas por todos coelhos (campos estaticos).

    // A idade que um coelho começa a procriar
    private static final int BREEDING_AGE = 5;
    // Idade maximo que um coelho pode viver.
    private static final int MAX_AGE = 50;
    // Propabilidade de um coelho procriar
    private static final double BREEDING_PROBABILITY = 0.15;
    // Numero maximo de nascimentos de uma gestacao
    private static final int MAX_LITTER_SIZE = 5;
    // Numero aleatorio compartilhado que controla os nascimentos
    private static final Random rand = new Random();
    
    // Caracteristicas individuais (campos de instancia).
    


    /**
     * @param randomAge Se true, os coelhos terao uma idade aleatoria.
     */
    public Rabbit(boolean randomAge) {
        super();
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
        }
    }

    @Override
    public void act (Field currentField, Field updatedField, List newRabbits) {
        run(updatedField, newRabbits);
    }

    @Override
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    @Override
    public int getMaxAge () {
        return MAX_AGE;
    }

    @Override
    public int getMaxLitterSize () {
        return MAX_LITTER_SIZE;
    }

    @Override
    public double getBreedingProbability () {
        return BREEDING_PROBABILITY;
    }

    /**
     * Isto e o que os coelhos normalmente fazem - correm por ai
     * As vezes procriam ou morrem de velhice.
     */ 
    private void run(Field updatedField, List newRabbits) {
        incrementAge();
        
        if(alive) {
            int births = breed();
            for(int b = 0; b < births; b++) {
                //Rabbit newRabbit = new Rabbit(false);
                //newRabbits.add(newRabbit);
                Location loc = updatedField.randomAdjacentLocation(location);
                Object obj = updatedField.getObjectAt(loc);
                if(!(obj instanceof Lago)){
                    Rabbit newRabbit = new Rabbit(false);
                    newRabbits.add(newRabbit);
                    newRabbit.setLocation(loc);
                    updatedField.place(newRabbit, loc);
                }
            }
            Location newLocation = updatedField.freeAdjacentLocation(location,false);
            // Apenas transfere para um novo campo se possuir uma localizacao livre
            if(newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            }
            else {
                // Nao pode mover nem ficar - superlotacao - todas localizacoes ocupadas
                alive = false;
            }
        }
    }
    

    /**
     * Conte para os coelhos que eles estao mortos agora :(
     */
    public void setEaten() {
        alive = false;
    }
    
}
