# Tema 1 - GwentStone
## Copyright 2022 David Nedelcu 324CA
___________________________________________________________________________________________________

	Package game.functionalities contine clasele:

- *ChosenDecks* este clasa in care vom tine deckurile alese pentru fiecare
jucator la inceput de joc. Aceasta clasa preia deckurile din input, iar dupa
ce alege cu care deck va juca fiecare player, sorteaza cartile si atribuie
fiecarei carti tipul acesteia dupa nume (ex: Warden, Goliath, Firestorm etc.), 
iar apoi le amesteca.

- *ChosenHeroes* este clasa in care vom tine eroii alesi pentru fiecare jucator
la inceput de joc. Aceasta clasa preia inputul si atribuie eroul specific 
fiecarui jucator dupa nume (Lord Royce, Empress Thorina, etc.) si pe langa asta
mai avem un array de Integer prin care vom tine evidenta daca eroul a atacat
sau nu tura respectiva (heroAlreadyAttacked[1] este checkerul pentru primul
erou si heroAlreadyAttacked[2] este checkerul pentru al doilea, daca valoarea
este 0 eroul nu a atacat, daca este 1 acesta deja a atacat).

- *GameTable* este clasa in care vom tine masa de joc si cateva atribute legate
de aceasta. Avem un array de array-uri de tip Minion care va reprezenta masa
propriu-zisa, o matrice de Integer (frozenCoords) in care vom stoca coordonatele
minionilor frozen sau care deja au atacat de pe fiecare rand (0 - nu a atacat; 
1 - a fost inghetat; 2 - deja a atacat) si 2 string array-uri drept referinte 
pentru pozitile front si back si unul pentru tank

- *Card* este clasa abstracta care tine modelul clasic de carte, la care am 
adaugat un camp protected 'position' care stocheaza daca cartea se pune pe 
randul din fata sau cel din spate.

- *GameFlow* este clasa care tine la un loc tot ce am mentionat mai sus, aceasta
preia datele de input si creeaza o instanta de tip GameTable (board), o instanta
tip ChosenDecks (chosenDecks),si una ChosenHeroes (chosenHeroes) dupa care 
creeaza cele 2 maini ale jucatorilor si muta prima carte din fiecare deck in mana
fiecarui jucator, seteaza playerTurn ul la jucatorul care incepe, counterul pentru 
runde la 1, mana fiecarui jucator la 1 si un roundSwitchChecker la 0 care ne ajuta
sa ne dam seama cand se schimba runda. Aceasta clasa reprezinta toate pregatirile 
necesare pentru inceputul de joc si pregateste jocul sa fie gata sa preia comenzi.

- *HasAbility* este o interfata care ne da posibilitatea sa atribuim eroilor si 
minionilor legendari abilitatile lor specifice. 
___________________________________________________________________________________________________

	Package-urile game.environment, game.heroes si game.minions 
reprezinta fiecare categorie de entitati sin joc, avand fiecare tipurile specifice 
si o clasa abstracta cu numele categoriei care tine modelele clasice de obiecte 
Environment, Hero respectiv Minion. Sa le luam pe rand:

- *Environment* clasa abstracta ce extinde Card si implementeaza HasAbility este 
clasa parinte pentru clasele Firestrosm, HeartHound si Winterfell carora le-am 
creat fiecareia abilitatea specifica

- *Hero* clasa abstracta ce extinde Card, la care am adaugat campul health cu 
valoarea de 30, implementeaza HasAbility si este clasa parinte pentru clasele 
EmpressThorina, GeneralKocioraw, KingMugface si LordRoyce carora le-am creat 
fiecareia abilitatea specifica

- *Minion* clasa abstracta ce extinde Card, la care am adaugat campurile 
attackDamage, health, tank si position, ultimele 2 fiind specificatori (daca
este tank sau nu si pe ce pozitie se plaseaza pe tabla), este clasa parinte 
pentru clasele Berserker, Goliath, Sentinel, Warden, Disciple, Miaraj, TheRipper, 
TheCursedOne si BasicMinion. Fiecare tip are specificat daca este tank sau nu si 
pozitia aferenta pe masa, iar minionii legendari au si abilitatile specifice 
implementate. Clasa BasicMinion este creata pentru a instantia un obiect de tip 
Minion (neputand sa fac asta din clasa Minion fiind abstracta), mai exact il 
folosesc pentru a face deep copy unei carti pt comanda getCardAtPosition.
___________________________________________________________________________________________________
	- Clasa *Main* :

	Am ales sa fac 2 for-uri, unul pentru a parsa jocurile si al doilea pentru a 
parsa comenzile. Am setat la 0 counterele pentru scor si jocuri jucate inainte 
de primul for. Pentru fiecare joc se instantiaza clasa GameFlow cu obiectul game
si se incrementeaza numarul de jocuri jucate, apoi intram in al 2-lea for, cel 
care citeste prin comenzile. Am stocat la inceput caracteristicile unei actiuni
din input pentru a le putea folosi mai usor pe parcurs. Creez un nod 'action' in 
care uremaza sa adaug key-val in functie de comanda specifica si sa il adaug in 
array-ul 'output'.

 	- Avem 19 comenzi:
___________________________________________________________________________________________________
```
* getPlayerDeck 
```
Se face deep copy pentru deckul cerut si se adauga in nod alaturi de 'command'
si 'playerIdx', la final se adauga nodul in output array.
___________________________________________________________________________________________________
```
* getPlayerHero
``` 
Se creaza un nod ce reprezinta un deep copy pentru eroul cerut in care se pune
fiecare caracteristica a acestuia prin metodele .put() si .putPOJO si se adauga 
in nodul action alaturi de 'command' si'playerIdx', la final se adauga nodul 
in output array.
___________________________________________________________________________________________________
```
* getPlayerTurn
```  
Se adauga 'command' si 'output' cu playerTurn-ul actual si se adauga nodul in 
output array.
___________________________________________________________________________________________________
```
* endPlayerTurn
```  
Se verifica turn-ul actual si se modifica, apoi se reseteaza minionii frozen 
sau care au atacat deja din jumatatea de masa a jucatorului ce si-a terminat 
turn-ul, la fel si checker-ul daca eroul acestuia a atacat, iar la final se 
verifica daca se trece la runda urmatoare, in caz ca da se reseteaza checker-ul 
pentru switch-round, se trage o carte din pachet si se sterge din deck, round
se incrementeaza si se atribuie mana, in caz contrar switch-round-checker-ul 
se incrementeaza.
___________________________________________________________________________________________________
```
* placeCard
```
In functie de turn se deduce jucatorul care plaseaza cartea, se verifica cazurile
invalide si daca niciunul nu este intalnit, se face un deep copy cartii din mana,
specificandu-se tipul ei inaintesa fie musa jos (functia specifyingTypeOfMinion),
se plaseaza pe masa si se scade mana aferenta, in caz contrar se specifica 
eroarea si se adauga nodul in output array.
___________________________________________________________________________________________________
```
* getCardsInHand
```   
Se face un deep copy pentru mana jucatorului si se adauga in nod alaturi de 
'comand' si 'playerIdx'.
___________________________________________________________________________________________________
```
* getCardsOnTable
```   
Se face un deep copy pentru masa si se adauga cu .putPOJO() in nodul action, 
alaturi de 'command'.
___________________________________________________________________________________________________
```
* getPlayerMana
```
Se adauga in nod 'command', 'playerIdx', mana jucatorului si se adauga nodul 
in array.
___________________________________________________________________________________________________
```
* getEnvironmentCardsInHand
```
Se creeaza un array de tip Card in care se adauga doar cartile Environment si 
acesta se adauga in nod cu .putPOJO() alaturi de 'playerIdx' si 'command' si 
se adauga nodul in output array.
___________________________________________________________________________________________________
```
* getCardAtPosition
```
Se face un deep copy al minionului de la coordonatele respective (pentru 
intantiere se foloseste clasa BasicMinion, Minion fiind abstracta) si se 
adauga in nod, in caz ca nu exista se adauga eroarea"No card at that position"
 alaturi de 'command' si se adauga in output array.    
___________________________________________________________________________________________________
```
* useEnvironmentCard
```
Se verifica al cui e turn-ul, apoi cazurile invalide, iar daca niciunul nu este
intalnit se apeleaza metoda useAbility() implemntata fiecarei carti de tipul 
Environment si se verifica daca a murit vreun minion pentru a fi sters de pe 
masa (functia removeMinionOnRowIfDied pentru a evita duplicate code).  
___________________________________________________________________________________________________
```
* getFrozenCardsOnTable
```
Se creeaza un array de tip Minion si se adauga in functie de coordonatele 
stocate in .getFrozenCoords() cartile inghetate la momentul actual, apoi se
adauga in nod array-ul si se pune in output array.    
___________________________________________________________________________________________________
```
* cardUsesAttack
```
Se verifica al cui este turn-ul, apoi daca exista carti tank pe randul 
adversarului, apoi cazurile invalide, iar daca nu ne aflam in niciun caz invalid 
cartea ataca, se scade viata cartii atacate si se verifica daca a murit pentru a
fi sters de pe masa (folosim functia checkForTanksAndUsesAttackpentru a evita 
duplicate code).
___________________________________________________________________________________________________
```
* cardUsesAbility
```
Se verifica al cui este turn-ul, apoi daca exista carti tank pe randul 
adversarului, apoi cazurile invalide, iar daca nu ne aflam in niciun caz
invalid cartea isi foloseste abilitatea apelata cu .use Ability(), se 
verifica daca a murit minionul atacat pentru a fi sters de pe masa (folosim 
functia checkForTanksAndUsesAbility pentru a evita duplicate code).    
___________________________________________________________________________________________________
```
* useHeroAbility
```
Se verifica al cui turn este, apoi cazurile invalide, iar daca nu ne aflam 
intr-un caz invalid eroul isi va folosi abilitatea apelata cu .useAbility()
 si se va scadea mana jucatorului in cauza. 
___________________________________________________________________________________________________
```
* useAttackHero
```
Se verifica al cui turn este, apoi daca exista carti tank pe randul adversarului,
apoi cazurile invalide, iar daca nu ne aflam intr-un caz invalid se va ataca 
eroul si i se va verifica viata, daca aceasta scade la zero sau mai putin se va
afisa "Player one/two killed the enemy hero", caz in care se actualizeaza scorul
(incrementam playerOneWins sau playerTwoWins).     
___________________________________________________________________________________________________
```
* getTotalGamesPlayed
```
Se adauga in nod 'command' si 'output' cu totalGamesPlayed, iar apoi se adauga
nodul in array    
___________________________________________________________________________________________________
```
* getPlayerOneWins
```
Se adauga in nod 'command' si 'output' cu playerOneWins, iar apoi se adauga
nodul in array    
___________________________________________________________________________________________________
```
* getPlayerTwoWins
```
Se adauga in nod 'command' si 'output' cu playerTwoWins, iar apoi se adauga
nodul in array    
___________________________________________________________________________________________________

## Feedback :

	Cea mai bine explicata tema de pana acum. Mi s-a parut greut sa respect limita
de caractere pe rand din checkstyle (100), de aceea am si spart niste randuri 
destul de ciudat, dar in mare mi-a placut maxim sa lucrez la ea, iar ideea cu
jocul este foarte faina. Big up!
 
___________________________________________________________________________________________________

## Resources:

1. [OCW](https://ocw.cs.pub.ro/courses/poo-ca-cd)
2. [ATTACOMSIAN](https://attacomsian.com/blog/jackson-create-json-array)
3. [WIKIPEDIA](https://en.wikipedia.org/wiki/Object-oriented_programming#Data_Abstraction)
