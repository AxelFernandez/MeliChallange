# Meli Challange

_Challange de Prueba para MercadoLibre, la app cuenta con un buscador, los resultados de la busqueda
 y una pantalla con los detalles del producto_

## Instrucciones para compilar 🚀

La instalacion en los respositorios Android es muy sencillo:

- Clona el repositorio con esta url `https://github.com/AxelFernandez/MeliChallange.git`
- Abrelo con Android Studio
- Espera la sincronizacion de Gradle y luego compila.

Puede tardar un tiempo, mientras tanto...puedes ir a buscar un café

## Arquitectura y Detalles a tener en cuenta

Este proyecto esta basado en las guidelines provistas por Google, en este documento 
voy a especificar en donde se utiliza cada una de ellas
    
## Componentes Visuales
    
Se crearon dos componentes visuales, 
 - Una AppBar Custom, que maneja el estado, el titulo y un boton de busqueda rápida.
 - Un Search Component que se utiliza en dos lugares de la app, al comienzo para la primera
  busqueda y en la pantalla de detalles del producto.
  
  En este ultimo lugar, si el usuario no encontro lo que buscaba, puede utilizar este componente
  que redirige nuevamente a la pantalla de busqueda pero limpiando su Stack, de esta manera, si 
  presiona back, la app lo redirige a la pantalla principal, y no al producto que ya habia visto.

## ViewModels:
    
Este proyecto cuenta con tres pantallas de las cuales dos de ellas cuentan con su propio View Model.
_ItemDetailFragmentViewModel_ que pertenece a la pantalla donde se muestra el detalle de un item, 
cuenta tambien con una factory que resuelve la inyeccion de Dependencias.

_ResultFragmentViewModel_ que pertenece a la pantalla que se encarga de mostrar los resultados,
utiliza una factory de _AbstractSavedStateViewModelFactory_ que aparte de resolver la inyeccion
de dependencias, tambien guarda el estado de la UI cuando el dispositivo es rotado.

Ambos ViewModels fueron diseñados para ser unitesteables.

Como Mejora, se podria haber utilizado una biblioteca como Dagger o Hilt, pero actualmente 
no estoy muy familarizado con esas bibliotecas, por lo que opte crear los View Models con Factory
y una inyeccion manual.
    
    
## Repository
    
Las buenas practicas de Google siempre recomiendan utilizar un unico repositorio por fragment,
por lo que este proyecto cuenta con dos repositorios, que son los encargados de abstraer 
la fuente de datos de la que se provee al ViewModel.

Como en este caso, solo necesitamos ir a buscar informacion a la API de Mercado Libre, 
los repositorios no son muy complejos. Pero si en un futuro guardamos productos en la base de 
datos del telefono, es en este punto donde debemos ir a buscarlos utilizando lo que recomientda
Google, que es DAO

## Api Helpers
    
La clase _ApiHelper_ es la clase que resuelve las llamadas de la api, pero sin crear dependencia 
con Retrofit pues sin esta clase, deberiamos de instanciar un objeto _RetrofitBuilder_ desde el 
repositorio. La clase ApiHelper recibe por parametro a _ApiService_ pero es aqui donde debemos 
de pasarle el objeto de Retrofit desde un fragment, para no generar ninguna dependencia entre 
estas clases. 
    
## Resources
Es la clase encargada de manejar los diferentes estados de la llamada, en Status se crearon cuatro

- Loading: Utilizado cuando el recurso esta cargando (normalmente utilizado al principio de la llamada)
- Error: Utilizado cuanod hay un error en la conexion por cualquier motivo.
- Success: Utilizado cuando la conexion se realizo exitosiamente.
- Empty: Utilizado cuando la conexion se realiza correctamente, pero el resultado del mismo es vacio

Cada LiveData que se crea, se utiliza este Resource, para actualizar por los diferentes estandos 
en que se encuentra la llamada a la api, desde que se establece la conexion y comeinza a cargar, 
hasta el estado de que si la conexion se realizo correctamente, o falló en algun punto.

En este diagrama se explica cual es la secuencia para las request de las Api y por las distintas
capas y estados por los que pasa.

![sequence](https://plantuml-server.kkeisuke.dev/svg/VP6zQWCn48JxVGgLIU4Nk2BOCOG4OWmktCjtWn7MIOTsvP2tprwAq-OIecf1Cz_ipvR55wbZ9F9LSghZ1ONefVZhY2Hky-JE0H-7F21sZY77j8-Xlxq75YIKXqVolOIx5pGXqLBEW1Ecp45o-UpS5N82roXlWf6jkwc8Mkqs2jXDuHLnGd46mBy8YuoeEMR_0J4y-_w6D2X1__O-Hkiq0YAhNgVFcHe_vSHGm82_4J_ZpJJ_4Kpxg_dRDZk5SQwbNtdjyipkrRjSidF72wFSls-cRwkTwjs_0000.svg)
    
## Paginación
    
En un Comienzo del proyecto se penso en realizar paginacion con Page 3 de Android, los modelos 
fueron diseñados con un apartado para Paginacion, pero por falta de tiempo en el proyecto, 
esta idea fue descartada.

Como mejora a futuro, se podría integrar paginacion al endpoint
    
    
## Live Data
    
Los Live Data utilizados en este proyecto son observados desde el Fragment y su valor es 
actualizado de acuerdo a los Resources emitidos anteriormente

En un comienzo se planteo utilizar dos formas de Live data,

En _ResultViewModel_ al necesitar de la interaccion del usuario en la barra de busqueda se opto 
por esta opcion, es la mas conocida, donde se llama a _livedata.postValue()_ 
para acutalizar su valor. Es una metodologia facil de utilizar y facil de testear. 
    
```
var items = MutableLiveData<Resource<ItemResponse>>()

    fun searchItem(search :String){
        viewModelScope.launch(dispatcher) {
            items.postValue(Resource.loading()) //Utilizado aqui
            try {
                val response = resultRepository.searchItems(search)
                if (response.results.isEmpty()){
                    items.postValue(Resource.empty())
                }else{
                    items.postValue(Resource.success(response))
                }
            }catch (e: Exception){
                items.postValue(Resource.error(null,e.message?:"Error Rendering items"))
            }
        }
    }
```

En _ItemDetailViewModel_ se opto por una metodologia nueva considerando que no dependiamos 
de la interaccion del usuario, ya que contabamos con su decision sobre el item que habia elegido

```
    fun getDetail(id : String) = liveData(dispatcher) {
        emit(Resource.loading()) //se actualiza el valor aqui
        try {
            emit(Resource.success(data = detailRepository.getItemDetail(id)))
        }catch (e:Exception){
            emit(Resource.error(null, e.message!!))
        }
    }
```  

Es una sintaxis mucho mas limpia y sencilla de entender, la corrutina se inicializa 
con el mismo LiveData, y los valores se emiten, por lo que en el fragment, llamamos la funcion y
se observa en la misma llamada.

Lamentablemente me encontre con el problema de no poder recuperar su valor para poder testearlo 
por lo que se opto utilizar la misma arquitectura que en _ResultViewModel_ para poder testearlo.

Como mejora en este punto, me encantaria aprender una manera correcta de testear esta ultima 
forma de Live Data.
    
## Corutinas e Injección de Dependencias

Como punto principal aqui, y como se menciono más arriba se opto por utilizar una inyeccion manual
en lugar de utilizar Hilt o Dagger, la idea de realizar una inyeccion de dependencias, es 
desacoplar responsabilidades en las clases y tambien facilitar los el testeo.

Un claro ejemplo son los test de corrutinas, pues si no se inyecta el dispatcher al View Model
 y este por defecto se utiliza _Dispatchers.IO_ a la hora de correr los test, ese hilo se pierde y no podemos 
testear lo que se encuentra dentro.

Para poder testear una corrutina, se debe utilizar _Dispatchers.Main_ y correr la misma corrutina 
en el hilo principal de la app.
    
![corrutines](https://i.stack.imgur.com/FvSax.jpg)

## Testing

Se ha realizado test unitarios en los View Models y test de integracion con espresso para testear 
la UI, tambien se realizo una integracion con GitHub para correr los test unitarios.
    
    
## Knows Bugs
En este ultimo apartado me gustaria dejar los pequeños bugs que he encontrado y por los tiempos del 
proyecto no he podido llegar a solucionarlos. 

STR:
    - Al escribir una busqueda en la pantalla principal y si se selecciona _Buscar_ sin bajar el teclado
    el teclado se mantiene en la proxima pantalla
    - En la pantalla de resultados, si se realiza alguna busqueda y luego se rota la pantalla, 
    el teclado vuelve a subir.

## Build with 🛠️

* [Junit](https://junit.org/junit5/) - Used for Test
* [Espresso](https://developer.android.com/training/testing/espresso) - Used for IntegrationTest


## Autor ✒️


* **Axel Fernandez** - *Developer* - [LinkedIn](https://www.linkedin.com/in/axel-fernandez/)

---
 Hecho con ❤️ por [Axel Fernandez](https://github.com/AxelFernandez) 😊
