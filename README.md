# ikea-edge-service

- [Ikea-edge-service](#ikea-edge-service)
  * [Over het project](#over-het-project)
  * [Links naar microservices](#links-naar-microservices)
  * [Link naar front-end](#link-naar-front-end)
  * [Diagram](#diagram)
  * [Swagger UI](#swagger-ui)

## Over het project
Welkom op de infopagina van mijn Java Advanced Topics project. Dit project gaat over de Ikea-winkels in België met informatie over hun bijhorende producten. De back-end van deze omgeving is opgezet met de store-service geconnecteerd met PostgreSQL, products-service geconnecteerd met MongoDB en de ikea-edge-service. De services sturen de gegevens van de database naar de front-end gemaakt in Angular. Links naar alle repositories vind je hier onder.

## Links naar microservices
Hier vind je de links naar alle microservices:
- [user-service](https://github.com/thomasdergent/store-service)
- [products-service](https://github.com/thomasdergent/products-service)

## Link naar front-end
Hier vind je de link naar de front-end:
- [front-end](https://github.com/thomasdergent/store-service)

## Diagram
Hier vind je de diagram van de volledige werking van de microservices architectuur:

![schema](https://user-images.githubusercontent.com/73995291/129954321-f1e4bf83-6ed5-4535-ad2e-0e148966f35f.jpg)

## Swagger UI
Hier vind je een overzicht van alle endpoints aanwezig in de ikea-edge-service:

- GET /store/{storeName}/article/{articleNumber}

![getproductbystorenameandarticlenumber](https://user-images.githubusercontent.com/73995291/129954892-00546b73-2206-4338-a996-f65a7303279b.jpg)

- GET /products/store/{storeName}

![getproductsbystorename](https://user-images.githubusercontent.com/73995291/129954970-bbce3217-350a-492f-8c25-6c41bc235b2b.jpg)

- GET /store/{storeName}/category/{category}

- GET /stores

- PUT /store/{storeName}/article/{articleNumber}

- DELETE /store/{storeName}/article/{articleNumber}

![delete](https://user-images.githubusercontent.com/73995291/129958871-dee71fc7-4074-4ea5-8325-3c4b8f751fc2.jpg)

