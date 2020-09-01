# NOAAChallenge

NOAA : Monitoreo de boyas en altamar. 
 
NOAA(National Oceanic and Atmospheric Administration) le ha pedido a las chicas de Backend de ADA que le armen un prototipo de API Rest que permita registrar embarcaciones desde una serie de boyas en altamar. Cada una de estas boyas marinas tiene sensores que detectan embarcaciones cercana y al mismo tiempo el nivel del mar actual que se encuentra la boya. Estas embarcaciones a su vez, transmiten un código único por barco usando ondas cortas que son detectadas por las boyas. 
Las boyas constantemente transmitirán lo que llamaremos “Muestra”. También poseen un faro de luz, que cambia de color según la siguiente tabla:  
ROJO: Marea peligrosa 
AMARILLO: Advertencia de marea peligrosa  
VERDE: todo Ok 
AZUL: indefinido 
  
Para esto, se diseñó lo siguiente: 
Entidades Principales 
 Boya  Id de Boya : number (numeración autogenerada) 
 Color Luz: string, color actual de la luz.(la primer vez esta en nulo). Ver tabla arriba 
 Longitud Instalacion: double (coordeanada donde se dejo la boya originalmente) 
              Latitud Instalacion: double (coordenada donde se dejo la boya originalmente) 
 Nota: el color de la boya es un string con los siguientes valores ROJO, VERDE, AZUL y AMARILLO 
 
Muestra:  
 
 Esta posee la información que transmite la boya 
 Id de Muestra: number: registro único que identifique  a una muestra, autoincremental. 
 Id de Boya: number: numero de boya a la que pertenece la muestra 
 Horario Muestra: Fecha y hora del horario de la muestra 
 Matricula Embarcacion: string, matrícula de la embarcación, esto solo si hay un barco en la cercanía o el barco está transmitiendo. Por lo tanto este es opcional. 
 Longitud: double, coordenada actual de la boya(no necesariamente es la misma en la que se instaló). 
 Latitud: double , coordenada actual de la boya 
 Altura al nivel del mar: double, número que especifica a qué nivel del mar está la boya en METROS. Ej 0 = significa que está a 0 metros del nivel del mar. -10 metros significa que esta “hundida” -10 metros y un valor de 20ms es que esta 20 metros arriba del nivel del mar. 
   
Nota 1: una boya genera varias muestras, y una muestra corresponde solo a una boya. 
 
API Rest Methods que pide NOAA para lograr el objetivo: 
 
Boyas 
POST /boyas    : que permita la creación boyas 
RequestBody: {  
 “longitudInstalacion”: 34.8877, 
 “latitudInstalacion”:  78.230 
} 
GET /boyas     : que devuelva las boyas SIN las muestras. 
GET /boyas/{id} : que devuelva la info de una boya en particular(SIN las muestras) 
PUT /boyas/{id} : que actualice SOLO el color de luz de la boya. El request esperado será: 
 { 
 “color”: “ROJO” 
 }
 Muestras 
POST /muestras   : que registre una muestra 
RequestBody { 
 “boyaId”: 32, 
 “horario”: “2020-08-08T22:25:30”,  //La separación de FECHA y HORA es por “T” 
 “matricula”: “99D9AAK” 
 “latitud”: -17.44681203, 
 “longitud”: -113.16478854, 
 “alturaNivelDelMar”: 50 
} 
Respuesta Esperada(JSON): 
{ 
 “id”: 25 //O cualquier número de muestra que devuelva. 
 “color”: “Un Color”  //Este será el color que deberá cambiar la luz de la boya 
} 
Nota: Si cuando se carga una muestra nueva en una boya, la alturaNivelDelMar es de MENOS de       -50(menos 50) o  de MAS de +50 (más 50), debera devolver “AMARILLO” en el color. 
En el caso de que sea menor a -100  o mayor a +100 el color deberá ser ROJO. 
Si no, el color deberá devolver es VERDE  
 
GET /muestras/boyas/{idBoya} : que devuelva la lista de muestras de una boya, indicado por “idBoya”. 
 
DELETE /muestras/{id}:   Reseteara el color de la luz de la boya a “AZUL” a partir de una muestra especifica   
Challenge BONUS: si logran cumplir CUALQUIERA de los siguientes objetivos, la NOAA duplicará los fondos para la realización final del producto. 
 
GET /muestras/colores/{color} : que devuelva la lista de muestras de un color en el siguiente formato JSON Array: 
[{ 
 “boyaId”: 1232, 
               “horario”: “2020-08-05T20:20:10”, 
 “alturaNivelDelMar”: 29 
}, 
{ 
 “boyaId”: 124, 
               “horario”: “2020-08-01T20:22:10”, 
 “alturaNivelDelMar”: 55 
}] 
 
GET /muestras/minima/{idBoya} : que devuelva la muestra donde la altura nivel del mar sea la minima para una boya en 
particular en este formato JSON(informar el horario en que ocurrió) 
{ 
 “color”: “ROJO”, //El color actual de la boya 
 “alturaNivelDelMarMinima”: 45, 
 “horario”: “2020-08-04T10:02:02” 
} 
EPIC BONU(TOP SECRET): si logran cumplir ESTE objetivo, NOAA no tendrá en cuenta los 2 bonus anteriors y triplicará el presupuesto. 
 
Intro: se ha comenzado a hacer un plan de contingencia para los casos que seamos atacados por Monstruos Gigantes o Naves Alienígenas marítimas. Lo que NOAA está pidiendo es armar un sistema de alerta que nos avise si una anomalía ha sucedido con las boyas. Este sistema deberá devolver un resultado diferente dependiendo de las siguientes consignas: 
A) Para una boya en particular, si se mantuvo en un lapso de 10 minutos a niveles de 200metros absolutos(o sea -200 o +200) por 10 minutos, ALERTA DE KAIJU (Monstruo Gigante como GODZILLA) B) Para una boya en particular, si se tuvo 2 muestras seguidas donde la diferencia de altura entre ambas es de +500 : ALERTA DE IMPACTO (Posible meteorito o Nave Alienígena que da brincos en el agua) 
El request será asi: 
GET /muestras/anomalias/{idBoya} 
Resultado Esperado: 
{ 
 “alturaNivelDelMarActual”: 30, 
 “horarioInicioAnomalia”: “2020-08-05T05:05:65”, 
“horarioInicioFinAnomalia”: “2020-08-05T05:05:65”, 
 “tipoAlerta”: “KAIJU” //Puede ser: KAIJU, IMPACTO 
} 
Para más referencias ver peliculas de: 
Godzilla 
Pacific Rim 
Battleship 
