// Agent jugador1 in project conecta4.mas2j

/* Initial beliefs and rules */

+!start:true. 
at(P) :- pos(P,X,Y) & pos(r1,X,Y).

/* Initial goals */

/* Plans */

+!put(1).
