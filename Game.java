import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Player jugador;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        jugador = new Player(createRooms());
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room tienda, centroComercial, parque, casaDelJugador, casino, casaDelLadron;
      
        // create the rooms
        tienda = new Room("en la tienda donde se ha producido un robo de varios productos. Como policia que soy encontrare a ese delincuente.");
        tienda.addObjeto("Caja de galletas", "galletas", 10, true);
        tienda.addObjeto("Bolsa de cereales", "cereales", 4, true);
        
        centroComercial = new Room("en el centro comercial, donde se escuchan ruidos y gritos proveniendo de una seccion de la segunda planta. Al llegar alli, me encuentro al ladron robando mas productos y pertenecian a la seccion en donde trabajaba su novia (Supongo que hay gente a la que le importa mas el dinero que cualquier otra cosa en esta vida...) ¡PUM! Ya eres mio, no saldras de la carcel en muchos años. La novia del ladron dice entre lloros y llantos: Gracias por darle su merecido a ese miserable traidor.");
        centroComercial.addObjeto("Ladron", "ladron", 65, false);
        
        parque = new Room("en el parque. No parece que el ladron este por aqui. Buscare en otra parte.");
        parque.addObjeto("Columpio", "columpio", 100, false);
        parque.addObjeto("Balon", "balon", 5, true);
        parque.addObjeto("Niño", "niño", 30, true);
        
        casaDelJugador = new Room("en tu propia casa. El ladron tampoco esta aqui.");
        
        casino = new Room("en el casino. Segun he escuchado, suele venir el ladron para obtener informacion acerca de la seguridad de las diferentes tiendas de la ciudad. Lamentablemente, no hay pistas por aqui.");
        
        casaDelLadron = new Room("en la casa del ladron, veamos si hay alguna pista por aqui sobre su paradero... Hmm he encontrado una foto de su novia, si no me equivoco trabaja en el centro comercial, puede que el ladron haya ido a visitarla para contarle sus hazañas...");
        
        // initialise room exits
        tienda.setExit("north", parque);
        tienda.setExit("east", casino);
        tienda.setExit("west", casaDelJugador);
        
        centroComercial.setExit("north", casaDelJugador);
        
        parque.setExit("south", tienda);
        
        casaDelJugador.setExit("east", tienda);
        casaDelJugador.setExit("south", centroComercial);
        
        casino.setExit("west", tienda);
        casino.setExit("southeast", casaDelLadron);
        
        casaDelLadron.setExit("northwest", casino);

        return tienda;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        jugador.printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            jugador.goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("eat")) {
            jugador.comer();
        }
        else if(commandWord.equals("back")) {
            jugador.goLastRoom();
        }
        else if(commandWord.equals("take")) {
            jugador.coger(command);
        }
        else if(commandWord.equals("items")) {
            jugador.mostrarObjetos();
        }
        
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Muestra por pantalla la informacion de la sala 
     * actual y las salidas posibles.
     */
    private void look() {
        System.out.println(jugador.getCurrentRoom().getLongDescription());
    }
}
