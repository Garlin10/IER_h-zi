step(-1). //default: dont move
//0: up
//1: right
//2: down
//3: left

+!step : not hasBox & ~boxPath(null)
	<- !moveTowardsInjured.

+!step : true
	<-!carry.

	//X and Y: location of injured
+!moveTowardsInjured : AgX = X & AgY > Y 
	<- +step(0). //up
	
+!moveTowardsInjured : AgX = X & AgY < Y 
	<- +step(2). //down
	
+!moveTowardsInjured : AgX < X & AgY = Y 
	<- +step(1). //right
	
+!moveTowardsInjured : AgX > X & AgY = Y 
	<- +step(3). //left

+!moveTowardsInjured : true <- .print("error").

+!carry : boxType(1)
	<- !carryType1Box.

+!carry : boxType(0)
	<- !carryType0Box.

+!carry : true <- .print("error").
	
//RX and RY: shortest exit path R
+!carryType1Box : AgX = RX & AgY > RY
	<- +step(0).
	
+!carryType1Box : AgX = RX & AgY < RY
	<- +step(2).
	
+!carryType1Box : AgX < RX & AgY = RY
	<- +step(1).
	
+!carryType1Box : AgX > RX & AgY = RY
	<- +step(3).
	
+!carryType1Box : true
	<- .print("error").
	
//BX and BY: shortest exit path B
+!carryType0Box : AgX = BX & AgY > BY
	<- +step(0).
	
+!carryType0Box : AgX = BX & AgY < BY
	<- +step(2).
	
+!carryType0Box : AgX < BX & AgY = BY
	<- +step(1).
	
+!carryType0Box : AgX > BX & AgY = BY
	<- +step(3).
	
+!carryType0Box : true
	<- .print("error").