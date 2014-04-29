import jason.NoValueForVarException;
import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.GridWorldModel;
import jason.environment.grid.GridWorldView;
import jason.environment.grid.Location;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class blackboard extends Environment {
    /* Tamaño del tablero */
    public static final int GSize = 8;
    /* Fichas del juego */
    public static final int BLUE = 1;
    public static final int RED = 2;

    private static final Logger logger = Logger.getLogger("conecta4.mas2j." + blackboard.class.getName());
	
    private MarsModel model;
    private MarsView  view;
    
    @Override
    public void init(String[] args) {
        /* Crear un modelo */
        model = new MarsModel();
        
        /* Crear la vista con ese modelo */
        view  = new MarsView(model);
        
        /* Asociar la vista con el modelo */
        model.setView(view);
    }
	
    @Override
    public boolean executeAction(String ag, Structure action) {
        logger.log(Level.INFO, "{0} doing: {1}", new Object[]{ag, action});
        
        try {
            if (action.getFunctor().equals("put")) {
                int x = (int)((NumberTerm)action.getTerm(0)).solve();
                model.put(x);
            }
            else {
                return false;
            }
        }
        catch (NoValueForVarException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        
        updatePercepts();
        
        try {
            Thread.sleep(200);
        }
        catch (InterruptedException e) {}
        
        informAgsEnvironmentChanged();
        
        return true;
    }
    
    void updatePercepts() {
    }

    class MarsModel extends GridWorldModel {
        
        private MarsModel() {
            super(GSize, GSize, 2);
            
            this.random = new Random(System.currentTimeMillis());
            
            try {
                Location r2Loc = new Location(GSize/2, GSize/2);
            }
            catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
        public void put(int x) {
            try{
                int p = getHeight();
                
                while (p >= 0 && !isFree(x,p)) {
                    p--;
                }
                
                if (p < 0){
                        System.out.println("overflow");
                }
                else {
                    add(BLUE, x, p);
                    Thread.sleep(500);
                }
            }
            catch (InterruptedException e) { }
        }
    }
	
    class MarsView extends GridWorldView {
        public MarsView(MarsModel model) {
            super(model, "Conecta4", 600);
            
            defaultFont = new Font("Arial", Font.BOLD, 18);
            
            setVisible(true);
            
            repaint();
        }
	
        @Override
        public void draw(Graphics g, int x, int y, int object) {
            switch (object) {
                case blackboard.BLUE: drawFicha(g, x, y);  break;
            }
        }
        
        public void drawFicha(Graphics g, int x, int y) {
            g.setColor(Color.blue);
            g.fillOval(x,y,70,70);
        }
    }
}

