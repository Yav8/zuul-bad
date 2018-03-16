import java.util.Stack;
import java.util.ArrayList;

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
    private Room currentRoom;
    private Stack<Room> lastRoom;
    private ArrayList<Item> listaDeObjetos;
    private int pesoTotal;
    public static final int PESO_QUE_PUEDE_LLEVAR_EL_PERSONAJE = 30;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        lastRoom = new Stack<>();
        listaDeObjetos = new ArrayList<>();
        pesoTotal = 0;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room tienda, centroComercial, parque, casaDelJugador, casino, casaDelLadron;
      
        // create the rooms
        tienda = new Room("en la tienda donde se ha producido un robo de varios productos. Como policia que soy encontrare a ese delincuente.");
        tienda.addObjeto("Caja de galletas", 10);
        tienda.addObjeto("Bolsa de cereales", 4);
        
        centroComercial = new Room("en el centro comercial, donde se escuchan ruidos y gritos proveniendo de una seccion de la segunda planta. Al llegar alli, me encuentro al ladron robando mas productos y pertenecian a la seccion en donde trabajaba su novia (Supongo que hay gente a la que le importa mas el dinero que cualquier otra cosa en esta vida...) ¡PUM! Ya eres mio, no saldras de la carcel en muchos años. La novia del ladron dice entre lloros y llantos: Gracias por darle su merecido a ese miserable traidor.");
        centroComercial.addObjeto("Ladron", 65);
        
        parque = new Room("en el parque. No parece que el ladron este por aqui. Buscare en otra parte.");
        parque.addObjeto("Columpio", 40);
        parque.addObjeto("Balon", 5);
        parque.addObjeto("Niño", 29);
        
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

        currentRoom = tienda;  // start game outside
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
        printLocationInfo();
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
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("eat")) {
            System.out.println("You have eaten now and you are not hungry anymore");
        }
        else if(commandWord.equals("back")) {
            goLastRoom();
        }
        else if(commandWord.equals("take")) {         
            take(command);
        }
        else if(commandWord.equals("drop")) {     
            drop(command);
        }
        else if(commandWord.equals("items")) {
            items();
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
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            lastRoom.push(currentRoom);
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    /** 
     * Va a la anterior sala, si la hay.
     */
    private void goLastRoom() 
    {
        if(!lastRoom.empty()) {
            currentRoom = lastRoom.pop();
            printLocationInfo();
        }
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
    private void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Muestra por pantalla la informacion de la sala 
     * actual y las salidas posibles.
     */
    private void look() {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Coge el objeto que el usuario desee en la sala actual.
     * @param command El comando que va a ser procesado.
     */
    private void take(Command command) {
        int numero = 0;
        if(command.hasSecondWord()) {
            if(command.getSecondWord().contains("0") || command.getSecondWord().contains("1") || command.getSecondWord().contains("2") || command.getSecondWord().contains("3") || command.getSecondWord().contains("4") || command.getSecondWord().contains("5") || command.getSecondWord().contains("6") || command.getSecondWord().contains("7") || command.getSecondWord().contains("8") || command.getSecondWord().contains("9")) {
                numero = Integer.parseInt(command.getSecondWord());
                if(currentRoom.getListaDeObjetos().size() > 0 && command.getSecondWord().equals("" + numero) && numero <= currentRoom.getListaDeObjetos().size() && numero > 0) {
                    pesoTotal += currentRoom.getListaDeObjetos().get(numero - 1).getPeso();
                    if(pesoTotal < PESO_QUE_PUEDE_LLEVAR_EL_PERSONAJE) {  
                        listaDeObjetos.add(currentRoom.getListaDeObjetos().get(numero - 1));
                        currentRoom.eliminarObjeto(currentRoom.getListaDeObjetos().get(numero - 1));
                    }
                    else {
                        pesoTotal -= currentRoom.getListaDeObjetos().get(numero - 1).getPeso();
                        System.out.println("You can't take that item, it's too weight for you!");
                    }
                }
                else {
                    System.out.println("There is no item!");
                }
            }
            else {
                System.out.println("Choose the number of the object in the second word that you want to take!");
            }
        }
        else {
            System.out.println("Take what?");
        }
    }
    
    /**
     * Suelta el objeto seleccionado en la sala actual.
     * @param command El comando que va a ser procesado.
     */
    private void drop(Command command) {
        int numero = 0;
        if(command.hasSecondWord()) {
            if(command.getSecondWord().contains("0") || command.getSecondWord().contains("1") || command.getSecondWord().contains("2") || command.getSecondWord().contains("3") || command.getSecondWord().contains("4") || command.getSecondWord().contains("5") || command.getSecondWord().contains("6") || command.getSecondWord().contains("7") || command.getSecondWord().contains("8") || command.getSecondWord().contains("9")) {
                numero = Integer.parseInt(command.getSecondWord());
                if(command.getSecondWord().equals("" + numero) && numero <= listaDeObjetos.size() && numero > 0) {
                    currentRoom.addObjeto(listaDeObjetos.get(numero - 1).getDescripcion(), listaDeObjetos.get(0).getPeso());
                    pesoTotal -= listaDeObjetos.get(numero - 1).getPeso();
                    listaDeObjetos.remove(numero - 1);  
                }
                else {
                    System.out.println("You don't have any item in that slot!");
                } 
            }
            else {
                System.out.println("Choose the number of the object in the second word that you want to drop!");
            }
        }
        else {
            System.out.println("Drop what?");
        }
    }
    
    /**
     * Muestra por pantalla el listado de objetos que actualmente 
     * tiene el personaje.
     */
    private void items() {        
        for(Item objeto : listaDeObjetos) {
            System.out.println(objeto.getCaracteristicas());
        }
        if(listaDeObjetos.isEmpty()) {
            System.out.println("You don't have any item yet!");
        }
    }
}
