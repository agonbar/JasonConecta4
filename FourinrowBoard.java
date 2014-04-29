import jason.NoValueForVarException;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.Structure;
import jason.environment.Environment;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FourinrowBoard extends Environment {
    /* Tamaño del tablero */
    private static final int GSize = 8;
    
    /* Nombres de los agentes */
    private final String JugadorPrimario = "jugadorPrimario";
    private final String JugadorSecundario = "jugadorSecundario";

    private static final Logger logger = Logger.getLogger("conecta4.mas2j." + FourinrowBoard.class.getName());
	
    private FourinrowBoardModel model;
    private FourinrowBoardView  view;
    
    @Override
    public void init(String[] args) {
        /* Crear un modelo */
        model = new FourinrowBoardModel(GSize, GSize, 2);
        
        /* Crear la vista con ese modelo */
        view  = new FourinrowBoardView(model);
        
        /* Asociar la vista con el modelo */
        model.setView(view);
    }
	
    @Override
    public boolean executeAction(String ag, Structure action) {
        logger.log(Level.INFO, "El agente <" + ag + "> ejecuta la acción <" + action.getFunctor() + ">");
        
        switch (action.getFunctor()) {
            case "put":
                try {
                    int x = (int)((NumberTerm)action.getTerm(0)).solve();

                    switch (ag) {
                        case JugadorPrimario:
                            model.put(FourinrowChip.BLUE, x);
                            break;
                        case JugadorSecundario:
                            model.put(FourinrowChip.RED, x);
                            break;
                        default:
                            logger.log(Level.SEVERE, "No se reconoce el nombre del agente <" + ag + ">");
                            return false;
                    }
                }
                catch (NoValueForVarException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                    return false;
                }
                break;
            default:
                logger.log(Level.SEVERE, "El agente <" + ag + "> ejecutó la acción no reconocida <" + action.getFunctor() + ">");
                return false;
        }
        
        updatePercepts();
            
        /* Se espera un tiempo para evitar errores inesperados */
        try {
            Thread.sleep(200);
        }
        catch (InterruptedException e) {}
        
        /* TODO: Comprobar si el juego ha finalizado */
        
        informAgsEnvironmentChanged();
        
        return true;
    }
    
    void updatePercepts() {
        this.clearPercepts();
        
        /* TODO: Notificar posicion de las fichas */
    }
}

