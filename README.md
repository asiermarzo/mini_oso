# mini_oso
Ejemplo minimalista del juego del Oso

oso.game.Jugada encapsula una posición (x,y) en el tablero así como la letra que se coloca. Los jugadores mandan jugadas al servidor cuando es su turno.

oso.game.Tablero contiene el array de casillas con los carácteres ' ' si la casilla está vacía, 'o' o 's' si tiene esas letras que están sin usar y 'O' o 'S' si ya han sido usadas para hacer osos y por lo tanto no se pueden volver a usar. La clase puede comprobar si una jugada se puede aplicar, así como aplicarla devolviendo el número de oso que realiza. También se serializa del servidor al cliente para mandar el estado del tablero.

oso.clients.ClienteOso un hilo que contiene la lógica para conectarse al servidor, crear el GUI e intercambiar paquetes con el Servidor. Dado que el juego es por turnos, sólo se necesita de este hilo en el cliente.

oso.clients.OsoGUI la interfaz gráfica para jugar con radioButtons para seleccionar si se pone una O o S, crea el array de botones, se activa desactivan los botones, y manda jugadas al servidor. También muestra los puntos.

oso.network.ConexionJugador encapsula el socket así como los ObjectInputStream y ObjectOutputStream para intercambiar objetos entre cliente <--> servidor, esta clase se utiliza tanto por el cliente como por el servidor. 

El PaquetePuntos se manda del servidor al cliente para indicar los puntos de cada jugador, el PaqueteTuTurno lo manda el servidor al cliente para indicarle que es su turno.

oso.server.Partida es un hilo que gestiona la Partida entre 2 jugadores. 

oso.server.ServidorOso es el hilo del servidor que acepta 2 clientes y crea una partida con ellos.

oso.ServerY2Clientes es para lanzar 1 servidor y 2 clientes de golpe y poder probar rápido las partidas multi-jugador. No es recomendable utilizalo para desarrollar/depurar ya que al cerrar cualquiera de los clientes o servidores, se cerrará todo.
