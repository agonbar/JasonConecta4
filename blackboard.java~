
import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.GridWorldModel;
import jason.environment.grid.GridWorldView;
import jason.environment.grid.Location;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import java.util.logging.Logger;

public class blackboard extends Environment {
	
	public static final int GSize = 8; // tamaño tablero
	public static final int BLUE  = 10; // ficha azul

    private Logger logger = Logger.getLogger("conecta4.mas2j."+blackboard.class.getName());
	
	private MarsModel model;
    private MarsView  view;
    
    @Override
    public void init(String[] args) {
        model = new MarsModel(); //crear un modelo
        view  = new MarsView(model); //crear la vista con ese modelo
        model.setView(view); //asociar 
    }
	
	@Override
    public boolean executeAction(String ag, Structure action) {
        logger.info(ag+" doing: "+ action);
        try {
            if (action.getFunctor().equals("put")) {
                int x = (int)((NumberTerm)action.getTerm(0)).solve();
			
                model.put(x);
            } else{return false;}
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        updatePercepts();

        try {
            Thread.sleep(200);
        } catch (Exception e) {}
        informAgsEnvironmentChanged();
        return true;
    }
    

    void updatePercepts() {
    
        
    }

    class MarsModel extends GridWorldModel {
        

       
        Random random = new Random(System.currentTimeMillis());
        private MarsModel() {
            super(GSize, GSize, 2);
            // initial location of agents
            try {
               //setAgPos(0, 0, 0);
                Location r2Loc = new Location(GSize/2, GSize/2);
                //setAgPos(1, r2Loc);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // initial location of garbage
        }
		public void put(int x) {
			try{
			int p=getHeight();
			while(p>=0 &&!isFree(x,p)){
				p--;
		}	
		if(p  <0){
			System.out.println("overflow");
	}else{
		add(BLUE,x,p);
		Thread.sleep(700);
	}
	}catch (Exception e) { }
}
	}
	
	class MarsView extends GridWorldView {

        public MarsView(MarsModel model) {
            super(model, "Conecta4", 600);
            defaultFont = new Font("Arial", Font.BOLD, 18); // change default font
            setVisible(true);
            repaint();
        }
		
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

