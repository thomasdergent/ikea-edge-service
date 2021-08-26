# ikea-edge-service

- [Ikea-edge-service](#ikea-edge-service)
  * [Over het project](#over-het-project)
  * [Links naar microservices](#links-naar-microservices)
  * [Link naar front-end](#link-naar-front-end)
  * [Diagram](#diagram)
  * [Swagger UI](#swagger-ui)

## Over het project
Welkom op de infopagina van mijn Java Advanced Topics project. Dit project gaat over de Ikea-winkels in België met informatie over hun bijhorende producten. De back-end van deze omgeving is opgezet met de products-service geconnecteerd met PostgreSQL, store-service geconnecteerd met MongoDB en de ikea-edge-service. De services sturen de gegevens van de database naar de front-end gemaakt in Angular. Links naar alle repositories vind je hier onder.

## Links naar microservices
Hier vind je de links naar alle microservices:
- [products-service](https://github.com/thomasdergent/products-service)
- [store-service](https://github.com/thomasdergent/store-service)

Docker commands
- docker run -d --name ikea-products-service -p 8051:8051 -e POSTGRES_HOST=host.docker.internal -e POSGTRES_HOST=host.docker.internal thomasdergent/products-service:latest
- docker run -d --name ikea-store-service -p 8052:8052 -e MONGODB_HOST=host.docker.internal thomasdergent/store-service:latest
- docker run -d --name ikea-edge-service -p 8050:8050 -e STORE_SERVICE_BASEURL=host.docker.internal=host.docker.internal:8052 -e PRODUCT_SERVICE_BASEURL=host.docker.internal:8051 thomasdergent/ikea-edge-service:latest

## Link naar front-end
Hier vind je de link naar de front-end:
- [front-end](https://github.com/thomasdergent/ikea-front-end)

## Diagram
Hier vind je de diagram van de volledige werking van de microservices architectuur:

![diagram](https://user-images.githubusercontent.com/73995291/131002021-cf6e3122-0f93-4b5e-a3c8-173fa53baf42.png)

## Swagger UI
Hier vind je een overzicht van alle endpoints aanwezig in de ikea-edge-service:

- GET /product/{articleNumber}

![get product by articlenumber](https://user-images.githubusercontent.com/73995291/130999043-c169db71-0be5-448c-b761-1a5bb63298bd.png)

- GET /products

![get all products](https://user-images.githubusercontent.com/73995291/130999135-004e26bc-f2e6-4b83-bd40-93c98d198f09.png)

- GET /products/{category}

![get products by category](https://user-images.githubusercontent.com/73995291/130999240-57399cfc-b549-461a-a35f-be4d5ad1fe15.png)

- GET /product/{articleNumber}/store/{storeName}

![get store by articlenumber and storename](https://user-images.githubusercontent.com/73995291/130999321-f18caf67-3817-492c-80d4-5126b3f7840b.png)

- POST /product

![Add prodct](https://user-images.githubusercontent.com/73995291/130999379-08c1b212-3b91-48a4-904d-06182b2598c0.png)

- PUT /product/{articleNumber}

![update product](https://user-images.githubusercontent.com/73995291/130999452-79e2a050-9b15-4abd-907c-00707e285b9a.png)

- DELETE /product/{articleNumber}

![delete product](https://user-images.githubusercontent.com/73995291/130999492-3a4b5c68-8dda-4c8a-9f47-066c28e72d81.png)

- POST /store

![Add store](https://user-images.githubusercontent.com/73995291/130999575-a51ae4b2-ebb1-484c-9b7f-f51a1ff2616e.png)

- PUT /product/{articleNumber}/store/{storeName}

![update store](https://user-images.githubusercontent.com/73995291/130999644-3d9c83aa-92fa-4964-8270-dce8f43e9852.png)

- DELETE /product/{articleNumber}/store/{storeName}

![delete store](https://user-images.githubusercontent.com/73995291/130999673-c1b5da34-88cc-4625-bfb1-01ce29516f42.png)
