import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PopulationGenerator {
    
    // A probabilidade de que uma raposa seja criada em qualquer posição da grade.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // A probabilidade de um coelho ser criado em qualquer posição da grade.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;
    // A probabilidade de um jacare ser criado em qualquer posicao da grade.
    private static final double JACARE_CREATION_PROBABILITY = 0.01;
    //A probabilidade de um furacao ser criado em qualquer posicao da grade.
    private static final double FURACAO_CREATION_PROBABILITY = 0.01;
    private static final double CHUVA_CREATION_PROBABILITY = 0.02;

    // Listas de autores no campo.
    private List<Actor> actors;
    // A lista de autores recem-nascidos
    private List newActors;
    // O estado atual do campo.
    private Field field;
    // Um segundo campo, usado para construir a proxima fase da simulacao.
    private Field updatedField;
    
    // Interface grafica visual da simulacao.
    private SimulatorView view;
    private Simulator simulator;


    public PopulationGenerator(int depth, int width, Simulator simulator) {
        this.simulator = simulator;
        actors = new ArrayList<>();
        newActors = new ArrayList<>();
        field = new Field(depth, width);
        updatedField = new Field(depth, width);
        setColor(depth,width);
    }

    private void setColor(int depth, int width) {
        // Cria uma visualizaao do estado de cada local no campo.
        this.view = new SimulatorView(depth, width, simulator);
        this.view.setColor(Fox.class, Color.blue);
        this.view.setColor(Rabbit.class, Color.orange);
        this.view.setColor(Jacare.class, Color.green);
        this.view.setColor(Furacao.class, Color.red);
        this.view.setColor(Chuva.class, Color.DARK_GRAY);
        
    }

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public SimulatorView getView () {
        return this.view;
    }

    public Field getUpdatedField () {
        return this.updatedField;
    }

    public void setUpdatedField (Field field) {
        this.updatedField = field;
    }

    public List<Actor> getNewActorsList() {
        return this.newActors;
    }
    
    public List<Actor> getActorsList() {
        return this.actors;
    }
   
    //Preencher o campo aleatoriamente com autores.

    public void populate(Field field)
    {
        Random rand = new Random();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Fox fox = new Fox(true);
                    actors.add(fox);
                    fox.setLocation(row, col);
                    field.place(fox, row, col);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Rabbit rabbit = new Rabbit(true);
                    actors.add(rabbit);
                    rabbit.setLocation(row, col);
                    field.place(rabbit, row, col);
                }
                else if(rand.nextDouble() <= JACARE_CREATION_PROBABILITY) {
                    Jacare jacare = new Jacare(true);
                    actors.add(jacare);
                    jacare.setLocation(row, col);
                    field.place(jacare, row, col);
                }
                else if(rand.nextDouble() <= FURACAO_CREATION_PROBABILITY) {
                    Furacao furacao = new Furacao();
                    actors.add(furacao);
                    furacao.setLocation(row, col);
                    field.place(furacao, row, col);
                }
                else if(rand.nextDouble() <= CHUVA_CREATION_PROBABILITY) {
                    Chuva chuva = new Chuva();
                    actors.add(chuva);
                    chuva.setLocation(row, col);
                    field.place(chuva, row, col);
                }
            }
        }
        Collections.shuffle(actors);
    }
}