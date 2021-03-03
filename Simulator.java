import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a field containing
 * rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-09
 */
public class Simulator {
    // As variáveis finais estáticas privadas representam informações de configuração para a simulação.

    // A largura padrão da grade.
    private static final int DEFAULT_WIDTH = 50;
    // A profundidade padrão da grade.
    private static final int DEFAULT_DEPTH = 50;
    // A probabilidade de que uma raposa seja criada em qualquer posição da grade.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // A probabilidade de um coelho ser criado em qualquer posição da grade.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;    

    // A etapa atual da simulação.
    private int step;

    private PopulationGenerator populacao;
    
   
    // Constroi um campo de simulação com tamanho padrão.
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Crie um campo de simulação com o tamanho fornecido.
     * @param depth Profundidade do campo. Deve ser maior que zero. 
     * @param width Largura do campo. Deve ser maior que zero.
     */
    public Simulator(int depth, int width) {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        populacao = new PopulationGenerator(depth, width);
        // Configure um ponto de partida válido.
        reset();
    }
    
    //Execute a simulação de seu estado atual por um período razoavelmente longo, por exemplo. 500 passos.
    public void runLongSimulation() {
        simulate(500);
    }
    
     
    // Execute a simulação de seu estado atual para um determinado número de etapas.
    // Pare antes de um determinado número de etapas se deixar de ser viável.
    public void simulate(int numSteps) {
        for(int step = 1; step <= numSteps && populacao.getView().isViable(populacao.getField()); step++) {
            simulateOneStep();
        }
    }
    

    // Execute a simulação de seu estado atual para uma única etapa.
    // Repita em todo o campo atualizando o estado de cada raposa e coelho.


    public void simulateOneStep() {
        step++;
        populacao.getNewActorsList().clear();
        
        // deixe todos os animais agirem
        for(Iterator<Actor> iter = populacao.getActorsList().iterator(); iter.hasNext(); ) {
            Actor animal = iter.next();
            if (animal.isAtive()) {
                animal.act(populacao.getField(), populacao.getUpdatedField(), populacao.getNewActorsList());
            }
            else {
                iter.remove();
            }
        }
        // adiciona animais recém-nascidos à lista de animais
        populacao.getActorsList().addAll(populacao.getNewActorsList());
        
        // Troque o campo e updatedField no final da etapa.
        Field temp = populacao.getField();
        populacao.setField(populacao.getUpdatedField());
        populacao.setUpdatedField(temp);
        populacao.getUpdatedField().clear();

        // exibir o novo campo na tela
        populacao.getView().showStatus(step, populacao.getField());
    }
        
    //Reset the simulation to a starting position.
    public void reset()
    {
        step = 0;
        populacao.getActorsList().clear();
        populacao.getField().clear();
        populacao.getUpdatedField().clear();
        populacao.populate(populacao.getField());
        
        // Show the starting state in the view.
        populacao.getView().showStatus(step, populacao.getField());
    }
    
/*
    // Povoe o campo com raposas e coelhos.
    private void populate(Field field) {
        Random rand = new Random();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Fox fox = new Fox(true);
                    animals.add(fox);
                    fox.setLocation(row, col);
                    field.place(fox, row, col);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Rabbit rabbit = new Rabbit(true);
                    animals.add(rabbit);
                    rabbit.setLocation(row, col);
                    field.place(rabbit, row, col);
                }
                // caso contrário, deixe o local vazio.
            }
        }
        Collections.shuffle(animals);
    }
*/
}
